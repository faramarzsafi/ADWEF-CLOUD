package common.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import common.tools.Analyser.LogRecord;
import common.tools.Analyser.Record;

public class ExchangedMessageAnalyser {

	
	
	/**
	 * @param args
	 */
	boolean date = false;
    boolean act = false;
	public static void main(String[] args) {
		
		ExchangedMessageAnalyser ma = new ExchangedMessageAnalyser();
//		ma.CalculateNumberOfMessages("CENT_1_LIN_sec_30000_1_1_12-14-35.csv", "D:\\temp\\Message\\IdontKnow.txt", "D:\\temp\\Message\\MessageNumber.txt");
//		ma.CalculateNumberOfMessages("IPD1_4_LIN_sec_30000_1_1_2009-07-16__09-31-51.csv", "D:\\temp\\test\\IPD1_4_Sec_Step=1.txt", "D:\\temp\\test\\MessageNumber.txt");
//		File dir = new File("D:\\temp\\Final-Results\\WithMessaging\\EXP\\");
		String basePath = "D:\\temp\\";
//		String path = basePath+"2010-03-18_FinalMessaging\\";
		String path = basePath+"2010-03-22-FirstTest_Final\\Messaging\\";
		File dir = new File(path);
		
		String[] children = dir.list();
	    if (children == null) {
	    	System.out.println("Either dir does not exist or is not a directory");
	    } else {
	        for (int i=0; i<children.length; i++) {
//	    	for (int i=0; i<1; i++) {
	        	//the name of experiment .csv files
//	    		String FILE_NAME = "IF_HIPD0_2_1_MODE2_BULK_LIN_sec_600.csv";
//	        	String FILE_NAME = "EX3_n=10.csv";
	        	String FILE_NAME = children[i];
	    		if (!ma.isDirECTORY(path+FILE_NAME)){
		        	//the name of message file is automaticcaly extracted from experiment file name, message folder must be different
//		    		String MESSAGE_LOGFILE_NAME = "D:\\temp\\2010-03-22-FirstTest_Final\\Messaging\\Messaging\\EX3_2_1_BULK_LIN_sec_600_1_50_2010-03-24";
		        	String MESSAGE_LOGFILE_NAME = path+"Messages\\"+FILE_NAME.substring(0,FILE_NAME.indexOf("."));
		        	
		        	//The results of message count are saved in a separate file, in a different folder  
		        	String MESSAGE_NUMBER_FILE_NAME = path+"Messages\\"+FILE_NAME+".txt";
	
		    		System.out.println("Starting experiment for ... "+FILE_NAME);
	//	        	System.out.println("Message Log file is ... "+MESSAGE_LOGFILE_NAME);
	
	       	   		ma.CalculateNumberOfMessages(FILE_NAME, MESSAGE_LOGFILE_NAME, MESSAGE_NUMBER_FILE_NAME);
	    		}
	        	System.out.println("Experiment Finished for ... "+FILE_NAME);	        			
	        }
	    }

		System.out.println("Experiment ... Finished");
		
	}
	
	public boolean isDirECTORY(String fileOrDirName){
		
		File dir = new File(fileOrDirName);
	    return dir.isDirectory();
	}
	
 	void CalculateNumberOfMessages(String FILE_NAME, String MESSAGE_LOGFILE_NAME, String MESSAGE_NUMBER_FILE_NAME){
 		
 		Connection c = null;
	    Statement stmnt = null;
	    
     try {
		
    	 Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );
         c = DriverManager.getConnection( "jdbc:odbc:fara", "", "" );
         
         stmnt = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                 ResultSet.CONCUR_UPDATABLE);
         String query = "select * from ["+FILE_NAME+"]";
	     ResultSet rs = stmnt.executeQuery( query );
	        int row = 0;
         while (rs.next()){
        	 row++;
        	 System.out.println("The Current row of the Results is: "+row);
//        	 String StartDateString  = rs.getString("StartDate"); 
//			 String EndDateString  = rs.getString("EndDate");
        	 long StartDateLong  = rs.getLong("StartDate"); 
			 long EndDateLong  = rs.getLong("EndDate");


			 
//			 String index  = rs.getString("index");
//			 if (!((StartDateString.equalsIgnoreCase(""))||(EndDateString.equalsIgnoreCase("")))){
			 if (((StartDateLong > 0)&&(EndDateLong > 0))){

//	        	DateFormat df = new SimpleDateFormat ("EEE MMM dd HH:mm:ss zzz yyyy");
//	        	Date StartDate  = df.parse(StartDateString);
//	            Date EndDate  = df.parse(EndDateString);
//				 String StartDate = new Date(StartDateLong); 
//				 String EndDate = new Date(EndDateLong);
//	            Date StartDate  = df.parse("Sun Jul 12 11:39:18 GMT+08:00 2009");
//	            Date EndDate  = df.parse("Sun Jul 12 11:39:19 GMT+08:00 2009");
	            System.out.println("StartDate is:" + StartDateLong);
	            System.out.println("EndDate is:" + EndDateLong);
	             
//				long messagesCount = AnalyzMessages(MESSAGE_LOGFILE_NAME, StartDate, EndDate);
				long messagesCount = AnalyzMessages(MESSAGE_LOGFILE_NAME, StartDateLong, EndDateLong);
//				System.out.println("the return MessagesCount is: "+messagesCount);
				 
		        BufferedWriter out = new BufferedWriter(new FileWriter(MESSAGE_NUMBER_FILE_NAME, true));
		        out.write(new Long(messagesCount).toString()+"\n");
		        out.close();

//				 String updateString = "UPDATE ["+FILE_NAME+"]" +
//				 "SET NumberOfMessages = 1 "; 
//				 +
//				 "WHERE index = 1";
//				 stmnt.executeUpdate(updateString);
//				 rs.moveToCurrentRow();
//				 rs.absolute(row);
//				 rs.updateString("NumberOfMessages",new Long(messagesCount).toString()); 
//				 System.out.println("the NumberOFMessages Updated: "+rs.getString("NumberOfMessages"));
//				 rs.updateRow(); // updates the row in the data source
//				 c.commit();
			 }
		}
         rs.close();
         stmnt.close();
         c.close();
		}catch (SQLException e){e.printStackTrace();} 
		 catch (ClassNotFoundException e) {e.printStackTrace();	} 
//		 catch (ParseException e) {e.printStackTrace();} 
		 catch (IOException e) {e.printStackTrace();}
     }

//	long AnalyzMessages(String MESSAGE_LOGFILE_NAME, Date start, Date end){
 	long AnalyzMessages(String MESSAGE_LOGFILE_NAME, long startDate, long endDate){
		
        int messageCount = 0;
        int wrongMessages = 0;
		int errornousMessages = 0;
        Date start = new Date(startDate); 
        Date end = new Date(endDate);
        
		try {
		        BufferedReader in = new BufferedReader(new FileReader(MESSAGE_LOGFILE_NAME));
		        String str;
		        int row = 0;
		        while ((str = in.readLine())!= null) {
		        	int rc = 0;
			        row++;
//			        if (row == 6000)System.out.print(str);
		        	Node node = null;
					try {
						node = convertStringToDocument(str);
					} catch (Exception e) {
				    	System.out.println("AnalyzMessages: Exception Caused in convertStringToDocument:"+e.getMessage()+e.getCause());
				    	node = null;
						errornousMessages++;
//						e.printStackTrace();
					}
//		        	if ((row %100) == 0)System.out.print("");
					
					if ((row %1000) == 0){System.out.print(".");rc++;} 	
//					else if ((rc %100) == 0){System.out.println(" "+row);rc=0;}


		        	date = false; act = false;
		        	if (node != null){
		        		if (validMessage(node, start, end)) 
		        			messageCount++; 
		        		else 
		        			wrongMessages++;
		        	}
		        }
		        System.out.println();
	        	System.out.println("Total rows number: "+row);
            	System.out.println("the total number of Right Messages: "+messageCount);
            	System.out.println("the total number of Worng messages: "+wrongMessages);
            	System.out.println("the total number of Errornous Messages: "+errornousMessages);
		        in.close();
		    } catch (IOException e) {
		    	System.out.println("AnalyzMessages: in the catch:"+e.getMessage()+e.getCause());
		    }
		    return messageCount;
		}
	     
	     /** Writes node and all child nodes into System.out
	      * @param node XML node from from XML tree wrom which will output statement start
	      * @param indent number of spaces used to indent output
	      */
	     public boolean validMessage(Node node, Date start, Date end) {
	    	 
	         // get element name
	         String nodeName = node.getNodeName();
	         // get element value
	         String nodeValue = getElementValue(node);
	         
	         if (nodeName == "date"){

                String patternStr = "a,b,c,d,e,f,g,h,i,g,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
                StringTokenizer st = new StringTokenizer(nodeValue,patternStr);
	        	DateFormat df = new SimpleDateFormat ("yyyyMMddHHmmssSSS");
	        	Date mDate = null;
				try {
					String dateString = st.nextToken()+st.nextToken();
					mDate = df.parse(dateString);
//					System.out.println("mDate is:" + mDate);
//	                if( ((mDate.equals(start))||(mDate.after(start)))
//		        	 	 &&((mDate.before(end))||(mDate.equals(end))) ){
	                	boolean b1 = ((mDate.equals(start))||(mDate.after(start)));
	                	boolean b2 = ((mDate.before(end))||(mDate.equals(end)));
	                	
//	                	System.out.println("b1: "+b1);
//	                	System.out.println("b2: "+b2);
//	                	System.out.println("mDate.before(end): "+mDate.before(end));
//	                	System.out.println("mDate.equals(end): "+mDate.equals(end));
//	                	System.out.println("mDate.after(start): "+mDate.after(start));
//	                	System.out.println("mDate.equals(start): "+mDate.equals(start));
//	                	System.out.println("start.equals(end): "+start.equals(end));
//	                	System.out.println("start.compareTo(end): "+start.compareTo(end));
	                	
	                	if (b1 && b2){
		                	date = true;
//		                	System.out.println("mDate:" + mDate+" :validated: "+date);
		                }
				} catch (ParseException e) {e.printStackTrace();}     
	         }
         
	         // get attributes of element
	         NamedNodeMap attributes = node.getAttributes();
//	         System.out.println(getIndentSpaces(indent) + "NodeName: " + nodeName + ", NodeValue: " + nodeValue);
	         for (int i = 0; i < attributes.getLength(); i++) {
	             Node attribute = attributes.item(i);
//        		 System.out.println( "AttributeName: " + attribute.getNodeName() + ", attributeValue: " + attribute.getNodeValue());
	             if(attribute.getNodeName().equalsIgnoreCase("act")){
//	        		 System.out.println( "AttributeName: " + attribute.getNodeName() + ", attributeValue: " + attribute.getNodeValue());
		        	 if (attribute.getNodeValue().equalsIgnoreCase("AGREE")||attribute.getNodeValue().equalsIgnoreCase("INFORM") ||attribute.getNodeValue().equalsIgnoreCase("REQUEST")){
		        		 act = true;
		        	 }
	             }
	         }
	         
	         // write all child nodes recursively
	         NodeList children = node.getChildNodes();
	         for (int i = 0; i < children.getLength(); i++) {
	             Node child = children.item(i);
	             if (child.getNodeType() == Node.ELEMENT_NODE) {
	            	 validMessage(child, start, end);
	            	 if ((date)&&(act)) return true;
	             }
	         }
	         return false;
	     }
	     
	 	
	 	/** Returns element value
        * @param elem element (it is XML tag)
	    * @return Element value otherwise empty String
	    */
 	    public final static String getElementValue( Node elem ) {
 	        Node kid;
 	        if( elem != null){
 	            if (elem.hasChildNodes()){
 	                for( kid = elem.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
 	                    if( kid.getNodeType() == Node.TEXT_NODE  ){
 	                        return kid.getNodeValue();
 	                    }
 	                }
 	            }
 	        }
 	        return "";
 	    }
	 	     
 		Node convertStringToDocument(String xmlString) throws ParserConfigurationException, SAXException, IOException{
 			
 			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
 	        DocumentBuilder builder = null;
	 	    Node root = null;
//	 		try {
	 			
	 			builder = factory.newDocumentBuilder();
	 			Document document = builder.parse(new InputSource(new StringReader(xmlString)));
	 			root = document.getDocumentElement();
	 				
// 			}
//	 		catch (SAXException e) {e.printStackTrace(); System.out.print("In the Catch 1: "+xmlString);return null; } 
// 			catch (IOException e) {e.printStackTrace(); System.out.print("In the Catch 2: "+xmlString);return null; } 
// 			catch (ParserConfigurationException e) {e.printStackTrace(); System.out.print("In the Catch 3: "+xmlString);return null; } 			
 			return root;
 		}	 	   
}

