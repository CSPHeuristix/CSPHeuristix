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

/** Represents Matrix Factorization Based Variable and Value Ordering Heuristics for Constraint Solving
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

class MFVVO extends Heuristics{
	
	//int [][]domains;
	
	//String inputFile = "IOFOLDER/INPUT/test/mf/userRatings.data";
	double [][] p = null;
	double [][] q = null;
	double [][] fullMatrix;
	
	int numberOfUsers;
	int numberOfItems;
	

	MFVVO(HeuristicID heuristicsID, SolverID solverID, DiagnoserID diagnosisAlgorithmID, String inputFile,
			String outputFolder, PerformanceIndicator pi, String stoppingCriteria, ClusteringAlgorithmID cid,
			int numberOfClusters, int m) {
		super(heuristicsID, solverID, diagnosisAlgorithmID, inputFile, outputFolder, pi, stoppingCriteria, cid,
				numberOfClusters, m);
		// TODO Auto-generated constructor stub
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
		
		
		// 3- Sort the variables and their values  -> learnedHeuristics
		learnedHeuristics = new Individual_VVO[numberOfUsers];
		
		for (int i=0;i<numberOfUsers;i++){
			this.learnedHeuristics[i] = new Individual_VVO(this.trainingDataset, hid, sid, pi, did, m);
			int index = 0;
			
			HashMap<Double,Integer> vars = new HashMap<Double,Integer>(this.numberOfVars);   
			
			// SORT VALUES
			for(int v=0;v<this.numberOfVars;v++){
				HashMap<Double,Integer> valuesOfv = new HashMap<Double,Integer>();   
				
				for(int d=0;d<basisCSP.getVars()[v].getMaxDomain();d++){
					valuesOfv.put(fullMatrix[i][index],d);
					index++;
				}
				List<Double> mapKeys = new ArrayList<>(valuesOfv.keySet());
			    Collections.sort(mapKeys);
			    Collections.reverse(mapKeys);
			    
			    for(int d=0;d<basisCSP.getVars()[v].getMaxDomain();d++){
			    	int val_index = valuesOfv.get(mapKeys.get(d));
			    	learnedHeuristics[i].valueOrdering[v][d] = val_index;
			    	if (d==0)
			    		vars.put(mapKeys.get(0),v); // put max value rating for each var
			    }
			    
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

	
	
}
