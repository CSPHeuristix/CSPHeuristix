package at.tugraz.ist.ase.jUnitTests;
import at.tugraz.ist.ase.diagnosers.Diagnoser;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.solvers.Const;
import at.tugraz.ist.ase.solvers.Var;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.SolverID;

import org.junit.Test;
import static org.junit.Assert.*;

/** Tests for Diagnosis
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public class TestDiagnoser {
	
	////////////////////////////////
	//  SETTINGS 				  //
	////////////////////////////////
	DiagnoserID did = DiagnoserID.fastdiag;
	SolverID sid = SolverID.choco;
	int m = 1;
	////////////////////////////////
	
	////////////////////////////////
	//  INPUTS  				  //
	////////////////////////////////
	Var var1 = new Var(""+0, 0, 5);
	Var [] vars = new Var[]{var1};
	
	Const cons1 = new Const(0, ">", 4);
	Const cons2 = new Const(0, "<", 3);
	Const cons3 = new Const(0, "=", 5);
	
	Const [] C1 = new Const []{cons1};
	Const [] AC1 = new Const []{cons1,cons2};
	
	Const [] C2 = new Const []{cons2,cons3};
	Const [] AC2 = new Const []{cons1,cons2,cons3};
	
	Const [] C2_reverse = new Const []{cons3,cons2};
	///////////////////////////////
	
	
	////////////////////////////////
	//  TESTS    				  //
	////////////////////////////////

    @Test
    public void testDiagnosisFound(){
    	did = DiagnoserID.fastdiag;
    	SolverID sid = SolverID.choco;
    	m = 1;
		/////////////////////////////////
		Diagnoser diagnoser = new Diagnoser();
		Const[] diag = diagnoser.diagnose(vars, SolverID.choco, C1, AC1, m, did);
		assertTrue(diag.length==1);
		///////////////////////////////
		
	}
    
    @Test
    public void testMinimumDiagnosisFound(){
    	did = DiagnoserID.fastdiag;
    	SolverID sid = SolverID.choco;
    	m = 1;
		/////////////////////////////////
    	Diagnoser diagnoser = new Diagnoser();
		Const[] diag = diagnoser.diagnose(vars, SolverID.choco, C2, AC2, m, did);
		assertTrue(diag.length==1 && diag[0].getValue()==3);
		///////////////////////////////	
	}
    
    @Test
    public void testMinimumDiagnosisFound_reverse(){
    	did = DiagnoserID.fastdiag;
    	SolverID sid = SolverID.choco;
    	m = 1;
		/////////////////////////////////
    	Diagnoser diagnoser = new Diagnoser();
		//CSPInterface csp = new ChocoCSP("test1",vars, consArray1);
		Const[] diag = diagnoser.diagnose(vars, SolverID.choco, C2_reverse,AC2, m, did);
		assertTrue(diag.length==1 && diag[0].getValue()==3);
		///////////////////////////////	
	}
   
    
    
}
