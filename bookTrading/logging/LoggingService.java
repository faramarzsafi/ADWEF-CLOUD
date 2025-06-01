package bookTrading.logging;

import jade.core.AID;
import jade.core.Agent;
import jade.core.AgentContainer;
import jade.core.BaseService;
import jade.core.Filter;
import jade.core.HorizontalCommand;
import jade.core.IMTPException;
import jade.core.MainContainer;
import jade.core.Node;
import jade.core.Profile;
import jade.core.ProfileException;
import jade.core.Service;
import jade.core.ServiceException;
import jade.core.ServiceHelper;
import jade.core.VerticalCommand;
import jade.core.messaging.GenericMessage;
import jade.core.messaging.MessagingSlice;
import jade.lang.acl.ACLMessage;

public class LoggingService extends BaseService {
	
	// Service name
	public static final String NAME = "bookTrading.logging.Logging";
		 
	// Service parameter names
	public static final String VERBOSE = "bookTrading_logging_LoggingService_verbose";
	private boolean verbose = false;
	
	private Filter outFilter = new OutgoingLoggingFilter();
	private LoggingSlicelmpl localSlice = new LoggingSlicelmpl() ;
		
	public Service.Slice getLocalSlice() {
		return localSlice;
	}
	
	public Class getHorizontalInterface() {
		 return LoggingSlice.class;
	}
	
	public Filter getCommandFilter(boolean direction) {
		System.out.println("Direction is: "+direction);	
		  if (direction == Filter.OUTGOING) {
		     return outFilter;
		  }
		  else {
		     return null;
		  }
	 }
	
	public String getName() {
	    return NAME;
	}
	
	public void boot(Profile p) throws ServiceException {
	     super.boot(p);
	     verbose = p.getBooleanProperty(VERBOSE, false);
//	     verbose = true;
	     System.out.println("VERBOSE is = "+verbose);
//	     System.out.println("HaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHa" +
//	     		"HaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHaHa");
	}
	
	private class OutgoingLoggingFilter extends Filter {
		 
		 public boolean accept (VerticalCommand cmd) {
			 
			if (cmd.getName().equals (MessagingSlice.SEND_MESSAGE)) {
					 Object[] params = cmd.getParams() ;
					 AID sender = (AID) params [0] ;
					 GenericMessage gMsg = (GenericMessage) params[1] ;
					 ACLMessage msg = gMsg.getACLMessage() ;
					 AID receiver = (AID) params[2] ;
					 // Prepare the log record
					 String logRecord = "Message from "+sender+" to "+receiver+": \n" ;
					 System.out.println("OutgoingLoggingFilter:log Record :"+ logRecord);
					 System.out.println("OutgoingLoggingFilter:verbose: "+verbose);
					 
					 if (verbose) {
						 logRecord = logRecord+msg;
					 }
					 else {
						 logRecord = logRecord+ACLMessage.getPerformative (msg.getPerformative());
					 }
		
					 // Send the log record to the logging slice on the MainContainer
					 try {
						 System.out.println("getNumberOfSlices() = "+getNumberOfSlices());
						 System.out.println("Here1");
						 if (getLocalSlice() == null) System.out.println("getLocalSlice is null");
						 System.out.println("Here2");
						 if (getSlice(MAIN_SLICE) == null) System.out.println("getSlice(MAIN_SLICE) is null");
						 System.out.println("Here3");
				         LoggingSlice mainSlice = (LoggingSlice)getSlice(MAIN_SLICE) ;
				         System.out.println("Here4");
						 mainSlice.logMessage(logRecord) ;
						 System.out.println("Here5");
					 }
					 catch (ServiceException se) {
						 System.out.println("OutgoingLoggingFilter:ServiceException:Error retrieving Main LoggingSlice") ;
						 se.printStackTrace() ;
					 }
				 catch (IMTPException impte) {
					 System.out.println ("OutgoingLoggingFilter:IMTPException:Error contacting Main LoggingSlice") ;
					 impte.printStackTrace();
				 }
			}
		 // Never block a command
		 return true;
		 }
	 }
  
	 private class LoggingSlicelmpl implements Service.Slice {
		
		public Service getService() {
			
			return LoggingService.this;
		}
		  
		public Node getNode() throws ServiceException {
			
			   try {
				   return LoggingService.this.getLocalNode() ;
			   }
			   catch (IMTPException imtpe) {
				   //Should never happen as this is a local call
				   throw new ServiceException("Unexpected error retrieving local node") ;
			   }
		}
		  
	  	public VerticalCommand serve (HorizontalCommand cmd) {
			   String cmdName = cmd.getName() ;
			   if (cmd.getName().equals (LoggingSlice.H_LOGMESSAGE)) {
				   Object[] params = cmd.getParams () ;
				   System.out.println (params [0]) ;
			   }  
			   //added by fara
	//		   return (VerticalCommand)cmd;
			   return null;
			}
   }
		
	private class LoggingHelperImpl implements LoggingHelper {
				
		public void init(Agent a) {
				
		}
		
		public void setVerbose (boolean v) {
		   verbose = v;
		 }
	}
	
}


