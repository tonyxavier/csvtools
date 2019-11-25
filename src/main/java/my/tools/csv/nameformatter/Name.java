package my.tools.csv.nameformatter;

public class Name {
	
	public String originalText;
	public String firstName,middleName,lastName;
	
	String[] toStringArray(){
		
		String[] names={originalText,firstName,middleName,lastName};
		
		return names;
		
	}

}
