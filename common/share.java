package common;
import java.util.Calendar;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class share {
	
	final public static byte[] MESSAGE = new byte[250000];
	final public static int boundaryLoopRepeatation = 10;
		
//	private static final share SINGLETON = new share();
//	public static share getInstance(){ return SINGLETON;}
	public static int repCount = 0 ;
	public static int recCount = 0;
	public static int flowCount = 0;
	private share(){
//		System.out.println("In the Share Constructor");
	}
	
	//Audio File Path
	public static final String AUDIO_FILE_PATH = "/opt/Waves/online.wav";
	
	//Sleep for platform preparation
	public static final long SLEEP_FOR_PREPARATION = 15000;
	
	public final static long DELAY_BETWEEN_EXPERIMENTS = 3000;
	
	public final static long SPEED = 1000;//shows time unit. for instance per minute = 60000
	
//	public static final int POOL_SIZE = (int) (Math.pow(2, 16)-1);
	public static final int POOL_SIZE = 1000;//Currently I do not use it.
	
	public final static int MAXRATE = 100; //Number of Threads Per Minute, Starting from 1 and next rate is calculated by RATE_STEP 
	public final static int RATE_START = 1;
	public final static int RATE_STEP = 100;
	
	public final static int MAX_INTEGER = 2147483647;//T MAX value of integer data type
	public final static long WORKFLOW_TIMEOUT_ALL = 2*3600*1000;//Max timeout is considered Two Hours
	
	public final static int NUMBER_OF_SERVERS = 1;
	public final static int NUMBER_OF_AGENTS = 1;
	
	//BULK = 2 means No BULK Request or perUnit request and 1 means Bulk Request
	public static int BULK = 2;
	
	//Mode=2 means using several servers and for each workflow fragment one performer agent
	//Mode=1 means for each workflow one performer agent on each server. all fragments of a workflow run on the same performer on one machine.
	//currently it set by user through a GUI
//	public static int MODE = 2;
	
	public static String LOAD ="LIN";
	
	public final static long ACTIVITY_SLEEP = 0;//ms for simple activities processing time// it will not set through init.txt
	
	public final static int INVALID = 0;
	public final static int CENTRALIZED = 1;
	public final static int FPD = 2;
	public final static int IPD0 = 3;
	public final static int IPD1 = 4;
	public final static int IPD2 = 5;
	public final static int IPD3 = 6;
	public final static int IPD4 = 7;
	
	public final static int HPD0_IF = 8;
	public final static int HPD1_IF = 9;
	public final static int HPD2_IF = 10;
	public final static int HIPD0_IF = 11;
	public final static int HIPD1_IF = 12;
	
	public final static int HPD0_WHILE = 13;
	public final static int HPD1_WHILE = 14;
	public final static int HPD2_WHILE = 15;
//	public final static int HIPD0_WHILE = 16;
	public final static int HIPD1_WHILE = 17;
	
	public final static int HPD0_FLOW = 18;
	public final static int HPD1_FLOW = 19;
	public final static int HPD2_FLOW = 20;
	public final static int HIPD1_FLOW = 21;
	
	public final static int HPD0_PICK = 22;
	public final static int HPD1_PICK = 23;
	public final static int HPD2_PICK = 24;
	
	public final static int HPD0_LOAN = 25;
	public final static int HPD1_LOAN = 26;
	public final static int HPD2_LOAN = 27;
	public final static int HPD3_LOAN = 28;
	public final static int HIPD0_LOAN = 29;
	public final static int HIPD1_LOAN = 30;
	
	
	public final static int EX4 = 95;
	public final static int EX3 = 96;
	public final static int EX2 = 97;
	public final static int EX1 = 98;
	
	public final static int IF_TEST1 = 99;
	public final static int IF_TEST2 = 100;
	
	public final static int FUZZY_BANDWIDTH = 101;
	
	public static String date = Date();
	public static String hour = Hour();
	
	public static String FILE_OUTPUT_FORMAT = 
		"Speed,Pool_Size,VM_Size,RTN,rate,responseTime," +
		"throughputPerUnit,totalTime,completedPerUnit,notCompletedPerUnit,sleepTime," +
		"mili,nano,expTime,loadTime,joinTime," +
		"NotStarted,NotStopped,Short_Life,Long_Life,NumberOfMessages," +
		"StartDate,EndDate"+"\n";
	
	public static String FINAL_FILE_OUTPUT_FORMAT = 
		"Speed,Pool_Size,Found,Real_Threads(RTN),rate," +
		"responseTime,throughputPerUnit,totalTime,completedPerUnit,notCompletedPerUnit," +
		"sleepTime,mili,nano,expTime,loadTime," +
		"joinTime,NotStarted,NotStopped,ShortLife,LongLife," +
		"ExchangedMessages"+"\n";

	public final static String MAIN_PROPERTIES_FILE_PATH = 
		(checkOs("XP"))?"C:/safi/MyPhD/cfg/main.properties":"/root/workspace/MyPhD/cfg/main.properties";
	

//	public final  static String CONFIGURATION_FILE_PATH ="D:\\WADE-Source\\WADE-2\\wade\\cfg\\configuration\\init.txt";
	
	public static String getWorkflowStyle(int workflowStyle){

		String result = null;
		switch(workflowStyle){
			case share.CENTRALIZED: result = "CENT_";break;
			case share.FPD: result =  "FPD_";break;
			case share.IPD0: result =  "IPD0_";break;
			case share.IPD1: result =  "IPD1_";break;
			case share.IPD2: result =  "IPD2_";break;
			case share.IPD3: result =  "IPD3_";break;
			case share.IPD4: result =  "IPD4_";break;
			
			case share.HPD0_IF: result =  "IF_HPD0_";break;
			case share.HPD1_IF: result =  "IF_HPD1_";break;
			case share.HPD2_IF: result =  "IF_HPD2_";break;
			case share.HIPD0_IF: result =  "IF_HIPD0_";break;
			case share.HIPD1_IF: result =  "IF_HIPD1_";break;
			
			case share.HPD0_WHILE: result =  "WHILE_HPD0_";break;
			case share.HPD1_WHILE: result =  "WHILE_HPD1_";break;
			case share.HPD2_WHILE: result =  "WHILE_HPD2_";break;
	//		case share.HIPD0_WHILE: result =  "WHILE_HIPD0_";break;
			case share.HIPD1_WHILE: result =  "WHILE_HIPD1_";break;
			
			case share.HPD0_FLOW: result =  "FLOW_HPD0_";break;
			case share.HPD1_FLOW: result =  "FLOW_HPD1_";break;
			case share.HPD2_FLOW: result =  "FLOW_HPD2_";break;
			case share.HIPD1_FLOW: result =  "FLOW_HIPD1_";break;
			
			case share.HPD0_PICK: result =  "PICK_HPD0_";break;
			case share.HPD1_PICK: result =  "PICK_HPD1_";break;
			case share.HPD2_PICK: result =  "PICK_HPD2_";break;
			
			case share.HPD0_LOAN: result =  "LOAN_HPD0_";break;
			case share.HPD1_LOAN: result =  "LOAN_HPD1_";break;
			case share.HPD2_LOAN: result =  "LOAN_HPD2_";break;
			case share.HPD3_LOAN: result =  "LOAN_HPD3_";break;
			case share.HIPD0_LOAN: result =  "LOAN_HIPD0_";break;
			case share.HIPD1_LOAN: result =  "LOAN_HIPD1_";break;
			
			case share.EX1: result =  "EX1_";break;
			case share.EX2: result =  "EX2_";break;
			case share.EX3: result =  "EX3_";break;
			case share.EX4: result =  "EX4_";break;
			
			case share.IF_TEST1: result =  "IF_TEST1_";break;
			case share.IF_TEST2: result =  "IF_TEST2_";break;
			
			case share.FUZZY_BANDWIDTH: result =  "FUZZY_BANDWIDTH_";break;
	}
		return result;

	}

	public static String getFilePath(int BULK, int WORKFLOW_STYLE, int NUMBER_OF_AGENTS, int NUMBER_OF_SERVERS, long SPEED, int MAXRATE, int RATE_START, int RATE_STEP){

		String filePath = (checkOs("XP"))?"d:/temp/"+date+"/":"/opt/temp/"+date+"/";
		String StylePrefix = getWorkflowStyle(WORKFLOW_STYLE);
		
		String SPEED_STR = "";
		String BULK_STR = "";
		String MODE_STR = "MODE";
		MODE_STR += ContainerManagement.getExecutionMode();
	
		if (SPEED == 1000) SPEED_STR = "sec";
		else if (SPEED == 60000) SPEED_STR = "min";
		else SPEED_STR = new Long(SPEED).toString();
		
		if (BULK == 1) BULK_STR = "BULK";//bulk
		else if (BULK == 2) BULK_STR = "PU";//means per unit
		
		if (WORKFLOW_STYLE == INVALID)
			filePath = null;
		else filePath += StylePrefix;

		filePath += NUMBER_OF_AGENTS+"_"+NUMBER_OF_SERVERS+"_"+MODE_STR+"_"+BULK_STR+"_"+
			LOAD+"_"+SPEED_STR+"_"+	
			MAXRATE+"_"+RATE_START+"_"+RATE_STEP+"_"+date+"__"+hour+".csv";
		
		return filePath;
	}
	
	public static String Date() {
    	final String DATE_FORMAT_NOW = "yyyy-MM-dd";	
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    return sdf.format(cal.getTime());
   }
	
	public static String Hour() {
    	final String DATE_FORMAT_NOW = "hh-mm-ss";	
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    return sdf.format(cal.getTime());
   }
	
	public static int getWorkflowStyle(){
		try {
	        BufferedReader in = new BufferedReader(new FileReader(MAIN_PROPERTIES_FILE_PATH));
	        int WStyle = 0;
	        String str;
	        while ((str = in.readLine())!= null) {
	        	str.trim();
	        	
	        	if (str.equals("#CENTRALIZED"))
	        	  WStyle = CENTRALIZED;
	        	else if (str.indexOf("#FPD")==0)
	        	  WStyle = FPD;
	        	else if (str.indexOf("#IPD0")==0)
	        	  WStyle = IPD0;
	        	else if (str.indexOf("#IPD1")==0)
	        	  WStyle = IPD1;
	        	else if (str.indexOf("#IPD2")==0)
	        	  WStyle = IPD2;
	        	else if (str.indexOf("#IPD3")==0)
	        	  WStyle = IPD3;
	        	else if (str.indexOf("#IPD4")==0)
	        	  WStyle = IPD4;
	        	else if (str.indexOf("#HPD0_IF")==0)
		        	  WStyle = HPD0_IF;
	        	else if (str.indexOf("#HPD1_IF")==0)
		        	  WStyle = HPD1_IF;
	        	else if (str.indexOf("#HPD2_IF")==0)
		        	  WStyle = HPD2_IF;
	        	else if (str.indexOf("#HIPD0_IF")==0)
		        	  WStyle = HIPD0_IF;
	        	else if (str.indexOf("#HIPD1_IF")==0)
		        	  WStyle = HIPD1_IF;
	        	else if (str.indexOf("#HPD0_WHILE")==0)
		        	  WStyle = HPD0_WHILE;
	        	else if (str.indexOf("#HPD1_WHILE")==0)
		        	  WStyle = HPD1_WHILE;
	        	else if (str.indexOf("#HPD2_WHILE")==0)
		        	  WStyle = HPD2_WHILE;
//	        	else if (str.indexOf("#HIPD0_WHILE")==0)
//		        	  WStyle = HIPD0_WHILE;
	        	else if (str.indexOf("#HIPD1_WHILE")==0)
		        	  WStyle = HIPD1_WHILE;
	        	else if (str.indexOf("#HPD0_FLOW")==0)
		        	  WStyle = HPD0_FLOW;
	        	else if (str.indexOf("#HPD1_FLOW")==0)
		        	  WStyle = HPD1_FLOW;
	        	else if (str.indexOf("#HPD2_FLOW")==0)
		        	  WStyle = HPD2_FLOW;
	        	else if (str.indexOf("#HIPD1_FLOW")==0)
		        	  WStyle = HIPD1_FLOW;
	        	else if (str.indexOf("#HPD0_PICK")==0)
		        	  WStyle = HPD0_PICK;
	        	else if (str.indexOf("#HPD1_PICK")==0)
		        	  WStyle = HPD1_PICK;
	        	else if (str.indexOf("#HPD2_PICK")==0)
		        	  WStyle = HPD2_PICK;
	        	else if (str.indexOf("#HPD0-LOAN")==0)
		        	  WStyle = HPD0_LOAN;
	        	else if (str.indexOf("#HPD1-LOAN")==0)
		        	  WStyle = HPD1_LOAN;
	        	else if (str.indexOf("#HPD2-LOAN")==0)
		        	  WStyle = HPD2_LOAN;
	        	else if (str.indexOf("#HPD3-LOAN")==0)
		        	  WStyle = HPD3_LOAN;
	        	else if (str.indexOf("#HIPD0-LOAN")==0)
		        	  WStyle = HIPD0_LOAN;
	        	else if (str.indexOf("#HIPD1-LOAN")==0)
		        	  WStyle = HIPD1_LOAN;
	        	
	        	else if (str.indexOf("#EX1")==0)
	        		WStyle = EX1;
	        	else if (str.indexOf("#EX2")==0)
	        		WStyle = EX2;
	        	else if (str.indexOf("#EX3")==0)
	        		WStyle = EX3;
	        	else if (str.indexOf("#EX4")==0)
	        		WStyle = EX4;
	        	
	        	else if (str.indexOf("#IF_TEST1")==0)
		        	  WStyle = IF_TEST1;
	        	else if (str.indexOf("#IF_TEST2")==0)
		        	  WStyle = IF_TEST2;
	        	
	        	else if (str.indexOf("#FUZZY_BANDWIDTH")==0)
		        	  WStyle = FUZZY_BANDWIDTH;
	        	
	        	else if ((str.indexOf("agents=")==0)&&(WStyle!=0))
	        	  return WStyle;

	        }
	        in.close();
	    } catch (IOException e) {
	    	System.out.println("Share: IOException: in the catch"+e.getMessage()+e.getCause());
	    }
		return 0;
	}
	
	public static void writeOutputHeader(File fPath) {
        try {
        	 (new File(fPath.getParent())).mkdirs();
			 (new File(fPath.getPath())).createNewFile();
			 BufferedWriter out = new BufferedWriter(new FileWriter(fPath.getPath(),true));
			 out.write(share.FILE_OUTPUT_FORMAT);
			 out.close();
		} catch (IOException e) {
			System.out.println("WorkflowCliet:writeOutputHeader:in catch: "+e.getMessage()+e.getCause());
			Logger(fPath.getPath(), e.getMessage());
		}
	}
	
	public static void Logger(String FILE_PATH, String ex) {
		BufferedWriter out = null;

        try {
    		(new File(FILE_PATH.substring(0,FILE_PATH.lastIndexOf(getSep())))).mkdirs();
			(new File(FILE_PATH)).createNewFile();
			
			out = new BufferedWriter(new FileWriter(FILE_PATH,true));
			out.write(ex+"\n");
			out.close();
		} catch (IOException e) {
			System.out.println("IOException:ClientWorkflow: writeOutputHeader: in the catch"+e.getMessage()+e.getCause());
			Logger(FILE_PATH, e.getMessage());
		}
	}
	
	static boolean checkOs(String os){
		if (System.getProperty("os.name").indexOf(os)>0) return true;
		else return false;
	}
	
	static String getSep(){
		return System.getProperty("file.separator");
	}

	public static String getAudioFilePath(String string) {
		return "/opt/Waves/online.wav";
	}
}
