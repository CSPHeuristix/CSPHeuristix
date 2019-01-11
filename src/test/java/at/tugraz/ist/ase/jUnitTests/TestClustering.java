package at.tugraz.ist.ase.jUnitTests;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import at.tugraz.ist.ase.algorithms.Clustering;
import at.tugraz.ist.ase.util.ClusteringAlgorithmID;
import at.tugraz.ist.ase.util.SolverID;

/** Tests for Clustering
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public class TestClustering {
	
	////////////////////////////////
	//  SETTINGS 				  //
	////////////////////////////////
	ClusteringAlgorithmID cid = ClusteringAlgorithmID.kmeans;
	String inputFile = "IOFOLDER/INPUT/test/kmeans/simpleCSPS.data";
	String outputFolder = "IOFOLDER/OUTPUT/test/kmeans/";
	int numberOfvars=4;
	int numberOfClusters=3;
	////////////////////////////////

	@Test
	public void testKmeans(){
		
		Clustering clustering = new Clustering();
		int [][] clusteredItems = clustering.cluster(cid, inputFile, outputFolder, numberOfvars, numberOfClusters);
		
		assertTrue(clusteredItems.length==numberOfClusters);
	}
}
