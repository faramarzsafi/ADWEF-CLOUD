package common;

public  class ContainerManagement {
	
	//FuzzyWorkflowClient needs to NumberOfServers
	//mode1 needs to NumberOfServers
	public static final int  numOfServers =0;
	static final int mode = 2;
	
	static int  n0=0;  static int n1=0; static int n2=0;static int n3=0;static int n4=0;static int n5=0;static int n6=0;static int n7=0;
	static int sn = 0;//used for round robin distribution of workflow fragments to performers. 
	
	synchronized public static String getContainer(String performer, String subflow){
//		System.out.println("mode is: "+mode);
		if (mode == 0)
			return mode0();
		else if (mode == 1)
			return mode1(performer, numOfServers);
		else if (mode == 2)
			return mode2(performer);
		else if (mode == 3)
			return mode3(performer, numOfServers);
		else if (mode == 4)
			return mode4(performer);
		else if (mode == 5)//Mode 5 is equal to Mode2-Fuzzy...
			return mode5(performer, subflow);
		else if (mode == 6)//Mode 6 is equal to Mode3-Fuzzy...
			return mode6(performer, subflow, numOfServers);
		return null;
	}

	private static String mode0() {
		//this method means having client and server workflows on one container and on one machine.
//		System.out.println("Mode0 selected for containers");
		return "Performer_Agent";
	}

	private static String mode1(String containerName, int nos) {
		//this method means having a client on mere one machine and one distinct performer on each server   
//		System.out.println("Mode1 selected for containers");

//          random distribution of workflows on performers
//			int min = 1;
//			int max = nos;
//			int boundNumber = (int)(Math.random() * (max - min + 1) ) + min;
//			
//			switch(boundNumber){
//				case 1: n1++; break;
//				case 2:n2++;break;
//				case 3:n3++;break;
//			}
//			return (boundNumber-1 < 0)? "server":"server"+ boundNumber;
		
//		Round Robin distribution of workflows on performers.
		sn = (sn+1) % (nos);

//		switch(sn){
//		case 0:n0++; break;
//		case 1:n1++; break;
//		case 2:n2++;break;
//		case 3:n3++;break;
//		case 4:n4++;break;
//		case 5:n5++;break;
//		case 6:n6++;break;
//		case 7:n7++;break;
//	}
		return (sn < 1)? "Performer_Agent":"Performer_Agent"+ sn;
	}
	
	private static String mode2(String containerName){
//	    this method means having a client on a machine and one distinct performer for each workflow fragement
//		System.out.println("Mode2 selected for container: "+containerName);
		return containerName;
	}
	
	private static String mode3(String containerName, int nos) {
		sn = (sn+1) % (nos);
		return (sn < 1)? containerName:containerName+ sn;
	}
	
	private static String mode4(String containerName) {//is equal to NINOS
		
		if (containerName.indexOf("CentralizedWorkflowAgent")>=0)
			return containerName;
		else if (containerName.indexOf("Assign")>=0)
			return "Assign_Agent";
		else if (containerName.indexOf("Sequence")>=0)
			return "Sequence_Agent";
		else if (containerName.indexOf("Pick")>=0)
			return "Pick_Agent";
		else if (containerName.indexOf("While")>=0) 
			return "While_Agent";
		else if (containerName.indexOf("Flow")>=0) 
			return "Flow_Agent";
		else if (containerName.indexOf("If")>=0)
			return "If_Agent";
		else if (containerName.indexOf("Receive")>=0)
			return "Receive_Agent";
		else if (containerName.indexOf("Reply")>=0)
			return "Reply_Agent";
		else if (containerName.indexOf("Invoke")>=0)
			return "Invoke_Agent";
		else if (containerName.indexOf("Client")>=0)
			return "Client_Agent";
		else 
			return containerName;
	}
	
	private static String mode5(String performer, String subflow){//is equal to Fuzzy mode-2
		
//		return subflow+"."+containerName;
		String performerAddress = 
			(performer.equalsIgnoreCase("CentralizedWorkflowAgent"))?
					subflow+"Agent"+"."+performer:subflow+"_Agent"+"."+performer;
		return  performerAddress;
	}
	
	private static String mode6(String performer, String subflow, int nos){//is equal to Fuzzy mode-2
		
//		return subflow+"."+containerName;
		String performerAddress = 
			(performer.equalsIgnoreCase("CentralizedWorkflowAgent"))?
					subflow+"Agent"+"."+performer:subflow+"_Agent"+"."+performer;
		sn = (sn+1) % (nos);
		return  ((sn<1)?performerAddress:performerAddress+sn);
	}

//	public static void setNOS(int number_of_servers) {
//		numOfServers = number_of_servers;
////		System.out.println("number_of_servers is: "+number_of_servers);
//	}
	
//	public static void setMode(int executionMode) {
//		mode = executionMode;
////		System.out.println("Execution mode is: Mode"+mode);
//	}
	
	public static int getExecutionMode() {
		return mode;
	}

	public static void main(String[] args) {
		for (int i=1;i<=100;i++){
			System.out.println(ContainerManagement.mode6("CentralizedWorkflowAgent","a.b.c", 10));
//			System.out.println(ContainerManagement.mode2("toTest"));
	}
//		System.out.println("n0= "+n0);
//		System.out.println("n1= "+n1);
//		System.out.println("n2= "+n2);
//		System.out.println("n3= "+n3);
//		System.out.println("n4= "+n4);
//		System.out.println("n5= "+n5);
//		System.out.println("n6= "+n6);
//		System.out.println("n7= "+n7);
	}
}
