package my.tools.csv.nameformatter.patterns;


import my.tools.csv.nameformatter.Name;

public abstract class NamePattern {
	
	protected static String NAME = "([a-zA-Z]+[-']?[a-zA-Z]+|[a-zA-Z])";
	protected static String SPACE = "\\s";
	protected static String COMMA =",";
	
	protected String originalText;
	 
	
	public abstract Name transform();
	
	
    protected void normalizeInput() {
		
		if(originalText.contains(",") && !originalText.contains(", "))
			originalText = originalText.replaceAll(",", ", ");
		
		if(originalText.contains(".") && !originalText.contains(". "))
			originalText = originalText.replaceAll(".", ". ");
		
		
	}

}
