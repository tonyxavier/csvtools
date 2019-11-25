package my.tools.csv.nameformatter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import my.tools.csv.nameformatter.patterns.NamePattern;

public class NameFormatter {
	
		
	public static void main(String args[]) {	
		
		try {
		CSVReader reader = new CSVReader(new FileReader("/Users/toxavier/Documents/Projects/UW/data.csv"));
		String[] record = null;
		
		String[] target = {"Original Text","First Name","Middle Name","Last Name"};
		
		List<String[]> output = new ArrayList<String[]>();
		output.add(target);
		
		FileWriter writer = new FileWriter("/Users/toxavier/Documents/Projects/UW/formatted_names.csv");
		CSVWriter csvWriter = new CSVWriter(writer);
		
		while((record=reader.readNext())!=null) {
			
			target = new String[4];
			String name = record[73];			
			System.out.println();
			System.out.print(name+"            ");			
			NamePattern namePattern = NamePatternFactory.getNamePattern(name);
			if(namePattern==null)
				continue;
			Name formattedName = namePattern.transform();
			String[] names = formattedName.toStringArray();
			output.add(names);              
                
		}
		
		csvWriter.writeAll(output);
		csvWriter.close();
		reader.close();
		
		}
		catch(FileNotFoundException fnfe)
		{
			fnfe.printStackTrace();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
	}
	
}
