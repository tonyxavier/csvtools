package my.tools.csv.nameformatter.patterns;

import java.util.regex.Pattern;

import my.tools.csv.nameformatter.Name;

public class LastCommaFirstMiddle extends NamePattern{

	public static final Pattern pattern = Pattern.compile("[a-zA-Z]+['-]?[a-zA-Z]+[,.]\\s?[a-zA-Z]+\\s[a-zA-Z]+");


	public LastCommaFirstMiddle(String text) {
    	originalText=text;
    }
	

	public Name transform() {

		Name outputName = new Name();
		try {
		
		int i=originalText.indexOf(',');
		if(originalText.charAt(i+1)!=' ') {//if there's no space after comma
			originalText=insertSpaceAfterComma(i,originalText);
		}
		String[] names=originalText.split(" ");
    	String last=names[0];
    	last=last.replaceAll(",", "");    	
    	outputName.originalText=originalText;
    	outputName.firstName=names[1];
    	outputName.middleName=names[2];
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
