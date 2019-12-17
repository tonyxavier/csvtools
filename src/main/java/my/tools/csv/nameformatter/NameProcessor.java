package my.tools.csv.nameformatter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;

import my.tools.csv.nameformatter.patterns.spacepatterns.FirstLastPattern;


public class NameProcessor implements Runnable{

	
	private boolean FASTMODE=false;; //To skip trying with Names API
	private NameParser parser;
	private StatusListener listener;
	private Instant startTime;	
	private File dataFile,saveFile;
	
	private List<String[]> outputRecords,orgRecords,reprocessedRecords,skippedRecords,allProcessedRecords;
	
	private int nameFieldIndex;
	
	private int totalCnt=0,orgCnt=0,namesCnt=0,reprocessedCnt=0,skippedCnt; 
	
	private ThirdPartyNameParser thirdPartyParser;
	private CustomNameParser customParser;
	
	private String originalName,fieldName;
	
	
	
	
	public NameProcessor(File dataFile,File saveFile,String fieldName,boolean fastmode) {
		this.dataFile=dataFile;
		this.saveFile=saveFile;
		this.FASTMODE = fastmode;
		if(fieldName==null || fieldName.trim().length()<1)
			this.fieldName="OWN_NAME";
		else			
		    this.fieldName=fieldName.trim();
		
	}	
	
	public void run() {				
		
	 CSVReader reader=null;
	  try {			
	    startTime = Instant.now();	
		
	    CSVParser csvParser = new CSVParser(CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER, '\0', CSVParser.DEFAULT_STRICT_QUOTES);

	   // System.setProperty("file.encoding", "UTF-8-BOM");
		reader = new CSVReader(new FileReader(dataFile),0,csvParser);
		
		String[] record = reader.readNext();
		nameFieldIndex = Utils.getCsvFieldIndex(record, fieldName);		
		if(nameFieldIndex==-1) {//The file does not have "OWN_NAME" field
			updateError("Uploaded file does not have "+fieldName+" field");			
			return;		
			
		 }
		
		initialize(record);			
				
		int begin=0;		
	    //int end=1000;	
	    int end=Integer.MAX_VALUE;
	    int currentRec=0,writeCount=0;
	    NameWriter nameWriter =new  NameWriter(saveFile);
	    
		while((record=reader.readNext())!=null) {			
			
			currentRec++;
			writeCount++;
			
			if(currentRec<=begin)
				continue;	
			
						
			totalCnt++;					
			String name = record[nameFieldIndex];	
			originalName=name;
			
			if(name!=null)
				name=name.trim();			
			
			
			if(name==null || name.length()<1 || skipRecord(name)) {
				processSkippedRecord(record,name);
				continue;
			}	
			
			if(name!=null)
				name=name.trim();
				
			if(Utils.isOrganization(name)) {
				processOrganization(record,name);				
				continue;				
			}
			
			name = Utils.preProcess(name);	
			
			
					
			parser = customParser;
			Name formattedName = parser.parse(name);
			
			if(formattedName==null && MultipleNamesProcessor.isMultipleName(name)) 
				formattedName = MultipleNamesProcessor.process(name);					
			
			
			if(formattedName==null && !FASTMODE) { //Custom Parser couldn't process the pattern
				parser=thirdPartyParser;	
				formattedName = parser.parse(name);					
				if(formattedName!=null) //Name service was able to format
					processReprocessedRecord(formattedName);			
			}		
			
			
			 if(formattedName == null) { //Both parsers could not format
				    if(Utils.isOrgPostProcess(name))				    
				    	processOrganization(record,name);				    			    
				    else //skip the record - not sure if its name or org				    
					  processSkippedRecord(record,originalName);					  
			        	
				    continue;		
			    }			 
			 
			processFormattedName(record,formattedName);	
			
			updateStatus(false);						
			
			if(totalCnt>end)
				break;	
			
			
			if(writeCount>=100000) {
				
				nameWriter.writeOutputFile(outputRecords);
				nameWriter.writeOrgFile(orgRecords);
				nameWriter.writeReprocessedFile(reprocessedRecords);
				nameWriter.writeSkippedFile(skippedRecords);
				nameWriter.writeAllRecordsFile(allProcessedRecords);	
				
				
				outputRecords.clear();
				orgRecords.clear();
				reprocessedRecords.clear();
				skippedRecords.clear();
				allProcessedRecords.clear();
				
				
				writeCount=0;
			}
			
               
		}
		
		reader.close();
		
		nameWriter.writeOutputFile(outputRecords);
		nameWriter.writeOrgFile(orgRecords);
		nameWriter.writeReprocessedFile(reprocessedRecords);
		nameWriter.writeSkippedFile(skippedRecords);
		nameWriter.writeAllRecordsFile(allProcessedRecords);	
				
		nameWriter.closeAllWriters();
		
		reportStatistics();
		GlobalReporting.report();
		
		}catch(FileNotFoundException fnfe)
		{
			fnfe.printStackTrace();		
			updateError(fnfe.getMessage());
			
		}catch(IOException ioe) {
			ioe.printStackTrace();
			updateError(ioe.getMessage());
		}catch(Exception e) {	  
	       e.printStackTrace();
	       updateError("Unexpected Error for record:"+totalCnt +" Name:"+originalName);		
	    }finally{
			
			try {
			reader.close();
			}catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
		}		
		
	}
	
	
	private void reportStatistics() {
		
		System.out.println("Total:"+totalCnt);		
		System.out.println("Organizations:"+orgCnt);
		System.out.println("Reprocessed:"+reprocessedCnt);
		System.out.println("Skipped:"+skippedCnt);
		System.out.println("Sucess rate:"+((namesCnt+orgCnt)*100.0)/(totalCnt));
		updateStatus(true);

		
	}
	
	
	
	private void initialize(String[] record) {
		
	String[] target = {"Original Text","First Name","Middle Name","Last Name","First Name 2","Middle Name 2","Last Name 2"};
	String[] allrecordsHeader= {"Original Text","First Name","Middle Name","Last Name","First Name 2","Middle Name 2","Last Name 2","Organization"};
		
		outputRecords = new ArrayList<String[]>();
		orgRecords = new ArrayList<String[]>();
		reprocessedRecords = new ArrayList<String[]>();
		skippedRecords = new ArrayList<String[]>();
		allProcessedRecords = new ArrayList<String[]>();
		reprocessedRecords.add(target);	
		allProcessedRecords.add(allrecordsHeader);
			
		
		String[] newFields = {fieldName+"_FIRST_NAME_1",fieldName+"_MIDDLE_NAME_1",fieldName+"_LAST_NAME_1",fieldName+"_FIRST_NAME_2",fieldName+"_MIDDLE_NAME_2",fieldName+"_LAST_NAME_2",fieldName+"_ORGANIZATION"};
		String[] finalHeader = Utils.insertInArray(record, newFields, nameFieldIndex+1);
		
		outputRecords.add(finalHeader);		
		
		thirdPartyParser = new ThirdPartyNameParser();
		customParser = new CustomNameParser();		
		parser=customParser;
		
		
	}
	
	private void updateStatus(boolean completed) {
		
		//System.out.println("Number of Recs Processed:"+totalCnt);		
		
		if(listener==null)
			return;
		
		StatusEvent status = new StatusEvent();
		status.recordsProcessed=totalCnt;
		status.orgCnt=orgCnt;
		status.skippedCnt=skippedCnt;
		status.namesCnt=namesCnt;
		status.timeElapsed=timeElapsed();
		status.completed=completed;
		listener.statusChanged(status);
	}
	
	
	
	private void updateError(String message) {
		
		System.out.println("Error at record:"+totalCnt);
		
		if(listener==null)
			return;
		
		StatusEvent status = new StatusEvent();		
		status.timeElapsed=timeElapsed();
		status.failed=true;
		status.errorMessage=message;
		listener.statusChanged(status);
		
		
	}
	
	private void processOrganization(String[] record, String name) {
		orgCnt++;
		String[] names=new String[7];
		names[6]=originalName;		
		String[] out=Utils.insertInArray(record,names,nameFieldIndex+1);		
		outputRecords.add(out);   
		String[] org = {originalName};
		orgRecords.add(org);
		
		String[] allFields = new String[8];
		allFields[0]=originalName;
		allFields[7]=originalName;
		allProcessedRecords.add(allFields);
	}
		
	
	private void processSkippedRecord(String[] record, String name) {
		//String[] names=new String[4];
		//names[0]=originalName;
		String[] empty = {null,null,null,null,null,null,null}; //send all empty values to csv
		String[] out=Utils.insertInArray(record,empty,nameFieldIndex+1);
		skippedCnt++;
		String[] skip = {originalName};
		skippedRecords.add(skip);
		outputRecords.add(out);
		
		String[] allFields = new String[8];
		allFields[0]=originalName;
		allProcessedRecords.add(allFields);

		
	}
	private void processFormattedName(String[] record, Name formattedName) {
		
		String[] names = formattedName.toStringArray();
		String[] out = Utils.insertInArray(record, names, nameFieldIndex+1);						
        outputRecords.add(out); 
        namesCnt++;
        
        String[] allFields = formattedName.toStringAllFields();
        allFields[0]=originalName;
		allProcessedRecords.add(allFields);
		
	}
	private void processReprocessedRecord(Name formattedName) {
		
		 reprocessedRecords.add(formattedName.toStringArrayFull());
		 reprocessedCnt++;	
		
	}
	
	
	
	
		
	public void addStatusListener(StatusListener listener) {
		
		this.listener=listener;
	}

	
	private  long timeElapsed() {
		
		 Instant currentTime = Instant.now();
		 long timeElapsed = Duration.between(startTime, currentTime).toMillis();
		 
		 //System.out.println("Minutes so far..."+timeElapsed/(60000));
		 return timeElapsed/(60000);
		 
		
	}
	
	
	private boolean skipRecord(String name) {
		
		name = name.toUpperCase();
		
		String[] skipWords = {"TAXPAYER"};		        
				       
		
		
		for(String exc:skipWords) {
			
			if(name.contains(exc)) 					
				return true;
			
		}
		
		
		Pattern pattern = Pattern.compile("[a-zA-Z]{2,}"); //skip single words
		
		Matcher m =null;
		m = pattern.matcher(name);
		if(m.matches()) {
			return true;
		}
		
		
		return false;
	}
	
	
}
