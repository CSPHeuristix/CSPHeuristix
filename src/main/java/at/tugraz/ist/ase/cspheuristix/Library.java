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
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.ReadFile;
import at.tugraz.ist.ase.util.SolverID;

public class Library {
	
	private static final Logger logger = Logger.getLogger(Library.class);
	
	HeuristicID hid;
	SolverID sid;
	DiagnoserID did;
	String inputFolder;
	CSP basisTask;
	Heuristics heuristix;
	
	
	public Library(HeuristicID heuristicsID, SolverID solverID, DiagnoserID diagnosisAlgorithmID, String inputFolder, String outputFolder, PerformanceIndicator pi){
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
			
			basisTask = generateBasisTask();
			
			heuristix.learn(heuristicsID, solverID, diagnosisAlgorithmID, inputFolder, outputFolder,basisTask, pi);
            
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
		
		logger.info("Library: end ("+dateFormat.format(date)+")");		
	}
	
	private CSP generateBasisTask(){
		
		// read vars 
		List<String> lines = new ReadFile().readFile(inputFolder);
		String name = lines.get(0).split(";")[1];
		String [] vars = lines.get(1).split(";");
		String [] consts = lines.get(2).split(";");
		
		Var [] varList = new Var [vars.length-1];
		Const [] constList = new Const [vars.length-1];
		for(int i=1;i<vars.length;i++){
			String [] minmax = vars[i].split(",");
			varList[i]=new Var(String.valueOf(i-1), Integer.valueOf(minmax[0]).intValue(), Integer.valueOf(minmax[1]).intValue());
		}
		for(int i=1;i<consts.length;i++){
			String [] constStr = consts[i].split(",");
			constList[i]=new Const(Integer.valueOf(constStr[0]).intValue(),constStr[1],Integer.valueOf(constStr[2]).intValue());
		}
				
		return new CSP(name, varList, constList);
	}
	
	public CSP[] solveTasks (){
		
		// read vars 
		List<String> lines = new ReadFile().readFile(inputFolder);
		String [] newREQs  = lines.get(3).split(";");
		CSP[] solutions = new CSP [newREQs.length-1];
		
		for (int i=1;i<newREQs.length;i++){
			CSP task = new CSP(basisTask);
			String [] reqsStr = newREQs[i].split(",");
			int [] reqs = new int[reqsStr.length-1];
			
			for(int j=1;j<reqsStr.length;j++)
				reqs[i]=Integer.valueOf(reqsStr[j]).intValue();
				
			task.insertConstraints(reqs);
			solutions[i-1] = heuristix.solveTask(task);
		}
		return solutions;
	}
	
}