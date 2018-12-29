package at.tugraz.ist.ase.algorithms;

public class MinMaxNormalization {

	
	public static double [] normalize(int [] arrayToNormalize, int[][] vars){
		
		double [] normalized = new double[arrayToNormalize.length];
		
		for(int i=0;i<arrayToNormalize.length;i++){
			double min = vars[i][0]; // lower bound
			double max = vars[i][1]; // upper bound
			normalized[i] = (double)((arrayToNormalize[i]-min)/(max-min));
			if(arrayToNormalize[i]==-1)
				normalized[i]=-1;
		}
		
		return normalized;
	}
	
	public static double [] normalizeTo01(double [] arrayToNormalize, double[] minmax){
		
		double [] normalized = new double[arrayToNormalize.length];
		
		for(int i=0;i<arrayToNormalize.length;i++){
			double min = minmax[0];
			double max = minmax[1];
			normalized[i] = (double)((arrayToNormalize[i]-min)/(max-min));
			if(arrayToNormalize[i]==-1)
				normalized[i]=-1;
		}
		
		return normalized;
	}
}
