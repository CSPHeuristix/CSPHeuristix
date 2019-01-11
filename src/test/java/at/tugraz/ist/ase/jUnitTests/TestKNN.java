package at.tugraz.ist.ase.jUnitTests;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.junit.Test;

import at.tugraz.ist.ase.algorithms.Clustering;
import at.tugraz.ist.ase.algorithms.KNN;
import at.tugraz.ist.ase.algorithms.MatrixFactorization;
import at.tugraz.ist.ase.algorithms.MinMaxNormalization;
import at.tugraz.ist.ase.util.ClusteringAlgorithmID;
import at.tugraz.ist.ase.util.SolverID;

/** Tests for finding KNN
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public  class TestKNN {
	
	////////////////////////////////
	//  SETTINGS 				  //
	////////////////////////////////
	int k=1;
	int [] req = {5, 9, 3, 2};
	int [][] otherReqs = {{5, 9, 3, 2},{5, 9, 3, 0},{0, 0, 0, 2},{5, 9, 0, 0},{0, 0, 0, 0}};
	int [][] varsMinMax = {{0,10},{0,18},{0,6},{0,4}};
	////////////////////////////////

	@Test
	public  void testKNN_1(){
		k=1;
		KNN knn = new KNN();
		int [] indexes = knn.getKNN(k, req, otherReqs, varsMinMax);
		assertTrue(indexes.length==k);
		assertTrue(indexes[0]==0);
	}
	
	@Test
	public  void testKNN_2(){
		k=2;
		KNN knn = new KNN();
		int [] indexes = knn.getKNN(k, req, otherReqs, varsMinMax);
		assertTrue(indexes.length==k);
		assertTrue(indexes[0]==0);
		assertTrue(indexes[1]==1);
	}
	
	@Test
	public  void testKNN_3(){
		k=3;
		KNN knn = new KNN();
		int [] indexes = knn.getKNN(k, req, otherReqs, varsMinMax);
		assertTrue(indexes.length==k);
		assertTrue(indexes[0]==0);
		assertTrue(indexes[1]==1);
		assertTrue(indexes[2]==3);
	}
	
	@Test
	public  void testKNN_4(){
		k=4;
		KNN knn = new KNN();
		int [] indexes = knn.getKNN(k, req, otherReqs, varsMinMax);
		assertTrue(indexes.length==k);
		assertTrue(indexes[0]==0);
		assertTrue(indexes[1]==1);
		assertTrue(indexes[2]==3);
		assertTrue(indexes[3]==2);
	}
	
	// TODO:
	//	findClosestCluster
}
