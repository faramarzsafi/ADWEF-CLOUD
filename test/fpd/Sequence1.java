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

@WorkflowLayout(entryPoint = @MarkerLayout(position = "(382,156)", activityName = "Sequence1Subflow"), exitPoints = { @MarkerLayout(position = "(381,315)", activityName = "Sequence1Subflow") }, activities={@ActivityLayout(size = "(156,50)", position="(319,218)", name = "Sequence1Subflow")})
public class Sequence1 extends WorkflowBehaviour {

	public static final String SEQUENCE1SUBFLOW_ACTIVITY = "Sequence1Subflow";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		SubflowDelegationBehaviour Sequence1Subflow = new SubflowDelegationBehaviour(
				SEQUENCE1SUBFLOW_ACTIVITY, this);
		Sequence1Subflow.setSubflow("test.fpd.Assign2");
		registerActivity(Sequence1Subflow, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}

	protected void executeReceive() throws Exception {
	}

	protected void executeSequence1Subflow(Subflow s) throws Exception {
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
		s.setPerformer("Assign2_Agent");
		performSubflow(s);
//		output = ((Integer)s.extract("output")).intValue();
//		System.out.println("Sequence1: return response is: "+output);
	}
}
