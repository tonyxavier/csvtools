package my.tools.csv.nameformatter;

import java.util.regex.Matcher;

import my.tools.csv.nameformatter.patterns.FirstLastInitialPattern;
import my.tools.csv.nameformatter.patterns.FirstLastPattern;
import my.tools.csv.nameformatter.patterns.FirstMiddleLastPattern;
import my.tools.csv.nameformatter.patterns.LastCommaFirstInitialPattern;
import my.tools.csv.nameformatter.patterns.LastCommaFirstPattern;
import my.tools.csv.nameformatter.patterns.NamePattern;

public class NamePatternFactory {
	
	public static NamePattern getNamePattern(String name) {
		
		
		Matcher m =null;
		m = FirstLastPattern.pattern.matcher(name);
		if(m.matches()) {
			return new FirstLastPattern(name);
		}
		
		m = LastCommaFirstPattern.pattern.matcher(name);
		if(m.matches()) {
			return new LastCommaFirstPattern(name);
		}
		
		m = FirstMiddleLastPattern.pattern.matcher(name);
		if(m.matches()) {
			return new FirstMiddleLastPattern(name);
		}
		
		m = FirstLastInitialPattern.pattern.matcher(name);
		if(m.matches()) {
			return new FirstLastInitialPattern(name);
		}
		
		m = LastCommaFirstInitialPattern.pattern.matcher(name);
		if(m.matches()) {
			return new LastCommaFirstInitialPattern(name);
		}
		
	return null;	
	}

}
