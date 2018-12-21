package at.tugraz.ist.ase.solvers;

import at.tugraz.ist.ase.CSPHeuristix.enumerators.SolverID;
import at.tugraz.ist.ase.solvers.choco4.ChocoSolver;

/** Represents a SolverInterface
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public class Solver {

	public CSP solveCSP(CSP csp, SolverID id) {
		
		if(id == SolverID.choco)
			return new ChocoSolver().solveCSP(csp);
		else
			return null;
		
		// TODO
	}
	
}
