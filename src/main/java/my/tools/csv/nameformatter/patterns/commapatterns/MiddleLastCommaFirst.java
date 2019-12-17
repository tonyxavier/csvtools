package my.tools.csv.nameformatter.patterns.commapatterns;

import java.util.regex.Pattern;

import my.tools.csv.nameformatter.Name;
import my.tools.csv.nameformatter.patterns.NamePattern;

public class MiddleLastCommaFirst extends NamePattern{
	
//	public static final Pattern pattern = Pattern.compile("[a-zA-Z]+['-]?[a-zA-Z]+\\s[a-zA-Z]+['-]?[a-zA-Z]+[,.]\\s?[a-zA-Z][a-zA-Z]+");
	public static final Pattern pattern = Pattern.compile(NAME+SPACE+NAME+"[,.]\\s?"+NAME);

	public MiddleLastCommaFirst(String text) {
    	originalText=text;
    	normalizeInput();
    }
	

	public Name transform() {

		Name outputName = new Name();
		outputName.originalText=originalText;
		try {
		
		if(originalText.matches(NAME+SPACE+NAME+"[,]"+NAME))	        
			       originalText=originalText.replace(",", ", ");	

	    if(originalText.matches(NAME+SPACE+NAME+"[.]"+NAME))	        
	       originalText=originalText.replace(".", ", "); 
		
		String[] names=originalText.split(" ");
    	String last=names[1];
    	last=last.replaceAll(",", "");    	
    	outputName.firstName=names[2];
    	outputName.middleName=names[0];
    	outputName.lastName=last;
    	outputName.transformed=true;
    	outputName.capitalize();
    	
	}
	catch(Exception e) {
		
		System.out.println("Failure processing LastCommaFirstMiddle:"+outputName.toString());
		e.printStackTrace();
	
		
	}
	
		return outputName;
	}
	
  private  String insertSpaceAfterComma(int i,String str) {
		
		String str1=str.substring(0, i+1)+" "+str.substring(i+1, str.length());
		return str1;
		
	}


}
