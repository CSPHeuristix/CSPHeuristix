package at.tugraz.ist.ase.algorithms;

public class EuclideanDistance {
	
	public double distanceOfArrays(double[] a, double[] b) {
        double diff_square_sum = 0.0;
        for (int i = 0; i < a.length; i++) {
        	if(a[i]!=-1)
        		diff_square_sum += (a[i] - b[i]) * (a[i] - b[i]);
        }
        return Math.sqrt(diff_square_sum);
    }
	
}
