package bookTrading.logging;

import jade.core.*;

public interface LoggingSlice extends Service.Slice {
	
   public static final String H_LOGMESSAGE = "log-message";
   public void logMessage (String s) throws IMTPException, ServiceException;
}
