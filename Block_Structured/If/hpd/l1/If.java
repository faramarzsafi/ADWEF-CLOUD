package Block_Structured.If.hpd.l1;


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


@WorkflowLayout(exitPoints = { }, activities={@ActivityLayout(size = "(205,50)", position="(267,197)", name="IfSubflowActivity1")})
public class If extends WorkflowBehaviour {

	public static final String IFSUBFLOWACTIVITY1_ACTIVITY = "IfSubflowActivity1";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		SubflowDelegationBehaviour ifSubflowActivity1Activity = new SubflowDelegationBehaviour(
				IFSUBFLOWACTIVITY1_ACTIVITY, this);
		ifSubflowActivity1Activity
				.setSubflow("Block_Structured.If.hpd.l1.Reply");
		registerActivity(ifSubflowActivity1Activity, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}

	protected void executeIfSubflowActivity1(Subflow s) throws Exception {
//		System.out.println("If: Run");
		try {
		Thread.sleep(share.ACTIVITY_SLEEP);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	if (((input>=1) && (input <=10))||((input>=21)&&(input<=100))){
//	if ((input>=1) && (input <=50)){
//		System.out.println("If-condition");
		Assign1();
	} else {//if ((input>10) && (input <=20)){
//	}else if ((input>50) && (input <=100)){
//		System.out.println("else-condition");
		Assign2();
	}
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
//		s.setPerformer("Reply_Agent");
		s.setPerformer(ContainerManagement.getContainer("Reply_Agent", "Block_Structured.If.hpd.l1.Reply"));
		performSubflow(s);
//		output = ((Integer)s.extract("output")).intValue();
//		System.out.println("If: Run out");
	}
	public void Assign1(){
//		System.out.println("CentralizedWorkflow: Assign6: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
//			System.out.println("test.centralized: Assign1: in the catch: "+input);
		}
	}
	
	public void Assign2(){
//		System.out.println("CentralizedWorkflow: Assign2: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
//			System.out.println("test.centralized: Assign2: in the catch: "+input);
		}
	}
}