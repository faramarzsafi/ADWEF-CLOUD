package LoanTaking.hipd.l1;

import com.tilab.wade.performer.RouteActivityBehaviour;
import com.tilab.wade.performer.Transition;
import com.tilab.wade.performer.layout.MarkerLayout;

import com.tilab.wade.performer.layout.ActivityLayout;
import com.tilab.wade.performer.layout.WorkflowLayout;
import com.tilab.wade.performer.CodeExecutionBehaviour;
import com.tilab.wade.performer.FormalParameter;
import com.tilab.wade.performer.WorkflowBehaviour;

import common.ContainerManagement;
import common.share;

@WorkflowLayout(exitPoints = {@MarkerLayout(position="(408,744)", activityName="Assign8") }, activities={@ActivityLayout(position="(375,236)", name="Workflow"), @ActivityLayout(position="(520,406)", name="Sequence3"), @ActivityLayout(position="(355,406)", name="Sequence2"), @ActivityLayout(position="(179,404)", name = "Sequence1"), @ActivityLayout(position="(377,267)", name="Flow"), @ActivityLayout(position="(328,219)", name="Assign"), @ActivityLayout(position="(378,347)", name = "Assign7"), @ActivityLayout(isDecisionPoint = true, position="(647,260)", name="If"), @ActivityLayout(position="(374,573)", name="Assign7"), @ActivityLayout(position="(745,371)", name="Assign6"), @ActivityLayout(position="(629,372)", name="Assign5"), @ActivityLayout(position="(515,371)", name="Assign4"), @ActivityLayout(position="(374,371)", name="Assign3"), @ActivityLayout(position="(222,372)", name="Assign2"), @ActivityLayout(position="(371,193)", name = "Assign1")})
public class FrequentPath1 extends WorkflowBehaviour {

	public static final String WORKFLOW_ACTIVITY = "Workflow";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	com.tilab.wade.performer.Subflow Assign5SubFlow;
	int scoreA;
	int reportA;
	int reportB;
	int claim;
	boolean approval;
	
	
	private void defineActivities() {
		CodeExecutionBehaviour workflowActivity = new CodeExecutionBehaviour(
				WORKFLOW_ACTIVITY, this);
		registerActivity(workflowActivity, INITIAL_AND_FINAL);
	}

	private void defineTransitions() {
	}
	
	protected void executeWorkflow() throws Exception {

		System.gc();
		Receive1();
		Assign1();
		Flow1();
		Switch();
		Reply();
		System.gc();
	}
	
	public void Receive1() {
//		System.out.println("Receive: "+input);
		claim = input;
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Receive:1 in the catch: "+input);
		}
	}
	
	public void Assign1(){
//		System.out.println("Assign1: "+input);
		boolean approval = false;
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("Assign2: in the catch: "+input);
		}
	}
	
	public void Flow1() throws InterruptedException{
//		System.out.println("Flow: "+input);
		
		Runnable seq1 = new Sequence1(this);
		Thread seqThread1 = new Thread(seq1);
		Runnable seq2 = new Sequence2 (this);
		Thread seqThread2 = new Thread(seq2);
		
		seqThread1.start();
		seqThread2.start();
		
		seqThread1.join();
		seqThread2.join();

	}
	
	public void Switch() {
//		System.out.println("If: "+input);
		if (((input>=1) && (input <=10))||((input>=21)&&(input<=100)))
			Assign4();
		else //	((input>10) && (input <=20))
			Assign5();
		
	}

	public void Assign2(){
//		System.out.println("Assign2: "+input);
		scoreA = score(reportA);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("Assign2: in the catch: "+input);
		}
	}
	
	public void Assign3(){
//		System.out.println("Assign3: "+input);
		scoreA = score(reportB);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("Assign3: in the catch: "+input);
		}
	}
	
	public void Assign4(){
//		System.out.println("Assign4: "+input);
		approval = true;
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("Assign4: in the catch: "+input);
		}
	}
	
	public void Assign5(){
//		System.out.println("Assign5: "+input);
		approval = false;
		
		Assign5SubFlow = new com.tilab.wade.performer.Subflow("LoanTaking.hipd.l1.Assign5");
		Assign5SubFlow.fill("input", input);
		Assign5SubFlow.fill("VARIABLE_MESSAGE", share.MESSAGE);
//		Assign5SubFlow.setPerformer("Assign5_Agent");
		Assign5SubFlow.setPerformer(ContainerManagement.getContainer("Assign5_Agent", "LoanTaking.hipd.l1.Assign5"));
		try {
			performSubflow(Assign5SubFlow);
		} catch (Exception e) {
			System.out.println("Assign5SubFlow Invocation"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void Reply() {
//		System.out.println("Reply: "+input);
		//response = approval;
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("Reply: in the catch: "+input);
		}
	}
	

	public void Invoke1(){
//		System.out.println("Invoke1: "+input);
		
		try {
			//call webServiceA.process(claim);
			callWebService("A", claim);
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("Invoke1: in the catch: "+input);
		}
		
	}
	
	public void Invoke2(){
//		System.out.println("Invoke2: "+input);
		callWebService("B", claim);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("Invoke2: in the catch: "+input);
		}
		
	}
	
	public void Receive2(){
//		System.out.println("Receive2: "+input);
		//delay in calling web service is 0 in this case.
		reportA = input; 
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("Receive2: in the catch: "+input);
		}
		
	}
	
	public void Receive3(){
//		System.out.println("Receive3: "+input);
		reportB = input;
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("Receive3: in the catch: "+input);
		}
	}
	
	private int callWebService(String string, int claim2) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private int score(int report) {
		// TODO Auto-generated method stub
		return 0;
	}
}

class Sequence  {
	protected FrequentPath1 cw;
	public Sequence(FrequentPath1 cw){
		this.cw = cw;
	}
}

class Sequence1 extends Sequence implements Runnable{

	public Sequence1(FrequentPath1 cw) {
		super(cw);
		this.cw = super.cw;
	}

	public void run() {
		cw.Invoke1();
		cw.Receive2();
		cw.Assign2();
	}
}

class Sequence2 extends Sequence implements Runnable{

	public Sequence2(FrequentPath1 cw) {
		super(cw);
		this.cw = super.cw;
	}
	
    public void run() {
		cw.Invoke2();
		cw.Receive3();
		cw.Assign3();
    }
}
