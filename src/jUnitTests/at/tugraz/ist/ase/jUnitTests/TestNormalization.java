package at.tugraz.ist.ase.jUnitTests;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.junit.Test;

import at.tugraz.ist.ase.algorithms.Clustering;
import at.tugraz.ist.ase.algorithms.MatrixFactorization;
import at.tugraz.ist.ase.algorithms.MinMaxNormalization;
import at.tugraz.ist.ase.util.ClusteringAlgorithmID;
import at.tugraz.ist.ase.util.SolverID;

/** Tests for Normalization
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public class TestNormalization {
	
	////////////////////////////////
	//  SETTINGS 				  //
	////////////////////////////////
	double [] arrayToNormalize = {5, 9, 3, 2};
	double [][] varsMinMax = {{0,10},{0,18},{0,6},{0,4}};
	double [] minMax = {0,18};
	////////////////////////////////

	@Test
    public void testNormalize(){
		
		MinMaxNormalization norm = new MinMaxNormalization();
		double [] normalized = norm.normalize(arrayToNormalize, varsMinMax);
		assertTrue(normalized.length==arrayToNormalize.length);
		assertTrue(normalized[0]==0.5);
	}
	
	@Test
    public void testNormalizeTo01(){
		
		MinMaxNormalization norm = new MinMaxNormalization();
		double [] normalized = norm.normalizeTo01(arrayToNormalize, minMax);
		assertTrue(normalized.length==arrayToNormalize.length);
		assertTrue(normalized[1]==0.5);
		assertFalse(normalized[0]==0.5);
	}
}
