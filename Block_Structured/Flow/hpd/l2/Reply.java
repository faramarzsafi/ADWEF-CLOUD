package Block_Structured.Flow.hpd.l2;


import com.tilab.wade.performer.RouteActivityBehaviour;
import com.tilab.wade.performer.Transition;
import com.tilab.wade.performer.layout.MarkerLayout;


import com.tilab.wade.performer.layout.ActivityLayout;
import com.tilab.wade.performer.layout.WorkflowLayout;
import com.tilab.wade.performer.CodeExecutionBehaviour;
import com.tilab.wade.performer.FormalParameter;
import com.tilab.wade.performer.WorkflowBehaviour;
import common.share;


@WorkflowLayout(exitPoints = { }, activities={@ActivityLayout(position="(250,236)", name="ReplyCodeActivity1")})
public class Reply extends WorkflowBehaviour {

	public static final String REPLYCODEACTIVITY1_ACTIVITY = "ReplyCodeActivity1";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		CodeExecutionBehaviour replyCodeActivity1Activity = new CodeExecutionBehaviour(
				REPLYCODEACTIVITY1_ACTIVITY, this);
		registerActivity(replyCodeActivity1Activity, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}
	
	protected void executeReplyCodeActivity1() throws Exception {
		
//    	System.out.println("Reply: Executed: "+share.repCount++);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}