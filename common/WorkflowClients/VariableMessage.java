package common.WorkflowClients;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class VariableMessage {


	public static void main(String[] args) {
		
		// Obtain an XML document; this method is implemented in
		// The Quintessential Program to Create a DOM Document from an XML File
		Document doc = parseXmlFile("infilename.xml", false);

		// Create a fragment
//		DocumentFragment frag = parseXml(doc, "hello <b>joe</b>");

		// Append the new fragment to the end of the root element
//		Element element = doc.getDocumentElement();
//		element.appendChild(frag);

	}

    public static Document parseXmlFile(String filename, boolean validating) {
        try {
            // Create a builder factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(validating);

            // Create the builder and parse the file
            Document doc = factory.newDocumentBuilder().parse(new File(filename));
            return doc;
        } catch (SAXException e) {
            // A parsing error occurred; the xml input is not valid
        } catch (ParserConfigurationException e) {
        } catch (IOException e) {
        }
        return null;
    }
    	
	 // This method visits all the nodes in a DOM tree
	 public static void visit(Node node, int level) {
	     // Process node
		 System.out.println("Node name is:"+ node.getNodeName());
		 System.out.println("Node value is:"+ node.getNodeValue());
	     // If there are any children, visit each one
	     NodeList list = node.getChildNodes();
	     for (int i=0; i<list.getLength(); i++) {
	         // Get child node
	         Node childNode = list.item(i);
	
	         // Visit child node
	         visit(childNode, level+1);
	     }
	 }

}
