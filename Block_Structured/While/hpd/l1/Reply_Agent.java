package Block_Structured.While.hpd.l1;

import com.tilab.wade.commons.AgentInitializationException;
import com.tilab.wade.commons.AgentType;
import com.tilab.wade.performer.WorkflowEngineAgent;
import common.share;

public class Reply_Agent extends WorkflowEngineAgent {

	/**
	 * Agent initialization
	 */
	public void agentSpecificSetup() throws AgentInitializationException {
		super.agentSpecificSetup();
		
		// A SearcherAgent can search for 1 component set at a time 
		// --> Set the pool-size to 1 so that we cannot execute workflows 
		// in parallel
		setPoolSize(share.MAX_INTEGER);
	}
	
	/**
	 * Return the type of this agent. This will be 
	 * inserted in the default DF description
	 */
	public AgentType getType() {
		AgentType type = new AgentType();
		type.setDescription("Reply-Agent");
		return type;
	}
	public long getDefaultWorkflowTimeout(){
		return share.WORKFLOW_TIMEOUT_ALL;//Max timeout supposed to be 1500 Sec that is converted to Ms. 
	
	}
}
