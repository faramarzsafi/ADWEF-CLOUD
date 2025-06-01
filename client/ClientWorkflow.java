package client;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.NotSerializableException;

import tuplespace.TupleSpace;
import com.tilab.wade.performer.Subflow;
import com.tilab.wade.performer.layout.MarkerLayout;
import com.tilab.wade.performer.layout.ActivityLayout;
import com.tilab.wade.performer.layout.WorkflowLayout;
import com.tilab.wade.performer.CodeExecutionBehaviour;
import com.tilab.wade.performer.FormalParameter;
import com.tilab.wade.performer.WorkflowBehaviour;

import common.ContainerManagement;
import common.share;

@WorkflowLayout(exitPoints = {@MarkerLayout(position="(410,426)", activityName="Reply"), @MarkerLayout(position="(408,744)", activityName="Assign8") }, activities={@ActivityLayout(position="(331,276)", name="WorkflowClientCodeActivity1"), @ActivityLayout(position="(375,236)", name="Workflow"), @ActivityLayout(position="(374,340)", name="Reply"), @ActivityLayout(position="(520,406)", name="Sequence3"), @ActivityLayout(position="(355,406)", name="Sequence2"), @ActivityLayout(position="(179,404)", name = "Sequence1"), @ActivityLayout(position="(377,267)", name="Flow"), @ActivityLayout(position="(328,219)", name="Assign"), @ActivityLayout(position="(378,347)", name = "Assign7"), @ActivityLayout(isDecisionPoint = true, position="(647,260)", name="If"), @ActivityLayout(position="(374,573)", name="Assign7"), @ActivityLayout(position="(745,371)", name="Assign6"), @ActivityLayout(position="(629,372)", name="Assign5"), @ActivityLayout(position="(515,371)", name="Assign4"), @ActivityLayout(position="(374,371)", name="Assign3"), @ActivityLayout(position="(222,372)", name="Assign2"), @ActivityLayout(position="(371,193)", name = "Assign1"), @ActivityLayout(size = "(102,50)", position="(389,172)", name = "Receive")})
public class ClientWorkflow extends WorkflowBehaviour {
	
	String FILE_PATH;

	public static final String WORKFLOWCLIENTCODEACTIVITY1_ACTIVITY = "WorkflowClientCodeActivity1";

	@FormalParameter
	private String SUBFLOW;
	
	@FormalParameter
	private String SUBFLOWAGENT;
	
//	@FormalParameter(mode=FormalParameter.OUTPUT)
//	private int output;

	
	private void defineActivities() {
		CodeExecutionBehaviour workflowClientCodeActivity1Activity = new CodeExecutionBehaviour(
				WORKFLOWCLIENTCODEACTIVITY1_ACTIVITY, this);
		registerActivity(workflowClientCodeActivity1Activity, INITIAL_AND_FINAL);

	}
	private void defineTransitions() {
	}
	public void sleepAtLeast(long millis) throws InterruptedException {
		  long t0 = System.currentTimeMillis();
		  long millisLeft = millis;
		  while (millisLeft > 0) {
		    Thread.sleep(millisLeft);
		    long t1 = System.currentTimeMillis();
		    millisLeft = millis - (t1 - t0);
		  }
		}

	protected void executeWorkflowClientCodeActivity1() {

		threadClientCall();
	}
	
	void performCallback(Subflow s){
		try {
			performSubflow(s);
		} catch (Exception e) {
			System.out.println("WorkflowClient: in the catch of performCallback: "+e.getMessage()+e.getCause());
		}
	}

	void threadClientCall()  {
		
		System.out.println("WorkflowClient: In thread Call");

		FILE_PATH = share.getFilePath(1,1,1,1, share.SPEED, share.MAXRATE, share.RATE_START, share.RATE_STEP);
		
		int rate;
		TupleSpace bag = null;
		common.Random random = null;
		ClientThread[] ct = null;
		long expBeforeLoading = 0;
		long expAfterLoading = 0;
		long expAfterJoining = 0;
		int notStarted = 0;
		int notStopped = 0;
		long totalTime = 0; int completedPerUnit = 0; int notCompletedPerUnit = 0;
		int rtn = 0;
		double responseTime = 0;
		double throughputPerUnit = 0;
		double expTimeLJ = 0;
		double loadTime = 0;
		double joinTime = 0;
		long lifeLength = 0;
		long longLife = 0;
		long shortLife = 0;
		
			
		writeOutputHeader(FILE_PATH);
		
		System.gc();
		long expStart = System.currentTimeMillis();
		
		rate = share.RATE_START;
		while (rate <= share.MAXRATE){
			
			bag = new TupleSpace();
			random = new common.Random();
			ct = new ClientThread[1000];
			
			for (int index =0; index<ct.length; index++){
				int inpt = (int)random.generateNumber_50_50();
				ct[index]= new ClientThread(this, bag, index, SUBFLOW, SUBFLOWAGENT);
				ct[index].setParams(inpt);
			}
			System.out.println("Statring...at: "+share.Hour());
			System.out.println("Statring...Rate: "+rate+" out of "+share.MAXRATE);

			double sleepTime = (double)share.SPEED/(double)rate;
			long mili = (long)sleepTime;
			int nano = (int) ((sleepTime*1000000f-(double)mili*1000000f));
			expBeforeLoading = System.currentTimeMillis();

			try {			
				
				rtn = 0;
				long startTime = System.currentTimeMillis();
//				while(((System.currentTimeMillis()-startTime)<share.SPEED)&&(rtn<rate)){
					while(((System.currentTimeMillis()-startTime)<1)){
				
					ct[rtn].start();
					Thread.sleep(mili,nano);
//					Thread.sleep(0,1);
					rtn++ ;
				}

				expAfterLoading = System.currentTimeMillis();
				
//				for(int i=0; i<ct.length; i++){
				for(int i=0; i<rtn; i++){
					ct[i].join();
				}
				
				expAfterJoining = System.currentTimeMillis();
				
				totalTime = 0; completedPerUnit = 0; notCompletedPerUnit = 0;
				notStarted = 0; notStopped = 0; shortLife =0; longLife = 0;
//				for(int i=0; i<ct.length; i++){
				for(int i=0; i<rtn; i++){
					
					totalTime += (ct[i].getStop()-ct[i].getStart());
					
					lifeLength =  ct[i].getStop() - startTime; 
					if (lifeLength >= share.SPEED)
						longLife++;
					else
						shortLife++;
					
					if (ct[i].getStart()<=0)
						notStarted++;
				
					if ((ct[i].getStart()>0)&&(ct[i].getStop()<=0))
						notStopped++;

					if (((ct[i].getStop()-startTime) < share.SPEED))
						completedPerUnit++;
					
					if (((ct[i].getStart()-startTime) < share.SPEED)&&
					((ct[i].getStop()-startTime)>= share.SPEED))
						notCompletedPerUnit++;
				}
				
				responseTime = (totalTime/rtn)/1000f;//Converted to Sec by /1000
				throughputPerUnit = (completedPerUnit*100f)/(completedPerUnit+notCompletedPerUnit);
				expTimeLJ = (expAfterJoining-expBeforeLoading)/(1000f);
				loadTime = (expAfterLoading-expBeforeLoading)/(1000f);
				joinTime = (expAfterJoining-expAfterLoading)/(1000f);

				writeResultRecord(share.SPEED,rtn,rate, responseTime, throughputPerUnit, totalTime, completedPerUnit, notCompletedPerUnit, sleepTime, mili, nano, expTimeLJ, loadTime, joinTime, notStarted, notStopped, shortLife, longLife, FILE_PATH);
				
			} catch (InterruptedException e) {
				System.out.println("InterruptException:ClientWorkflow: ThreadClientCall: in the catch"+e.getMessage()+e.getCause());
				ExcpetionLogger(FILE_PATH, e.getMessage());
			}
			
				System.out.println("Per unit(ms): "+share.SPEED);
				System.out.println("ClientThreadRate: "+rate);
			    System.out.println("Real Produced Threads/Min: "+rtn);
				System.out.println("Thread Pool Size: "+ ct.length);
				System.out.println("Not started: "+notStarted);
				System.out.println("Not stopped: "+ notStopped);
				System.out.println("Sleep Time: "+sleepTime+" -> mili: "+mili+" -> Nano: "+nano);
			    System.out.println("Experiment time(Load...join) Sec: "+expTimeLJ);
			    System.out.println("Time to loading Sec: "+loadTime);
			    System.out.println("Time to Joining Sec: "+joinTime);
				System.out.println("Total runtime of All Threads Sec: "+totalTime/(1000f));
				System.out.println("Response Time Sec: "+responseTime);
				System.out.println("Long Life Threads: "+longLife);
				System.out.println("Short Life Threads : "+shortLife);
				System.out.println("Completed Per Unit : "+completedPerUnit);
				System.out.println("Not Completed Per Unit : "+notCompletedPerUnit);
				System.out.println("Throughput % : "+throughputPerUnit);
				System.out.println("");
				
				bag = null; random = null; ct = null;
				System.gc();
				rate =(rate < share.RATE_STEP)?(share.RATE_STEP):(rate+share.RATE_STEP);

		}
			long expStop = System.currentTimeMillis();
			System.out.println("Total Experiment time (Begin...End) Sec: "+(expStop-expStart)/1000f);
	}

	double linearMiliLoad(int rate){
		
		long sleepTime = new Double(share.SPEED/(double)rate).longValue();

		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			System.out.println("InterruptedException:ClientAgent: linearLoad: in the catch"+e.getMessage()+e.getCause());
			ExcpetionLogger(FILE_PATH, e.getMessage());
		}
		return sleepTime;
	}
	
	void linearMiliNanoLoad(long mili, int nano){

		try {
	
			Thread.sleep(mili,nano);
		} catch (InterruptedException e) {
			
			System.out.println("InterruptedException:ClientAgent: linearLoad: in the catch"+e.getMessage()+e.getCause());
			ExcpetionLogger(FILE_PATH, e.getMessage());
		}
	}
	
	private void writeOutputHeader(String FILE_PATH) {
		BufferedWriter out = null;

        try {
    		(new File(FILE_PATH.substring(0,FILE_PATH.lastIndexOf("\\")))).mkdirs();
			(new File(FILE_PATH)).createNewFile();
			out = new BufferedWriter(new FileWriter(FILE_PATH,true));
			out.write(share.FILE_OUTPUT_FORMAT);
			out.close();
		} catch (IOException e) {
			System.out.println("IOException:ClientWorkflow: writeOutputHeader: in the catch"+e.getMessage()+e.getCause());
			ExcpetionLogger(FILE_PATH, e.getMessage());
		}
	}
	
	private void writeResultRecord(long speed, int rtn, int rate, double responseTime, double throughputPerUnit, long totalTime, int completedPerUnit, int notCompletedPerUnit, double sleepTime, long mili, int nano, double expTimeLJ, double loadTime, double joinTime, int notStarted, int notStopped, long shortLife, long longLife, String FILE_PATH) {
		BufferedWriter out = null;

		try {out = new BufferedWriter(new FileWriter(FILE_PATH,true));

			out.write(speed+","+rtn+","+rate+","+responseTime+","+throughputPerUnit+","+totalTime+","+completedPerUnit+","+notCompletedPerUnit+","+sleepTime+","+mili+","+nano+","+rtn+","+expTimeLJ+","+loadTime+","+joinTime+","+notStarted+","+notStopped+","+shortLife+","+longLife+"\n");
			out.close();
		} catch (IOException e) {
			System.out.println("IOException:ClientWorkflow: writeResultRecord: in the catch"+e.getMessage()+e.getCause());
			ExcpetionLogger(FILE_PATH, e.getMessage());
		}
	}
	
	private void ExcpetionLogger(String FILE_PATH, String ex) {
		BufferedWriter out = null;

        try {
    		(new File(FILE_PATH.substring(0,FILE_PATH.lastIndexOf("\\")))).mkdirs();
			(new File(FILE_PATH)).createNewFile();
			out = new BufferedWriter(new FileWriter(FILE_PATH,true));
			out.write(ex+"\n");
			out.close();
		} catch (IOException e) {
			System.out.println("IOException:ClientWorkflow: writeOutputHeader: in the catch"+e.getMessage()+e.getCause());
			ExcpetionLogger(FILE_PATH, e.getMessage());
		}
	}

}

class ClientThread extends Thread {
	
	private TupleSpace bag;
	private long start; 
	private long stop;
	private com.tilab.wade.performer.Subflow sf;
	private int input;
	private int index;
	private ClientWorkflow wc;
	

	public ClientThread(ClientWorkflow wc, TupleSpace ts, int index, String SUBFLOW, String SUBFLOWAGENT){ 
		this.wc = wc;
		this.bag = ts;
		this.setStart(0);
		this.setStop(0);
		this.index = index;
		this.sf = new com.tilab.wade.performer.Subflow(SUBFLOW);
//		sf.setPerformer(SUBFLOWAGENT);
		sf.setPerformer(ContainerManagement.getContainer(SUBFLOWAGENT, SUBFLOW) );
		sf.fill("input", input);
		sf.setAsynch(false);
	}

  public void run () {
		
//		try {
			setStart(System.currentTimeMillis());
			wc.performCallback(sf);
			setStop(System.currentTimeMillis());
			bag.out("result"+index, ((Long)(getStop()-getStart())).longValue());
//		} catch (Exception e){
//			System.out.println("ClientThread: run: in the catch:");
//		}
	}

	public void setParams(int input){
		this.input = input;
	}

	public void setStop(long stop) {
		this.stop = stop;
	}

	public long getStop() {
		return stop;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getStart() {
		return start;
	}
}
class testThread extends Thread {
	static int a = 0;
	
	 public void run () {
		 a++;
		 System.out.println("New thread #"+a);
          for(;;){
              try {
            	  Thread.sleep(10000);
              } catch (Exception e){
            	  System.out.println(e);
              }
          }
	 }
}

 class DateUtils {
	 
  public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

  public static String now() {
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
    return sdf.format(cal.getTime());

  }

  public static void  main(String arg[]) {
    System.out.println("Now : " + DateUtils.now());
  }
}

