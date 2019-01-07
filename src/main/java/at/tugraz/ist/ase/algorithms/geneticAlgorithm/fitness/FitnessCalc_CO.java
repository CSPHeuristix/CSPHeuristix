package at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual;
import at.tugraz.ist.ase.diagnosers.Diagnoser;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.solvers.Const;
import at.tugraz.ist.ase.solvers.Solver;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

/** Represents Fitness Calculation of a Genetic Algorithm for learning Constraint Ordering
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public class FitnessCalc_CO extends FitnessCalc{
	
	Const[][] diagnosisArray;

	public FitnessCalc_CO(Individual individual, String target, PerformanceIndicator pi, HeuristicID hi,
			CSP[] trainingDataset, SolverID sid, DiagnoserID did, int m) {
		super(individual, target, pi, hi, trainingDataset, sid, did, m);
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
	protected float getRuntimeFitness() {
		// TODO Auto-generated method stub
		long start=System.nanoTime();
		diagnosisArray = new Const[trainingDataset.length][];
		
		for (int i=0;i<trainingDataset.length;i++){
			CSP task = trainingDataset[i];
			Diagnoser d = new Diagnoser();
			diagnosisArray[i] = d.diagnose(task.getVars(), sid, task.getREQ(), task.getAC(), m, this.did);
		}
		long stop=System.nanoTime();
		
		long time=stop-start;
		
		return (float)((float)1/(float)(time));
	}

	@Override
	protected float getpredictionQualityFitness() {
		// TODO SEDA
		return 0;
	}


}
