package my.tools.csv.nameformatter;

import my.tools.csv.nameformatter.patterns.NamePattern;

public class MultipleNamesProcessor {
	
	

	
	
	
public static boolean isMultipleName(String text) {
	
	if(text.contains("&")||text.contains(" AND ")||text.contains(";"))	
	  return true;
	else
		return false;
}




public static Name process(String text) {
	
	String[] names=null;
	
	if(text.contains("&"))
		names = text.split("&");
	else if(text.contains(" AND "))
		names = text.split(" AND ");
	else if(text.contains(";"))
		names = text.split(";");
	else
		return null;
	
	
	if(names.length<2)
		return null;
	
	String firstPart = names[0].trim();
	String secondPart = names[1].trim();
	
	if(firstPart.length()<1 || secondPart.length()<1 )
		return null;
	
	NamePattern namePattern = NamePatternFactory.getNamePattern(firstPart);
	
	if(namePattern==null)
	   return null;
	
		
	Name formattedName = namePattern.transform();
	
	
	if(isSingleName(secondPart)) { //if second part is a single name, then last name from first part must be used
	
		formattedName.firstName_2 = secondPart;
		formattedName.lastName_2 = formattedName.lastName;	
		formattedName.capitalizeSecondPart();
		return formattedName;
		
	}
	
	if(noLastNameInSecondPart(secondPart,firstPart)) { //more than one name in second part, but there's no last name
	
	   String[] parts =  secondPart.split(" ");
	   formattedName.firstName_2 = parts[0];
	   formattedName.middleName_2 = parts[1];
	   formattedName.lastName_2 = formattedName.lastName;
	   formattedName.capitalizeSecondPart();
	   return formattedName;
		
	}
	
	
	
	//The second part is an unrelated name
	
     namePattern = NamePatternFactory.getNamePattern(secondPart);
	
	 if(namePattern==null)		 
		 return formattedName;	
		
	 Name formattedNameSecond = namePattern.transform();
	
	 formattedName.firstName_2 = formattedNameSecond.firstName;
	 formattedName.middleName_2 = formattedNameSecond.middleName;
	 formattedName.lastName_2 = formattedNameSecond.lastName;	

	
	return formattedName;
	
	
	

}



private static boolean isSingleName(String name) {
	
	if(!(name.contains(" ")||name.contains(",")))
		return true;
	else
		return false;
	
	
}

private static boolean noLastNameInSecondPart(String second,String first) {
	
	if(first.contains(",") && second.contains(" ") && !second.contains(","))
		return true;
	else
		return false;
	
	
}
	

}
