package my.tools.csv.nameformatter;

public class Name {
	
	public String originalText;
	public String firstName,middleName,lastName;
	public String firstName_2,middleName_2,lastName_2;
	public String organizationName;
	public boolean transformed=false;
	
	
	public String[] toStringArray(){
		
		String[] names={firstName,middleName,lastName,firstName_2,middleName_2,lastName_2,organizationName};
		
		return names;
		
	}
	
    public String[] toStringArrayFull(){
		
		String[] names={originalText,firstName,middleName,lastName,firstName_2,middleName_2,lastName_2};
		
		return names;
		
	}


    public String[] toStringAllFields() {
    	
    	String[] names= {originalText,firstName,middleName,lastName,firstName_2,middleName_2,lastName_2,organizationName};
    	return names;
    }
	
	public String toString() {
		
		return " Original Text:"+originalText+" First Name: "+firstName+" Middle Name: "+middleName+" Last Name: "
		  +lastName+" First Name 2: "+firstName_2+" Middle Name 2: "+middleName_2+" Last Name 2: "+lastName_2;
		
	}
	
	
	public boolean isTransformed() {
		
		return transformed;
	}
	
	
public void capitalize() {
		
		if(firstName!=null)		
		   firstName=capitalizeWord(firstName);		
		
		
       if(middleName!=null) {
			
			if(middleName.contains(" ")) 		
				middleName = capitalizeTwoWords(middleName);
			else				
				middleName=capitalizeWord(middleName);				
		}
       
       
       if(lastName!=null) {
			
			if(lastName.contains(" "))				
				lastName = capitalizeTwoWords(lastName);				
			else				
				lastName=capitalizeWord(lastName.toLowerCase());			
			
		}
       
       
		
	}
	
	
	private static String capitalizeWord(String str) {
		
		
	  try{
		
	    str = str.toLowerCase();
		return str.substring(0, 1).toUpperCase() + str.substring(1);

	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
		  return str;
	  }
	}
	

	private static String capitalizeTwoWords(String str) {
		
	
		String[] names = str.split(" ");
		
		if(names.length>1) {
			
			String name1 = capitalizeWord(names[0]);
			String name2 = capitalizeWord(names[1]);
			return name1+" "+name2;
		}
		
		
		
		return str;
	}
	
	
	public boolean isEmpty() {
		
		if(firstName==null && middleName==null && lastName==null && organizationName==null)
			return true;
		else
		   return false;
		
	}

	
	public void capitalizeSecondPart() {
		

		if(firstName_2!=null)		
			firstName_2=capitalizeWord(firstName_2);				
		
		
        if(middleName_2!=null) {
			
        	if(middleName_2.contains(" ")) 		
        		middleName_2 = capitalizeTwoWords(middleName_2);
			else				
				middleName_2=capitalizeWord(middleName_2);				
			
		}
      
      
      if(lastName_2!=null) {
			
    	  if(lastName_2.contains(" ")) 		
    		  lastName_2 = capitalizeTwoWords(lastName_2);
		  else				
			  lastName_2=capitalizeWord(lastName_2);	
			
		}
      

			

	}
	

}
