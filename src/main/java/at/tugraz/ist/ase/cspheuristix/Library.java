package at.tugraz.ist.ase.cspheuristix;

/** Represents a Library
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

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
import at.tugraz.ist.ase.util.ReadFile;
import at.tugraz.ist.ase.util.SolverID;

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
	
	public Library(HeuristicID heuristicsID, SolverID solverID, DiagnoserID diagnosisAlgorithmID, String inputFolder, String outputFolder, PerformanceIndicator pi, String stoppingCriteria, ClusteringAlgorithmID cid, int numberOfClusters){
		
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
		         case clusterBasedVVO:
		        	 heuristix = new CLVVO();
		        	 break;
		                 
		         case clusterBasedCO:
		        	 heuristix = new CLCO();
		             break;
		                      
		         case MFBasedCO: 
		        	 heuristix = new MFCO();
		             break;
		                     
		         case MFBasedVVO:
		        	 heuristix = new MFVVO();
		             break;
		             
		         default:
		        	 break;
		     }
			
			
			heuristix.learn(heuristicsID, solverID, diagnosisAlgorithmID, inputFolder, outputFolder, pi, stoppingCriteria,cid,numberOfClusters);
            
		}catch(Exception ex){
			logger.error(ex.toString());
		}
		
			
	}
	
	public CSP[] solveTasks (){
		CSP [] newTasks = heuristix.generateNewCSPs(inputFolder);
		CSP [] solutions = new CSP [newTasks.length];
		
		for (int i=0;i<solutions.length;i++){
			solutions[i] = heuristix.solveTask(newTasks[i]);
		}
		String solutionsStr="";
		for(int i=0;i<solutions.length;i++)
			solutionsStr+="\n"+solutions[i].toString();
		
		logger.info("SOLUTIONS:"+solutionsStr);	
		logger.info("END ("+dateFormat.format(date)+")");
		logger.info("\n####################################################\n");
		return solutions;
	}
	
}