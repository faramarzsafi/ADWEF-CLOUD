package common.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

//this is a new version of variableMessaging; albeit without needing to VariableMessageGrouping 
public class VariableMessaging_MaxDistribution {
//
//	public static final int IF = 1;
//	public static final int WHILE= 2;
//	public static final int FLOW = 3;
//	public static final int LOAN = 4;

	String colName;
	int MaxRows;
	int row = 0;
	int FILE_TYPE;
	BufferedWriter out;
//	int NumberOfMachines = 0;

	LinkedList matchList = new LinkedList();
	NameFilter nf = new NameFilter();

	public static void main(String[] args) {
		File[] dirs = new File[5];

		try {
			for (int count=1;count<=16;count++){

				String src = "D:/Backups/PhD_Experiments_Final_Data/VariableMessaging_finished_13_July_2010_Corrected_23_Aug_Again_21_Sep_2010_Data/Type-2";
				int FILE_TYPE=NameFilter.IF;
//				int NumberOfMachines = count;
				String TYPE=null;
				if (FILE_TYPE==NameFilter.IF)TYPE ="IF";
				else if (FILE_TYPE==NameFilter.FLOW)TYPE ="FLOW";
				else if (FILE_TYPE==NameFilter.WHILE)TYPE ="WHILE";
				else if (FILE_TYPE==NameFilter.LOAN)TYPE ="LOAN";

				String destPath =
				"D:/PhD_Experiments_Final_Results/VariableMessaging_finished_13_July_2010_Corrected_23_Aug_Again_21_Sep_2010_Result/Type-2"+
				"/Analysis"+"/"+TYPE+"/"+TYPE+"_MaxDistribution"+".csv";

				VariableMessaging_MaxDistribution md = new VariableMessaging_MaxDistribution(new File(destPath).getPath());

				md.FILE_TYPE=FILE_TYPE;//determined which experiment type must be analyzed
//				md.NumberOfMachines = NumberOfMachines;
				md.MaxRows = 4;

				dirs[0] = new File(src+"/050Kb");
				dirs[1] = new File(src+"/100Kb");
				dirs[2] = new File(src+"/150Kb");
				dirs[3] = new File(src+"/200Kb");
				dirs[4] = new File(src+"/250Kb");

				md.out.write("\n\n\n\n----------------------Response Time for Speed 50----------------------\n\n\n\n");
				md.row = 1;
				md.colName = "responseTime";
				md.printOutput(dirs, "0");

				md.out.write("\n\n\n\n------------------------Throughput for Speed 50-----------------------\n\n\n\n");
				md.row = 1;
				md.colName = "throughputPerUnit";
				md.printOutput(dirs, "100");

				md.out.write("\n\n\n\n---------------------Response Time for Speed 500---------------------\n\n\n\n");
				md.row = 2;
				md.colName = "responseTime";
				md.printOutput(dirs, "0");

				md.out.write("\n\n\n\n-----------------------Throughput for Speed 500----------------------\n\n\n\n");
				md.row = 2;
				md.colName = "throughputPerUnit";
				md.printOutput(dirs, "100");

				md.out.close();
			}		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public VariableMessaging_MaxDistribution(String resultFileName) throws IOException {
		new File(new File(resultFileName).getParent()).mkdirs();
		new File(resultFileName).createNewFile();
		out = new BufferedWriter(new FileWriter(resultFileName));
	}

	void printOutput(File[] dirs, String tr) throws IOException {
		
		int step=50;		
		matchList.clear();
		getMatchFileList(dirs[0]);
		System.out.println("matchlist size->"+matchList.size());
		out.write(",");

		for (int j=0;j<matchList.size();j++) {
			System.out.println("matchlist->"+matchList.get(j));
			out.write(","+nf.filter((String) matchList.get(j)));
		}
		out.write("\n");
		for (int j=0;j<matchList.size();j++) {
			out.write((j==0)?",0":","+tr);
		}
		out.write(","+tr+"\n");
		for (int fIndex=0;fIndex<dirs.length;fIndex++){

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
		} else if(nf.checkMaxDistMatchesfile(dir, FILE_TYPE)){
			System.out.println("matched File->"+dir.getName());
			matchList.add(dir.getName());
		}
	}

	private void processFile(File dir) {
		if (dir.getName().indexOf("result")>=0) return;
		NameFilter nf = new NameFilter();
		if (!nf.checkMaxDistMatchesfile(dir, FILE_TYPE)) return;
		try{
			//			System.out.println("visited file: "+dir.getName());
			int col = getColNumber(dir, colName);
			if (col<0) throw new Exception(colName+" does not exist");
			BufferedReader br = new BufferedReader( new FileReader(dir.getPath()));
			String strLine = "";
			StringTokenizer st = null;
			int lineNumber = 0, tokenNumber = 0;
			//read comma separated file line by line
			while( (strLine = br.readLine()) != null){
				if (lineNumber == row){
					st = new StringTokenizer(strLine, ",");
					String nToken;
					while(st.hasMoreTokens()){
						nToken = st.nextToken();
						if (tokenNumber == col){
							out.write( ","+nToken);
							break;
						}
						tokenNumber++;
					}
					break;
				}
				lineNumber++;
			}
		}catch(Exception e){
			System.out.println("Exception while reading csv file: " + e);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
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