package at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.solvers.Solver;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

public class FitnessCalc_VVO extends FitnessCalc{

	@Override
	public float getFitness(Individual individual, Individual target, PerformanceIndicator pi, HeuristicID hi,
			CSP[] trainingDataset, SolverID sid) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				switch(pi){
					case runtime:
						return getRuntimeFitness();
					case predictionQuality:
						return getpredictionQualityFitness();
					default:
						return getRuntimeFitness();
				}
	}

	@Override
	public int getMaxFitness(Individual target, HeuristicID hi) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/////////////////////////////////////////////////
	/////////////////////////////////////////////////
	/////////////    PRIVATE          ///////////////
	/////////////////////////////////////////////////
	/////////////////////////////////////////////////
	
	private float getRuntimeFitness(){
		long start=System.currentTimeMillis();
		
		for (int i=0;i<trainingDataset.length;i++){
			Solver s = new Solver();
			s.solveCSP(trainingDataset[i], this.sid); 
		}
		long stop=System.currentTimeMillis();
		
		return 1/(stop-start);
	}
	
	private float getpredictionQualityFitness(){
		// TODO SEDA
		return 0;
	}

}
