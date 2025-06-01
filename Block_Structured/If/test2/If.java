package Block_Structured.If.test2;

import com.tilab.wade.performer.layout.ActivityLayout;
import com.tilab.wade.performer.layout.WorkflowLayout;
import com.tilab.wade.performer.CodeExecutionBehaviour;
import com.tilab.wade.performer.FormalParameter;
import com.tilab.wade.performer.WorkflowBehaviour;

import common.ContainerManagement;
import common.share;


@WorkflowLayout(exitPoints = { }, activities={@ActivityLayout(position="(325,212)", name="IfCodeActivity1")})
public class If extends WorkflowBehaviour {

	public static final String IFCODEACTIVITY1_ACTIVITY = "IfCodeActivity1";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		CodeExecutionBehaviour ifCodeActivity1Activity = new CodeExecutionBehaviour(
				IFCODEACTIVITY1_ACTIVITY, this);
		registerActivity(ifCodeActivity1Activity, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
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

	protected void executeIfCodeActivity1() throws Exception {
//		System.out.println("If: Run");
//		try {
//		Thread.sleep(share.ACTIVITY_SLEEP);
//	} catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	
	if (((input>=1) && (input <=10))||((input>=21)&&(input<=100))){
//		System.out.println("If-condition");
		Assign1();
	} else {//if ((input>10) && (input <=20)){
//		System.out.println("else-condition");
		Assign2();
	}
//		s.fill("input", input);
//		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
//		s.setPerformer("Reply_Agent");
//		s.setPerformer(ContainerManagement.getContainer("Reply_Agent"));
	
//		performSubflow(s);
//		output = ((Integer)s.extract("output")).intValue();
//		System.out.println("If: Run out");
	}
}