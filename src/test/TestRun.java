import at.tugraz.ist.ase.CSPHeuristix.enumerators.DiagnoserID;
import at.tugraz.ist.ase.CSPHeuristix.enumerators.HeuristicID;
import at.tugraz.ist.ase.CSPHeuristix.enumerators.SolverID;
import at.tugraz.ist.ase.CSPHeuristix.Library;

public class TestRun {
	
	public static void main(String [] args){
		
		Library lib = new Library(HeuristicID.clusterBasedCO, SolverID.choco, DiagnoserID.fastdiag, null);
	}

}
