package at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc_CO;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc_Default;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc_VVO;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

public abstract class Individual{
	
	protected int geneLength;
	protected CSP[] trainingDataset;
	protected HeuristicID hi;
	protected SolverID sid;
	protected PerformanceIndicator pi;
	protected String target;
	
	protected int [] variableOrdering;
	protected int [][] valueOrdering;
	protected int numberOfVars;
	protected float fitness=0;
	FitnessCalc fitnessCalc;
	
	
	
	public Individual(int geneLength, CSP[] trainingDataset, HeuristicID hi, SolverID sid, PerformanceIndicator pi){
		this.geneLength = geneLength;
		this.trainingDataset = trainingDataset.clone();
		this.hi=hi;
		this.sid=sid;
		this.pi=pi;
	}
	

	public void setDefaultGeneLength(int length) {
		// TODO Auto-generated method stub
		this.geneLength=length;
		
	}

	public int getGeneLength() {
		// TODO Auto-generated method stub
		return this.geneLength;
	}
	
	// Crossover individuals
	public abstract Individual crossover(Individual indiv2, double uniformRate);
//	{
//		switch(hi){
//			case clusterBasedVVO:
//				return new Individual_VVO(geneLength, trainingDataset, hi, sid, pi).crossover_VVO(indiv2, uniformRate);
//			case clusterBasedCO:	
//				return new Individual_CO(geneLength, trainingDataset, hi, sid, pi).crossover_CO(indiv2, uniformRate);
//			default:
//				return new Individual_Default(geneLength, trainingDataset, hi, sid, pi).crossover_Def(indiv2, uniformRate);
//		}	
//	}

    // Mutate an individual
	public abstract void mutate(double mutationRate);
//	{
//		switch(hi){
//			case clusterBasedVVO:
//				 new Individual_VVO(geneLength, trainingDataset, hi, sid,pi).mutate_VVO(mutationRate);
//				 break;
//			case clusterBasedCO:	
//				 new Individual_CO(geneLength, trainingDataset, hi, sid,pi).mutate_CO(mutationRate);
//				 break;
//			default:
//				 new Individual_Default(geneLength, trainingDataset, hi, sid,pi).mutate_Def(mutationRate);
//				 break;
//		}	
//	}

	public abstract float getFitness(Individual target, PerformanceIndicator pi);
//    {
//    	switch(hi){
//			case clusterBasedVVO:
//				return new Individual_VVO(geneLength, trainingDataset, hi, sid,pi).getFitness_VVO(target, pi);
//			case clusterBasedCO:	
//				return new Individual_CO(geneLength, trainingDataset, hi, sid,pi).getFitness_CO(target, pi);
//			default:
//				return new Individual_Default(geneLength, trainingDataset, hi, sid,pi).getFitness_Def(target, pi);
//		}
//    	
//    }
	public abstract String toString();
//	{
//    	switch(hi){
//			case clusterBasedVVO:
//				return null;
//			case clusterBasedCO:	
//				return null;
//			default:
//				return new Individual_Default(geneLength, trainingDataset, hi, sid,pi).toString();
//		}
//    }


//	switch(hi){
//		case clusterBasedVVO:
//			new Individual_VVO(geneLength, trainingDataset);
//		case clusterBasedCO:
//			new Individual_CO(geneLength, trainingDataset);
//		default:
//			new Individual_Default(geneLength);
//	}
}