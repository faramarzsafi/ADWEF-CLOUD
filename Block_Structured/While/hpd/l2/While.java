package Block_Structured.While.hpd.l2;

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
public class While extends WorkflowBehaviour {

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
		whileSubflowActivity1Activity
				.setSubflow("Block_Structured.While.hpd.l2.Reply");
		registerActivity(whileSubflowActivity1Activity, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}

	protected void executeWhileCodeActivity1() throws Exception {
	}

	protected void executeWhileSubflowActivity1(Subflow s) throws Exception {
//		System.out.println("While: "+input);
		if (((input>=1) && (input <=10))||((input>=21)&&(input<=100))){
//		if ((input>=1) && (input <=50)){
			int n = 0;
			while (n<10) {
				Assign1(); 
				n++;
			}
		}
		s.setPerformer(ContainerManagement.getContainer("Reply_Agent", "Block_Structured.While.hpd.l2.Reply"));
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
		performSubflow(s);
	}

	void Assign1(){
		Assign1SubFlow = new com.tilab.wade.performer.Subflow("Block_Structured.While.hpd.l2.Assign1");
		Assign1SubFlow.fill("input", input);
		Assign1SubFlow.fill("VARIABLE_MESSAGE", share.MESSAGE);
//		Assign1SubFlow.setPerformer("Assign1_Agent");
		Assign1SubFlow.setPerformer(ContainerManagement.getContainer("Assign1_Agent", "Block_Structured.While.hpd.l2.Assign1"));
		try {
			performSubflow(Assign1SubFlow);
		} catch (Exception e) {
			System.out.println("Assign1-subFlow Invocation"+e.getMessage());
			e.printStackTrace();
		}
	}


}