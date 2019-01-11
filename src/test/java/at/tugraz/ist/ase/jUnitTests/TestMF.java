package at.tugraz.ist.ase.jUnitTests;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.junit.Test;

import at.tugraz.ist.ase.algorithms.Clustering;
import at.tugraz.ist.ase.algorithms.MatrixFactorization;
import at.tugraz.ist.ase.util.ClusteringAlgorithmID;
import at.tugraz.ist.ase.util.SolverID;

/** Tests for Matrix Factorization
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public  class TestMF {
	
	////////////////////////////////
	//  SETTINGS 				  //
	////////////////////////////////
	int numVariables=4;
	int numFeatures =3; // mxk, kxn -> k value
	int numIterations =2;
	int userID=20;
	int numberRecommendedItems=numVariables;
	String inputFile = "IOFOLDER/INPUT/test/mf/userRatings.data";
	double [][] p = null;
	double [][] q = null;
	////////////////////////////////

	@Test
	public  void testMF(){
		
		MatrixFactorization mf = new MatrixFactorization();
		DataModel dataModel;
		try {
			dataModel = new FileDataModel(new File(inputFile));
			mf.SVD(dataModel, numFeatures, numIterations, userID, numberRecommendedItems);
			p=mf.UF;
			q=mf.IF;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		assertTrue(p!=null);
		assertTrue(q!=null);
	}
}
