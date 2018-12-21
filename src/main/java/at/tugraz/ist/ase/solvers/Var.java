package at.tugraz.ist.ase.solvers;

/** Represents a VariableInterface
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public class Var{
	
	String name;
	int minDomain;
	int maxDomain;
	
	  
	public Var(String name, int minDomain, int maxDomain){
		this.name = name;
		this.minDomain = minDomain;
		this.maxDomain = maxDomain;
	}
	
	public String getVarname(){
		return this.name;
	}
	public int getMinDomain(){
		return this.minDomain;
	}
	public int getMaxDomain(){
		return this.maxDomain;
	}
	
}
