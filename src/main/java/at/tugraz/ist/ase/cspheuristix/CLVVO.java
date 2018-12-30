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
	int k;
	ClusteringAlgorithmID cid = ClusteringAlgorithmID.kmeans;
	SolverID sid;
	CSP[][] trainingDataset;
	Individual_VVO[] learnedHeuristics;
	int [][] clusteredItems;
	String stoppingCriteria;
	CSP [] pastCSPs;
	////////////////////////
	
	
	@Override
	protected void learn(HeuristicID heuristicsID, SolverID solverID, DiagnoserID diagnosisAlgorithmID,
			String inputFile, String outputFolder, PerformanceIndicator pi, String stoppingCriteria ,ClusteringAlgorithmID cid, int numberOfClusters) {
		this.sid=solverID;
		this.cid=cid;
		this.k=numberOfClusters;
		this.stoppingCriteria=stoppingCriteria;
		// get number of variables from inputFile
		int numberOfvars = new ReadFile().readFile(inputFile).get(0).split(",").length-1;
		//basisTask = generateBasisTask(inputFile);
		pastCSPs = generatePastCSPs(inputFile);
		
		// Step-1: Cluster past user requirements of the same CSP tasks
		Clustering clustering = new Clustering();
		clusteredItems = clustering.cluster(cid, inputFile, outputFolder, numberOfvars, k);
		trainingDataset = new CSP[k][];
		
		for (int i=0;i<k;i++){
			trainingDataset[i]=new CSP[clusteredItems[i].length];
			for(int j=0;j<clusteredItems[i].length;j++)
				trainingDataset[i][j]=pastCSPs[clusteredItems[i][j]];
		}
		learnedHeuristics = new Individual_VVO[k];
			
		// Step-2: Learn Heuristics for Clusters
		for(int i=0;i<k;i++){
			learnedHeuristics[i] = (Individual_VVO)(new GeneticAlgorithm_VVO().getTheFittestIndividual(numberOfvars, stoppingCriteria, pi, trainingDataset[i], heuristicsID, solverID));
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

	
}
