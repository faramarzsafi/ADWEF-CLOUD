package common.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

import org.apache.axis.utils.FieldPropertyDescriptor;

public class VariableRate_Improvement {

	public static final int IF = 1;
	public static final int WHILE= 2;
	public static final int FLOW = 3;
	public static final int LOAN = 4;
	
	String colName;
	int MaxRows;
	int row = 0;
	int FILE_TYPE;
	String FPD_REG_EXP = "";
	BufferedWriter out;
	int responseTime_throughput_mode=0;//0 = responsetime, 1 = throughput
	
//	double totalImprove = 0;
//	int noOfImprove = 0;
//	double avgImprove = 0;
//		
	LinkedList matchList = new LinkedList();
	NameFilter nf = new NameFilter();
		
	public static void main(String[] args) {
		
		try {

			
			int FILE_TYPE=NameFilter.LOAN;
			String src = 
			"D:/Backups/PhD_Experiments_Final_Data/Expetiment_Linux_PU_30%_70%_finished_7June_2010_Final_Data_Files/Mode=2/Data/Mode=2-Server=2";

			String FPD_REG_EXP = "";
			String TYPE=null;
			if (FILE_TYPE==NameFilter.IF){TYPE ="IF"; FPD_REG_EXP="IF_HPD2_6";}
			else if (FILE_TYPE==NameFilter.FLOW){TYPE ="FLOW"; FPD_REG_EXP="FLOW_HPD2_6";}
			else if (FILE_TYPE==NameFilter.WHILE){TYPE ="WHILE";FPD_REG_EXP="WHILE_HPD2_5";}
			else if (FILE_TYPE==NameFilter.LOAN){TYPE ="LOAN"; FPD_REG_EXP="LOAN_HPD3_16";}
 
			String improveDisPath =  
			"D:/PhD_Experiments_Final_Results/Expetiment_Linux_PU_30%_70%_finished_7June_2010_Final_Result/Mode=2/Analysis/Mode=2-Server=2/Analysis" +
			"/"+TYPE+"/"+TYPE+"_m2s2_improvement.csv";
	
			VariableRate_Improvement imp = new VariableRate_Improvement(new File(improveDisPath).getPath());
			
			imp.FILE_TYPE = FILE_TYPE;//determines which experiment type must be analyzed
			imp.FPD_REG_EXP = FPD_REG_EXP;
			imp.MaxRows = 14;
			
			imp.responseTime_throughput_mode = 0;
			imp.out.write("\n\n\n\n----------------------Response Time ----------------------\n\n\n\n");
			imp.colName = "responseTime";
			imp.printOutput(new File(src));
	
			imp.responseTime_throughput_mode = 1;
			imp.out.write("\n\n\n\n------------------------Throughput -----------------------\n\n\n\n");
			imp.colName = "throughputPerUnit";
			imp.printOutput(new File(src));
			
			imp.out.flush();
			System.out.println("Improvement Analysing Finished....");
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public VariableRate_Improvement(String resultFileName) throws IOException {
		new File(new File(resultFileName).getParent()).mkdirs();
		new File(resultFileName).createNewFile();
		out = new BufferedWriter(new FileWriter(resultFileName));
	}
	
	private void printOutput(File dirs) throws IOException {

		NameFilter nf = new NameFilter();
		matchList.clear();
		getMatchFileList(dirs);
		System.out.println("matchlist size->"+matchList.size());
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
	    }
	    else if(nf.checkMatchesTYPE(dir, FILE_TYPE)){
//	    	System.out.println("matched File->"+dir.getName());
	    	matchList.add(dir.getName());
	    }
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

		if (!nf.checkMatchesTYPE(dir, FILE_TYPE))return;
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
//		System.out.println("fpd Path: "+dir.getParent());
//		System.out.println("fpd Name: "+fpdName);

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
				
				if ((br.equals(null))||(br==null)) System.out.println("br==null");
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
							System.out.println("VisitedFile->"+dir.getName());
							System.out.println("improve= "+improve);
						}
					}else if (responseTime_throughput_mode==1){
						improve = (othersValue-fpdValue);
						if (improve < 0){
							System.out.println("-----------Throughput--------");
							System.out.println("fpdValue ->"+fpdValue);
							System.out.println("otherValues ->"+othersValue);
							System.out.println("VisitedFile->"+dir.getName());
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

//	private void processFile___(File dir) {
//		
//		if (dir.getName().indexOf("result")>=0) return;
//		NameFilter nf = new NameFilter();
//		
//		String fpdName = "";
//		for (int j=0;j<matchList.size();j++){
//			String reg[]=new String[1];
//			reg[0]=FPD_REG_EXP;
//			if (nf.checkMatching(reg, (String)matchList.get(j))>=0)
//				fpdName = (String)(matchList.get(j));
//		}
////		System.out.println("fpd File Name: "+fpdName);
//
//		if (!nf.checkMatchesTYPE(dir, FILE_TYPE))return;
//		if (fpdName.equalsIgnoreCase(dir.getName()))return;
//		
//		try{
////			System.out.println("visited file: "+dir.getName());
//			int col = getColNumber(dir, colName);
//			if (col<0) throw new Exception(colName+" does not exist");
//			BufferedReader br = new BufferedReader( new FileReader(dir.getPath()));
//			BufferedReader fpdbr = new BufferedReader( new FileReader(dir.getParent()+"/"+fpdName));
//			String strLine = "";
//			String fpdStrLine="";
//			StringTokenizer st = null;
//			StringTokenizer fpdst = null;
//			int lineNumber = 0, tokenNumber = 0;
//			//read comma separated file line by line
//			while( (strLine = br.readLine()) != null){
//				fpdStrLine = fpdbr.readLine();
//				if (lineNumber == row){
//					
//					st = new StringTokenizer(strLine, ",");
//					fpdst = new StringTokenizer(fpdStrLine, ",");
//					String nToken;
//					String fpdnToken;
//					while(st.hasMoreTokens()){
//						nToken = st.nextToken();
//						fpdnToken = fpdst.nextToken();
//						boolean found = false;
//						if (tokenNumber == col){
//							found = true;
////							out.write( ","+nToken);
//							Double fpdDbl = new Double(fpdnToken);
//							Double nTokenDbl = new Double(nToken);
////							System.out.println("fpdDbl= "+fpdDbl.doubleValue());
////							System.out.println("nTokenDbl= "+nTokenDbl.doubleValue());
//							double fpdValue = fpdDbl.doubleValue();
//							double othersValue = nTokenDbl.doubleValue();
//							double improve=0;
////							improve = (othersValue>=fpdValue)?((othersValue-fpdValue)*100)/fpdValue:((fpdValue-othersValue)*100)/othersValue;
//							if (responseTime_throughput_mode==0){
//								improve = ((fpdValue-othersValue)*100)/fpdValue;
//								if (improve < 0){
//									
//									System.out.println("fpdValue ->"+fpdValue);
//									System.out.println("otherValues ->"+othersValue);
//									System.out.println("improve= "+improve);
//								}
//							}else if (responseTime_throughput_mode==1){
////								improve = ((othersValue-fpdValue)*100)/othersValue;
//								improve = (othersValue-fpdValue);
//								if (improve < 0){
//									System.out.println("fpdValue ->"+fpdValue);
//									System.out.println("otherValues ->"+othersValue);
//									System.out.println("improve= "+improve);
//								}
//							}
////							System.out.println("improve= "+improve);
//							out.write( ","+improve);
//							break;
//						}
//						if (!found) new Exception("not found column: "+colName);
//						tokenNumber++;
//					}
//					break;
//				}
//				lineNumber++;
//			}
//		}catch(Exception e){
//			System.out.println("Exception while reading csv file: " + e);
//		}
//	}
	
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