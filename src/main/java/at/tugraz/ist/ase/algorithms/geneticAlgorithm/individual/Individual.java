package at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc_CO;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc_Default;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc_VVO;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

/** Represents an Abstract Individual of a Genetic Algorithm
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public abstract class Individual{
	
	public int [] variableOrdering;
	public int [][] valueOrdering;
	
	
	protected CSP[] trainingDataset;
	protected int geneLength;
	protected HeuristicID hi;
	protected SolverID sid;
	protected PerformanceIndicator pi;
	protected DiagnoserID did; 
	protected int m;
	protected String target;
	protected int numberOfVars;
	protected float fitness=0;
	FitnessCalc fitnessCalc;
	
	
	public Individual(CSP[] trainingDataset, HeuristicID hi, SolverID sid, 
			PerformanceIndicator pi, DiagnoserID did, int m){
		
		this.trainingDataset = trainingDataset.clone();
		this.hi=hi;
		this.sid=sid;
		this.pi=pi;
		this.did=did;
		this.m=m;
		
		this.geneLength = trainingDataset[0].getVars().length;
		this.numberOfVars=this.geneLength;
		
		generateIndividual();
		//fitnessCalc= new FitnessCalc_Default(this, target, pi, hi, trainingDataset, sid,did, m);
		
	}
	

	protected abstract void generateIndividual();


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

	public float getFitness(){
		if(fitness!=0)
			return fitness;
		
		fitness = fitnessCalc.getFitness();
		return fitness;
	}
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

	<T>T[] shuffleArray(T [] ar)
	  {
	    // If running on Java 6 or older, use `new Random()` on RHS here
	    Random rnd = ThreadLocalRandom.current();
	    for (int i = ar.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      T a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	    return ar;
	  }
	
	int[] shuffleArray(int [] ar)
	  {
	    // If running on Java 6 or older, use `new Random()` on RHS here
	    Random rnd = ThreadLocalRandom.current();
	    for (int i = ar.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      int a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	    return ar;
	  }

}