package at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_Default;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

public class FitnessCalc_Default extends FitnessCalc{


	public FitnessCalc_Default(Individual individual, String target, PerformanceIndicator pi, HeuristicID hi,
			CSP[] trainingDataset, SolverID sid) {
		super(individual, target, pi, hi, trainingDataset, sid);
		// TODO Auto-generated constructor stub
	}

	@Override
	public float getFitness() {
		// TODO Auto-generated method stub
		int fitness = 0;
        // Loop through our individuals genes and compare them to our candidates
        for (int i = 0; i < individual.getGeneLength() && i < target.length(); i++) {
            if (((Individual_Default)individual).getGene(i) == target.indexOf(i)) {
                fitness++;
            }
        }
        return (float)fitness;
	}

	@Override
	public int getMaxFitness() {
		// TODO Auto-generated method stub
		 int maxFitness = target.length();
	     return maxFitness;
	}
	
	/////////////////////////////////////////////////
	/////////////////////////////////////////////////
	/////////////    PRIVATE          ///////////////
	/////////////////////////////////////////////////
	/////////////////////////////////////////////////

    
    // To make it easier we can use this method to set our candidate solution 
    // with string of 0s and 1s
    private Individual stringToTarget(String newSolution, String trgt) {
    	Individual_Default target = new Individual_Default(trgt,null, null, sid,pi);
        // Loop through each character of our string and save it in our byte 
        // array
        for (int i = 0; i < newSolution.length(); i++) {
            String character = newSolution.substring(i, i + 1);
            if (character.contains("0") || character.contains("1")) {
                target.setGene(i, Byte.parseByte(character));
            } else {
                target.setGene(i, (byte)0);
            }
        }
        return target;
    }


}


