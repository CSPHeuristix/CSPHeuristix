package at.tugraz.ist.ase.algorithms.geneticAlgorithm.population;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_CO;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_Default;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_VVO;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.IndividualID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

public class Population {

    Individual[] individuals;
    Individual target=null;
    PerformanceIndicator pi = PerformanceIndicator.runtime;
    int geneLenght;
    SolverID sid;

    /*
     * Constructors
     */
    // Create a population
    public Population(int populationSize, boolean initialise, IndividualID iid, Individual target,PerformanceIndicator pi, CSP[] trainingDataset,SolverID sid) {
    	this.pi = pi;
    	this.sid=sid;
    	this.geneLenght = target.getGeneLength();
        individuals = new Individual[populationSize];
        this.target = target;
        // Initialise population
        if (initialise) {
            // Loop and create individuals
            for (int i = 0; i < size(); i++) {
            	Individual newIndividual=null;
            	//newIndividual = new Individual(this.geneLenght,trainingDataset,HeuristicID.clusterBasedVVO,sid);
    			
            	switch(iid){
            		case vvo:
            			newIndividual = new Individual_VVO(this.geneLenght,trainingDataset,HeuristicID.clusterBasedVVO,sid, pi);
            			//newIndividual.instantiate(this.geneLenght, trainingDataset);
            			break;
            		case co:
            			newIndividual = new Individual_CO(this.geneLenght,trainingDataset,HeuristicID.clusterBasedCO,sid, pi);
            			break;
            		case binary:
            			newIndividual = new Individual_Default(this.geneLenght, null, null,sid, pi);
            			break;
            		default:
            			newIndividual = new Individual_Default(this.geneLenght, null, null,sid, pi);
            			break;
            			
            	}
            	//newIndividual.generateIndividual();
                saveIndividual(i, newIndividual);
            }
        }
    }

    /* Getters */
    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public Individual getFittest() {
    	Individual fittest = individuals[0];
        // Loop through individuals to find fittest
        for (int i = 0; i < size(); i++) {
            if (fittest.getFitness(target,pi) <= getIndividual(i).getFitness(target,pi)) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }

    /* Public methods */
    // Get population size
    public int size() {
        return individuals.length;
    }

    // Save individual
    public void saveIndividual(int index, Individual indiv) {
        individuals[index] = indiv;
    }
}
