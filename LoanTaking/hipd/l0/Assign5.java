package LoanTaking.hipd.l0;



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
import common.share;


@WorkflowLayout(exitPoints = { }, activities={@ActivityLayout(position="(371,267)", name="Assign5CodeActivity1")})
public class Assign5 extends WorkflowBehaviour {

	public static final String ASSIGN5CODEACTIVITY1_ACTIVITY = "Assign5CodeActivity1";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	boolean approval;
	
	private void defineActivities() {
		CodeExecutionBehaviour assign5CodeActivity1Activity = new CodeExecutionBehaviour(
				ASSIGN5CODEACTIVITY1_ACTIVITY, this);
		registerActivity(assign5CodeActivity1Activity, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}

	protected void executeAssign5CodeActivity1() throws Exception {
//		System.out.println("Assign5: "+input);
		approval = false;
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("Assign5: in the catch: "+input);
		}
	}	
}