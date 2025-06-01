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


@WorkflowLayout(exitPoints = { }, activities={@ActivityLayout(size = "(203,50)", position="(370,241)", name="Sequence1SubflowActivity1")})
public class Sequence1 extends WorkflowBehaviour {

	public static final String SEQUENCE1SUBFLOWACTIVITY1_ACTIVITY = "Sequence1SubflowActivity1";
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
		SubflowDelegationBehaviour sequence1SubflowActivity1Activity = new SubflowDelegationBehaviour(
				SEQUENCE1SUBFLOWACTIVITY1_ACTIVITY, this);
		sequence1SubflowActivity1Activity
				.setSubflow("LoanTaking.hpd.l3.Invoke1");
		registerActivity(sequence1SubflowActivity1Activity, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}

	protected void executeSequence1SubflowActivity1(Subflow s) throws Exception {
//		System.out.println("Sequence1: Run");
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		s.setPerformer("Invoke1_Agent");
		s.setPerformer(ContainerManagement.getContainer("Invoke1_Agent", "LoanTaking.hpd.l3.Invoke1"));
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
		performSubflow(s);
	}
}

