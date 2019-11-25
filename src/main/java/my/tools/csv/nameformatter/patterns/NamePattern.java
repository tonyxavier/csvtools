package my.tools.csv.nameformatter.patterns;

import java.util.regex.Pattern;

import my.tools.csv.nameformatter.Name;

public abstract class NamePattern {
	
	protected String originalText;
	 
	
	public abstract Name transform();

}
