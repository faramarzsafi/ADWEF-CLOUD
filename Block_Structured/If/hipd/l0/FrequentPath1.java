package Block_Structured.If.hipd.l0;

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

@WorkflowLayout(entryPoint = @MarkerLayout(position = "(288,63)", activityName = "Sequence0Workflow"), exitPoints = {@MarkerLayout(position="(288,562)", activityName="ReplyCodeActivity1"), @MarkerLayout(position="(408,744)", activityName="Assign8") }, activities={@ActivityLayout(position="(250,488)", name="ReplyCodeActivity1"), @ActivityLayout(position="(405,399)", name="ElseConditionSubflowActivity1"), @ActivityLayout(position="(111,402)", name="IfConditionCodeActivity1"), @ActivityLayout(isDecisionPoint = true, position="(278,320)", name="FrequentPath1RouteActivity1"), @ActivityLayout(size = "(183,50)", position="(209,232)", name="ReceiveCodeActivity1"), @ActivityLayout(position="(252,141)", name = "Sequence0Workflow"), @ActivityLayout(position="(520,406)", name="Sequence3"), @ActivityLayout(position="(355,406)", name="Sequence2"), @ActivityLayout(position="(179,404)", name = "Sequence1"), @ActivityLayout(position="(377,267)", name="Flow"), @ActivityLayout(position="(328,219)", name="Assign"), @ActivityLayout(position="(378,347)", name = "Assign7"), @ActivityLayout(isDecisionPoint = true, position="(647,260)", name="If"), @ActivityLayout(position="(374,573)", name="Assign7"), @ActivityLayout(position="(745,371)", name="Assign6"), @ActivityLayout(position="(629,372)", name="Assign5"), @ActivityLayout(position="(515,371)", name="Assign4"), @ActivityLayout(position="(374,371)", name="Assign3"), @ActivityLayout(position="(222,372)", name="Assign2"), @ActivityLayout(position="(371,193)", name = "Assign1")})
public class FrequentPath1 extends WorkflowBehaviour {

	public static final String REPLYCODEACTIVITY1_ACTIVITY = "ReplyCodeActivity1";
	public static final String ELSECONDITIONSUBFLOWACTIVITY1_ACTIVITY = "ElseConditionSubflowActivity1";
	public static final String IFCONDITIONCODEACTIVITY1_ACTIVITY = "IfConditionCodeActivity1";
	public static final String FREQUENTPATH1ROUTEACTIVITY1_ACTIVITY = "FrequentPath1RouteActivity1";
	public static final String RECEIVECODEACTIVITY1_ACTIVITY = "ReceiveCodeActivity1";
	public static final String SEQUENCE0WORKFLOW_ACTIVITY = "Sequence0Workflow";
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
		CodeExecutionBehaviour Sequence0Workflow = new CodeExecutionBehaviour(
				SEQUENCE0WORKFLOW_ACTIVITY, this);
		registerActivity(Sequence0Workflow, INITIAL);
		CodeExecutionBehaviour receiveCodeActivity1Activity = new CodeExecutionBehaviour(
				RECEIVECODEACTIVITY1_ACTIVITY, this);
		registerActivity(receiveCodeActivity1Activity);
		RouteActivityBehaviour frequentPath1RouteActivity1Activity = new RouteActivityBehaviour(
				FREQUENTPATH1ROUTEACTIVITY1_ACTIVITY, this);
		registerActivity(frequentPath1RouteActivity1Activity);
		CodeExecutionBehaviour ifConditionCodeActivity1Activity = new CodeExecutionBehaviour(
				IFCONDITIONCODEACTIVITY1_ACTIVITY, this);
		registerActivity(ifConditionCodeActivity1Activity);
		SubflowDelegationBehaviour elseConditionSubflowActivity1Activity = new SubflowDelegationBehaviour(
				ELSECONDITIONSUBFLOWACTIVITY1_ACTIVITY, this);
		elseConditionSubflowActivity1Activity
				.setSubflow("Block_Structured.If.hipd.l0.Assign2");
		registerActivity(elseConditionSubflowActivity1Activity);
		CodeExecutionBehaviour replyCodeActivity1Activity = new CodeExecutionBehaviour(
				REPLYCODEACTIVITY1_ACTIVITY, this);
		registerActivity(replyCodeActivity1Activity, FINAL);
	}

	private void defineTransitions() {
		registerTransition(new Transition(), SEQUENCE0WORKFLOW_ACTIVITY,
				RECEIVECODEACTIVITY1_ACTIVITY);
		registerTransition(new Transition(), RECEIVECODEACTIVITY1_ACTIVITY,
				FREQUENTPATH1ROUTEACTIVITY1_ACTIVITY);
		registerTransition(new Transition("FrequentPath1RouteActivity1ToIfConditionCodeActivity1", this),
				FREQUENTPATH1ROUTEACTIVITY1_ACTIVITY,
				IFCONDITIONCODEACTIVITY1_ACTIVITY);
		registerTransition(new Transition("FrequentPath1RouteActivity1ToElseConditionSubflowActivity1", this),
				FREQUENTPATH1ROUTEACTIVITY1_ACTIVITY,
				ELSECONDITIONSUBFLOWACTIVITY1_ACTIVITY);
		registerTransition(new Transition(), IFCONDITIONCODEACTIVITY1_ACTIVITY,
				REPLYCODEACTIVITY1_ACTIVITY);
		registerTransition(new Transition(),
				ELSECONDITIONSUBFLOWACTIVITY1_ACTIVITY,
				REPLYCODEACTIVITY1_ACTIVITY);
	}
	
	protected void executeSequence0Workflow() throws Exception {
		Sequence0();
	}
	
	protected void executeReceiveCodeActivity1() throws Exception {
//		System.out.println("Receive: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
//			System.out.println("test.centralized: Receive: in the catch: "+input);
		}
	}

	protected void executeIfConditionCodeActivity1() throws Exception {
		Assign1();
	}

	protected void executeElseConditionSubflowActivity1(Subflow s)
			throws Exception {
//		s.setPerformer("Assign2_Agent");
		s.setPerformer(ContainerManagement.getContainer("Assign2_Agent", "Block_Structured.If.hipd.l0.Assign2"));
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", share.MESSAGE);
		performSubflow(s);
//		output = ((Integer)s.extract("output")).intValue();
//		System.out.println("Else: Run out");
	}
	public void Sequence0() {
//		System.out.println("Sequence0: ");
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.centralized: Receive: in the catch: "+input);
		}
//		System.out.println("Sequence0: Run out"+output);
	}

	public void Assign1(){

		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("test.centralized: Assign1: in the catch: "+input);
		}
//		System.out.println("Assign1 Run out");
	}
	
//	protected void executeReplySubflowActivity1(Subflow s) throws Exception {
////		s.setPerformer("Reply_Agent");
//		s.setPerformer(ContainerManagement.getContainer("I think it is Invalid", ""));
//		s.fill("input", input);
//		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
//		performSubflow(s);
////		output = ((Integer)s.extract("output")).intValue();
////		System.out.println("Reply: Run out");
//	}
	
	protected boolean checkFrequentPath1RouteActivity1ToIfConditionCodeActivity1() {
//		System.out.println("Else-condition Run");
		if (((input>=1) && (input <=10))||((input>=21)&&(input<=100)))return true;
		else return false;
	}

	protected boolean checkFrequentPath1RouteActivity1ToElseConditionSubflowActivity1() {
//		System.out.println("If-condition Run");
		if ((input>10) && (input <=20))return true;
		else return false;
	}

	protected void executeReplyCodeActivity1() throws Exception {

//		System.out.println("Reply: Executed");
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
