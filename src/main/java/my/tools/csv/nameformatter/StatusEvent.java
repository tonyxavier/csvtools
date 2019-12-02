package my.tools.csv.nameformatter;

public class StatusEvent {
	
	int recordsProcessed;
	int skippedCnt,orgCnt,namesCnt;
	long timeElapsed;
	
	boolean completed=false;
	boolean failed=false;
	
	String errorMessage;

}
