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

public  class TestDiagnoser {
	
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
    	CSP task = new CSP(null);
    	task.setVars(vars);
    	task.setBC(C1);
    	task.setAC(AC1);
		/////////////////////////////////
		Diagnoser diagnoser = new Diagnoser();
		task = diagnoser.diagnose(task, SolverID.choco,did,m);
		assertTrue(task.getDiagnosis().length==1);
		///////////////////////////////
		
	}
    
    @Test
    public void testMinimumDiagnosisFound(){
    	did = DiagnoserID.fastdiag;
    	SolverID sid = SolverID.choco;
    	m = 1;
    	CSP task = new CSP(null);
    	task.setVars(vars);
    	task.setBC(C2);
    	task.setAC(AC2);
		/////////////////////////////////
    	Diagnoser diagnoser = new Diagnoser();
		task = diagnoser.diagnose(task, SolverID.choco,did,m);
		
		assertTrue(task.getDiagnosis().length==1 && task.getDiagnosis()[0].getValue()==3);
		///////////////////////////////	
	}
    
    @Test
    public void testMinimumDiagnosisFound_reverse(){
    	did = DiagnoserID.fastdiag;
    	SolverID sid = SolverID.choco;
    	m = 1;
    	CSP task = new CSP(null);
    	task.setVars(vars);
    	task.setBC(C2_reverse);
    	task.setAC(AC2);
		/////////////////////////////////
    	Diagnoser diagnoser = new Diagnoser();
		task = diagnoser.diagnose(task, SolverID.choco,did,m);
		
		assertTrue(task.getDiagnosis().length==1 && task.getDiagnosis()[0].getValue()==3);
		///////////////////////////////	
	}
   
    
}
