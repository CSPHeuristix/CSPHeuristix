package at.tugraz.ist.ase.solvers;


import static org.chocosolver.solver.search.strategy.Search.intVarSearch;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.constraints.nary.cnf.LogOp.Operator;
import org.chocosolver.solver.search.strategy.selectors.values.IntValueSelector;
import org.chocosolver.solver.search.strategy.selectors.variables.VariableSelector;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.Variable;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual;
import at.tugraz.ist.ase.cspheuristix.Heuristics;
import at.tugraz.ist.ase.util.SolverID;

/** Represents Choco Solver v4
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

class Choco4 extends at.tugraz.ist.ase.solvers.Solver{

	boolean withHeuristics = false;
	Individual heuristix;
	CSP csp;
	
	protected CSP solveCSP(CSP task, Individual heu) {
		 this.csp=task;
		 this.heuristix=heu;
		 if(heu!=null)
			 this.withHeuristics=true;
		 // TODO Auto-generated method stub
		 Model chocoModel= createChocoModel(this.csp);
		 Solver solver = chocoModel.getSolver();
		 if(withHeuristics)
			 solver = setHeuristics(solver);
		 
		 long time = 0;
		 long start = System.nanoTime();
		 boolean isSolved= solver.solve();
		 long end = System.nanoTime();
		 time = end-start;
		
		 CSP solution = new CSP(csp);
		 int [] vars = new int [chocoModel.getNbVars()];
		 for(int i=0;i<vars.length;i++)
			 vars[i]= ((IntVar)chocoModel.getVar(i)).getValue();
		 solution.setSolutions(vars);
		 
		 solution.isSolved = isSolved;
		 solution.runtime = time;
		 
		 return solution;
	}
		
	private Model createChocoModel (CSP csp){
		
		Model newModel = new Model(csp.getName());
		IntVar [] vars = new IntVar[csp.getVars().length];
		Constraint [] cons = new Constraint[csp.getAllConstraints().length];
		
		// INSERT VARS
		for(int m=0;m<vars.length;m++){
			int id = m;
			int minDomain = csp.getVars()[m].getMinDomain();
			int maxDomain = csp.getVars()[m].getMaxDomain();
			
			vars[m] = newModel.intVar(""+id,minDomain,maxDomain);
		}
		
		// INSERT CONSTRAINTS
		if(cons!=null)
		for(int m=0;m<cons.length;m++){
			int id = csp.getAllConstraints()[m].getVarID();
			String operator = csp.getAllConstraints()[m].getOperator();
			int value = csp.getAllConstraints()[m].getValue();
			
			IntVar var = (IntVar)(newModel.getVar(id));
			cons[m] = newModel.arithm(var, operator, value);
			cons[m].post();
		}
		
		return newModel;
	}

	private Solver setHeuristics (Solver solver){
		
		// TODO: SEDA : debug this function
		
		VariableSelector<IntVar> varSelector = null;
		IntValueSelector valueSelector = new Choco4ValueOrder(heuristix.valueOrdering);
		IntVar[] intvars = getIntVars(solver.getModel());
		 
		varSelector =(VariableSelector<IntVar>) variables -> {
			for(int i =0;i<solver.getModel().getVars().length;i++){
		        return intvars[heuristix.variableOrdering[i]];
		    }
		    return null;
	    };
		
		solver.setSearch(intVarSearch(
                
				varSelector,
                
				valueSelector,
              
                // variables to branch on
                getIntVars(solver.getModel())
			));
		return solver;
		
	}
	private IntVar[] getIntVars(Model model){
		 Variable[] vars = model.getVars();
		 IntVar[] intvars = new IntVar[vars.length];
		 
		 for(int v=0;v<vars.length;v++){
			 intvars[v]= (IntVar)vars[v];
		 }
		 return intvars;
		
	}
}

class Choco4ValueOrder implements IntValueSelector {
	
    int [][] valueSelections;
    int counter = 0;
    
    public Choco4ValueOrder(int[][] heuristic){
    	valueSelections =new int [heuristic.length][];
    	for(int i=0;i<heuristic.length;i++){
    		valueSelections[i] = new int [heuristic[i].length];
    		valueSelections = heuristic.clone();
    	}
    }
    
 	@Override
    public int selectValue(IntVar var) {
 		
 		if(counter>valueSelections[var.getId()].length)
 			counter=0;
 		
        return valueSelections[var.getId()][counter];
    }
}

