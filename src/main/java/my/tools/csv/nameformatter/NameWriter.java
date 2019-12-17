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
	private CSVWriter outputCsvWriter,orgCsvWriter,skippedCsvWriter,reprocessCsvWriter,allRecordsCsvwriter;
	private FileWriter outputFilewriter,orgFilewriter,skippedFilewriter,reprocessFilewriter,allRecordsFilewriter;
	public NameWriter(File outputFile) {
			
		this.outputFile=outputFile;	
		outputDirectory=Utils.getDirectoryName(outputFile);	
		
		try {
		outputFilewriter = new FileWriter(outputFile);
	    outputCsvWriter = new CSVWriter(outputFilewriter);
	    
	    
	    orgFilewriter = new FileWriter(outputDirectory+"//organizations.csv");
		orgCsvWriter = new CSVWriter(orgFilewriter);
		
		skippedFilewriter = new FileWriter(outputDirectory+"//skipped.csv");
		skippedCsvWriter = new CSVWriter(skippedFilewriter);
		
		 reprocessFilewriter = new FileWriter(outputDirectory+"//reprocessed.csv");
		  reprocessCsvWriter = new CSVWriter(reprocessFilewriter);
		  
		  allRecordsFilewriter = new FileWriter(outputDirectory+"//allrecords.csv");
		   allRecordsCsvwriter = new CSVWriter(allRecordsFilewriter);
	    
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
	}
	
	public void writeOutputFile(List<String[]> outputRecords) {		
		
	
			
		    outputCsvWriter.writeAll(outputRecords);
		    
		  
		    
		
	}
	
    public void writeOrgFile(List<String[]> orgRecords) {		
		
	

		    
		   orgCsvWriter.writeAll(orgRecords);
		  
          
	   }

    
    
    public void writeSkippedFile(List<String[]> skippedRecords) {	
	
	  
		  
		  skippedCsvWriter.writeAll(skippedRecords);
		 
	
         }
	

   public void writeReprocessedFile(List<String[]> reprocessedRecords) {	
	
	  
	    	
		   reprocessCsvWriter.writeAll(reprocessedRecords);		   	
	
        
	 
   }
   
   public void writeAllRecordsFile(List<String[]> allRecords) {	
		
	   
	    	
		   allRecordsCsvwriter.writeAll(allRecords);	
        
   }

   
   public void closeAllWriters() {
	   
		
	   try {
	   
	   outputCsvWriter.close();   
	   allRecordsCsvwriter.close();	 
	   reprocessCsvWriter.close();	
	   skippedCsvWriter.close();		
	   orgCsvWriter.close();
	   }catch(IOException ioe){
		   ioe.printStackTrace();
	   
       }
   }
	
	
}
