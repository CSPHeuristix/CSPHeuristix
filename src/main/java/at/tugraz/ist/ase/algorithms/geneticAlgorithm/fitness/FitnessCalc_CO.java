package at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

public class FitnessCalc_CO extends FitnessCalc{

	FitnessCalc_CO(Individual individual, String target, PerformanceIndicator pi, HeuristicID hi,
			CSP[] trainingDataset, SolverID sid) {
		super(individual, target, pi, hi, trainingDataset, sid);
		// TODO Auto-generated constructor stub
	}

	@Override
	public float getFitness() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxFitness() {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
