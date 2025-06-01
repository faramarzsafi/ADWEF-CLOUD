package firstTest;

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


@WorkflowLayout(exitPoints = { }, activities={@ActivityLayout(position="(404,282)", name="WhileSubflowActivity1")})
public class While2 extends WorkflowBehaviour {

	public static final String WHILESUBFLOWACTIVITY1_ACTIVITY = "WhileSubflowActivity1";
	
	com.tilab.wade.performer.Subflow Assign1SubFlow;
	
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		SubflowDelegationBehaviour whileSubflowActivity1Activity = new SubflowDelegationBehaviour(
				WHILESUBFLOWACTIVITY1_ACTIVITY, this);
		whileSubflowActivity1Activity.setSubflow("firstTest.Assign");
		registerActivity(whileSubflowActivity1Activity, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}

	protected void executeWhileCodeActivity1() throws Exception {
	}

	protected void executeWhileSubflowActivity1(Subflow s) throws Exception {
		int n = 0;
		while (n<share.boundaryLoopRepeatation) {
			s.setPerformer("Performer_Agent");
			s.fill("input", input);
			s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
			performSubflow(s); 
			n++;
		}
	}
}