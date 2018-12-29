package at.tugraz.ist.ase.solvers;


import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

class Choco4 {

	protected CSP solveCSP(CSP csp) {
		// TODO Auto-generated method stub
		
		 Model chocoModel= createChocoModel(csp);
		 Solver solver = chocoModel.getSolver();
 	 
		 long time = 0;
		 long start = System.nanoTime();
		 boolean isSolved= solver.solve();
		 long end = System.nanoTime();
		 time = end-start;
		
		 CSP solution = new CSP(null,null,null);
		 solution.isSolved = isSolved;
		 solution.runtime = time;
		 
		 return solution;
	}
	
	private Model createChocoModel (CSP csp){
		
		Model newModel = new Model(csp.getName());
		IntVar [] vars = new IntVar[csp.getVars().length];
		Constraint [] cons = new Constraint[csp.getConstraints().length];
		
		// INSERT VARS
		for(int m=0;m<vars.length;m++){
			int id = m;
			int minDomain = csp.getVars()[m].getMinDomain();
			int maxDomain = csp.getVars()[m].getMaxDomain();
			
			vars[m] = newModel.intVar(""+id,minDomain,maxDomain);
		}
		
		// INSERT CONSTRAINTS
		for(int m=0;m<cons.length;m++){
			int id = csp.getConstraints()[m].getVarID();
			String operator = csp.getConstraints()[m].getOperator();
			int value = csp.getConstraints()[m].getValue();
			cons[m] = newModel.arithm((IntVar) newModel.getVar(id), operator, value);
			cons[m].post();
		}
		
		return newModel;
	}
	
	
}
