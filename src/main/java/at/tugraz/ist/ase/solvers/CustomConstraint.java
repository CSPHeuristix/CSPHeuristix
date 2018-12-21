package at.tugraz.ist.ase.solvers;

/** Represents a ConstraintInterface
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public class CustomConstraint {
	
	int varID;
	String operator;
	int value;
	 
	public CustomConstraint(int varID, String operator, int value){
		this.varID = varID;
		this.operator = operator;
		this.value = value;
	}
	
	public String getOperator(){
		return this.operator;
	}
	public int getVarID(){
		return this.varID;
	}
	public int getValue(){
		return this.value;
	}
	
}
