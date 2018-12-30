package at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

public abstract class FitnessCalc{

	SolverID sid;
	PerformanceIndicator pi;
	Individual individual;
	Individual target;
	CSP[] trainingDataset;

	FitnessCalc(){
		
	}
	public abstract float getFitness(Individual individual, Individual target,PerformanceIndicator pi, HeuristicID hi, CSP[] trainingDataset, SolverID sid); // {
//    	this.sid=sid;
//    	this.pi=pi;
//    	this.individual=individual;
//    	this.target=target;
//    	this.trainingDataset = trainingDataset.clone();
//    	switch(hi){
//			case clusterBasedVVO:
//				return new FitnessCalc_VVO().getFitness();
//			case clusterBasedCO:	
//				return new FitnessCalc_CO().getFitness();
//			default:
//				return new FitnessCalc_Default().getFitness();
//    	}	
//    	
//    }
    
    // Get optimum fitness
	public abstract int getMaxFitness(Individual target, HeuristicID hi); //{
//    	switch(hi){
//			case clusterBasedVVO:
//				return new FitnessCalc_VVO().getMaxFitness(target, hi);
//			case clusterBasedCO:	
//				return new FitnessCalc_CO().getMaxFitness(target, hi);
//			default:
//				return new FitnessCalc_Default().getMaxFitness(target, hi);
//    	}	
//    }


}


