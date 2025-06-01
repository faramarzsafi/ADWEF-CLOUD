package Block_Structured.Pick.hpd.l2;



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


@WorkflowLayout(entryPoint = @MarkerLayout(position = "(312,100)", activityName = "PickRouteActivity1"), transitions = { }, exitPoints = {@MarkerLayout(position="(317,461)", activityName="IfSubflowActivity3") }, activities={@ActivityLayout(position="(278,367)", name="IfSubflowActivity3"), @ActivityLayout(position="(388,275)", name = "PickSubflowActivity2"), @ActivityLayout(size = "(122,50)", position="(148,274)", name = "PickSubflowActivity1"), @ActivityLayout(isDecisionPoint = true, position="(301,172)", name = "PickRouteActivity1")})
public class Pick extends WorkflowBehaviour {

	public static final String IFSUBFLOWACTIVITY3_ACTIVITY = "IfSubflowActivity3";
	public static final String PICKSUBFLOWACTIVITY2_ACTIVITY = "PickSubflowActivity2";
	public static final String PICKSUBFLOWACTIVITY1_ACTIVITY = "PickSubflowActivity1";
	public static final String PICKROUTEACTIVITY1_ACTIVITY = "PickRouteActivity1";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		RouteActivityBehaviour PickRouteActivity1 = new RouteActivityBehaviour(
				PICKROUTEACTIVITY1_ACTIVITY, this);
		registerActivity(PickRouteActivity1, INITIAL);
		SubflowDelegationBehaviour PickSubflowActivity1 = new SubflowDelegationBehaviour(
				PICKSUBFLOWACTIVITY1_ACTIVITY, this);
		PickSubflowActivity1
				.setSubflow("Block_Structured.Pick.hpd.l2.Assign1");
		registerActivity(PickSubflowActivity1);
		SubflowDelegationBehaviour PickSubflowActivity2 = new SubflowDelegationBehaviour(
				PICKSUBFLOWACTIVITY2_ACTIVITY, this);
		PickSubflowActivity2
				.setSubflow("Block_Structured.Pick.hpd.l2.Assign2");
		registerActivity(PickSubflowActivity2);
		SubflowDelegationBehaviour ifSubflowActivity3Activity = new SubflowDelegationBehaviour(
				IFSUBFLOWACTIVITY3_ACTIVITY, this);
		ifSubflowActivity3Activity
				.setSubflow("Block_Structured.Pick.hpd.l2.Reply");
		registerActivity(ifSubflowActivity3Activity, FINAL);

	}

	private void defineTransitions() {
		registerTransition(new Transition("PickRouteActivity1ToPickSubflowActivity1", this), PICKROUTEACTIVITY1_ACTIVITY,
				PICKSUBFLOWACTIVITY1_ACTIVITY);
		registerTransition(new Transition("PickRouteActivity1ToPickSubflowActivity2", this), PICKROUTEACTIVITY1_ACTIVITY,
				PICKSUBFLOWACTIVITY2_ACTIVITY);
		registerTransition(new Transition(), PICKSUBFLOWACTIVITY2_ACTIVITY,
				IFSUBFLOWACTIVITY3_ACTIVITY);
		registerTransition(new Transition(), PICKSUBFLOWACTIVITY1_ACTIVITY,
				IFSUBFLOWACTIVITY3_ACTIVITY);
	
	}
	protected boolean checkPickRouteActivity1ToPickSubflowActivity1() {
		
		if (((input>=0) && (input <=20))||((input>50)&&(input<=100)))return true;//onMessage
		else return false;
	}

	protected boolean checkPickRouteActivity1ToPickSubflowActivity2() {
		
		if ((input>20) && (input <=50))return true;//onAlarm
		else return false;
	}

	protected void executePickSubflowActivity1(Subflow s) throws Exception {
//		System.out.println("Pick: Call Assign1: "+input);
		try {
		Thread.sleep(share.ACTIVITY_SLEEP);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		s.setPerformer("Assign1_Agent");
		s.setPerformer(ContainerManagement.getContainer("Assign1_Agent", "Block_Structured.Pick.hpd.l2.Assign1"));
		
		s.fill("input", input);
		performSubflow(s);
//		output = ((Integer)s.extract("output")).intValue();
//		System.out.println("If-condition:run "+output);
	}
	
	protected void executePickSubflowActivity2(Subflow s) throws Exception {
//		System.out.println("Pick: Call Assign2 ");
			try {
				Thread.sleep(share.ACTIVITY_SLEEP);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			s.setPerformer("Assign2_Agent");
			s.setPerformer(ContainerManagement.getContainer("Assign2_Agent", "Block_Structured.Pick.hpd.l2.Assign2"));
			s.fill("input", input);
			performSubflow(s);
	}

	protected void executeIfSubflowActivity3(Subflow s) throws Exception {
//		System.out.println("Pick: Call Reply ");
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		s.setPerformer("Reply_Agent");
		s.setPerformer(ContainerManagement.getContainer("Reply_Agent", "Block_Structured.Pick.hpd.l2.Reply"));
		s.fill("input", input);
		performSubflow(s);
	}



}