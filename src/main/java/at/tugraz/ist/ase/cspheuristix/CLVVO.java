package at.tugraz.ist.ase.cspheuristix;

import at.tugraz.ist.ase.algorithms.Clustering;
import at.tugraz.ist.ase.algorithms.KNN;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.GeneticAlgorithm_VVO;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_VVO;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.solvers.Const;
import at.tugraz.ist.ase.solvers.Solver;
import at.tugraz.ist.ase.util.ClusteringAlgorithmID;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.FileOperations;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

/** Represents Cluster Based Variable and Value Ordering Heuristics for Constraint Solving
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/
class CLVVO extends Heuristics{
	
	int [][] clusteredItems;
	CSP [][] trainingDatasetClustered;

	CLVVO(HeuristicID heuristicsID, SolverID solverID, DiagnoserID diagnosisAlgorithmID, String inputFile,
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
//	CSP[][] trainingDataset;
//	Individual_VVO[] learnedHeuristics;
//	int [][] clusteredItems;
//	String stoppingCriteria;
//	CSP [] pastCSPs;
//	////////////////////////
	
	@Override
	protected void learn() {
		
		// get number of variables from inputFile
		int numberOfvars = FileOperations.readFile(pastSolutionsFile).get(0).split(",").length-1;
		//basisTask = generateBasisTask(inputFile);
		// pastCSPs = generatePastCSPs();
		
		// Step-1: Cluster past user requirements of the same CSP tasks
		Clustering clustering = new Clustering();
		clusteredItems = clustering.cluster(cid, pastSolutionsFile, outputFolder, numberOfvars, k);
		trainingDatasetClustered = new CSP[k][];
		
		for (int i=0;i<k;i++){
			trainingDatasetClustered[i]=new CSP[clusteredItems[i].length];
			for(int j=0;j<clusteredItems[i].length;j++)
				trainingDatasetClustered[i][j]=trainingDataset[clusteredItems[i][j]];
		}
		learnedHeuristics = new Individual_VVO[k];
			
		// Step-2: Learn Heuristics for Clusters -> learnedHeuristics
		for(int i=0;i<k;i++){
			learnedHeuristics[i] = (Individual_VVO)(new GeneticAlgorithm_VVO(numberOfvars, stoppingCriteria, pi, trainingDatasetClustered[i], hid, sid,did,m).getTheFittestIndividual());
		}
	}
	
	@Override
	protected CSP solveTask(CSP task) {
		// TODO Auto-generated method stub
		
		// Step-3: Find nearest cluster
		KNN knn = new KNN();
		int index = knn.findClosestCluster(clusteredItems, task.getREQs());
		
		
		// Step-4: Solve with the heuristics of the nearest cluster
		Solver s = new Solver();
		CSP solution = s.solveCSP(task, sid, learnedHeuristics[index]);
		
		return solution;
	}

	@Override
	protected CSP diagnoseTask(CSP task) {
		// TODO Auto-generated method stub
		
		// This method is not supported
		
		return null;
	}

	
}
