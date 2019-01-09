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
	Individual[] learnedHeuristics;
	String stoppingCriteria;
	int m;
	PerformanceIndicator pi;
	String outputFolder;
	String inputFolder;
	////////////////////////
	protected int numberOfVars;
	
	protected String basisCSPFile;
	protected String newReqsFile;
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
		
		basisCSPFile = inputFolder+"/basisCSP";
		newReqsFile = inputFolder+"/newReqs";
		pastSolutionsFile = inputFolder+"/pastSolutions";
		pastConsistentReqsFile = inputFolder+"/pastConsistentReqs";
		pastDiagnosesFile = inputFolder+"/pastDiagnoses";
		pastInconsistentReqsFile = inputFolder+"/pastInconsistentReqs";
		
		ratingsForSolvingFile = outputFolder+"/ratingsForSolving";
		ratingsForDiagnosisFile = outputFolder+"/ratingsForDiagnosis";
		
		FileOperations.cleanOutputFolder(outputFolder);
		
		generatePastCSPs();
		generateNewCSPs();
		generateSolvingRatingFile();
		generateDiagnosisRatingFile();
		
	}
	
    protected abstract void learn();

    protected abstract CSP solveTask(CSP task);
    
    protected abstract CSP diagnoseTask(CSP task);
    
    private CSP[] generatePastCSPs(){
    	CSP basis = generateBasisCSP();
    	
    	return generateCSPs(pastSolutionsFile,basis); 	
	}
    
    private void generateNewCSPs(){
    	CSP basis = generateBasisCSP();
    	
    	newTasks =  generateCSPs(newReqsFile,basis); 	
	}
    
    private CSP[] generateCSPs(String inputFile,CSP basis){
    	
		// read reqs 
		List<String> lines = FileOperations.readFile(inputFile);
		String [][] reqsStr = new String[lines.size()][];
		trainingDataset= new CSP[reqsStr.length];
    	
		for(int i=0;i<lines.size();i++){
			reqsStr[i] = lines.get(i).split(",");
			int [] reqs= new int [reqsStr[i].length-1];
					
			for(int j=0;j<reqsStr[i].length-1;j++)
				reqs[j]= Integer.valueOf(reqsStr[i][j].trim());
				
			trainingDataset[i]= new CSP(basis.getName()+i,basis.getVars(),basis.getAllConstraints());
			trainingDataset[i].insertReqs(reqs);
		}
				
		return trainingDataset;
		
	}
    
    private CSP generateBasisCSP(){
		
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
		return new CSP(name, varList, constList);
	}
    
    
    private void generateSolvingRatingFile(){
    	// generate the file: ratingsForSolvingFile
    	// from pastSolutionsFile & pastConsistentReqsFile
    	
    	
		List<String> pastSolutions =FileOperations.readFile(pastSolutionsFile);
		this.numberOfVars = pastSolutions.get(0).split(",").length-1; // the last one is user ID
		
		List<String> pastConsistentReqs =FileOperations.readFile(pastConsistentReqsFile);
		//numberOfVars = pastConsistentReqs.get(0).split(",").length-1; // the last one is user ID
		
		for(int i=0;i<pastSolutions.size();i++){
			for(int j=0;j<numberOfVars;j++){
				int value = Integer.valueOf(pastSolutions.get(i).split(",")[j].trim());
				String line = i+","+j+","+value;
				FileOperations.writeALineToAFile(line,ratingsForSolvingFile);
			}
		}
		
		for(int i=0;i<pastConsistentReqs.size();i++){
			for(int j=0;j<numberOfVars;j++){
				int value = Integer.valueOf(pastSolutions.get(i).split(",")[j].trim());
				if(value!=-1){ // unassigned variable
					int uID =pastSolutions.size()+i;
					String line = uID+","+j+","+value;
					FileOperations.writeALineToAFile(line,ratingsForSolvingFile);
				}
			}
		}
		
    }
    
    private void generateDiagnosisRatingFile(){
    	// generate the file: ratingsForDiagnosisFile
    	// from pastDiagnosesFile & pastInconsistentReqsFile
    	
    	
		List<String> pastDiagnoses =FileOperations.readFile(pastDiagnosesFile);
		this.numberOfVars = (pastDiagnoses.get(0).split(",").length-1)/2; // the last one is user ID, others are vars+diagnoses
		
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
