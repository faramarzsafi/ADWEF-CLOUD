package LoanTaking.hpd.l2;

import java.util.List;

import com.tilab.wade.performer.SubflowList;
import com.tilab.wade.performer.SubflowJoinBehaviour;
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


@WorkflowLayout(exitPoints = {@MarkerLayout(position="(325,357)", activityName = "FlowSubflowActivity1If") }, activities={@ActivityLayout(size = "(152,50)", position="(261,198)", name="FlowCodeActivity1"), @ActivityLayout(size = "(154,50)", position="(261,284)", name = "FlowSubflowActivity1If")})
public class Flow extends WorkflowBehaviour {

	public static final String FLOWCODEACTIVITY1_ACTIVITY = "FlowCodeActivity1";
	public static final String FLOWSUBFLOWACTIVITY1IF_ACTIVITY = "FlowSubflowActivity1If";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	com.tilab.wade.performer.Subflow Sequence1SubFlow;
	com.tilab.wade.performer.Subflow Sequence2SubFlow;
	
	int scoreA;
	int reportA;
	int reportB;
	int claim;
	boolean approval;
	
	private void defineActivities() {
		SubflowDelegationBehaviour FlowSubflowActivity1If = new SubflowDelegationBehaviour(
				FLOWSUBFLOWACTIVITY1IF_ACTIVITY, this);
		FlowSubflowActivity1If.setSubflow("LoanTaking.hpd.l2.If");
		registerActivity(FlowSubflowActivity1If, FINAL);
		CodeExecutionBehaviour flowCodeActivity1Activity = new CodeExecutionBehaviour(
				FLOWCODEACTIVITY1_ACTIVITY, this);
		registerActivity(flowCodeActivity1Activity, INITIAL);

	}

	private void defineTransitions() {
		registerTransition(new Transition(), FLOWCODEACTIVITY1_ACTIVITY,
				FLOWSUBFLOWACTIVITY1IF_ACTIVITY);
	}
	protected void executeFlowCodeActivity1() throws Exception {
		
		Thread thread1  = new AssignThread(this, 1);
		Thread thread2 = new AssignThread(this, 2);

		thread1.start();
		thread2.start();
		
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void callSequence1() {
//		System.out.println("Flow-before calling Sequence1: Executed");
		Sequence1SubFlow = new com.tilab.wade.performer.Subflow("LoanTaking.hpd.l2.Sequence1");
		Sequence1SubFlow.fill("input", input);
		Sequence1SubFlow.fill("VARIABLE_MESSAGE", share.MESSAGE);
//		Sequence1SubFlow.setPerformer("Sequence1_Agent");
		Sequence1SubFlow.setPerformer(ContainerManagement.getContainer("Sequence1_Agent", "LoanTaking.hpd.l2.Sequence1"));
		
		try {
			performSubflow(Sequence1SubFlow);
		} catch (Exception e) {
			System.out.println("Sequence1SubFlow Invocation"+e.getMessage());
			e.printStackTrace();
		}
	}

	void callSequence2() {
//		System.out.println("Flow-before calling Sequence2: Executed");
		Sequence2SubFlow = new com.tilab.wade.performer.Subflow("LoanTaking.hpd.l2.Sequence2");
		Sequence2SubFlow.fill("input", input);
		Sequence2SubFlow.fill("VARIABLE_MESSAGE", share.MESSAGE);
//		Sequence2SubFlow.setPerformer("Sequence2_Agent");
		Sequence2SubFlow.setPerformer(ContainerManagement.getContainer("Sequence2_Agent", "LoanTaking.hpd.l2.Sequence2"));
		try {
			performSubflow(Sequence2SubFlow);
		} catch (Exception e) {
			System.out.println("Sequence2SubFlow Invocation"+e.getMessage());
			e.printStackTrace();
		}
	}
	protected void executeFlowSubflowActivity1If(Subflow s) throws Exception {
//		System.out.println("Flow-before calling If: Executed");
//		s.setPerformer("If_Agent");
		s.setPerformer(ContainerManagement.getContainer("If_Agent", "LoanTaking.hpd.l2.If"));
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
		performSubflow(s);
	}
}

class AssignThread extends Thread {
	Flow cw;
	int assign;
	public AssignThread(Flow cw, int assign){
		this.cw = cw;
		this.assign = assign;
	}
    // This method is called when the thread runs
    public void run() {
    	if (assign == 1) cw.callSequence1();
    	else if (assign ==2) cw.callSequence2();
   }
}


