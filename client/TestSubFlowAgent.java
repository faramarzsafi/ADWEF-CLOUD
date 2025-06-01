package client;

import jade.core.Location;

import java.util.Iterator;

import com.tilab.wade.commons.AgentInitializationException;
import com.tilab.wade.commons.AgentType;
import com.tilab.wade.performer.WorkflowEngineAgent;
import common.share;

public class TestSubFlowAgent extends WorkflowEngineAgent {

	/**
	 * Agent initialization
	 */
	@Override
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
	@Override
	public AgentType getType() {
		AgentType type = new AgentType();
		type.setDescription("Subflow-Agent");
		System.out.println("Hello from dynamically created Agent");
		return type;
	}

//	public TestSubFlowAgent getSearcherAgent(){
//		return this;
//	}
	
	
}
