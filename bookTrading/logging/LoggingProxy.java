package bookTrading.logging;

import jade.core.*;

public class LoggingProxy extends SliceProxy implements LoggingSlice {
	
  public void logMessage (String s) throws IMTPException, ServiceException{
     GenericCommand cmd = 
    	 new GenericCommand(H_LOGMESSAGE, LoggingService.NAME, null);
     cmd.addParam(s) ;
     getNode().accept(cmd);
  }
}


