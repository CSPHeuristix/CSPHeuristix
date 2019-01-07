package at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc_Default;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc_VVO;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

public class Individual_VVO extends Individual{
	

	public Individual_VVO(CSP[] trainingDataset, HeuristicID hi,SolverID sid, PerformanceIndicator pi, DiagnoserID did, int m) {
		super(trainingDataset, hi,sid, pi,did,m);
		fitnessCalc= new FitnessCalc_VVO(this, target, pi, hi, trainingDataset, sid,did, m);
	}

	
	@Override
	public Individual crossover(Individual indiv2, double uniformRate) {
		// TODO Auto-generated method stub
		// crossover value orderings
		
		Individual_VVO newSol = new Individual_VVO(trainingDataset, hi, sid, pi,did,m);
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

//	@Override
//	public float getFitness(String target, PerformanceIndicator pi) {
//		// TODO Auto-generated method stub
//		if(fitness!=0)
//			return fitness;
//		
//		fitness = fitnessCalc.getFitness();
//		return fitness;
//	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void generateIndividual() {
		// TODO Auto-generated method stub
		variableOrdering = new int [numberOfVars];
		for(int i=0;i<numberOfVars;i++)
			variableOrdering[i]=i;
			
		int [][] domains = new int [numberOfVars][2];
		for (int i=0;i<numberOfVars;i++){
				domains[i][0]= trainingDataset[0].getVars()[i].getMinDomain();
				domains[i][1]= trainingDataset[0].getVars()[i].getMaxDomain();
		}
		for(int i=0;i<numberOfVars;i++){
			valueOrdering[i]=new int[domains[i].length];
			valueOrdering[i]=domains[i].clone();
		}
		
		// SHUFFLE
		variableOrdering = shuffleArray(variableOrdering).clone();
		for(int i=0;i<numberOfVars;i++)
			valueOrdering[i] = shuffleArray(valueOrdering[i]).clone();
	
	}
	
	///////////////////////////////////////////////
	///////////////////////////////////////////////
	///////////////////////////////////////////////
	///////////////////////////////////////////////
	
//	private void initiate(int defaultGeneLength, CSP[] trainingDataset) {
//		//super(defaultGeneLength,trainingDataset);
//		this.numberOfVars = defaultGeneLength;
//		variableOrdering = new int [numberOfVars];
//		for(int i=0;i<numberOfVars;i++)
//			variableOrdering[i]=i;
//			
//		int [][] domains = new int [numberOfVars][2];
//		for (int i=0;i<numberOfVars;i++){
//				domains[i][0]= trainingDataset[0].getVars()[i].getMinDomain();
//				domains[i][1]= trainingDataset[0].getVars()[i].getMaxDomain();
//		}
//		initiate(this.numberOfVars, domains);
//		generateIndividual();
//	}
//
//	private void initiate(int numberOfVars, int [][] domains){
//		this.numberOfVars = numberOfVars;
//		variableOrdering = new int [numberOfVars];
//		valueOrdering = new int [numberOfVars][];
//		for(int i=0;i<numberOfVars;i++)
//			variableOrdering[i]=i;
//			
//		for(int i=0;i<numberOfVars;i++){
//			valueOrdering[i]=new int[domains[i].length];
//			valueOrdering[i]=domains[i].clone();
//		}
//	}

}
