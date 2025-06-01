package test.ipd.g4;


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


@WorkflowLayout(entryPoint = @MarkerLayout(position = "(475,203)", activityName = "Sequence3Subflow"), exitPoints = {@MarkerLayout(position="(475,328)", activityName = "Sequence3Subflow") }, activities={@ActivityLayout(size = "(163,50)", position="(407,261)", name = "Sequence3Subflow")})
public class Sequence3 extends WorkflowBehaviour {

	public static final String SEQUENCE3SUBFLOW_ACTIVITY = "Sequence3Subflow";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		SubflowDelegationBehaviour Sequence3Subflow = new SubflowDelegationBehaviour(
				SEQUENCE3SUBFLOW_ACTIVITY, this);
		Sequence3Subflow.setSubflow("test.ipd.g4.Agent1Workflow");
		registerActivity(Sequence3Subflow, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}

	protected void executeSequence3Code() throws Exception {
	}

	protected void executeSequence3Subflow(Subflow s) throws Exception {
		s.fill("input", input);
		s.setPerformer("Agent1Workflow_Agent");
		performSubflow(s);
//		output = ((Integer)s.extract("output")).intValue();
//		System.out.println("Sequence3: return response is: "+output);
	}
}
