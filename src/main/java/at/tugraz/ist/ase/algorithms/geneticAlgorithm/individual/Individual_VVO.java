package at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc_Default;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc_VVO;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

public class Individual_VVO extends Individual{
	

	public Individual_VVO(int geneLength, CSP[] trainingDataset, HeuristicID hi,SolverID sid, PerformanceIndicator pi) {
		super(geneLength, trainingDataset, hi,sid, pi);
		initiate(geneLength, trainingDataset);
		fitnessCalc = new FitnessCalc_VVO(this, target, pi, hi, trainingDataset, sid);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public Individual crossover(Individual indiv2, double uniformRate) {
		// TODO Auto-generated method stub
		// crossover value orderings
		
		Individual_VVO newSol = new Individual_VVO(indiv2.getGeneLength(),trainingDataset, hi, sid, pi);
				//newSol.instantiate(numberOfVars, trainingDataset);
		for (int i = 0; i < this.getGeneLength(); i++) {
		            // Crossover
		            if (Math.random() <= uniformRate) {
		                newSol.valueOrdering[i] = this.valueOrdering[i].clone();
		            } else {
		               newSol.valueOrdering[i] = ((Individual_VVO)indiv2).valueOrdering[i].clone();
		            }
		 }
		 return (Individual)newSol;
	}

	@Override
	public void mutate(double mutationRate) {
		// TODO Auto-generated method stub
		for (int i = 0; i < this.getGeneLength()-1; i++) {
            if (Math.random() <= mutationRate) {
                int swap = this.variableOrdering[i];
            	this.variableOrdering[i]=this.variableOrdering[i+1];
            	this.variableOrdering[i+1]=swap;
            }
        }
		
	}

	@Override
	public float getFitness(Individual target, PerformanceIndicator pi) {
		// TODO Auto-generated method stub
		if(fitness!=0)
			return fitness;
		
		fitness = fitnessCalc.getFitness();
		return fitness;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	///////////////////////////////////////////////
	///////////////////////////////////////////////
	///////////////////////////////////////////////
	///////////////////////////////////////////////
	
	private void initiate(int defaultGeneLength, CSP[] trainingDataset) {
		//super(defaultGeneLength,trainingDataset);
		this.numberOfVars = numberOfVars;
		variableOrdering = new int [numberOfVars];
		for(int i=0;i<numberOfVars;i++)
			variableOrdering[i]=i;
			
		int [][] domains = new int [numberOfVars][];
		for (int i=0;i<numberOfVars;i++){
				domains[i][0]= trainingDataset[0].getVars()[i].getMinDomain();
				domains[i][1]= trainingDataset[0].getVars()[i].getMaxDomain();
		}
		
		generateIndividual();
	}

	private void initiate(int numberOfVars, int [][] domains){
		this.numberOfVars = numberOfVars;
		variableOrdering = new int [numberOfVars];
		for(int i=0;i<numberOfVars;i++)
			variableOrdering[i]=i;
			
		for(int i=0;i<numberOfVars;i++){
			valueOrdering[i]=new int[domains[i].length];
			valueOrdering[i]=domains[i].clone();
		}
	}

	private void generateIndividual() {
		// TODO Auto-generated method stub
		this.variableOrdering = shuffleArray(this.variableOrdering);
		for(int i=0;i<numberOfVars;i++)
			this.valueOrdering[i]=shuffleArray(this.valueOrdering[i]);
	}

	
	int[] shuffleArray(int[] ar)
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
