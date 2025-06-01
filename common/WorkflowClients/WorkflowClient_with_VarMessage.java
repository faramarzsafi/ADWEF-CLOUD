package common.WorkflowClients;

import java.awt.List;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;

import javax.realtime.PeriodicParameters;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;

import org.w3c.dom.Document;

import tuplespace.TupleSpace;
import com.tilab.wade.performer.Subflow;
import com.tilab.wade.performer.layout.MarkerLayout;
import com.tilab.wade.performer.layout.ActivityLayout;
import com.tilab.wade.performer.layout.WorkflowLayout;
import com.tilab.wade.performer.CodeExecutionBehaviour;
import com.tilab.wade.performer.FormalParameter;
import com.tilab.wade.performer.WorkflowBehaviour;
import common.ContainerManagement;
import common.Random;
import common.share;


@WorkflowLayout(exitPoints = {@MarkerLayout(position="(410,426)", activityName="Reply"), @MarkerLayout(position="(408,744)", activityName="Assign8") }, activities={@ActivityLayout(position="(331,276)", name="WorkflowClientCodeActivity1"), @ActivityLayout(position="(375,236)", name="Workflow"), @ActivityLayout(position="(374,340)", name="Reply"), @ActivityLayout(position="(520,406)", name="Sequence3"), @ActivityLayout(position="(355,406)", name="Sequence2"), @ActivityLayout(position="(179,404)", name = "Sequence1"), @ActivityLayout(position="(377,267)", name="Flow"), @ActivityLayout(position="(328,219)", name="Assign"), @ActivityLayout(position="(378,347)", name = "Assign7"), @ActivityLayout(isDecisionPoint = true, position="(647,260)", name="If"), @ActivityLayout(position="(374,573)", name="Assign7"), @ActivityLayout(position="(745,371)", name="Assign6"), @ActivityLayout(position="(629,372)", name="Assign5"), @ActivityLayout(position="(515,371)", name="Assign4"), @ActivityLayout(position="(374,371)", name="Assign3"), @ActivityLayout(position="(222,372)", name="Assign2"), @ActivityLayout(position="(371,193)", name = "Assign1"), @ActivityLayout(size = "(102,50)", position="(389,172)", name = "Receive")})
public class WorkflowClient_with_VarMessage extends WorkflowBehaviour {

	public static final String WORKFLOWCLIENTCODEACTIVITY1_ACTIVITY = "WorkflowClientCodeActivity1";

	@FormalParameter
	private String SUBFLOW;
	@FormalParameter
	private String SUBFLOWAGENT;
	
	@FormalParameter
	private int MAXRATE;
	@FormalParameter
	private int RATE_START;
	@FormalParameter
	private int RATE_STEP;
	
	@FormalParameter
	private int POOL_SIZE;
	
	@FormalParameter
	private long SPEED;
	
	@FormalParameter
	private int WORKFLOW_STYLE;		
	
	@FormalParameter
	private int NUMBER_OF_SERVERS;
	
	@FormalParameter
	private int NUMBER_OF_AGENTS;
	
	@FormalParameter
	private int BULK;
	
	String FILE_PATH;
	
	private void defineActivities() {
		CodeExecutionBehaviour workflowClientCodeActivity1Activity = new CodeExecutionBehaviour(
				WORKFLOWCLIENTCODEACTIVITY1_ACTIVITY, this);
		registerActivity(workflowClientCodeActivity1Activity, INITIAL_AND_FINAL);

	}
	private void defineTransitions() {
	}
	protected void executeWorkflowClientCodeActivity1() throws Exception {
		
		System.out.println("WorkflowClient: In the code activity: before threadClientCall");
		
		threadClientCall();
		
		System.out.println("WorkflowClient: In the code activity: After thread Client Call");
	}
	
	void performCallback(Subflow s){
		try {
			performSubflow(s);
		} catch (Exception e) {
			System.out.println("WorkflowClient: in the catch of performCallback: "+e.getMessage()+e.getCause());
			e.getStackTrace();
		}
	}
	
	void threadClientCall()  {
		
		System.out.println("WorkflowClient: In threadClientCall Starting ...");
		
//		if (NUMBER_OF_SERVERS == 0) NUMBER_OF_SERVERS = share.NUMBER_OF_SERVERS;
//		if (NUMBER_OF_AGENTS == 0) NUMBER_OF_AGENTS = share.NUMBER_OF_AGENTS;
//		if (POOL_SIZE == 0) POOL_SIZE = share.POOL_SIZE;
//		if (SPEED == 0) SPEED = share.SPEED;
//		
//		if (MAXRATE == 0) MAXRATE = share.MAXRATE;
//		if (RATE_START == 0) RATE_START = share.RATE_START;
//		if (RATE_STEP == 0) RATE_STEP = share.RATE_STEP;
//		
//		if(BULK ==0) BULK = share.BULK;
	
		FILE_PATH = share.getFilePath(BULK, WORKFLOW_STYLE, NUMBER_OF_AGENTS, NUMBER_OF_SERVERS, SPEED, MAXRATE, RATE_START, RATE_STEP);
		long expStart = System.currentTimeMillis();

		inlineThreadClient();
		
		long expStop = System.currentTimeMillis();
	    System.out.println("Total Experiment time (Begin...End) Sec: "+(expStop-expStart)/1000f);
}

	private void inlineThreadClient() {
		
		//=============================================
		VariableMessage vm = new VariableMessage();
//		Document varMessage = vm.parseXmlFile("C:\\safi\\50kb.xml", false);
		Document varMes = vm.parseXmlFile("/opt/temp/varMessages/50kb.xml", false);
		//==============================================

		TupleSpace bag = null;
		common.Random random = null;
		LinkedList threadList = null;

		int rtn = 0; 		int rate; double responseTime = 0; double throughputPerUnit = 0; 
		long totalTime = 0; int completedPerUnit = 0; int notCompletedPerUnit = 0; 
		double expTimeLJ = 0; double loadTime = 0;	double joinTime = 0;
		long lifeLength = 0; long longLife = 0; long shortLife = 0;
		long expAfterLoading = 0; long expAfterJoining = 0;
		double sleepTime = 0;
		long mili = 0;
		int nano = 0;
		
		int notStarted = 0; int notStopped = 0; int numberOfMessaging = 0;
		long StartDate; long EndDate;
		
		share.writeOutputHeader(new File(FILE_PATH));
		
		rate = RATE_START;
		while (rate <= MAXRATE){
			
			try {//Delay before experiments
				Thread.sleep(share.DELAY_BETWEEN_EXPERIMENTS);// THIS IS TO MAKE A DELAY BETWEEN EXPs FROM MESSAGING POINT OF VIEW
			} catch (InterruptedException e) {
				System.out.println("From My Client: InterruptException:ClientWorkflow: ThreadClientCall: in the catch"+e.getMessage()+e.getCause());
				share.Logger(FILE_PATH, "From My Client: After Thread.sleep(share.DELAY_BETWEEN_EXPERIMENTS): InterruptedException "+e.getMessage());
			}
			System.gc();			
			bag = new TupleSpace();
			random = new common.Random();
			
			//------------------------------------------------------
			threadList = new LinkedList();
			
//			for (int count =0; count<POOL_SIZE; count++){
//				int inpt = (int)random.generateNumber_50_50();
//				threadList.add(new ClientThread(this, bag, count, SUBFLOW, SUBFLOWAGENT));
//				((ClientThread)threadList.get(count)).setParams(inpt);
//				((ClientThread)threadList.get(count)).setVariableMessage(doc);
//			}
			//-------------------------------------------------------
			
			System.out.println("\n \nStatring...time is: "+share.Hour());
			System.out.println("Statring...Rate: "+rate+" out of "+MAXRATE);

			rtn = 0;
			System.gc();
			
			long startTime = System.currentTimeMillis();
			StartDate = startTime;
			try {
				if (BULK == 1){
//					rtn = bulkRequestMode(startTime, SPEED, rate, threadList);
					System.out.println("BULK bulkRequestMode METHOD IS INACTIVE AT THE MOMENT");
				} else if (BULK == 2){
					sleepTime = (double)SPEED/(double)rate;
					mili = (long)sleepTime;
//					nano = (int) ((sleepTime*1000000f-(double)mili*1000000f));
					rtn = perUnitRequestMode(startTime, SPEED, rate, threadList, mili, varMes);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("WorkflowClient:sending requetsts"+ e.getMessage()+e.getCause());
			}
		
			expAfterLoading = System.currentTimeMillis();
			for(int i=0; i< rtn; i++){
				try {
					((ClientThread)threadList.get(i)).join();
				} catch (InterruptedException e) {
					System.out.println("From My Client: InterruptException:ClientWorkflow: ThreadClientCall: in the catch"+e.getMessage()+e.getCause());
					share.Logger(FILE_PATH, "From My Client: After Thread.join(): InterruptedException "+e.getMessage());
				}
			}
				expAfterJoining = System.currentTimeMillis();
				EndDate = expAfterJoining;
				totalTime = 0; completedPerUnit = 0; notCompletedPerUnit = 0;
				notStarted = 0; notStopped = 0; shortLife =0; longLife = 0;
				for(int i=0; i<rtn; i++){
					
					totalTime += (((ClientThread)threadList.get(i)).getStop()-((ClientThread)threadList.get(i)).getStart());
					
					lifeLength =  ((ClientThread)threadList.get(i)).getStop() - startTime; 
					if (lifeLength >= SPEED)
						longLife++;
					else
						shortLife++;
					
					if (((ClientThread)threadList.get(i)).getStart()<=0)
						notStarted++;
				
					if ((((ClientThread)threadList.get(i)).getStart()>0)&&(((ClientThread)threadList.get(i)).getStop()<=0))
						notStopped++;

					if (((((ClientThread)threadList.get(i)).getStop()-startTime) < SPEED)){
						completedPerUnit++;
					}
					if (((((ClientThread)threadList.get(i)).getStart()-startTime) < SPEED)&&
					((((ClientThread)threadList.get(i)).getStop()-startTime)>= SPEED))
						notCompletedPerUnit++;
				}
				
				responseTime = (totalTime/rtn)/1000f;//Converted to Sec by /1000
				throughputPerUnit = (completedPerUnit*100f)/(completedPerUnit+notCompletedPerUnit);
				loadTime = (expAfterLoading-startTime)/(1000f);
				expTimeLJ = (expAfterJoining-startTime)/(1000f);
				joinTime = (expAfterJoining-expAfterLoading)/(1000f);
				

				writeResultRecord(SPEED,POOL_SIZE, rtn, rate, responseTime, throughputPerUnit, totalTime, completedPerUnit, notCompletedPerUnit, sleepTime, mili, nano, expTimeLJ, loadTime, joinTime, notStarted, notStopped, shortLife, longLife, startTime, expAfterJoining, numberOfMessaging, StartDate, EndDate, FILE_PATH);
				System.out.println("TimeUnit(ms): "+SPEED);
				System.out.println("ClientThreadRate: "+rate);
			    System.out.println("RealThreadsNumber(RTN)/Unit: "+rtn);
				System.out.println("ThreadPoolSize: "+ threadList.size());
				System.out.println("NotStarted: "+notStarted);
				System.out.println("NotStopped: "+ notStopped);
				System.out.println("SleepTime: "+sleepTime+" -> mili: "+mili+" -> Nano: "+nano);
			    System.out.println("ExperimentTime(Sec)(Load..join): "+expTimeLJ);
			    System.out.println("LoadingTime(Sec): "+loadTime);
			    System.out.println("JoiningTime(Sec): "+joinTime);
				System.out.println("AllThreadsTotalTime(Sec): "+totalTime/(1000f));
				System.out.println("LongLifeThreads: "+longLife);
				System.out.println("ShortLifeThreads : "+shortLife);
				System.out.println("CompletedPerUnit : "+completedPerUnit);
				System.out.println("NotCompletedPerUnit : "+notCompletedPerUnit);
				System.out.println("ResponseTime(Sec): "+responseTime);
				System.out.println("Throughput(%) : "+throughputPerUnit);
				
				System.out.println("");
				
				bag = null; random = null; threadList = null;
				System.gc();
				rate =(rate < RATE_STEP)?(RATE_STEP):(rate+RATE_STEP);
		}
		
		for (int i=0; i<100;i++){ 
			System.out.println("Beeping...");
//			Toolkit.getDefaultToolkit().beep();
		}
		varMes = null;
		System.gc();
	}
	private void writeResultRecord(long speed, int poolSize, int rtn, int rate, double responseTime, double throughputPerUnit, long totalTime, int completedPerUnit, int notCompletedPerUnit, double sleepTime, long mili, int nano, double expTimeLJ, double loadTime, double joinTime, int notStarted, int notStopped, long shortLife, long longLife, long startTime, long expAfterJoining, int messageNumber, long StartDate, long EndDate, String FILE_PATH) {
		BufferedWriter out = null;

		try {out = new BufferedWriter(new FileWriter(FILE_PATH,true));

			out.write(speed+","+poolSize+","+rtn+","+rate+","+responseTime+","+throughputPerUnit+","+totalTime+","+completedPerUnit+","+notCompletedPerUnit+","+sleepTime+","+mili+","+nano+","+expTimeLJ+","+loadTime+","+joinTime+","+notStarted+","+notStopped+","+shortLife+","+longLife+","+messageNumber+","+StartDate+","+EndDate+"\n");
			out.close();
		} catch (IOException e) {
			System.out.println("IOException:ClientWorkflow: writeResultRecord: in the catch: "+e.getMessage()+e.getCause());
			share.Logger(FILE_PATH, e.getMessage());
		}
	}
	
	int bulkRequestMode(long startTime, long speed, int rate, LinkedList threadList) throws Exception{
		int rtn = 0;
//		while(((System.currentTimeMillis()-startTime)<speed)&&(rtn<rate)){
//			((ClientThread)threadList.get(rtn)).start();
//			rtn++ ;//Real Thread Number Counter
//			if (rtn>POOL_SIZE) {
//				throw (new Exception("rtn > POOL_SIZE exception in bulkRequestMode"));
//			}
//		}
		return rtn;
	}
	
	int perUnitRequestMode(long startTime, long speed, int rate, LinkedList threadList, long mili, Document varMessage) throws Exception{

		PeriodicParameters pp = new PeriodicParameters( new RelativeTime(mili, 0));
	    RTPeriodicClient rtc = new RTPeriodicClient(this, pp, threadList, startTime, speed, rate, varMessage);
	    rtc.start();
	    rtc.join();
////		if (rtc.getRtn()>POOL_SIZE) {
////			throw (new Exception("rtn > POOL_SIZE exception in perUnitRequestMode"));
//		}
	    return rtc.getRtn();
	}
	
	class RTPeriodicClient extends RealtimeThread {
		
		long startTime;
		long speed;
		int rate;
		LinkedList threadList;
		int count = 0;
		Document vm;
		WorkflowClient_with_VarMessage wfc;
		TupleSpace bag;
		Random random;
		
		RTPeriodicClient(WorkflowClient_with_VarMessage wfc, PeriodicParameters pp, LinkedList threadList, long startTime, long speed, int rate, Document variableMessage){
		    super(null, pp);
		    this.startTime = startTime;
		    this.speed = speed;
		    this.rate = rate;
		    this.threadList = threadList;
		    this.vm = variableMessage;
		    this.wfc = wfc;
			bag = new TupleSpace();
			random = new Random();
		}
		  
		public int getRtn() {
			return count;
		}

		public void run(){
			
			while(((System.currentTimeMillis()-startTime)<speed)&&(count<rate)){
				
				int inpt = (int)random.generateNumber_70_30();
				threadList.add(new ClientThread(wfc, bag, count, SUBFLOW, SUBFLOWAGENT));
				((ClientThread)threadList.get(count)).setParams(inpt);
				((ClientThread)threadList.get(count)).setVariableMessage(vm);
				((ClientThread)threadList.get(count)).start();
				
				count++ ;//Real Thread Number Counter
		        waitForNextPeriod();
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
		private WorkflowClient_with_VarMessage wc;
	
		public ClientThread(WorkflowClient_with_VarMessage wc, TupleSpace ts, int index, String SUBFLOW, String SUBFLOWAGENT){ 
			this.wc = wc;
			this.bag = ts;
			this.setStart(0);
			this.setStop(0);
			this.index = index;
			this.sf = new com.tilab.wade.performer.Subflow(SUBFLOW);
	//		sf.setPerformer(SUBFLOWAGENT);
			sf.setPerformer(ContainerManagement.getContainer(SUBFLOWAGENT, SUBFLOW));
			sf.setAsynch(false);
		}
	
	public void run () {
			
			try {
	
				setStart(System.currentTimeMillis());
				wc.performCallback(sf);
				setStop(System.currentTimeMillis());
				bag.out("result"+index, ((Long)(getStop()-getStart())).longValue());
				sf = null;
				System.gc();
			} catch (Exception e){
				System.out.println("ClientThread: in the catch: run: thread finished");
			}
		}
	
	  	public void setVariableMessage(Document doc) {
			sf.fill("VARIABLE_MESSAGE", doc);
		}
	
		public void setParams(int input){
			this.input = input;
			sf.fill("input", input);
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
}