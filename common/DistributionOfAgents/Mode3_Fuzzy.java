package common.DistributionOfAgents;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

import java.util.regex.*;


import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;

public class Mode3_Fuzzy extends FuzzyBaseInfo{


	void Mode3_Fuzzy_Distribution(String path) throws IOException{
		
		String [] tl = getTotalList(agentL0, agentL1, agentL2, agentL3);
		for (int server = 1; server<=16; server++ ){
			String directoryName = path+(server)+(((server)==1)?"Server":"Servers");
			(new File(directoryName)).mkdirs();
			Writer out = new OutputStreamWriter(new FileOutputStream(directoryName+"/"+"Loan_33_"+server+".xml"));
			out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			out.write("<platform description=\"This is a sample configuration\" name=\""+"Loan_33_"+server+"\">\n");
			out.write("\t<containerProfiles>\n");
			out.write("\t\t<containerProfile name=\"monitored\" type=\"JADE\">\n");
			out.write("\t\t<properties>\n");
			out.write("\t\t\t<property key=\"jade_core_AgentContainerImpl_enablemonitor\" value=\"true\"/>\n");
			out.write("\t\t\t</properties>\n");
			out.write("\t\t</containerProfile>\n");
			out.write("\t</containerProfiles>\n");
			out.write("\t<hosts>\n");
			out.write("\t\t<host name=\""+hostList[0]+"\">\n");
			out.write("\t\t\t<containers>\n");
			out.write("\t\t\t\t<container name=\"Client\" jadeProfile=\"monitored\">\n");
			out.write("\t\t\t\t\t<agents>\n");
			out.write("\t\t\t\t\t\t<agent name=\"Client_Agent\" className=\"common.FuzzyClientAgent\">\n");
			out.write("\t\t\t\t\t\t\t<parameters>\n");
			out.write("\t\t\t\t\t\t\t\t<parameter key=\"BULK\">\n");
			out.write("\t\t\t\t\t\t\t\t\t<value>2</value>\n");
			out.write("\t\t\t\t\t\t\t\t</parameter>\n");
			out.write("\t\t\t\t\t\t\t\t<parameter key=\"NUMBER_OF_SERVERS\">\n");
			out.write("\t\t\t\t\t\t\t\t\t<value>"+ server +"</value>\n");
			out.write("\t\t\t\t\t\t\t\t</parameter>\n");
			out.write("\t\t\t\t\t\t\t\t<parameter key=\"NUMBER_OF_AGENTS\">\n");
			out.write("\t\t\t\t\t\t\t\t\t<value>33</value>\n");
			out.write("\t\t\t\t\t\t\t\t</parameter>\n");
			out.write("\t\t\t\t\t\t\t\t<parameter key=\"WORKFLOW_STYLE\">\n");
			out.write("\t\t\t\t\t\t\t\t\t<value>101</value>\n");
			out.write("\t\t\t\t\t\t\t\t</parameter>\n");
			out.write("\t\t\t\t\t\t\t\t<parameter key=\"POOL_SIZE\">\n");
			out.write("\t\t\t\t\t\t\t\t\t<value>0</value>\n");
			out.write("\t\t\t\t\t\t\t\t</parameter>\n");
			out.write("\t\t\t\t\t\t\t\t<parameter key=\"SPEED\">\n");
			out.write("\t\t\t\t\t\t\t\t\t<value>60000</value>\n");
			out.write("\t\t\t\t\t\t\t\t</parameter>\n");
			out.write("\t\t\t\t\t\t\t\t<parameter key=\"MAXRATE\">\n");
			out.write("\t\t\t\t\t\t\t\t\t<value>6000</value>\n");
			out.write("\t\t\t\t\t\t\t\t</parameter>\n");
			out.write("\t\t\t\t\t\t\t\t<parameter key=\"RATE_START\">\n");
			out.write("\t\t\t\t\t\t\t\t\t<value>6000</value>\n");
			out.write("\t\t\t\t\t\t\t\t</parameter>\n");
			out.write("\t\t\t\t\t\t\t\t<parameter key=\"RATE_STEP\">\n");
			out.write("\t\t\t\t\t\t\t\t\t<value>6000</value>\n");
			out.write("\t\t\t\t\t\t\t\t</parameter>\n");
			out.write("\t\t\t\t\t\t\t</parameters>\n");
			out.write("\t\t\t\t\t\t</agent>\n");
			out.write("\t\t\t\t\t</agents>\n");
			out.write("\t\t\t\t</container>\n");
			out.write("\t\t\t</containers>\n");
			out.write("\t\t</host>\n");


			for (int i=0; i<server; i++){
				DistributionForServerSave(tl,i, out);
			}
		
			out.write("\t</hosts>\n");
			out.write("</platform>");
			out.close();
		}
	}
	
	void DistributionForServerSave(String[] tl, int sn, Writer out) throws IOException{

		out.write("\t\t<host name=\""+hostList[sn+1]+"\">\n");
		out.write("\t\t\t<containers>\n");
		out.write("\t\t\t\t<container name=\"server\" jadeProfile=\"monitored\">\n");
        out.write("\t\t\t\t\t<agents>\n");
        
		for (int i=0; i<tl.length; i++)	
			out.write("\t\t\t\t\t"+matchParts(tl[i], sn)+"\n");
		
		out.write("\t\t\t\t\t</agents>\n");
		out.write("\t\t\t\t</container>\n");
		out.write("\t\t\t</containers>\n");
		out.write("\t\t</host>\n");  
	}
	
	  private static final String fNEW_LINE = System.getProperty("line.separator");
	  private static final String fREGEXP =
	      "#Group1 - Package prefix without last dot: " + fNEW_LINE +
	      "( (?:\\w|\\.)+ ) \\." + fNEW_LINE +
	      "#Group2 - Class name starts with uppercase: " + fNEW_LINE +
	      "( [A-Z](?:\\w)+ )";
	  private  String matchParts( String aText, int sn ){
//		    System.out.println(fNEW_LINE + "Match PARTS:");
		    //(note the necessity of the comments flag, since our regular
		    //expression contains comments:)
		    Pattern pattern = Pattern.compile( fREGEXP, Pattern.COMMENTS );
		    Matcher matcher = pattern.matcher( aText );
		    
		    String finalStr = "<agent name=\"";
//		    while ( matcher.find() ) {
		    matcher.find();
		    
		    finalStr += matcher.group(1)+"."+
		    	matcher.group(2)+((sn==0)?"":sn)+
		    			"\""+" className=\"";
		    
		    matcher.find();
		    finalStr += matcher.group(1)+"."+matcher.group(2)+"\"/>";
//		    System.out.println("finalStr= "+finalStr);
//		    System.out.println("Num groups: " + matcher.groupCount());
//		    System.out.println("Package: " + matcher.group(1));
//		    System.out.println("Class: " + matcher.group(2));
//		    }
		    return finalStr;
		  }

	public static void main(String[] args) throws IOException {
		System.out.print("Starting ....");
		String fileName;
		Mode3_Fuzzy mad = new Mode3_Fuzzy();
//		mad.distribution();
//		mad.showTotalLiat();
//		mad.fairDistribution();
//		mad.circularFairDistribution();
//		for(mad.nos=1; mad.nos<=16;mad.nos++){ 
//			mad.Mode2_Fuzzy_Distribution("/opt/wade/cfg/mode2-fuzzy/");
//		}
//		System.out.println("all the files configured");
//		mad.nos = 16;
//		mad.Mode3_Fuzzy_Distribution("C:\\Safi\\wade\\cfg\\mode3-fuzzy\\");
		mad.Mode3_Fuzzy_Distribution("/opt/wade/cfg/mode3-fuzzy/");
		
		System.out.println("Finished.");
	}
}
