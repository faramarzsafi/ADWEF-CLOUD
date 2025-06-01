package common;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Result;
import jade.domain.FIPAException;
import jade.domain.FIPAService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JTextField;

import tuplespace.TupleSpace;

import com.tilab.wade.commons.AgentInitializationException;
import com.tilab.wade.dispatcher.DispatchingCapabilities;
import com.tilab.wade.performer.FormalParameter;
import com.tilab.wade.performer.WorkflowEngineAgent;
import com.tilab.wade.performer.WorkflowException;
import com.tilab.wade.performer.descriptors.ElementDescriptor;
import com.tilab.wade.performer.descriptors.WorkflowDescriptor;

/**
 * This is the agent that tries to assemble toys (PUPPET, WAGON)
 * @author Annalisa Marando - Universita' di Reggio Calabria
 * @author Giovanna Sacchi - Telecom Italia
 */
@SuppressWarnings("serial")
public class FuzzyClientAgent extends WorkflowEngineAgent {

	private FuzzyClientAgentGui myGui;
//	int executionMode;
	private DispatchingCapabilities dc = new DispatchingCapabilities();
	
	
	private String WORKFLOW_DESCRIPTOR;

	int NUMBER_OF_SERVERS = 0;
	int NUMBER_OF_AGENTS = 0;
	int WORKFLOW_STYLE = 0;
	String SUBFLOW;
	String SUBFLOWAGENT;
	
	int MAXRATE = 0;
	int RATE_START = 1;
	int RATE_STEP = 0;
	
	int POOL_SIZE = 0; 
	long SPEED = 0;
	int BULK = 0;
//	int MODE = 0;
	
	/**
	 * Agent initialization
	 */
	
	protected void agentSpecificSetup() throws AgentInitializationException {
		super.agentSpecificSetup();
		System.out.println("FuzzyClientAgent Reports...");
		System.out.println("In the AgentSpecificSetup");
		System.out.println("Default Workflow Timeout is Sec: "+getDefaultWorkflowTimeout()/1000);
		
		//setting pool size
		setPoolSize(share.MAX_INTEGER);

		Object[] args = getArguments();
				
		if (args != null){
			
			System.out.println("The arguments is : "+args.toString());
			
			String argStr = args[0].toString();
			argStr = argStr.substring(argStr.indexOf('{')+1,argStr.indexOf('}')).trim();
			Scanner scanner = new Scanner(argStr);
		    scanner.useDelimiter(",");
	    
		    while (scanner.hasNext()){
		    	
		    	Scanner sc = new Scanner(scanner.next());
		    	sc.useDelimiter("=");
		    	String name = sc.next().trim();
		    	String value = sc.next().trim();
		    	
		    	System.out.println(name+" = "+value);
		    	
			    if (name.equalsIgnoreCase("NUMBER_OF_SERVERS"))
			    	NUMBER_OF_SERVERS = new Integer(value).intValue();
			    else if (name.equalsIgnoreCase("NUMBER_OF_AGENTS"))
			    	NUMBER_OF_AGENTS = new Integer(value).intValue();
			    else if (name.equalsIgnoreCase("WORKFLOW_STYLE"))
			    	WORKFLOW_STYLE = new Integer(value).intValue();
			    else if (name.equalsIgnoreCase("POOL_SIZE" ))
			    	POOL_SIZE = new Integer(value).intValue();
			    else if (name.equalsIgnoreCase("SPEED" ))
			    	SPEED = new Integer(value).intValue();
			    else if (name.equalsIgnoreCase("MAXRATE" ))
			    	MAXRATE = new Integer(value).intValue();
			    else if (name.equalsIgnoreCase("RATE_START" ))
			    	RATE_START = new Integer(value).intValue();
			    else if (name.equalsIgnoreCase("RATE_STEP" ))
			    	RATE_STEP = new Integer(value).intValue();
			    else if (name.equalsIgnoreCase("BULK" ))
			    	BULK = new Integer(value).intValue();
//			    else if (name.equalsIgnoreCase("MODE" ))
//			    	MODE = new Integer(value).intValue();
		    }
		}else{ 
			System.out.println("The arguments is ...null - Reading from share file");
			NUMBER_OF_SERVERS = share.NUMBER_OF_SERVERS;
			NUMBER_OF_AGENTS = share.NUMBER_OF_AGENTS;
			WORKFLOW_STYLE = share.getWorkflowStyle();
			POOL_SIZE = share.POOL_SIZE;
			SPEED = share.SPEED;
			MAXRATE = share.MAXRATE;
			RATE_START = share.RATE_START;
			RATE_STEP = share.RATE_STEP;
			BULK = share.BULK;
//			MODE = share.MODE;
			System.out.println("The Workflow Style Code is: "+WORKFLOW_STYLE);
		}
		System.out.println("Execution mode is: "+ContainerManagement.getExecutionMode());
		switch (WORKFLOW_STYLE){
		
		case share.INVALID:  
			WORKFLOW_DESCRIPTOR = "";SUBFLOW = null;SUBFLOWAGENT = null;
			MAXRATE = 0; RATE_START = 0; POOL_SIZE = 0;	RATE_STEP = 0; NUMBER_OF_SERVERS = 0; SPEED = 0;
			System.out.println("No Workflow Style Detected");
			break;
		case share.CENTRALIZED:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "test.centralized.CentralizedWorkflow"; 
			SUBFLOWAGENT = "CentralizedWorkflow_Agent";
			System.out.println("The Workflow Style is: CENTRALIZED");
			break;
		case share.FPD:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "test.fpd.Sequence0"; 
			SUBFLOWAGENT = "Sequence0_Agent";
			System.out.println("The Workflow Style is: FPD");
			break;
		case share.IPD0:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "test.ipd.g0.Agent1Workflow"; 
			SUBFLOWAGENT = "Agent1Workflow_Agent";
			System.out.println("The Workflow Style is: IPD0");
			break;
		case share.IPD1:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "test.ipd.g1.Agent1Workflow"; 
			SUBFLOWAGENT = "Agent1Workflow_Agent";
			System.out.println("The Workflow Style is: IPD1");
			break;
		case share.IPD2:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "test.ipd.g2.Sequence0"; 
			SUBFLOWAGENT = "Sequence0_Agent";
			System.out.println("The Workflow Style is: IPD2");
			break;
		case share.IPD3:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "test.ipd.g3.Sequence0"; 
			SUBFLOWAGENT = "Sequence0_Agent";
			System.out.println("The Workflow Style is: IPD3");
			break;
		case share.IPD4:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "test.ipd.g4.Sequence0"; 
			SUBFLOWAGENT = "Sequence0_Agent";
			System.out.println("The Workflow Style is: IPD4");
			break;
			
		case share.IF_TEST1:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "Block_Structured.If.test1.If"; 
			SUBFLOWAGENT = "If_Agent";
			System.out.println("The Workflow Style is: IF_TEST1");
			break;
		case share.IF_TEST2:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "Block_Structured.If.test2.If"; 
			SUBFLOWAGENT = "If_Agent";
			System.out.println("The Workflow Style is: IF_TEST2");
			break;
			
		case share.EX1:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "firstTest.While1"; 
			SUBFLOWAGENT = "Performer_Agent";
			System.out.println("The Workflow Style is: EX1 ");
			break;
			
		case share.EX2:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "firstTest.While2"; 
			SUBFLOWAGENT = "Performer_Agent";
			System.out.println("The Workflow Style is: EX2 ");
			break;
			
		case share.EX3:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "firstTest.While3"; 
			SUBFLOWAGENT = "Performer1_Agent";
			System.out.println("The Workflow Style is: EX3 ");
			break;
			
		case share.EX4:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "firstTest.While4"; 
			SUBFLOWAGENT = "Performer1_Agent";
			System.out.println("The Workflow Style is: EX4 ");
			break;
			
		case share.HPD0_IF:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "Block_Structured.If.hpd.l0.CentralizedWorkflow"; 
			SUBFLOWAGENT = "CentralizedWorkflowAgent";
			System.out.println("The Workflow Style is: HPD0_IF");
			break;
		case share.HPD1_IF:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "Block_Structured.If.hpd.l1.Sequence0"; 
			SUBFLOWAGENT = "Sequence0_Agent";
			System.out.println("The Workflow Style is: HPD1_IF");
			break;
		case share.HPD2_IF:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "Block_Structured.If.hpd.l2.Sequence0"; 
			SUBFLOWAGENT = "Sequence0_Agent";
			System.out.println("The Workflow Style is: HPD2_IF");
			break;
		case share.HIPD0_IF:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "Block_Structured.If.hipd.l0.FrequentPath1"; 
			SUBFLOWAGENT = "Frequentpath1_Agent";
			System.out.println("The Workflow Style is: HIPD0_IF");
			break;
		case share.HIPD1_IF:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
//			SUBFLOW = "Block_Structured.If.hipd.l1.Sequence0"; 
//			SUBFLOWAGENT = "Sequence0_Agent";
			SUBFLOW = "Block_Structured.If.hipd.l1.FrequentPath1"; 
			SUBFLOWAGENT = "FrequentPath1_Agent";
			System.out.println("The Workflow Style is: HIPD1_IF");
			break;
		case share.HPD0_WHILE:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "Block_Structured.While.hpd.l0.CentralizedWorkflow"; 
			SUBFLOWAGENT = "CentralizedWorkflowAgent";
			System.out.println("The Workflow Style is: HPD0_WHILE");
			break;
		case share.HPD1_WHILE:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "Block_Structured.While.hpd.l1.Sequence0"; 
			SUBFLOWAGENT = "Sequence0_Agent";
			System.out.println("The Workflow Style is: HPD1_WHILE");
			break;
		case share.HPD2_WHILE:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "Block_Structured.While.hpd.l2.Sequence0"; 
			SUBFLOWAGENT = "Sequence0_Agent";
			System.out.println("The Workflow Style is: HPD2_WHILE");
			break;
//		case share.HIPD0_WHILE:
//			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
//			SUBFLOW = "Block_Structured.While.hipd.l0.FrequentPath1"; 
//			SUBFLOWAGENT = "FrequentPath1_Agent";
//			System.out.println("The Workflow Style is: HIPD0_WHILE");
//			break;
		case share.HIPD1_WHILE:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "Block_Structured.While.hipd.l1.Sequence0"; 
			SUBFLOWAGENT = "Sequence0_Agent";
			System.out.println("The Workflow Style is: HIPD1_WHILE");
			break;
		case share.HPD0_FLOW:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "Block_Structured.Flow.hpd.l0.CentralizedWorkflow"; 
			SUBFLOWAGENT = "CentralizedWorkflowAgent";
			System.out.println("The Workflow Style is: HPD0_FLOW");
			break;
		case share.HPD1_FLOW:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "Block_Structured.Flow.hpd.l1.Sequence0"; 
			SUBFLOWAGENT = "Sequence0_Agent";
			System.out.println("The Workflow Style is: HPD1_FLOW");
			break;
		case share.HPD2_FLOW:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "Block_Structured.Flow.hpd.l2.Sequence0"; 
			SUBFLOWAGENT = "Sequence0_Agent";
			System.out.println("The Workflow Style is: HPD2_FLOW");
			break;
		case share.HIPD1_FLOW:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "Block_Structured.Flow.hipd.l1.Sequence0"; 
			SUBFLOWAGENT = "Sequence0_Agent";
			System.out.println("The Workflow Style is: HIPD1_FLOW");
			break;
		case share.HPD0_PICK:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "Block_Structured.Pick.hpd.l0.CentralizedWorkflow"; 
			SUBFLOWAGENT = "CentralizedWorkflowAgent";
			System.out.println("The Workflow Style is: HPD0_PICK");
			break;
		case share.HPD1_PICK:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "Block_Structured.Pick.hpd.l1.Sequence0"; 
			SUBFLOWAGENT = "Sequence0_Agent";
			System.out.println("The Workflow Style is: HPD1_PICK");
			break;
		case share.HPD2_PICK:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "Block_Structured.Pick.hpd.l2.Sequence0"; 
			SUBFLOWAGENT = "Sequence0_Agent";
			System.out.println("The Workflow Style is: HPD2_PICK");
			break;
		case share.HPD0_LOAN:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "LoanTaking.hpd.l0.CentralizedWorkflow"; 
			SUBFLOWAGENT = "CentralizedWorkflowAgent";
			System.out.println("The Workflow Style is: HPD0_LOAN");
			break;
		case share.HPD1_LOAN:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "LoanTaking.hpd.l1.Sequence0"; 
			SUBFLOWAGENT = "Sequence0_Agent";
			System.out.println("The Workflow Style is: HPD1_LOAN");
			break;
		case share.HPD2_LOAN:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "LoanTaking.hpd.l2.Sequence0"; 
			SUBFLOWAGENT = "Sequence0_Agent";
			System.out.println("The Workflow Style is: HPD2_LOAN");
			break;
		case share.HPD3_LOAN:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "LoanTaking.hpd.l3.Sequence0"; 
			SUBFLOWAGENT = "Sequence0_Agent";
			System.out.println("The Workflow Style is: HPD3_LOAN");
			break;
		case share.HIPD0_LOAN:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "LoanTaking.hipd.l0.FrequentPath1"; 
			SUBFLOWAGENT = "FrequentPath1_Agent";
			System.out.println("The Workflow Style is: HIPD0_LOAN");
			break;
		case share.HIPD1_LOAN:
			WORKFLOW_DESCRIPTOR = "common.WorkflowClient";
			SUBFLOW = "LoanTaking.hipd.l1.Sequence0"; 
			SUBFLOWAGENT = "Sequence0_Agent";
			System.out.println("The Workflow Style is: HIPD1_LOAN");
			break;
		case share.FUZZY_BANDWIDTH:
			WORKFLOW_DESCRIPTOR = "common.FuzzyWorkflowClient";
			SUBFLOW = ""; 
			SUBFLOWAGENT = "";
			System.out.println("The Workflow Style is: FUZZY_BANDWIDTH");
			break;
		}
		
		// Create and show the gui
		myGui = new FuzzyClientAgentGui(this);
		myGui.initGui();
		myGui.setVisible(true);

		// Initialize the DispatchingCapabilities instance used 
		// to launch workflows
		dc.init(this);

		// Subscribe to the DF to keep the searchers list up to date
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Client-Agent");
		
		DFAgentDescription dfTemplate = new DFAgentDescription();
		dfTemplate.addServices(sd);
		
		SearchConstraints sc = new SearchConstraints();
		sc.setMaxResults(new Long(-1));
	}

	/**
	 * Agent clean-up
	 */
	protected void takeDown() {
		// Turn off the GUI on agent termination
		System.out.println("ClientAgent GUI is taking down...");
		if (myGui != null) {
			myGui.setVisible(false);
			myGui.dispose();
		}
	}
	

	public long getDefaultWorkflowTimeout(){
		return share.WORKFLOW_TIMEOUT_ALL;//Max timeout supposed to be 1500 Sec that is converted to Ms. 
	
	}

	/**
	 * Return the type of this agent. This will be 
	 * inserted in the default DF description
	 */

	 public long invocation() {
		 
		//Sleeping for 20 Second due to system initialization
		try {
			System.out.println("Sleeping before system preparation....");
			Thread.sleep(share.SLEEP_FOR_PREPARATION);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("In the catch of Thread.Sleep(20000)-when system waits for platfrom initialization");
			e.printStackTrace();
		}
		
		Map inParams = new HashMap();
		inParams.put("SUBFLOW", SUBFLOW);
		inParams.put("SUBFLOWAGENT", SUBFLOWAGENT);
		
		inParams.put("MAXRATE", MAXRATE);
		inParams.put("RATE_START", RATE_START);
		inParams.put("RATE_STEP", RATE_STEP);
		
		inParams.put("WORKFLOW_STYLE", WORKFLOW_STYLE);
		inParams.put("POOL_SIZE", POOL_SIZE);
		inParams.put("SPEED", SPEED);
		inParams.put("NUMBER_OF_SERVERS", NUMBER_OF_SERVERS);
		inParams.put("NUMBER_OF_AGENTS", NUMBER_OF_AGENTS);
		inParams.put("BULK", BULK);
//		inParams.put("MODE", MODE);
		
		WorkflowDescriptor wd = new WorkflowDescriptor(WORKFLOW_DESCRIPTOR, inParams);

		long start = 0;
		long stop = 0;
		try {
			ACLMessage request = dc.prepareRequest(getAID(), wd, null);

			start = System.currentTimeMillis();//save start time
			ACLMessage response = FIPAService.doFipaRequestClient(this, request);
			stop = System.currentTimeMillis(); // save stop time
			
//			Result r = (Result) this.getContentManager().extractContent(response);
//			Map outParams = ElementDescriptor.paramListToMap(r.getItems());
	
		} catch (WorkflowException e) {
			System.out.println("ClinetAgent: LaunchWorkflow-1: in the catch"+e.getMessage()+e.getCause());
		} catch (FIPAException e) {
			System.out.println("ClinetAgent: LaunchWorkflow-2: in the catch"+e.getMessage()+e.getCause());
//		} catch (UngroundedException e) {
//			System.out.println("ClinetAgent: LaunchWorkflow-3: in the catch"+e.getMessage()+e.getCause());
//		} catch (CodecException e) {
//			System.out.println("ClinetAgent: LaunchWorkflow-4: in the catch"+e.getMessage()+e.getCause());
//		} catch (OntologyException e) {
//			System.out.println("ClinetAgent: LaunchWorkflow-5: in the catch"+e.getMessage()+e.getCause());
		}
		return (stop-start);
	}



}

