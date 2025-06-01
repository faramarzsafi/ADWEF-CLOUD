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


@WorkflowLayout(activities={@ActivityLayout(position="(424,237)", name="Assign6Code")})
public class Assign6 extends WorkflowBehaviour {

	public static final String ASSIGN6CODE_ACTIVITY = "Assign6Code";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		CodeExecutionBehaviour assign6CodeActivity = new CodeExecutionBehaviour(
				ASSIGN6CODE_ACTIVITY, this);
		registerActivity(assign6CodeActivity, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}

	protected void executeReceive() throws Exception {

	}

	protected void executeAssign6Code() throws Exception {
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.fpd: Assign6 : in the catch: "+e.getMessage()+e.getCause());
		}
	}
}
