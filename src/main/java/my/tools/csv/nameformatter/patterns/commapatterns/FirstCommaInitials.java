package my.tools.csv.nameformatter.patterns.commapatterns;

import java.util.regex.Pattern;

import my.tools.csv.nameformatter.Name;
import my.tools.csv.nameformatter.patterns.NamePattern;

public class FirstCommaInitials extends NamePattern{

	
public static final Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z]+[,.]?\\s[a-zA-Z]{1}");

	
	public FirstCommaInitials(String text) {
    	originalText=text;
    }
	
	Name outputName = new Name();
	public Name transform() {
try {
		
		String[] names = originalText.trim().split(" ");		
		outputName.originalText=originalText;
		String last = names[0];
		last=last.replaceAll(",", "");    
		outputName.firstName=names[1];
		outputName.lastName=last;
		outputName.transformed=true;
		outputName.capitalize();
	}
	catch(Exception e) {
		
		System.out.println("Failure processing FirstCommaInitials:"+outputName.toString());
		e.printStackTrace();
	
		
	}
		
		return outputName;
	}


}
