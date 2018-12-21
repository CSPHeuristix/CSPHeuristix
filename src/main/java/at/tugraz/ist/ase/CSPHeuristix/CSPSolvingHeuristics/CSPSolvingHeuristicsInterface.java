package at.tugraz.ist.ase.CSPHeuristix.CSPSolvingHeuristics;

import at.tugraz.ist.ase.CSPHeuristix.enumerators.DiagnoserID;
import at.tugraz.ist.ase.CSPHeuristix.enumerators.HeuristicID;
import at.tugraz.ist.ase.CSPHeuristix.enumerators.SolverID;

/** Represents a CSPSolvingHeuristicsInterface
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public interface CSPSolvingHeuristicsInterface {

	public void initiate(HeuristicID heuristicsID, SolverID solverID, DiagnoserID diagnosisAlgorithmID, String inputFolder);

}

