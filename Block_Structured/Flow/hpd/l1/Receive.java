package Block_Structured.Flow.hpd.l1;


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


@WorkflowLayout(exitPoints = { }, activities={@ActivityLayout(size = "(200,50)", position="(199,209)", name="ReceiveSubflowActivity1")})
public class Receive extends WorkflowBehaviour {

	public static final String RECEIVESUBFLOWACTIVITY1_ACTIVITY = "ReceiveSubflowActivity1";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		SubflowDelegationBehaviour receiveSubflowActivity1Activity = new SubflowDelegationBehaviour(
				RECEIVESUBFLOWACTIVITY1_ACTIVITY, this);
		receiveSubflowActivity1Activity
				.setSubflow("Block_Structured.Flow.hpd.l1.Flow");
		registerActivity(receiveSubflowActivity1Activity, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}

	protected void executeReceiveSubflowActivity1(Subflow s) throws Exception {
//		System.out.println("Receive: Executed");
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {e.printStackTrace();}
		
//		s.setPerformer("Flow_Agent");
		s.setPerformer(ContainerManagement.getContainer("Flow_Agent", "Block_Structured.Flow.hpd.l1.Flow"));
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
		performSubflow(s);
//		output = ((Integer)s.extract("output")).intValue();
//		System.out.println("Receive: run out"+output);

	}
}