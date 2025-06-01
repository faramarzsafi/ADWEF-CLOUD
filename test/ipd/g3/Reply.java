package test.ipd.g3;


import com.tilab.wade.performer.RouteActivityBehaviour;
import com.tilab.wade.performer.Transition;
import com.tilab.wade.performer.layout.MarkerLayout;


import com.tilab.wade.performer.layout.ActivityLayout;
import com.tilab.wade.performer.layout.WorkflowLayout;
import com.tilab.wade.performer.CodeExecutionBehaviour;
import com.tilab.wade.performer.FormalParameter;
import com.tilab.wade.performer.WorkflowBehaviour;
import common.share;


@WorkflowLayout(activities={@ActivityLayout(position="(466,174)", name="ReplyCode")})
public class Reply extends WorkflowBehaviour {

	public static final String REPLYCODE_ACTIVITY = "ReplyCode";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		CodeExecutionBehaviour replyCodeActivity = new CodeExecutionBehaviour(
				REPLYCODE_ACTIVITY, this);
		registerActivity(replyCodeActivity, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}
	protected void executeReplyCode() throws Exception {
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.ipd.g3: Reply : in the catch: "+e.getMessage()+e.getCause());
		}
	}
}
