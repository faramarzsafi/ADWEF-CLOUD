package common.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import common.share;

public class FuzzyMessageCounter {

//	final String MESSAGE_LOGFILE_NAME = "c:\\Safi\\MessagingTest\\Messages_FUZZY_8machines_33Agent_mode2_2000PerMin.txt";
//	final String MESSAGE_NUMBER_FILE_NAME = "c:\\Safi\\MessagingTest\\"+share.Date()+"\\";
	
//	String baseD = "C:\\safi";
//	String baseR = "C:\\safi";
//		
//	String rPathD = "\\MessagingDataset";
//	String rPathR = "\\MessagingResult";
	
	String pathD; // = baseD+rPathD;
	String pathR; // = baseR+rPathR;
		
	boolean date = false;
    boolean act = false;
    
    Date minDate;
    Date maxDate;
    final long step = 1000;
    
	public static void main(String[] args) throws Exception {
		
		FuzzyMessageCounter fmc = new FuzzyMessageCounter();
		
		args[0].trim();
		args[1].trim();
		
		if (args[0]== null) throw new Exception("source path cannot be null");
		if (args[0]== null) throw new Exception("destination path cannot be null");
		if (args[0]== "") throw new Exception("source path cannot be empty");
		if (args[0]== "") throw new Exception("destination path cannot be empty");
		
//		fmc.pathD = "C:\\Safi\\test\\MessageDataset\\HIPD_FUZZY\\Exponential";//args[0];//
//		fmc.pathR = "C:\\Safi\\test\\results\\HIPD_FUZZY\\Exponential";//args[1];//
		
		fmc.pathD = args[0];
		fmc.pathR = args[1];
		
		fmc.copyDirectory(new File(fmc.pathD), new File(fmc.pathR));
	}

	void startAnalysisByStep(File f, File fileR){
		System.out.println("startAnalysisByStep ... ");
		BufferedWriter out = null;
		setMinMaxTime(f);
   	 	Date nextTime = copyDateByValue(minDate);
   	 	
   	 	long total = 0;
   		try {
//			File fileR = makeAlternativeResultFile(f);
			out = new BufferedWriter(new FileWriter(fileR.getPath(), true));
			System.out.println("Resultset File was set to -> "+ fileR.getPath());
			out.write("T1"+","+"T2"+","+"Step"+","+"MessagesT1T2"+","+"TotalUntilT2"+"\n");
			do{
				
				Date oldTime = copyDateByValue(nextTime);
				nextTime.setTime(((nextTime.getTime()+step)>maxDate.getTime())? maxDate.getTime():(nextTime.getTime()+step));
				long messagesCount = AnalyzMessages( f, oldTime, nextTime );
				total += messagesCount;
				out.write(oldTime.getTime()+","+nextTime.getTime()+","+step+","+messagesCount+","+total+"\n");
				out.flush();

			}while(nextTime.before(maxDate));
			out.close();			

		} catch (IOException e) {
			System.out.println("startMessageAnalyzer:"+e.getMessage()+e.getCause());
			e.printStackTrace();
		}	
	}

	private Date copyDateByValue(Date date) {
		Date tDate = new Date();
		tDate.setTime(date.getTime());
		return tDate;
	}

	public Date setMinMaxTime(File f) {
		System.out.println("Setting min and max time started...");
		Node node = null;
        try {
			BufferedReader in = new BufferedReader(new FileReader(f.getPath()));
	        String str;
	        try {		        
	        str = in.readLine();
	        if (str == null) throw new Exception("setMinMax:file "+f.getPath()+"is empty");

			node = convertStringToDocument(str);
			Node dateNode = findNode(node, "date");
			Date nDate = getNodeDate(dateNode);
	        minDate = nDate;
	        maxDate = nDate;
			while ((str = in.readLine())!= null) {
				
				node = convertStringToDocument(str);
				dateNode = findNode(node, "date");
				nDate = getNodeDate(dateNode);
				minDate = (minDate.after(nDate))?nDate: minDate ;
            	maxDate = (maxDate.before(nDate))?nDate:maxDate;
			}
			in.close();
			System.out.println("min and max time was set...");
			System.out.println("minTime = "+minDate);
			System.out.println("maxTime = "+maxDate);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    }catch (Exception e) {
	    	System.out.println("setMinMaxTime: Exception Caused in convertStringToDocument:"+e.getMessage()+e.getCause());
	    	node = null;
	    }
	return null;
    }

	long AnalyzMessages(File f, Date startDate, Date endDate) {
		
        int messageCount = 0;
        int wrongMessages = 0;
		int errornousMessages = 0;

		try {
	        String str;
	        int row = 0;
	        BufferedReader in = new BufferedReader(new FileReader(f.getPath()));
	        while ((str = in.readLine())!= null) {
		        row++; Node node = null;
		        
				try {
					node = convertStringToDocument(str);
				} catch (Exception e) {
			    	System.out.println("AnalyzMessages: Exception Escaped...:"+e.getMessage()+e.getCause());
			    	node = null;
					errornousMessages++;
				}
				System.out.print((((row %1000==0)&&row>0)?(((row%100000)==0)?"["+row+"]\n":"."):"")); 	
				date = false; act = false;
	        	if (node != null){
	        		if (validateMessage(node, startDate, endDate))
	        			messageCount++; 
	        		else 
	        			wrongMessages++;
		        	}
		        }
	        	System.out.print("["+row+"] \n");
	        	
				System.out.println("minTime = "+minDate);
				System.out.println("maxTime = "+maxDate);
				
				System.out.println("Start Time = "+startDate);
				System.out.println("End Time = "+endDate);
				
	        	System.out.println("Total rows number: "+row);
	        	
            	System.out.println("total number of Right Messages: "+messageCount);
            	System.out.println("total number of Worng messages: "+wrongMessages);
            	System.out.println("total number of Errornous Messages: "+errornousMessages);
            	in.close();
		    } catch (IOException e) {
		    	System.out.println("AnalyzMessages: in IOException catch:"+e.getMessage()+e.getCause());
		    }
		    return messageCount;
		}
	     
 		public boolean validateMessage(Node node, Date start, Date end) {
	    	 
			try {
		        Node dateNode = findNode(node, "date");
		        if (dateNode == null) throw new Exception("validateMessage: Node date not found");
		        Date mDate = copyDateByValue(getNodeDate(dateNode));
            	boolean b1 = ((mDate.equals(start))||(mDate.after(start)));
            	boolean b2 = ((mDate.before(end))||(mDate.equals(end)));
//	            boolean b1 = (mDate.getTime()>=start.getTime());
//	            boolean b2 = (mDate.getTime()<=end.getTime());
	            if ((b1) &&(b2)) return true;
			} catch (Exception e) {
		        System.out.println("validateMessage: Catch: "+e.getCause()+e.getMessage());
				e.printStackTrace();
			}
         return false;
	    }

 		private Node findNode(Node node, String nn) {
 	    	
	    	// get element name
	        String nodeName = node.getNodeName();
	        boolean found  = false;
	        if ( nodeName.equalsIgnoreCase(nn)) found = true; 
	        if (found) return node;
		    NodeList children = node.getChildNodes();
		    for (int i = 0; i < children.getLength(); i++) {
		        Node child = children.item(i);
		        Node fNode = null; 
		        if (child.getNodeType() == Node.ELEMENT_NODE) {
		        	if ((fNode =findNode(child, nn))!= null) return fNode; 
		        }
		    }
 	    return null;
    }

		private Date getNodeDate(Node node) {

 	    	String nodeName = node.getNodeName();
	        String nodeValue = getElementValue(node);
	        Date mDate = stringToDate(nodeValue);
	        return mDate;
		}
		
		Date stringToDate(String dateStr){
			
			String patternStr = "a,b,c,d,e,f,g,h,i,g,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
			StringTokenizer st = new StringTokenizer(dateStr,patternStr);
        	DateFormat df = new SimpleDateFormat ("yyyyMMddHHmmssSSS");
        	Date mDate = null;
			try {
				mDate = df.parse(st.nextToken()+st.nextToken());
			} catch (ParseException e) 
			{
				System.out.println("getNodeDate: ParseException: ");
				e.printStackTrace();
			}
			return mDate;
		}

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

 			builder = factory.newDocumentBuilder();
 			Document document = builder.parse(new InputSource(new StringReader(xmlString)));
 			root = document.getDocumentElement();
 			return root;
		}
 		// Process only files under dir 
// 		public void visitAllFiles(File pathD){
// 			
// 			if (pathD.isDirectory()){ 
// 				String[] children = pathD.list(); 
// 				for (int i=0; i<children.length; i++) { 
// 					visitAllFiles(new File(pathD, children[i])); 
// 				} 
// 			}else{ 
// 				startAnalysisByStep(pathD);
// 			}
// 		} 
 		
 		// Copies all srcDir under srcDir to dstDir.
 		// If dstDir does not exist, it will be created.
 		public void copyDirectory(File srcDir, File dstDir) throws IOException {
 			 if (srcDir.isDirectory()) {
 			     if (!dstDir.exists()) {
 			         dstDir.mkdir();
 			     }
 			     String[] children = srcDir.list();
 			     for (int i=0; i<children.length; i++) {
 			         copyDirectory(new File(srcDir, children[i]), new File(dstDir, children[i]));
 			     }
 		 	}else{
 		 		String fName = dstDir.getName();
 	 			fName = fName.substring(0, fName.indexOf('.')-1);
 	 			fName +="_"+share.Date()+"_"+share.Hour()+".txt";
 	 			new File(dstDir.getParent()).mkdirs();
 	 			File destFile = new File(dstDir.getParent()+"\\"+fName);
 	 			destFile.createNewFile();
 	 			startAnalysisByStep(srcDir, destFile);
 		 	}
 		}
// 	 	private File makeAlternativeResultFile(File f) throws IOException {
// 	 		
// 			String nPath = f.getName();
// 			nPath = nPath.substring(0, nPath.indexOf('.')-1);
// 			System.out.println("common path is : "+ getCommonPath(f, new File(pathR)).getPath());
// 			System.out.println("Uncommon path is : "+ getUncommonPath(f, new File(pathR)).getPath());
//			File fileR = new File(getCommonPath(f, new File(pathR)).getPath()+"\\"+
//					getUncommonPath(f, new File(pathR)).getPath()+"\\"
//					+nPath+"_"+share.Date()+"_"+share.Hour()+".txt");
// 			(new File(fileR.getParent())).mkdirs();
// 			System.out.println("path is : "+ fileR.getPath());
// 			
// 			fileR.createNewFile();
// 			return fileR;
// 		}
// 		public File getCommonPath(File dir1, File dir2) throws IOException{
// 			
//			while (dir1!= null){
// 				File tDir = new File(dir2.getPath()); 
// 				while (tDir!=null){
// 					if (!tDir.getPath().equalsIgnoreCase(dir1.getPath()))
// 						tDir= (tDir.getParentFile()==null)?null:tDir.getParentFile();
// 					else return tDir; 
// 				}
// 				dir1 = dir1.getParentFile();
// 			}
// 			return null;
// 		}
//		public File getUncommonPath(File dir1, File dir2) throws IOException{
// 			
//			while (dir1!= null){
// 				File tDir = new File(dir2.getPath());
// 				String unCommon = "";
// 				while (tDir!=null){
// 					if (!tDir.getPath().equals(dir1.getPath())){
// 						unCommon = tDir.getName()+"\\"+unCommon;
// 						tDir= (tDir.getParentFile()==null)?null:tDir.getParentFile();
// 					}
// 					else return new File(unCommon); 
// 				}
// 				dir1 = dir1.getParentFile();
// 			}
// 			return null;
// 		}
}
