package my.tools.csv.nameformatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import my.tools.csv.nameformatter.patterns.NamePattern;

public class Test {
	
	public static void main(String[] args) {
		
		String str="SUNDY J MICHAEL X";
		
		//str=preProcess( str);
		//System.out.println(str);
		
		patternTest(str);
		
		NamePattern namePatterns = NamePatternFactory.getNamePattern(str);
		CustomNameParser customParser = new CustomNameParser();
		Name formattedName = customParser.parse(str);
		//System.out.println(formattedName);
		
	}
	
	
	private static void patternTest(String str) {
		
		Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z]+\\s[a-zA-Z]{1}\\s[a-zA-Z][a-zA-Z]+");
		Matcher m = pattern.matcher(str);
		
		if(m.matches()) {
			
			System.out.println("Pattern matched!");
			
		}
		else
			System.out.println("Pattern NOT matched!");
		
		Name outputName = new Name();
		try {		
		String[] names=str.split(" ");
		
		}
		catch(Exception e) {
			
			System.out.println("Failure processing FirstLastInitialPattern:"+outputName.toString());
			e.printStackTrace();		
			
		}

		
		
	}
		
		
		
		private static String preProcess(String str) {
			
			
			str = str.toUpperCase().trim();
			
			if(str.contains(" JR"))
				str=str.replace(" JR", "");
			
			if(str.contains(" SR"))
				str=str.replace(" SR", "");
			
			if(str.contains(" ET AL"))
				str=str.replace(" ET AL", "");
			
						
			return str;
		}
		
	

}
