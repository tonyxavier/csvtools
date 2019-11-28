package my.tools.csv.nameformatter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class NameProcessor implements Runnable{

	private NameParser parser;
	private StatusListener listener;
	Instant startTime;	
	
	public void run() {		
		
		
		try {
			
			startTime = Instant.now();	
		
		CSVReader reader = new CSVReader(new FileReader("/Users/toxavier/Documents/Projects/UW/alldata.csv"));
		String[] record = null;
		
		String[] target = {"Original Text","First Name","Middle Name","Last Name"};
		
		List<String[]> output = new ArrayList<String[]>();
		List<String[]> skippedRecs = new ArrayList<String[]>();
		List<String[]> reprocessedRecs = new ArrayList<String[]>();
		reprocessedRecs.add(target);
		output.add(target);
		
		
		FileWriter writer = new FileWriter("/Users/toxavier/Documents/Projects/UW/formatted_names.csv");
		FileWriter skipwriter = new FileWriter("/Users/toxavier/Documents/Projects/UW/skipped_names.csv");
		FileWriter reprocesswriter = new FileWriter("/Users/toxavier/Documents/Projects/UW/reprocessed_names.csv");
		
		
		CSVWriter csvWriter = new CSVWriter(writer);
		CSVWriter skipcsvWriter = new CSVWriter(skipwriter);
		CSVWriter reprocesscsvWriter = new CSVWriter(reprocesswriter);
		

			
		ThirdPartyNameParser thirdPartyParser = new ThirdPartyNameParser();
		CustomNameParser customParser = new CustomNameParser();
		
		int total=0,skipped=0,sucess=0,reprocessed=0;
		reader.readNext();
		//formatter.parser=thirdPartyParser;
		
		parser=customParser;
		boolean isReprocessed=false;
		int begin=80000;
		int end=81000;
		while((record=reader.readNext())!=null) {
			
			
			total++;	
			
			if(total<begin)
				continue;
			
			
			
			parser=customParser;	
			isReprocessed=false;
			target = new String[4];
			String name = record[73];			
			
			String[] names=new String[4];
			
			if(notAName(name)) {
				skipped++;
				names[0]=name;
				output.add(names);   
				String[] skip = {name};
				//skippedRecs.add(skip);
				continue;				
			}
			
			name = preProcess(name);			
			Name formattedName = parser.parse(name);
			
			if(formattedName==null) { //Custom Parser cannot process the pattern
				parser=thirdPartyParser;	
				formattedName = parser.parse(name);	
				if(formattedName!=null) {
					reprocessedRecs.add(formattedName.toStringArray());
					 reprocessed++;
				}
			}		
			
			 if(formattedName == null) { 
					names[0]=name;
					skipped++;
					String[] skip = {name};
					skippedRecs.add(skip);
					continue;
			 }	
			 
			 
			
				
					//System.out.println("Reprocessed!!!:"+name);
		
					sucess++;					
					names = formattedName.toStringArray();
					
	
						
			output.add(names);  
			
			
			if((total%100)==0) {
				updateStatus(total,false);
				//timeElapsed();
			}
			
			
			
			if(total>end)
				break;
			
			
               
		}
		
		csvWriter.writeAll(output);
		csvWriter.close();
		
		skipcsvWriter.writeAll(skippedRecs);
		skipcsvWriter.close();
		
		reprocesscsvWriter.writeAll(reprocessedRecs);
		reprocesscsvWriter.close();
		reader.close();
		
		
		
		System.out.println("Total:"+total);
		System.out.println("Sucess:"+sucess);
		System.out.println("Skipped:"+skipped);
		System.out.println("Reprocessed:"+reprocessed);
		System.out.println("Sucess rate:"+(sucess*100.0)/(total-begin));
		updateStatus(total,true);
		}
		catch(FileNotFoundException fnfe)
		{
			fnfe.printStackTrace();		
			updateError(fnfe.getMessage());
			
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
			updateError(ioe.getMessage());
		}
		
		
		
	}
	
	
	private void updateStatus(int numRecs,boolean completed) {
		
		System.out.println("Number of Recs Processed:"+numRecs);
		
		
		if(listener==null)
			return;
		
		StatusEvent status = new StatusEvent();
		status.recordsProcessed=numRecs;
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
	
	
	public void addStatusListener(StatusListener listener) {
		
		this.listener=listener;
	}
	
	
	private static boolean notAName(String name) {
		
		
		name = name.toUpperCase();
		
		String[] excludedWords = {" LLC"," INC","ENTERPRISE","PROPERTY","MGMT","COMMUNI","GROUP",
				       "ESTAT","MANAGE","LIMIT","PARTNER","HABITAT","COUNCIL",
				       "REALTY","LTD","PRODUCTS","HOUSING","PROGRAM","APARTM","TOWN","COUNTY",
				       "TRUST","HOUSE","AUTHORIT","DEVELOP","SELLER","TIITF","CHURCH","DISTRIBUTOR","INDUSTR",
				       "DEPARTM","STATE"," COMPA","SOUTHERN","INVEST","CENTER","PROPERT","LLP",
				       "ASSET","SERVICE","NATIONAL","BANK","AMERICA","UNIVERS","CORP","ASSOC",
				       "SCHOOL","FUND","CAPITAL","L.L.C","L.C","HOUSING","LENDING","FUNDING",
				       "MORTGAGE","JACKSONVILLE","COOPERAT","PROFESSION","P.A","HOLDINGS","CREDIT",
				       "COUNTRY","OWNER","MOBILE","LEASING","EXPORT","L L P","VENTURE","HOMES",
				       "AVENUE","STORE","SOCIETY"," FARMS","MANUFACTURE","CONSTRUCTION","L L C",
				        " AT "," OF "," FOR ","FELLOWSHIP","ASSEMBL"," LP","DISTRICT","BAPTIST",
				        "SUBSIDIARY","SOUTHEAST","UNITED","MISSIONARY","SPRINGS"," EST"};
		
		
		for(String exc:excludedWords) {
			
			if(name.contains(exc)) {
				
				//System.out.println("SKIPPED!!!!:"+name +" contains "+exc);
				return true;
			}
		}
		
		
		Pattern pattern = Pattern.compile(".*[0-9]{2,}.*");
		Matcher m = pattern.matcher(name);
		if(m.matches()) {
			//System.out.println("NUMBER IN Name!!!!!!!!!:"+name);
			return true;
		
		}
		
		if(name.endsWith(" CO"))
			return true;
		
		
		if(name.contains(" CO "))
			return true;
		
		if(name.endsWith(" L P"))
			return true;
		
		if(name.endsWith(" LL"))
			return true;
		
		return false;
	}
	
	
		
	private String preProcess(String str) {
		
		
		str = str.toUpperCase().trim();
		
		if(str.contains(" JR"))
			str=str.replace(" JR", "");
		
		if(str.contains(" SR"))
			str=str.replace(" SR", "");
		
		if(str.contains(" ET AL"))
			str=str.replace(" ET AL", "");
		
		if(str.contains(" ETAL"))
			str=str.replace(" ETAL", "");
		
		if(str.contains("B/E"))
			str=str.replace("B/E", "");
		
		if(str.contains("R/S"))
			str=str.replace("R/S", "");
		
		if(str.endsWith("II")||str.endsWith("III")||str.endsWith("IV")||str.endsWith("VI"))
			str=str.substring(0,str.length()-2).trim();
		
		
		str = str.trim().replaceAll(" +", " ");
					
		return str;
	}
	
	
	private  long timeElapsed() {
		
		 Instant currentTime = Instant.now();
		 long timeElapsed = Duration.between(startTime, currentTime).toMillis();
		 
		 //System.out.println("Minutes so far..."+timeElapsed/(60000));
		 return timeElapsed/(60000);
		 
		
	}
	
	
}
