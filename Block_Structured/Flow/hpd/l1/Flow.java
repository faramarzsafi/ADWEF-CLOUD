package Block_Structured.Flow.hpd.l1;

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
	
	private void defineActivities() {
		SubflowDelegationBehaviour FlowSubflowActivity1 = new SubflowDelegationBehaviour(
				FLOWSUBFLOWACTIVITY1_ACTIVITY, this);
		FlowSubflowActivity1.setSubflow("Block_Structured.Flow.hpd.l1.Reply");
		registerActivity(FlowSubflowActivity1, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}

	protected void executeFlowSubflowActivity1(Subflow s) throws Exception {
//  	System.out.println("Flow: "+input);
		Runnable ass1 = new Assign(this,1);
		Thread assignThread1 = new Thread(ass1);
		Runnable ass2 = new Assign (this,2);
		Thread assignThread2 = new Thread(ass2);
		assignThread1.start();
		assignThread2.start();
		
		try {
			assignThread1.join();
			assignThread2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		s.setPerformer("Reply_Agent");
		s.setPerformer(ContainerManagement.getContainer("Reply_Agent", "Block_Structured.Flow.hpd.l1.Reply"));
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
		
		performSubflow(s);
	}

	public void Assign1(){
//		System.out.println("Assign1: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
//			System.out.println("test.centralized: Assign1: in the catch: "+input);
		}
	}
	public void Assign2(){
//		System.out.println("Assign2: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
//			System.out.println("test.centralized: Assign1: in the catch: "+input);
		}
	}
	
}
class Assign implements Runnable {
	Flow cw;
	int assign;
	public Assign(Flow cw, int assign){
		this.cw = cw;
		this.assign = assign;
	}
    // This method is called when the thread runs
    public void run() {
    	if (assign == 1) cw.Assign1();
    	else if (assign ==2) cw.Assign2();
    }
}