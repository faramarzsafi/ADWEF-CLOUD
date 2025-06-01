package common.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class VariableRate_MaximumDistribution {

//	public static final int IF = 1;
//	public static final int WHILE= 2;
//	public static final int FLOW = 3;
//	public static final int LOAN = 4;
	
	String colName;
	int MaxRows;
	int row = 0;
	int FILE_TYPE;
	BufferedWriter out;
	
	LinkedList matchList = new LinkedList();
	NameFilter nf = new NameFilter();
		
	public static void main(String[] args) {
		
		try {
			int FILE_TYPE=NameFilter.FLOW;
			
			String src = "D:/Backups/PhD_Experiments_Final_Data/Expetiment_Linux_PU_30%_70%_finished_7June_2010_Final_Data_Files/Mode=1/Data";
//			String src = "C:/Safi/Experiments/Expetiment_Linux_PU_30%_70%_finished_7June_2010_Final/Mode=2/Data";
			
			String TYPE=null;
			if (FILE_TYPE==NameFilter.IF)TYPE ="IF";
			else if (FILE_TYPE==NameFilter.FLOW)TYPE ="FLOW";
			else if (FILE_TYPE==NameFilter.WHILE)TYPE ="WHILE";
			else if (FILE_TYPE==NameFilter.LOAN)TYPE ="LOAN";
//			String maxDisPath = 
//				"C:/Safi/Experiments/Expetiment_Linux_PU_30%_70%_finished_7June_2010_Final/Mode=1/Analysis/MAXIMUM_DISTRIBUTION/"
//			+TYPE+"_max_distribution.csv";
			
			String maxDisPath = 
				"D:/PhD_Experiments_Final_Results/Expetiment_Linux_PU_30%_70%_finished_7June_2010_Final_Result/Mode=1/Analysis/MAXIMUM_DISTRIBUTION/"+
				TYPE+"_max_distribution.csv";
	
			VariableRate_MaximumDistribution md = new VariableRate_MaximumDistribution(new File(maxDisPath).getPath());
			
			md.FILE_TYPE=FILE_TYPE;//determined which experiment type must be analyzed
			md.MaxRows = 14;
			
			md.out.write("\n\n\n\n----------------------Response Time ----------------------\n\n\n\n");
			md.colName = "responseTime";
			md.printOutput(new File(src));
	
			md.out.write("\n\n\n\n------------------------Throughput -----------------------\n\n\n\n");
			md.colName = "throughputPerUnit";
			md.printOutput(new File(src));
			
			md.out.flush();
			System.out.println("Analysing Maximum Distribution Files Finished....");
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public VariableRate_MaximumDistribution(String resultFileName) throws IOException {
		new File(new File(resultFileName).getParent()).mkdirs();
		new File(resultFileName).createNewFile();
		out = new BufferedWriter(new FileWriter(resultFileName));
	}
	
	private void printOutput(File dirs) throws IOException {

		NameFilter nf = new NameFilter();
		matchList.clear();
		getMatchFileList(dirs);
//		System.out.println("matchlist size->"+matchList.size());
		out.write(",");
		for (int j=0;j<matchList.size();j++) {
//			System.out.println("matchlist->"+matchList.get(j));
			out.write(","+nf.filter((String) matchList.get(j)));
		}
		out.write("\n");
		int step=1;
		for (int i=1; i<MaxRows; i++){
			row = i;
			out.write(","+step);
			visitAllFiles(dirs);
			out.write("\n");
			step = ((i==1)?(50):(step+=50));
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
		if (!nf.checkMaxDistMatchesfile(dir, FILE_TYPE))return;
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