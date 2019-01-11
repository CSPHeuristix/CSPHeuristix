package at.tugraz.ist.ase.cspheuristix;


import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_CO;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_VVO;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.solvers.Const;
import at.tugraz.ist.ase.solvers.Var;
import at.tugraz.ist.ase.util.ClusteringAlgorithmID;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.FileOperations;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

/** Represents an Abstract Class of Heuristics for Constraint Solving and Diagnosis
 * Variations of heuristics have to extend this abstract class
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public abstract class Heuristics{
	
	////////////////////////
	// Default parameters //
	int k;
	ClusteringAlgorithmID cid = ClusteringAlgorithmID.kmeans;
	SolverID sid;
	HeuristicID hid;
	DiagnoserID did;
	CSP [] trainingDataset;
	CSP [] newTasks;
	CSP basisCSP;
	Individual[] learnedHeuristics;
	String stoppingCriteria;
	int m;
	PerformanceIndicator pi;
	String outputFolder;
	String inputFolder;
	int [][] solutionsOfNewTasks;
	int [][] pastDiagnoses;
	////////////////////////
	protected int numberOfVars;
	
	protected String basisCSPFile;
	protected String newConsistentReqsFile;
	protected String newInconsistentReqsFile;
	protected String pastSolutionsFile;
	protected String pastConsistentReqsFile;
	protected String pastDiagnosesFile;
	protected String pastInconsistentReqsFile;
	
	protected String ratingsForSolvingFile;
	protected String ratingsForDiagnosisFile;
	////////////////////////
	
	
	Heuristics(HeuristicID heuristicsID, SolverID solverID, DiagnoserID diagnosisAlgorithmID, 
			String inputFolder, String outputFolder, PerformanceIndicator pi, 
			String stoppingCriteria, ClusteringAlgorithmID cid, int numberOfClusters, 
			int m){
		this.sid=solverID;
		this.cid=cid;
		this.hid=heuristicsID;
		this.did=diagnosisAlgorithmID;
		this.k=numberOfClusters;
		this.stoppingCriteria=stoppingCriteria;
		this.m = m;
		this.pi=pi;
		this.outputFolder=outputFolder;
		this.inputFolder=inputFolder;
		
		// input files
		basisCSPFile = inputFolder+"/basisCSP";
		newConsistentReqsFile = inputFolder+"/newConsistentReqs";
		newInconsistentReqsFile = inputFolder+"/newInconsistentReqs";
		pastSolutionsFile = inputFolder+"/pastSolutions";
		pastConsistentReqsFile = inputFolder+"/pastConsistentReqs";
		pastDiagnosesFile = inputFolder+"/pastDiagnoses";
		pastInconsistentReqsFile = inputFolder+"/pastInconsistentReqs";
		//
		
		ratingsForSolvingFile = outputFolder+"/ratingsForSolving";
		ratingsForDiagnosisFile = outputFolder+"/ratingsForDiagnosis";
		
		FileOperations.cleanOutputFolder(outputFolder);
		
		generateBasisCSP();
		generatePastCSPs();
		generateNewCSPs();
		generateSolvingRatingFile();
		generateDiagnosisRatingFile();
		
	}
	
    protected abstract void learn();

    protected abstract CSP solveTask(CSP task);
    
    protected abstract CSP diagnoseTask(CSP task);
    
    private void generatePastCSPs(){
    	//basisCSP = generateBasisCSP();
    	if(hid==HeuristicID.clusterBasedCO || hid==HeuristicID.MFBasedCO)
    		trainingDataset = generateCSPs(pastDiagnosesFile,false).clone(); 
    	else
    		trainingDataset = generateCSPs(pastSolutionsFile,false).clone(); 
	}
    
    private void generateNewCSPs(){
    	//CSP basis = generateBasisCSP();
    	
    	if(hid==HeuristicID.clusterBasedCO || hid==HeuristicID.MFBasedCO)
    		newTasks =  generateCSPs(newInconsistentReqsFile,true).clone(); 	
    	else
    		newTasks =  generateCSPs(newConsistentReqsFile,true).clone(); 	
	}
    
    private CSP[] generateCSPs(String inputFile,boolean isNew){
    	
		// read reqs 
		List<String> lines = FileOperations.readFile(inputFile);
		int numberOfUsers=lines.size();
		String [][] reqsStr = new String[numberOfUsers][];
		solutionsOfNewTasks = new int[numberOfUsers][];
		pastDiagnoses = new int[numberOfUsers][];
		
		CSP [] generatedCSPs= new CSP[reqsStr.length];
    	
		for(int i=0;i<numberOfUsers;i++){
			reqsStr[i] = lines.get(i).split(",");
			int [] reqs= new int [numberOfVars];
			
			if((reqsStr[i].length-1)/2==numberOfVars){
				if(isNew)
					solutionsOfNewTasks[i] = new int [numberOfVars];
				else
					pastDiagnoses[i] = new int [numberOfVars];
			}
			
						
			for(int j=0;j<numberOfVars;j++){
				reqs[j]= Integer.valueOf(reqsStr[i][j].trim());
				if((reqsStr[i].length-1)/2==numberOfVars){
					if(isNew)
						solutionsOfNewTasks[i][j]=Integer.valueOf(reqsStr[i][j+numberOfVars].trim());
					else
						pastDiagnoses[i][j]=Integer.valueOf(reqsStr[i][j+numberOfVars].trim());
				}
			}
		
			generatedCSPs[i]= new CSP(basisCSP.getName()+i,basisCSP.getVars(),basisCSP.getAllConstraints());
			generatedCSPs[i].insertReqs(reqs);
		}
				
		return generatedCSPs;
		
	}
    
    private void generateBasisCSP(){
		
		// read vars 
		List<String> lines = FileOperations.readFile(basisCSPFile);
		String name = lines.get(0).split(";")[1];
		String [] vars = lines.get(1).split(";");
		String [] consts = lines.get(2).split(";");
		
		Var [] varList = new Var [vars.length-1];
		Const [] constList = new Const[consts.length-1];
		
		for(int i=1;i<vars.length;i++){
			String [] minmax = vars[i].split(",");
			varList[i-1]=new Var(String.valueOf(i-1), Integer.valueOf(minmax[0]).intValue(), Integer.valueOf(minmax[1]).intValue());
		}
		for(int i=1;i<consts.length;i++){
			String [] constStr = consts[i].split(",");
			constList[i-1]=new Const(Integer.valueOf(constStr[0]).intValue(),constStr[1].trim(),Integer.valueOf(constStr[2]).intValue());
		}
		//new CSP(name, varList, constList);
		
		this.numberOfVars = varList.length;
		this.basisCSP = new CSP(name, varList, constList);
	}
    
    
    private void generateSolvingRatingFile(){
    	// generate the file: ratingsForSolvingFile
    	// from pastSolutionsFile & pastConsistentReqsFile
    	
    	
		List<String> pastSolutions =FileOperations.readFile(pastSolutionsFile);
		//this.numberOfVars = pastSolutions.get(0).split(",").length-1; // the last one is user ID
		
		List<String> pastConsistentReqs =FileOperations.readFile(pastConsistentReqsFile);
		//numberOfVars = pastConsistentReqs.get(0).split(",").length-1; // the last one is user ID
		
		for(int i=0;i<pastSolutions.size();i++){
			int userID=i;
			int itemID=0;
			for(int j=0;j<numberOfVars;j++){
				int valueRange=basisCSP.getVars()[j].getMaxDomain()-basisCSP.getVars()[j].getMinDomain();
				int value = Integer.valueOf(pastSolutions.get(i).split(",")[j].trim());
				for(int v=0;v<valueRange;v++){
					int rating = 0;
					if(v==value)
						rating = 1;
					String line = userID+","+itemID+","+rating;
					FileOperations.writeALineToAFile(line,ratingsForSolvingFile);
					itemID++;
				}
			}
		}
		
		for(int i=0;i<pastConsistentReqs.size();i++){
			int userID=i+pastSolutions.size();
			int itemID=0;
			for(int j=0;j<numberOfVars;j++){
				int valueRange=basisCSP.getVars()[j].getMaxDomain()-basisCSP.getVars()[j].getMinDomain();
				int value = Integer.valueOf(pastConsistentReqs.get(i).split(",")[j].trim());
				if(value>-1)
					for(int v=0;v<valueRange;v++){
						int rating = 0;
						if(v==value)
							rating = 1;
						String line = userID+","+itemID+","+rating;
						FileOperations.writeALineToAFile(line,ratingsForSolvingFile);
						itemID++;
					}
				else
					itemID += valueRange;
			}
		}
		
    }
    
    private void generateDiagnosisRatingFile(){
    	// generate the file: ratingsForDiagnosisFile
    	// from pastDiagnosesFile & pastInconsistentReqsFile
    	
    	
		List<String> pastDiagnoses =FileOperations.readFile(pastDiagnosesFile);
		//this.numberOfVars = (pastDiagnoses.get(0).split(",").length-1)/2; // the last one is user ID, others are vars+diagnoses
		
		List<String> pastInconsistentReqs =FileOperations.readFile(pastInconsistentReqsFile);
		//numberOfVars = pastConsistentReqs.get(0).split(",").length-1; // the last one is user ID
		
	    // ratings with diagnoses
		for(int i=0;i<pastDiagnoses.size();i++){
			for(int j=0;j<numberOfVars;j++){
				int reqvalue = Integer.valueOf(pastDiagnoses.get(i).split(",")[j].trim());
				String reqline = i+","+j+","+reqvalue;
				FileOperations.writeALineToAFile(reqline,ratingsForDiagnosisFile);
				
				int itemID = j+numberOfVars;
				int diagvalue = Integer.valueOf(pastDiagnoses.get(i).split(",")[itemID].trim());
				String diagline = i+","+itemID+","+diagvalue;
				FileOperations.writeALineToAFile(diagline,ratingsForDiagnosisFile);
			}
			
		}

		// ratings without diagnoses
		for(int i=0;i<pastInconsistentReqs.size();i++){
			for(int j=0;j<numberOfVars;j++){
				int value = Integer.valueOf(pastInconsistentReqs.get(i).split(",")[j].trim());
				if(value!=-1){ // unassigned variable
					int uID =pastDiagnoses.size()+i;
					String line = uID+","+j+","+value;
					FileOperations.writeALineToAFile(line,ratingsForDiagnosisFile);
				}
			}
		}

    }
    
 }
