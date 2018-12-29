package at.tugraz.ist.ase.diagnosers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.solvers.Const;
import at.tugraz.ist.ase.solvers.Solver;
import at.tugraz.ist.ase.solvers.Var;
import at.tugraz.ist.ase.util.SolverID;
/** Represents a FastDiag
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

class FastDiag {
	
	Var[] vars;
	SolverID solverID;
	int m=1;
//	Algorithm − FastDiag
	
//	1 func FastDiag(C ⊆ AC, AC = {c1..ct}) : ∆
//	2 if isEmpty(C) or inconsistent(AC − C) return null
//	3 else return F D(null, C, AC)
	
//	4 func FD(D, C = {c1..cq}, AC) : diagnosis ∆
//	5 if D != null and consistent(AC) return ∅;
//	6 if singleton(C) return C;
//	7 k = q/2;
//	8 C1 = {c1..ck}; C2 = {ck+1..cq};
//	9 D1 = F D(C1, C2, AC − C1);
//	10 D2 = F D(D1, C1, AC − D1);
//	11 return(D1 ∪ D2);
	
	protected Const[] diagnose (Var[] vars, SolverID id, Const[] C, Const[] AC){
		this.vars = vars;
		this.solverID = id;
		
		if(C==null || C.length==0 || !isConsistent(subtract(AC,C)))
			return null;
		
		else
			return FD(null,C,AC);
	}
	
	//////////////////////////////////
	// for FlexDiag                 //
	//////////////////////////////////
	protected Const[] diagnose (Var[] vars, SolverID id, Const[] C, Const[] AC, int m){
		this.m = m;
		return diagnose(vars, id, C, AC);
		
	}

	private Const[] FD (Const[] D, Const[] C, Const[] AC){
		if(D!=null && isConsistent(AC))
			return D;
		
		if(C!=null && C.length==1)
			return C;
		
		int k = AC.length/2;
		
		Const[] C1 = Arrays.copyOfRange(C, 0, k);
		Const[] C2 = Arrays.copyOfRange(C, k, C.length);
				
		Const[] D1 = FD(C1, C2, subtract(AC, C1));
		Const[] D2 = FD(D1, C2, subtract(AC, D1));
		
		
		Const[] Diagnosis = new Const[D1.length+D2.length];
		System.arraycopy(D1, 0, Diagnosis, 0, D1.length);
		System.arraycopy(D2, 0, Diagnosis, D1.length, D2.length);
		
		
		////////////////////////////////////
		// convert the Array to Set       //
		// to eliminate dublications      //
		// in the diagnosis array         //
		////////////////////////////////////
        Set<Const> diagnonsisSet = convertArrayToSet(Diagnosis); 
        Const[] diagnosisArray = diagnonsisSet.toArray(new Const[0]);
        //////////////////
        
		return diagnosisArray;
	}
	
	///////////////////////////

	private Set<Const> convertArrayToSet(Const array[]) 
    { 
        // Create an empty Set 
        Set<Const> set = new HashSet<>(); 
  
        // Iterate through the array 
        for (Const t : array) { 
            // Add each element into the set 
            set.add(t); 
        } 
        // Return the converted Set 
        return set; 
    }
	private boolean isConsistent(Const[] C){
		// TODO
		Solver solver = new Solver();
		CSP csp = new CSP("test",vars, C);
		CSP soln = solver.solveCSP(csp,this.solverID);
		return soln.isSolved();
		
	}
	
	private Const[] subtract (Const[] C1, Const[] C2){
		// C1- C2
	    
		List<Const> c1 = new ArrayList<Const>(Arrays.asList(C1));
		List<Const> c2 = new ArrayList<Const>(Arrays.asList(C2));
		
		c1.removeAll(c2);
		
		if(c1.isEmpty())
			return null;
		
		return c1.toArray(new Const[c1.size()]);
		
	}

} 
