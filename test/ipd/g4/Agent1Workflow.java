package test.ipd.g4;


import com.tilab.wade.performer.RouteActivityBehaviour;
import com.tilab.wade.performer.Transition;
import com.tilab.wade.performer.layout.MarkerLayout;

import com.tilab.wade.performer.layout.ActivityLayout;
import com.tilab.wade.performer.layout.WorkflowLayout;
import com.tilab.wade.performer.CodeExecutionBehaviour;
import com.tilab.wade.performer.FormalParameter;
import com.tilab.wade.performer.Subflow;
import com.tilab.wade.performer.SubflowDelegationBehaviour;
import com.tilab.wade.performer.WorkflowBehaviour;
import common.share;

@WorkflowLayout(exitPoints = {@MarkerLayout(position="(165,200)", activityName="Assign4Subflow"), @MarkerLayout(position="(408,744)", activityName="Assign8") }, activities={@ActivityLayout(position="(132,119)", name="Assign4Subflow"), @ActivityLayout(position="(375,236)", name = "Agent1Workflow"), @ActivityLayout(position="(520,406)", name="Sequence3"), @ActivityLayout(position="(355,406)", name="Sequence2"), @ActivityLayout(position="(179,404)", name = "Sequence1"), @ActivityLayout(position="(377,267)", name="Flow"), @ActivityLayout(position="(328,219)", name="Assign"), @ActivityLayout(position="(378,347)", name = "Assign7"), @ActivityLayout(isDecisionPoint = true, position="(647,260)", name="If"), @ActivityLayout(position="(374,573)", name="Assign7"), @ActivityLayout(position="(745,371)", name="Assign6"), @ActivityLayout(position="(629,372)", name="Assign5"), @ActivityLayout(position="(515,371)", name="Assign4"), @ActivityLayout(position="(374,371)", name="Assign3"), @ActivityLayout(position="(222,372)", name="Assign2"), @ActivityLayout(position="(371,193)", name = "Assign1")})
public class Agent1Workflow extends WorkflowBehaviour {

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
		CodeExecutionBehaviour Agent1Workflow = new CodeExecutionBehaviour(
				AGENT1WORKFLOW_ACTIVITY, this);
		registerActivity(Agent1Workflow, INITIAL_AND_FINAL);
		
	}

	private void defineTransitions() {
	}
	
	protected void executeAgent1Workflow() throws Exception {
	
		IF();
	}
	
	public void IF(){
		
		try {
			com.tilab.wade.performer.Subflow s;
			//If
			if ((input>=0) && (input <=10)){
	//			Assign4();
				s = new com.tilab.wade.performer.Subflow("test.ipd.g4.Assign4");
				s.fill("input", input);
				s.setPerformer("Assign4_Agent");
				performSubflow(s);
//				output = ((Integer)s.extract("output")).intValue();
//				System.out.println("test.ipd.g4: Flow: Branch 1: Assign4: the output  "+output);
			}else if ((input>10) && (input <=20)){
	//			Assign5();
				s = new com.tilab.wade.performer.Subflow("test.ipd.g4.Assign5");
				s.fill("input", input);
				s.setPerformer("Assign5_Agent");
				performSubflow(s);
//				output = ((Integer)s.extract("output")).intValue();
//				System.out.println("test.ipd.g4: Flow: Branch 2: Assign5: the output  "+output);
	
			}else{
				Assign6();
			}
		} catch (Exception e) {
			System.out.println("test.ipd.g4: Flow: IF : in the catch: "+e.getMessage()+e.getCause());
		}
		
	}

	public void Assign6(){
//		System.out.println("test.ipd.g4: Assign6: the output"+output);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.ipd.g4: Flow: Assign6 : in the catch: "+e.getMessage()+e.getCause());
		}
	}
}
