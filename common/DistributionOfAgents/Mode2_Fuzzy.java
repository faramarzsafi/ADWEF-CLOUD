package common.DistributionOfAgents;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Mode2_Fuzzy extends FuzzyBaseInfo{


	void Mode2_Fuzzy_Distribution(String path) throws IOException{
		for(int nos=1; nos<=16;nos++){ 
			Mode2_circularFairDistributionSave(path, nos);
		}
	}
	
	void Mode2_circularFairDistributionSave(String path, int nos) throws IOException{
		
//		String [] tl = getTotalList(agentL0, agentL1, agentL2, agentL3);//Considering HPD methods
		String [] tl = getTotalList(agentL4, agentL5, agentL2, agentL3);//Considering HIPD methods
		String directoryName = path+(nos)+(((nos)==1)?"Server":"Servers");
		(new File(directoryName)).mkdirs(); 
		Writer out = new OutputStreamWriter(new FileOutputStream(directoryName+"/"+"Loan_33_"+nos+".xml"));
		out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		out.write("<platform description=\"This is a sample configuration\" name=\""+"Loan_33_"+nos+"\">\n");
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
		out.write("\t\t\t\t\t\t\t\t\t<value>"+nos+"</value>\n");
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

		for (int i=0; i<nos; i++){
			circularDistributionForServerSave(tl,i, nos, out);
		}
		
		out.write("\t</hosts>\n");
		out.write("</platform>");
		out.close();
	}

	void circularDistributionForServerSave(String[] l, int sn, int nos, Writer out) throws IOException{
		
		int i =0;
		int j =0; 
		out.write("\t\t<host name=\""+hostList[sn+1]+"\">\n");
		out.write("\t\t\t<containers>\n");
		out.write("\t\t\t\t<container name=\"server\" jadeProfile=\"monitored\">\n");
        out.write("\t\t\t\t\t<agents>\n");

		while(i<l.length){
			if (j == sn) out.write("\t\t\t\t\t"+l[i]+"\n");
			j= (++j<nos)? j: j%nos;
			i++;
		}
		
		out.write("\t\t\t\t\t</agents>\n");
		out.write("\t\t\t\t</container>\n");
		out.write("\t\t\t</containers>\n");
		out.write("\t\t</host>\n");  
	}

	public static void main(String[] args) throws IOException {
		System.out.print("Starting ....");
		Mode2_Fuzzy mad = new Mode2_Fuzzy();
		mad.Mode2_Fuzzy_Distribution("/opt/wade/cfg/mode2-fuzzy/");
		System.out.println("Finished.");
	}
}
