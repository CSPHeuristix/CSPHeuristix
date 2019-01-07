package at.tugraz.ist.ase.cspheuristix;

import at.tugraz.ist.ase.algorithms.Clustering;
import at.tugraz.ist.ase.algorithms.KNN;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.GeneticAlgorithm_CO;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_CO;
import at.tugraz.ist.ase.diagnosers.Diagnoser;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.solvers.Const;
import at.tugraz.ist.ase.solvers.Solver;
import at.tugraz.ist.ase.util.ClusteringAlgorithmID;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.ReadFile;
import at.tugraz.ist.ase.util.SolverID;

/** Represents Cluster Based Constraint Ordering Heuristics for Consistency Based Diagnosis
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

class CLCO extends Heuristics{
	
	int [][] clusteredItems;
	
	CLCO(HeuristicID heuristicsID, SolverID solverID, DiagnoserID diagnosisAlgorithmID, String inputFile,
			String outputFolder, PerformanceIndicator pi, String stoppingCriteria, ClusteringAlgorithmID cid,
			int numberOfClusters, int m) {
		super(heuristicsID, solverID, diagnosisAlgorithmID, inputFile, outputFolder, pi, stoppingCriteria, cid,
				numberOfClusters, m);
		// TODO Auto-generated constructor stub
	}

//	////////////////////////
//	// Default parameters //
//	int k;
//	ClusteringAlgorithmID cid = ClusteringAlgorithmID.kmeans;
//	SolverID sid;
//	DiagnoserID did;
//	CSP[][] trainingDataset;
//	Individual_CO[] learnedHeuristics;
//	int [][] clusteredItems;
//	String stoppingCriteria;
//	CSP [] pastCSPs;
//	int m;
//	////////////////////////

	@Override
	protected void learn() {
		// TODO Auto-generated method stub
		
		
		this.stoppingCriteria=stoppingCriteria;
		// get number of variables from inputFile
		int numberOfvars = new ReadFile().readFile(pastSolutionsFile).get(0).split(",").length-1;
		pastCSPs = generatePastCSPs();
		
		// Step-1: Cluster past inconsistent user requirements of the same CSP tasks
		Clustering clustering = new Clustering();
		clusteredItems = clustering.cluster(cid, pastSolutionsFile, outputFolder, numberOfvars, k);
		trainingDataset = new CSP[k][];
		
		for (int i=0;i<k;i++){
			trainingDataset[i]=new CSP[clusteredItems[i].length];
			for(int j=0;j<clusteredItems[i].length;j++)
				trainingDataset[i][j]=pastCSPs[clusteredItems[i][j]];
		}
		learnedHeuristics = new Individual_CO[k];
			
		// Step-2: Learn Heuristics for Clusters
		for(int i=0;i<k;i++){
			learnedHeuristics[i] = (Individual_CO)(new GeneticAlgorithm_CO(numberOfvars, stoppingCriteria, pi, trainingDataset[i], hid, sid,did,m).getTheFittestIndividual());
		}
		
	}
	
	@Override
	protected CSP solveTask(CSP task) {
		// TODO Auto-generated method stub
		
		// This method is not supported yet
		
		return null;
	}

	@Override
	protected Const[] diagnoseTask(CSP task) {
		// TODO Auto-generated method stub
		Const[] diagnosis = null;
		
		// Step-3: Find nearest cluster 
		KNN knn = new KNN();
		int index = knn.findClosestCluster(clusteredItems, task.getREQs());
		
		// Step-4: order REQs
		Const[] unsorted = task.getREQ().clone();
		Const[] sorted = new Const[unsorted.length];
		for(int i=0;i<unsorted.length;i++){
			int constIndex = learnedHeuristics[index].variableOrdering[i];
			sorted[i]=unsorted[constIndex];
			//sorted[i]=learnedHeuristics[index].variableOrdering.getREQ()[i];
		}
		task.setREQ(sorted);
		
				
		// Step-5: Solve with the heuristics of the nearest cluster
		Diagnoser d = new Diagnoser();
		diagnosis = d.diagnose(task.getVars(), sid, task.getREQ(), task.getAC(), m, this.did);
				//.solveCSP(task, sid, learnedHeuristics[index]);
				
		return diagnosis;	
	}

	
}
