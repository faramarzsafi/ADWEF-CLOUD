package common;

import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import javax.realtime.PeriodicParameters;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;

import tuplespace.TupleSpace;
import com.tilab.wade.performer.Subflow;
import com.tilab.wade.performer.layout.MarkerLayout;
import com.tilab.wade.performer.layout.ActivityLayout;
import com.tilab.wade.performer.layout.WorkflowLayout;
import com.tilab.wade.performer.CodeExecutionBehaviour;
import com.tilab.wade.performer.FormalParameter;
import com.tilab.wade.performer.WorkflowBehaviour;



@WorkflowLayout(exitPoints = {@MarkerLayout(position="(410,426)", activityName="Reply"), @MarkerLayout(position="(408,744)", activityName="Assign8") }, activities={@ActivityLayout(position="(331,276)", name="WorkflowClientCodeActivity1"), @ActivityLayout(position="(375,236)", name="Workflow"), @ActivityLayout(position="(374,340)", name="Reply"), @ActivityLayout(position="(520,406)", name="Sequence3"), @ActivityLayout(position="(355,406)", name="Sequence2"), @ActivityLayout(position="(179,404)", name = "Sequence1"), @ActivityLayout(position="(377,267)", name="Flow"), @ActivityLayout(position="(328,219)", name="Assign"), @ActivityLayout(position="(378,347)", name = "Assign7"), @ActivityLayout(isDecisionPoint = true, position="(647,260)", name="If"), @ActivityLayout(position="(374,573)", name="Assign7"), @ActivityLayout(position="(745,371)", name="Assign6"), @ActivityLayout(position="(629,372)", name="Assign5"), @ActivityLayout(position="(515,371)", name="Assign4"), @ActivityLayout(position="(374,371)", name="Assign3"), @ActivityLayout(position="(222,372)", name="Assign2"), @ActivityLayout(position="(371,193)", name = "Assign1"), @ActivityLayout(size = "(102,50)", position="(389,172)", name = "Receive")})
public class FuzzyWorkflowClient extends WorkflowBehaviour {

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
	String FUZZY_RND_FILE_PATH; 
	final ProcessRecord pRepos[] = new ProcessRecord[4];
	
	private void defineActivities() {
		CodeExecutionBehaviour workflowClientCodeActivity1Activity = new CodeExecutionBehaviour(
				WORKFLOWCLIENTCODEACTIVITY1_ACTIVITY, this);
		registerActivity(workflowClientCodeActivity1Activity, INITIAL_AND_FINAL);

	}
	private void defineTransitions() {
		
	}
	protected void executeWorkflowClientCodeActivity1() throws Exception {
		
		System.out.println("FuzzyWorkflowClient: In the code activity: before threadClientCall");
		
		threadClientCall();
		
		System.out.println("FuzzyWorkflowClient: In the code activity: After thread Client Call");
	}
	
	void performCallback(Subflow s){

		try {
			performSubflow(s);
		} catch (Exception e) {
			System.out.println("FuzzyWorkflowClient: in the catch of performCallback: "+e.getMessage()+e.getCause());
			e.getStackTrace();
		}
	}
	

	private void inlineThreadClient() throws Exception {
		
//		pRepos[0] = new ProcessRecord(1,"LoanTaking.hpd.l0.CentralizedWorkflow","CentralizedWorkflowAgent");
//		pRepos[1] = new ProcessRecord(6,"LoanTaking.hpd.l1.Sequence0","Sequence0_Agent");
		
		//Setup for HIPD method 
		pRepos[0] = new ProcessRecord(2,"LoanTaking.hipd.l0.FrequentPath1","FrequentPath1_Agent");
		pRepos[1] = new ProcessRecord(3,"LoanTaking.hipd.l1.Sequence0","Sequence0_Agent");
		pRepos[2] = new ProcessRecord(10,"LoanTaking.hpd.l2.Sequence0", "Sequence0_Agent");
		pRepos[3] = new ProcessRecord(16,"LoanTaking.hpd.l3.Sequence0", "Sequence0_Agent");

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

//			bag = new TupleSpace();
//			random = new common.Random();
			
			threadList = new LinkedList();
			
//			for (int count =0; count<POOL_SIZE; count++){
//				int inpt = random.generateNumber_50_50();
//				threadList.add(new ClientThread(this, bag, count, SUBFLOW, SUBFLOWAGENT));
//				((ClientThread)threadList.get(count)).setParams(inpt);
//			}
			
			System.out.println("\n\nStatring...time is: "+share.Hour());
			System.out.println("Statring...Rate: "+rate+" out of "+MAXRATE);

			rtn = 0;
			System.gc();
			
			long startTime = System.currentTimeMillis();
			StartDate = startTime;
			try {
				
				if (BULK == 1)
//					rtn = bulkRequestMode(startTime, SPEED, rate, threadList);
					System.out.println("BULK bulkRequestMode METHOD IS INACTIVE AT THE MOMENT");
				else if (BULK == 2){
					sleepTime = (double)SPEED/(double)rate;
					mili = (long)sleepTime;
//					nano = (int) ((sleepTime*1000000f-(double)mili*1000000f));
					rtn = perUnitRequestMode(startTime, SPEED, rate, threadList, mili);
					if (rtn ==0) throw new Exception("Rtn cannot be equal to or less than zero...");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("FuzzyWorkflowClient:sending requetsts"+ e.getMessage()+e.getCause());
			}
		
			expAfterLoading = System.currentTimeMillis();
			for(int i=0; i< rtn; i++){
				try {
					((ClientThread)threadList.get(i)).join();
					System.out.print(((i%50)==0)?" ["+i+"]\n":".");
				} catch (InterruptedException e) {
					System.out.println("From My Client: InterruptException:ClientWorkflow: ThreadClientCall: in the catch"+e.getMessage()+e.getCause());
					share.Logger(FILE_PATH, "From My Client: After Thread.join(): InterruptedException "+e.getMessage());
				}
			}
			System.out.println("");
		
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
				
				//log random bandwidth numbers for fuzzy
				fuzzyBandwidthLogRecord(rtn, threadList, FUZZY_RND_FILE_PATH);
				
				bag = null; random = null; threadList = null;
				System.gc();
				rate =(rate < RATE_STEP)?(RATE_STEP):(rate+RATE_STEP);
		}
		for (int i=0; i<10;i++){ 
			System.out.println("Beeping...");
			Toolkit.getDefaultToolkit().beep();
		}
		System.gc();
	}
	
	private void fuzzyBandwidthLogRecord(int rtn, LinkedList threadList, String path) {
		
		share.Logger(path, "date,hour,start(ms),bandwidth(rand/(100-expo)), bwExpo(lambada=0.05),FragGranul,FragProp, FragPropLevel\n");			
		for(int i=0; i<rtn; i++){
			
//			int rndInp = ((ClientThread)threadList.get(i)).getRandomInput();
			long start = ((ClientThread)threadList.get(i)).getStart();
			//note: bw = 100-bwExpo
			double bw = ((ClientThread)threadList.get(i)).getBandwidth();
			//note: bwExpo=100-bw
			double bwExpo = ((ClientThread)threadList.get(i)).getBandwidthExpo();
			int fg = ((ClientThread)threadList.get(i)).getFragGran();
			int fp = ((ClientThread)threadList.get(i)).getFragProp();
			int fpl = ((ClientThread)threadList.get(i)).getFragPropLevel();
			
			String out = share.Date()+","+share.Hour()+","+start+","+
			bw+","+bwExpo+","+fg+","+fp+","+fpl+"\n";
			share.Logger(FUZZY_RND_FILE_PATH, out);
		}
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
	
//	int bulkRequestMode(long startTime, long speed, int rate, LinkedList threadList) throws Exception{
//		int rtn = 0;
//		while(((System.currentTimeMillis()-startTime)<speed)&&(rtn<rate)){
//			((ClientThread)threadList.get(rtn)).start();
//			rtn++ ;//Real Thread Number Counter
//			if (rtn>POOL_SIZE) {
//				throw (new Exception("rtn > POOL_SIZE exception in bulkRequestMode"));
//			}
//		}
//		return rtn;
//	}
	

	void threadClientCall()  {
		
		System.out.println("FuzzyWorkflowClient: In threadClientCall Starting ...");
		
		FILE_PATH = share.getFilePath(BULK, WORKFLOW_STYLE, NUMBER_OF_AGENTS, NUMBER_OF_SERVERS, SPEED, MAXRATE, RATE_START, RATE_STEP);
		FUZZY_RND_FILE_PATH = FILE_PATH.substring(0, FILE_PATH.indexOf('.'))+"_RAND_BW_NUMBERS"+".csv";
		long expStart = System.currentTimeMillis();

		try {
			inlineThreadClient();
		} catch (Exception e) {
			System.out.println("threadClientCall: Exception: "+e.getMessage()+e.getCause());
			e.printStackTrace();
		}
		
		long expStop = System.currentTimeMillis();
	    System.out.println("Total Experiment time (Begin...End) Sec: "+(expStop-expStart)/1000f);
}
	
	int perUnitRequestMode(long startTime, long speed, int rate, LinkedList threadList, long mili) throws InterruptedException {
		
		FuzzySugenoDynamicBandwidth fsdb = new FuzzySugenoDynamicBandwidth();
		PeriodicParameters pp = new PeriodicParameters( new RelativeTime(mili, 0));
	    RTPeriodicClient rtc = new RTPeriodicClient(this, fsdb, pp, threadList, startTime, speed, rate);
	    rtc.start();
		rtc.join();
	    return rtc.getRtn();
	}

	
	class RTPeriodicClient extends RealtimeThread {
		int count;
		long startTime;
		long speed;
		int rate;
		LinkedList threadList;
		FuzzyWorkflowClient fwc;
		FuzzySugenoDynamicBandwidth fsdb;

		RTPeriodicClient(FuzzyWorkflowClient fwc, FuzzySugenoDynamicBandwidth fsdb, 
				PeriodicParameters pp, LinkedList threadList, long startTime, long speed, int rate){
		    super(null, pp);
		    this.startTime = startTime;
		    this.speed = speed;
		    this.rate = rate;
		    this.threadList = threadList;
		    this.fwc = fwc;
		    this.fsdb = fsdb;
			this.count = 0;
		}

		public int getRtn() {
			return count;
		}

		public void run(){
			
			TupleSpace bag = new TupleSpace();
			Random randInp = new Random();
			
			try {	
				while(((System.currentTimeMillis()-startTime)<speed)&&(count<rate)){
					
//					double inpt = randInp.generateNumber_50_50();
					double inpt = randInp.generateNumber_70_30();
					int fp = fsdb.fragmentProportionality(pRepos, ContainerManagement.numOfServers);
					int pl = fsdb.proportionalLevel(pRepos, fp);

					//random bandwidth generation
					double bw = randInp.generateNumber_50_50();
					
					//exponential bandwidth generation
//					double exp = randInp.exponential(rate);
//					double bw = 100-exp;
					
					bw = (bw==100)?99.99:bw;
					bw = (bw==0)?0.1:bw;
					int fg = 3;
//					int fg = fsdb.fuzzyGranularity(pRepos, bw, pl);
					String subFlow = pRepos[fg].getSubflow();
					String subFlowAgent = pRepos[fg].getSubflowAgent();
					threadList.add(new ClientThread(fwc, bag, count, subFlow, subFlowAgent));
					((ClientThread)threadList.get(count)).setParams((int)inpt);
					((ClientThread)threadList.get(count)).setBandwidth(bw);
					((ClientThread)threadList.get(count)).setBandwidthExpo(0);
					((ClientThread)threadList.get(count)).setFragProp(fp);
					((ClientThread)threadList.get(count)).setFragPropLevel(pl);
					((ClientThread)threadList.get(count)).setFragGran(fg);
					((ClientThread)threadList.get(count)).start();
					count++ ;//Real Thread Number Counter
			        waitForNextPeriod();
				}
			} catch (Exception e) {
				System.out.println("RTPeriodicClient: run: "+e.getMessage()+e.getCause());
				e.printStackTrace();
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
		private FuzzyWorkflowClient wc;
		private double bw;
		private double exp;
		private int fg;
		private int pl;
		private int fp;
		
		
		public ClientThread(FuzzyWorkflowClient fuzzywc, TupleSpace ts, int index, String SUBFLOW, String SUBFLOWAGENT){
			
			this.wc = fuzzywc;
			this.bag = ts;
			this.setStart(0);
			this.setStop(0);
			this.index = index;
			this.sf = new com.tilab.wade.performer.Subflow(SUBFLOW);
	//		sf.setPerformer(SUBFLOWAGENT);
//			System.out.println("workflow Container is: -> "+ContainerManagement.getContainer(SUBFLOWAGENT, SUBFLOW));
			sf.setPerformer(ContainerManagement.getContainer(SUBFLOWAGENT, SUBFLOW));
			sf.setAsynch(false);
		}
	
	public void run () {
			
			try {
				setStart(System.currentTimeMillis());
				wc.performCallback(sf);
				setStop(System.currentTimeMillis());
				bag.out("result"+index, ((Long)(getStop()-getStart())).longValue());
			} catch (Exception e){
				System.out.println("ClientThread: in the catch: run: thread finished");
			}
		}
	
		public void setFragGran(int fg) {
			this.fg = fg;
		}
		public int getFragGran(){
			return this.fg;
		}
		
		
		public void setFragPropLevel(int pl) {
			this.pl = pl;
		}
		public int getFragPropLevel(){
			return this.pl;
		}
		
		
		public void setFragProp(int fp) {
			this.fp = fp;
		}
		public int getFragProp(){
			return this.fp;
		}
		
		
		public void setBandwidthExpo(double exp) {
			this.exp = exp;
		}	
		public double getBandwidthExpo(){
			return exp;
		}
	  
		
		public void setBandwidth(double bw){
			this.bw = bw;
		}
		
		public double getBandwidth(){
			return this.bw;
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
		public int getRandomInput() {
			return input;
		}
	}
}