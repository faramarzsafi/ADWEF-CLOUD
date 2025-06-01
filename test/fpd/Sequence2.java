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

@WorkflowLayout(entryPoint = @MarkerLayout(position = "(371,233)", activityName = "Sequence2Subflow"), exitPoints = {@MarkerLayout(position="(374,372)", activityName="Sequence2Subflow") }, activities={@ActivityLayout(size = "(158,50)", position="(308,289)", name="Sequence2Subflow")})
public class Sequence2 extends WorkflowBehaviour {

	public static final String SEQUENCE2SUBFLOW_ACTIVITY = "Sequence2Subflow";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		SubflowDelegationBehaviour sequence2SubflowActivity = new SubflowDelegationBehaviour(
				SEQUENCE2SUBFLOW_ACTIVITY, this);
		sequence2SubflowActivity.setSubflow("test.fpd.Assign3");
		registerActivity(sequence2SubflowActivity, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}

	protected void executeReceive() throws Exception {
	}

	protected void executeSequence2Code() throws Exception {
	}

	protected void executeSequence2Subflow(Subflow s) throws Exception {
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
		s.setPerformer("Assign3_Agent");
		performSubflow(s);
//		output = ((Integer)s.extract("output")).intValue();
//		System.out.println("Sequence2: return response is: "+output);
	}
}
