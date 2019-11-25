package my.tools.csv.nameformatter.patterns;

import java.util.regex.Pattern;

import my.tools.csv.nameformatter.Name;

public class FirstLastPattern extends NamePattern{	 
	
	
	public static final Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z]+\\s[a-zA-Z][a-zA-Z]+");

	
	public FirstLastPattern(String text) {
    	originalText=text;
    }
	
	public Name transform() {

		Name outputName = new Name();
		String[] names = originalText.split(" ");
		System.out.print(names[0]+"  "+names[1]);
		outputName.originalText=originalText;
		outputName.firstName=names[0];
		outputName.lastName=names[1];
		
		
		return outputName;
	}

}
