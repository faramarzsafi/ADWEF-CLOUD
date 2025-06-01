package test.fpd;

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


@WorkflowLayout(exitPoints = { @MarkerLayout(position="(503,471)", activityName="Assign6Subflow"), @MarkerLayout(position="(170,473)", activityName="Assign4Subflow"), @MarkerLayout(position = "(346,475)", activityName = "Assign5Subflow") }, activities={@ActivityLayout(size = "(124,50)", position="(456,405)", name = "Assign6Subflow"), @ActivityLayout(size = "(124,50)", position="(297,404)", name = "Assign5Subflow"), @ActivityLayout(size = "(123,50)", position="(122,405)", name = "Assign4Subflow"), @ActivityLayout(isDecisionPoint = true, position="(334,282)", name="IFRouteActivity1")})
public class IF extends WorkflowBehaviour {

	public static final String ASSIGN6SUBFLOW_ACTIVITY = "Assign6Subflow";
	public static final String ASSIGN5SUBFLOW_ACTIVITY = "Assign5Subflow";
	public static final String ASSIGN4SUBFLOW_ACTIVITY = "Assign4Subflow";
	public static final String IFROUTEACTIVITY1_ACTIVITY = "IFRouteActivity1";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		RouteActivityBehaviour iFRouteActivity1Activity = new RouteActivityBehaviour(
				IFROUTEACTIVITY1_ACTIVITY, this);
		registerActivity(iFRouteActivity1Activity, INITIAL);
		SubflowDelegationBehaviour Assign4Subflow = new SubflowDelegationBehaviour(
				ASSIGN4SUBFLOW_ACTIVITY, this);
		Assign4Subflow.setSubflow("test.fpd.Assign4");
		registerActivity(Assign4Subflow, FINAL);
		SubflowDelegationBehaviour Assign5Subflow = new SubflowDelegationBehaviour(
				ASSIGN5SUBFLOW_ACTIVITY, this);
		Assign5Subflow.setSubflow("test.fpd.Assign5");
		registerActivity(Assign5Subflow, FINAL);
		SubflowDelegationBehaviour Assign6Subflow = new SubflowDelegationBehaviour(
				ASSIGN6SUBFLOW_ACTIVITY, this);
		Assign6Subflow.setSubflow("test.fpd.Assign6");
		registerActivity(Assign6Subflow, FINAL);
	}

	private void defineTransitions() {
		registerTransition(new Transition("IFRouteActivity1ToAssign4Subflow", this), IFROUTEACTIVITY1_ACTIVITY,
				ASSIGN4SUBFLOW_ACTIVITY);
		registerTransition(new Transition("IFRouteActivity1ToAssign5Subflow", this), IFROUTEACTIVITY1_ACTIVITY,
				ASSIGN5SUBFLOW_ACTIVITY);
		registerTransition(new Transition("IFRouteActivity1ToAssign6Subflow", this), IFROUTEACTIVITY1_ACTIVITY,
				ASSIGN6SUBFLOW_ACTIVITY);
	}

	protected void executeAssign4Subflow(Subflow s) throws Exception {
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
		s.setPerformer("Assign4_Agent");
		performSubflow(s);
//		output = ((Integer)s.extract("output")).intValue();
//		System.out.println("IF: Branch1: return response is: "+output);
	}

	protected void executeAssign5Subflow(Subflow s) throws Exception {
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
		s.setPerformer("Assign5_Agent");
		performSubflow(s);
//		output = ((Integer)s.extract("output")).intValue();
//		System.out.println("IF: Branch2: return response is: "+output);
	}

	protected void executeAssign6Subflow(Subflow s) throws Exception {
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
		s.setPerformer("Assign6_Agent");
		performSubflow(s);
//		output = ((Integer)s.extract("output")).intValue();
//		System.out.println("IF:  Branch3: return response is: "+output);
	}

	protected boolean checkIFRouteActivity1ToAssign4Subflow() {
//		System.out.println("IF: Condition1");
		if (input <= 10) return true; else return false;
	}

	protected boolean checkIFRouteActivity1ToAssign5Subflow() {
//		System.out.println("IF: Condition2");
		if ((input > 10)&&(input <= 20)) return true; else return false;
	}

	protected boolean checkIFRouteActivity1ToAssign6Subflow() {
//		System.out.println("IF: Condition3");
		if (input > 20) return true; else return false;
	}
}
