package LoanTaking.hpd.l3;



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

import common.ContainerManagement;
import common.share;


@WorkflowLayout(exitPoints = { }, activities={@ActivityLayout(size = "(168,50)", position="(297,193)", name = "Receive2SubflowActivity1")})
public class Receive2 extends WorkflowBehaviour {

	public static final String RECEIVE2SUBFLOWACTIVITY1_ACTIVITY = "Receive2SubflowActivity1";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		SubflowDelegationBehaviour Receive2SubflowActivity1 = new SubflowDelegationBehaviour(
				RECEIVE2SUBFLOWACTIVITY1_ACTIVITY, this);
		Receive2SubflowActivity1.setSubflow("LoanTaking.hpd.l3.Assign2");
		registerActivity(Receive2SubflowActivity1, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}
	
	protected void executeReceive2SubflowActivity1(Subflow s) throws Exception {
//		System.out.println("Receive2: Executed");
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
//		s.setPerformer("Assign2_Agent");
		s.setPerformer(ContainerManagement.getContainer("Assign2_Agent", "LoanTaking.hpd.l3.Assign2"));
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
		performSubflow(s);
	}
}