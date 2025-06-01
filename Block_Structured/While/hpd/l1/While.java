package Block_Structured.While.hpd.l1;

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


@WorkflowLayout(exitPoints = { }, activities={@ActivityLayout(position="(343,259)", name="WhileSubflowActivity1")})
public class While extends WorkflowBehaviour {

	public static final String WHILESUBFLOWACTIVITY1_ACTIVITY = "WhileSubflowActivity1";
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
				.setSubflow("Block_Structured.While.hpd.l1.Reply");
		registerActivity(whileSubflowActivity1Activity, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}

	protected void executeWhileSubflowActivity1(Subflow s) throws Exception {
//		System.out.println("While: "+input);
		if (((input>=1) && (input <=10))||((input>=21)&&(input<=100))){
//		if ((input>=1) && (input <=50)){
			int n = 0;
			while (n<10) {Assign1(); n++;};
		}
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
		s.setPerformer(ContainerManagement.getContainer("Reply_Agent", "Block_Structured.While.hpd.l1.Reply"));
		performSubflow(s);
	}

	public void Assign1(){
//		System.out.println("Assign1: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
//			System.out.println("test.centralized: Assign1: in the catch: "+input);
		}
	}
	
}