package common;
//Example of a simple Takagi-Sugeno-Kang rule system (order 1).
//
// In this case there are 2 rules each with 2 inputs and 1 output.
//
// The inputs are:
//	x with fuzzy values small and big
//	y with fuzzy values medium and big
//
// The output is z
//
// The 2 rules are:
//
//	Rule1: IF x is big AND y is medium THEN z = x - 3*y
//	Rule2: IF x is small AND y is big THEN  z = 4 + 2*x


import java.io.IOException;

import common.tools.XYChartExample;

import nrc.fuzzy.*;

public class FuzzyGranularitySugeno {

	FuzzyVariable bandwidth;
	
	FuzzyVariable getBWFuzzyVariable() {
		return bandwidth;
	}
	
	
	FuzzyVariable segmentedBandwidth(int fp) 
		throws InvalidFuzzyVariableNameException, InvalidUODRangeException, XValueOutsideUODException, InvalidFuzzyVariableTermNameException, XValuesOutOfOrderException{
	
		double min = 0; double max = 100;
		 FuzzyVariable bandwidth = new FuzzyVariable("bandwidth", min, max, "");
	//		if (fp==0) throw new Exception();
		
		if (fp==1) {
			bandwidth.addTerm("t"+0, new TriangleFuzzySet(min, (min+max)/2, max));
			return bandwidth;
		}
	
		double k = (max-min)/(fp-1);
	
			int i = 0;
		for (double n=min; n<=max; n=n+k){
				
			if (n==min){
				bandwidth.addTerm("t"+i, new RightLinearFuzzySet(min+k/2,k ));
	//			bandwidth.addTerm("t"+i, new RightLinearFuzzySet(min+k,2*k ));
			}else if (n==max){
				bandwidth.addTerm("t"+i, new LeftLinearFuzzySet(max-k, max-k/2));
	//			bandwidth.addTerm("t"+i, new LeftLinearFuzzySet(max-2*k, max-k));
			}else if ((min<n)&&(n<max)){
				System.out.println("n-k: "+(n-k));
				System.out.println("n: "+(n));
				System.out.println("n+k: "+(n+k));
			    bandwidth.addTerm("t"+i, new TriangleFuzzySet(n-k, n, n+k));
			}
		i++;
		}
		return bandwidth;
	}
	
	int fragmentProportionality(ProcessRecord[] fs,int nom ){
		int distance = nom;
		for (int i = 0; i<fs.length; i++)
			if ((fs[i].getNof()-nom)<distance)
				distance =  fs[i].getNof()-nom;
		
		return distance;
	}
	
	public int fuzzyGranularity(double bw, int fp){
		
		double crispWeightedGranularity = 0;
	
		try{
		     System.out.println("Bandwidth = "+bw);
			  
		     bandwidth = segmentedBandwidth(fp);
		     FuzzyValue bwVal =  new FuzzyValue(bandwidth, new SingletonFuzzySet(bw));
		     FuzzyVariable z = new FuzzyVariable("z", 0.0, fp, "");
		     	     
		     FuzzyRule dr[] = new FuzzyRule[fp];
		     
		     for (int i=0; i<fp; i++){
		    	 dr[i]= new FuzzyRule();
		    	 
		    	 dr[i].removeAllAntecedents();
		    	 dr[i].addAntecedent(new FuzzyValue(bandwidth,"t"+i));
		    	 
		    	 dr[i].removeAllConclusions();
		    	 dr[i].addConclusion(new FuzzyValue(z, new SingletonFuzzySet(fp-1-i)));
		    	 
		         dr[i].removeAllInputs();
	             dr[i].addInput(bwVal);
		     }
		     
		     FuzzyValueVector zfvv [] = new FuzzyValueVector[fp];
		     FuzzyValue zFVal []= new FuzzyValue[fp];
		     
		     int j = 0;
		     for (int i=0; i < fp; i++){
		    	 if (dr[i].testRuleMatching()){ 
		    		zfvv[i] = dr[i].execute();
		    	 	zFVal[j] = zfvv[i].fuzzyValueAt(0);
		    	 	j++;
		    	 }
		     }
		     
		     System.out.println(zFVal[0].getFuzzySet());
		     for (int i=1; i<j;i++){
		    	 System.out.println(zFVal[i].getFuzzySet());
		    	 zFVal[0].fuzzySum(zFVal[i]);
		     }
		     crispWeightedGranularity = zFVal[0].weightedAverageDefuzzify();
			  
             // Step 6 (let's look at the fuzzy set of the output and the crisp value)
		     FuzzySet.setToStringPrecision(6);
		     System.out.println("\n Weighted Average Defuzzify value is [0 - "+(fp-1)+"]: " + crispWeightedGranularity);
		     
		  }catch (Exception e){
			  System.out.println("in the catch: "+e.getMessage()+" "+e.getCause());
			  e.printStackTrace();
		  }
		  return (int)crispWeightedGranularity;
	}
	
	public static void main(String[] argv) throws XValueOutsideUODException, IOException{

		FuzzyGranularitySugeno sug = new FuzzyGranularitySugeno();
		// fp = findFragmentProportionality(fArrayl, |M|);
	    int fp = 5;
	    double bw = 0.1;
	    int fGran = sug.fuzzyGranularity(bw, fp);
        System.out.println("fp = "+fp);
		System.out.println("Bandwidth = yVal = "+bw);
		System.out.println("Fuzzy Granualtiy = "+fGran);
		
	     XYChartExample band = new XYChartExample("bandwidth", "bandwidth", "bandwidth", "bandwidth");
	     band.plotFuzzyValues(sug.getBWFuzzyVariable());
         band.showChart("bandwidth");
  	}
	
	public static void main_2(String[] argv){
		  
		FuzzyGranularitySugeno sug = new FuzzyGranularitySugeno();
		try{
		  	 // fp = findFragmentProportionality(fArrayl, |M|);
	         int fp = 5;
	//	     int max = 8;
	//	     max = fp;
	 	     double bw = 0.1;
		     System.out.println("fp = "+fp);
	//	     System.out.println("max = "+max);
		     System.out.println("Bandwidth = yVal = "+bw);
			  
		     FuzzyVariable bandwidth = sug.segmentedBandwidth(fp); 
			  
		     XYChartExample band = new XYChartExample("bandwidth", "bandwidth", "bandwidth", "bandwidth");
		     band.plotFuzzyValues(bandwidth);
	         band.showChart("bandwidth");
	         
		     FuzzyValue yFVal =  new FuzzyValue(bandwidth, new SingletonFuzzySet(bw));
		     FuzzyVariable z = new FuzzyVariable("z", 0.0, fp, "");
		     	     
		     FuzzyRule dr[] = new FuzzyRule[fp];
		     XYChartExample zChart = new XYChartExample("z", "z", "z", "z");
		     
		     for (int i=0; i<fp; i++){
		    	 dr[i]= new FuzzyRule();
		    	 
		    	 dr[i].removeAllAntecedents();
		    	 dr[i].addAntecedent(new FuzzyValue(bandwidth,"t"+i));
		    	 
		    	 dr[i].removeAllConclusions();
		    	 dr[i].addConclusion(new FuzzyValue(z, new SingletonFuzzySet(fp-1-i)));
		    	 
		         dr[i].removeAllInputs();
	             dr[i].addInput(yFVal);
		     }
		     //t0->8, t1->7, t2->6, t3->5, t4->4, t5->3, t6->2, t7->1, t8->0
	
	//         zChart.showChart("");
	
		     FuzzyValueVector zfvv [] = new FuzzyValueVector[fp];
		     FuzzyValue zFVal []= new FuzzyValue[fp];
		     int j = 0;
		     for (int i=0; i < fp; i++){
		    	 if (dr[i].testRuleMatching()){ 
		    		zfvv[i] = dr[i].execute();
		    	 	zFVal[j] = zfvv[i].fuzzyValueAt(0);
		    	 	j++;
		    	 }
		     }
	
	//	     XYChartExample resultChart = new XYChartExample("", "", "", "...");	
		     System.out.println(zFVal[0].getFuzzySet());
		     for (int i=1; i<j;i++){
		    	 System.out.println(zFVal[i].getFuzzySet());
		    	 zFVal[0].fuzzySum(zFVal[i]);
		     }
	
	//	     resultChart.plotFuzzyValues(zfvv[0].fuzzyValueAt(0));
	//	     resultChart.showChart("Result Chart");
		     
		  
		  // Step 5 (defuzzify the output to get crisp values)
		  // calculate the deffuzified value
	//	     double crispMoment = zfvv[0].fuzzyValueAt(0).momentDefuzzify();
	//	     double crispMaximum = zfvv[0].fuzzyValueAt(0).maximumDefuzzify();
	//	     double crispCenter = zfvv[0].fuzzyValueAt(0).centerOfAreaDefuzzify();
		     double crispWeighted = zFVal[0].weightedAverageDefuzzify();
		  
		  // Step 6 (let's look at the fuzzy set of the output and the crisp value)
		     FuzzySet.setToStringPrecision(6);
	//	     System.out.println("\nDefuzzified crispMoment value is: " + crispMoment);
	//	     System.out.println("\nDefuzzified crispMaximum value is: " + crispMaximum);
	//	     System.out.println("\nDefuzzified crispCenter value is: " + crispCenter);
		     System.out.println("\n Weighted Average Defuzzify value is [0 - "+(fp-1)+"]: " + crispWeighted);
		     
	//         resultChart.showChart("Result Chart");
		  }catch (Exception e){
			  System.out.println("in the catch: "+e.getMessage()+" "+e.getCause());
			  e.printStackTrace();
		  }
	  	}
}
