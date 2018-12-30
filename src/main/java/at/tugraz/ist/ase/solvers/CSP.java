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
	
	public boolean isSolved= false;
	public long runtime = -1;
	
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
				REQ[i].operator="=";
				REQ[i].varID=i;
				REQ[i].value=reqs[i];
			}
		}
		
		Const[] C= cons.clone();
		Const[] AC = new Const[C.length+REQ.length];
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
	public long getRuntime() {
		return this.runtime;
	}

}
