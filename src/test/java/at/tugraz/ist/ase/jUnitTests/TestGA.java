package at.tugraz.ist.ase.jUnitTests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.sun.prism.shape.ShapeRep.InvalidationType;

import at.tugraz.ist.ase.algorithms.GeneticAlgorithm;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.GeneticAlgorithm_Default;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc_Default;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual.Individual;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.population.Population;
import at.tugraz.ist.ase.util.IndividualID;
import at.tugraz.ist.ase.util.PerformanceIndicator;

/** Tests for Genetic Algorithm
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public  class TestGA {
	
	@Test
	public void testGA(){
		int geneLength = 64;
		PerformanceIndicator pi = PerformanceIndicator.runtime;
		String targetStr = "1111000000000000000000000000000000000000000000000000000000001111";
		
		Individual result = new GeneticAlgorithm_Default(geneLength, targetStr, pi, null, null, null, null, 0).getTheFittestIndividual();
				
	    assertTrue(result!=null);
	}

}
