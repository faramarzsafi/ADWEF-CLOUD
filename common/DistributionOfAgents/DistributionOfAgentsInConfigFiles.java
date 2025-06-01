package common.DistributionOfAgents;
/*
 * ParseXMLFile.java
 *
 * Created on 4. listopad 2003, 10:14
 */

import java.io.*;

import org.w3c.dom.*;
import org.xml.sax.*;

import common.ContainerManagement;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

//it is to initialize the configuration files based on the following information
public class DistributionOfAgentsInConfigFiles {
    
	
	private static final String BULK = "2";
	private static final String POOL_SIZE = "0";
	private static final String SPEED = "60000";
	
	private static final String RATE_START = "50";
	private static final String RATE_STEP = "500";
	private static final String MAX_RATE = "500";
	
	private static final String TYPE = "JAVA";
	private static final String TYPE_KEY = "Xmx1024m";
	
    String[] hostList = {
    		"10.100.100.53",
    		"10.100.100.51",
    		"10.100.100.52",
    		"10.100.100.56",
    		"10.100.100.58",
    		"10.100.100.59",
    		"10.100.100.60",
    		"10.100.100.61",
    		"10.100.100.62",
    		"10.100.100.63",
    		"10.100.100.64",
    		"10.100.100.65",
    		"10.100.100.66",
    		"10.100.100.67",
    		"10.100.100.68",
    		"10.100.100.69",
    		"10.100.100.70",
    };
    
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
    
    public final static void setElementValue( Node elem, String value ) {
        Node kid ;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( kid = elem.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
                    if( kid.getNodeType() == Node.TEXT_NODE  ){
                        kid.setNodeValue(value);
                    }
                }
            }
        }
    }
    
    private String getIndentSpaces(int indent) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < indent; i++) {
            buffer.append(" ");
        }
        return buffer.toString();
    }
    
    public int writeDocumentToOutput(Node node,int indent, int index) {

	        String nodeName = node.getNodeName();
	        if ( nodeName.equalsIgnoreCase("host")){
		        NamedNodeMap attributes = node.getAttributes();
	        	for (int i = 0; i < attributes.getLength(); i++) {
	            Node attribute = attributes.item(i);
	            if ( attribute.getNodeName().equalsIgnoreCase("name")){
	            	attribute.setNodeValue(hostList[index]);
		        	index++;
	            }
	    	}
		    }else if ( nodeName.equalsIgnoreCase("containerProfile")){
		        NamedNodeMap attributes = node.getAttributes();
		        boolean javaProfile = false;
	        	for (int i = 0; i < attributes.getLength(); i++) {
		            Node attribute = attributes.item(i);
	            	if ( attribute.getNodeName().equalsIgnoreCase("type"))
						attribute.setNodeValue(TYPE);
        		}

		    }else if ( nodeName.equalsIgnoreCase("property")){
		        NamedNodeMap attributes = node.getAttributes();
	        	for (int i = 0; i < attributes.getLength(); i++) {
	            Node attribute = attributes.item(i);
	            	if ( attribute.getNodeName().equalsIgnoreCase("value"))
						attributes.removeNamedItem("value");
	            	if ( attribute.getNodeName().equalsIgnoreCase("key"))
		            	attribute.setNodeValue(TYPE_KEY);
	        	}
		    }else if ( nodeName.equalsIgnoreCase("container")){
		        NamedNodeMap attributes = node.getAttributes();
	        	for (int i = 0; i < attributes.getLength(); i++) {
	            Node attribute = attributes.item(i);
	            	if ( attribute.getNodeName().equalsIgnoreCase("jadeProfile")){
						attributes.removeNamedItem("jadeProfile");
						Node attNode = attribute.getOwnerDocument().createAttribute("javaProfile");
						attNode.setNodeValue("monitored");
						attributes.setNamedItem(attNode);
	            	}
	        	}
		    }else if ( nodeName.equalsIgnoreCase("parameter")){
		        NamedNodeMap attributes = node.getAttributes();
	        	for (int i = 0; i < attributes.getLength(); i++) {
	            Node attribute = attributes.item(i);
	            if ( attribute.getNodeName().equalsIgnoreCase("key")&&attribute.getNodeValue().equalsIgnoreCase("POOL_SIZE")){
	            	NodeList keyChildren = node.getChildNodes();
	        	    for (int j = 0; j < keyChildren.getLength(); j++) {
	        	        Node keyChild = keyChildren.item(j);
	        	        if (keyChild.getNodeType() == Node.ELEMENT_NODE)
	        	        	if(keyChild.getNodeName().equalsIgnoreCase("value")){
	        	        		setElementValue(keyChild, POOL_SIZE);
	        	        	}
	        	    }
		    }
            if ( attribute.getNodeName().equalsIgnoreCase("key")&&attribute.getNodeValue().equalsIgnoreCase("SPEED")){
	            	NodeList keyChildren = node.getChildNodes();
	        	    for (int j = 0; j < keyChildren.getLength(); j++) {
	        	        Node keyChild = keyChildren.item(j);
	        	        if (keyChild.getNodeType() == Node.ELEMENT_NODE)
	        	        	if(keyChild.getNodeName().equalsIgnoreCase("value")){
	        	        		setElementValue(keyChild, SPEED);
	        	        	}
	        	    }
	            }
            if ( attribute.getNodeName().equalsIgnoreCase("key")&&attribute.getNodeValue().equalsIgnoreCase("MAXRATE")){
            	NodeList keyChildren = node.getChildNodes();
        	    for (int j = 0; j < keyChildren.getLength(); j++) {
        	        Node keyChild = keyChildren.item(j);
        	        if (keyChild.getNodeType() == Node.ELEMENT_NODE)
	    	        	if(keyChild.getNodeName().equalsIgnoreCase("value")){
	    	        		setElementValue(keyChild, MAX_RATE);
	    	        	}
        	    }
            }
            
            if ( attribute.getNodeName().equalsIgnoreCase("key")&&attribute.getNodeValue().equalsIgnoreCase("RATE_START")){
            	NodeList keyChildren = node.getChildNodes();
        	    for (int j = 0; j < keyChildren.getLength(); j++) {
        	        Node keyChild = keyChildren.item(j);
        	        if (keyChild.getNodeType() == Node.ELEMENT_NODE)
        	        	if(keyChild.getNodeName().equalsIgnoreCase("value")){
//        	        		System.out.println(getIndentSpaces(indent) + "NodeName: " + keyChild.getNodeName()+ ", NodeValue: " + getElementValue(keyChild));
        	        		setElementValue(keyChild, RATE_START);
//        	        		System.out.println(getIndentSpaces(indent) + "NodeName: " + keyChild.getNodeName()+ ", NodeValue: " + getElementValue(keyChild));
        	        	}
        	    }
            }
            
            if ( attribute.getNodeName().equalsIgnoreCase("key")&&attribute.getNodeValue().equalsIgnoreCase("RATE_STEP")){
            	NodeList keyChildren = node.getChildNodes();
            	for (int j = 0; j < keyChildren.getLength(); j++) {
            		Node keyChild = keyChildren.item(j);
            		if (keyChild.getNodeType() == Node.ELEMENT_NODE)
            			if(keyChild.getNodeName().equalsIgnoreCase("value")){
            				setElementValue(keyChild, RATE_STEP);
    	        	}
    	    	}
        	}
            
            if ( attribute.getNodeName().equalsIgnoreCase("key")&&attribute.getNodeValue().equalsIgnoreCase("BULK")){
            	NodeList keyChildren = node.getChildNodes();
        	    for (int j = 0; j < keyChildren.getLength(); j++) {
        	        Node keyChild = keyChildren.item(j);
        	        if (keyChild.getNodeType() == Node.ELEMENT_NODE)
        	        	if(keyChild.getNodeName().equalsIgnoreCase("value")){
        	        		setElementValue(keyChild, BULK);
        	        	}
        	    	}
            	}
        	}
    	}

        NodeList children = node.getChildNodes();
	    for (int i = 0; i < children.getLength(); i++) {
	        Node child = children.item(i);
	        if (child.getNodeType() == Node.ELEMENT_NODE) {
	            index = writeDocumentToOutput(child,indent + 2, index);
	        }
	    }
	    return index;
    }
    
    /** Saves XML Document into XML file.
     * @param fileName XML file name
     * @param doc XML document to save
     * @return <B>true</B> if method success <B>false</B> otherwise
     */    
    public boolean saveXMLDocument(String fileName, Document doc) {
//        System.out.println("Saving XML file... " + fileName);
        // open output stream where XML Document will be saved
        File xmlOutputFile = new File(fileName);
        FileOutputStream fos;
        Transformer transformer;
        try {
            fos = new FileOutputStream(xmlOutputFile);
        }
        catch (FileNotFoundException e) {
            System.out.println("Error occured: " + e.getMessage());
            return false;
        }
        // Use a Transformer for output
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            transformer = transformerFactory.newTransformer();
        }
        catch (TransformerConfigurationException e) {
            System.out.println("Transformer configuration error: " + e.getMessage());
            return false;
        }
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(fos);
        // transform source into result will do save
        try {
            transformer.transform(source, result);
        }
        catch (TransformerException e) {
            System.out.println("Error transform: " + e.getMessage());
        }
//        System.out.println("XML file saved.");
        return true;
    }
    
    /** Parses XML file and returns XML document.
     * @param fileName XML file to parse
     * @return XML document or <B>null</B> if error occured
     */
    public Document parseFile(String fileName) {
//        System.out.println("Parsing XML file... " + fileName);
        DocumentBuilder docBuilder;
        Document doc = null;
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setIgnoringElementContentWhitespace(true);
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            System.out.println("Wrong parser configuration: " + e.getMessage());
            return null;
        }
        File sourceFile = new File(fileName);
        try {
            doc = docBuilder.parse(sourceFile);
        }
        catch (SAXException e) {
            System.out.println("Wrong XML file structure: " + e.getMessage());
            return null;
        }
        catch (IOException e) {
            System.out.println("Could not read source file: " + e.getMessage());
        }
//        System.out.println("XML file parsed");
        return doc;
    }
	public boolean isDirECTORY(String fileOrDirName){
		
		File dir = new File(fileOrDirName);
	    return dir.isDirectory();
	}
	
	
	void goDir(String path){	
		File dir = new File(path);
		String[] children = dir.list();
	    if (children == null) {
	    	System.out.println("Either dir does not exist or is not a directory");
	    } else {
	        for (int i=0; i<children.length; i++) {
//	        	String xmlFileName = children[i];
	        	System.out.println("children["+i+"]= "+children[i]);
	        	String newPath = path + children[i];
	        	if (isDirECTORY(newPath)){
	        		goDir(newPath+"/");
	        	}else{ //if (!isDirECTORY(path+xmlFileName)){
	
			        // parse XML file -> XML document will be build
			        Document doc = parseFile(newPath);
			        // get root node of xml tree structure
			        Node root = doc.getDocumentElement();
			        // write node and its child nodes into System.out
//			        System.out.println("Statemend of XML document...");
			        writeDocumentToOutput(root,0, 0);
//			        System.out.println("... end of statement");
			        // write Document into XML file
			        saveXMLDocument(newPath, doc);
	    		}
	        }
	    }
	}

    
    /** Starts XML parsing example
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	DistributionOfAgentsInConfigFiles pxf = new DistributionOfAgentsInConfigFiles();
        
//        String path = "c:\\safi\\wade\\cfg\\Mode3-fuzzy\\6Servers\\";
//          String path = "/opt/wade/cfg/mode2/";
          String path = "/opt/wade/cfg/configuration/";
//        	String path = "d:\\mode2\\";
//        String path = "c:\\safi\\wade\\cfg\\Mode2\\";
		pxf.goDir(path);
    }
}
