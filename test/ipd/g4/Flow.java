package test.ipd.g4;

import java.util.List;


import com.tilab.wade.performer.SubflowList;
import com.tilab.wade.performer.SubflowJoinBehaviour;
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

@WorkflowLayout(entryPoint = @MarkerLayout(position = "(541,26)", activityName = "Sequence1"), exitPoints = {@MarkerLayout(position="(246,446)", activityName="Assign7SubFlow"), @MarkerLayout(position="(410,426)", activityName="Reply"), @MarkerLayout(position="(408,744)", activityName="Assign8") }, activities={@ActivityLayout(size = "(112,50)", position="(207,373)", name="Assign7SubFlow"), @ActivityLayout(position="(273,221)", name="Join"), @ActivityLayout(position="(576,382)", name = "Sequence3"), @ActivityLayout(position="(562,297)", name = "Sequence2"), @ActivityLayout(position="(519,213)", name = "Sequence1"), @ActivityLayout(position="(375,236)", name="Workflow"), @ActivityLayout(position="(374,340)", name="Reply"), @ActivityLayout(position="(520,406)", name="Sequence3"), @ActivityLayout(position="(355,406)", name="Sequence2"), @ActivityLayout(position="(179,404)", name = "Sequence1"), @ActivityLayout(position="(377,267)", name="Flow"), @ActivityLayout(position="(328,219)", name="Assign"), @ActivityLayout(position="(378,347)", name = "Assign7"), @ActivityLayout(isDecisionPoint = true, position="(647,260)", name="If"), @ActivityLayout(position="(374,573)", name="Assign7"), @ActivityLayout(position="(745,371)", name="Assign6"), @ActivityLayout(position="(629,372)", name="Assign5"), @ActivityLayout(position="(515,371)", name="Assign4"), @ActivityLayout(position="(374,371)", name="Assign3"), @ActivityLayout(position="(222,372)", name="Assign2"), @ActivityLayout(position="(371,193)", name = "Assign1"), @ActivityLayout(size = "(102,50)", position="(389,172)", name = "Receive")})
public class Flow extends WorkflowBehaviour {

	public static final String ASSIGN7SUBFLOW_ACTIVITY = "Assign7SubFlow";
	public static final String JOIN_ACTIVITY = "Join";
	public static final String SEQUENCE3_ACTIVITY = "Sequence3";
	public static final String SEQUENCE2_ACTIVITY = "Sequence2";
	public static final String SEQUENCE1_ACTIVITY = "Sequence1";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		SubflowDelegationBehaviour Sequence1 = new SubflowDelegationBehaviour(
				SEQUENCE1_ACTIVITY, this);
		Sequence1.setSubflow("test.ipd.g4.Sequence1");
		Sequence1.setAsynch();
		registerActivity(Sequence1, INITIAL);
		SubflowDelegationBehaviour Sequence2 = new SubflowDelegationBehaviour(
				SEQUENCE2_ACTIVITY, this);
		Sequence2.setSubflow("test.ipd.g4.Sequence2");
		Sequence2.setAsynch();
		registerActivity(Sequence2);
		SubflowDelegationBehaviour Sequence3 = new SubflowDelegationBehaviour(
				SEQUENCE3_ACTIVITY, this);
		Sequence3.setSubflow("test.ipd.g4.Sequence3");
		Sequence3.setAsynch();
		registerActivity(Sequence3);
		SubflowJoinBehaviour joinActivity = new SubflowJoinBehaviour(
				JOIN_ACTIVITY, this);
		joinActivity.addSubflowDelegationActivity(SEQUENCE1_ACTIVITY,
				SubflowJoinBehaviour.ALL);
		joinActivity.addSubflowDelegationActivity(SEQUENCE2_ACTIVITY,
				SubflowJoinBehaviour.ALL);
		joinActivity.addSubflowDelegationActivity(SEQUENCE3_ACTIVITY,
				SubflowJoinBehaviour.ALL);
		registerActivity(joinActivity);
		SubflowDelegationBehaviour assign7SubFlowActivity = new SubflowDelegationBehaviour(
				ASSIGN7SUBFLOW_ACTIVITY, this);
		assign7SubFlowActivity.setSubflow("test.ipd.g4.Assign7");
		registerActivity(assign7SubFlowActivity, FINAL);

	}

	private void defineTransitions() {
		registerTransition(new Transition(), SEQUENCE1_ACTIVITY,
				SEQUENCE2_ACTIVITY);
		registerTransition(new Transition(), SEQUENCE2_ACTIVITY,
				SEQUENCE3_ACTIVITY);
		registerTransition(new Transition(), JOIN_ACTIVITY,
				ASSIGN7SUBFLOW_ACTIVITY);
		registerTransition(new Transition(), SEQUENCE3_ACTIVITY, JOIN_ACTIVITY);
	}

	protected void executeReceive() throws Exception {
	}

	protected void executeSequence1(Subflow s) throws Exception {
		s.fill("input", input);
		s.setPerformer("Sequence1_Agent");
		performSubflow(s);
	}

	protected void executeSequence2(Subflow s) throws Exception {
		s.fill("input", input);
		s.setPerformer("Sequence2_Agent");
		performSubflow(s);
	}

	protected void executeSequence3(Subflow s) throws Exception {
		s.fill("input", input);
		s.setPerformer("Sequence3_Agent");
		performSubflow(s);
	}

	protected void executeJoin(SubflowList ss) throws Exception {
		Subflow s;
		List<Subflow> ls;
		ls = (List<Subflow>) ss.get(SEQUENCE1_ACTIVITY);
		for (Subflow tmpS : ls) {
//			output = ((Integer)tmpS.extract("output")).intValue();
//			System.out.println("test.ipd.g4: Flow: Seq1: Join: return response is: "+output);
		}
		ls = (List<Subflow>) ss.get(SEQUENCE2_ACTIVITY);
		for (Subflow tmpS : ls) {
//			output = ((Integer)tmpS.extract("output")).intValue();
//			System.out.println("test.ipd.g4: Flow: Seq2: Join: return response is: "+output);
		}
		ls = (List<Subflow>) ss.get(SEQUENCE3_ACTIVITY);
		for (Subflow tmpS : ls) {
//			output = ((Integer)tmpS.extract("output")).intValue();
//			System.out.println("test.ipd.g4: Flow: Seq3: Join: return response is: "+output);
		}
	}

	protected void executeAssign7SubFlow(Subflow s) throws Exception {
		s.fill("input", input);
		s.setPerformer("Assign7_Agent");
		performSubflow(s);
//		output = ((Integer)s.extract("output")).intValue();
//		System.out.println("test.ipd.g4: Flow-Join: return response from Assign7 is: "+output);
	}
}
