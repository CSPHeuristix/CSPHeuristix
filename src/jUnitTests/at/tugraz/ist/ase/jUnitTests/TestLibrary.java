package at.tugraz.ist.ase.jUnitTests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import at.tugraz.ist.ase.cspheuristix.Library;
import at.tugraz.ist.ase.solvers.CSP;
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
	HeuristicID hid;
	ClusteringAlgorithmID cid = ClusteringAlgorithmID.kmeans;
	String inputFile;
	String outputFolder;
	int numberOfvars=4;
	int numberOfClusters=3;
	String stoppingCriteria = "10"; // stop learning after 10 ms
	int m=1;
	////////////////////////////////

	@Test
    public void testCLVVO(){
		hid= HeuristicID.clusterBasedVVO;
		// INPUT FILES: 
		// <training dataset filename>: complete solutions (values of all variables) and user IDs
		// <training dataset filename>_basisCSP: defines the basis CSP
		// <training dataset filename>_newReqs: defines new Reqs. Uninitiated variables have -1 
		// (those vars are not used in distance calculation)
		inputFile = "IOFOLDER/INPUT/test/CSPSolving/pastReqs";
		
		// OUTPUT FILES: 
		// <output foldername>: holds generated clusters
		// Log file: holds performance results
		outputFolder = "IOFOLDER/OUTPUT/test/CSPSolving/";
		Library lib = new Library(hid, sid, did, inputFile, outputFolder, pi, stoppingCriteria, cid, numberOfClusters,m);
		CSP[] solutions = lib.solveTasks();
		assertTrue(solutions!=null);
	}
	
	@Test
    public void testCLCO(){
		hid= HeuristicID.clusterBasedCO;
		// INPUT FILES: 
		// <training dataset filename>: reqs and diagnoses (Uninitiated variables have -1) and user IDs
		// <training dataset filename>_basisCSP: defines the basis CSP
		// <training dataset filename>_newReqs: defines new Reqs. Uninitiated variables have -1 
		// (those vars are not used in distance calculation)
		inputFile = "IOFOLDER/INPUT/test/mf/userRatings";
		
		outputFolder = "IOFOLDER/OUTPUT/test/CSPSolving/";
		
		Library lib = new Library(hid, sid, did, inputFile, outputFolder, pi, stoppingCriteria, cid, numberOfClusters,m);
		CSP[] solutions = lib.solveTasks();
		assertTrue(solutions!=null);
	}
	
	
}
