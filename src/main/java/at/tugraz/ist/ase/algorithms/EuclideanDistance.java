package at.tugraz.ist.ase.algorithms;

/** Represents Euclidean Distance Function
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

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
