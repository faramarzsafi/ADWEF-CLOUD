package test.ipd.g2;


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

@WorkflowLayout(entryPoint = @MarkerLayout(position = "(428,113)", activityName = "ReceiveCode"), exitPoints = { @MarkerLayout(position = "(427,330)", activityName = "Receive1Subflow") }, activities={@ActivityLayout(position="(391,254)", name = "Receive1Subflow"), @ActivityLayout(position="(392,172)", name = "ReceiveCode")})
public class Receive extends WorkflowBehaviour {

	public static final String RECEIVE1SUBFLOW_ACTIVITY = "Receive1Subflow";
	public static final String RECEIVECODE_ACTIVITY = "ReceiveCode";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		CodeExecutionBehaviour ReceiveCode = new CodeExecutionBehaviour(
				RECEIVECODE_ACTIVITY, this);
		registerActivity(ReceiveCode, INITIAL);
		SubflowDelegationBehaviour Receive1Subflow = new SubflowDelegationBehaviour(
				RECEIVE1SUBFLOW_ACTIVITY, this);
		Receive1Subflow.setSubflow("test.ipd.g2.Assign1");
		registerActivity(Receive1Subflow, FINAL);

	}

	private void defineTransitions() {
		registerTransition(new Transition(), RECEIVECODE_ACTIVITY,
				RECEIVE1SUBFLOW_ACTIVITY);
	
	}

	protected void executeReceive() throws Exception {
	}

	protected void executeReceiveCode() throws Exception {
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void executeReceive1Subflow(Subflow s) throws Exception {
		s.fill("input", input);
		s.setPerformer("Assign1_Agent");
		performSubflow(s);
//		output = ((Integer)s.extract("output")).intValue();
//		System.out.println("test.ipd.g2: Receive: return response is: "+output);
	}
}
