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


@WorkflowLayout(exitPoints = { }, activities={@ActivityLayout(size = "(168,50)", position="(297,193)", name = "Invoke2SubflowActivity1")})
public class Invoke2 extends WorkflowBehaviour {

	public static final String INVOKE2SUBFLOWACTIVITY1_ACTIVITY = "Invoke2SubflowActivity1";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	int claim;
	
	private void defineActivities() {
		SubflowDelegationBehaviour Invoke2SubflowActivity1 = new SubflowDelegationBehaviour(
				INVOKE2SUBFLOWACTIVITY1_ACTIVITY, this);
		Invoke2SubflowActivity1.setSubflow("LoanTaking.hpd.l3.Receive3");
		registerActivity(Invoke2SubflowActivity1, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}
	
	protected void executeInvoke2SubflowActivity1(Subflow s) throws Exception {
//		System.out.println("Invoke2: "+input);
		
		try {
			//call webServiceA.process(claim);
			callWebService("A", claim);
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("Invoke2: in the catch: "+input);
		}
	
//		s.setPerformer("Receive3_Agent");
		s.setPerformer(ContainerManagement.getContainer("Receive3_Agent", "LoanTaking.hpd.l3.Receive3"));
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
		performSubflow(s);
	}
	
	
	private int callWebService(String string, int claim2) {
		return 0;
	}
}