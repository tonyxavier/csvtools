package my.tools.csv.nameformatter;

public class Name {
	
	public String originalText;
	public String firstName,middleName,lastName;
	public boolean transformed=false;
	
	String[] toStringArray(){
		
		String[] names={originalText,firstName,middleName,lastName};
		
		return names;
		
	}
	
	public String toString() {
		
		return " Original Text:"+originalText+" First Name: "+firstName+" Middle Name: "+middleName+" Last Name: "+lastName;
		
	}
	
	
	public boolean isTransformed() {
		
		return transformed;
	}
	
	
public void capitalize() {
		
		if(firstName!=null) {
			
			if(firstName.length()>1)
			firstName=capitalizeWord(firstName.toLowerCase());			
			
		}
		
       if(middleName!=null) {
			
			if(middleName.length()>1)
			middleName=capitalizeWord(middleName.toLowerCase());			
			
		}
       
       
       if(lastName!=null) {
			
			if(lastName.length()>1)
			lastName=capitalizeWord(lastName.toLowerCase());			
			
		}
			
		
	}
	
	
	private static String capitalizeWord(String str) {
		
	
		return str.substring(0, 1).toUpperCase() + str.substring(1);

		
	}

	

}
