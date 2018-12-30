package algorithms;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import at.tugraz.ist.ase.cspheuristix.Library;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.ClusteringAlgorithmID;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

public class TestLibrary {
	
	////////////////////////////////
	//  SETTINGS 				  //
	////////////////////////////////
	DiagnoserID did = DiagnoserID.fastdiag;
	SolverID sid = SolverID.choco;
	PerformanceIndicator pi = PerformanceIndicator.runtime;
	HeuristicID hid= HeuristicID.clusterBasedVVO;
	ClusteringAlgorithmID cid = ClusteringAlgorithmID.kmeans;
	String inputFile = "IOFOLDER/INPUT/test/kmeans/simpleCSPS.data";
	String outputFolder = "IOFOLDER/OUTPUT/test/kmeans/";
	int numberOfvars=4;
	int numberOfClusters=3;
	////////////////////////////////

	@Test
    public void testCLVVO(){
		Library lib = new Library(hid, sid, did, inputFile, outputFolder, pi);
		CSP[] solutions = lib.solveTasks();
		assertTrue(solutions!=null);
	}
}
