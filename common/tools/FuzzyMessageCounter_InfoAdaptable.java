package common.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.regex.Pattern;

public class FuzzyMessageCounter_InfoAdaptable {

	private static String REGEX = ",";

	static	String infoPath = "C:/Safi/EXCHANGED_MESSAGES_Results/HPD_FUZZY_MESSAGING_Result_7_July_2010/EXPONENTIAL/EXPONENTIAL_4000perMinute/Info/" +
	"FUZZY_BANDWIDTH_33_13_MODE5_PU_LIN_min_4000_4000_4000_2010-06-18__10-12-04.csv";
	static String resultsPath = "C:/Safi/EXCHANGED_MESSAGES_Results/HPD_FUZZY_MESSAGING_Result_7_July_2010/EXPONENTIAL/EXPONENTIAL_4000perMinute/Results/" +
	"Messages_FUZZY_13machines_33Agent_mode2_4000PerMi_2010-07-11_03-03-00.txt.csv";

  public static void main(String[] argv) throws Exception  {
	  
	  System.out.println("Starting...");
//	  FuzzyMessageCounter_InfoAdaptable fmc = new FuzzyMessageCounter_InfoAdaptable();
	  File f1 = new File(infoPath);
	  String startDateString;
//	try {
      startDateString = selectColumn(f1, 2, 21);
      if (startDateString == null)System.out.println("startDateString is null");
	  Date startDate = new Date( new Long(startDateString.trim()).longValue());
	  File f2 = new File(resultsPath);
	  boolean found = false;
	  int index = 2;
	  while (!found){
		  String t1String = selectColumn(f2, index, 1);
		  if (t1String==null) throw new Exception("t1String is null");
		  long t1 = (new Long(t1String)).longValue();
		  if(t1 > startDate.getTime()){
			  found = true;
			  System.out.println("Start Row Number is: "+index);
		  }
		  index++;
	  }
      String endDateString = selectColumn(f1, 2, 22);
      if (startDateString == null)System.out.println("endDateString is null");
	  Date endDate = new Date( new Long(endDateString.trim()).longValue());
	  f2 = new File(resultsPath);
	  found = false;
	  index = 2;
	  while (!found){
		  String t1String = selectColumn(f2, index, 1);
		  if (t1String==null) throw new Exception("t1String is null");
		  long t1 = (new Long(t1String)).longValue();
		  if(t1 > endDate.getTime()){
			  found = true;
			  System.out.println("End Row Number is: "+(index-1));
		  }
		  index++;
	  }
  }
  
 static String selectColumn(File f, int row, int col) {
	BufferedReader in;
	try {
		in = new BufferedReader(new FileReader(f.getPath()));
		String str = null;
	    int count =1;
		while(count<=row){
			str = in.readLine();
			count++;
		}
		if (str==null) throw new Exception("selectColumn: null row referred");
		Pattern p = Pattern.compile(REGEX);
	    String[] items = p.split(str);
		for (int i = 1; i <= items.length; i++) {
			if(i==col) return items[i-1];
		}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	  
	  
}
