package at.tugraz.ist.ase.cspheuristix;


import java.util.List;

import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.solvers.Const;
import at.tugraz.ist.ase.solvers.Var;
import at.tugraz.ist.ase.util.ClusteringAlgorithmID;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.ReadFile;
import at.tugraz.ist.ase.util.SolverID;

public abstract class Heuristics{
	
	
	
    protected abstract void learn(HeuristicID heuristicsID, SolverID solverID, DiagnoserID diagnosisAlgorithmID, String inputFile, String outputFolder, PerformanceIndicator pi, String stoppingCriteria,ClusteringAlgorithmID cid, int numberOClusters);

    protected abstract CSP solveTask(CSP task);
    
    
    protected CSP[] generatePastCSPs(String inputFile){
    	CSP basis = generateBasisCSP(inputFile);
    	
    	return generateCSPs(inputFile,basis); 	
	}
    
    protected CSP[] generateNewCSPs(String inputFile){
    	CSP basis = generateBasisCSP(inputFile);
    	
    	return generateCSPs(inputFile+"_newReqs",basis); 	
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
				
			pastCSPs[i]= new CSP(basis.getName()+i,basis.getVars(),basis.getConstraints());
			pastCSPs[i].insertConstraints(reqs);
		}
		
		return pastCSPs;
		
	}
    
    
    private CSP generateBasisCSP(String inputFile){
		
		// read vars 
		List<String> lines = new ReadFile().readFile(inputFile+"_basisCSP");
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
    
    
 }
