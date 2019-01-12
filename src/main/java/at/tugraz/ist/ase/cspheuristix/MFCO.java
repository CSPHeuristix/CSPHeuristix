package at.tugraz.ist.ase.cspheuristix;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;

import at.tugraz.ist.ase.algorithms.KNN;
import at.tugraz.ist.ase.algorithms.MatrixFactorization;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_CO;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual_VVO;
import at.tugraz.ist.ase.diagnosers.Diagnoser;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.solvers.Const;
import at.tugraz.ist.ase.solvers.Solver;
import at.tugraz.ist.ase.util.ClusteringAlgorithmID;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

/** Represents Matrix Factorization Based Constraint Ordering Heuristics for Consistency Based Diagnosis
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

class MFCO extends Heuristics{
	
	//String inputFile = "IOFOLDER/INPUT/test/mf/userRatings.data";
	double [][] p = null;
	double [][] q = null;
	double [][] fullMatrix;
	
	int numberOfUsers;
	int numberOfItems;
	
	MFCO(HeuristicID heuristicsID, SolverID solverID, DiagnoserID diagnosisAlgorithmID, String inputFile,
			String outputFolder, PerformanceIndicator pi, String stoppingCriteria, ClusteringAlgorithmID cid,
			int numberOfClusters, int m) {
		super(heuristicsID, solverID, diagnosisAlgorithmID, inputFile, outputFolder, pi, stoppingCriteria, cid,
				numberOfClusters, m);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void learn() {
		// TODO Auto-generated method stub
		
				// 1- Factorize the matrix
		
				MatrixFactorization mf = new MatrixFactorization();
				DataModel dataModel;
				try {
					dataModel = new FileDataModel(new File(ratingsForDiagnosisFile));
					mf.SVD(dataModel);
					p=mf.UF;
					q=mf.IF;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
				
				numberOfUsers = p.length;
				numberOfItems = q.length;
				
				this.numberOfVars = numberOfItems/2; // other half is for diagnosis
				
				// 2- calculate the full matrix from P and Q
				fullMatrix = new MatrixFactorization().multiplyByMatrix(p, q);
				
				
				// 3- Sort the variables and their values  -> learnedHeuristics
				this.learnedHeuristics = new Individual_CO[numberOfUsers];
				
				for (int i=0;i<numberOfUsers;i++){
					// CSP[] trainingDataset, HeuristicID hi, SolverID sid, PerformanceIndicator pi, DiagnoserID did, int m
					this.learnedHeuristics[i] = new Individual_CO(this.trainingDataset, hid, sid, pi, did, m);
					this.learnedHeuristics[i].variableOrdering = new int [this.numberOfVars];
					
					HashMap<Double,Integer> vars = new HashMap<Double,Integer>(this.numberOfVars);   
					
					// SORT CONSTRAINTS (VARS)
					// use only the right half of the matrix
					for(int v=0;v<this.numberOfVars;v++)
						vars.put(fullMatrix[i][v+this.numberOfVars],v);
					
					List<Double> mapKeys2 = new ArrayList<>(vars.keySet());
				    Collections.sort(mapKeys2);
				    Collections.reverse(mapKeys2);
				    
				    for(int v=0;v<this.numberOfVars;v++){
				    	int var_index = vars.get(mapKeys2.get(v));
				    	learnedHeuristics[i].variableOrdering[v]= var_index; 
					}
			      
				}
		
	}

	@Override
	protected CSP solveTask(CSP task) {
		// TODO Auto-generated method stub
		
		// This method is not supported yet
		
		return null;
	}

	@Override
	protected CSP diagnoseTask(CSP task) {
		// TODO Auto-generated method stub
		
		Const[] diagnosis = null;
		
		// Step-4: Find nearest cluster
		KNN knn = new KNN();
		int index = knn.findClosestCluster(fullMatrix, task.getREQs());
					
		// Step-5: order REQs
		Const[] unsorted = task.getREQ().clone();
		Const[] sorted = new Const[unsorted.length];
		for(int i=0;i<unsorted.length;i++){
			
			for(int j=0;j<learnedHeuristics[index].variableOrdering.length;j++){
				int constIndex = learnedHeuristics[index].variableOrdering[j];
				if(unsorted[i].getVarID()==constIndex)
					sorted[constIndex]=unsorted[i];
			}
			//int constIndex = learnedHeuristics[index].variableOrdering[i];
			//sorted[i]=unsorted[constIndex];
		}
		task.setREQ(sorted);
						
		// Step-6: Solve with the heuristics of the nearest cluster
		Diagnoser d = new Diagnoser();
		return d.diagnose(task, this.sid,this.did,m);
		
	}
}
