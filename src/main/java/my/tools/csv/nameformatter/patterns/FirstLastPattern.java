package my.tools.csv.nameformatter.patterns;

import java.util.regex.Pattern;

import my.tools.csv.nameformatter.Name;

public class FirstLastPattern extends NamePattern{	 
	
	
	public static final Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z]+\\s[a-zA-Z][a-zA-Z]+");

	
	public FirstLastPattern(String text) {
    	originalText=text;
    }
	
	Name outputName = new Name();
	public Name transform() {
try {
		
		String[] names = originalText.split(" ");		
		outputName.originalText=originalText;
		outputName.firstName=names[0];
		outputName.lastName=names[1];
		outputName.transformed=true;
		outputName.capitalize();
	}
	catch(Exception e) {
		
		System.out.println("Failure processing FirstLastPattern:"+outputName.toString());
		e.printStackTrace();
	
		
	}
		
		return outputName;
	}

}
