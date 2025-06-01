package common.tools;

	import java.io.BufferedReader;
	import java.io.BufferedWriter;
	import java.io.File;
	import java.io.FileReader;
	import java.io.FileWriter;
	import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

	//this file is for analyzing variable messaging files...
	public class ResponseTimeThroughputAnalyzer {

		int row = 0;
		String  colName = "";
		BufferedWriter out;
		private int MaxRows;

		public ResponseTimeThroughputAnalyzer(String resultFileName) throws IOException{
			out = new BufferedWriter(new FileWriter(resultFileName));
		}

		public static void main(String[] args) throws IOException {
			
			int numberOfMachines = 2;
			for(int counter=1; counter<=numberOfMachines; counter++){
				String base = 
				"D:/Backups/PhD_Experiments_Final_Data/Expetiment_Linux_PU_30%_70%_finished_7June_2010_Final_Data_Files/Mode=2/Data/Mode=2-Server=2";
				String rfName = 
				"D:/PhD_Experiments_Final_Results/Expetiment_Linux_PU_30%_70%_finished_7June_2010_Final_Result/Mode=2/Analysis/Mode=2-Server=2/Analysis"+
				"/Loan"+"/Loan_m2s"+numberOfMachines+"_min_distribution.csv";
//				String base = "C:/Safi/Experiment-VariableMessaging_finished_13_July_2010/Type-1/Analysis/"+"While/"+counter+"machine";
//				String base = "C:/Safi/Experiments/BoundaryConditionExperiments_finished_17_Aug_2010/n=1";

//				String base = "C:/Safi/Experiments/Expetiment_Linux_PU_30%_70%_finished_7June_2010_Final/Mode=2/Mode=2-Server="+counter+"/Analysis/If";
//				String rfName = (new File(base)).getParent()+"/min_distribution.csv";
				
//				String base = "C:/Safi/Experiments/BoundaryConditionExperiments_finished_17_Aug_2010/ResponseTime-throughput/n=10";
//				String rfName = (new File(base)).getParent()+"/min_distribution.csv";
				
				ResponseTimeThroughputAnalyzer csv = new ResponseTimeThroughputAnalyzer(rfName);
				
				File dirs = new File(base);

				csv.MaxRows=14;
				
				csv.out.write("\n\n\n\n----------------------Response Time ----------------------\n\n\n\n");
				csv.colName = "responseTime";
				csv.printOutput(dirs, "0");
				
				csv.out.write("\n\n\n\n------------------------Throughput -----------------------\n\n\n\n");
				csv.colName = "throughputPerUnit";
				csv.printOutput(dirs, "100");
				csv.out.flush();
			}
				System.out.println("Responsetime, throughput analyzing finished...");
			}
	
		private void printOutput(File dirs, String tr) throws IOException {
			NameFilter nf = new NameFilter();
			String[] fList = sortArray(dirs.list());
			out.write( ",");
			for (int j=0;j<fList.length;j++) out.write(","+nf.filter(fList[j]));
//			for (int j=0;j<=fList.length;j++) out.write((j==0)?("0,"):(tr+","));
			out.write("\n");
//			out.write( ",");
			int step=1;
			for (int i=1; i<MaxRows; i++){
				row = i;
				out.write(","+step);
//				out.write(","+((i==0)?(""):(row))+",");
				visitAllFiles(dirs);
				out.write("\n");
				step = ((i==1)?(50):(step+=50));
//				out.write(step);
			}
		}
		
		private void processFile(File dir) {
			if (dir.getName().indexOf("result")>=0) return;
			try{
//				System.out.println("visited file: "+dir.getName());
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

