package at.tugraz.ist.ase.cspheuristix;

import at.tugraz.ist.ase.algorithms.Clustering;
import at.tugraz.ist.ase.algorithms.KNN;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.GeneticAlgorithm_VVO;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_VVO;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.solvers.Solver;
import at.tugraz.ist.ase.util.ClusteringAlgorithmID;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.ReadFile;
import at.tugraz.ist.ase.util.SolverID;

class CLVVO extends Heuristics{

	////////////////////////
	// Default parameters //
	int numberOfClusters=4;
	ClusteringAlgorithmID cid = ClusteringAlgorithmID.kmeans;
	CSP[][] trainingDataset;
	Individual_VVO[] learnedHeuristics;
	int [][] clusteredItems;
	////////////////////////
	
	public void setParameters(int numberOfClusters, ClusteringAlgorithmID cid){
		this.cid=cid;
		this.numberOfClusters=numberOfClusters;
	}
	
	@Override
	protected void learn(HeuristicID heuristicsID, SolverID solverID, DiagnoserID diagnosisAlgorithmID,
			String inputFile, String outputFolder, CSP basisTask, PerformanceIndicator pi) {
		this.sid=sid;
		// get number of variables from inputFile
		int numberOfvars = new ReadFile().readFile(inputFile).get(0).split(",").length-1;
		
		// Step-1: Cluster past user requirements of the same CSP tasks
		Clustering clustering = new Clustering();
		clusteredItems = clustering.cluster(cid, inputFile, outputFolder, numberOfvars, numberOfClusters);
		trainingDataset = new CSP[numberOfClusters][];
		learnedHeuristics = new Individual_VVO[numberOfClusters];
			
		// Step-2: Learn Heuristics for Clusters
		for(int i=0;i<numberOfClusters;i++){
			learnedHeuristics[i] = (Individual_VVO)new GeneticAlgorithm_VVO().getTheFittestIndividual(numberOfvars, null, pi, trainingDataset[i], heuristicsID, solverID);
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
		s.solveCSP(task, sid, learnedHeuristics[index]);
		
		return null;
	}

	
}
