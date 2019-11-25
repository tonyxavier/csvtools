package my.tools.csv.nameformatter.patterns;

import java.util.regex.Pattern;

import my.tools.csv.nameformatter.Name;

public class FirstLastInitialPattern extends NamePattern{
	//Original text like Arun Kumar B
	public static final Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z]+\\s?[a-zA-Z][a-zA-Z]+\\s[a-zA-Z]{1}");

    public FirstLastInitialPattern(String text) {
    	originalText=text;
    }

	public Name transform() {

		Name outputName = new Name();
		String[] names=originalText.split(" ");
		outputName.originalText=originalText;
		outputName.originalText=originalText;
		outputName.firstName=names[0];
		outputName.middleName=names[2];
		outputName.lastName=names[1];
		
		
		return outputName;
	}

}
