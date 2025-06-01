package test.fpd;

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


@WorkflowLayout(exitPoints = { @MarkerLayout(position = "(495,333)", activityName = "ReplySubflow") }, activities={@ActivityLayout(position="(459,252)", name="ReplySubflow"), @ActivityLayout(position="(458,157)", name="Assign7Code")})
public class Assign7 extends WorkflowBehaviour {

	public static final String REPLYSUBFLOW_ACTIVITY = "ReplySubflow";
	public static final String ASSIGN7CODE_ACTIVITY = "Assign7Code";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		CodeExecutionBehaviour assign7CodeActivity = new CodeExecutionBehaviour(
				ASSIGN7CODE_ACTIVITY, this);
		registerActivity(assign7CodeActivity, INITIAL);
		SubflowDelegationBehaviour replySubflowActivity = new SubflowDelegationBehaviour(
				REPLYSUBFLOW_ACTIVITY, this);
		replySubflowActivity.setSubflow("test.fpd.Reply");
		registerActivity(replySubflowActivity, FINAL);

	}

	private void defineTransitions() {
		registerTransition(new Transition(), ASSIGN7CODE_ACTIVITY,
				REPLYSUBFLOW_ACTIVITY);
	
	}

	protected void executeReceive() throws Exception {
	}

	protected void executeAssign7Code() throws Exception {
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.fpd: Assign7 : in the catch: "+e.getMessage()+e.getCause());
		}
	}

	protected void executeReplySubflow(Subflow s) throws Exception {
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
		s.setPerformer("Reply_Agent");
		performSubflow(s);
//		output = ((Integer)s.extract("output")).intValue();
//		System.out.println("Assign1: return response is: "+output);
	}
}
