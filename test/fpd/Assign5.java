package test.fpd;

import com.tilab.wade.performer.RouteActivityBehaviour;
import com.tilab.wade.performer.Transition;
import com.tilab.wade.performer.layout.MarkerLayout;


import com.tilab.wade.performer.layout.ActivityLayout;
import com.tilab.wade.performer.layout.WorkflowLayout;
import com.tilab.wade.performer.CodeExecutionBehaviour;
import com.tilab.wade.performer.FormalParameter;
import com.tilab.wade.performer.WorkflowBehaviour;
import common.share;


@WorkflowLayout(activities={@ActivityLayout(position="(387,165)", name="Assign5Code")})
public class Assign5 extends WorkflowBehaviour {

	public static final String ASSIGN5CODE_ACTIVITY = "Assign5Code";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		CodeExecutionBehaviour assign5CodeActivity = new CodeExecutionBehaviour(
				ASSIGN5CODE_ACTIVITY, this);
		registerActivity(assign5CodeActivity, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}

	protected void executeReceive() throws Exception {

	}

	protected void executeAssign5Code() throws Exception {
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.fpd: Assign5 : in the catch: "+e.getMessage()+e.getCause());
		}
	}
	
	
	
}
