package my.tools.csv.nameformatter.patterns.commapatterns;

import java.util.regex.Pattern;

import my.tools.csv.nameformatter.Name;
import my.tools.csv.nameformatter.patterns.NamePattern;

public class LastCommaFirst extends NamePattern{
	
	public static final Pattern pattern = Pattern.compile(NAME+"[,.]\\s?"+NAME);


	public LastCommaFirst(String text) {
    	originalText=text;
    	normalizeInput();
    	//System.out.println("Orig:"+originalText);
    }
	
	
	
	public static void main(String args[]) {
		
		LastCommaFirst pat = new LastCommaFirst("U-O, L'K");
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
		outputName.transformed=true;
		outputName.capitalize();
	}	
	catch(Exception e) {
		
		System.out.println("Failure processing LastCommaFirst:"+"Original:"+originalText+"  "+outputName.toString());
		e.printStackTrace();
	
		
	}
	
		
		
		return outputName;
	}


}
