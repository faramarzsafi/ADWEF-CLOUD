package common;

import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import javax.realtime.PeriodicParameters;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;

import org.apache.commons.collections.bag.SynchronizedBag;
import org.w3c.dom.Document;

import test.common.testerAgentControlOntology.Exit;
import tuplespace.TupleSpace;

import com.tilab.wade.performer.CodeExecutionBehaviour;
import com.tilab.wade.performer.FormalParameter;
import com.tilab.wade.performer.Subflow;
import com.tilab.wade.performer.WorkflowBehaviour;
import com.tilab.wade.performer.layout.ActivityLayout;
import com.tilab.wade.performer.layout.MarkerLayout;
import com.tilab.wade.performer.layout.WorkflowLayout;
import common.tools.Audio;

@WorkflowLayout(exitPoints = {@MarkerLayout(position="(410,426)", activityName="Reply"), @MarkerLayout(position="(408,744)", activityName="Assign8") }, activities={@ActivityLayout(position="(331,276)", name="WorkflowClientCodeActivity1"), @ActivityLayout(position="(375,236)", name="Workflow"), @ActivityLayout(position="(374,340)", name="Reply"), @ActivityLayout(position="(520,406)", name="Sequence3"), @ActivityLayout(position="(355,406)", name="Sequence2"), @ActivityLayout(position="(179,404)", name = "Sequence1"), @ActivityLayout(position="(377,267)", name="Flow"), @ActivityLayout(position="(328,219)", name="Assign"), @ActivityLayout(position="(378,347)", name = "Assign7"), @ActivityLayout(isDecisionPoint = true, position="(647,260)", name="If"), @ActivityLayout(position="(374,573)", name="Assign7"), @ActivityLayout(position="(745,371)", name="Assign6"), @ActivityLayout(position="(629,372)", name="Assign5"), @ActivityLayout(position="(515,371)", name="Assign4"), @ActivityLayout(position="(374,371)", name="Assign3"), @ActivityLayout(position="(222,372)", name="Assign2"), @ActivityLayout(position="(371,193)", name = "Assign1"), @ActivityLayout(size = "(102,50)", position="(389,172)", name = "Receive")})
public class WorkflowClient extends WorkflowBehaviour {

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

	//	@FormalParameter(mode=FormalParameter.OUTPUT)
	//	private int output;

	String FILE_PATH;
	int rate;
//	boolean loopFinished;
	ExperimentRecord er;

	private boolean loopFinished; 

	private void defineActivities() {
		CodeExecutionBehaviour workflowClientCodeActivity1Activity = new CodeExecutionBehaviour(
				WORKFLOWCLIENTCODEACTIVITY1_ACTIVITY, this);
		registerActivity(workflowClientCodeActivity1Activity, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {

	}
	
	public static void main(String[] args) {
		
		WorkflowClient nwc = new WorkflowClient();
		try {
			
			nwc.RATE_START = 50;
			nwc.RATE_STEP = 500;
			nwc.MAXRATE = 500;
			nwc.SPEED = 1000;
			nwc.BULK = 2;
			nwc.WORKFLOW_STYLE=10;
			nwc.executeWorkflowClientCodeActivity1();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void executeWorkflowClientCodeActivity1() throws Exception {
		
		System.out.println("Starting client Side...");
		FILE_PATH = share.getFilePath(BULK, WORKFLOW_STYLE, NUMBER_OF_AGENTS, NUMBER_OF_SERVERS, SPEED, MAXRATE, RATE_START, RATE_STEP);
		share.writeOutputHeader((new File(FILE_PATH)));

		rate = RATE_START;
		while (rate <= MAXRATE){
			
			delayBetweenExperiments(share.DELAY_BETWEEN_EXPERIMENTS);
			System.gc();

			er = new ExperimentRecord(SPEED, RATE_START, RATE_STEP, MAXRATE, rate);
			er.setVMSize(share.MESSAGE.length);

			System.out.print("\n\n");
			System.out.println("Start_Time: "+share.Hour());
			System.out.println("Start_Rate: "+rate+" ... "+MAXRATE+" ,step="+RATE_STEP);
			System.out.println("Message_Size = "+er.getVMSize()/1000+" kb");
			System.out.print("\n");
			System.out.println("heapSize = "+Runtime.getRuntime().totalMemory()/1000000); 
			System.out.println("heapMaxSize = "+Runtime.getRuntime().maxMemory()/1000000); 
			System.out.println("heapFreeSize = "+Runtime.getRuntime().freeMemory()/1000000); 

			System.gc();
			
			er.startDate = System.currentTimeMillis();

			if (BULK == 1)
				new Exception("inlineThreadClient:BULK bulkRequestMode METHOD IS INACTIVE AT THE MOMENT");
			else if (BULK == 2)
				perUnitRequestMode();

			er.expAfterLoading = System.currentTimeMillis();
			
			er.allThreadFinished.acquire();//wait for all threads to return

			er.expAfterJoining = System.currentTimeMillis();
			er.endDate = er.expAfterJoining;
			er.showXRtnMarker(true);
			er.expAnalyzer();
			er.showExpResult();
			er.saveExpResultToFile(new File(FILE_PATH));

			System.out.print("\n");

			er = null; 
			System.gc();
			rate =(rate < RATE_STEP)?(RATE_STEP):(rate+RATE_STEP);
		}

		playBeep(1500);
		System.gc();

		System.out.println("Finishing client Side...");
	}

	void perUnitRequestMode(){
		try {
			er.sleepTime = (double)SPEED/(double)rate;
			er.mili = (long)er.sleepTime;
			if (er.mili==0) 
				throw new Exception("er.sleepTime cannot be less than zero");
			PeriodicParameters pp = new PeriodicParameters(
			      new RelativeTime(er.mili, 0));
			RTPeriodicClient rtc = new RTPeriodicClient(this, pp);
			rtc.start();
		} catch (Exception e) {
			System.out.println("WorkflowClient:perUnitRequestMode:Catch: "+e.getMessage()+e.getCause());
			e.printStackTrace();
		}

	}
	
	void performCallback(Subflow s) throws Exception{
		try {
			performSubflow(s);
		} catch (Exception e) {
			System.out.println("WorkflowClient:performCallback:in catch: "+e.getMessage()+e.getCause());
			e.getStackTrace();
			throw e;
		}
	}

	void delayBetweenExperiments(long delay){
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			System.out.println("WorkflowClient:delayBetweenExperiments: in catch"+e.getMessage()+e.getCause());
		}
	}
	
	private void playBeep(int nop) {
		Audio aud = new Audio();
		aud.repeatPlay(nop);
	}
	synchronized private void setloopFinished(boolean b) {
		loopFinished = b;
	}
	synchronized private  boolean getLoopFinished() {
		return loopFinished;
	}
	
	class RTPeriodicClient extends RealtimeThread {

		Random random;
		int index = 0;
		WorkflowClient wfc;

		RTPeriodicClient(WorkflowClient wfc, PeriodicParameters pp){
			super(null, pp);
			random = new Random();
			this.wfc = wfc; 
		}
		
		public void run(){
			try {			
				while(((System.currentTimeMillis()-er.startDate)<er.speed)&&(index<rate)){
					int inpt = (int)random.generateNumber_70_30();
					ClientThread ct = new ClientThread(wfc, index, SUBFLOW, SUBFLOWAGENT);
					ct.setInputParams(inpt);
					er.addThread(ct);
					ct.start();
					waitForNextPeriod();
					index++;
				}
				for (int i=0;i<index;i++) er.finishedThread.acquire();
				er.allThreadFinished.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	class ClientThread extends Thread {

		private int input;
		private com.tilab.wade.performer.Subflow sf;
		private int inaddThreadput;
		private int index;
		private WorkflowClient wfc;
		private boolean finished = false;

		public ClientThread(WorkflowClient wfc, int index, String SUBFLOW, String SUBFLOWAGENT){ 
			this.wfc = wfc;
			this.index = index;
			this.sf = new com.tilab.wade.performer.Subflow(SUBFLOW);
			//		sf.setPerformer(SUBFLOWAGENT);
			sf.setPerformer(ContainerManagement.getContainer(SUBFLOWAGENT, SUBFLOW));
			sf.setAsynch(false);
		}

		public void run () {
			
			try {
				ThreadRecord tr = new ThreadRecord(0,0);
				setVariableMessage();
				tr.setStart(System.currentTimeMillis());
				
				wfc.performCallback(sf);
				
				tr.setStop(System.currentTimeMillis());
				tr.setInput(input);
				er.bag.out(Integer.toString(index), tr);
				er.finishedThread.release();//first signals then setFinished 
				setFinished(true);
				er.clearFinishedThreads();
			} catch (Exception e) {
				System.out.println("ClientThread:run:in catch: "+e.getMessage()+e.getCause());
				e.printStackTrace();
			}
		}
		
	  	public void setVariableMessage() {
			sf.fill("VARIABLE_MESSAGE", share.MESSAGE);
		}

		public void setInputParams(int input){
			this.input = input;
			sf.fill("input", input);
		}

		synchronized public boolean isFinished() {
			return finished;
		}
		synchronized public void setFinished(boolean b) {
			finished = b;
		}
	}
	
	class ThreadRecord{
		
		private long startDate;
		private long stopDate;
		private int input;
		ThreadRecord(long start, long stop){
			startDate = start;
			stopDate = stop;
		}

		public void setStop(long stop) {
			this.stopDate = stop;
		}
		public long getStop() {
			return stopDate;
		}
		public void setStart(long start) {
			this.startDate = start;
		}
		public long getStart() {
			return startDate;
		}
		public int getInput() {
			return input;
		}
		public void setInput(int input) {
			this.input=input;
		}
	}
	
	public class ExperimentRecord {

		public Semaphore allThreadFinished;
		public Semaphore finishedThread;
		public int poolSize = 0;
		private LinkedList threadList;
		private int rtn = 0;
		private int xRtn = 0;
		public long expAfterLoading = 0;
		public long expAfterJoining = 0;
		public long totalTime = 0; 
		public int completedPerUnit = 0; 
		public int notCompletedPerUnit = 0; 
		public long lifeLength = 0; 
		public long longLife = 0; 
		public long shortLife = 0;
		public long endDate = 0;
		public long startDate = 0;
		public double sleepTime = 0;
		public long mili = 0;
		public int nano = 0;
		public long speed;
		public long messageNumber;
		public long notStarted;
		public long notStopped;
		public int rate;
		public int rate_start;
		public int rate_step;
		public int maxrate;
		TupleSpace bag;
		int vmSize;//variable message size
		
		public ExperimentRecord(long SPEED, int RATE_START, int RATE_STEP,
				int MAXRATE, int rate) {
			this.rate = rate;
			this.speed = SPEED;
			this.rate_start = RATE_START;
			this.rate_step = RATE_STEP;
			this.maxrate = MAXRATE;
			threadList = new LinkedList();
			bag = new TupleSpace();
			finishedThread = new Semaphore(0, true);
			allThreadFinished = new Semaphore(0, true);
		}
		
		public int getVMSize() {
			return vmSize;
		}

		public void setVMSize(int length) {
			vmSize = length;
		}

		public float getResponseTime() {
			//Converted to Sec by /1000f
			return (totalTime/rtn)/1000f;
		}

		public float getThroughputPerUnit() {
			return (completedPerUnit*100f)/(completedPerUnit+notCompletedPerUnit);
		}

		public float getLoadTime() {
			return (expAfterLoading-startDate)/(1000f);
		}

		public float getExpTimeLoadToJoin() {
			return (expAfterJoining-startDate)/(1000f);
		}

		public float getJoinTime() {
			return (expAfterJoining-expAfterLoading)/(1000f);		
		}

		public float getAllThreadTotalTime() {
			// time converted to second .../(1000f)
			return totalTime/(1000f);
		}
		
		synchronized public void clearFinishedThreads() {
			xRtn++;
			showXRtnMarker(false);
			for (int i=0; i < threadList.size();i++){
				if (((ClientThread)(threadList.get(i))).isFinished())
					threadList.remove(i);
			}
		}

		synchronized public void addThread(ClientThread clientThread) {
			threadList.add(clientThread);
			rtn++;
		}
		
		synchronized public int getRtn() {
			return rtn;
		}
		
		synchronized public void showXRtnMarker(boolean last) {
			if (last==false)
				System.out.print(((xRtn%100)==0)?". ["+(xRtn)+"]\n":".");
			else 
				System.out.print(((xRtn%100)!=0)?" ["+(xRtn)+"]":"");
		}

		public void expAnalyzer() {
			
			int notStarted = 0; int notStopped = 0; int numberOfMessaging = 0;
			totalTime = 0; completedPerUnit = 0; notCompletedPerUnit = 0;
			notStarted = 0; notStopped = 0; shortLife =0; longLife = 0;
			try {
				
				for(int index=0; index<rtn; index++){
					
					ThreadRecord tr = (ThreadRecord)bag.in(Integer.toString(index));
					totalTime += tr.getStop()-tr.getStart();
					lifeLength =  tr.getStop() - startDate; 
		
					if (lifeLength >= speed)
						longLife++;
					else
						shortLife++;
		
					if (tr.getStart()<=0) notStarted++;
		
					if ((tr.getStart()>0)&&(tr.getStop()<=0)) notStopped++;
		
					if ((tr.getStop()-startDate)<speed) completedPerUnit++;
					
					if (((tr.getStart()-startDate)<speed)&&((tr.getStop()-startDate)>= speed))
						notCompletedPerUnit++;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void showExpResult(){
			System.out.print("\n");
			System.out.println("TimeUnit(ms): "+speed);
			System.out.println("ClientThreadRate: "+rate);
			System.out.println("RealThreadsNumber(RTN)/Unit: "+getRtn());
//			System.out.println("ThreadPoolSize: "+ poolSize);
			System.out.println("VariableMessageSize: "+ vmSize);
//			System.out.println("NotStarted: "+notStarted);
//			System.out.println("NotStopped: "+ notStopped);
			System.out.println("SleepTime: "+sleepTime+" -> mili: "+mili+" -> Nano: "+nano);
//			System.out.println("ExperimentTime(Sec)(Load..join): "+getExpTimeLoadToJoin());
//			System.out.println("LoadingTime(Sec): "+getLoadTime());
//			System.out.println("JoiningTime(Sec): "+getJoinTime());
			System.out.println("AllThreadsTotalTime(Sec): "+getAllThreadTotalTime());
//			System.out.println("LongLifeThreads: "+longLife);
//			System.out.println("ShortLifeThreads : "+shortLife);
			System.out.println("CompletedPerUnit : "+completedPerUnit);
			System.out.println("NotCompletedPerUnit : "+notCompletedPerUnit);
			System.out.println("ResponseTime(Sec): "+getResponseTime());
			System.out.println("Throughput(%) : "+getThroughputPerUnit());
		}
		
		public void saveExpResultToFile(File filePath){

			BufferedWriter out = null;

			try {out = new BufferedWriter(new FileWriter(filePath.getPath(),true));

			out.write(
					speed+","+poolSize+","+vmSize+","+getRtn()+","+rate+","+getResponseTime()+","
					+getThroughputPerUnit()+","+totalTime+","+completedPerUnit+","+notCompletedPerUnit+","+sleepTime+","
					+mili+","+nano+","+getExpTimeLoadToJoin()+","+getLoadTime()+","+getJoinTime()+","
					+notStarted+","+notStopped+","+shortLife+","+longLife+","+messageNumber+","
					+startDate+","+endDate+"\n");
			out.close();
			} catch (IOException e) {
				System.out.println("ExperimentRecord:saveExpResultToFile:in catch: "+e.getMessage()+e.getCause());

			}
		}
	}
}