package at.tugraz.ist.ase.solvers;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual;
import at.tugraz.ist.ase.cspheuristix.Heuristics;
import at.tugraz.ist.ase.util.SolverID;

/** Represents a Solver
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public class Solver {
	boolean withHeuristics = false;
	Individual heuristix;
	CSP csp;
	
	public CSP solveCSP(CSP csp, SolverID id, Individual heuristix)
	{
		if(heuristix!=null)
			this.withHeuristics=true;
		this.heuristix = heuristix;
		this.csp=csp;
		
		if(id == SolverID.choco)
			return new Choco4().solveCSP();
		else
			return null;
		// TODO
	}
	
	
}
