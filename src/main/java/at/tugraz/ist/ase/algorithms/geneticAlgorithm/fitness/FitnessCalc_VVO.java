package at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.solvers.Solver;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

/** Represents Fitness Calculation of a Genetic Algorithm for learning Variable and Value Ordering
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public class FitnessCalc_VVO extends FitnessCalc{

	public FitnessCalc_VVO(Individual individual, String target, PerformanceIndicator pi, HeuristicID hi,
			CSP[] trainingDataset, SolverID sid, DiagnoserID did,int m) {
		super(individual, target, pi, hi, trainingDataset, sid, did,m);
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public int getMaxFitness() {
		// TODO Auto-generated method stub
		// not yet supported
		return 0;
	}
	
	/////////////////////////////////////////////////
	/////////////////////////////////////////////////
	/////////////    PRIVATE          ///////////////
	/////////////////////////////////////////////////
	/////////////////////////////////////////////////
	
	@Override
	protected float getRuntimeFitness(){
		long start=System.nanoTime();
		
		for (int i=0;i<trainingDataset.length;i++){
			Solver s= new Solver();
			s.solveCSP(trainingDataset[i], this.sid, this.individual); 
		}
		long stop=System.nanoTime();
		
		long time=stop-start;
		
		return (float)((float)1/(float)(time));
	}
	
	@Override
	protected float getpredictionQualityFitness(){
		float avg_predictionQuality=0;
		 
		for (int i=0;i<trainingDataset.length;i++){
			Solver s= new Solver();
			s.solveCSP(trainingDataset[i], this.sid, this.individual); 
			avg_predictionQuality += trainingDataset[i].getPredictionQuality();
		}
		
		avg_predictionQuality = avg_predictionQuality/trainingDataset.length;
		
		return avg_predictionQuality;
	}

}
