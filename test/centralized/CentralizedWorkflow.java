package test.centralized;

import com.tilab.wade.performer.RouteActivityBehaviour;
import com.tilab.wade.performer.Transition;
import com.tilab.wade.performer.layout.MarkerLayout;

import com.tilab.wade.performer.layout.ActivityLayout;
import com.tilab.wade.performer.layout.WorkflowLayout;
import com.tilab.wade.performer.CodeExecutionBehaviour;
import com.tilab.wade.performer.FormalParameter;
import com.tilab.wade.performer.WorkflowBehaviour;
import common.share;

@WorkflowLayout(exitPoints = {@MarkerLayout(position="(408,744)", activityName="Assign8") }, activities={@ActivityLayout(position="(375,236)", name="Workflow"), @ActivityLayout(position="(520,406)", name="Sequence3"), @ActivityLayout(position="(355,406)", name="Sequence2"), @ActivityLayout(position="(179,404)", name = "Sequence1"), @ActivityLayout(position="(377,267)", name="Flow"), @ActivityLayout(position="(328,219)", name="Assign"), @ActivityLayout(position="(378,347)", name = "Assign7"), @ActivityLayout(isDecisionPoint = true, position="(647,260)", name="If"), @ActivityLayout(position="(374,573)", name="Assign7"), @ActivityLayout(position="(745,371)", name="Assign6"), @ActivityLayout(position="(629,372)", name="Assign5"), @ActivityLayout(position="(515,371)", name="Assign4"), @ActivityLayout(position="(374,371)", name="Assign3"), @ActivityLayout(position="(222,372)", name="Assign2"), @ActivityLayout(position="(371,193)", name = "Assign1")})
public class CentralizedWorkflow extends WorkflowBehaviour {

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

		Sequence0();
	}
	
	public void Sequence0() {
			Receive();
			Assign1();
			Flow();
			Assign7();
			Reply();

	}
	public void Receive() {
//		System.out.println("CentralizedWorkflow: Receive: "+input);
//		a = input;
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.centralized: Receive: in the catch: "+input);
		}
	}	
	
	public void Assign1(){
//		System.out.println("CentralizedWorkflow: Assign1: "+input);
//		a = input;
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.centralized: Assign1: in the catch: "+input);
		}
	}
	
	public void Assign2(){
//		System.out.println("CentralizedWorkflow: Assign2: "+input);
//		a = input;
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.centralized: Assign2: in the catch: "+input);
		}
	}
	
	public void Assign3(){
//		System.out.println("CentralizedWorkflow: Assign3: "+input);
//		a = input;
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.centralized: Assign3: in the catch: "+input);
		}
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
			System.out.println("test.centralized: Flow: in the catch: "+input);
		}		
	}
	public void IF(){
		//If
		if ((input>=0) && (input <=10)){
			Assign4();
		}else if ((input>10) && (input <=20)){
			Assign5();
		}else{
			Assign6();
		}
	}
	
	public void Assign4(){
//		System.out.println("CentralizedWorkflow: Assign4: "+input);
//		a = input;
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.centralized: Assign4: in the catch: "+input);
			
		}
	}
	
	public void Assign5(){
//		System.out.println("CentralizedWorkflow: Assign5: "+input);
//		a = input;
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.centralized: Assign5: in the catch: "+input);		}
	}
	
	public void Assign6(){
//		System.out.println("CentralizedWorkflow: Assign6: "+input);
//		a = input;
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.centralized: Assign6: in the catch: "+input);
		}
	}
	
	public void Assign7(){
//		System.out.println("CentralizedWorkflow: Assign7: "+input);
//		a = input;
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.centralized: Assign7: in the catch: "+input);
		}
	}
	public void Reply() {
//		System.out.println("CentralizedWorkflow: Reply: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("test.centralized: Reply: in the catch: "+input);
		}
	}
}

class Sequence1 extends Thread{
	
	int input; 
	CentralizedWorkflow cwf;
	Sequence1(CentralizedWorkflow cwf, int input){
		this.input = input;
		this.cwf = cwf;
	}
	public void run () {

		cwf.Assign2();
	}
}

class Sequence2 extends Thread{
	
		int input; 
		CentralizedWorkflow cwf;
		Sequence2(CentralizedWorkflow cwf, int input){
			this.input = input;
			this.cwf = cwf;
		}
		public void run () {
			cwf.Assign3();
		}
	}

class Sequence3 extends Thread{

		int input; 
		CentralizedWorkflow cwf;
		Sequence3(CentralizedWorkflow cwf, int input){
			this.input = input;
			this.cwf = cwf;
	}
	public void run () {
		cwf.IF();
	}
}
