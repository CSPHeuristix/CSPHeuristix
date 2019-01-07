package at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

public abstract class FitnessCalc{

	SolverID sid;
	PerformanceIndicator pi;
	HeuristicID hi;
	Individual individual;
	String target;
	CSP[] trainingDataset;
	DiagnoserID did;
	int m;

	FitnessCalc(Individual individual, String target2,PerformanceIndicator pi, HeuristicID hi, CSP[] trainingDataset, SolverID sid, DiagnoserID did,int m){
		this.individual = individual;
		this.target=target2;
		this.pi=pi;
		this.hi=hi;
		this.trainingDataset=trainingDataset.clone();
		this.sid=sid;
		this.did = did;
		this.m=m;
	}
	
	public float getFitness(){
		switch(pi){
			case runtime:
				return getRuntimeFitness();
			case predictionQuality:
				return getpredictionQualityFitness();
			default:
				return getRuntimeFitness();
		}
	}
		
		
	 // {
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
	public abstract int getMaxFitness(); //{
//    	switch(hi){
//			case clusterBasedVVO:
//				return new FitnessCalc_VVO().getMaxFitness(target, hi);
//			case clusterBasedCO:	
//				return new FitnessCalc_CO().getMaxFitness(target, hi);
//			default:
//				return new FitnessCalc_Default().getMaxFitness(target, hi);
//    	}	
//    }
	
	protected abstract float getRuntimeFitness();	
	protected abstract float getpredictionQualityFitness();


}


