package common;

public class Random {
	int o=0;
	int p =0; 
	int q =0;

		static double priorX = -1;
		public double exponential(int rate){
			double x;
			double lambada =0.05;//0.2;
			int min = 0;
			double max = 100;
			double step =((max-min)/rate);
			x = (priorX<0)? 0: ((((priorX+step)>max)&&((priorX+step))<(max+step))?(max):priorX+step);
			priorX = x;
	//		double expn = 1- (1/Math.exp(lambada*x));
			double expn = 1- (Math.exp(-lambada*x));
			return expn*100;
			
		}
	
		public double generateNumber_50_50(){

	        int min = 1;
	        int max = 100;
	        double boundNumber = (Math.random() * (max - min + 1) ) + min;
	        boundNumber = (boundNumber>100)?100:boundNumber;
	        boundNumber = (boundNumber<1)?1:boundNumber;
	        
	        return boundNumber;
		}
		

	
		public double generateNumber_70_30(){
 
			double rawRandomNumber = 0;
			int min = 1;
			int max = 100;
			double boundNumber = (Math.random() * (max - min + 1) ) + min;
 
			if ((boundNumber>0) && (boundNumber<=20)){ //20% 
				int cmin1 = 1;
				int cmax1 = 10;
				rawRandomNumber = (Math.random() * (cmax1 - cmin1 + 1) ) + cmin1;
				o++;
			}else if((boundNumber>20) && (boundNumber<=50)){ //30%
				int cmin2 = 11;
				int cmax2 = 20;
				rawRandomNumber = (Math.random() * (cmax2 - cmin2 + 1) ) + cmin2;
				p++;
			}else if ((boundNumber>50) && (boundNumber<=100)){ //50%
				int cmin3 = 21;
				int cmax3 = 100;
				rawRandomNumber = (Math.random() * (cmax3 - cmin3 + 1) ) + cmin3;
				q++;
			}
			rawRandomNumber = (rawRandomNumber>100)?100:rawRandomNumber;
			rawRandomNumber = (rawRandomNumber<1)?1:rawRandomNumber;
//			System.out.println("boundNumber: "+boundNumber+" o= "+o+" p= "+p+" q= "+q+" rawRandomNumber= "+rawRandomNumber);
			return rawRandomNumber;
 	}
	
	public int genrateNumberForLoan(){

//		int o=0;
//		int p =0; 
//		int q =0;
		int rawRandomNumber = 0;

        int min = 0;
        int max = 100;
        double boundNumber = (int)(Math.random() * (max - min + 1) ) + min;

        if ((boundNumber>=0) && (boundNumber<=70)){
        	int cmin1 = 0;
	        int cmax1 = 70;
        	rawRandomNumber = (int)(Math.random() * (cmax1 - cmin1 + 1) ) + cmin1;
        	o++;
        }else if((boundNumber>70) && (boundNumber<=100)){
        	int cmin2 = 71;
	        int cmax2 = 100;
        	rawRandomNumber = (int)(Math.random() * (cmax2 - cmin2 + 1) ) + cmin2;
        	p++;
        }
		return rawRandomNumber;
	}
	
	public static void main(String args[]){
		Random rand = new Random();
		for (int a=1;a<100;a++){
			rand.generateNumber_50_50();
		}

	}
	
}
