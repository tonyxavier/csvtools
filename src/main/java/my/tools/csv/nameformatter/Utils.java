package my.tools.csv.nameformatter;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Utils {
	
	public static void main(String args[]) {
		
		String[] orig = {"1","2","7","8","9"};
		String[] newFields= {"3","4","5","6"};
		
		String[] result=insertInArray(orig,newFields,5);
		
		
		for(String s:result)
		System.out.print(s+" ");
		
		
		
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
			
			String[] excludedWords = {" LLC"," INC","ENTERPRIS","PROPERTY","MGMT","COMMUNI","GROUP",
					       "ESTAT","MANAGE","LIMIT","PARTNER","HABITAT","COUNCIL",
					       "REALTY","LTD","PRODUCTS","HOUSING","PROGRAM","APARTM","TOWN","COUNTY",
					       "TRUST"," TRUS","HOUSE","AUTHORIT","DEVELOP","SELLER","TIITF","CHURCH","DISTRIBUTOR","INDUSTR",
					       "DEPARTM","STATE"," COMPA","SOUTHERN","INVEST","CENTER","PROPERT","LLP",
					       "ASSET","SERVICE","NATIONAL","BANK","AMERICA","UNIVERS","CORP","ASSOC",
					       "SCHOOL","FUND","CAPITAL","L.L.C","L.C","HOUSING","LENDING","FUNDING",
					       "MORTGAGE","JACKSONVILLE","COOPERAT","PROFESSION","P.A","HOLDINGS","CREDIT",
					       "COUNTRY","OWNER","MOBILE","LEASING","EXPORT","L L P","VENTURE","HOMES",
					       "AVENUE","STORE","SOCIETY"," FARMS","MANUFACTURE","CONSTRUCTION","L L C",
					        " AT "," OF "," FOR ","FELLOWSHIP","ASSEMBL"," LP"," LC","DISTRICT","BAPTIST",
					        "SUBSIDIARY","SOUTHEAST","UNITED","MISSIONARY","SPRINGS"," EST","OUTDOOR",
					        "ADVERTIS","CHAPEL","COOLING","COMMERCI","TRANSPOR","REALTOR","FAMILY",
					        "ACADEMY","EXECUTIVE","HEAVEN", " ROAD ","RESIDENTIAL","CEMETERY"," LIFE ",
					        " REVOCAB"," LIFE E"," LIVING ","PENTECOSTAL"," CITY"};
			
			
			for(String exc:excludedWords) {
				
				if(name.contains(exc)) {
					
					//System.out.println("SKIPPED!!!!:"+name +" contains "+exc);
					return true;
				}
			}
			
			
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
			
			return false;
		}
	  
	  public static String preProcess(String str) {
			
			
			str = str.toUpperCase().trim();
			
			if(str.contains(" JR"))
				str=str.replace(" JR", "");
			
			if(str.contains(" SR"))
				str=str.replace(" SR", "");
			
			if(str.contains(" ET AL"))
				str=str.replace(" ET AL", "");
			
			if(str.endsWith(" ETA L"))
				str=str.replace(" ETA L", "");
			
			if(str.contains(" ETAL"))
				str=str.replace(" ETAL", "");
			
			
			if(str.endsWith(" ET A"))
				str=str.replace(" ET A", "");
			
			
			if(str.contains("B/E"))
				str=str.replace("B/E", "");
			
			if(str.contains("R/S"))
				str=str.replace("R/S", "");
			
			if(str.endsWith("II")||str.endsWith("III")||str.endsWith("IV")||str.endsWith("VI"))
				str=str.substring(0,str.length()-2).trim();
			
			
			str = str.trim().replaceAll(" +", " ");
						
			return str;
		}

	  
	  
	  public static boolean isOrgPostProcess(String name) {
		  
		  
		  if(name.contains(" & "))
			  return true;
		  
		  
		  return false;
		  
	  }
		

}
