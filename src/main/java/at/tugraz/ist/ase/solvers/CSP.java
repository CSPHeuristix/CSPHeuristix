package at.tugraz.ist.ase.solvers;

import java.util.ArrayList;
import java.util.List;

/** Represents a CSP with Var[] and Const[]
 * @author Seda Polat Erdeniz (AIG, TUGraz)
 * @author http://ase.ist.tugraz.at
 * @version 1.0
 * @since 1.0
*/

public class CSP {
	
	
	Var[] vars;
	Const[] AC; // all constraints = BC + REQ
	Const[] BC; // basis constraints -> always consistent
	Const[] REQ; // user requirements -> can be inconsistent
	String name;
	int [] intREQ;
	int [] expectedSolution;
	
	/////
	int [] solution;
	Const[] diagnosis;
	/////
	
	public boolean isSolved= false;
	public float runtime = -1;
	public float predictionQuality = -1;
	
	public CSP (CSP basisCSP){

		this.name= basisCSP.name;
		this.vars= basisCSP.vars.clone();
		this.AC = basisCSP.AC.clone();
		this.BC = basisCSP.BC.clone();
		
		if(basisCSP.REQ!=null)
			this.REQ = basisCSP.REQ.clone();
		if(basisCSP.intREQ!=null)
			this.intREQ = basisCSP.intREQ.clone();
		if(basisCSP.expectedSolution!=null)
			this.expectedSolution=basisCSP.expectedSolution.clone();
	}
	
	public CSP(String name,Var[] vars,Const[] cons,int[]reqs,int[]expecetedSoln){
		this.name= name;
		this.vars= vars;
		this.AC = cons.clone();
		this.BC = cons.clone();
		if(reqs!=null){
			this.intREQ = reqs.clone();
			insertReqs(reqs);
		}
		if(expecetedSoln!=null)
			this.expectedSolution=expecetedSoln.clone();
		
	}

	private void insertReqs(int [] reqs){
		this.intREQ = reqs;
		List<Const> reqList = new ArrayList<Const>();
		
		//REQ = new Const[reqs.length];
		for(int i=0;i<reqs.length;i++){
			if(reqs[i]>-1){ // IF INITIATED
				reqList.add(new Const(i, "=", reqs[i]));
			}
		}
		REQ = new Const[reqList.size()];
		REQ = reqList.toArray(REQ);
		
		Const[] currentAC= AC.clone();
		AC = new Const[BC.length+REQ.length];
		
		for(int i=0;i<BC.length;i++)
			AC[i]=currentAC[i];
		
		for(int i=0;i<REQ.length;i++)
			AC[BC.length+i]=REQ[i];
		
	}
	
	public float getPredictionQuality(){
		
		if(predictionQuality==-1){
			float totalSameResults=0;
			if(expectedSolution!=null){
				if(solution!=null)
					if(solution.length==expectedSolution.length){
						for(int i=0;i<solution.length;i++){
							if(solution[i]==expectedSolution[i])
								totalSameResults++;
						}
						predictionQuality = (float)(totalSameResults/solution.length);
					}
				else if (diagnosis!=null)
					if(diagnosis.length==expectedSolution.length){
						for(int i=0;i<diagnosis.length;i++){
							if(expectedSolution[diagnosis[i].getVarID()]!=intREQ[diagnosis[i].getVarID()]) // if the req is diagnosed 
								totalSameResults++;
						}
						predictionQuality = (float)(totalSameResults/solution.length);
					}
			}
			
		}
		
		return predictionQuality;
	}

	public String getName() {
		return this.name;
	}
	public int[] getREQs() {
		return this.intREQ;
	}
	public Var[] getVars() {
		return this.vars;
	}  
	public Const[] getAllConstraints() {
		return this.AC;
	}
	
	public boolean isSolved() {
		return this.isSolved;
	}
	public float getRuntime() {
		return this.runtime;
	}
	
	public String toString(){
		
		String print = "CSP-"+name+": ";
		
		if(solution!=null)
			for (int i=0;i<solution.length;i++)
				print += ", var-"+i+"= "+solution[i];
				
		if(diagnosis!=null)
			for (int i=0;i<diagnosis.length;i++)
					print+= diagnosis[i].toString()+";";
			
		print+= ", runtime: "+this.runtime+" ns";
		
		print+= ", prediction quality: "+this.predictionQuality;
		return print;
		
	}

	/**
	 * @return the solutions
	 */
	public int[] getSolutions() {
		return solution;
	}

	/**
	 * @param solutions the solutions to set
	 */
	public void setSolutions(int[] solutions) {
		this.solution = solutions.clone();
	}

	/**
	 * @return the aC
	 */
	public Const[] getAC() {
		return AC;
	}

	/**
	 * @param aC the aC to set
	 */
	public void setAC(Const[] aC) {
		AC = aC.clone();
	}

	/**
	 * @return the c
	 */
	public Const[] getBC() {
		return BC;
	}

	/**
	 * @param c the c to set
	 */
	public void setBC(Const[] c) {
		BC = c.clone();
	}

	/**
	 * @return the rEQ
	 */
	public Const[] getREQ() {
		return REQ;
	}

	/**
	 * @param rEQ the rEQ to set
	 */
	public void setREQ(Const[] rEQ) {
		REQ = rEQ.clone();
	}
	
	public void setOneREQ(int i, Const req) {
		REQ[i] = req;
	}

	/**
	 * @return the diagnoses
	 */
	public Const[] getDiagnosis() {
		return diagnosis;
	}

	/**
	 * @param diagnoses the diagnoses to set
	 */
	public void setDiagnosis(Const[] diagnoses) {
		this.diagnosis = diagnoses;
	}

	/**
	 * @return the expectedSolution
	 */
	public int[] getExpectedSolution() {
		return expectedSolution;
	}

	/**
	 * @param expectedSolution the expectedSolution to set
	 */
	public void setExpectedSolution(int[] expectedSolution) {
		this.expectedSolution = expectedSolution;
	}

	/**
	 * @param vars the vars to set
	 */
	public void setVars(Var[] vars) {
		this.vars = vars;
	}
	

}
