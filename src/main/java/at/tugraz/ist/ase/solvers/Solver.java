package at.tugraz.ist.ase.solvers;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual;
import at.tugraz.ist.ase.cspheuristix.Heuristics;
import at.tugraz.ist.ase.util.SolverID;

/** Represents a CSP Solver
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public class Solver {
	
	
	public CSP solveCSP(CSP task, SolverID id, Individual heu)
	{
		
		if(id == SolverID.choco)
			return new Choco4().solveCSP(task,heu);
		else
			return null;
		// TODO
	}
	
	
}
