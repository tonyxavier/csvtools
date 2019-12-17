package my.tools.csv.nameformatter;

import java.util.regex.Matcher;

import my.tools.csv.nameformatter.patterns.LastCommaFirstMiddle;
import my.tools.csv.nameformatter.patterns.NamePattern;
import my.tools.csv.nameformatter.patterns.commapatterns.FirstCommaInitials;
import my.tools.csv.nameformatter.patterns.commapatterns.LastCommaFirst;
import my.tools.csv.nameformatter.patterns.commapatterns.LastCommaFirstInitialPattern;
import my.tools.csv.nameformatter.patterns.commapatterns.LastCommaFirstPattern;
import my.tools.csv.nameformatter.patterns.commapatterns.LastCommaFirstTwoMiddle;
import my.tools.csv.nameformatter.patterns.commapatterns.MiddleLastCommaFirst;
import my.tools.csv.nameformatter.patterns.commapatterns.TwoLastCommaFirstMiddle;
import my.tools.csv.nameformatter.patterns.spacepatterns.FirstAndInitials;
import my.tools.csv.nameformatter.patterns.spacepatterns.FirstAndTwoInitials;
import my.tools.csv.nameformatter.patterns.spacepatterns.FirstInitialLast;
import my.tools.csv.nameformatter.patterns.spacepatterns.FirstLastInitialPattern;
import my.tools.csv.nameformatter.patterns.spacepatterns.FirstLastPattern;
import my.tools.csv.nameformatter.patterns.spacepatterns.FirstMiddleLastPattern;
import my.tools.csv.nameformatter.patterns.spacepatterns.FourNamesPattern;
import my.tools.csv.nameformatter.patterns.spacepatterns.LastFirst;
import my.tools.csv.nameformatter.patterns.spacepatterns.LastFirstMiddle;


public class NamePatternFactory {
	
	public static NamePattern getNamePattern(String name) {
		
	
	
		/*SPACE Patterns*/
		
		
		
		Matcher m =null;
		m = LastFirstMiddle.pattern.matcher(name);
		if(m.matches()) {
			GlobalReporting.LAST_FIRST_MIDDLE_CNT++;
			return new LastFirstMiddle(name);
		}
		
		
		
	
		m = LastFirst.pattern.matcher(name);
		if(m.matches()) {
			GlobalReporting.LAST_FIRST_CNT++;
			return new LastFirst(name);
		}
		
		
		m = FourNamesPattern.pattern.matcher(name);
		if(m.matches()) {
			GlobalReporting.FOUR_NAMES_CNT++;
			return new FourNamesPattern(name);
		}
		
		
		
		
		/**Comma Patterns**/
		
		
		
		
		
		m = LastCommaFirst.pattern.matcher(name);
		if(m.matches()) {
			GlobalReporting.LAST_COMMA_FIRST_CNT++;
			return new LastCommaFirst(name);
		}
		
		
		m = LastCommaFirstMiddle.pattern.matcher(name);
		if(m.matches()) {
			GlobalReporting.LAST_COMMA_FIRST_MIDDLE_CNT++;
			return new LastCommaFirstMiddle(name);
		}
			
		
		m = TwoLastCommaFirstMiddle.pattern.matcher(name);
		if(m.matches()) {
			GlobalReporting.TWO_LAST_COMMA_FIRST_MIDDLE_CNT++;
			return new TwoLastCommaFirstMiddle(name);
		}
		
		
		m = LastCommaFirstTwoMiddle.pattern.matcher(name);
		if(m.matches()) {
			GlobalReporting.LAST_COMMA_FIRST_TWO_MIDDLE_CNT++;
			return new LastCommaFirstTwoMiddle(name);
		}
		
		m = MiddleLastCommaFirst.pattern.matcher(name);
		if(m.matches()) {
			GlobalReporting.MIDDLE_LAST_COMMA_FIRST_CNT++;
			return new MiddleLastCommaFirst(name);
		}
		
				
		
	/*
		m = FirstLastPattern.pattern.matcher(name);
		if(m.matches()) {
			GlobalReporting.FIRST_LAST_CNT++;
			return new FirstLastPattern(name);
		}
		
		m = LastCommaFirstPattern.pattern.matcher(name);
		if(m.matches()) {
			GlobalReporting.LAST_COMMA_FIRST_CNT++;
			return new LastCommaFirstPattern(name);
		}
		
		m = FirstMiddleLastPattern.pattern.matcher(name);
		if(m.matches()) {
			GlobalReporting.FIRST_MIDDLE_LAST_CNT++;
			return new FirstMiddleLastPattern(name);
		}
		
		m = FirstLastInitialPattern.pattern.matcher(name);
		if(m.matches()) {
			GlobalReporting.FIRST_LAST_INITIAL_CNT++;
			return new FirstLastInitialPattern(name);
		}
		
		m = LastCommaFirstInitialPattern.pattern.matcher(name);
		if(m.matches()) {
			GlobalReporting.LAST_COMMA_FIRST_INITIAL_CNT++;
			return new LastCommaFirstInitialPattern(name);
		}
		
		m = LastCommaFirstMiddle.pattern.matcher(name);
		if(m.matches()) {
			GlobalReporting.LAST_COMMA_FIRST_MIDDLE_CNT++;
			return new LastCommaFirstMiddle(name);
		}
		
						
		m = FirstAndInitials.pattern.matcher(name);
		if(m.matches()) {
			GlobalReporting.FIRST_AND_INITIALS_CNT++;
			return new FirstAndInitials(name);
		}
		
		m = FirstAndTwoInitials.pattern.matcher(name);
		if(m.matches()) {
			GlobalReporting.FIRST_AND_TWO_INITIALS_CNT++;
			return new FirstAndTwoInitials(name);
		}
		
		m = FirstInitialLast.pattern.matcher(name);
		if(m.matches()) {
			GlobalReporting.FIRST_INITIAL_LAST_CNT++;
			return new FirstInitialLast(name);
		}
		
				
		
			m = FirstCommaInitials.pattern.matcher(name);
		if(m.matches()) {
			GlobalReporting.FIRST_COMMA_INITIALS_CNT++;
			return new FirstCommaInitials(name);
		}
		
		
		
		*/
		
		
		
		
		
	
		
		
		
		
		
		
	return null;	
	}

}
