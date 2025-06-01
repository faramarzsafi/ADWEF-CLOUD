package test.ipd.g2;


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

@WorkflowLayout(exitPoints = {@MarkerLayout(position="(165,200)", activityName="Assign4Subflow"), @MarkerLayout(position="(408,744)", activityName="Assign8") }, activities={@ActivityLayout(position="(132,119)", name="Assign4Subflow"), @ActivityLayout(position="(375,236)", name="Workflow"), @ActivityLayout(position="(520,406)", name="Sequence3"), @ActivityLayout(position="(355,406)", name="Sequence2"), @ActivityLayout(position="(179,404)", name = "Sequence1"), @ActivityLayout(position="(377,267)", name="Flow"), @ActivityLayout(position="(328,219)", name="Assign"), @ActivityLayout(position="(378,347)", name = "Assign7"), @ActivityLayout(isDecisionPoint = true, position="(647,260)", name="If"), @ActivityLayout(position="(374,573)", name="Assign7"), @ActivityLayout(position="(745,371)", name="Assign6"), @ActivityLayout(position="(629,372)", name="Assign5"), @ActivityLayout(position="(515,371)", name="Assign4"), @ActivityLayout(position="(374,371)", name="Assign3"), @ActivityLayout(position="(222,372)", name="Assign2"), @ActivityLayout(position="(371,193)", name = "Assign1")})
public class Agent1Workflow extends WorkflowBehaviour {

	public static final String WORKFLOW_ACTIVITY = "Workflow";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	//Local Variables
	private int a = 0; 
	private int b = 0;
	private int c = 0;	

	private void defineActivities() {
		CodeExecutionBehaviour workflowActivity = new CodeExecutionBehaviour(
				WORKFLOW_ACTIVITY, this);
		registerActivity(workflowActivity, INITIAL_AND_FINAL);
		
	}

	private void defineTransitions() {
	}
	
	

	protected void executeWorkflow() throws Exception {
	
		Flow();
		Assign7();//Call it as a Subflow()
	}
	

	public void Assign2(){
//		System.out.println("test.ipd.g2: Assign2: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.ipd.g2: Assign2 : in the catch: "+e.getMessage()+e.getCause());
		}
//		a = input;
	}
	
	public void Assign3(){
//		System.out.println("test.ipd.g2: Assign3: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.ipd.g2: Assign3 : in the catch: "+e.getMessage()+e.getCause());
		}
//		a = input;		
	}
	
public void Flow(){
		
		try {
		Sequence1 sq1 = new Sequence1(this, input);
		Sequence2 sq2 = new Sequence2(this, input);
		Sequence3 sq3 = new Sequence3(this, input);
		
		sq1.start();
		sq2.start();
		sq3.start();

		sq1.join();
		sq2.join();
		sq3.join();
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.ipd.g2: Flow: in the catch: "+input);
		}

	}

	public void IF(){
		try {
			
		com.tilab.wade.performer.Subflow s;
		if ((input>=0) && (input <=10)){
	//		Assign4();
	//		System.out.println("test.ipd.g0: : before calling Assign4: "+input);
			s = new com.tilab.wade.performer.Subflow("test.ipd.g2.Assign4");
			s.fill("input", input);
			s.setPerformer("Assign4_Agent");
			performSubflow(s);
//			output = ((Integer)s.extract("output")).intValue();
	//		System.out.println("test.ipd.g2: Flow: Branch 1: Assign4: the output  "+output);
		}else if ((input>10) && (input <=20)){
	//		Assign5();
	//		System.out.println("test.ipd.g2: before calling Assign5: "+input);
			s = new com.tilab.wade.performer.Subflow("test.ipd.g2.Assign5");
			s.fill("input", input);
			s.setPerformer("Assign5_Agent");
			performSubflow(s);
//			output = ((Integer)s.extract("output")).intValue();
	//		System.out.println("test.ipd.g2: Flow: Branch 2: Assign5: the output  "+output);
		
		}else{
			Assign6();
		}
		
		} catch (Exception e) {
			System.out.println("test.ipd.g0: Flow: branch : in the catch: "+e.getMessage()+e.getCause());
		}
}
	
	public void Assign4(){
//		System.out.println("test.ipd.g2: Assign4: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.ipd.g2: Assign4 : in the catch: "+e.getMessage()+e.getCause());
		}
//		a = input;	
	}
	
	public void Assign5(){
//		System.out.println("test.ipd.g2: Assign5: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.ipd.g2: Assign5 : in the catch: "+e.getMessage()+e.getCause());
		}
//		a = input;	
	}
	
	public void Assign6(){
//		System.out.println("test.ipd.g2: Assign6: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.ipd.g2: Assign6 : in the catch: "+e.getMessage()+e.getCause());
		}
//		a = input;
	}
	
	public void Assign7(){
//		System.out.println("test.ipd.g2: Assign7: "+input);
		com.tilab.wade.performer.Subflow s;
		s = new com.tilab.wade.performer.Subflow("test.ipd.g2.Assign7");
		s.fill("input", input);
		s.setPerformer("Assign7_Agent");
		try {
			performSubflow(s);
		} catch (Exception e) {
			System.out.println("test.ipd.g2: Assign7-call Subflow : in the catch: "+e.getMessage()+e.getCause());
		}
//		output = ((Integer)s.extract("output")).intValue();
//		System.out.println("test.ipd.g2: Assign7: the output  "+output);		
	}
	
}

class Sequence1 extends Thread{
	
	int input; 
	Agent1Workflow cwf;
	Sequence1(Agent1Workflow cwf, int input){
		this.input = input;
		this.cwf = cwf;
	}
	public void run () {
		cwf.Assign2();
	}
}

class Sequence2 extends Thread{
	
	int input; 
	Agent1Workflow cwf;
	Sequence2(Agent1Workflow cwf, int input){
		this.input = input;
		this.cwf = cwf;
	}
	public void run () {
		cwf.Assign3();
	}}

class Sequence3 extends Thread{

	int input; 
	Agent1Workflow cwf;
	Sequence3(Agent1Workflow cwf, int input){
		this.input = input;
		this.cwf = cwf;
	}
	public void run () {
		cwf.IF();
	}
}
