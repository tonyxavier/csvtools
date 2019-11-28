package my.tools.csv.nameformatter;

public class StatusEvent {
	
	int recordsProcessed;
	int skipped,sucess;
	long timeElapsed;
	
	boolean completed=false;
	boolean failed=false;
	
	String errorMessage;

}
