import at.tugraz.ist.ase.cspheuristix.Library;
import at.tugraz.ist.ase.util.DiagnoserID;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.SolverID;

public class TestLibrary {
	
	public static void main(String [] args){
		
		Library lib = new Library(HeuristicID.clusterBasedCO, SolverID.choco, DiagnoserID.fastdiag, null);
	}

}
