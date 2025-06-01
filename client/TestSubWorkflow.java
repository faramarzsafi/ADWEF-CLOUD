package client;

//import toysassembler.Component;
//import toysassembler.Room;

import com.tilab.wade.performer.layout.ActivityLayout;
import com.tilab.wade.performer.layout.WorkflowLayout;
import com.tilab.wade.performer.CodeExecutionBehaviour;
import com.tilab.wade.performer.FormalParameter;
import com.tilab.wade.performer.WorkflowBehaviour;
/**
* The main toy assembling workflow
*/
@WorkflowLayout(activities={@ActivityLayout(position="(311,189)", name="SubWorkflowCodeActivity1")})
public class TestSubWorkflow extends WorkflowBehaviour {

	public static final String SUBWORKFLOWCODEACTIVITY1_ACTIVITY = "SubWorkflowCodeActivity1";

	@FormalParameter
	private int input;
//	@FormalParameter
//	private String componentType;
//	@FormalParameter
//	private int componentNumber;
	
	@FormalParameter(mode=FormalParameter.OUTPUT)
	private int testReturn;	
	
//	@FormalParameter(mode=FormalParameter.OUTPUT)
//	private Component[] components;
	
	private void defineActivities() {
		CodeExecutionBehaviour subWorkflowCodeActivity1Activity = new CodeExecutionBehaviour(
				SUBWORKFLOWCODEACTIVITY1_ACTIVITY, this);
		registerActivity(subWorkflowCodeActivity1Activity, INITIAL_AND_FINAL);
	}

	protected void executeSubWorkflowCodeActivity1() throws Exception {
		
		
		
//		Thread.sleep(10);
		System.out.println("SubFlow: hi from activity 1: "+input);
		testReturn = input;
	}
	
}