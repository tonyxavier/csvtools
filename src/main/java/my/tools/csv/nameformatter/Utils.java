package my.tools.csv.nameformatter;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Utils {
	
	private static String NAME = "([a-zA-Z]+[-']?[a-zA-Z]+|[a-zA-Z])";
	private static  String SPACE = "\\s";
	
	public static void main(String args[]) {
		
		String name = "MORRIS, VINCENT";
		
		System.out.println(isOrganization(name));
		
		
	}
	
	
	public static String[] insertInArray(String[] orig,String[] newFields,int insertAt) {
		
		int lengthOfNewArray=orig.length+newFields.length;
		String[] out = new String[lengthOfNewArray];
		
		for(int i=0;i<insertAt;i++)
			out[i]=orig[i];
		
		
		for(int i=insertAt,j=0;i<insertAt+newFields.length;i++,j++)
			out[i]=newFields[j];
		
		
		
		for(int i=insertAt+newFields.length,j=insertAt;i<lengthOfNewArray;i++,j++)
			out[i]=orig[j];
		
			
		
	
		return out;
		
	}
	
	
	public static int getCsvFieldIndex(String[] record, String fieldName) {		
		
				
		for(int i=0;i< record.length;i++)
		{
			if(record[i].equalsIgnoreCase(fieldName)){
				return i;
			}
			
		}
		
		return -1;
		
	}
	
	
	  public static String getDirectoryName(File file)
	   {
		   
		   if(file==null)
			   return null;
		   
		   if(file.isDirectory())
			   return file.getName();
		   
		   String fullName=file.getAbsolutePath();
		   
		   String filePath = fullName.
		    	     substring(0,fullName.lastIndexOf(File.separator));	
		   
		   System.out.println("Path:"+filePath);
		   
		   return filePath;
		   
	   }
	  
	  
	  public static boolean isOrganization(String name) {
			
			
			name = name.toUpperCase();
			
			/*
			String[] counts = name.split(" ");
			if(counts.length > 4)
				return true;
			*/
			
			
			Pattern pattern = Pattern.compile(".*[0-9]{2,}.*");
			Matcher m = pattern.matcher(name);
			if(m.matches()) {
				//System.out.println("NUMBER IN Name!!!!!!!!!:"+name);
				return true;
			
			}
			
			if(name.endsWith(" CO"))
				return true;
			
			
			
			if(name.contains(" CO "))
				return true;
			
			if(name.endsWith(" L P"))
				return true;
			
			if(name.endsWith(" LL"))
				return true;
			
			if(name.endsWith(" OF"))
				return true;
			
			if(name.endsWith(" TRU")||name.endsWith(" TRUS")||name.endsWith(" TR"))
				return true;			
			
			if(name.startsWith("ST "))
				return true;
			
			if(name.startsWith("ST. "))
				return true;
			
			if(name.startsWith("NORTH "))
				return true;
			
			if(name.startsWith("GREAT "))
				return true;
	
			
			
			
			String[] excludedWords = {"LLC",",LLC", " INC",".INC","ENTERPRIS","PROPERTY","MGMT","COMMUNI","GROUP",
					       "ESTAT","MANAGE","LIMIT","PARTNER","HABITAT","COUNCIL",
					       "REALTY","LTD","PRODUCTS","HOUSING","PROGRAM","APARTM","TOWN","COUNTY",
					       "TRUST"," TRUS","HOUSE","AUTHORIT","DEVELOP","SELLER","TIITF","CHURCH","DISTRIBUTOR","INDUSTR",
					       "DEPARTM","STATE"," COMPA","SOUTHERN","INVEST","CENTER","PROPERT","LLP",
					       "ASSET","SERVICE","NATIONAL","BANK","AMERICA","UNIVERS","CORP","ASSOC",
					       "SCHOOL","FUND","CAPITAL","L.L.C","L.C","HOUSING","LENDING","FUNDING",
					       "MORTGAGE","JACKSONVILLE","COOPERAT","PROFESSION","P.A","HOLDINGS","CREDIT",
					       "COUNTRY","OWNER","MOBILE","LEASING","EXPORT","L L P","VENTURE","HOMES",
					       "AVENUE","STORE","SOCIETY"," FARMS","MANUFA","CONSTRUCT","L L C",
					        " AT "," OF "," FOR ","FELLOWSHIP","ASSEMBL"," LP"," LC"," L C","DISTRICT","BAPTIST",
					        "SUBSIDIARY","SOUTHEAST","UNITED","MISSIONARY","SPRINGS","OUTDOOR",
					        "ADVERTIS","CHAPEL","COOLING","COMMERCI","TRANSPOR","REALTOR","FAMILY",
					        "ACADEMY","EXECUTIVE","HEAVEN", " ROAD ","RESIDENTIAL","CEMETERY"," LIFE ",
					        " REVOCAB"," LIFE E"," LIVING ","PENTECOSTAL"," CITY"," MINISTR"," PET ",
					        "BUILDERS"," CREATION"," CATHOLIC","BUILDING","CAPITAL","HOSPITAL","BELIEVERS",
					        "METHODIS"," CHURC"," CLUB"," FLAVORS"," COUNC","NORTHWEST","RETAIL","COMFORT",
					        "SOLUTION","OPPORTUN","NEW ","ENGINEERING","MACHINE","MARKET","AUTOMOTIVE",
					        "ROOFING","CONTROL"," UNDER ","STREET "," YOUTH ","CONSULT","TAX ","SPECIAL",
					        " STAR ","PACIFIC"," PAINT","CONDOMI"," ON ","INNOVAT","HOMEOW","SUPPORT",
					        "REPAIR","MAINTEN"," THE ","MOTOR","IMPORT","CAREER","FLORIDA","FOUNDA",
					        "QUALITY","WHOLESALE"," GAS ","PETROLEUM"," FUEL","ELECTRIC","BUSINESS",
					        "SOUTH "," GATE ","STATION","CONSULT","INFORMA"," BY ","U S A"," HOME ",
					        "ADMINIST","CHARITY"," ST ","RECREATION","OFFICE","PLAZA","OUTER"
					        
					        };
			
			
			for(String exc:excludedWords) {
				
				if(name.contains(exc)) { 	
					//System.out.println("Conains:"+exc);
					return true;
				}
				
			}
			
			
				
			return false;
		}
	  
	  
	  public static String preProcess(String str) {
			
			
			str = str.toUpperCase().trim();
			
			if(str.endsWith("."))
				str=str.substring(0,str.length()-1).trim();	
			
			if(str.endsWith("&"))
				str=str.substring(0,str.length()-1).trim();	
	
			
			if(str.contains(" JR "))
				str=str.replace(" JR ", " ");
			

			if(str.contains(" JR,"))
				str=str.replace(" JR,", ",");
			 
			if(str.contains(" JR."))
				str=str.replace(" JR.", ",");
			
			if(str.endsWith(" JR"))
				str=str.replace(" JR", "");
			
			
			
			
			if(str.contains(" , "))
				str=str.replace(" , ", ", ");	          
	        
			
			if(str.contains(" SR "))
				str=str.replace(" SR ", " ");
			
		

	        if(str.matches(".* SR"))	        
	        	str=str.replace(" SR", "");
	        

	        if(str.matches(".* JR"))	        
	        	str=str.replace(" JR", "");
	        
			
			
			if(str.contains(" ET AL"))
				str=str.replace(" ET AL", "");
			
			if(str.endsWith(" ETA L"))
				str=str.replace(" ETA L", "");
			
			if(str.endsWith("& ET AL"))
				str=str.replace("& ET AL", "");
			
			if(str.contains(" ETAL"))
				str=str.replace(" ETAL", "");
			

			if(str.contains("\\"))
				str=str.replaceAll("\\\\", "");
			
			if(str.trim().endsWith("/"))
				str=str.replaceAll("/", "");
			
			
			if(str.endsWith(" ET A"))
				str=str.replace(" ET A", "");
			
			
			if(str.contains("(") && str.contains(")"))
			    str=removeBrackets(str);

			if(str.endsWith("."))
				str=str.substring(0,str.length()-1).trim();			
			
			if(str.contains("B/E"))
				str=str.replace("B/E", "");
			
			if(str.contains("R/S"))
				str=str.replace("R/S", "");
			
			if(str.endsWith("III"))
				str=str.substring(0,str.length()-3).trim();			
			
			
			if(str.endsWith("II")||str.endsWith("IV")||str.endsWith("VI"))
				str=str.substring(0,str.length()-2).trim();
			
			
			if(str.contains(" III "))
				str=str.replace(" III ", " ");	
			
			if(str.contains(" II "))
				str=str.replace(" II ", " ");	
			
			if(str.contains(" IV "))
				str=str.replace(" IV ", " ");	
			
			if(str.endsWith("."))
				str=str.substring(0,str.length()-1).trim();
			
			
			str = str.trim().replaceAll(" +", " ");
			
			
			
			
			if(str.matches(NAME+SPACE+NAME+SPACE+"[a-zA-Z][.]\\s[a-zA-Z][.]"))	        
	        	str=str.replace(".", "");
			
			if(str.matches(NAME+SPACE+NAME+SPACE+"[a-zA-Z][.][a-zA-Z][.]"))	        
	        	str=str.replace(".", " ");
			
			
			//if there's no space after comma, insert a space.
			/*
			 if(str.matches("[a-zA-Z]+['-]?[a-zA-Z]+[,][a-zA-Z]+\\s?[a-zA-Z]+"))	        
		        	str=str.replace(",", ", ");
		        	
		        
		     if(str.matches("[a-zA-Z]+['-]?[a-zA-Z]+[,][a-zA-Z]+"))	        
		        	str=str.replace(",", ", "); 
		     
		     if(str.matches("[a-zA-Z]+['-]?[a-zA-Z]+[.][a-zA-Z]+"))	        
		        	str=str.replace(".", ", "); 
				*/		
			return str.trim();
		}

	  
	  
	  public static boolean isOrgPostProcess(String name) {
		  
		  
		  if(name.contains(" & "))
			  return true;
		  
		  String[] counts = name.split(" ");
			if(counts.length > 4)
				return true;
		  
		  return false;
		  
	  }
	  
	  
	  private static String removeBrackets(String str) {
		  
		 return str.replaceAll("\\s*\\([^\\)]*\\)\\s*", " ").trim();
		  
	  }
		

}
