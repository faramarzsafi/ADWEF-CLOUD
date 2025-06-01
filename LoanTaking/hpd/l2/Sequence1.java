package LoanTaking.hpd.l2;


import com.tilab.wade.performer.SubflowDelegationBehaviour;
import com.tilab.wade.performer.RouteActivityBehaviour;
import com.tilab.wade.performer.Transition;
import com.tilab.wade.performer.layout.MarkerLayout;


import com.tilab.wade.performer.layout.ActivityLayout;
import com.tilab.wade.performer.layout.WorkflowLayout;
import com.tilab.wade.performer.CodeExecutionBehaviour;
import com.tilab.wade.performer.FormalParameter;
import com.tilab.wade.performer.Subflow;
import com.tilab.wade.performer.WorkflowBehaviour;
import common.share;


@WorkflowLayout(exitPoints = { }, activities={@ActivityLayout(size = "(222,50)", position="(272,219)", name="Sequence1CodeActivity1")})
public class Sequence1 extends WorkflowBehaviour {

	public static final String SEQUENCE1CODEACTIVITY1_ACTIVITY = "Sequence1CodeActivity1";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	int scoreA;
	int reportA;
	int reportB;
	int claim;
	boolean approval;
	
	private void defineActivities() {
		CodeExecutionBehaviour sequence1CodeActivity1Activity = new CodeExecutionBehaviour(
				SEQUENCE1CODEACTIVITY1_ACTIVITY, this);
		registerActivity(sequence1CodeActivity1Activity, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}


	protected void executeSequence1CodeActivity1() throws Exception {
//		System.out.println("Sequence1: Run");
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Invoke1();
		Receive2();
		Assign2();
	}
	public void Invoke1(){
//		System.out.println("Invoke1: "+input);
		
		try {
			//call webServiceA.process(claim);
			callWebService("A", claim);
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("Invoke1: in the catch: "+input);
		}
	}
	
	public void Receive2(){
//		System.out.println("Receive2: "+input);
		//delay in calling web service is 0 in this case.
		reportA = input; 
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("Receive2: in the catch: "+input);
		}
	}
	
	public void Assign2(){
//		System.out.println("Assign2: "+input);
		scoreA = score(reportA);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("Assign2: in the catch: "+input);
		}
	}
	
	private int callWebService(String string, int claim2) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private int score(int report) {
		// TODO Auto-generated method stub
		return 0;
	}

}

