package at.tugraz.ist.ase.cspheuristix;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.*;

import at.tugraz.ist.ase.diagnosers.Diagnoser;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.solvers.Const;
import at.tugraz.ist.ase.solvers.Solver;
import at.tugraz.ist.ase.solvers.Var;
import at.tugraz.ist.ase.util.ClusteringAlgorithmID;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

/** Represents the entry point of the CSPHeuristix Library
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/
public class Library {
	
	private static final Logger logger = Logger.getLogger(Library.class);
	
	HeuristicID hid;
	SolverID sid;
	ClusteringAlgorithmID cid;
	int numberOfClusters;
	DiagnoserID did;
	String inputFolder;
	Heuristics heuristix;
	String outputFolder;
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
	
	/**
	   * Library is the entry point of the CSPHeuristix Library.
	   * The Library constructor collects all required parameters and initiates the heuristics learning phase. 
	   * @param heuristicsID is the selected heuristics method to solve configuration/diagnosis tasks
	   * @param solverID is the selected CSP solver which is used during heuristics learning and solving tasks
	   * @param diagnosisAlgorithmID is the selected diagnosis algorithm which is used for diagnosis tasks
	   * @param inputFolder is the folder which holds all required input files
	   * Required input files for configuration tasks: basisCSP, newConsistentReqs, pastConsistentReqs, pastSolutions
	   * Required input files for diagnosis tasks: basisCSP, newInconsistentReqs, pastInconsistentReqs, pastDiagnoses
	   * @param outputFolder is the folder which holds all produced output files (clustering results, generated rating files, etc.)
	   * @param pi is the performance indicator which is optimized during heuristics learning
	   * @param stoppingCriteria is the time in milliseconds as stopping criterion for genetic algorithm.  
	   * @param cid is the clustering algorithm to be used in cluster-based heuristics learning methods
	   * @param numberOfClusters is the number of clusters to be used in cluster-based heuristics learning methods
	   * @param m is required for solving diagnosis tasks when diagnosisAlgorithmID=DiagnoserID.flexdiag
	   */
	public Library(HeuristicID heuristicsID, SolverID solverID, DiagnoserID diagnosisAlgorithmID, 
			String inputFolder, String outputFolder, PerformanceIndicator pi, 
			String stoppingCriteria, ClusteringAlgorithmID cid, int numberOfClusters, int m){
		
		this.hid= heuristicsID;
		this.sid= solverID;
		this.did= diagnosisAlgorithmID;
		this.inputFolder= inputFolder;
		this.cid=cid;
		this.numberOfClusters=numberOfClusters;
		this.outputFolder=outputFolder;
		
		try{
			logger.info("\n####################################################");
			logger.info("START ("+dateFormat.format(date)+")");
			logger.info("heuristicsID: "+ heuristicsID);
			logger.info("solverID: "+ solverID);
			logger.info("ClusteringAlgorithmID: "+ cid);
			logger.info("PerformanceIndicator: "+ pi);
			logger.info("stoppingCriteria: "+ stoppingCriteria);
			logger.info("diagnosisAlgorithmID: "+ diagnosisAlgorithmID);
			logger.info("inputFile: "+ inputFolder);
			logger.info("outputFolder: "+ outputFolder);
			
			switch (heuristicsID) {
		         case CLVVO:
		        	 heuristix = new CLVVO(heuristicsID, solverID, diagnosisAlgorithmID, inputFolder, outputFolder, pi, stoppingCriteria,cid,numberOfClusters,m);
		        	 break;
		                 
		         case CLCO:
		        	 heuristix = new CLCO(heuristicsID, solverID, diagnosisAlgorithmID, inputFolder, outputFolder, pi, stoppingCriteria,cid,numberOfClusters,m);
		             break;
		                      
		         case MFCO: 
		        	 heuristix = new MFCO(heuristicsID, solverID, diagnosisAlgorithmID, inputFolder, outputFolder, pi, stoppingCriteria,cid,numberOfClusters,m);
		             break;
		                     
		         case MFVVO:
		        	 heuristix = new MFVVO(heuristicsID, solverID, diagnosisAlgorithmID, inputFolder, outputFolder, pi, stoppingCriteria,cid,numberOfClusters,m);
		             break;
		             
		         default:
		        	 break;
		     }
			
			
			heuristix.learn();
			
			String heuristics = "";
			for(int i=0;i<heuristix.learnedHeuristics.length;i++){
				heuristics += "Heuristics-"+i+": "+heuristix.learnedHeuristics[i].toString()+"\n";
			}
			
			logger.info("LEARNED HEURISTICS: \n"+heuristics);	
			
		}catch(Exception ex){
			logger.error(ex.toString());
		}
		
			
	}
	
	/**
	   * This method is used to solve the new configuration/diagnosis tasks which are defined in the input file: newReqs.
	   * These tasks are solved using the learned heuristics in the Library constructor.
	   * @return CSP[] This method returns an array of CSPs which hold solutions of the given tasks in newReqs.
	   * Each CSP holds the heuristics to solve it, spent time for solving this CSP based configuration/diagnosis task, and the prediction quality (if their solutions are already given in the input file: newReqs) 
	   */
	public CSP[] solveTasks (){
		//CSP [] newTasks = heuristix.generateNewCSPs();
		CSP [] solutions = new CSP [heuristix.newTasks.length];
		
		switch (hid) {
	        case CLVVO:
	        case MFVVO:
	        	for (int i=0;i<solutions.length;i++){
	    			solutions[i] = heuristix.solveTask(heuristix.newTasks[i]);
	    		}
	        	break;
	                
	        case CLCO:
	        case MFCO: 
	        	for (int i=0;i<heuristix.newTasks.length;i++){
	        		solutions[i] = heuristix.diagnoseTask(heuristix.newTasks[i]);
	    		}
	       	 	break;
	    }
		
		String solutionsStr="";
		for(int i=0;i<solutions.length;i++)
			solutionsStr+="\n"+solutions[i].toString();
		
		logger.info("RESULTS:"+solutionsStr);	
		logger.info("END ("+dateFormat.format(date)+")");
		logger.info("\n####################################################\n");
		return solutions;
	}
//	
//	public Const[][] diagnoseTasks (){
//		CSP [] newTasks = heuristix.generateNewCSPs();
//		Const[][] diagnoses = new Const[newTasks.length][];
//		
//		for (int i=0;i<newTasks.length;i++){
//			diagnoses[i] = heuristix.diagnoseTask(newTasks[i]);
//		}
//		String solutionsStr="";
//		for(int i=0;i<diagnoses.length;i++){
//			solutionsStr+="\n";
//			for(int j=0;j<diagnoses[i].length;j++)
//				solutionsStr+= diagnoses[i][j].toString()+";";
//		}
//		
//		logger.info("DIAGNOSES:"+solutionsStr);	
//		logger.info("END ("+dateFormat.format(date)+")");
//		logger.info("\n####################################################\n");
//		return diagnoses;
//	}
//	
	
}