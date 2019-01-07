package at.tugraz.ist.ase.algorithms.geneticAlgorithm;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc_Default;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_Default;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.population.Population;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.IndividualID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

/** Represents a default Genetic Algorithm
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public class GeneticAlgorithm_Default extends GeneticAlgorithm {
	
   
	public GeneticAlgorithm_Default(int geneLength, String targetStr, PerformanceIndicator pi, CSP[] trainingDataset,
			HeuristicID hi, SolverID sid, DiagnoserID did, int m) {
		super(geneLength, targetStr, pi, trainingDataset, hi, sid, did, m);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Individual getTheFittestIndividual() {
		// TODO Auto-generated method stub
						
    	// Set a target individual
        Individual_Default target = new Individual_Default(targetStr, trainingDataset, null, sid, pi,did,m);
        target.setDefaultGeneLength(geneLength);
    		
        // Create an initial population
        Population myPop = new Population(50, true, IndividualID.binary, targetStr, pi,trainingDataset,sid,did,m);
    		    
    	// Evolve our population until we reach an optimum solution
        int generationCount = 0;
        
    	while (myPop.getFittest().getFitness() < new FitnessCalc_Default(target,targetStr,pi,hi,trainingDataset, sid,did,m).getMaxFitness()) {
    		generationCount++;
    		System.out.println("Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness());
    		myPop = this.evolvePopulation(myPop);
    	}
    	System.out.println("Solution found!");
    	System.out.println("Generation: " + generationCount);
    	System.out.println("Genes:");
    	System.out.println(myPop.getFittest());
    	
		return myPop.getFittest();
    	
    }

}
