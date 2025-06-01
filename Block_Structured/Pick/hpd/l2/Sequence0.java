package Block_Structured.Pick.hpd.l2;


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


@WorkflowLayout(entryPoint = @MarkerLayout(position = "(475,95)", activityName = "Sequence0SubflowActivity1"), exitPoints = {@MarkerLayout(position="(472,318)", activityName="Sequence0SubflowActivity1") }, activities={@ActivityLayout(size = "(191,50)", position="(395,196)", name="Sequence0SubflowActivity1")})
public class Sequence0 extends WorkflowBehaviour {

	public static final String SEQUENCE0SUBFLOWACTIVITY1_ACTIVITY = "Sequence0SubflowActivity1";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		SubflowDelegationBehaviour sequence0SubflowActivity1Activity = new SubflowDelegationBehaviour(
				SEQUENCE0SUBFLOWACTIVITY1_ACTIVITY, this);
		sequence0SubflowActivity1Activity
				.setSubflow("Block_Structured.Pick.hpd.l2.Receive");
		registerActivity(sequence0SubflowActivity1Activity, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}

	protected void executeSequence0SubflowActivity1(Subflow s) throws Exception {
		System.out.println("Sequence0:Run ");
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		s.setPerformer("Receive_Agent");
		s.setPerformer(ContainerManagement.getContainer("Receive_Agent", "Block_Structured.Pick.hpd.l2.Receive"));
		s.fill("input", input);
		performSubflow(s);
	}

}