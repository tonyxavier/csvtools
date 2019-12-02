package my.tools.csv.nameformatter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


import com.opencsv.CSVReader;


public class NameProcessor implements Runnable{

	private NameParser parser;
	private StatusListener listener;
	private Instant startTime;	
	private File dataFile,saveFile;
	
	private List<String[]> outputRecords,orgRecords,reprocessedRecords,skippedRecords,allProcessedRecords;
	
	private int nameFieldIndex;
	
	private int totalCnt=0,orgCnt=0,namesCnt=0,reprocessedCnt=0,skippedCnt; 
	
	private ThirdPartyNameParser thirdPartyParser;
	private CustomNameParser customParser;
	
	
	public NameProcessor(File dataFile,File saveFile) {
		this.dataFile=dataFile;
		this.saveFile=saveFile;
		
	}	
	
	public void run() {				
		
	 CSVReader reader=null;
	  try {			
	    startTime = Instant.now();	
		
		reader = new CSVReader(new FileReader(dataFile));
		
		String[] record = reader.readNext();
		nameFieldIndex = Utils.getCsvFieldIndex(record, "OWN_NAME");		
		if(nameFieldIndex==-1) {//The file does not have "OWN_NAME" field
			updateError("Uploaded file does not have OWN_NAME field");			
			return;		
			
		 }
		
		initialize(record);			
				
		int begin=0;		
	    int end=140000;	
	   // int end=Integer.MAX_VALUE;
	    int currentRec=0;
		while((record=reader.readNext())!=null) {			
			
			currentRec++;
			if(currentRec<=begin)
				continue;	
			
						
			totalCnt++;					
			String name = record[nameFieldIndex];				
			
			if(Utils.isOrganization(name)) {
				processOrganization(record,name);				
				continue;				
			}
			
			name = Utils.preProcess(name);	
			parser = customParser;
			Name formattedName = parser.parse(name);
			
			if(formattedName==null) { //Custom Parser couldn't process the pattern
				parser=thirdPartyParser;	
				formattedName = parser.parse(name);					
				if(formattedName!=null) //Name service was able to format
					processReprocessedRecord(formattedName);			
			}			
			
			 if(formattedName == null) { //Both parsers could not format
				    if(Utils.isOrgPostProcess(name))				    
				    	processOrganization(record,name);				    			    
				    else //skip the record - not sure if its name or org				    
					  processSkippedRecord(record,name);					  
			        	
				    continue;		
			    }			 
			 
			processFormattedName(record,formattedName);				
			
			if((totalCnt%100)==0) 
				updateStatus(false);				
			
			if(totalCnt>end)
				break;			
               
		}
		
		reader.close();
		
		NameWriter nameWriter =new  NameWriter(saveFile);		
		nameWriter.writeOutputFile(outputRecords);
		nameWriter.writeOrgFile(orgRecords);
		nameWriter.writeReprocessedFile(reprocessedRecords);
		nameWriter.writeSkippedFile(skippedRecords);
		
		reportStatistics();
		
		}catch(FileNotFoundException fnfe)
		{
			fnfe.printStackTrace();		
			updateError(fnfe.getMessage());
			
		}catch(IOException ioe) {
			ioe.printStackTrace();
			updateError(ioe.getMessage());
		}finally {
			
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
		
	String[] target = {"Original Text","First Name","Middle Name","Last Name"};
		
		outputRecords = new ArrayList<String[]>();
		orgRecords = new ArrayList<String[]>();
		reprocessedRecords = new ArrayList<String[]>();
		skippedRecords = new ArrayList<String[]>();
		allProcessedRecords = new ArrayList<String[]>();
		reprocessedRecords.add(target);	
		allProcessedRecords.add(target);
			
		
		String[] newFields = {"FIRST_NAME","MIDDLE_NAME","LAST_NAME","ORGANIZATION"};
		String[] finalHeader = Utils.insertInArray(record, newFields, nameFieldIndex+1);
		
		outputRecords.add(finalHeader);		
		
		thirdPartyParser = new ThirdPartyNameParser();
		customParser = new CustomNameParser();		
		parser=customParser;
		
		
	}
	
	private void updateStatus(boolean completed) {
		
		System.out.println("Number of Recs Processed:"+totalCnt);		
		
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
		String[] names=new String[4];
		names[3]=name;
		allProcessedRecords.add(names);
		String[] out=Utils.insertInArray(record,names,nameFieldIndex+1);
		outputRecords.add(out);   
		String[] org = {name};
		orgRecords.add(org);
	}
		
	
	private void processSkippedRecord(String[] record, String name) {
		String[] names=new String[4];
		names[0]=name;
		String[] empty = {null,null,null,null}; //send all empty values to csv
		String[] out=Utils.insertInArray(record,empty,nameFieldIndex+1);
		skippedCnt++;
		String[] skip = {name};
		skippedRecords.add(skip);
		outputRecords.add(out);

		
	}
	private void processFormattedName(String[] record, Name formattedName) {
		
		String[] names = formattedName.toStringArray();
		String[] out = Utils.insertInArray(record, names, nameFieldIndex+1);						
        outputRecords.add(out); 
        namesCnt++;
		
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
	
	
	
	
}
