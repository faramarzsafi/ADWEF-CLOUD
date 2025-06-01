package Block_Structured.While.hpd.l0;

import com.tilab.wade.performer.RouteActivityBehaviour;
import com.tilab.wade.performer.Transition;
import com.tilab.wade.performer.layout.MarkerLayout;

import com.tilab.wade.performer.layout.ActivityLayout;
import com.tilab.wade.performer.layout.WorkflowLayout;
import com.tilab.wade.performer.CodeExecutionBehaviour;
import com.tilab.wade.performer.FormalParameter;
import com.tilab.wade.performer.WorkflowBehaviour;
import common.share;

@WorkflowLayout(exitPoints = {@MarkerLayout(position="(408,744)", activityName="Assign8") }, activities={@ActivityLayout(position="(375,236)", name="Workflow"), @ActivityLayout(position="(520,406)", name="Sequence3"), @ActivityLayout(position="(355,406)", name="Sequence2"), @ActivityLayout(position="(179,404)", name = "Sequence1"), @ActivityLayout(position="(377,267)", name="Flow"), @ActivityLayout(position="(328,219)", name="Assign"), @ActivityLayout(position="(378,347)", name = "Assign7"), @ActivityLayout(isDecisionPoint = true, position="(647,260)", name="If"), @ActivityLayout(position="(374,573)", name="Assign7"), @ActivityLayout(position="(745,371)", name="Assign6"), @ActivityLayout(position="(629,372)", name="Assign5"), @ActivityLayout(position="(515,371)", name="Assign4"), @ActivityLayout(position="(374,371)", name="Assign3"), @ActivityLayout(position="(222,372)", name="Assign2"), @ActivityLayout(position="(371,193)", name = "Assign1")})
public class CentralizedWorkflow extends WorkflowBehaviour {

	public static final String WORKFLOW_ACTIVITY = "Workflow";
	@FormalParameter
	private int input;
	
//	@FormalParameter
//	private byte[] VARIABLE_MESSAGE;
//	
//	@FormalParameter(mode=FormalParameter.OUTPUT)
//	private byte[] RETURNED_VARIABLE_MESSAGE = null;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	byte message[] = share.MESSAGE;
	
	private void defineActivities() {
		CodeExecutionBehaviour workflowActivity = new CodeExecutionBehaviour(
				WORKFLOW_ACTIVITY, this);
		registerActivity(workflowActivity, INITIAL_AND_FINAL);
	}

	private void defineTransitions() {
	}
	
	protected void executeWorkflow() throws Exception {
		
		Sequence0();
	}
	
	public void Sequence0() {
//		System.out.println("Sequence0: "+input);
		Receive();
		While();
		Reply();
	}
	
	public void Receive() {
//		System.out.println("Receive: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
//			System.out.println("test.centralized: Receive: in the catch: "+input);
		}
	}	

	public void While(){
//		System.out.println("While: "+input);
		if (((input>=1) && (input <=10))||((input>=21)&&(input<=100))){
//		if ((input>=1) && (input <=50)){
			int n = 0;
			while (n<10) {Assign1(); n++;};
		}
	}

	public void Assign1(){
//		System.out.println("Assign1: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
//			System.out.println("test.centralized: Assign1: in the catch: "+input);
		}
	}
	
	public void Reply() {
//		System.out.println("Reply: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
//			System.out.println("test.centralized: Reply: in the catch: "+input);
		}
	}
}


