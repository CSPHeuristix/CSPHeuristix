package at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual;

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
		for(int m=0;m<this.trainingDataset.length;m++)
			for (int i = 0; i < this.getGeneLength()-1; i++) {
	            if (Math.random() <= mutationRate) {
	                Const swap = this.trainingDataset[m].getREQ()[i];
	            	this.trainingDataset[m].setOneREQ(i, this.trainingDataset[m].getREQ()[i+1]);
	            	this.trainingDataset[m].setOneREQ(i+1,swap);
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
		// SHUFFLE REQs
		for(int m=0;m<this.trainingDataset.length;m++){
			Const [] shuffled = shuffleArray(this.trainingDataset[m].getREQ()).clone();
			this.trainingDataset[m].setREQ(shuffled);
		}
		
	}
	
}

