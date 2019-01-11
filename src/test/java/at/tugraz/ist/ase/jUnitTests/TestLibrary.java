package at.tugraz.ist.ase.jUnitTests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import at.tugraz.ist.ase.cspheuristix.Library;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.solvers.Const;
import at.tugraz.ist.ase.util.ClusteringAlgorithmID;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

/** Tests for the main Library
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/
 public class TestLibrary {
	
	////////////////////////////////
	//  SETTINGS 				  //
	////////////////////////////////
	DiagnoserID did = DiagnoserID.fastdiag;
	SolverID sid = SolverID.choco;
	PerformanceIndicator pi = PerformanceIndicator.runtime;
	HeuristicID[] hid = {HeuristicID.clusterBasedVVO, HeuristicID.clusterBasedCO, HeuristicID.MFBasedVVO, HeuristicID.MFBasedCO};
	ClusteringAlgorithmID cid = ClusteringAlgorithmID.kmeans;
	String inputFolder = "IOFOLDER/INPUT/test/LibraryInputFolder1";
	String outputFolder = "IOFOLDER/OUTPUT/test/LibraryOutputFolder1";
	int numberOfvars=4;
	int numberOfClusters=3;
	String stoppingCriteria = "10"; // stop learning after 10 ms
	int m=1;
	CSP[] results;
	////////////////////////////////


	@Test
	public void testResultsNotNull(){
		
		for(int i=0;i<hid.length;i++){
			Library lib = new Library(hid[i], sid, did, inputFolder, outputFolder, pi, stoppingCriteria, cid, numberOfClusters,m);
			results = lib.solveTasks();
			assertTrue(results!=null);
		}
	}
	
	
}
