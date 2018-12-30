package at.tugraz.ist.ase.algorithms.geneticAlgorithm;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc_Default;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_Default;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.population.Population;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.IndividualID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

public class GeneticAlgorithm_Default extends GeneticAlgorithm {
	
   
	@Override
	public Individual getTheFittestIndividual(int geneLength, String targetStr, PerformanceIndicator pi,
			CSP[] trainingDataset, HeuristicID hi, SolverID sid) {
		// TODO Auto-generated method stub
						
    	// Set a target individual
        Individual_Default target = new Individual_Default(geneLength, trainingDataset, null, sid, pi);
        target.setDefaultGeneLength(geneLength);
    		
        // Create an initial population
        Population myPop = new Population(50, true, IndividualID.binary, target, pi,trainingDataset,sid);
    		    
    	// Evolve our population until we reach an optimum solution
        int generationCount = 0;
        
    	while (myPop.getFittest().getFitness(target,pi) < new FitnessCalc_Default().getMaxFitness(target, null)) {
    		generationCount++;
    		System.out.println("Generation: " + generationCount + " Fittest: " + myPop.getFittest().getFitness(target,pi));
    		myPop = this.evolvePopulation(myPop);
    	}
    	System.out.println("Solution found!");
    	System.out.println("Generation: " + generationCount);
    	System.out.println("Genes:");
    	System.out.println(myPop.getFittest());
    	
		return myPop.getFittest();
    	
    }

}
