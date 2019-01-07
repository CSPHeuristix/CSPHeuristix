package at.tugraz.ist.ase.algorithms.geneticAlgorithm.population;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_CO;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_Default;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_VVO;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.IndividualID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

/** Represents a Population of a Genetic Algorithm 
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/
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
    public Population(int populationSize, boolean initialise, IndividualID iid, String target,PerformanceIndicator pi, CSP[] trainingDataset,SolverID sid, DiagnoserID did, int m) {
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
            			individuals[i] = new Individual_VVO(trainingDataset,HeuristicID.clusterBasedVVO,sid, pi,did,m);
            			//newIndividual.instantiate(this.geneLenght, trainingDataset);
            			break;
            		case co:
            			individuals[i] = new Individual_CO(trainingDataset,HeuristicID.clusterBasedCO,sid, pi,did,m);
            			break;
            		case binary:
            			individuals[i] = new Individual_Default(target, null, null,sid, pi,did,m);
            			break;
            		default:
            			individuals[i] = new Individual_Default(target, null, null,sid, pi,did,m);
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
            if (fittest.getFitness() <= getIndividual(i).getFitness()) {
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
