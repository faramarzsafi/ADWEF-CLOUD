package Block_Structured.While.hipd.l1;

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
import common.share;

@WorkflowLayout(entryPoint = @MarkerLayout(position = "(362,125)", activityName = "ReceiveCodeActivity1"), exitPoints = {@MarkerLayout(position="(360,436)", activityName="ReplyCodeActivity1"), @MarkerLayout(position="(408,744)", activityName="Assign8") }, activities={@ActivityLayout(size = "(187,50)", position="(280,356)", name = "ReplyCodeActivity1"), @ActivityLayout(size = "(202,50)", position="(273,186)", name="ReceiveCodeActivity1"), @ActivityLayout(size = "(195,50)", position="(275,276)", name="WhileCodeActivity1"), @ActivityLayout(position="(520,406)", name="Sequence3"), @ActivityLayout(position="(355,406)", name="Sequence2"), @ActivityLayout(position="(179,404)", name = "Sequence1"), @ActivityLayout(position="(377,267)", name="Flow"), @ActivityLayout(position="(328,219)", name="Assign"), @ActivityLayout(position="(378,347)", name = "Assign7"), @ActivityLayout(isDecisionPoint = true, position="(647,260)", name="If"), @ActivityLayout(position="(374,573)", name="Assign7"), @ActivityLayout(position="(745,371)", name="Assign6"), @ActivityLayout(position="(629,372)", name="Assign5"), @ActivityLayout(position="(515,371)", name="Assign4"), @ActivityLayout(position="(374,371)", name="Assign3"), @ActivityLayout(position="(222,372)", name="Assign2"), @ActivityLayout(position="(371,193)", name = "Assign1")})
public class FrequentPath1 extends WorkflowBehaviour {

	public static final String REPLYCODEACTIVITY1_ACTIVITY = "ReplyCodeActivity1";
	public static final String RECEIVECODEACTIVITY1_ACTIVITY = "ReceiveCodeActivity1";
	public static final String WHILECODEACTIVITY1_ACTIVITY = "WhileCodeActivity1";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		CodeExecutionBehaviour whileCodeActivity1Activity = new CodeExecutionBehaviour(
				WHILECODEACTIVITY1_ACTIVITY, this);
		registerActivity(whileCodeActivity1Activity);
		CodeExecutionBehaviour receiveCodeActivity1Activity = new CodeExecutionBehaviour(
				RECEIVECODEACTIVITY1_ACTIVITY, this);
		registerActivity(receiveCodeActivity1Activity, INITIAL);
		CodeExecutionBehaviour ReplyCodeActivity1 = new CodeExecutionBehaviour(
				REPLYCODEACTIVITY1_ACTIVITY, this);
		registerActivity(ReplyCodeActivity1, FINAL);
	}
	private void defineTransitions() {
		registerTransition(new Transition(), RECEIVECODEACTIVITY1_ACTIVITY,
				WHILECODEACTIVITY1_ACTIVITY);
		registerTransition(new Transition(), WHILECODEACTIVITY1_ACTIVITY,
				REPLYCODEACTIVITY1_ACTIVITY);
	}
	
	protected void executeReceiveCodeActivity1() throws Exception {
//		System.out.println("Receive: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
//			System.out.println("Receive: in the catch: "+input);
		}
		
	}

	protected void executeWhileCodeActivity1() throws Exception {
//		System.out.println("While: "+input);
		if (((input>=1) && (input <=10))||((input>=21)&&(input<=100))){
			int n = 0;
			while (n<10) {Assign1(); n++;};
		}
	}
	public void Assign1(){
//		System.out.println("Assign1: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
//			System.out.println("test.centralized: Assign1: in the catch: "+input);
		}
	}

	protected void executeReplyCodeActivity1() throws Exception {
//		System.out.println("Reply: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
//			System.out.println("Reply: in the catch: "+input);
		}
	}
}