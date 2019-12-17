package my.tools.csv.nameformatter.patterns.spacepatterns;

import java.util.regex.Pattern;

import my.tools.csv.nameformatter.Name;
import my.tools.csv.nameformatter.patterns.NamePattern;

public class FourNamesPattern extends NamePattern{

	//public static final Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z]+\\s[a-zA-Z][a-zA-Z]+\\s[a-zA-Z][a-zA-Z]+\\s[a-zA-Z][a-zA-Z]+");
	public static final Pattern pattern = Pattern.compile(NAME+SPACE+NAME+SPACE+NAME+SPACE+NAME);

	public FourNamesPattern(String text) {
    	originalText=text;
    }

	
	public static void main(String args[]) {
		
		FourNamesPattern pat = new FourNamesPattern("MARIN MIGUEL ANGEL CARMONA");
		System.out.println(pat.transform());
		
		
	}
	
	
	public Name transform() {

		Name outputName = new Name();
		try {
		String[] names=originalText.split(" ");
		outputName.originalText=originalText;
		outputName.firstName=names[1];		
		outputName.lastName=names[0];
		outputName.middleName = names[2]+" "+names[3];
		outputName.transformed=true;
		outputName.capitalize();
		//outputName.middleName=capitalizeWord(names[2])+" "+capitalizeWord(names[3]);
	}
	catch(Exception e) {
		
		System.out.println("Failure processing FourNamesPattern:"+outputName.toString());
		e.printStackTrace();
	
		
	}
	
		
		return outputName;
	}
	
	private static String capitalizeWord(String str) {
		
		
		return str.substring(0, 1).toUpperCase() + str.substring(1);

		
	}

}
