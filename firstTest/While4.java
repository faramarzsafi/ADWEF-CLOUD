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


@WorkflowLayout(exitPoints = { }, activities={@ActivityLayout(position="(308,270)", name="While1SubflowActivity1")})
public class While4 extends WorkflowBehaviour {

	public static final String WHILE1SUBFLOWACTIVITY1_ACTIVITY = "While1SubflowActivity1";

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
		SubflowDelegationBehaviour while1SubflowActivity1Activity = new SubflowDelegationBehaviour(
				WHILE1SUBFLOWACTIVITY1_ACTIVITY, this);
		while1SubflowActivity1Activity.setSubflow("firstTest.Assign");
		registerActivity(while1SubflowActivity1Activity, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}

	protected void executeWhileCodeActivity1() throws Exception {
	}


	protected void executeWhile1SubflowActivity1(Subflow s) throws Exception {
//		System.out.println("While: "+input);
//		if (((input>=1) && (input <=10))||((input>=21)&&(input<=100))){
			int n = 0;
			while (n<share.boundaryLoopRepeatation) {
				s.setPerformer("Performer2_Agent");
				s.fill("input", input);
				s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
				performSubflow(s);
				n++;
			}
//		}
	}
	
	

//	void Assign1(){
//		Assign1SubFlow = new com.tilab.wade.performer.Subflow("firstTest.Assign1");
//		Assign1SubFlow.fill("input", input);
////		Assign1SubFlow.setPerformer("Assign1_Agent");
//		Assign1SubFlow.setPerformer(ContainerManagement.getContainer("Assign1_Agent"));
//		try {
//			performSubflow(Assign1SubFlow);
//		} catch (Exception e) {
//			System.out.println("Assign1-subFlow Invocation"+e.getMessage());
//			e.printStackTrace();
//		}
//	}


}