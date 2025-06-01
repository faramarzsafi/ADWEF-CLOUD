package LoanTaking.hpd.l2;


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


@WorkflowLayout(exitPoints = { }, activities={@ActivityLayout(size = "(184,50)", position="(578,271)", name="IfSubflowActivity2Else"), @ActivityLayout(size = "(193,50)", position="(216,267)", name="IfSubflowActivity2IfCondition"), @ActivityLayout(isDecisionPoint = true, position="(449,166)", name="IfRouteActivity1"), @ActivityLayout(size = "(205,50)", position="(371,368)", name = "IfSubflowActivity1Reply")})
public class If extends WorkflowBehaviour {

	public static final String IFSUBFLOWACTIVITY2ELSE_ACTIVITY = "IfSubflowActivity2Else";
	public static final String IFSUBFLOWACTIVITY2IFCONDITION_ACTIVITY = "IfSubflowActivity2IfCondition";
	public static final String IFROUTEACTIVITY1_ACTIVITY = "IfRouteActivity1";
	public static final String IFSUBFLOWACTIVITY1REPLY_ACTIVITY = "IfSubflowActivity1Reply";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	boolean approval;
	
	private void defineActivities() {
		SubflowDelegationBehaviour IfSubflowActivity1Reply = new SubflowDelegationBehaviour(
				IFSUBFLOWACTIVITY1REPLY_ACTIVITY, this);
		IfSubflowActivity1Reply.setSubflow("LoanTaking.hpd.l2.Reply");
		registerActivity(IfSubflowActivity1Reply, FINAL);
		RouteActivityBehaviour ifRouteActivity1Activity = new RouteActivityBehaviour(
				IFROUTEACTIVITY1_ACTIVITY, this);
		registerActivity(ifRouteActivity1Activity, INITIAL);
		SubflowDelegationBehaviour ifSubflowActivity2IfConditionActivity = new SubflowDelegationBehaviour(
				IFSUBFLOWACTIVITY2IFCONDITION_ACTIVITY, this);
		ifSubflowActivity2IfConditionActivity
				.setSubflow("LoanTaking.hpd.l2.Assign4");
		registerActivity(ifSubflowActivity2IfConditionActivity);
		SubflowDelegationBehaviour ifSubflowActivity2ElseActivity = new SubflowDelegationBehaviour(
				IFSUBFLOWACTIVITY2ELSE_ACTIVITY, this);
		ifSubflowActivity2ElseActivity.setSubflow("LoanTaking.hpd.l2.Assign5");
		registerActivity(ifSubflowActivity2ElseActivity);

	}

	private void defineTransitions() {
		registerTransition(new Transition("IfRouteActivity1ToIfSubflowActivity2IfCondition", this), IFROUTEACTIVITY1_ACTIVITY,
				IFSUBFLOWACTIVITY2IFCONDITION_ACTIVITY);
		registerTransition(new Transition("IfRouteActivity1ToIfSubflowActivity2Else", this), IFROUTEACTIVITY1_ACTIVITY,
				IFSUBFLOWACTIVITY2ELSE_ACTIVITY);
		registerTransition(new Transition(),
				IFSUBFLOWACTIVITY2IFCONDITION_ACTIVITY,
				IFSUBFLOWACTIVITY1REPLY_ACTIVITY);
		registerTransition(new Transition(), IFSUBFLOWACTIVITY2ELSE_ACTIVITY,
				IFSUBFLOWACTIVITY1REPLY_ACTIVITY);
	
	}

	protected void executeIfSubflowActivity1Reply(Subflow s) throws Exception {
//		System.out.println("Reply: Run");
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		s.setPerformer("Reply_Agent");
		s.setPerformer(ContainerManagement.getContainer("Reply_Agent", "LoanTaking.hpd.l2.Reply"));
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
		performSubflow(s);
	}

	protected void executeIfSubflowActivity2IfCondition(Subflow s)
			throws Exception {
//			System.out.println("If branch executed");
//			s.setPerformer("Assign4_Agent");
			s.setPerformer(ContainerManagement.getContainer("Assign4_Agent", "LoanTaking.hpd.l2.Assign4"));
			s.fill("input", input);
			s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
			performSubflow(s);
	}

	protected void executeIfSubflowActivity2Else(Subflow s) throws Exception {
//		System.out.println("Else branch executed");
//		s.setPerformer("Assign5_Agent");
		s.setPerformer(ContainerManagement.getContainer("Assign5_Agent", "LoanTaking.hpd.l2.Assign5" ));
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
		performSubflow(s);
	}

	protected boolean checkIfRouteActivity1ToIfSubflowActivity2IfCondition() {
		if (((input>=1) && (input <=10))||((input>=21)&&(input<=100)))
//		if ((input>=1) && (input <=50))
			return true;
		else
			return false;
	}

	protected boolean checkIfRouteActivity1ToIfSubflowActivity2Else() {
		if ((input>10) && (input <=20))
//		if ((input>50) && (input <=100))
			return true;
		else 
			return false;
		
	}
}