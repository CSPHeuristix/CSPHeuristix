package at.tugraz.ist.ase.cspheuristix;

import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.ClusteringAlgorithmID;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

/** Represents a MFBasedVVO
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

class MFVVO extends Heuristics{

	@Override
	protected void learn(HeuristicID heuristicsID, SolverID solverID, DiagnoserID diagnosisAlgorithmID,
			String inputFile, String outputFolder, PerformanceIndicator pi, String stoppingCriteria ,ClusteringAlgorithmID cid, int numberOClusters) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected CSP solveTask(CSP task) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
