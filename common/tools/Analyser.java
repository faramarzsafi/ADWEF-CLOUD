package common.tools;

	import java.io.BufferedWriter;
	import java.io.File;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.sql.Connection;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.sql.ResultSet;
	import java.sql.DriverManager;
import java.util.LinkedList;

import org.exolab.castor.xml.schema.FinalList;

import common.share;
	
	public class Analyser { 
	
	public static void main( String [] args )
    {
		System.out.println("Analyzing Started ...");
		Analyser a = new Analyser();
		//String Path = "D:\\temp\\Final-Results\\WithMessaging\\EXP\\";
		String Path = "D:\\temp\\2009-11-30-final_IF\\temp";
		File dir =  
			new File(Path);
		
	    String[] children = dir.list();
	    if (children == null) {
	    	System.out.println("Either dir does not exist or is not a directory");
	    } else {
	        for (int i=0; i<children.length; i++) {
	            // Get filename of file or directory
				String FILE_NAME = children[i];
				if(!a.isDirECTORY(Path+FILE_NAME)){
					String ANALYZED_DIR =  
	//					"D:\\temp\\analayzed\\MSG_Dcent_Sec_Step=1\\";
						Path+"Analysed\\";
					a.analyz(FILE_NAME,ANALYZED_DIR );
					System.out.println("\""+FILE_NAME+"\" : "+" ...Anayzing Finished.");
				}
	        }
	        System.out.println(" End of Experiment.");
	    }
    }
	
	public boolean isDirECTORY(String fileOrDirName){
		
		File dir = new File(fileOrDirName);
	    return dir.isDirectory();
	}
		
	void analyz(String FILE_NAME, String ANALYZED_DIR){
		Connection c = null;
//		Connection c2 = null;
	    Statement stmnt = null;
//	    Statement stmnt2 = null;
	    
	    LinkedList<LogRecord> finalList = new LinkedList<LogRecord>();
	    
		long speed =0;int poolSize=0;int rtn=0;int rate =0;double responseTime=0;double throughputPerUnit=0;
		long totalTime=0;int completedPerUnit=0;int notCompletedPerUnit =0;double sleepTime =0;
		long mili =0;int nano =0;double expTimeLJ =0;double loadTime =0;double joinTime =0;
		int notStarted =0;int notStopped =0;long shortLife =0;long longLife =0; long NumberOfMessages = 0;
		System.out.println("Analysed: "+ANALYZED_DIR);
		System.out.println("File_name: "+FILE_NAME);
		String FILE_PATH = ANALYZED_DIR+FILE_NAME.substring(0, FILE_NAME.indexOf('.'))+"_"+share.Date()+"_"+share.hour+".csv";
		writeOutputHeader(FILE_PATH, share.FINAL_FILE_OUTPUT_FORMAT);
		
	     try {
			Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );
	
//			c = DriverManager.getConnection( "jdbc:odbc:fara", "", "" );
//	        stmnt = c.createStatement();
//	        String query = "select * from ["+FILE_NAME+"]";
//	        ResultSet rs1 = stmnt.executeQuery( query );
//	        LinkedList<LogRecord> l1 = convertToList(rs1);
	        
	        c = DriverManager.getConnection("jdbc:odbc:fara", "", "" );
	        stmnt = c.createStatement();
	        String query = "select * from ["+FILE_NAME+"]";
	        ResultSet rs = stmnt.executeQuery( query );
	        
	        LinkedList<LogRecord> mlist = convertToList(rs);
	        LinkedList<LogRecord> mlist2 = mlist;
	        
//	        int cnt = 0; 
//	        for (int t = 0; t<mlist.size();t++)	{
//    			if (((LogRecord)mlist.get(t)).rtn == 84){
//    	        	cnt++;
//    			}
//    		}
//			System.out.println("count is: "+cnt);
			
			int index1 = 0;			
        	while (mlist.size()>0){
        		
        		int found = 0;
        		LinkedList<LogRecord> tlist = new LinkedList<LogRecord>();
        		int key = ((LogRecord)mlist.get(index1)).rtn;
//        		System.out.println("key is: "+key);
//	        	if (key==84)	{
//	        		System.out.println("84 found");
//	        	}
//	        	
        		int index2 =0;    		
        		while ( index2 < mlist2.size()){
//        			System.out.println("mlist2.size(): "+mlist2.size());
	     			if (((LogRecord)mlist2.get(index2)).rtn == key){
	     				found++;
		         		tlist.add((LogRecord)mlist2.remove(index2));
//		        		System.out.println("key removed!");
		         		index2=-1;
	     			}
    				index2++;
        		}
//        		System.out.println("here3");
//	        	if (key==84)	{
//	        		System.out.println("Found is: "+found);
//	        		for (int t = 0; t<tlist.size();t++)	{
//	        			System.out.println(((LogRecord)tlist.get(t)).rtn);
//	        		}
//	        	}
        	mlist = mlist2;        	
        	if (found > 0){

	        	
	     		speed =0;poolSize=0; rtn=0; rate =0; responseTime=0; throughputPerUnit=0;
	     		totalTime=0; completedPerUnit=0; notCompletedPerUnit =0; sleepTime =0;
	     		mili =0; nano =0; expTimeLJ =0; loadTime =0; joinTime =0;
	     		notStarted =0; notStopped =0; shortLife =0; longLife =0; NumberOfMessages = 0;
	     		
	     		for (int i=0; i<tlist.size(); i++){
	     			speed += ((LogRecord)tlist.get(i)).speed; 
	     			poolSize += ((LogRecord)tlist.get(i)).poolSize;
	     			rtn += ((LogRecord)tlist.get(i)).rtn;
	     			rate += ((LogRecord)tlist.get(i)).rate;
	     			responseTime +=  ((LogRecord)tlist.get(i)).responseTime;
	     			throughputPerUnit += ((LogRecord)tlist.get(i)).throughputPerUnit;
	     			totalTime += ((LogRecord)tlist.get(i)).totalTime;
	     			completedPerUnit += ((LogRecord)tlist.get(i)).completedPerUnit;
	     			notCompletedPerUnit += ((LogRecord)tlist.get(i)).notCompletedPerUnit;
	     			sleepTime += ((LogRecord)tlist.get(i)).sleepTime;
	     			mili += ((LogRecord)tlist.get(i)).mili;
	     			nano += ((LogRecord)tlist.get(i)).nano;
	     			expTimeLJ += ((LogRecord)tlist.get(i)).expTimeLJ;
	     			loadTime += ((LogRecord)tlist.get(i)).loadTime;
	     			joinTime += ((LogRecord)tlist.get(i)).joinTime;
	     			notStarted += ((LogRecord)tlist.get(i)).notStarted;
	     			notStopped += ((LogRecord)tlist.get(i)).notStopped;
	     			shortLife += ((LogRecord)tlist.get(i)).shortLife;
	     			longLife += ((LogRecord)tlist.get(i)).longLife;
	     			NumberOfMessages += ((LogRecord)tlist.get(i)).NumberOfMessages;
	     			
	     		}
	     		
	         	Record rec = new Record(
	         	speed /tlist.size(),
		     	poolSize / tlist.size(),
		     	found,
		     	rtn/tlist.size(),
		     	rate / tlist.size(),
		     	responseTime / tlist.size(),
		     	throughputPerUnit / tlist.size(),
		     	totalTime / tlist.size(),
		     	completedPerUnit / tlist.size(),
		     	notCompletedPerUnit/ tlist.size(),
		     	sleepTime / tlist.size(),
		     	mili / tlist.size(),
		     	nano / tlist.size(),
		     	expTimeLJ / tlist.size(),
		     	loadTime / tlist.size(),
		     	joinTime / tlist.size(),
		     	notStarted / tlist.size(),
		     	notStopped / tlist.size(),
		     	shortLife / tlist.size(),
		     	longLife / tlist.size(),
		     	NumberOfMessages / tlist.size());
	         	
		     	boolean added = false;
 				if (finalList.size() == 0)
 					finalList.add(rec);
 				else for (int si =0; ((si <= finalList.size()&&(!added))); si++)
 					if (si == finalList.size())
					{finalList.add(rec);	added = true;}
 					else if (((Record)finalList.get(si)).rtn > rec.rtn)
	     					{finalList.add(si, rec);	added = true;}
	        }
	     }
	        
//   for (int si = 0; si < finalList.size(); si++)
//    	System.out.println("Element[" +si+"]= "+((Record)finalList.get(si)).rtn);
	        
   for (int si = 0; si < finalList.size(); si++)
	   writeResultRecord(((Record)finalList.get(si)).speed,((Record)finalList.get(si)).poolSize,
	   	((Record)finalList.get(si)).found,((Record)finalList.get(si)).rtn,((Record)finalList.get(si)).rate, 
	   	((Record)finalList.get(si)).responseTime, ((Record)finalList.get(si)).throughputPerUnit, 
	   	((Record)finalList.get(si)).totalTime, ((Record)finalList.get(si)).completedPerUnit, 
	   	((Record)finalList.get(si)).notCompletedPerUnit,((Record)finalList.get(si)).sleepTime, 
	   	((Record)finalList.get(si)).mili, ((Record)finalList.get(si)).nano, 
	   	((Record)finalList.get(si)).expTimeLJ, ((Record)finalList.get(si)).loadTime, 
	   	((Record)finalList.get(si)).joinTime, ((Record)finalList.get(si)).notStarted, 
	   	((Record)finalList.get(si)).notStopped, ((Record)finalList.get(si)).shortLife, 
	   	((Record)finalList.get(si)).longLife, ((Record)finalList.get(si)).NumberOfMessages, 
	   	FILE_PATH);
   
     }catch( Exception e ){ System.out.println( "in the Exception Try: "+e.getMessage()+" "+e.getCause() );  }
	   finally{
        try {stmnt.close();  c.close(); }
        catch( Exception e ) { System.out.println( "in the Exception Finally: "+e.getMessage()+e.getCause()); }
	    }
	}

	private void writeResultRecord(long speed, int poolSize,int found, int rtn, int rate, double responseTime, double throughputPerUnit, long totalTime, int completedPerUnit, int notCompletedPerUnit, double sleepTime, long mili, int nano, double expTimeLJ, double loadTime, double joinTime, int notStarted, int notStopped, long shortLife, long longLife, long NumberOfMessages, String FILE_PATH) {
		BufferedWriter out = null;
		try {
    		(new File(FILE_PATH.substring(0,FILE_PATH.lastIndexOf("\\")))).mkdirs();
			(new File(FILE_PATH)).createNewFile();
			out = new BufferedWriter(new FileWriter(FILE_PATH,true));
			out.write(speed+","+poolSize+","+found+","+rtn+","+rate+","+responseTime+","+throughputPerUnit+","+totalTime+","+completedPerUnit+","+notCompletedPerUnit+","+sleepTime+","+mili+","+nano+","+expTimeLJ+","+loadTime+","+joinTime+","+notStarted+","+notStopped+","+shortLife+","+longLife+","+NumberOfMessages+"\n");
			out.close();
		} catch (IOException e) {
			System.out.println("IOException:ClientWorkflow: writeResultRecord: in the catch"+e.getMessage()+e.getCause());
		}
	}
		
	private void writeOutputHeader(String FILE_PATH, String HEADER) {
		BufferedWriter out = null;
        try {
    		(new File(FILE_PATH.substring(0,FILE_PATH.lastIndexOf("\\")))).mkdirs();
			(new File(FILE_PATH)).createNewFile();
			out = new BufferedWriter(new FileWriter(FILE_PATH,true));
			out.write(HEADER);
			out.close();
		} catch (IOException e) {
			System.out.println("IOException:ClientWorkflow: writeOutputHeader: in the catch"+e.getMessage()+e.getCause());
		}
	}

	private LinkedList<LogRecord> convertToList(ResultSet rs) {
		LinkedList<LogRecord> ls = new LinkedList<LogRecord>();
		try {
			while (rs.next()){
					LogRecord r = new LogRecord();
					r.speed = rs.getLong("Speed");
					r.poolSize = rs.getInt("Pool Size");
					r.rtn = rs.getInt("Real Threads(RTN)");
					r.rate = rs.getInt("rate");
					r.responseTime = rs.getDouble("responseTime");
					r.throughputPerUnit = rs.getDouble("throughputPerUnit");
					r.totalTime =  rs.getLong("totalTime");
					r.completedPerUnit = rs.getInt("completedPerUnit");
					r.notCompletedPerUnit = rs.getInt("notCompletedPerUnit");
					r.sleepTime = rs.getDouble("sleepTime"); 
					r.mili = rs.getLong("mili"); 
					r.nano = rs.getInt("nano"); 
					r.expTimeLJ = rs.getDouble("expTime"); 
					r.loadTime = rs.getDouble("loadTime"); 
					r.joinTime = rs.getDouble("joinTime");; 
					r.notStarted = rs.getInt("NotStarted"); 
					r.notStopped = rs.getInt("NotStopped"); 
					r.shortLife  = rs.getLong("Short Life"); 
					r.longLife  = rs.getLong("Long Life");
					r.NumberOfMessages = rs.getLong("NumberOfMessages");
					ls.add(r);
			}
		} catch (SQLException e) {e.printStackTrace();}
		return ls;
	}

	class Record extends LogRecord{
		protected int found;
		public Record(long speed, int poolSize, int found, int rtn, int rate, double responseTime, double throughputPerUnit,
				long totalTime, int completedPerUnit, int notCompletedPerUnit, double sleepTime, 
				long mili, int nano, double expTimeLJ, double loadTime,
				double joinTime, int notStarted, int notStopped, long shortLife, long longLife, long NumberOfMessages) {

				this.speed =speed; this.poolSize =poolSize;  this.found = found;  this.rtn= rtn;  this.rate =rate;
				this.responseTime =responseTime;   this.throughputPerUnit =throughputPerUnit;  this.totalTime =totalTime;  
				this.completedPerUnit = completedPerUnit; this.notCompletedPerUnit=notCompletedPerUnit;  
				this.sleepTime =sleepTime;  this.mili =mili; this.nano = nano;  this.expTimeLJ = expTimeLJ; 
				this.loadTime =loadTime; this.joinTime =joinTime;  this.notStarted =notStarted;  this.notStopped =notStopped;     	
				this.shortLife = shortLife;  this.longLife = longLife; this.NumberOfMessages = NumberOfMessages;
     	}
	}

	class LogRecord {
		
		public LogRecord() {}
		
		protected long speed;
		protected int poolSize;
		protected int rtn;
		protected int rate;
		protected double responseTime;
		protected double throughputPerUnit;
		protected long totalTime;
		protected int completedPerUnit;
		protected int notCompletedPerUnit;
		protected double sleepTime; 
		protected long mili; 
		protected int nano; 
		protected double expTimeLJ; 
		protected double loadTime; 
		protected double joinTime; 
		protected int notStarted; 
		protected int notStopped; 
		protected long shortLife; 
		protected long longLife;
		protected long NumberOfMessages;
	}	
}



