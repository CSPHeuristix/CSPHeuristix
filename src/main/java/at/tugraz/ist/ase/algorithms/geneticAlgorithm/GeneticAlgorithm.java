package at.tugraz.ist.ase.algorithms.geneticAlgorithm;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.population.Population;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.IndividualID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

public abstract class GeneticAlgorithm {
	
	/* GA parameters */
	private double uniformRate = 0.5;
	private double mutationRate = 0.015;
	private int tournamentSize = 5;
	private boolean elitism = true;
	private IndividualID iid= IndividualID.vvo;
	private Individual target =null;
	private PerformanceIndicator pi = PerformanceIndicator.runtime;
	CSP[] trainingDataset;
	SolverID sid;

    /* Public methods */
    
   
    public abstract Individual getTheFittestIndividual(int geneLength, String targetStr,PerformanceIndicator pi, CSP[] trainingDataset, HeuristicID hi,SolverID sid);
//    {
//    	
//    	trainingDataset = trainingDataset.clone();
//    	this.sid=sid;
//    	switch(hi){
//    		case clusterBasedVVO:
//    			return new GeneticAlgorithm_VVO().getTheFittest(geneLenght, targetStr, pi, trainingDataset);
//    		case clusterBasedCO:	
//    			return new GeneticAlgorithm_CO().getTheFittest(geneLenght, targetStr, pi, trainingDataset);
//    		default:
//    			return new GeneticAlgorithm_Default().getTheFittest(geneLenght, targetStr, pi, trainingDataset);
//    	}	
//    }
    
    // Evolve a population
    protected Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.size(), false, iid, target,pi,trainingDataset,sid);

        // Keep our best individual
        if (elitism) {
            newPopulation.saveIndividual(0, pop.getFittest());
        }

        // Crossover population
        int elitismOffset;
        if (elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        // Loop over the population size and create new individuals with
        // crossover
        for (int i = elitismOffset; i < pop.size(); i++) {
            Individual indiv1 = tournamentSelection(pop);
            Individual indiv2 = tournamentSelection(pop);
            Individual newIndiv = indiv1.crossover(indiv2, uniformRate);
            newPopulation.saveIndividual(i, newIndiv);
        }

        // Mutate population
        for (int i = elitismOffset; i < newPopulation.size(); i++) {
        	newPopulation.getIndividual(i).mutate(mutationRate);
        }

        return newPopulation;
    }

    // Select individuals for crossover
    protected Individual tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(tournamentSize, false, iid, target,pi,trainingDataset,sid);
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            tournament.saveIndividual(i, pop.getIndividual(randomId));
        }
        // Get the fittest
        Individual fittest = tournament.getFittest();
        return fittest;
    }

}
