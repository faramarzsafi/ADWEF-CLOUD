package LoanTaking.hpd.l3;




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
import common.share;


@WorkflowLayout(exitPoints = { }, activities={@ActivityLayout(size = "(160,50)", position="(350,236)", name="Assign3CodeActivity1")})
public class Assign3 extends WorkflowBehaviour {

	public static final String ASSIGN3CODEACTIVITY1_ACTIVITY = "Assign3CodeActivity1";
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
	
	private void defineActivities() {
		CodeExecutionBehaviour assign3CodeActivity1Activity = new CodeExecutionBehaviour(
				ASSIGN3CODEACTIVITY1_ACTIVITY, this);
		registerActivity(assign3CodeActivity1Activity, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}

	protected void executeAssign3CodeActivity1() throws Exception {
//		System.out.println("Assign3: Executed");
		scoreB = score(reportB);
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private int score(int report) {
		return 0;
	}
}