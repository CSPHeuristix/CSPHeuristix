package at.tugraz.ist.ase.solvers;

/** Represents a CSPInterface
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public interface CSPInterface {
	
	public String getName();  
	public CustomVariable[] getVars();  
	public CustomConstraint[] getConstraints();
	
	public boolean isSolved();
	public long getRuntime();

}
