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
	
	public boolean isSolved= false;
	public long runtime = -1;
	
	public CSP(String name,Var[] vars,Const[] cons){
		this.name= name;
		this.vars= vars;
		this.cons = cons;
	}

	public String getName() {
		return this.name;
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
