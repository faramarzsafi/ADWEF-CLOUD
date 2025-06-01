package client;

import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Result;
import jade.core.AID;
import jade.core.Agent;
import jade.core.AgentContainer;
import jade.core.Location;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.mobility.MobilityOntology;
import jade.lang.acl.ACLMessage;
import jade.proto.SubscriptionInitiator;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import tuplespace.TupleSpace;

import com.tilab.wade.commons.AgentInitializationException;
import com.tilab.wade.commons.AgentType;
import com.tilab.wade.dispatcher.DispatchingCapabilities;
import com.tilab.wade.dispatcher.WorkflowResultListener;
import com.tilab.wade.performer.WorkflowEngineAgent;
import com.tilab.wade.performer.WorkflowException;
import com.tilab.wade.performer.descriptors.ElementDescriptor;
import com.tilab.wade.performer.descriptors.WorkflowDescriptor;
import com.tilab.wade.performer.ontology.ExecutionError;
import common.share;

/**
 * This is the agent that tries to assemble toys (PUPPET, WAGON)
 * @author Annalisa Marando - Universita' di Reggio Calabria
 * @author Giovanna Sacchi - Telecom Italia
 */
public class ClientAgent extends WorkflowEngineAgent {

	private ClientAgentGui myGui;
	private DispatchingCapabilities dc = new DispatchingCapabilities();
	private List subflowAgents = new ArrayList();	
	private int index = 0;
	/**
	 * Agent initialization
	 */
	protected void agentSpecificSetup() throws AgentInitializationException {
		super.agentSpecificSetup();
		setPoolSize(share.MAX_INTEGER);

		// Create and show the gui
		myGui = new ClientAgentGui(this);
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
		
		ACLMessage subscribe = DFService.createSubscriptionMessage(this, getDefaultDF(), dfTemplate, sc);
		
		// register the SL0 content language
	    getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL0);
	    // register the mobility ontology
	    getContentManager().registerOntology(MobilityOntology.getInstance());
		//to compile avaiable locations
		addBehaviour(new GetAvailableLocationsBehaviour(this));
	    Behaviour b2 = new ServeIncomingMessagesBehaviour(this);
	    addBehaviour(b2);
				
//		addBehaviour(new SubscriptionInitiator(this, subscribe) {
//			protected void handleInform(ACLMessage inform) {
//				try {
//					DFAgentDescription[] dfds = DFService.decodeNotification(inform.getContent());
//					for (int i = 0; i < dfds.length; ++i) {
//						AID aid = dfds[i].getName();
//						System.out.println("dfds["+i+"]= "+dfds[i].getName());
//						if (dfds[i].getAllServices().hasNext()) {
//							// Registration/Modification
//							if (!subflowAgents.contains(aid)) {
//								subflowAgents.add(aid);
//								System.out.println("SubflowAgnet: "+aid.getLocalName()+" added to the list of subflow agents");
//							} else {
//								// 	Deregistration
//								subflowAgents.remove(aid);
//								System.out.println("SubflowAgent: "+aid.getLocalName()+" removed from the list of Subflow agents");
//							}
//						}
//					}
//					System.out.println("Inside behavior subflowAgents[]= "+subflowAgents.size());
//				}
//				catch (FIPAException fe) {
//					fe.printStackTrace();
//				}
//			}
//		} );
	}

	/**
	 * Agent clean-up
	 */
	protected void takeDown() {
		// Turn off the GUI on agent termination 
		if (myGui != null) {
			myGui.setVisible(false);
			myGui.dispose();
		}
	}

	/**
	 * Return the type of this agent. This will be 
	 * inserted in the default DF description
	 */
	public AgentType getType() {
		AgentType type = new AgentType();
		type.setDescription("Client-Agent");
		return type;
	}

	public AID getSearcherAgent1() {
		if (subflowAgents.isEmpty()) {
			throw new RuntimeException("No ClientWorkflowAgent available");
		}
		if (index >=subflowAgents.size()) {
			index = 0;
		}
		return (AID) subflowAgents.get(index++);
	}

	public ClientAgent getSearcherAgent(){
		return this;
	}
	
	public long getDefaultWorkflowTimeout(){
		return share.WORKFLOW_TIMEOUT_ALL;//Max timeout supposed to be 1500 Sec that is converted to Ms. 
	}
	void deployment(){

		   jade.core.Runtime rt = jade.core.Runtime.instance();

		   // Exit the JVM when there are no more containers around
		   rt.setCloseVM(true);
		   Object[] arguments = new Object[1];
		   
		   // Create a default profile
		   Profile p = new ProfileImpl();
		   // Create a new non-main container, connecting to the default
		   // main container (i.e. on this host, port 1099)
		   ContainerController cc = rt.createAgentContainer(p);

		try {
	        AgentController traveller = cc.createNewAgent("TestSubFlowAgent","client.TestSubFlowAgent", arguments);
			traveller.start();
//			traveller.move();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
	}
	
	long invocation() {
		
		deployment();

		
		Map inParams = new HashMap();
		inParams.put("SUBFLOW", "client.TestSubWorkflow");
		inParams.put("SUBFLOWAGENT", "TestSubFlowAgent");
		WorkflowDescriptor wd = new WorkflowDescriptor("client.ClientWorkflow", inParams);

		long start = 0;
		long stop = 0;
		try {
			ACLMessage request = dc.prepareRequest(getAID(), wd, null);

			start = System.currentTimeMillis();//save start time
			ACLMessage response = FIPAService.doFipaRequestClient(this, request);
			stop = System.currentTimeMillis(); // save stop time
			
			Result r = (Result) this.getContentManager().extractContent(response);
			Map outParams = ElementDescriptor.paramListToMap(r.getItems());
	

		} catch (WorkflowException e) {
			System.out.println("ClinetAgent: LaunchWorkflow-1: in the catch"+e.getMessage()+e.getCause());
		} catch (FIPAException e) {
			System.out.println("ClinetAgent: LaunchWorkflow-2: in the catch"+e.getMessage()+e.getCause());
		} catch (UngroundedException e) {
			System.out.println("ClinetAgent: LaunchWorkflow-3: in the catch"+e.getMessage()+e.getCause());
		} catch (CodecException e) {
			System.out.println("ClinetAgent: LaunchWorkflow-4: in the catch"+e.getMessage()+e.getCause());
		} catch (OntologyException e) {
			System.out.println("ClinetAgent: LaunchWorkflow-5: in the catch"+e.getMessage()+e.getCause());
		}
		return (stop-start);
	}
	
	public void updateLocations(Iterator list) {
//	public void updateLocations() {
//		availableSiteListModel.clear(); //to get added to a list
		System.out.println("hey in the updateLocation");
	    for ( ; list.hasNext(); ) {
	    	Object obj = list.next();
//	        availableSiteListModel.add((Location) obj); // to get added to a list
	    	System.out.println("the addresses are: "+((Location) obj).getAddress());
	    	
	    }
//	    availableSiteListModel.fireTableDataChanged();// to get added to a list
	  }
}



	
