import at.tugraz.ist.ase.CSPHeuristix.enumerators.SolverID;
import at.tugraz.ist.ase.diagnosers.DiagnoserInterface;
import at.tugraz.ist.ase.diagnosers.FastDiag;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.solvers.Const;
import at.tugraz.ist.ase.solvers.Var;
import at.tugraz.ist.ase.solvers.choco4.ChocoSolver;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestFastDiag {
	
	Var var1 = new Var(""+0, 0, 5);
	Var [] vars = new Var[]{var1};
	
	Const cons1 = new Const(0, ">", 4);
	Const [] C = new Const []{cons1};
	
	Const cons2 = new Const(0, "<", 3);
	Const [] AC = new Const []{cons1,cons2};

    @Test
    public void testDiagnosisFound(){
		
		/////////////////////////////////
		FastDiag diagnoser = new FastDiag();
		//CSPInterface csp = new ChocoCSP("test1",vars, consArray1);
		Const[] diag = diagnoser.diagnose(vars, SolverID.choco, C, AC);
		assertTrue(diag.length==1 && diag[0].getValue()==4);
		///////////////////////////////
		
	}
    
}
