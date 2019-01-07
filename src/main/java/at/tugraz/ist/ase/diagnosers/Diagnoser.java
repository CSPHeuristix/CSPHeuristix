package at.tugraz.ist.ase.diagnosers;

import at.tugraz.ist.ase.solvers.Const;
import at.tugraz.ist.ase.solvers.Var;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.SolverID;

/** Represents a Diagnoser
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public class Diagnoser {
	
	public Const[] diagnose (Var[] vars, SolverID sid, Const[] C, Const[] AC, int m, DiagnoserID did){
		
		switch(did){
			case fastdiag:
				return new FastDiag().diagnose(vars, sid, C, AC);
			case flexdiag:
				return new FlexDiag().diagnose(vars, sid, C, AC, m);
			default:
				return new FastDiag().diagnose(vars, sid, C, AC);
		}
		
	}

}
