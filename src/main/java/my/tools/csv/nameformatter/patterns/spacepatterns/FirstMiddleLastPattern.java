package my.tools.csv.nameformatter.patterns.spacepatterns;

import java.util.regex.Pattern;

import my.tools.csv.nameformatter.Name;
import my.tools.csv.nameformatter.patterns.NamePattern;

public class FirstMiddleLastPattern extends NamePattern{	
	
	public static final Pattern pattern = Pattern.compile("[a-zA-Z]+['-]?[a-zA-Z]\\s[a-zA-Z]+['-]?[a-zA-Z]\\s[a-zA-Z]+['-]?[a-zA-Z]");


	public FirstMiddleLastPattern(String text) {
    	originalText=text;
    }

	public Name transform() {

		Name outputName = new Name();
		try {
		String[] names=originalText.split(" ");
		outputName.originalText=originalText;
		outputName.firstName=names[1];
		outputName.middleName=names[2];
		outputName.lastName=names[0];
		outputName.transformed=true;
		outputName.capitalize();
	}
	catch(Exception e) {
		
		System.out.println("Failure processing FirstMiddleLastPattern:"+outputName.toString());
		e.printStackTrace();
	
		
	}
	
		
		return outputName;
	}

}
