package at.tugraz.ist.ase.jUnitTests;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.junit.Test;

import at.tugraz.ist.ase.algorithms.Clustering;
import at.tugraz.ist.ase.algorithms.MatrixFactorization;
import at.tugraz.ist.ase.algorithms.MinMaxNormalization;
import at.tugraz.ist.ase.util.ClusteringAlgorithmID;
import at.tugraz.ist.ase.util.FileOperations;
import at.tugraz.ist.ase.util.SolverID;

/** Tests for File Operations
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public  class TestFileOperations {
	
	////////////////////////////////
	//  SETTINGS 				  //
	////////////////////////////////
	String testFile = "IOFOLDER/INPUT/test/file/testFile.data";
	String testFolder = "IOFOLDER/OUTPUT/test/file/";
	////////////////////////////////

	@Test
	public  void writeAndReadALine(){
		int random = (int) (Math.random()*1000);
		String line = "test-"+random;
		FileOperations.writeALineToAFile(line,testFile);
		
		List<String>lines =FileOperations.readFile(testFile);
		
		assertTrue(lines.get(lines.size()-1).equals(line));
		
	}
	
	@Test
	public  void appendAndReadLines(){
		int random = (int) (Math.random()*1000);
		String line = "test-"+random;
		FileOperations.writeALineToAFile(line,testFile);
		
		List<String>lines =FileOperations.readFile(testFile);
		
		assertTrue(lines.get(lines.size()-1).equals(line));
		
		
		random = (int) (Math.random()*1000);
		line = "test-"+random;
		FileOperations.writeALineToAFile(line,testFile);
		
		List<String>lines2 =FileOperations.readFile(testFile);
		
		assertTrue(lines2.get(lines2.size()-1).equals(line));
		
	}
	
}
