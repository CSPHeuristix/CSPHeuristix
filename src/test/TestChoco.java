import at.tugraz.ist.ase.CSPHeuristix.enumerators.SolverID;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.solvers.Const;
import at.tugraz.ist.ase.solvers.Solver;
import at.tugraz.ist.ase.solvers.Var;
import at.tugraz.ist.ase.solvers.choco4.ChocoSolver;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestChoco {
	
	Var var1 = new Var(""+0, 0, 5);
	Var [] vars = new Var[]{var1};
	
	Const cons1 = new Const(0, ">", 3);
	Const [] consArray1 = new Const []{cons1};
	
	Const cons2 = new Const(0, ">", 6);
	Const [] consArray2 = new Const []{cons2};

    @Test
    public void testSolutionFound(){
		
		/////////////////////////////////
		Solver solver = new Solver();
		CSP csp = new CSP("test1",vars, consArray1);
		
		CSP soln = solver.solveCSP(csp,SolverID.choco);
		
		assertTrue(soln.isSolved());
		///////////////////////////////
		
	}
    
    @Test
    public void testNoSolutionFound(){
		
		/////////////////////////////////
		Solver solver2 = new Solver();
		CSP csp2 = new CSP("test2",vars, consArray2);
		
		CSP soln2 = solver2.solveCSP(csp2,SolverID.choco);
		
		assertFalse(soln2.isSolved());
		///////////////////////////////
		
	}
}
