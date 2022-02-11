package Search;



public class NoeudHeuristic implements Comparable<NoeudHeuristic> {
	private Solution solution;
	private int functionG; /* Représente le coût du nœud */
	private int functionH;/* Représente le coût estimé du nœud par rapport à l'objectif */
  
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

		return 0;/* Les deux nœuds sont égaux */
	}

	






}