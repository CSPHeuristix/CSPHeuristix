package at.tugraz.ist.ase.CSPHeuristix;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.*;

public class Library {
	
	
	private static final Logger logger = Logger.getLogger(Library.class);
	

	public Library(HeuristicID heuristicsID, SolverID solverID, DiagnoserID diagnosisAlgorithmID, String inputFolder){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		//System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
		
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