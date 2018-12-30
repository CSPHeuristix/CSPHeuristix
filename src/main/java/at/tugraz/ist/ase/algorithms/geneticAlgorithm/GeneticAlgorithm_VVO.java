package at.tugraz.ist.ase.algorithms.geneticAlgorithm;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_VVO;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.population.Population;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.IndividualID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

public class GeneticAlgorithm_VVO extends GeneticAlgorithm {
	
   
	@Override
	public Individual getTheFittestIndividual(int geneLength, String targetStr, PerformanceIndicator pi,
			CSP[] trainingDataset, HeuristicID hi, SolverID sid) {	
    				
        // Create an initial population
        Population myPop = new Population(50, true, IndividualID.vvo, null, pi,trainingDataset,sid);
    		    
    	// Evolve our population until we reach an optimum solution
        int generationCount = 0;
        
        long targetRuntime = Long.valueOf(targetStr);
        long runtime=0;
        
    	while (runtime<targetRuntime) {
    		long start= System.currentTimeMillis();
    		generationCount++;
    		// System.out.println("Generation: " + generationCount + " Fittest: " + ((Individual_VVO)(myPop.getFittest())).getFitness(null,pi));
    		myPop = this.evolvePopulation(myPop);
    		long stop= System.currentTimeMillis();
    		runtime += stop-start;
    	}
    	// System.out.println("Solution found!");
//    	System.out.println("Generation: " + generationCount);
//    	System.out.println("Genes:");
//    	System.out.println(myPop.getFittest());
    	
		return myPop.getFittest();
    	
    }

    
}
