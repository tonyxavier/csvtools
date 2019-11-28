package my.tools.csv.nameformatter;

import my.tools.csv.nameformatter.patterns.NamePattern;

public class CustomNameParser implements NameParser{
	
	
	public Name parse(String text) {
		
		NamePattern namePattern = NamePatternFactory.getNamePattern(text);
		if(namePattern==null)
		   return null;
		Name formattedName = namePattern.transform();
		
		return formattedName;
	}

}
