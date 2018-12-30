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

import org.apache.log4j.*;

import at.tugraz.ist.ase.diagnosers.Diagnoser;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.solvers.Solver;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

public class Library {
	
	private static final Logger logger = Logger.getLogger(Library.class);
	
	HeuristicID hid;
	SolverID sid;
	DiagnoserID did;
	String inputFolder;
	
	IHeuristics heuristix;
	
	
	public Library(HeuristicID heuristicsID, SolverID solverID, DiagnoserID diagnosisAlgorithmID, String inputFolder, String outputFolder,CSP basisTask, PerformanceIndicator pi){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		this.hid= heuristicsID;
		this.sid= solverID;
		this.did= diagnosisAlgorithmID;
		this.inputFolder= inputFolder;
		
		try{
			logger.info("Library: start ("+dateFormat.format(date)+")");
			logger.info("heuristicsID: "+ heuristicsID);
			logger.info("solverID: "+ solverID);
			logger.info("diagnosisAlgorithmID: "+ diagnosisAlgorithmID);
			logger.info("inputFolder: "+ inputFolder);
			
			switch (heuristicsID) {
		         case clusterBasedVVO:
		        	 logger.info("heuristicsID: clusterBasedVVO");
		        	 heuristix = new CLVVO();
		        	 break;
		                 
		         case clusterBasedCO:
		        	 logger.info("heuristicsID: clusterBasedCO");
		        	 heuristix = new CLCO();
		             break;
		                      
		         case MFBasedCO: 
		        	 logger.info("heuristicsID: MFBasedCO");
		        	 heuristix = new MFCO();
		             break;
		                     
		         case MFBasedVVO:
		        	 logger.info("heuristicsID: MFBasedVVO");
		        	 heuristix = new MFVVO();
		             break;
		             
		         default:
		        	 logger.info("default");
		             break;
		     }
			
			heuristix.learn(heuristicsID, solverID, diagnosisAlgorithmID, inputFolder, outputFolder,basisTask, pi);
            
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		
		logger.info("Library: end ("+dateFormat.format(date)+")");		
	}
	
	public CSP solveTask (CSP task){
		return heuristix.solveTask(task);
	}
	
}