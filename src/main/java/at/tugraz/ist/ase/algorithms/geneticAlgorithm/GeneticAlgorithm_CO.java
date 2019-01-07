package at.tugraz.ist.ase.algorithms.geneticAlgorithm;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.population.Population;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.IndividualID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

/** Represents a Genetic Algorithm for learning Constraint Ordering
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public class GeneticAlgorithm_CO extends GeneticAlgorithm {

	public GeneticAlgorithm_CO(int geneLength, String targetStr, PerformanceIndicator pi, CSP[] trainingDataset,
			HeuristicID hi, SolverID sid, DiagnoserID did, int m) {
		super(geneLength, targetStr, pi, trainingDataset, hi, sid, did, m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Individual getTheFittestIndividual() {
		// TODO Auto-generated method stub
		 // Create an initial population
        Population myPop = new Population(50, true, IndividualID.co, null, pi,trainingDataset,sid,did,m);
    		    
    	// Evolve our population until we reach an optimum solution
        int generationCount = 0;
        
        long targetRuntime = Long.valueOf(targetStr);
        long runtime=0;
        
    	while (runtime<targetRuntime) {
    		long start= System.currentTimeMillis();
    		generationCount++;
    		myPop = this.evolvePopulation(myPop);
    		long stop= System.currentTimeMillis();
    		runtime += stop-start;
    	}
    	
		return myPop.getFittest();
	}
	
	// TODO: SEDA
   
}
