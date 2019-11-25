package my.tools.csv.nameformatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	
	public static void main(String[] args) {
		
		
		Matcher m =null;
		Pattern firstlast = Pattern.compile("[a-zA-Z][a-zA-Z]+\\s[a-zA-Z][a-zA-Z]+");
		Pattern lastcommafirst = Pattern.compile("[a-zA-Z][a-zA-Z]+[,]\\s?[a-zA-Z][a-zA-Z]+");
		Pattern firstMiddleLast = Pattern.compile("[a-zA-Z][a-zA-Z]+\\s[a-zA-Z][a-zA-Z]+\\s[a-zA-Z][a-zA-Z]+");
		Pattern lastCommaFirstInit = Pattern.compile("[a-zA-Z][a-zA-Z]+[,]\\s?[a-zA-Z][a-zA-Z]+\\s[a-zA-Z]{1}");
		Pattern firstlastInitial = Pattern.compile("[a-zA-Z][a-zA-Z]+\\s?[a-zA-Z][a-zA-Z]+\\s[a-zA-Z]{1}");
		
		
		 m = firstlast.matcher("Lo M");
		boolean isFirstLast = m.matches();
		
		m=lastcommafirst.matcher("Xavier,Tony");
		boolean isLastCommaFirst = m.matches();
		
		m=firstMiddleLast.matcher("Tony K Xavier");
		boolean isFirstMiddleLast = m.matches();
		
		m=lastCommaFirstInit.matcher("Xavier, Tony K");
		boolean isLastCommaFirstInit=m.matches();
		
		
		m=firstlastInitial.matcher("Tony Xavier Ko");
		boolean isFirstlastInitial=m.matches();
		
		
		
	    if(isFirstLast)
	    	System.out.println("First Last");
	    else
	    	System.out.println("Not First Last");
	    
	    
	    
	    if(isLastCommaFirst)
	    	System.out.println("Last, First");
	    else
	    	System.out.println("Not Last, First");
	    
	    
	    if(isFirstMiddleLast)
	    	System.out.println("First Middle Last");
	    else
	    	System.out.println("Not First Middle Last");
	    
	    if(isLastCommaFirstInit)
	    	System.out.println("Last, First Init");
	    else
	    	System.out.println("Not Last, First Init");
	    
	    if(isFirstlastInitial)
	    	System.out.println("First Last Init");
	    else
	    	System.out.println("Not First Last Init");
	    
		
	}

}
