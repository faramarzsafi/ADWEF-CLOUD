package Block_Structured.Flow.hipd.l1;

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
public class FrequentPath1 extends WorkflowBehaviour {

	public static final String WORKFLOW_ACTIVITY = "Workflow";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		CodeExecutionBehaviour workflowActivity = new CodeExecutionBehaviour(
				WORKFLOW_ACTIVITY, this);
		registerActivity(workflowActivity, INITIAL_AND_FINAL);
	}

	private void defineTransitions() {
	}
	
	protected void executeWorkflow() throws Exception {

		Receive();
		Flow();
		Reply();
	}
	

	public void Receive() {
//		System.out.println("Receive: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
//			System.out.println("test.centralized: Receive: in the catch: "+input);
		}
	}	

	public void Flow(){ 
//		System.out.println("Flow: "+input);
		Runnable ass1 = new Assign(this,1);
		Thread assignThread1 = new Thread(ass1);
		Runnable ass2 = new Assign (this,2);
		Thread assignThread2 = new Thread(ass2);
		assignThread1.start();
		assignThread2.start();
		
		try {
			assignThread1.join();
			assignThread2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Assign1(){
//		System.out.println("Assign1: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
//			System.out.println("test.centralized: Assign1: in the catch: "+input);
		}
	}
	public void Assign2(){
//		System.out.println("Assign2: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
//			System.out.println("test.centralized: Assign1: in the catch: "+input);
		}
	}
	
	public void Reply() {
//		System.out.println("Reply: "+input);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
//			System.out.println("test.centralized: Reply: in the catch: "+input);
		}
	}
}
class Assign implements Runnable {
	FrequentPath1 cw;
	int assign;
	public Assign(FrequentPath1 cw, int assign){
		this.cw = cw;
		this.assign = assign;
	}
    // This method is called when the thread runs
    public void run() {
    	if (assign == 1) cw.Assign1();
    	else if (assign ==2) cw.Assign2();
    }
}
