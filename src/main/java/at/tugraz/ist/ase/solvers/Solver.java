package at.tugraz.ist.ase.solvers;

import at.tugraz.ist.ase.util.SolverID;

/** Represents a Solver
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public class Solver {

	public CSP solveCSP(CSP csp, SolverID id) {
		
		if(id == SolverID.choco)
			return new Choco4().solveCSP(csp);
		else
			return null;
		// TODO
	}
	
}
