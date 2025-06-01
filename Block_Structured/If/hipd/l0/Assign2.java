package Block_Structured.If.hipd.l0;



import com.tilab.wade.performer.RouteActivityBehaviour;
import com.tilab.wade.performer.Transition;
import com.tilab.wade.performer.layout.MarkerLayout;


import com.tilab.wade.performer.layout.ActivityLayout;
import com.tilab.wade.performer.layout.WorkflowLayout;
import com.tilab.wade.performer.CodeExecutionBehaviour;
import com.tilab.wade.performer.FormalParameter;
import com.tilab.wade.performer.WorkflowBehaviour;
import common.share;


@WorkflowLayout(exitPoints = { }, activities={@ActivityLayout(position="(250,236)", name = "Assign2CodeActivity1")})
public class Assign2 extends WorkflowBehaviour {

	public static final String ASSIGN2CODEACTIVITY1_ACTIVITY = "Assign2CodeActivity1";
	@FormalParameter
	private int input;
	
	@FormalParameter
	private byte[] VARIABLE_MESSAGE;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private byte[] RETURNED_VARIABLE_MESSAGE = VARIABLE_MESSAGE;	
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int output = input;
	
	private void defineActivities() {
		CodeExecutionBehaviour Assign2CodeActivity1 = new CodeExecutionBehaviour(
				ASSIGN2CODEACTIVITY1_ACTIVITY, this);
		registerActivity(Assign2CodeActivity1, INITIAL_AND_FINAL);

	}

	private void defineTransitions() {
	
	}
	
	protected void executeAssign2CodeActivity1() throws Exception {
//		System.out.println("Assign2: Executed");
		try {
			Thread.sleep(share.ACTIVITY_SLEEP);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}