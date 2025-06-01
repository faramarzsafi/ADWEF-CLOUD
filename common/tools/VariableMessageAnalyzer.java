package common.tools;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

//this file is for analyzing variable messaging files...
public class VariableMessageAnalyzer{

		int row = 0;
		int col = 0;
		BufferedWriter out;

	public VariableMessageAnalyzer(String resultFileName) throws IOException{
		out = new BufferedWriter(new FileWriter(resultFileName));
	}

	public static void main(String[] args) throws IOException {
		
		
		int numberOfMachines = 1;
		for(int counter=1; counter<=numberOfMachines; counter++){
			
			String base = "C:/Safi/Experiment-VariableMessaging_finished_13_July_2010/Type-1/Analysis/"+"While/"+counter+"machine";
//			String base = "C:/Safi/Experiments/BoundaryConditionExperiments_finished_17_Aug_2010/n=10";
			String rfName = base+"/result.csv";
			VariableMessageAnalyzer csv = new VariableMessageAnalyzer(rfName);
			
			File[] dirs = {
			new File(base+"/050Kb"),
			new File(base+"/100Kb"),
			new File(base+"/150Kb"),
			new File(base+"/200Kb"),
			new File(base+"/250Kb")
			};
			csv.out.write("\n\n\n\n----------------------Response Time for Speed 50----------------------\n\n\n\n");
			csv.row = 1;
			csv.col = 5;
			csv.printOutput(dirs, "0");
			
			csv.out.write("\n\n\n\n------------------------Throughput for Speed 50-----------------------\n\n\n\n");
			csv.row = 1;
			csv.col = 6;
			csv.printOutput(dirs, "100");
			
			csv.out.write("\n\n\n\n---------------------Response Time for Speed 500---------------------\n\n\n\n");
			csv.row = 2;
			csv.col = 5;
			csv.printOutput(dirs, "0");
	
			csv.out.write("\n\n\n\n-----------------------Throughput for Speed 500----------------------\n\n\n\n");
			csv.row = 2;
			csv.col = 6;
			csv.printOutput(dirs, "100");
			
			csv.out.close();
			System.out.println("Result File Created -> "+rfName);
		}
	}
	
	void printOutput(File[] dirs, String tr) throws IOException {
		NameFilter nf = new NameFilter();
		String[] fList = sortArray(dirs[0].list());
		for (int j=0;j<fList.length;j++) out.write(","+nf.filter(fList[j]));
		out.write("\n");
		for (int j=0;j<=fList.length;j++) out.write((j==0)?("0,"):(tr+","));
		out.write("\n");
		for(int i=0; i < dirs.length; i++){
			String dirName = dirs[i].getName();
			out.write(dirName.substring(0, dirName.indexOf("Kb"))+",");
			visitAllFiles(dirs[i]);
			out.write("\n");
		}
	}
	
	private void processFile(File dir) {
		try{
//			System.out.println("visited file: "+dir.getPath());
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
							out.write( nToken+",");
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