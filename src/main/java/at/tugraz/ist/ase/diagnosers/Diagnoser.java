package at.tugraz.ist.ase.diagnosers;

import at.tugraz.ist.ase.solvers.CSP;
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
	
	public CSP diagnose (CSP task, SolverID sid,  DiagnoserID did, int m){
		Const[] diagnosis;
		
		long time = 0;
	    long start = System.nanoTime();
		switch(did){
			case fastdiag:
				diagnosis = new FastDiag().diagnose(task.getVars(), sid, task.getREQ(), task.getAC());
			case flexdiag:
				diagnosis =  new FlexDiag().diagnose(task.getVars(), sid, task.getREQ(), task.getAC(), m);
			default:
				diagnosis =  new FastDiag().diagnose(task.getVars(), sid, task.getREQ(), task.getAC());
		}
		long end = System.nanoTime();
		time = end-start;
		
		task.setDiagnosis(diagnosis);
		task.runtime = time;
		
		return task;
		
	}
//	public Const[] diagnose (Var[] vars, SolverID sid, Const[] C, Const[] AC, int m, DiagnoserID did, CSP task){
//		Const[] diagnosis;
//		
//		switch(did){
//			case fastdiag:
//				diagnosis = new FastDiag().diagnose(vars, sid, C, AC);
//			case flexdiag:
//				diagnosis =  new FlexDiag().diagnose(vars, sid, C, AC, m);
//			default:
//				diagnosis =  new FastDiag().diagnose(vars, sid, C, AC);
//		}
//		task.setDiagnosis(diagnosis);
//		
//		return diagnosis;
//		
//	}
	
}
