package my.tools.csv.nameformatter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVWriter;

import my.tools.csv.nameformatter.Utils;

public class NameWriter {
	
	private String outputDirectory;
	private File outputFile;
	
	
	public NameWriter(File outputFile) {
			
		this.outputFile=outputFile;	
		outputDirectory=Utils.getDirectoryName(outputFile);	
		
	}
	
	public boolean writeOutputFile(List<String[]> outputRecords) {		
		
		try {
			FileWriter outputFilewriter = new FileWriter(outputFile);
		    CSVWriter outputCsvWriter = new CSVWriter(outputFilewriter);
		    outputCsvWriter.writeAll(outputRecords);
		    outputCsvWriter.close();
		    return true;
		    }catch(IOException ioe)
		    {
			ioe.printStackTrace();
			return false;			
		    }
		
	}
	
    public boolean writeOrgFile(List<String[]> orgRecords) {		
		
	
	    try {
		   FileWriter orgFilewriter = new FileWriter(outputDirectory+"//organizations.csv");
		   CSVWriter orgCsvWriter = new CSVWriter(orgFilewriter);
		   orgCsvWriter.writeAll(orgRecords);
		   orgCsvWriter.close();
          }catch(IOException ioe){
	        ioe.printStackTrace();
	        return false;
	
            }	
		return true;
		
	   }

    
    
    public boolean writeSkippedFile(List<String[]> skippedRecords) {	
	
	   try {
		  FileWriter skippedFilewriter = new FileWriter(outputDirectory+"//skipped.csv");
		  CSVWriter skippedCsvWriter = new CSVWriter(skippedFilewriter);
		  skippedCsvWriter.writeAll(skippedRecords);
		  skippedCsvWriter.close();		
	
         }catch(IOException ioe){
	       ioe.printStackTrace();
	       return false;
	
          }	
	      return true;
         }
	

   public boolean writeReprocessedFile(List<String[]> reprocessedRecords) {	
	
	   try {
	    	FileWriter reprocessFilewriter = new FileWriter(outputDirectory+"//reprocessed.csv");
		   CSVWriter reprocessCsvWriter = new CSVWriter(reprocessFilewriter);
		   reprocessCsvWriter.writeAll(reprocessedRecords);
		   reprocessCsvWriter.close();		
	
         }catch(IOException ioe){
	     ioe.printStackTrace();
	      return false;	
          }
	
	  return true;
   }
	
	
}
