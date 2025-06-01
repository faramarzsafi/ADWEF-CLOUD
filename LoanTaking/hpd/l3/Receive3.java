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


@WorkflowLayout(exitPoints = { }, activities={@ActivityLayout(size = "(168,50)", position="(297,193)", name = "Receive3SubflowActivity1")})
public class Receive3 extends WorkflowBehaviour {

	public static final String RECEIVE3SUBFLOWACTIVITY1_ACTIVITY = "Receive3SubflowActivity1";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	int reportA;
	private void defineActivities() {
		SubflowDelegationBehaviour Receive3SubflowActivity1 = new SubflowDelegationBehaviour(
				RECEIVE3SUBFLOWACTIVITY1_ACTIVITY, this);
		Receive3SubflowActivity1.setSubflow("LoanTaking.hpd.l3.Assign3");
		registerActivity(Receive3SubflowActivity1, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}
	
	protected void executeReceive3SubflowActivity1(Subflow s) throws Exception {
//		System.out.println("Receive3: "+input);
		//delay in calling web service is 0 in this case.
		reportA = input; 
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("Receive3: in the catch: "+input);
		}
//		s.setPerformer("Assign3_Agent");
		s.setPerformer(ContainerManagement.getContainer("Assign3_Agent", "LoanTaking.hpd.l3.Assign3"));
		s.fill("input",input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
		
		performSubflow(s);
	}
}