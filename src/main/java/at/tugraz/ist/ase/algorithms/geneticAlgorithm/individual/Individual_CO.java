package at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual;

import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

public class Individual_CO extends Individual{

	public Individual_CO(CSP[] trainingDataset, HeuristicID hi, SolverID sid, PerformanceIndicator pi) {
		super(trainingDataset, hi, sid, pi);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Individual crossover(Individual indiv2, double uniformRate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mutate(double mutationRate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public float getFitness(String target, PerformanceIndicator pi) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
