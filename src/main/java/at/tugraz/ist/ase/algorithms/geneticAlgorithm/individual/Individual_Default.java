package at.tugraz.ist.ase.algorithms.geneticAlgorithm.individual;

import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc;
import at.tugraz.ist.ase.algorithms.geneticAlgorithm.fitness.FitnessCalc_Default;
import at.tugraz.ist.ase.solvers.CSP;
import at.tugraz.ist.ase.util.HeuristicID;
import at.tugraz.ist.ase.util.PerformanceIndicator;
import at.tugraz.ist.ase.util.SolverID;

public class Individual_Default extends Individual {

	private byte[] genes = new byte[this.geneLength];
    private float fitness = 0;
    

	public Individual_Default(int geneLength, CSP[] trainingDataset, HeuristicID hi,SolverID sid,PerformanceIndicator pi) {
		super(geneLength, trainingDataset, hi, sid, pi);
		generateIndividual();
		fitnessCalc= new FitnessCalc_Default();
		// TODO Auto-generated constructor stub
	}

    
	@Override
	public Individual crossover(Individual indiv2, double uniformRate) {
		// TODO Auto-generated method stub
		Individual_Default newSol = new Individual_Default(this.geneLength,null, null, sid,pi);
        // Loop through genes
        for (int i = 0; i < this.getGeneLength(); i++) {
            // Crossover
            if (Math.random() <= uniformRate) {
                newSol.setGene(i, this.getGene(i));
            } else {
                newSol.setGene(i, ((Individual_Default)indiv2).getGene(i));
            }
        }
        return (Individual)newSol;
	}

	@Override
	public void mutate(double mutationRate) {
		// TODO Auto-generated method stub
		// Loop through genes
        for (int i = 0; i < this.getGeneLength(); i++) {
            if (Math.random() <= mutationRate) {
                // Create random gene
                byte gene = (byte) Math.round(Math.random());
                this.setGene(i, gene);
            }
        }
	}

	@Override
	public float getFitness(Individual target, PerformanceIndicator pi) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	///////////////////////////////////////////////////
	
	
    // Create a random individual
    private void generateIndividual() {
        for (int i = 0; i < this.getGeneLength(); i++) {
            byte gene = (byte) Math.round(Math.random());
            genes[i] = gene;
        }
    }

    /* Getters and setters */
    // Use this if you want to create individuals with different gene lengths
    public void setDefaultGeneLength(int length) {
        this.geneLength = length;
    }
    
    public byte getGene(int index) {
        return genes[index];
    }

    public void setGene(int index, byte value) {
        genes[index] = value;
        fitness = 0;
    }

    /* Public methods */
    public int getGeneLength() {
        return genes.length;
    }

    public float getFitness_Def(Individual target, PerformanceIndicator pi) {
        if (fitness == 0) 
            fitness = fitnessCalc.getFitness(this, target, pi, hi, trainingDataset, sid);
 
        return fitness;
    }

    @Override
	public String toString() {
        String geneString = "";
        for (int i = 0; i < this.getGeneLength(); i++) {
            geneString += getGene(i);
        }
        return geneString;
    }
   

}