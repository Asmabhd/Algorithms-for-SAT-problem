package ACS;

public class Solution {
	public int sat[];
	public int fitnes;

	public Solution() {
		super();
	}

	public Solution(int[] init, int fitnes) {
		super();
		this.sat = init;
		this.fitnes = fitnes;
	}

	public int[] getInit() {
		return sat;
	}

	public void setInit(int[] init) {
		this.sat = init;
	}

	public int getFitnes() {
		return fitnes;
	}

	public void setFitnes(int fitnes) {
		this.fitnes = fitnes;
	}
	
	public int num_clause_satisfied(int[] solution,Instance p) {
		int tmpindex;
		int cpt = 0;
		for (Clause c : p.Clause) {
			for (Litteral l : c.getLiteral()) {
				tmpindex = l.getvaleur();
				if (l.indice == false) {
					tmpindex *= -1;
					if (solution[tmpindex - 1] == 0) {
						cpt++;
						break;
					}
				} else {
					if (solution[tmpindex - 1] == 1) {
						cpt++;
						break;
					}
				}
			} 
		}
		return cpt;
	}
}
