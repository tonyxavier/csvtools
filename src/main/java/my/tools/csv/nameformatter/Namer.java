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
	 File dataFile=null;
	    


	private static final long serialVersionUID = 4890832876837628615L;
	public static final int SCREEN_WIDTH=Toolkit.getDefaultToolkit ().getScreenSize().width;
	public static final int SCREEN_HEIGHT=Toolkit.getDefaultToolkit ().getScreenSize().height;
	
	
	public static final int APPLICATION_WIDTH=(SCREEN_WIDTH*50)/100;
	public static final int APPLICATION_HEIGHT=(SCREEN_HEIGHT*60)/100;
	

	private static Dimension applicationSize=new Dimension(APPLICATION_WIDTH,APPLICATION_HEIGHT);	

	JLabel lbl_SelectUploadFile,lbl_provideOutputFileName,lbl_SelectedUploadFile,lbl_processStatus,lbl_SelectedOutputFile;
	JButton btn_selectUploadFile,btn_startProcessing,btn_selectOutputFile;
	
	boolean dataFileSelected=false,outputFileSelected=false;
	
	NameProcessor processor= new NameProcessor();
	
	
	public Namer() {
		
		
		processor.addStatusListener(this);
		/****First row********************************/
		 lbl_SelectUploadFile =new JLabel("Select data file");		
		lbl_SelectUploadFile.setLocation(100,100);
		lbl_SelectUploadFile.setVisible(true);
		lbl_SelectUploadFile.setSize(100, 25);		
		add(lbl_SelectUploadFile);
		
		
		 btn_selectUploadFile = new JButton("Select Data File");
		btn_selectUploadFile.setLocation(220, 100);
		btn_selectUploadFile.setVisible(true);
		btn_selectUploadFile.setSize(170, 25);
		btn_selectUploadFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
	
				 dataFile=selectFile("CSV(.csv)",fileFilter);
				 if(dataFile!=null)
					 dataFileSelected=true;
				 lbl_SelectedUploadFile.setText(dataFile.getName());
				
			}
			
			
		});	
		add(btn_selectUploadFile);
		
		
		lbl_SelectedUploadFile =new JLabel("");		
		lbl_SelectedUploadFile.setLocation(400,100);
		lbl_SelectedUploadFile.setVisible(true);
		lbl_SelectedUploadFile.setSize(100, 25);		
		add(lbl_SelectedUploadFile);
		
	
		
		
		/****Second row********************************/
		
		 lbl_provideOutputFileName =new JLabel("Provide output file name");		
		lbl_provideOutputFileName.setLocation(30,150);
		lbl_provideOutputFileName.setVisible(true);
		lbl_provideOutputFileName.setSize(180, 25);		
		add(lbl_provideOutputFileName);		
		
		
		 btn_selectOutputFile = new JButton("Select Output File");
		 btn_selectOutputFile.setLocation(220, 150);
		 btn_selectOutputFile.setVisible(true);
		 btn_selectOutputFile.setSize(170, 25);
		 btn_selectOutputFile.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae)
				{
		
					String outputFile=openSaveFileDialog();
					
					System.out.println("Output to:"+outputFile);
					
					
					 if(outputFile!=null && outputFile.length()>0)
						 outputFileSelected=true;
					 
					 lbl_SelectedOutputFile.setText(outputFile);
					 
					 
					 if(dataFileSelected && outputFileSelected)
						 btn_startProcessing.setEnabled(true);
					
				}
				
				
			});	
			add(btn_selectOutputFile);
		
		
			
			lbl_SelectedOutputFile =new JLabel("");		
			lbl_SelectedOutputFile.setLocation(400,150);
			lbl_SelectedOutputFile.setVisible(true);
			lbl_SelectedOutputFile.setSize(100, 25);		
			add(lbl_SelectedOutputFile);
			
		
			
			
		
			
		/******Third row***************/
			
		 btn_startProcessing = new JButton("Start Data Processing");
		btn_startProcessing.setLocation(220, 280);
		btn_startProcessing.setEnabled(false);
		btn_startProcessing.setVisible(true);
		btn_startProcessing.setSize(170, 25);
		
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
		
		
		
		    lbl_processStatus =new JLabel("test                                                   end ");		
		    lbl_processStatus.setLocation(150,350);
		    lbl_processStatus.setVisible(true);
		    lbl_processStatus.setSize(500, 35);		
			add(lbl_processStatus);		
			
		
		
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
	
	public void showsaveDialog(JFrame frame) {
	
	JFileChooser fileChooser = new JFileChooser();
	fileChooser.setDialogTitle("Specify a file to save");   
	 
	int userSelection = fileChooser.showSaveDialog(frame);
	 
	if (userSelection == JFileChooser.APPROVE_OPTION) {
	    File fileToSave = fileChooser.getSelectedFile();
	    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
	}
	}
	
	public void statusChanged(StatusEvent event) {
		
	
		
		lbl_processStatus.setText("Records Processed:"+event.recordsProcessed+" Time Elapsed:"+event.timeElapsed+" mins");
		
		if(event.completed)
		{
			
			
			 btn_selectUploadFile.setEnabled(true);
			 btn_selectOutputFile.setEnabled(true);
			 
			 lbl_processStatus.setText("Completed! Records Processed:"+event.recordsProcessed+" Time Elapsed:"+event.timeElapsed+" mins");
			
			 
			
			
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
	
	
	public String openSaveFileDialog()
	{
		
		JFileChooser c = new JFileChooser();
		 
		  String[] types={".map"};
		 FileFilter filter = new FileNameExtensionFilter("Mapping Definition(.map)",types );
		 c.setFileFilter(filter);
		 c.addChoosableFileFilter(filter);
	      int rVal = c.showSaveDialog(this);
	      if (rVal == JFileChooser.APPROVE_OPTION) {
	        String  name =c.getSelectedFile().getName();
	        String dir=c.getCurrentDirectory().toString();
	        return dir+File.separator+name;
	        
	      }
	      
	      else return null;
		
		
	}
	
	

}
