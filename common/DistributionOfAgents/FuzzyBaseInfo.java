package common.DistributionOfAgents;

public class FuzzyBaseInfo {
	
	
	protected String[] hostList = {
    		"10.100.100.53",
    		"10.100.100.51",
    		"10.100.100.52",
    		"10.100.100.56",
    		"10.100.100.57",
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
    };
	
	String[] agentL0 = {//HPD0
		"<agent name=\"LoanTaking.hpd.l0.CentralizedWorkflowAgent.CentralizedWorkflowAgent\" className=\"LoanTaking.hpd.l0.CentralizedWorkflowAgent\"/>",
		};
		String[] agentL1 = {//HPD1
			"<agent name=\"LoanTaking.hpd.l1.Assign1_Agent.Assign1_Agent\" className=\"LoanTaking.hpd.l1.Assign1_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l1.Flow_Agent.Flow_Agent\" className=\"LoanTaking.hpd.l1.Flow_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l1.If_Agent.If_Agent\" className=\"LoanTaking.hpd.l1.If_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l1.Receive1_Agent.Receive1_Agent\" className=\"LoanTaking.hpd.l1.Receive1_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l1.Reply_Agent.Reply_Agent\" className=\"LoanTaking.hpd.l1.Reply_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l1.Sequence0_Agent.Sequence0_Agent\" className=\"LoanTaking.hpd.l1.Sequence0_Agent\"/>",
				
		};
		String[] agentL2 = {//HPD2
			"<agent name=\"LoanTaking.hpd.l2.Assign1_Agent.Assign1_Agent\" className=\"LoanTaking.hpd.l2.Assign1_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l2.Assign4_Agent.Assign4_Agent\" className=\"LoanTaking.hpd.l2.Assign4_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l2.Assign5_Agent.Assign5_Agent\" className=\"LoanTaking.hpd.l2.Assign5_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l2.Flow_Agent.Flow_Agent\" className=\"LoanTaking.hpd.l2.Flow_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l2.If_Agent.If_Agent\" className=\"LoanTaking.hpd.l2.If_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l2.Receive1_Agent.Receive1_Agent\" className=\"LoanTaking.hpd.l2.Receive1_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l2.Reply_Agent.Reply_Agent\" className=\"LoanTaking.hpd.l2.Reply_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l2.Sequence0_Agent.Sequence0_Agent\" className=\"LoanTaking.hpd.l2.Sequence0_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l2.Sequence1_Agent.Sequence1_Agent\" className=\"LoanTaking.hpd.l2.Sequence1_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l2.Sequence2_Agent.Sequence2_Agent\" className=\"LoanTaking.hpd.l2.Sequence2_Agent\"/>",
		};
				
		String[] agentL3 = {//HPD3
			"<agent name=\"LoanTaking.hpd.l3.Assign1_Agent.Assign1_Agent\" className=\"LoanTaking.hpd.l3.Assign1_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l3.Assign2_Agent.Assign2_Agent\" className=\"LoanTaking.hpd.l3.Assign2_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l3.Assign3_Agent.Assign3_Agent\" className=\"LoanTaking.hpd.l3.Assign3_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l3.Assign4_Agent.Assign4_Agent\" className=\"LoanTaking.hpd.l3.Assign4_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l3.Assign5_Agent.Assign5_Agent\" className=\"LoanTaking.hpd.l3.Assign5_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l3.Flow_Agent.Flow_Agent\" className=\"LoanTaking.hpd.l3.Flow_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l3.If_Agent.If_Agent\" className=\"LoanTaking.hpd.l3.If_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l3.Invoke1_Agent.Invoke1_Agent\" className=\"LoanTaking.hpd.l3.Invoke1_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l3.Invoke2_Agent.Invoke2_Agent\" className=\"LoanTaking.hpd.l3.Invoke2_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l3.Receive1_Agent.Receive1_Agent\" className=\"LoanTaking.hpd.l3.Receive1_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l3.Receive2_Agent.Receive2_Agent\" className=\"LoanTaking.hpd.l3.Receive2_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l3.Receive3_Agent.Receive3_Agent\" className=\"LoanTaking.hpd.l3.Receive3_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l3.Reply_Agent.Reply_Agent\" className=\"LoanTaking.hpd.l3.Reply_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l3.Sequence0_Agent.Sequence0_Agent\" className=\"LoanTaking.hpd.l3.Sequence0_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l3.Sequence1_Agent.Sequence1_Agent\" className=\"LoanTaking.hpd.l3.Sequence1_Agent\"/>",
			"<agent name=\"LoanTaking.hpd.l3.Sequence2_Agent.Sequence2_Agent\" className=\"LoanTaking.hpd.l3.Sequence2_Agent\"/>",
		};
		
		String[] agentL4 = {//HIPD0
			"<agent name=\"LoanTaking.hipd.l0.FrequentPath1_Agent.FrequentPath1_Agent\" className=\"LoanTaking.hipd.l0.FrequentPath1_Agent\"/>",
			"<agent name=\"LoanTaking.hipd.l0.Assign5_Agent.Assign5_Agent\" className=\"LoanTaking.hipd.l0.Assign5_Agent\"/>",
		};
		
		String[] agentL5 = {//HIPD1
				"<agent name=\"LoanTaking.hipd.l1.Assign5_Agent.Assign5_Agent\" className=\"LoanTaking.hipd.l1.Assign5_Agent\"/>",
				"<agent name=\"LoanTaking.hipd.l1.FrequentPath1_Agent.FrequentPath1_Agent\" className=\"LoanTaking.hipd.l1.FrequentPath1_Agent\"/>",
				"<agent name=\"LoanTaking.hipd.l1.Sequence0_Agent.Sequence0_Agent\" className=\"LoanTaking.hipd.l1.Sequence0_Agent\"/>",
		};
	
		void showTotalLiat(){
			String [] totalList = getTotalList(agentL0, agentL1, agentL2, agentL3);
			for (int i =0; i<totalList.length; i++)
				System.out.println(totalList[i]);
			
		}
		
		String [] getTotalList(String[] l0, String[] l1, String[] l2, String[] l3){

			String [] totalList = new String [l0.length+l1.length+l2.length+l3.length];
			
			for (int i=0; i<l0.length; i++){
				totalList[i] = l0[i];
			}
			for (int i=l0.length; i<(l0.length+l1.length); i++){
				totalList[i] = l1[i - l0.length];
			}
			for (int i=l0.length+l1.length; i<(l0.length+l1.length+l2.length); i++){
				totalList[i] = l2[i - l0.length - l1.length];
			}
			for (int i=(l0.length+l1.length+l2.length); i<(l0.length+l1.length+l2.length+l3.length); i++){
				totalList[i] = l3[i - l0.length - l1.length - l2.length];
			}
			return totalList;
		}

//		void distribution(String[] l){
//			
//			for (int i = 0; i<nos; i++){
//				for (int j=i; j<l.length; j=j+ nos){ 
//					System.out.println("\t\t\t\t\t"+l[j]);
//				}
//			}
//		}
//		
//		void distributeForServer(String[] l, int sn){
//			
//			for (int b = sn; b<l.length; b=b+ nos){ 
//				System.out.println("\t\t\t\t\t"+l[b]);
//			}
//		}
//		void circularDistributionForServer(String[] l, int sn){
//			int i =0;
//			int j =0; 
//			while(i<l.length){
//				if (j == sn) System.out.println("\t\t\t\t\t"+l[i]);
//				j= (++j<nos)? j: j%nos;
//				i++;
//			}
//		}
//		
//		
//		void circularFairDistribution(){
//			String [] tl = getTotalLiat(agentL0, agentL1, agentL2, agentL3);			
//			for (int i=0; i<nos; i++){
//				System.out.println("\t<host name=\"10.100.100.51\">");
//				System.out.println("\t\t<containers>");
//				System.out.println("\t\t\t<container name=\"server\" jadeProfile=\"monitored\">");
//	            System.out.println("\t\t\t\t<agents>");
//	            circularDistributionForServer(tl,i);
//				System.out.println("\t\t\t\t</agents>");
//				System.out.println("\t\t\t</container>");
//				System.out.println("\t\t</containers>");
//				System.out.println("\t</host>");  
//			}
//		}
//
//		
//	
//		void fairDistribution(){
//			
//			for (int i=0; i<nos; i++){
//				System.out.println("\t<host name=\"10.100.100.51\">");
//				System.out.println("\t\t<containers>");
//				System.out.println("\t\t\t<container name=\"server\" jadeProfile=\"monitored\">");
//	            System.out.println("\t\t\t\t<agents>");
//				distributeForServer(agentL0,i);
//				distributeForServer(agentL1,i);
//				distributeForServer(agentL2,i);
//				distributeForServer(agentL3,i);
//				System.out.println("\t\t\t\t</agents>");
//				System.out.println("\t\t\t</container>");
//				System.out.println("\t\t</containers>");
//				System.out.println("\t</host>");  
//			}
//		}


}
