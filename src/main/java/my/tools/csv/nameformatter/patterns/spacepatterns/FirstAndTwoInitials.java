package my.tools.csv.nameformatter.patterns.spacepatterns;

import java.util.regex.Pattern;

import my.tools.csv.nameformatter.Name;
import my.tools.csv.nameformatter.patterns.NamePattern;

public class FirstAndTwoInitials extends NamePattern{
	
	public static final Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z]+\\s[a-zA-Z]{1}\\s[a-zA-Z]{1}");
	public FirstAndTwoInitials(String text) {
    	originalText=text;
    }
	
	Name outputName = new Name();
	public Name transform() {
try {
		
		String[] names = originalText.trim().split(" ");		
		outputName.originalText=originalText;
		outputName.firstName=names[1];
		outputName.middleName=names[2];
		outputName.lastName=names[0];
		outputName.transformed=true;
		outputName.capitalize();
	}
	catch(Exception e) {
		
		System.out.println("Failure processing FirstAndInitials:"+outputName.toString());
		e.printStackTrace();
	
		
	}
		
		return outputName;
	}



}
