package Block_Structured.Flow.hpd.l2;

import java.util.List;

import com.tilab.wade.performer.SubflowList;
import com.tilab.wade.performer.SubflowJoinBehaviour;
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


@WorkflowLayout(exitPoints = { }, activities={@ActivityLayout(size = "(164,50)", position="(347,379)", name="ReplySubflowActivity1"), @ActivityLayout(size = "(177,50)", position="(340,281)", name="FlowCodeActivity1")})
public class Flow extends WorkflowBehaviour {

	public static final String REPLYSUBFLOWACTIVITY1_ACTIVITY = "ReplySubflowActivity1";
	public static final String FLOWCODEACTIVITY1_ACTIVITY = "FlowCodeActivity1";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	com.tilab.wade.performer.Subflow Assign1SubFlow;
	com.tilab.wade.performer.Subflow Assign2SubFlow;
	
	
	private void defineActivities() {
		CodeExecutionBehaviour flowCodeActivity1Activity = new CodeExecutionBehaviour(
				FLOWCODEACTIVITY1_ACTIVITY, this);
		registerActivity(flowCodeActivity1Activity, INITIAL);
		SubflowDelegationBehaviour replySubflowActivity1Activity = new SubflowDelegationBehaviour(
				REPLYSUBFLOWACTIVITY1_ACTIVITY, this);
		replySubflowActivity1Activity
				.setSubflow("Block_Structured.Flow.hpd.l2.Reply");
		registerActivity(replySubflowActivity1Activity, FINAL);

	}

	private void defineTransitions() {
		registerTransition(new Transition(), FLOWCODEACTIVITY1_ACTIVITY,
				REPLYSUBFLOWACTIVITY1_ACTIVITY);
	
	}

	protected void executeFlowCodeActivity1() {
		
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
	
	void Assign1(){
		Assign1SubFlow = new com.tilab.wade.performer.Subflow("Block_Structured.Flow.hpd.l2.Assign1");
		Assign1SubFlow.fill("input", input);
		Assign1SubFlow.fill("VARIABLE_MESSAGE", share.MESSAGE);
//		Assign1SubFlow.setPerformer("Assign1_Agent");
		Assign1SubFlow.setPerformer(ContainerManagement.getContainer("Assign1_Agent", "Block_Structured.Flow.hpd.l2.Assign1"));
		try {
			performSubflow(Assign1SubFlow);
		} catch (Exception e) {
			System.out.println("Assign1-subFlow Invocation"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	void Assign2(){
		Assign2SubFlow = new com.tilab.wade.performer.Subflow("Block_Structured.Flow.hpd.l2.Assign2");
		Assign2SubFlow.fill("input", input);
		Assign2SubFlow.fill("VARIABLE_MESSAGE", share.MESSAGE);
//		Assign2SubFlow.setPerformer("Assign2_Agent");
		Assign2SubFlow.setPerformer(ContainerManagement.getContainer("Assign2_Agent", "Block_Structured.Flow.hpd.l2.Assign2"));
		try {
			performSubflow(Assign2SubFlow);
		} catch (Exception e) {
			System.out.println("Assign2-subFlow Invocation"+e.getMessage());
			e.printStackTrace();
		}
	}

	protected void executeReplySubflowActivity1(Subflow s) throws Exception {
//		System.out.println("Reply: run");
//		s.setPerformer("Reply_Agent");
		s.setPerformer(ContainerManagement.getContainer("Reply_Agent", "Block_Structured.Flow.hpd.l2.Reply"));
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
		performSubflow(s);
//		System.out.println("Reply: run out");
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
    	if (assign == 1) cw.Assign1();
    	else if (assign ==2) cw.Assign2();
   }
}
