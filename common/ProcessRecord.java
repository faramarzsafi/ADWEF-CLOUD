package common;
public class ProcessRecord {
	
	int nof;
	String subflow;
	String subflowAgent;
	
	public ProcessRecord(){
		nof = 0;
		subflow = "";
		subflowAgent = "";
	}
	public ProcessRecord(int nof, String subflow, String subflowAgent){
		this.nof = nof;
		this.subflow = subflow;
		this.subflowAgent = subflowAgent;
	}
	public int getNof(){
		return nof;
	}
	public String getSubflow(){
		return subflow;
	}
	public String getSubflowAgent(){
		return subflowAgent;
	}
}