package my.tools.csv.nameformatter.patterns.commapatterns;

import java.util.regex.Pattern;

import my.tools.csv.nameformatter.Name;
import my.tools.csv.nameformatter.patterns.NamePattern;

public class LastCommaFirstInitialPattern extends NamePattern{
	
	public static final Pattern pattern = Pattern.compile("[a-zA-Z]+[,.]\\s?[a-zA-Z]+\\s[a-zA-Z]{1}");


	public LastCommaFirstInitialPattern(String text) {
    	originalText=text;
    	normalizeInput();
    	
    }
	

	public Name transform() {

		Name outputName = new Name();
		try {
			
		
			
		String[] names=originalText.split(" ");
    	String last=names[0];
    	last=last.replaceAll(",", "");    	
    	outputName.originalText=originalText;
    	outputName.firstName=names[2];
    	outputName.middleName=names[1];
    	outputName.lastName=last;
    	outputName.transformed=true;
    	outputName.capitalize();
	}
	catch(Exception e) {
		
		System.out.println("Failure processing LastCommaFirstInitialPattern:"+"ORIGINAL:"+originalText+" "+outputName.toString());
		e.printStackTrace();
	
		
	}
	
		return outputName;
	}
	
	
}
