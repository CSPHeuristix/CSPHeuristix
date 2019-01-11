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
	int [] reqs;
	int [] solution;
	Const[] diagnosis;
	
	public boolean isSolved= false;
	public float runtime = -1;
	
	public CSP (CSP basisCSP){
		this.name= basisCSP.name;
		this.vars= basisCSP.vars.clone();
		this.AC = basisCSP.AC.clone();
		this.BC = basisCSP.BC.clone();
	}
	
	public CSP(String name,Var[] vars,Const[] cons){
		this.name= name;
		this.vars= vars;
		this.AC = cons.clone();
		this.BC = cons.clone();
	}

	public void insertReqs(int [] reqs){
		this.reqs = reqs;
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

	public String getName() {
		return this.name;
	}
	public int[] getREQs() {
		return this.reqs;
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
	public Const[] getC() {
		return BC;
	}

	/**
	 * @param c the c to set
	 */
	public void setC(Const[] c) {
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
	public Const[] getDiagnoses() {
		return diagnosis;
	}

	/**
	 * @param diagnoses the diagnoses to set
	 */
	public void setDiagnoses(Const[] diagnoses) {
		this.diagnosis = diagnoses;
	}

}
