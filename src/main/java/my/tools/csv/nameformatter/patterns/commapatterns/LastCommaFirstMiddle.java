package my.tools.csv.nameformatter.patterns.commapatterns;

import java.util.regex.Pattern;

import my.tools.csv.nameformatter.Name;
import my.tools.csv.nameformatter.patterns.NamePattern;

public class LastCommaFirstMiddle extends NamePattern{
	
	public static final Pattern pattern = Pattern.compile(NAME+"[,.]\\s?"+NAME+SPACE+NAME);


	public LastCommaFirstMiddle(String text) {
    	originalText=text;
    	normalizeInput();
    	//System.out.println("Orig:"+originalText);
    }
	
	
	
	public static void main(String args[]) {
		
		LastCommaFirstMiddle pat = new LastCommaFirstMiddle("U'O,L");
		System.out.println(pat.transform());
		
	}
	
	

	public Name transform() {

		Name outputName = new Name();
		try {
		String[] names = originalText.split(" ");
		String first = names[1];
		String last = names[0];
		last=last.replaceAll(",", "");
		last=last.trim();
		
		outputName.originalText = originalText;
		outputName.firstName=first;
		outputName.lastName=last;
		outputName.middleName=names[2];
		outputName.transformed=true;
		outputName.capitalize();
	}	
	catch(Exception e) {
		
		System.out.println("Failure processing LastCommaFIrstMiddle:"+"Original:"+originalText+"  "+outputName.toString());
		e.printStackTrace();
	
		
	}
	
		
		
		return outputName;
	}

}
