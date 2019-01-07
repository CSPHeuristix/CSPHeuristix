package at.tugraz.ist.ase.cspheuristix;


import java.util.List;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_CO;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_VVO;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.solvers.Const;
import at.tugraz.ist.ase.solvers.Var;
import at.tugraz.ist.ase.util.ClusteringAlgorithmID;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.ReadFile;
import at.tugraz.ist.ase.util.SolverID;
import at.tugraz.ist.ase.util.WriteToFile;

/** Represents an Abstract Heuristics for Constraint Solving and Diagnosis
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
	CSP[][] trainingDataset;
	Individual[] learnedHeuristics;
	String stoppingCriteria;
	CSP [] pastCSPs;
	int m;
	PerformanceIndicator pi;
	String outputFolder;
	String inputFolder;
	////////////////////////
	
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
		
		ratingsForSolvingFile = outputFolder+"ratingsForSolving";
		ratingsForDiagnosisFile = outputFolder+"ratingsForDiagnosis";
		
		generateSolvingRatingFile();
		generateDiagnosisRatingFile();
	}
	
    protected abstract void learn();

    protected abstract CSP solveTask(CSP task);
    
    protected abstract Const[] diagnoseTask(CSP task);
    
    protected CSP[] generatePastCSPs(){
    	CSP basis = generateBasisCSP();
    	
    	return generateCSPs(pastSolutionsFile,basis); 	
	}
    
    protected CSP[] generateNewCSPs(){
    	CSP basis = generateBasisCSP();
    	
    	return generateCSPs(newReqsFile,basis); 	
	}
    
    protected CSP[] generateCSPs(String inputFile,CSP basis){
    	
		// read reqs 
		List<String> lines = new ReadFile().readFile(inputFile);
		String [][] reqsStr = new String[lines.size()][];
		CSP[] pastCSPs= new CSP[reqsStr.length];
    	
		for(int i=0;i<lines.size();i++){
			reqsStr[i] = lines.get(i).split(",");
			int [] reqs= new int [reqsStr[i].length-1];
					
			for(int j=0;j<reqsStr[i].length-1;j++)
				reqs[j]= Integer.valueOf(reqsStr[i][j].trim());
				
			pastCSPs[i]= new CSP(basis.getName()+i,basis.getVars(),basis.getAllConstraints());
			pastCSPs[i].insertReqs(reqs);
		}
		
		return pastCSPs;
		
	}
    
    private CSP generateBasisCSP(){
		
		// read vars 
		List<String> lines = new ReadFile().readFile(basisCSPFile);
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
    	int numberOfVars;
    	
		ReadFile readPastSolutionsFile = new ReadFile();
		List<String> pastSolutions =readPastSolutionsFile.readFile(pastSolutionsFile);
		numberOfVars = pastSolutions.get(0).split(",").length-1; // the last one is user ID
		
		ReadFile readPastConsistentReqsFile = new ReadFile();
		List<String> pastConsistentReqs =readPastConsistentReqsFile.readFile(pastConsistentReqsFile);
		//numberOfVars = pastConsistentReqs.get(0).split(",").length-1; // the last one is user ID
		
		WriteToFile writeToRatingsForSolvingFile = new WriteToFile();
		for(int i=0;i<pastSolutions.size();i++){
			for(int j=0;j<numberOfVars;j++){
				int value = Integer.valueOf(pastSolutions.get(i).split(",")[j].trim());
				String line = i+","+j+","+value;
				writeToRatingsForSolvingFile.writeALineToAFile(line,ratingsForSolvingFile);
			}
		}
		
		for(int i=0;i<pastConsistentReqs.size();i++){
			for(int j=0;j<numberOfVars;j++){
				int value = Integer.valueOf(pastSolutions.get(i).split(",")[j].trim());
				if(value!=-1){ // unassigned variable
					int uID =pastSolutions.size()+i;
					String line = uID+","+j+","+value;
					writeToRatingsForSolvingFile.writeALineToAFile(line,ratingsForSolvingFile);
				}
			}
		}
		
    }
    
    private void generateDiagnosisRatingFile(){
    	// generate the file: ratingsForDiagnosisFile
    	// from pastDiagnosesFile & pastInconsistentReqsFile
    	
    	int numberOfVars;
    	
		ReadFile readPastDiagnosesFile = new ReadFile();
		List<String> pastDiagnoses =readPastDiagnosesFile.readFile(pastDiagnosesFile);
		numberOfVars = (pastDiagnoses.get(0).split(",").length-1)/2; // the last one is user ID, others are vars+diagnoses
		
		ReadFile readPastInconsistentReqsFile = new ReadFile();
		List<String> pastInconsistentReqs =readPastInconsistentReqsFile.readFile(pastInconsistentReqsFile);
		//numberOfVars = pastConsistentReqs.get(0).split(",").length-1; // the last one is user ID
		
		WriteToFile writeToRatingsForSolvingFile = new WriteToFile();
		
		// ratings with diagnoses
		for(int i=0;i<pastDiagnoses.size();i++){
			for(int j=0;j<numberOfVars;j++){
				int reqvalue = Integer.valueOf(pastDiagnoses.get(i).split(",")[j].trim());
				String reqline = i+","+j+","+reqvalue;
				writeToRatingsForSolvingFile.writeALineToAFile(reqline,ratingsForDiagnosisFile);
				
				int itemID = j+numberOfVars;
				int diagvalue = Integer.valueOf(pastDiagnoses.get(i).split(",")[itemID].trim());
				String diagline = i+","+itemID+","+diagvalue;
				writeToRatingsForSolvingFile.writeALineToAFile(diagline,ratingsForDiagnosisFile);
			}
			
		}

		// ratings without diagnoses
		for(int i=0;i<pastInconsistentReqs.size();i++){
			for(int j=0;j<numberOfVars;j++){
				int value = Integer.valueOf(pastDiagnoses.get(i).split(",")[j].trim());
				if(value!=-1){ // unassigned variable
					int uID =pastDiagnoses.size()+i;
					String line = uID+","+j+","+value;
					writeToRatingsForSolvingFile.writeALineToAFile(line,ratingsForDiagnosisFile);
				}
			}
		}

    }
    
 }
