package my.tools.csv.nameformatter.patterns;

import java.util.regex.Pattern;

import my.tools.csv.nameformatter.Name;

public class LastCommaFirstPattern extends NamePattern{
	
	public static final Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z]+[,]\\s?[a-zA-Z][a-zA-Z]+");


	public LastCommaFirstPattern(String text) {
    	originalText=text;
    }

	public Name transform() {

		Name outputName = new Name();		
		String[] names = originalText.split(" ");
		String first = names[1];
		String last = names[0];
		last=last.replaceAll(",", "");
		last=last.trim();
		
		outputName.originalText = originalText;
		outputName.firstName=first;
		outputName.lastName=last;
		
		
		
		return outputName;
	}
	

}
