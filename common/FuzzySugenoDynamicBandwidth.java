package common;

import java.io.File;
import java.util.Properties;

import common.tools.XYChartExample;

import nrc.fuzzy.FuzzyRule;
import nrc.fuzzy.FuzzySet;
import nrc.fuzzy.FuzzyValue;
import nrc.fuzzy.FuzzyValueVector;
import nrc.fuzzy.FuzzyVariable;
import nrc.fuzzy.IncompatibleFuzzyValuesException;
import nrc.fuzzy.IncompatibleRuleInputsException;
import nrc.fuzzy.InvalidDefuzzifyException;
import nrc.fuzzy.InvalidLinguisticExpressionException;
import nrc.fuzzy.InvalidUODRangeException;
import nrc.fuzzy.LeftLinearFuzzySet;
import nrc.fuzzy.RightLinearFuzzySet;
import nrc.fuzzy.SingletonFuzzySet;
import nrc.fuzzy.TriangleFuzzySet;
import nrc.fuzzy.XValueOutsideUODException;
import nrc.fuzzy.XValuesOutOfOrderException;

public class FuzzySugenoDynamicBandwidth {

	 FuzzyVariable bandwidth;
	
	public FuzzySugenoDynamicBandwidth(){
		
		System.out.println("Fuzzy Class started....");
	}

	public FuzzyVariable segmentedBandwidth(ProcessRecord pr[], int pl) throws Exception{
		
		double min = 0.00; double max = 100.00;
		bandwidth = new FuzzyVariable("bandwidth", min-0.1, max+0.1, "");
//		System.out.println(bandwidth);
		
		int maxFragment = maximumNof(pr, pl);

		double priorX = min;
		double posteriorX = min;
//		for (int i=0; i<pr.length; i++){
		for (int i=0; i<pl; i++){
			if (pr[i].getNof() ==0) throw new Exception("number of fragments cannot be zero");
			double xi = (pr[i].getNof()*100)/maxFragment;
			
//			if(i==0) {bandwidth.addTerm("S"+i, new RightLinearFuzzySet(xi/2, xi )); priorX = min; posteriorX=xi;}
			if(i==0) {bandwidth.addTerm("S"+i, new RightLinearFuzzySet(min, xi )); priorX = min; posteriorX=xi;}
//			else if(i+1==pl) bandwidth.addTerm("S"+i, new LeftLinearFuzzySet(priorX, xi/2));
			else if(i+1==pl) bandwidth.addTerm("S"+i, new LeftLinearFuzzySet(priorX, max));
			else	{bandwidth.addTerm("S"+i, new TriangleFuzzySet( priorX, xi/2, xi )); priorX = posteriorX; posteriorX = xi;}
		}
		
//		System.out.print("bandwidth fuzzy variable is:"+ bandwidth);
		return bandwidth;
	}
	
	public int maximumNof(ProcessRecord[] pr, int pl) {
		int maxFragment = 0;
//		for (int i=0; i<pr.length; i++){
		for (int i=0; i<pl; i++){
			if (maxFragment<pr[i].getNof())
				maxFragment = pr[i].getNof();
		}
		return maxFragment;
	}

	public int fragmentProportionality(ProcessRecord[] fs,int nom ){
		int distance = nom;
		int fp = 0;
		for (int i = 0; i<fs.length; i++)
			if (Math.abs((nom - fs[i].getNof()))<distance){
				distance =  Math.abs(nom - fs[i].getNof());
				fp = fs[i].getNof();
			}
		return fp;
	}
	
	public int proportionalLevel(ProcessRecord[] fs, int fp) throws Exception {
		
		for (int i=0; i< fs.length; i++)
			if (fs[i].nof == fp) return i;
		throw new Exception("proportionalLevel: fp = "+fp+" is out of range of fragment set array....");
	}
	
	public int fuzzyGranularity(ProcessRecord[] fsa, double bw, int pl) throws Exception{
		
		if (pl<0) throw new Exception("fpl cannot be less than zero");
		if (bw<0) throw new Exception("bwl cannot be less than zero");
		
		int fuzzyGranularity = 0; 
		try{

			bandwidth = segmentedBandwidth( fsa, pl+1);
//			System.out.println("bw is: "+bw);
		    FuzzyValue bwVal =  new FuzzyValue(bandwidth, new SingletonFuzzySet(bw));
		    FuzzyVariable z = new FuzzyVariable("z", 0.0, maximumNof(fsa, pl+1) , "");
//		    System.out.println("fuzzyGranularity: z = "+z);
		    FuzzyRule fr[] = new FuzzyRule[pl+1];
//		    System.out.println("fuzzyGranularity: fr length = "+fr.length);
		     
		    for (int i=0; i<fr.length; i++){
		    	 
		    	 fr[i]= new FuzzyRule();

		    	 fr[i].removeAllAntecedents();
		    	 fr[i].addAntecedent(new FuzzyValue(bandwidth,"S"+i));
		    	 
		    	 fr[i].removeAllConclusions();
		    	 fr[i].addConclusion(new FuzzyValue(z, new SingletonFuzzySet(fsa[i].getNof())));
		    	 
		         fr[i].removeAllInputs();
	             fr[i].addInput(bwVal);
		     }
		     
		     FuzzyValueVector zfvv [] = new FuzzyValueVector[pl+1];
		     FuzzyValue zFVal []= new FuzzyValue[pl+1];
		     
		     int j = 0;
		     for (int i=0; i < fr.length; i++){
		    	 if (fr[i].testRuleMatching()){ 
//		    		 System.out.println("Rule matched -> "+i);
		    		zfvv[i] = fr[i].execute();
		    	 	zFVal[j] = zfvv[i].fuzzyValueAt(0);
		    	 	j++;
		    	 }
		     }
		     
//		     System.out.println(zFVal[0].getFuzzySet());
		     for (int i=1; i<j;i++){
//		    	 System.out.println(zFVal[i].getFuzzySet());
		    	 zFVal[0].fuzzySum(zFVal[i]);
		     }
             // Step 6 (let's look at the fuzzy set of the output and the crisp value)		     
		     double crispWeightedGranularity = zFVal[0].weightedAverageDefuzzify();
		     FuzzySet.setToStringPrecision(6);
		     //converting crispGranularity to process tree level
			 fuzzyGranularity = proportionalLevel(fsa, fragmentProportionality(fsa, (int)crispWeightedGranularity)); 
//		     System.out.println("\nWeighted Average Defuzzify value is [0 - "+(fpl)+"]: " + fuzzyGranularity);
			 
	    }catch (InvalidUODRangeException e){
			  System.out.println("FuzzySugenoDynamicBandwidth: InvalidUODRangeException "+e.getMessage()+" "+e.getCause());
			  e.printStackTrace();
	    } catch (XValuesOutOfOrderException e) {
			  System.out.println("FuzzySugenoDynamicBandwidth: XValuesOutOfOrderException "+e.getMessage()+" "+e.getCause());
			  e.printStackTrace();
		} catch (InvalidDefuzzifyException e) {
			  System.out.println("FuzzySugenoDynamicBandwidth: InvalidDefuzzifyException "+e.getMessage()+" "+e.getCause());
			e.printStackTrace();
		} catch (XValueOutsideUODException e) {
			  System.out.println("FuzzySugenoDynamicBandwidth: XValueOutsideUODException "+e.getMessage()+" "+e.getCause());
			e.printStackTrace();
		} catch (IncompatibleFuzzyValuesException e) {
			  System.out.println("FuzzySugenoDynamicBandwidth: IncompatibleFuzzyValuesException "+e.getMessage()+" "+e.getCause());
			e.printStackTrace();
		} catch (IncompatibleRuleInputsException e) {
			  System.out.println("FuzzySugenoDynamicBandwidth: IncompatibleRuleInputsException "+e.getMessage()+" "+e.getCause());
			e.printStackTrace();
		} catch (InvalidLinguisticExpressionException e) {
			  System.out.println("FuzzySugenoDynamicBandwidth: InvalidLinguisticExpressionException "+e.getMessage()+" "+e.getCause());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("FuzzySugenoDynamicBandwidth: Exception "+e.getMessage()+" "+e.getCause());
			e.printStackTrace();
		}
		  return fuzzyGranularity;
	}
	
	public static void main(String[] args)  {
		
		Properties sProp = java.lang.System.getProperties();
		String sVersion = sProp.getProperty("java.version");
//		sVersion = sVersion.substring(0, 3);
	    System.out.println("Java version is ...."+sVersion);
	    
		FuzzySugenoDynamicBandwidth sdb = new FuzzySugenoDynamicBandwidth();
		
		int nom = 16;
		double bw = 0;
		
		int fp = 0;
		int pl=0;
		int fGran = 0;
	
		ProcessRecord fsa[] = new ProcessRecord[4];
		fsa[0] = new ProcessRecord(1,"LoanTaking.hpd.l0.CentralizedWorkflow","CentralizedWorkflowAgent");
		fsa[1] = new ProcessRecord(6,"LoanTaking.hpd.l1.Sequence0","Sequence0_Agent");
		fsa[2] = new ProcessRecord(10,"LoanTaking.hpd.l2.Sequence0","Sequence0_Agent");
		fsa[3] = new ProcessRecord(16,"LoanTaking.hpd.l3.Sequence0","Sequence0_Agent");
		
		Random rbw = new Random();
		long st = System.currentTimeMillis();
//		while(System.currentTimeMillis()-st<5000){
			
//			bw= rbw.generateNumber_50_50();
			bw = 100;
			fp = sdb.fragmentProportionality(fsa, nom);
			try {
				pl = sdb.proportionalLevel(fsa, fp);
				fGran = sdb.fuzzyGranularity(fsa, bw, pl);
			} catch (Exception e) {
				System.out.println("main: exception in main: "+e.getMessage()+e.getCause());
				e.printStackTrace();
//			}
			bw = (bw==100)?99.99:bw;

//			System.out.println("nom -> "+nom);
//			System.out.println("bw -> "+bw);
//			System.out.println("fp -> "+fp);
//			System.out.println("pl-> "+pl);
//			System.out.println("fGran-> "+fGran);
		}
		
//		System.out.println("nom -> "+nom);
//		System.out.println("bw -> "+bw);
//		System.out.println("fp -> "+fp);
//		System.out.println("pl-> "+pl);
//		System.out.println("fGran-> "+fGran);
//         
		XYChartExample band = new XYChartExample("bandwidth", "bandwidth", "bandwidth", "bandwidth");
	    band.plotFuzzyValues(sdb.bandwidth);
	    band.saveChartValuesToFile(sdb.bandwidth, (new File("c:/charts")));
	    band.showChart("bandwidth");
	}
}
