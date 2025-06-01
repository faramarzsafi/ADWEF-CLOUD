package common.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

//this is a new version of variableMessaging; albeit without needing to VariableMessageGrouping 
public class VariableMessageSize_Improvement {

	String colName;
	int MaxRows;
	int row = 0;
	int FILE_TYPE;
	BufferedWriter out;
	int NumberOfMachines = 0;
	int responseTime_throughput_mode=0;//0 = responsetime, 1 = throughput
	String FPD_REG_EXP = "";

	LinkedList matchList = new LinkedList();
	NameFilter nf = new NameFilter();

	public static void main(String[] args) {

		try {
			int max_nof_machines =0;
			int FILE_TYPE=NameFilter.LOAN;
			String FPD_REG_EXP = "";
			String TYPE=null;
			if (FILE_TYPE==NameFilter.IF){TYPE ="IF"; FPD_REG_EXP="IF_HPD2_6";max_nof_machines = 5;}
			else if (FILE_TYPE==NameFilter.FLOW){TYPE ="FLOW"; FPD_REG_EXP="FLOW_HPD2_6";max_nof_machines = 6;}
			else if (FILE_TYPE==NameFilter.WHILE){TYPE ="WHILE";FPD_REG_EXP="WHILE_HPD2_5";max_nof_machines = 5;}
			else if (FILE_TYPE==NameFilter.LOAN){TYPE ="LOAN"; FPD_REG_EXP="LOAN_HPD3_16";max_nof_machines = 10;}
			
			for (int count=1;count<= max_nof_machines;count++){

				String src = "D:/Backups/PhD_Experiments_Final_Data/VariableMessaging_finished_13_July_2010_Corrected_23_Aug_Again_21_Sep_2010_Data/Type-2";

				int NumberOfMachines = count;
				String destPath =
					"D:/PhD_Experiments_Final_Results/VariableMessaging_finished_13_July_2010_Corrected_23_Aug_Again_21_Sep_2010_Result/Type-2/Analysis"+
					"/"+TYPE+"/"+TYPE+"_"+"m2s"+NumberOfMachines+"_improvement"+".csv";

				VariableMessageSize_Improvement md = new VariableMessageSize_Improvement(new File(destPath).getPath());

				md.FILE_TYPE=FILE_TYPE;//determined which experiment type must be analyzed
				md.NumberOfMachines = NumberOfMachines;
				md.FPD_REG_EXP =FPD_REG_EXP+"_"+md.NumberOfMachines;
				md.MaxRows = 4;

				File[] dirs = {

					new File(src+"/050Kb"),
					new File(src+"/100Kb"),
					new File(src+"/150Kb"),
					new File(src+"/200Kb"),
					new File(src+"/250Kb")
				};
				
				md.responseTime_throughput_mode = 0;
				md.out.write("\n\n\n\n----------------------Response Time for Speed 50----------------------\n\n\n\n");
				md.row = 1;
				md.colName = "responseTime";
				md.printOutput(dirs, "0");

				md.responseTime_throughput_mode = 1;
				md.out.write("\n\n\n\n------------------------Throughput for Speed 50-----------------------\n\n\n\n");
				md.row = 1;
				md.colName = "throughputPerUnit";
				md.printOutput(dirs, "100");

				md.responseTime_throughput_mode = 0;
				md.out.write("\n\n\n\n---------------------Response Time for Speed 500---------------------\n\n\n\n");
				md.row = 2;
				md.colName = "responseTime";
				md.printOutput(dirs, "0");
				
				md.responseTime_throughput_mode = 1;
				md.out.write("\n\n\n\n-----------------------Throughput for Speed 500----------------------\n\n\n\n");
				md.row = 2;
				md.colName = "throughputPerUnit";
				md.printOutput(dirs, "100");
				md.out.write("\n\n\n");
				md.out.close();
			}		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public VariableMessageSize_Improvement(String resultFileName) throws IOException {
		new File(new File(resultFileName).getParent()).mkdirs();
		new File(resultFileName).createNewFile();
		out = new BufferedWriter(new FileWriter(resultFileName));
	}

	void printOutput(File[] dirs, String tr) throws IOException {
		System.out.println("Number of machines....>"+NumberOfMachines);
		int step=50;		
		matchList.clear();
		getMatchFileList(dirs[0]);
//		System.out.println("matchlist size->"+matchList.size());
		out.write(",");
		for (int j=0;j<matchList.size();j++) {
//			System.out.println("matchlist->"+matchList.get(j));
			out.write(","+nf.filter((String) matchList.get(j)));
		}
		out.write("\n");
//		for (int j=0;j<matchList.size();j++) {
//			out.write((j==0)?",0":","+tr);
//		}
//		out.write(","+tr+"\n");
		for (int fIndex=0;fIndex<dirs.length;fIndex++){
			matchList.clear();
			getMatchFileList(dirs[fIndex]);
			out.write(","+step);
			visitAllFiles(dirs[fIndex]);
			out.write("\n");
			step+=50;
		}
	}

	private void getMatchFileList(File dir) {

		if (dir.isDirectory()) {
			String[] children = sortArray(dir.list());
			for (int i=0; i<children.length; i++) {
				getMatchFileList(new File(dir, children[i]));
			}
		} else if(nf.checkMatchesTYPEandNOM(dir, FILE_TYPE,NumberOfMachines)){
//			System.out.println("matched File->"+dir.getName());
			matchList.add(dir.getName());
		}
	}
	
	// Process only files under dir
	public  void visitAllFiles(File dir) {

		if (dir.isDirectory()) {
			String[] children = sortArray(dir.list());
			for (int i=0; i<children.length; i++) {
				visitAllFiles(new File(dir, children[i]));
			}
		} else processFile(dir);
	}

	private void processFile(File dir) {
		
		if (dir.getName().indexOf("result")>=0) return;
		NameFilter nf = new NameFilter();
		
		String fpdName = "";
		for (int j=0;j<matchList.size();j++){
			String reg[]=new String[1];
			reg[0]=FPD_REG_EXP;
//			System.out.println("FPD_REG_EXP-> "+FPD_REG_EXP);
			if (nf.checkMatching(reg, (String)matchList.get(j))>=0)
				fpdName = (String)(matchList.get(j));
		}

		if (!nf.checkMatchesTYPEandNOM(dir, FILE_TYPE, NumberOfMachines))return;
		if (fpdName.equalsIgnoreCase(dir.getName()))return;
		if ((fpdName.equals(null))||(fpdName.equals("")))return;
		
//		System.out.println("visited file: "+dir.getName());

		BufferedReader br = null;
		int col = 0;
		try {
			col = getColNumber(dir, colName);
			if (col<0)	throw new Exception(colName+" does not exist in Visited File");
			br = new BufferedReader( new FileReader(dir.getPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("fpd Path: "+dir.getParent());
		System.out.println("fpd Name: "+fpdName);

		BufferedReader fpdbr = null;
		int fpdCol=0;
		try {
			fpdCol = getColNumber(new File(dir.getParent()+"/"+fpdName), colName);
			if (fpdCol<0)	throw new Exception(colName+" does not exist in FPD");
			fpdbr = new BufferedReader( new FileReader(dir.getParent()+"/"+fpdName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String strLine = "";
		String fpdStrLine="";
		StringTokenizer st = null;
		StringTokenizer fpdst = null;
		int lineNumber = 0;
		try{
			//read comma separated file line by line
			while( (strLine = br.readLine()) != null){
				
				if ((br.equals(null))||(br==null)) System.out.println("inside while:br==null");
				fpdStrLine = fpdbr.readLine();
//				System.out.println("fpdStrLine: "+fpdStrLine);
				if (lineNumber == row){

					st = new StringTokenizer(strLine, ",");
					fpdst = new StringTokenizer(fpdStrLine, ",");
					String nToken;
					String fpdnToken;
					int tokenNumber = 0;
					Double nTokenDbl = null;
					boolean found = false;
					while(st.hasMoreTokens()){
						nToken = st.nextToken();
						if (tokenNumber == col){
							found = true;
							nTokenDbl = new Double(nToken);
						}
						if (!found) new Exception("not found column: "+colName);
						tokenNumber++;
					}
					tokenNumber = 0;
					Double fpdDbl = null;
					found = false;
					while(fpdst.hasMoreTokens()){
						fpdnToken = fpdst.nextToken();
						if (tokenNumber == fpdCol){
							found = true;
							fpdDbl = new Double(fpdnToken);
						}
						if (!found) new Exception("not found column: "+colName);
						tokenNumber++;
					}

//					System.out.println("fpdDbl= "+fpdDbl.doubleValue());
//					System.out.println("nTokenDbl= "+nTokenDbl.doubleValue());
					double fpdValue = fpdDbl.doubleValue();
					double othersValue = nTokenDbl.doubleValue();
					double improve=0;
					//improve = (othersValue>=fpdValue)?((othersValue-fpdValue)*100)/fpdValue:((fpdValue-othersValue)*100)/othersValue;
					if (responseTime_throughput_mode==0){
						improve = ((fpdValue-othersValue)*100)/fpdValue;
						if (improve < 0){
							System.out.println("-----------Response Time--------");
							System.out.println("fpdValue ->"+fpdValue);
							System.out.println("otherValues ->"+othersValue);
							System.out.println("improve= "+improve);
						}
					}else if (responseTime_throughput_mode==1){
						improve = (othersValue-fpdValue);
						if (improve < 0){
							System.out.println("-----------Throughput--------");
							System.out.println("fpdValue ->"+fpdValue);
							System.out.println("otherValues ->"+othersValue);
							System.out.println("improve= "+improve);
						}
					}
//					System.out.println("improve= "+improve);
					try {
						out.write( ","+improve);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
				lineNumber++;
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("\nException while reading csv file: " + e);
		}
	}

	int getColNumber(File dir, String colName){
		try {			
			BufferedReader br = new BufferedReader( new FileReader(dir.getPath()));
			String strLine = "";
			StringTokenizer st = null;
			int lineNumber = 0, tokenNumber = 0;
			//read comma separated file line by line
			strLine = br.readLine();
			if(strLine!= null){
				st = new StringTokenizer(strLine, ",");
				while(st.hasMoreTokens()){

					String nToken;
					nToken = st.nextToken();
					if (nToken.equalsIgnoreCase(colName)){
						return tokenNumber;
					}
					tokenNumber++;
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}


	private String[] sortArray(String[] children){

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