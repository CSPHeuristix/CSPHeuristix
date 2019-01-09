package at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual;

import java.util.Arrays;
import java.util.stream.IntStream;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc_CO;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc_Default;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc_VVO;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.solvers.Const;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

/** Represents an Individual of a Genetic Algorithm for learning Constraint Ordering 
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/
public class Individual_CO extends Individual{


	public Individual_CO(CSP[] trainingDataset, HeuristicID hi, SolverID sid, PerformanceIndicator pi, DiagnoserID did,
			int m) {
		super(trainingDataset, hi, sid, pi, did, m);
		fitnessCalc= new FitnessCalc_CO(this, target, pi, hi, trainingDataset, sid,did, m);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public Individual crossover(Individual indiv2, double uniformRate) {
		// TODO Auto-generated method stub
		
		// NO CHANGES IN CONSTRAINT ORDERING DURING CROSSOVER
		return this;
	}

	@Override
	public void mutate(double mutationRate) {
		// TODO Auto-generated method stub
		
			for (int i = 0; i < this.getGeneLength()-1; i++) {
	            if (Math.random() <= mutationRate) {
	                // Update constraint ordering 
	            	int swapIndex =  this.variableOrdering[i];
	            	this.variableOrdering[i] = this.variableOrdering[i+1];
	            	this.variableOrdering[i+1] = swapIndex;
	            	
	            	
	            	// Update constraints in TrainingDataset 
	            	for(int m=0;m<this.trainingDataset.length;m++){
		                Const swap = this.trainingDataset[m].getREQ()[i];
		            	this.trainingDataset[m].setOneREQ(i, this.trainingDataset[m].getREQ()[i+1]);
		            	this.trainingDataset[m].setOneREQ(i+1,swap);
	            	}
	            	
	            }
	        }
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected void generateIndividual() {
		this.variableOrdering = new int [this.numberOfVars]; // constraint ordering
		//Arrays.setAll(variableOrdering, i -> i + 1);
		variableOrdering = IntStream.rangeClosed(0, numberOfVars-1).toArray();
		
		// Update constraint ordering 
		this.variableOrdering = shuffleArray(variableOrdering).clone(); 
	
		// Update trainingDataset
		for(int m=0;m<this.trainingDataset.length;m++){
			Const [] shuffledReqs= new Const[this.numberOfVars];
			for(int i=0;i<shuffledReqs.length;i++)
				shuffledReqs[i]=this.trainingDataset[m].getREQ()[this.variableOrdering[i]];
			this.trainingDataset[m].setREQ(shuffledReqs);
		}
		
	}
	
}

