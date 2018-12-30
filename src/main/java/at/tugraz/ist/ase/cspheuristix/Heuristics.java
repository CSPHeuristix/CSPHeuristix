package at.tugraz.ist.ase.cspheuristix;


import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

public abstract class Heuristics{
	
	//int [][] clusteredItems;
	SolverID sid;

    protected abstract void learn(HeuristicID heuristicsID, SolverID solverID, DiagnoserID diagnosisAlgorithmID, String inputFile, String outputFolder, CSP basisTask, PerformanceIndicator pi);

    protected abstract CSP solveTask(CSP task);
    
 }
