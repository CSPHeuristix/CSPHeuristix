package at.tugraz.ist.ase.cspheuristix;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.junit.Test;

import at.tugraz.ist.ase.algorithms.KNN;
import at.tugraz.ist.ase.algorithms.MAUT;
import at.tugraz.ist.ase.algorithms.MatrixFactorization;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_CO;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_VVO;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.solvers.Const;
import at.tugraz.ist.ase.solvers.Solver;
import at.tugraz.ist.ase.util.ClusteringAlgorithmID;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.FileOperations;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

/** Represents Matrix Factorization and MAUT Function Based Variable Ordering Heuristics for Constraint Solving 
 * -> (designed for "Optimized Release Planning using MF based Heuristics")
 * -> additional input is the input file : itemDimensions 
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

class MFMVO extends Heuristics{
	
	//int [][]domains;
	
	//String inputFile = "IOFOLDER/INPUT/test/mf/userRatings.data";
	double [][] p = null;
	double [][] q = null;
	double [][] fullMatrix;
	
	int numberOfUsers;
	int numberOfItems;
	
	double [][] dimensionValuesOfVars;
	

	MFMVO(HeuristicID heuristicsID, SolverID solverID, DiagnoserID diagnosisAlgorithmID, String inputFile,
			String outputFolder, PerformanceIndicator pi, String stoppingCriteria, ClusteringAlgorithmID cid,
			int numberOfClusters, int m) {
		super(heuristicsID, solverID, diagnosisAlgorithmID, inputFile, outputFolder, pi, stoppingCriteria, cid,
				numberOfClusters, m);
		// TODO Auto-generated constructor stub
		
		getDimensions();
	}

	@Override
	protected void learn() {
		
		// 1- Factorize the matrix
		
		MatrixFactorization mf = new MatrixFactorization();
		DataModel dataModel;
		try {
			dataModel = new FileDataModel(new File(ratingsForSolvingFile));
			mf.SVD(dataModel);
			p=mf.UF;
			q=mf.IF;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		numberOfUsers = p.length;
		numberOfItems = q.length;
		
		// 2- calculate the full matrix from P and Q
		fullMatrix = new MatrixFactorization().multiplyByMatrix(p, q);
		
		
		// 3- Sort the variables  -> learnedHeuristics
		learnedHeuristics = new Individual_VVO[numberOfUsers];
		
		for (int i=0;i<numberOfUsers;i++){
			this.learnedHeuristics[i] = new Individual_VVO(this.trainingDataset, hid, sid, pi, did, m);
			int index = 0;
			
			HashMap<Double,Integer> vars = new HashMap<Double,Integer>(this.numberOfVars);   


			// READ DIMENSIONS AND CALCULATE MAUT FOR EACH VAR
			for(int v=0;v<this.numberOfVars;v++){
				//HashMap<Double,Integer> dimensionRatingsOfUserForVar = new HashMap<Double,Integer>();   
				double [] dimensionRatingsOfUserForVar = new double [dimensionValuesOfVars[0].length];
				
				
				for(int d=0;d<dimensionValuesOfVars[0].length;d++){
					dimensionRatingsOfUserForVar[d] = fullMatrix[i][index];
					index++;
				}
				
				// Maut
				double utility = MAUT.basic(dimensionRatingsOfUserForVar, dimensionValuesOfVars[v]);
				
				
				// Var
			    vars.put(utility,v); // put maut value for each var for this user
			    
			}
			
			// SORT VARIABLES
			List<Double> mapKeys2 = new ArrayList<>(vars.keySet());
		    Collections.sort(mapKeys2);
		    Collections.reverse(mapKeys2);
		
	        for(int v=0;v<this.numberOfVars;v++){
		    	int var_index = vars.get(mapKeys2.get(v));
		    	learnedHeuristics[i].variableOrdering[v]= var_index; 
		    	//recommendationTasks.recomTasks_copies[h][i].variableOrdering[v] = var_index; 
			}
		}
		
	}

	@Override
	protected CSP solveTask(CSP task) {
		// TODO Auto-generated method stub
		
		// Step-4: Find nearest cluster
		KNN knn = new KNN();
		
		// convert reqs of the task into binary format to compare with matrix rows
		int numberOfEncodedItems=fullMatrix[0].length;
		int [] encodedReqs = new int [numberOfEncodedItems];
		int itemID=0;
		for(int i=0;i<basisCSP.getVars().length;i++){
			int domainSize = basisCSP.getVars()[i].getMaxDomain();
			for(int j=0;j<domainSize;j++){
				if(task.getREQs()[i]>-1){ // initialized
					if(task.getREQs()[i]==j)
						encodedReqs[itemID] = 1;
					else
						encodedReqs[itemID] = 0;
				}
				else{ // not initialized
					encodedReqs[itemID] = -1;
				}
				itemID++;
			}
		}
			
		int index = knn.findClosestCluster(fullMatrix, encodedReqs);
				
				
		// Step-5: Solve with the heuristics of the nearest cluster
		Solver s = new Solver();
		CSP solution = s.solveCSP(task, sid, learnedHeuristics[index]);
				
		return solution;
	}

	@Override
	protected CSP diagnoseTask(CSP task) {
		// TODO Auto-generated method stub
		
		// This method is not supported
		
		return null;
	}

	private void getDimensions(){
		int numberOfDimensions=0;
		String dimensionFileName = inputFolder+"/itemDimensions";
		List<String> items = FileOperations.readFile(dimensionFileName);
		numberOfDimensions = items.get(0).split(",").length;
		dimensionValuesOfVars = new double[items.size()][numberOfDimensions];
		
		for (int i=0;i<items.size();i++){
			String[] dimensions = items.get(i).split(",");
			for(int j=0;j<numberOfDimensions;j++)
				dimensionValuesOfVars[i][j] = Double.valueOf(dimensions[j]);
		}
		
	}
	
	
}
