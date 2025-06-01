package common.tools;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameFilter {
	
	public static final int EX = 0;
	public static final int IF = 1;
	public static final int WHILE= 2;
	public static final int FLOW = 3;
	public static final int LOAN = 4;
	
	String[] fNames = {
				
				"LOAN_HIPD0_2_1",
				"LOAN_HIPD0_2_2",
				"LOAN_HIPD1_3_1",
				"LOAN_HIPD1_3_2",
				"LOAN_HIPD1_3_3",
				"LOAN_HPD0_1_1",
				"LOAN_HPD1_6_1",
				"LOAN_HPD1_6_2",
				"LOAN_HPD1_6_3",
				"LOAN_HPD1_6_4",
				"LOAN_HPD1_6_5",
				"LOAN_HPD1_6_6",
				"LOAN_HPD2_10_1",
				"LOAN_HPD2_10_2",
				"LOAN_HPD2_10_3",
				"LOAN_HPD2_10_4",
				"LOAN_HPD2_10_5",
				"LOAN_HPD2_10_6",
				"LOAN_HPD2_10_7",
				"LOAN_HPD2_10_8",
				"LOAN_HPD2_10_9",
				"LOAN_HPD2_10_10",
				"LOAN_HPD3_16_1",
				"LOAN_HPD3_16_2",
				"LOAN_HPD3_16_3",
				"LOAN_HPD3_16_4",
				"LOAN_HPD3_16_5",
				"LOAN_HPD3_16_6",
				"LOAN_HPD3_16_7",
				"LOAN_HPD3_16_8",
				"LOAN_HPD3_16_9",
				"LOAN_HPD3_16_10",
				"LOAN_HPD3_16_11",
				"LOAN_HPD3_16_12",
				"LOAN_HPD3_16_13",
				"LOAN_HPD3_16_14",
				"LOAN_HPD3_16_15",
				"LOAN_HPD3_16_16",
				
				"FLOW_HIPD1_2_1",
				"FLOW_HIPD1_2_2",
				"FLOW_HPD0_1_1",
				"FLOW_HPD1_4_1",
				"FLOW_HPD1_4_2",
				"FLOW_HPD1_4_3",
				"FLOW_HPD1_4_4",
				"FLOW_HPD2_6_1",
				"FLOW_HPD2_6_2",
				"FLOW_HPD2_6_3",
				"FLOW_HPD2_6_4",
				"FLOW_HPD2_6_5",
				"FLOW_HPD2_6_6",
				
				"IF_HIPD0_2_1",
				"IF_HIPD0_2_2",
				"IF_HIPD1_3_1",
				"IF_HIPD1_3_2",
				"IF_HIPD1_3_3",
				"IF_HPD0_1_1",
				"IF_HPD1_4_1",
				"IF_HPD1_4_2",
				"IF_HPD1_4_3",
				"IF_HPD1_4_4",
				"IF_HPD2_6_1",
				"IF_HPD2_6_2",
				"IF_HPD2_6_3",
				"IF_HPD2_6_4",
				"IF_HPD2_6_5",
				"IF_HPD2_6_6",
				
				"WHILE_HIPD1_2_1",
				"WHILE_HIPD1_2_2",
				"WHILE_HPD0_1_1",
				"WHILE_HPD1_4_1",
				"WHILE_HPD1_4_2",
				"WHILE_HPD1_4_3",
				"WHILE_HPD1_4_4",
				"WHILE_HPD2_5_1",
				"WHILE_HPD2_5_2",
				"WHILE_HPD2_5_3",
				"WHILE_HPD2_5_4",
				"WHILE_HPD2_5_5",
				
				"EX1",
				"EX2",
				"EX3",
				"EX4"
				
		};
		String[] mistakeCases = {
				
				"LOAN_HIPD0_2_1",
				"LOAN_HIPD0_2_2",

				"LOAN_HIPD1_3_1",
				"LOAN_HIPD1_3_2",
				"LOAN_HIPD1_3_3",

				"LOAN_HPD2_10_1",
				"LOAN_HPD2_10_2",
				"LOAN_HPD2_10_3",
				"LOAN_HPD2_10_4",
				"LOAN_HPD2_10_5",
				"LOAN_HPD2_10_6",
				"LOAN_HPD2_10_7",
				"LOAN_HPD2_10_8",
				"LOAN_HPD2_10_9",
				"LOAN_HPD2_10_10",
				
				"LOAN_HPD3_16_1",
				"LOAN_HPD3_16_2",
				"LOAN_HPD3_16_3",
				"LOAN_HPD3_16_4",
				"LOAN_HPD3_16_5",
				"LOAN_HPD3_16_6",
				"LOAN_HPD3_16_7",
				"LOAN_HPD3_16_8",
				"LOAN_HPD3_16_9",
				"LOAN_HPD3_16_10",
				"LOAN_HPD3_16_11",
				"LOAN_HPD3_16_12",
				"LOAN_HPD3_16_13",
				"LOAN_HPD3_16_14",
				"LOAN_HPD3_16_15",
				"LOAN_HPD3_16_16",
				
				"FLOW_HPD2_6_1",
				"FLOW_HPD2_6_2",
				"FLOW_HPD2_6_3",
				"FLOW_HPD2_6_4",
				"FLOW_HPD2_6_5",
				"FLOW_HPD2_6_6",
				
				"WHILE_HPD2_5_1",
				"WHILE_HPD2_5_2",
				"WHILE_HPD2_5_3",
				"WHILE_HPD2_5_4",
				"WHILE_HPD2_5_5",
		};
		String CENT_REGEXs[]={
				"LOAN_HPD0",
				"FLOW_HPD0",
				"IF_HPD0_1",
				"WHILE_HPD0_1"
		};
		String IPD_REGEXs[]={
				"LOAN_HIPD0_2",
				"FLOW_HIPD0",
				"IF_HIPD0_2",
				"WHILE_HIPD0",
		};
		String FPD_REGEXs[]={
				"LOAN_HPD3",
				"LOAN_HIPD3",
				"FLOW_HPD2",
				"FLOW_HIPD2",
				"IF_HPD2",
				"IF_HIPD2",
				"FLOW_HIPD2",
				"WHILE_HPD2",
				"WHILE_HIPD2",
		};					
		String EXP_REGEXs[]={
				"EX1",
				"EX2",
				"EX3",
				"EX4"
		};
		//Max distribution patterns
		String MAX_DIST_IF[]={
				"IF_HIPD0_2_2",
				"IF_HIPD1_3_3",
				"IF_HPD1_4_4",
				"IF_HPD2_6_6",
		};
		String MAX_DIST_WHILE[]={
				"WHILE_HIPD1_2_2",
				"WHILE_HPD1_4_4",
				"WHILE_HPD2_5_5",
		};
		String MAX_DIST_FLOW[]={
				"FLOW_HIPD1_2_2",
				"FLOW_HPD1_4_4",
				"FLOW_HPD2_6_6",
		};
		String MAX_DIST_LOAN[]={
				"LOAN_HIPD0_2_2",
				"LOAN_HIPD1_3_3",
				"LOAN_HPD1_6_6",
				"LOAN_HPD2_10_10",
				"LOAN_HPD3_16_16",
		};
		String Loan_Machines[] = {
				"LOAN_.*_.*_1",
				"LOAN_.*_.*_2",
				"LOAN_.*_.*_3",
				"LOAN_.*_.*_4",
				"LOAN_.*_.*_5",
				"LOAN_.*_.*_6",
				"LOAN_.*_.*_7",
				"LOAN_.*_.*_8",
				"LOAN_.*_.*_9",
				"LOAN_.*_.*_10",
				"LOAN_.*_.*_11",
				"LOAN_.*_.*_12",
				"LOAN_.*_.*_13",
				"LOAN_.*_.*_14",
				"LOAN_.*_.*_15",
				"LOAN_.*_.*_16",
		};
		String Flow_Machines[]={
				"FLOW_.*_.*_1",
				"FLOW_.*_.*_2",
				"FLOW_.*_.*_3",
				"FLOW_.*_.*_4",
				"FLOW_.*_.*_5",
				"FLOW_.*_.*_6",
		};
		String If_Machines[]={
				"IF_.*_.*_1",
				"IF_.*_.*_2",
				"IF_.*_.*_3",
				"IF_.*_.*_4",
				"IF_.*_.*_5",
				"IF_.*_.*_6",
		};
		String While_Machines[]={
				"WHILE_.*_.*_1",
				"WHILE_.*_.*_2",
				"WHILE_.*_.*_3",
				"WHILE_.*_.*_4",
				"WHILE_.*_.*_5"
		};
		String EX_Machines[]={
				"EX1_.*_1",
				"EX2_.*_1",
				"EX3_.*_1",
				"EX4_.*_2",
		};
		
	String filter(String fName){
			String pName = null;
			int index = checkMatching(fNames, fName);
//			System.out.println("matched index->"+index+" "+fNames[index]);
			if(index>=0){
				pName=fNames[index];
				if(checkMatching(CENT_REGEXs, fName)>=0)
					pName+="=Centralized";
				if (checkMatching(IPD_REGEXs, fName)>=0)
					pName+="=IPD";
				if(checkMatching(FPD_REGEXs, fName)>=0)
					pName+="=FPD";
				if(checkMatching(EXP_REGEXs, fName)>=0){
					if(pName.indexOf("EX1")>=0) return "Config-1";	
					if(pName.indexOf("EX2")>=0) return "Config-2";
					if(pName.indexOf("EX3")>=0) return "Config-3";
					if(pName.indexOf("EX4")>=0) return "Config-4";
				}
			}
			return pName;
	}

	int checkMatching(String[] reg, String name){

		for(int i=0; i<reg.length; i++){
			
			String REGEX = reg[i]+"_.*";
		    Pattern p = Pattern.compile(REGEX);
		    Matcher m = p.matcher(name); // get a matcher object

			if (m.matches()) {
//				System.out.println("checkMatching:name->"+name);
//				System.out.println("checkMatching:REXP->"+REGEX);
//				System.out.println("matched->"+m.matches());
				return i;
			}
		}
		return -1;
	}
	private String splitByRegexp(String[] reg, String name) {
		int cm = checkMatching(reg, name);
		if(cm>=0){
			String REGEX = reg[cm];
		    Pattern p = Pattern.compile(REGEX);
		    Matcher m = p.matcher(name); // get a matcher object
			if (m.matches()) {
				String[] tfName = name.split(REGEX);
				return tfName[0];
			}
		}
		return null;
	}
	
	int getNumberOfmachine(File path){
		
		for (int i=1; i<=16; i++){
			String REGEX = ".*_.*_.*_"+i+"_.*";//for while, flow, if and loan experiments
		    Pattern p = Pattern.compile(REGEX);
		    Matcher m = p.matcher(path.getName()); // get a matcher object
//		    StringBuffer sb = new StringBuffer();
		    if (m.matches()) return i;
		}
    	return 0;
	}
	String getType(File f){
		
		if (f.getName().indexOf("LOAN")>=0) return "Loan";
		else if (f.getName().indexOf("IF")>=0) return "If";
		else if (f.getName().indexOf("WHILE")>=0) return "While";
		else if (f.getName().indexOf("FLOW")>=0) return "Flow";
		else if (f.getName().indexOf("EX")>=0) return "EX";
		else return "";
		
	}
	public boolean checkMaxDistMatchesfile(File file, int fType) {
		
		String[] maxDestPatterns = null;
		if (fType==IF)
			maxDestPatterns = MAX_DIST_IF;
		else if (fType==WHILE)
			maxDestPatterns = MAX_DIST_WHILE;
		else if (fType==FLOW)
			maxDestPatterns = MAX_DIST_FLOW;
		else if (fType==LOAN)
			maxDestPatterns = MAX_DIST_LOAN;
		
		if (checkMatching(maxDestPatterns,file.getName())>=0)
			return true;
		else return false;
	}
	
	public boolean checkMatchesTYPEandNOM(File file, int fType, int nom) {
		
		String[] patterns = null;
		if (fType== IF){
			patterns = new String[1]; 
		patterns[0] = If_Machines[nom-1];
		}else if (fType==NameFilter.WHILE){
			patterns = new String[1];
			patterns[0] = While_Machines[nom-1];
		}else if (fType==FLOW){
			patterns = new String[1];
			patterns[0] = Flow_Machines[nom-1];
		}else if (fType==LOAN){
			patterns = new String[1];
			patterns[0] = Loan_Machines[nom-1];
		}else if (fType==EX){
//			patterns = new String[1];
			patterns = EXP_REGEXs;
		}
		
		if (checkMatching(patterns,file.getName())>=0)
			return true;
		else return false;
	}
	
	public boolean checkMatchesTYPE(File file, int fType) {
		
		String[] patterns = null;
		if (fType==IF){
			patterns = new String[1]; 
			patterns[0] = "IF";
		}else if (fType==WHILE){
			patterns = new String[1];
			patterns[0] = "WHILE";
		}else if (fType==FLOW){
			patterns = new String[1];
			patterns[0] = "FLOW";
		}else if (fType==LOAN){
			patterns = new String[1];
			patterns[0] = "LOAN";
		}else if (fType==LOAN){
		patterns = new String[1];
		patterns[0] = "EX";
	}
		if (checkMatching(patterns,file.getName())>=0)
			return true;
		else return false;
	}
	
	String[] sortArray(String[] children){
		
		if (children==null) return null;
		for(int i =0; i<children.length;i++)
			for(int j =i; j<children.length;j++)
				if(children[i].compareTo(children[j])>0){
					String tmp=children[i];
					children[i]=children[j];
					children[j]=tmp;
				}
		return children;
	}
}