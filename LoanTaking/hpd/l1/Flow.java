package LoanTaking.hpd.l1;

import Block_Structured.Flow.hpd.l0.CentralizedWorkflow;

import com.tilab.wade.performer.SubflowDelegationBehaviour;
import com.tilab.wade.performer.Subflow;
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


@WorkflowLayout(exitPoints = { }, activities={@ActivityLayout(size = "(151,50)", position="(321,256)", name = "FlowSubflowActivity1")})
public class Flow extends WorkflowBehaviour {

	public static final String FLOWSUBFLOWACTIVITY1_ACTIVITY = "FlowSubflowActivity1";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	int scoreA;
	int reportA;
	int reportB;
	int claim;
	boolean approval;
	
	private void defineActivities() {
		SubflowDelegationBehaviour FlowSubflowActivity1 = new SubflowDelegationBehaviour(
				FLOWSUBFLOWACTIVITY1_ACTIVITY, this);
		FlowSubflowActivity1.setSubflow("LoanTaking.hpd.l1.If");
		registerActivity(FlowSubflowActivity1, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}

	protected void executeFlowSubflowActivity1(Subflow s) throws Exception {
//		System.out.println("Flow: "+input);
		
		Runnable seq1 = new Sequence1(this);
		Thread seqThread1 = new Thread(seq1);
		Runnable seq2 = new Sequence2 (this);
		Thread seqThread2 = new Thread(seq2);
		
		seqThread1.start();
		seqThread2.start();
		
		seqThread1.join();
		seqThread2.join();

//		s.setPerformer("If_Agent");
		s.setPerformer(ContainerManagement.getContainer("If_Agent", "LoanTaking.hpd.l1.If"));
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
		performSubflow(s);
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
	protected Flow cw;
	public Sequence(Flow flow){
		this.cw = flow;
	}
}

class Sequence1 extends Sequence implements Runnable{

	public Sequence1(Flow flow) {
		super(flow);
		this.cw = super.cw;
	}

	public void run() {
		cw.Invoke1();
		cw.Receive2();
		cw.Assign2();
	}
}

class Sequence2 extends Sequence implements Runnable{

	public Sequence2(Flow flow) {
		super(flow);
		this.cw = super.cw;
	}
	
    public void run() {
		cw.Invoke2();
		cw.Receive3();
		cw.Assign3();
    }
}
