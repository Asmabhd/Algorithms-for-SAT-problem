package Search;



public class NoeudHeuristic implements Comparable<NoeudHeuristic> {
	private Solution solution;
	private int functionG; /* Repr�sente le co�t du n�ud */
	private int functionH;/* Repr�sente le co�t estim� du n�ud par rapport � l'objectif */
  
	public NoeudHeuristic(Solution solution,int functionG, int functionH) {
		this.solution = solution;
		this.functionG = functionG;
		this.functionH = functionH; 
	}

	public Solution getSolution() { return solution; }
	public int getFunctionG() { return functionG; }
	public int getFunctionH() { return functionH; }
	

	public static void setClset(ClauseSet clset) {
	}
	public int getValHeuristique() {
		return (this.functionG+this.functionH);
	}

	@Override
	public int compareTo(NoeudHeuristic anotherNode) {
		if(anotherNode==null){return 1;}
	    if(!( anotherNode instanceof NoeudHeuristic)){return 1;}
	    
		if((this.functionG + this.functionH) > (anotherNode.functionG + anotherNode.functionH))
			return 1;

		if((this.functionG + this.functionH) < (anotherNode.functionG + anotherNode.functionH))
			return -1;

		return 0;/* Les deux n�uds sont �gaux */
	}

	






}