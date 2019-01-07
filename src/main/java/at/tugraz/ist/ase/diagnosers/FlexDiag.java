package at.tugraz.ist.ase.diagnosers;

import at.tugraz.ist.ase.solvers.Const;
import at.tugraz.ist.ase.solvers.Var;
import at.tugraz.ist.ase.util.SolverID;

/** Represents a FlexDiag Diagnoser
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

class FlexDiag {

	protected Const[] diagnose (Var[] vars, SolverID id, Const[] C, Const[] AC, int m){
		return new FastDiag().diagnose(vars, id, C, AC, m);
		//return AC;
	}
}
