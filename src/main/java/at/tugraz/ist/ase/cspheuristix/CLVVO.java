package at.tugraz.ist.ase.cspheuristix;

import at.tugraz.ist.ase.algorithms.Clustering;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.GeneticAlgorithm;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.GeneticAlgorithm_VVO;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_VVO;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.ClusteringAlgorithmID;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.ReadFile;
import at.tugraz.ist.ase.util.SolverID;

class CLVVO extends IHeuristics{

	////////////////////////
	// Default parameters //
	int numberOfClusters=4;
	ClusteringAlgorithmID cid = ClusteringAlgorithmID.kmeans;
	CSP[][] trainingDataset;
	Individual_VVO[] learnedHeuristics;
	////////////////////////
	
	public void setParameters(int numberOfClusters, ClusteringAlgorithmID cid){
		this.cid=cid;
		this.numberOfClusters=numberOfClusters;
	}
	
	
	@Override
	protected void learn(HeuristicID heuristicsID, SolverID solverID, DiagnoserID diagnosisAlgorithmID,
			String inputFile, String outputFolder, CSP basisTask, PerformanceIndicator pi) {
		
		// get number of variables from inputFile
		int numberOfvars = new ReadFile().readFile(inputFile).get(0).split(",").length-1;
		
		// Step-1: Cluster past user requirements of the same CSP tasks
		Clustering clustering = new Clustering();
		int [][] clusteredItems = clustering.cluster(cid, inputFile, outputFolder, numberOfvars, numberOfClusters);
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
		
		// Step-3: Find nearest neighbor
		
		// Step-4: Solve with its heuristics
		
		return null;
	}
	
}
