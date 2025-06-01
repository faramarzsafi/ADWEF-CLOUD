package Block_Structured.If.hipd.l1;

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


@WorkflowLayout(entryPoint = @MarkerLayout(position = "(387,45)", activityName = "ReceiveCodeActivity1"), activities={@ActivityLayout(position="(352,378)", name = "ReplyCodeActivity2"), @ActivityLayout(position="(485,273)", name="Assign2SubflowActivity1"), @ActivityLayout(position="(222,275)", name="Assign1CodeActivity2"), @ActivityLayout(isDecisionPoint = true, position="(378,196)", name="FrequentPath1RouteActivity1"), @ActivityLayout(size = "(167,50)", position="(318,107)", name = "ReceiveCodeActivity1")})
public class FrequentPath1 extends WorkflowBehaviour {

	public static final String REPLYCODEACTIVITY2_ACTIVITY = "ReplyCodeActivity2";
	public static final String ASSIGN2SUBFLOWACTIVITY1_ACTIVITY = "Assign2SubflowActivity1";
	public static final String ASSIGN1CODEACTIVITY2_ACTIVITY = "Assign1CodeActivity2";
	public static final String FREQUENTPATH1ROUTEACTIVITY1_ACTIVITY = "FrequentPath1RouteActivity1";
	public static final String RECEIVECODEACTIVITY1_ACTIVITY = "ReceiveCodeActivity1";
	@FormalParameter
	private int input;
	
//	@FormalParameter
//	private byte[] VARIABLE_MESSAGE;
//	
//	@FormalParameter(mode=FormalParameter.OUTPUT)
//	private byte[] RETURNED_VARIABLE_MESSAGE = null;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		CodeExecutionBehaviour ReceiveCodeActivity1 = new CodeExecutionBehaviour(
				RECEIVECODEACTIVITY1_ACTIVITY, this);
		registerActivity(ReceiveCodeActivity1, INITIAL);
		RouteActivityBehaviour frequentPath1RouteActivity1Activity = new RouteActivityBehaviour(
				FREQUENTPATH1ROUTEACTIVITY1_ACTIVITY, this);
		registerActivity(frequentPath1RouteActivity1Activity);
		CodeExecutionBehaviour assign1CodeActivity2Activity = new CodeExecutionBehaviour(
				ASSIGN1CODEACTIVITY2_ACTIVITY, this);
		registerActivity(assign1CodeActivity2Activity);
		SubflowDelegationBehaviour assign2SubflowActivity1Activity = new SubflowDelegationBehaviour(
				ASSIGN2SUBFLOWACTIVITY1_ACTIVITY, this);
		assign2SubflowActivity1Activity
				.setSubflow("Block_Structured.If.hipd.l1.Assign2");
		registerActivity(assign2SubflowActivity1Activity);
		CodeExecutionBehaviour ReplyCodeActivity2 = new CodeExecutionBehaviour(
				REPLYCODEACTIVITY2_ACTIVITY, this);
		registerActivity(ReplyCodeActivity2, FINAL);
	}
	protected void executeReceiveCodeActivity1() throws Exception {
//		System.out.println("Receive: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("Receive: in the catch: ");
			e.printStackTrace();
		}
		
	}
	private void defineTransitions() {
		registerTransition(new Transition(),
				RECEIVECODEACTIVITY1_ACTIVITY,
				FREQUENTPATH1ROUTEACTIVITY1_ACTIVITY);
		registerTransition(new Transition("FrequentPath1RouteActivity1ToAssign1CodeActivity2", this),
				FREQUENTPATH1ROUTEACTIVITY1_ACTIVITY,
				ASSIGN1CODEACTIVITY2_ACTIVITY);
		registerTransition(new Transition(
				"FrequentPath1RouteActivity1ToAssign2SubflowActivity1", this),
				FREQUENTPATH1ROUTEACTIVITY1_ACTIVITY,
				ASSIGN2SUBFLOWACTIVITY1_ACTIVITY);
		registerTransition(new Transition(), ASSIGN1CODEACTIVITY2_ACTIVITY,
				REPLYCODEACTIVITY2_ACTIVITY);
		registerTransition(new Transition(), ASSIGN2SUBFLOWACTIVITY1_ACTIVITY,
				REPLYCODEACTIVITY2_ACTIVITY);
	}
	protected void executeAssign1CodeActivity2() throws Exception {
		Assign1();
	}
	protected void executeAssign2SubflowActivity1(Subflow s) throws Exception {
//		s.setPerformer("Assign2_Agent");
		s.setPerformer(ContainerManagement.getContainer("Assign2_Agent", "Block_Structured.If.hipd.l1.Assign2"));
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", share.MESSAGE);
		try {
			performSubflow(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.print("In the catch of calling Assign2_Agent: "+e.getMessage()+e.getCause());
			e.printStackTrace();
			throw e;
		}
//		output = ((Integer)s.extract("output")).intValue();
//		System.out.println("Calling Subflow Assign2: Run out");
	}
	protected boolean checkFrequentPath1RouteActivity1ToAssign2SubflowActivity1() {
//		System.out.println("the condition (input>10) && (input <=20) is: "+((input>10) && (input <=20)));
		if ((input>10) && (input <=20))return true;
		else return false;
	}
	protected boolean checkFrequentPath1RouteActivity1ToAssign1CodeActivity2() {
//		System.out.println("the condition (((input>=1) && (input <=10))||((input>=21)&&(input<=100))) is: "+(((input>=1) && (input <=10))||((input>=21)&&(input<=100))));
		if (((input>=1) && (input <=10))||((input>=21)&&(input<=100)))return true;
		else return false;
	}
	protected void executeReplyCodeActivity2() throws Exception {
//		System.out.println("Reply: Executed");
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("Reply: in the catch: "+e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}
	public void Assign1() throws InterruptedException{
//		System.out.println("Assign1:"+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("Assign1: in the catch: ");
			e.printStackTrace();
			throw e;
		}
	}

}