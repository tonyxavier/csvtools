package my.tools.csv.nameformatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import my.tools.csv.nameformatter.patterns.NamePattern;

public class Test {
	
	protected static String NAME = "([a-zA-Z]+[-']?[a-zA-Z]+|[a-zA-Z])";
	protected static String SPACE = "\\s";
	protected static String COMMA =",";
	

	
	public static void main(String[] args) {
		
		String str="MARIN MIGUEL ANGEL CARMONA";
		
		patternTest(str);
		
		
		
				
	}
	
	
private static String insertSpaceAfterComma(int i,String str) {
		
		String str1=str.substring(0, i+1)+" "+str.substring(i+1, str.length());
		return str1;
		
	}
	
	private static void patternTest(String str) {
		
		Pattern pattern = Pattern.compile(NAME+SPACE+NAME+SPACE+NAME+SPACE+NAME);
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
		
		private static String capitalizeWord(String str) {
			
			
			
			
		    str = str.toLowerCase();
			return str.substring(0, 1).toUpperCase() + str.substring(1);

			
		}
		

}
