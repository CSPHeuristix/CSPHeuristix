package at.tugraz.ist.ase.algorithms;

import org.apache.mahout.cf.taste.impl.recommender.svd.Factorization;
import org.apache.mahout.cf.taste.impl.recommender.svd.Factorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDPlusPlusFactorizer;
import org.apache.mahout.cf.taste.model.DataModel;

/** Represents a SVD based Matrix Factorization
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public class MatrixFactorization {
	
	public double[][] IF;
	public double[][] UF;
	
    ////////////////////////////////////////////
	///////////// Default Settings /////////////
	int numFeatures=6;  // mxk, kxn -> k value
	int numIterations=5;
    ////////////////////////////////////////////	
	
	
	public void SVD(DataModel dataModel){
		try {
			long start = System.nanoTime();
			Factorizer factorizer_svd;
		
			factorizer_svd = new SVDPlusPlusFactorizer(dataModel,numFeatures,numIterations);
			
			Factorization facts = factorizer_svd.factorize();
			IF = facts.allItemFeatures();
			UF = facts.allUserFeatures();
		
			long end  = System.nanoTime();
			System.out.println("SVD Time: "+ (end-start));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public double[][] multiplyByMatrix(double[][] m1, double[][] m2) {
		
		m2 = transposeMatrix(m2);
        int m1ColLength = m1[0].length; // m1 columns length
        int m2RowLength = m2.length;    // m2 rows length
        if(m1ColLength != m2RowLength) return null; // matrix multiplication is not possible
        int mRRowLength = m1.length;    // m result rows length
        int mRColLength = m2[0].length; // m result columns length
        double[][] mResult = new double[mRRowLength][mRColLength];
        for(int i = 0; i < mRRowLength; i++) {         // rows from m1
            for(int j = 0; j < mRColLength; j++) {     // columns from m2
                for(int k = 0; k < m1ColLength; k++) { // columns from m1
                    mResult[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return mResult;
    }
	
	private double[][] transposeMatrix(double [][] m){
	        double[][] temp = new double[m[0].length][m.length];
	        for (int i = 0; i < m.length; i++)
	            for (int j = 0; j < m[0].length; j++)
	                temp[j][i] = m[i][j];
	        return temp;
	    }


}
