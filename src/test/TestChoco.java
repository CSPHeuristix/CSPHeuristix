import at.tugraz.ist.ase.solvers.CSPInterface;
import at.tugraz.ist.ase.solvers.CustomConstraint;
import at.tugraz.ist.ase.solvers.CustomVariable;
import at.tugraz.ist.ase.solvers.SolverInterface;
import at.tugraz.ist.ase.solvers.choco4.ChocoCSP;
import at.tugraz.ist.ase.solvers.choco4.ChocoSolver;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestChoco {
	
	CustomVariable var1 = new CustomVariable(""+0, 0, 5);
	CustomVariable [] vars = new CustomVariable[]{var1};
	
	CustomConstraint cons1 = new CustomConstraint(0, ">", 3);
	CustomConstraint [] consArray1 = new CustomConstraint []{cons1};
	
	CustomConstraint cons2 = new CustomConstraint(0, ">", 6);
	CustomConstraint [] consArray2 = new CustomConstraint []{cons2};

    @Test
    public void testSolutionFound(){
		
		/////////////////////////////////
		SolverInterface solver = new ChocoSolver();
		CSPInterface csp = new ChocoCSP("test1",vars, consArray1);
		
		CSPInterface soln = solver.solveCSP(csp);
		
		assertTrue(soln.isSolved());
		///////////////////////////////
		
	}
    
    @Test
    public void testNoSolutionFound(){
		
		/////////////////////////////////
		SolverInterface solver2 = new ChocoSolver();
		CSPInterface csp2 = new ChocoCSP("test2",vars, consArray2);
		
		CSPInterface soln2 = solver2.solveCSP(csp2);
		
		assertFalse(soln2.isSolved());
		///////////////////////////////
		
	}
}
