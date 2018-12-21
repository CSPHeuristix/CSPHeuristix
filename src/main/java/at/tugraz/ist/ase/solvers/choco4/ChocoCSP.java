package at.tugraz.ist.ase.solvers.choco4;

import org.chocosolver.solver.Model;

import at.tugraz.ist.ase.solvers.CSPInterface;
import at.tugraz.ist.ase.solvers.CustomConstraint;
import at.tugraz.ist.ase.solvers.CustomVariable;

/** Represents a Choco CSP Model
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public class ChocoCSP implements CSPInterface{
	
	Model chocoModel;
	CustomVariable[] vars;
	CustomConstraint[] cons;
	String name;
	
	
	boolean isSolved= false;
	long runtime = -1;
	
	ChocoCSP (Model model){
		this.chocoModel = model;
	}
	
	public ChocoCSP (String name, CustomVariable[] vars, CustomConstraint[] cons){
		this.name = name;
		this.vars = vars;
		this.cons = cons;
	}
	
	
	@Override
	public boolean isSolved() {
		// TODO Auto-generated method stub
		return isSolved;
	}

	@Override
	public long getRuntime() {
		// TODO Auto-generated method stub
		return runtime;
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}


	@Override
	public CustomVariable[] getVars() {
		// TODO Auto-generated method stub
		return vars;
	}


	@Override
	public CustomConstraint[] getConstraints() {
		// TODO Auto-generated method stub
		return cons;
	}

}
