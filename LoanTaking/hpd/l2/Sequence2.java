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


@WorkflowLayout(exitPoints = { }, activities={@ActivityLayout(size = "(157,50)", position="(322,235)", name="Sequence2CodeActivity1")})
public class Sequence2 extends WorkflowBehaviour {

	public static final String SEQUENCE2CODEACTIVITY1_ACTIVITY = "Sequence2CodeActivity1";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	int scoreB;
	int reportB;
	int claim;
	boolean approval;
	
	private void defineActivities() {
		CodeExecutionBehaviour sequence2CodeActivity1Activity = new CodeExecutionBehaviour(
				SEQUENCE2CODEACTIVITY1_ACTIVITY, this);
		registerActivity(sequence2CodeActivity1Activity, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}


	protected void executeSequence2CodeActivity1() throws Exception {
//		System.out.println("Sequence2: Run");
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Invoke2();
		Receive3();
		Assign3();
	}
	public void Invoke2(){
//		System.out.println("Invoke2: "+input);
		
		try {
			//call webServiceA.process(claim);
			callWebService("A", claim);
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("Invoke2: in the catch: "+input);
		}
	}
	
	public void Receive3(){
//		System.out.println("Receive3: "+input);
		//delay in calling web service is 0 in this case.
		reportB = input; 
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("Receive3: in the catch: "+input);
		}
	}
	
	public void Assign3(){
//		System.out.println("Assign3: "+input);
		scoreB = score(reportB);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			System.out.println("Assign3: in the catch: "+input);
		}
	}
	
	private int callWebService(String string, int claim2) {
		return 0;
	}
	
	private int score(int report) {
		return 0;
	}

}
