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

import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.SolverID;

public class Library {
	
	
	private static final Logger logger = Logger.getLogger(Library.class);
	
	public Library(HeuristicID heuristicsID, SolverID solverID, DiagnoserID diagnosisAlgorithmID, String inputFolder){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		try{
			logger.info("Library: start ("+dateFormat.format(date)+")");
			logger.info("heuristicsID: "+ heuristicsID);
			logger.info("solverID: "+ solverID);
			logger.info("diagnosisAlgorithmID: "+ diagnosisAlgorithmID);
			logger.info("inputFolder: "+ inputFolder);
			
			switch (heuristicsID) {
		         case clusterBasedVVO:
		        	 logger.info("heuristicsID: clusterBasedVVO");
		             break;
		                 
		         case clusterBasedCO:
		        	 logger.info("heuristicsID: clusterBasedCO");
		             break;
		                      
		         case MFBasedCO: 
		        	 logger.info("heuristicsID: MFBasedCO");
		             break;
		                     
		         case MFBasedVVO:
		        	 logger.info("heuristicsID: MFBasedVVO");
		             break;
		             
		         default:
		        	 logger.info("default");
		             break;
		     }
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		
		logger.info("Library: end ("+dateFormat.format(date)+")");		
	}
	
    
}