package test.ipd.g3;

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

@WorkflowLayout(entryPoint = @MarkerLayout(position = "(533,74)", activityName = "Agent1Workflow"), exitPoints = {@MarkerLayout(position="(306,644)", activityName="Assign7SubFlow"), @MarkerLayout(position="(410,426)", activityName="Reply"), @MarkerLayout(position="(408,744)", activityName="Assign8") }, activities={@ActivityLayout(size = "(112,50)", position="(257,554)", name="Assign7SubFlow"), @ActivityLayout(position="(214,272)", name="Join"), @ActivityLayout(position="(497,506)", name = "Agent3Workflow"), @ActivityLayout(position="(570,321)", name = "Agent2Workflow"), @ActivityLayout(position="(595,153)", name = "Agent1Workflow"), @ActivityLayout(position="(375,236)", name="Workflow"), @ActivityLayout(position="(374,340)", name="Reply"), @ActivityLayout(position="(520,406)", name="Sequence3"), @ActivityLayout(position="(355,406)", name="Sequence2"), @ActivityLayout(position="(179,404)", name = "Sequence1"), @ActivityLayout(position="(377,267)", name="Flow"), @ActivityLayout(position="(328,219)", name="Assign"), @ActivityLayout(position="(378,347)", name = "Assign7"), @ActivityLayout(isDecisionPoint = true, position="(647,260)", name="If"), @ActivityLayout(position="(374,573)", name="Assign7"), @ActivityLayout(position="(745,371)", name="Assign6"), @ActivityLayout(position="(629,372)", name="Assign5"), @ActivityLayout(position="(515,371)", name="Assign4"), @ActivityLayout(position="(374,371)", name="Assign3"), @ActivityLayout(position="(222,372)", name="Assign2"), @ActivityLayout(position="(371,193)", name = "Assign1"), @ActivityLayout(size = "(102,50)", position="(389,172)", name = "Receive")})
public class Flow extends WorkflowBehaviour {

	public static final String ASSIGN7SUBFLOW_ACTIVITY = "Assign7SubFlow";
	public static final String JOIN_ACTIVITY = "Join";
	public static final String AGENT3WORKFLOW_ACTIVITY = "Agent3Workflow";
	public static final String AGENT2WORKFLOW_ACTIVITY = "Agent2Workflow";
	public static final String AGENT1WORKFLOW_ACTIVITY = "Agent1Workflow";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		SubflowDelegationBehaviour Agent1Workflow = new SubflowDelegationBehaviour(
				AGENT1WORKFLOW_ACTIVITY, this);
		Agent1Workflow.setAsynch();
		Agent1Workflow.setSubflow("test.ipd.g3.Agent1Workflow");
		registerActivity(Agent1Workflow, INITIAL);
		SubflowDelegationBehaviour Agent2Workflow = new SubflowDelegationBehaviour(
				AGENT2WORKFLOW_ACTIVITY, this);
		Agent2Workflow.setAsynch();
		Agent2Workflow.setSubflow("test.ipd.g3.Agent2Workflow");
		registerActivity(Agent2Workflow);
		SubflowDelegationBehaviour Agent3Workflow = new SubflowDelegationBehaviour(
				AGENT3WORKFLOW_ACTIVITY, this);
		Agent3Workflow.setAsynch();
		Agent3Workflow.setSubflow("test.ipd.g3.Agent3Workflow");
		registerActivity(Agent3Workflow);
		SubflowJoinBehaviour joinActivity = new SubflowJoinBehaviour(
				JOIN_ACTIVITY, this);
		joinActivity.addSubflowDelegationActivity(AGENT1WORKFLOW_ACTIVITY,
				SubflowJoinBehaviour.ALL);
		joinActivity.addSubflowDelegationActivity(AGENT2WORKFLOW_ACTIVITY,
				SubflowJoinBehaviour.ALL);
		joinActivity.addSubflowDelegationActivity(AGENT3WORKFLOW_ACTIVITY,
				SubflowJoinBehaviour.ALL);
		registerActivity(joinActivity);
		SubflowDelegationBehaviour assign7SubFlowActivity = new SubflowDelegationBehaviour(
				ASSIGN7SUBFLOW_ACTIVITY, this);
		assign7SubFlowActivity.setSubflow("test.ipd.g3.Assign7");
		registerActivity(assign7SubFlowActivity, FINAL);

	}

	private void defineTransitions() {
		registerTransition(new Transition(), AGENT1WORKFLOW_ACTIVITY,
				AGENT2WORKFLOW_ACTIVITY);
		registerTransition(new Transition(), AGENT2WORKFLOW_ACTIVITY,
				AGENT3WORKFLOW_ACTIVITY);
		registerTransition(new Transition(), JOIN_ACTIVITY,
				ASSIGN7SUBFLOW_ACTIVITY);
		registerTransition(new Transition(), AGENT3WORKFLOW_ACTIVITY, JOIN_ACTIVITY);
	}

	protected void executeReceive() throws Exception {
	}

	protected void executeAgent1Workflow(Subflow s) throws Exception {
		s.fill("input", input);
		s.setPerformer("Agent1Workflow_Agent");
		performSubflow(s);
	}

	protected void executeAgent2Workflow(Subflow s) throws Exception {
		s.fill("input", input);
		s.setPerformer("Agent2Workflow_Agent");
		performSubflow(s);
	}

	protected void executeAgent3Workflow(Subflow s) throws Exception {
		s.fill("input", input);
		s.setPerformer("Agent3Workflow_Agent");
		performSubflow(s);
	}

	protected void executeJoin(SubflowList ss) throws Exception {
		Subflow s;
		List<Subflow> ls;
		ls = (List<Subflow>) ss.get(AGENT1WORKFLOW_ACTIVITY);
		for (Subflow tmpS : ls) {
//			output = ((Integer)tmpS.extract("output")).intValue();
//			System.out.println("test.ipd.g3: Flow: Join: return response from Sequence1 is: "+output);
		}
		ls = (List<Subflow>) ss.get(AGENT2WORKFLOW_ACTIVITY);
		for (Subflow tmpS : ls) {
//			output = ((Integer)tmpS.extract("output")).intValue();
//			System.out.println("test.ipd.g3: Flow: Join: return response  from Sequence2 is: "+output);
		}
		ls = (List<Subflow>) ss.get(AGENT3WORKFLOW_ACTIVITY);
		for (Subflow tmpS : ls) {
//			output = ((Integer)tmpS.extract("output")).intValue();
//			System.out.println("test.ipd.g3: Flow: Join: return response  from Sequence3 is: "+output);
		}
	}

	protected void executeAssign7SubFlow(Subflow s) throws Exception {
		s.fill("input", input);
		s.setPerformer("Assign7_Agent");
		performSubflow(s);
//		output = ((Integer)s.extract("output")).intValue();
//		System.out.println("test.ipd.g3:Flow-Join: return response from Assign7 is: "+output);
	}
}
