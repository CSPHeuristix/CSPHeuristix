package at.tugraz.ist.ase.solvers;

/** Represents a CSPInterface
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public class CSP {
	
	
	Var[] vars;
	Const[] cons;
	String name;
	int [] reqs;
	int[] solutions;
	
	public boolean isSolved= false;
	public float runtime = -1;
	
	public CSP (CSP basisCSP){
		this.name= basisCSP.name;
		this.vars= basisCSP.vars;
		this.cons = basisCSP.cons;
	}
	
	public CSP(String name,Var[] vars,Const[] cons){
		this.name= name;
		this.vars= vars;
		this.cons = cons;
	}

	public void insertConstraints(int [] reqs){
		this.reqs = reqs;
		Const[] REQ = new Const[reqs.length];
		for(int i=0;i<reqs.length;i++){
			if(reqs[i]>0){ // IF INITIATED
				REQ[i]=new Const(i, "=", reqs[i]);
			}
		}
		
		Const[] C= cons.clone();
		Const[] AC = new Const[C.length+REQ.length];
		
		for(int i=0;i<C.length;i++)
			AC[i]=C[i];
		
		for(int i=0;i<REQ.length;i++)
			AC[C.length+i]=REQ[i];
		
		this.cons = new Const[AC.length];
		cons=AC.clone();
	}

	public String getName() {
		return this.name;
	}
	public int[] getREQs() {
		return this.reqs;
	}
	public Var[] getVars() {
		return this.vars;
	}  
	public Const[] getConstraints() {
		return this.cons;
	}
	
	public boolean isSolved() {
		return this.isSolved;
	}
	public float getRuntime() {
		return this.runtime;
	}
	
	public String toString(){
		
		String print = name;
		if(solutions!=null)
			for (int i=0;i<solutions.length;i++)
				print += ", var-"+i+"= "+solutions[i];
		print+= ", runtime: "+this.runtime+"ms";
		return print;
		
	}

	/**
	 * @return the solutions
	 */
	public int[] getSolutions() {
		return solutions;
	}

	/**
	 * @param solutions the solutions to set
	 */
	public void setSolutions(int[] solutions) {
		this.solutions = solutions;
	}

}
