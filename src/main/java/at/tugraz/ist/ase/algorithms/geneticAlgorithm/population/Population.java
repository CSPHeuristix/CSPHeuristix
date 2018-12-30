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
    String target=null;
    PerformanceIndicator pi = PerformanceIndicator.runtime;
    int geneLenght;
    SolverID sid;

    /*
     * Constructors
     */
    // Create a population
    public Population(int populationSize, boolean initialise, IndividualID iid, String target,PerformanceIndicator pi, CSP[] trainingDataset,SolverID sid) {
    	this.pi = pi;
    	this.sid=sid;
    	individuals = new Individual[populationSize];
        this.target = target;
        // Initialise population
        if (initialise) {
            // Loop and create individuals
            for (int i = 0; i < size(); i++) {
            	//newIndividual = new Individual(this.geneLenght,trainingDataset,HeuristicID.clusterBasedVVO,sid);
    			
            	switch(iid){
            		case vvo:
            			individuals[i] = new Individual_VVO(trainingDataset,HeuristicID.clusterBasedVVO,sid, pi);
            			//newIndividual.instantiate(this.geneLenght, trainingDataset);
            			break;
            		case co:
            			individuals[i] = new Individual_CO(trainingDataset,HeuristicID.clusterBasedCO,sid, pi);
            			break;
            		case binary:
            			individuals[i] = new Individual_Default(target, null, null,sid, pi);
            			break;
            		default:
            			individuals[i] = new Individual_Default(target, null, null,sid, pi);
            			break;
            			
            	}
            
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

    public void saveIndividual(int i, Individual ind){
    	individuals[i] = ind;
    }

}
