package my.tools.csv.nameformatter;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;





public class Namer extends JFrame implements StatusListener{
	

	 private String[] fileFilter={"xlsx","xls","xlsm","csv","CSV"};   
	 private File dataFile=null,saveFile=null;
	 private Namer mainFrame = this;


	private static final long serialVersionUID = 4890832876837628615L;
	private static final int SCREEN_WIDTH=Toolkit.getDefaultToolkit ().getScreenSize().width;
	private static final int SCREEN_HEIGHT=Toolkit.getDefaultToolkit ().getScreenSize().height;
	
	private static final int BUTTON_WIDTH=(SCREEN_WIDTH*12)/100;
	private static final int BUTTON_HEIGHT=(int)(SCREEN_HEIGHT*2.7f)/100;
	
	private static final int LABEL_WIDTH_SMALL=(SCREEN_WIDTH*10)/100;
	private static final int LABEL_WIDTH_MEDIUM=(SCREEN_WIDTH*15)/100;
	private static final int LABEL_WIDTH_LARGE=(SCREEN_WIDTH*20)/100;
	private static final int LABEL_HEIGHT=(int)(SCREEN_HEIGHT*2.7f)/100;
	
	
	private static final int LEFT_MARGIN=(SCREEN_WIDTH*5)/100;
	private static final int TOP_MARGIN=(SCREEN_HEIGHT*6)/100;
	private static final int BOTTOM_MARGIN=(SCREEN_HEIGHT*5)/100;
	private static final int WIDTH_SPACING_SMALL=(SCREEN_HEIGHT*2)/100;
	private static final int WIDTH_SPACING_MEDIUM=(SCREEN_HEIGHT*4)/100;	
	private static final int HEIGHT_SPACING_SMALL=(SCREEN_HEIGHT*2)/100;
	private static final int HEIGHT_SPACING_MEDIUM=(SCREEN_HEIGHT*4)/100;
	
		
	
	private static final int APPLICATION_WIDTH=(SCREEN_WIDTH*50)/100;
	private static final int APPLICATION_HEIGHT=(SCREEN_HEIGHT*60)/100;
	

	private static Dimension applicationSize=new Dimension(APPLICATION_WIDTH,APPLICATION_HEIGHT);	

	private JLabel lbl_SelectUploadFile,lbl_provideOutputFileName,lbl_SelectedUploadFile,lbl_processStatus,lbl_SelectedOutputFile;
	private JButton btn_selectUploadFile,btn_startProcessing,btn_selectOutputFile;
	
	private boolean dataFileSelected=false,outputFileSelected=false;
	
	private NameProcessor processor;
	
	
	public Namer() {
		
		
		
		
		/****First row********************************/
		 lbl_SelectUploadFile =new JLabel("Select data file");		
		lbl_SelectUploadFile.setLocation(LEFT_MARGIN+WIDTH_SPACING_SMALL*3,TOP_MARGIN);
		lbl_SelectUploadFile.setVisible(true);
		lbl_SelectUploadFile.setSize(LABEL_WIDTH_SMALL, LABEL_HEIGHT);		
		add(lbl_SelectUploadFile);
		
		
		
		int secondColumnLocX=LEFT_MARGIN+LABEL_WIDTH_SMALL+WIDTH_SPACING_SMALL;
		
		 btn_selectUploadFile = new JButton("Select Data File");		
		btn_selectUploadFile.setLocation(secondColumnLocX, TOP_MARGIN);
		btn_selectUploadFile.setVisible(true);
		btn_selectUploadFile.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		btn_selectUploadFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
	
				 dataFile=selectFile("CSV(.csv)",fileFilter);
				
				 
				 if(dataFile!=null)
					 dataFileSelected=true;
				 else
					 return;
				 
				 lbl_SelectedUploadFile.setText(dataFile.getName());
				 
				 
				 if(dataFileSelected && outputFileSelected)
				 {
					 btn_startProcessing.setEnabled(true);
					 processor= new NameProcessor(dataFile,saveFile);
					 processor.addStatusListener(mainFrame);
				 }
				
			}
			
			
		});	
		add(btn_selectUploadFile);
		
		
		int thirdColumnLocX = secondColumnLocX + BUTTON_WIDTH+WIDTH_SPACING_SMALL;
		
		lbl_SelectedUploadFile =new JLabel("");		
		lbl_SelectedUploadFile.setLocation(thirdColumnLocX,TOP_MARGIN);
		lbl_SelectedUploadFile.setVisible(true);
		lbl_SelectedUploadFile.setSize(LABEL_WIDTH_MEDIUM, LABEL_HEIGHT);		
		add(lbl_SelectedUploadFile);
		
	
		
		
		/****Second row********************************/
		
		
		int secondRowLocY=TOP_MARGIN+LABEL_HEIGHT+HEIGHT_SPACING_MEDIUM;
		
		lbl_provideOutputFileName =new JLabel("Provide output file name");		
		lbl_provideOutputFileName.setLocation(LEFT_MARGIN,secondRowLocY);
		lbl_provideOutputFileName.setVisible(true);		
		lbl_provideOutputFileName.setSize(LABEL_WIDTH_MEDIUM, LABEL_HEIGHT);		
		add(lbl_provideOutputFileName);		
		
		
		
		 btn_selectOutputFile = new JButton("Select Output File");
		 btn_selectOutputFile.setLocation(secondColumnLocX, secondRowLocY);
		 btn_selectOutputFile.setVisible(true);
		 btn_selectOutputFile.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		 btn_selectOutputFile.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae)
				{
		
					 saveFile=openSaveFileDialog();				
					
					
					 if(saveFile!=null)
						 outputFileSelected=true;
					 else
						 return;
					 
					 lbl_SelectedOutputFile.setText(saveFile.getName());
					 
					 
					 if(dataFileSelected && outputFileSelected)
					 {
						 btn_startProcessing.setEnabled(true);
						 processor= new NameProcessor(dataFile,saveFile);
						 processor.addStatusListener(mainFrame);
					 }
					
				}
				
				
			});	
			add(btn_selectOutputFile);
		
		
			
			lbl_SelectedOutputFile =new JLabel("");		
			lbl_SelectedOutputFile.setLocation(thirdColumnLocX,secondRowLocY);
			lbl_SelectedOutputFile.setVisible(true);
			lbl_SelectedOutputFile.setSize(LABEL_WIDTH_MEDIUM, LABEL_HEIGHT);		
			add(lbl_SelectedOutputFile);
			
		
			
			
		
			
		/******Third row***************/
			
		int thirdRowLocY = 	secondRowLocY+LABEL_HEIGHT+HEIGHT_SPACING_MEDIUM;
		
			
		 btn_startProcessing = new JButton("Start Data Processing");
		btn_startProcessing.setLocation(secondColumnLocX, thirdRowLocY);
		btn_startProcessing.setEnabled(false);
		btn_startProcessing.setVisible(true);
		btn_startProcessing.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		
		btn_startProcessing.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
	
				 btn_startProcessing.setEnabled(false); 
				 btn_selectUploadFile.setEnabled(false);
				 btn_selectOutputFile.setEnabled(false);
				 
				 
				 Thread backgroundThread = new Thread(processor);
				 backgroundThread.start();
				 
				
			}
			
			
		});	
		add(btn_startProcessing);
		
		
		
		
		/*********Fourth Row*****************/
		
		
		int fourthRowLocY = thirdRowLocY + LABEL_HEIGHT + HEIGHT_SPACING_MEDIUM*2;
		
		    lbl_processStatus =new JLabel("");		
		    lbl_processStatus.setLocation(LEFT_MARGIN+WIDTH_SPACING_MEDIUM,fourthRowLocY);
		    lbl_processStatus.setVisible(true);
		    lbl_processStatus.setSize(LABEL_WIDTH_LARGE*2, LABEL_HEIGHT);		
			add(lbl_processStatus);		
			
			
			
			
		/***Fifth row******************/
			
			JLabel lbl_Help = new JLabel("***Tool for converting names in various formats into First Name, Middle Name and Last Name***");
			lbl_Help.setLocation(WIDTH_SPACING_SMALL,470);
			lbl_Help.setVisible(true);
			lbl_Help.setSize((int)(LABEL_WIDTH_LARGE*2.3f), LABEL_HEIGHT);		
			add(lbl_Help);		
				
			
		
	}
	
	public static void main(String[] args) {
		
		    Namer application=new Namer();	
		   
			application.setSize(applicationSize);
			application.setLocation(100, 10);
			application.setResizable(false);
			application.setLayout(null);
			application.setVisible(true);
			application.setTitle("Namer");
			application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
			
			
		
		
	}
	
	
	
	public void statusChanged(StatusEvent event) {
		
	
		if(event.errorMessage!=null) {

			 btn_selectUploadFile.setEnabled(true);
			 btn_selectOutputFile.setEnabled(true);
			 
			 lbl_processStatus.setText("Error! "+ event.errorMessage);
			 return;
			
		}			
		
		lbl_processStatus.setText(event.recordsProcessed+" Records Processed in " + event.timeElapsed+" Minutes");
		
		if(event.completed)
		{		
			
			 btn_selectUploadFile.setEnabled(true);
			 btn_selectOutputFile.setEnabled(true);			 
			 //lbl_processStatus.setText("Completed! "+ event.recordsProcessed+ " Records Processed in " + event.timeElapsed+ " Minutes");
			 lbl_processStatus.setText("Completed! "+" Total:"+event.recordsProcessed+" Names:"+event.namesCnt+" Orgs:"+event.orgCnt+" Skipped:"+event.skippedCnt+" Success Rate:"+((event.namesCnt+event.orgCnt)*100.0)/(event.recordsProcessed)+"%");
			 			
		}
		
		
	}
	
	
	private File selectFile(String name,String[] types)
	 {
		 
		 JFileChooser chooser=new JFileChooser();
		 FileFilter filter = new FileNameExtensionFilter(name, types);
		 chooser.setFileFilter(filter);
		 chooser.addChoosableFileFilter(filter);
		   chooser.setMultiSelectionEnabled(false);		 
		   
		   chooser.showOpenDialog(this);
		   
		   
		   System.out.println("Selected file is "+chooser.getSelectedFile());
		   
		   return chooser.getSelectedFile();
		   
		  
		
		 
	 }
	
	
	public File openSaveFileDialog()
	{
		
		JFileChooser fileChooser = new JFileChooser();
		 
		 String[] types={".csv"};
		 FileFilter filter = new FileNameExtensionFilter("CSV(.csv)",types );
		 fileChooser.setFileFilter(filter);
		 fileChooser.addChoosableFileFilter(filter);
	     int rVal = fileChooser.showSaveDialog(this);
	     if (rVal == JFileChooser.APPROVE_OPTION) {
	    	 
	    	
	     return fileChooser.getSelectedFile();     
	                
	        
	      }	      
	      else return null;
		
		
	}
	
	

}
