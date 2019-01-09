package at.tugraz.ist.ase.algorithms;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.population.Population;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.IndividualID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

/** Represents an Abstract Genetic Algorithm
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public abstract class GeneticAlgorithm {
	
	/* GA parameters */
	private double uniformRate = 0.5;
	private double mutationRate = 0.015;
	private int tournamentSize = 5;
	private boolean elitism = true;
	private IndividualID iid= IndividualID.vvo;
	private String target =null;
	
	/* other parameters */
	protected PerformanceIndicator pi;
	protected CSP[] trainingDataset;
	protected SolverID sid;
	protected DiagnoserID did; 
	protected int m;
	protected int geneLength;
	protected String targetStr;
	protected HeuristicID hi;

    /* Public methods */
    
	public GeneticAlgorithm(int geneLength, String targetStr,PerformanceIndicator pi, CSP[] trainingDataset, HeuristicID hi,SolverID sid, DiagnoserID did, int m){
		this.geneLength= geneLength;
		this.targetStr=targetStr;
		this.pi=pi;
		this.trainingDataset=trainingDataset;
		this.hi=hi;
		this.sid=sid;
		this.did=did;
		this.m=m;
		
	}
    public abstract Individual getTheFittestIndividual();

    // Evolve a population
    protected Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.size(), false, iid, target,pi,trainingDataset,sid,did,m);

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
        Population tournament = new Population(tournamentSize, false, iid, target,pi,trainingDataset,sid,did,m);
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
