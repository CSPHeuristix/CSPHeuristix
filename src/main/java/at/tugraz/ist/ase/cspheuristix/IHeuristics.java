package at.tugraz.ist.ase.cspheuristix;


import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

class IHeuristics{
	
	//int [][] clusteredItems;

    protected void learn(HeuristicID heuristicsID, SolverID solverID, DiagnoserID diagnosisAlgorithmID, String inputFile, String outputFolder, CSP basisTask, PerformanceIndicator pi){}

    protected CSP solveTask(CSP task){return null;}
    
    protected CSP[] getTrainingDataSet(CSP basisTask,int [][] clusteredItems){
    	
    	return null;
    }
}
