package at.tugraz.ist.ase.algorithms;

public class MinMaxNormalization {

	
	public double [] normalize(int[] arrayToNormalize, int[][] varsMinMax){
		
		return normalize(copyFromIntArray(arrayToNormalize),copyFromIntArray(varsMinMax));
		
	}
	
	
	public double [] normalize(double[] arrayToNormalize, double[][] varsMinMax){
		
		double [] normalized = new double[arrayToNormalize.length];
		
		for(int i=0;i<arrayToNormalize.length;i++){
			double min = varsMinMax[i][0]; // lower bound
			double max = varsMinMax[i][1]; // upper bound
			normalized[i] = (double)((arrayToNormalize[i]-min)/(max-min));
			if(arrayToNormalize[i]==-1)
				normalized[i]=-1;
		}
		
		return normalized;
	}
	
	public double [] normalizeTo01(double [] arrayToNormalize, double[] minmax){
		
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
	
	private double[] copyFromIntArray(int[] source) {
	    double[] dest = new double[source.length];
	    for(int i=0; i<source.length; i++) {
	        dest[i] = source[i];
	    }
	    return dest;
	}
	
	private double[][] copyFromIntArray(int[][] source) {
	    double[][] dest = new double[source.length][];
	    
	    for (int j=0;j<source.length;j++){
	    	dest[j] = new double[source[j].length];
	    	
		    for(int i=0; i<source[j].length; i++) {
		        dest[j][i] = source[j][i];
		    }
		}
	    return dest;
	}
}
