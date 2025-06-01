package Block_Structured.If.hpd.l2;


import com.tilab.wade.performer.layout.TransitionLayout;
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


@WorkflowLayout(entryPoint = @MarkerLayout(position = "(312,100)", activityName = "IfRouteActivity1"), transitions = { }, exitPoints = {@MarkerLayout(position="(317,461)", activityName="IfSubflowActivity3") }, activities={@ActivityLayout(position="(278,367)", name="IfSubflowActivity3"), @ActivityLayout(position="(388,275)", name="IfSubflowActivity2"), @ActivityLayout(size = "(122,50)", position="(148,274)", name="IfSubflowActivity1"), @ActivityLayout(isDecisionPoint = true, position="(301,172)", name="IfRouteActivity1")})
public class If extends WorkflowBehaviour {

	public static final String IFSUBFLOWACTIVITY3_ACTIVITY = "IfSubflowActivity3";
	public static final String IFSUBFLOWACTIVITY2_ACTIVITY = "IfSubflowActivity2";
	public static final String IFSUBFLOWACTIVITY1_ACTIVITY = "IfSubflowActivity1";
	public static final String IFROUTEACTIVITY1_ACTIVITY = "IfRouteActivity1";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		RouteActivityBehaviour ifRouteActivity1Activity = new RouteActivityBehaviour(
				IFROUTEACTIVITY1_ACTIVITY, this);
		registerActivity(ifRouteActivity1Activity, INITIAL);
		SubflowDelegationBehaviour ifSubflowActivity1Activity = new SubflowDelegationBehaviour(
				IFSUBFLOWACTIVITY1_ACTIVITY, this);
		ifSubflowActivity1Activity
				.setSubflow("Block_Structured.If.hpd.l2.Assign1");
		registerActivity(ifSubflowActivity1Activity);
		SubflowDelegationBehaviour ifSubflowActivity2Activity = new SubflowDelegationBehaviour(
				IFSUBFLOWACTIVITY2_ACTIVITY, this);
		ifSubflowActivity2Activity
				.setSubflow("Block_Structured.If.hpd.l2.Assign2");
		registerActivity(ifSubflowActivity2Activity);
		SubflowDelegationBehaviour ifSubflowActivity3Activity = new SubflowDelegationBehaviour(
				IFSUBFLOWACTIVITY3_ACTIVITY, this);
		ifSubflowActivity3Activity
				.setSubflow("Block_Structured.If.hpd.l2.Reply");
		registerActivity(ifSubflowActivity3Activity, FINAL);

	}

	private void defineTransitions() {
		registerTransition(new Transition("IfRouteActivity1ToIfSubflowActivity1", this), IFROUTEACTIVITY1_ACTIVITY,
				IFSUBFLOWACTIVITY1_ACTIVITY);
		registerTransition(new Transition("IfRouteActivity1ToIfSubflowActivity2", this), IFROUTEACTIVITY1_ACTIVITY,
				IFSUBFLOWACTIVITY2_ACTIVITY);
		registerTransition(new Transition(), IFSUBFLOWACTIVITY2_ACTIVITY,
				IFSUBFLOWACTIVITY3_ACTIVITY);
		registerTransition(new Transition(), IFSUBFLOWACTIVITY1_ACTIVITY,
				IFSUBFLOWACTIVITY3_ACTIVITY);
	
	}
	protected boolean checkIfRouteActivity1ToIfSubflowActivity1() {
		
		if (((input>=1) && (input <=10))||((input>=21)&&(input<=100)))return true;
//		if ((input>=1) && (input <=50)) return true;
		else return false;
	}

	protected boolean checkIfRouteActivity1ToIfSubflowActivity2() {
		
		if ((input>10) && (input <=20))return true;
//		if ((input>50) && (input <=100)) return true;
		else return false;
	}

	protected void executeIfSubflowActivity1(Subflow s) throws Exception {
//		System.out.println("If: Call Assign1 ");
		try {
		Thread.sleep(share.ACTIVITY_SLEEP);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
//		s.setPerformer("Assign1_Agent");
		s.setPerformer(ContainerManagement.getContainer("Assign1_Agent", "Block_Structured.If.hpd.l2.Assign1"));
		performSubflow(s);
//		output = ((Integer)s.extract("output")).intValue();
//		System.out.println("If-condition:run "+output);
	}
	
	protected void executeIfSubflowActivity2(Subflow s) throws Exception {
//		System.out.println("If: Call Assign2 ");
			try {
				Thread.sleep(share.ACTIVITY_SLEEP);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			s.fill("input", input);
			s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
//			s.setPerformer("Assign2_Agent");
			s.setPerformer(ContainerManagement.getContainer("Assign2_Agent", "Block_Structured.If.hpd.l2.Assign2"));
			performSubflow(s);
//			output = ((Integer)s.extract("output")).intValue();
//			System.out.println("IF:else:Run "+output);
	}

	protected void executeIfSubflowActivity3(Subflow s) throws Exception {
//		System.out.println("If: Call Reply ");
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.fill("input", input);
		s.fill("VARIABLE_MESSAGE", VARIABLE_MESSAGE);
//		s.setPerformer("Reply_Agent");
		s.setPerformer(ContainerManagement.getContainer("Reply_Agent", "Block_Structured.If.hpd.l2.Reply"));
		performSubflow(s);
//		output = ((Integer)s.extract("output")).intValue();
	}
}