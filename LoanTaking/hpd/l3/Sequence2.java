package LoanTaking.hpd.l3;


import com.tilab.wade.performer.SubflowDelegationBehaviour;
import com.tilab.wade.performer.RouteActivityBehaviour;
import com.tilab.wade.performer.Transition;
import com.tilab.wade.performer.layout.MarkerLayout;


import com.tilab.wade.performer.layout.ActivityLayout;
import com.tilab.wade.performer.layout.WorkflowLayout;
import com.tilab.wade.performer.CodeExecutionBehaviour;
import com.tilab.wade.performer.FormalParameter;
import com.tilab.wade.performer.Subflow;
import com.tilab.wade.performer.WorkflowBehaviour;

import common.ContainerManagement;
import common.share;


@WorkflowLayout(exitPoints = { }, activities={@ActivityLayout(size = "(193,50)", position="(243,223)", name="Sequence2SubflowActivity1")})
public class Sequence2 extends WorkflowBehaviour {

	public static final String SEQUENCE2SUBFLOWACTIVITY1_ACTIVITY = "Sequence2SubflowActivity1";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	int scoreA;
	int reportA;
	int reportB;
	int claim;
	boolean approval;
	
	private void defineActivities() {
		SubflowDelegationBehaviour sequence2SubflowActivity1Activity = new SubflowDelegationBehaviour(
				SEQUENCE2SUBFLOWACTIVITY1_ACTIVITY, this);
		sequence2SubflowActivity1Activity
				.setSubflow("LoanTaking.hpd.l3.Invoke2");
		registerActivity(sequence2SubflowActivity1Activity, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}

	protected void executeSequence2SubflowActivity1(Subflow s) throws Exception {
//		System.out.println("Sequence2: Run");
//		s.setPerformer("Invoke2_Agent");
		s.setPerformer(ContainerManagement.getContainer("Invoke2_Agent", "LoanTaking.hpd.l3.Invoke2"));
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
		performSubflow(s);
	}

}
