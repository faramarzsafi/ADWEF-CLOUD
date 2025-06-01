package test.ipd.g4;


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

@WorkflowLayout(entryPoint = @MarkerLayout(position = "(335,108)", activityName = "Assign1Code"), exitPoints = {@MarkerLayout(position="(337,319)", activityName="Assign1Subflow"), @MarkerLayout(position="(410,426)", activityName="Reply"), @MarkerLayout(position="(408,744)", activityName="Assign8") }, activities={@ActivityLayout(size = "(113,50)", position="(294,243)", name="Assign1Subflow"), @ActivityLayout(position="(297,168)", name="Assign1Code"), @ActivityLayout(position="(375,236)", name="Workflow"), @ActivityLayout(position="(374,340)", name="Reply"), @ActivityLayout(position="(520,406)", name="Sequence3"), @ActivityLayout(position="(355,406)", name="Sequence2"), @ActivityLayout(position="(179,404)", name = "Sequence1"), @ActivityLayout(position="(377,267)", name="Flow"), @ActivityLayout(position="(328,219)", name="Assign"), @ActivityLayout(position="(378,347)", name = "Assign7"), @ActivityLayout(isDecisionPoint = true, position="(647,260)", name="If"), @ActivityLayout(position="(374,573)", name="Assign7"), @ActivityLayout(position="(745,371)", name="Assign6"), @ActivityLayout(position="(629,372)", name="Assign5"), @ActivityLayout(position="(515,371)", name="Assign4"), @ActivityLayout(position="(374,371)", name="Assign3"), @ActivityLayout(position="(222,372)", name="Assign2"), @ActivityLayout(position="(371,193)", name = "Assign1"), @ActivityLayout(size = "(102,50)", position="(389,172)", name = "Receive")})
public class Assign1 extends WorkflowBehaviour {

	
	public static final String ASSIGN1SUBFLOW_ACTIVITY = "Assign1Subflow";
	public static final String ASSIGN1CODE_ACTIVITY = "Assign1Code";
	
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		CodeExecutionBehaviour assign1CodeActivity = new CodeExecutionBehaviour(
				ASSIGN1CODE_ACTIVITY, this);
		registerActivity(assign1CodeActivity, INITIAL);
		SubflowDelegationBehaviour assign1SubflowActivity = new SubflowDelegationBehaviour(
				ASSIGN1SUBFLOW_ACTIVITY, this);
		assign1SubflowActivity.setSubflow("test.ipd.g4.Flow");
		registerActivity(assign1SubflowActivity, FINAL);
	}

	private void defineTransitions() {
		registerTransition(new Transition(), ASSIGN1CODE_ACTIVITY,
				ASSIGN1SUBFLOW_ACTIVITY);
	}

	protected void executeAssign1Code() throws Exception {
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.ipd.g4: Assign1 : in the catch: "+e.getMessage()+e.getCause());
		}
	}

	protected void executeAssign1Subflow(Subflow s) throws Exception {
		s.fill("input", input);
		s.setPerformer("Flow_Agent");
		performSubflow(s);
//		output = ((Integer)s.extract("output")).intValue();
//		System.out.println("Assign1: return response is: "+output);
	}
}