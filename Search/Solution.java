package Search;

import java.util.ArrayList;


  public class Solution {
		private ArrayList<Integer> solution = new ArrayList<Integer>();
		


		public Solution(int size) { 
			for(int i=0; i<size; i++)
				this.solution.add(0); 
		}


		public Solution(Solution solution) {
			for(int i=0; i<solution.getSolutionSize(); i++)
				this.solution.add(solution.getLiteral(i));
		}
	
		
		public ArrayList<Integer> affichage(ArrayList<Integer> solution) {
			
			for(int i=0; i<this.getSolutionSize(); i++) {
				this.solution.set(i,solution.get(i)< 0 ? 0 : 1);
			}
		return 	solution;
		
		}


		public void randomSolution() { /* g�n�ration d'une solution al�atoire */
			int literalValue;

			for(int i=0; i<this.getSolutionSize(); i++) {
				literalValue = (int) (Math.random()*10)%2;

				this.solution.set(i, ((i+1) * (literalValue == 0 ? -1 : 1)));
			}
		}


		public int randomLiteral(int literals) {   /* g�n�ration d'un lit�ral al�atoire */
			int randomLiteral;

			do {
				randomLiteral = (int) (Math.random()*100)%literals + 1;
			}while(this.getLiteral(randomLiteral-1) != 0);

			return randomLiteral * (((int) (Math.random()*10)%2) == 0 ? 1 : -1);
		}
	

		public void setSolution(ArrayList<Integer> solution) {
			this.solution = solution;
		}


		public ArrayList<Integer> getSolution() {
			return solution;
		}



		public int getLiteral(int position) { /* R�cup�re un litt�ral � la position "p" */
			return this.solution.get(position);
		}


		public int getSolutionSize() {  /* R�cup�re le nombre de litt�ral dans une solution */
			return this.solution.size();
		}


		public int getActiveLiterals() {
			int activeLiterals=0;

			for(int literal : this.solution)
				if(literal != 0)
					activeLiterals++;

			return activeLiterals;
		}


		public boolean changeLiteral(int position, int value) { /* Change la valeur de v�rit� du litt�ral en position "position" */
			if((position < 0) || (position >= this.solution.size()))/* Erreur : index hors limites du tableau */
				return false;

			this.solution.set(position, value);

			return true;
		}



		public int satisfiedClauses(ClauseSet clset) { /* Compte le nombre de clauses satisfaites par cette solution */
			int count = 0;  /* Compteur des clauses satisfaites */
			int literal; /* Stocke le litt�ral r�el */


			for(int i=0; i<clset.getNumberclauses(); i++) {/* Parcourir toutes les clauses de "clauses set" */
				Clause clause = clset.getClause(i);  
				for(int j=0; j<clset.getLengthclauses(); j++) {  /* Parcourir toutes les literals de "clauses set" */
					literal = clause.getLiteral(j);  
	 
					if(literal == this.getLiteral(Math.abs(literal)-1)) {
						count++;
						break; /* Au moins un litt�ral satisfait la clause ==> cette clause est satisfaite */
					}
				}
			}

			return count;
		}


		public boolean isSolution(ClauseSet clset) { /* V�rifie si cette solution satisfait TOUTES LES CLAUSES dans "ensemble de clauses" */
			return clset.getNumberclauses() == this.satisfiedClauses(clset);
		}


		public int sameSatisfiedClausesAsLiteral( ClauseSet clset, int literal) { /* Compte le nombre de clauses satisfaites par "litt�ral" d�j� satisfaites par cette solution */
			int countSat = 0;
			int tempLiteral;
			boolean solutionSatClause, literalSatClause;

			for(int i=0; i<clset.getNumberclauses(); i++) { /* Parcourir toutes les clauses de "clauses set" */
				solutionSatClause = false;		literalSatClause = false;
				Clause clause = clset.getClause(i);  
				for(int j=0; j<clset.getLengthclauses(); j++) { /* Parcourir toutes les literals de "clauses set" */
					
					tempLiteral = clause.getLiteral(j); 
					
					if(literal == tempLiteral)
						literalSatClause = true; /* Le litt�ral satisfait la clause actuelle (nombre "i") */
					if(this.getLiteral(Math.abs(tempLiteral)-1) == tempLiteral)
						solutionSatClause = true; /* Cette solution satisfait la clause actuelle (num�ro "i") */
				}
				if(solutionSatClause && literalSatClause)
					countSat++; /* Si "literal" et que la solution satisfait la clause "i", incr�mente le compteur "countSat" */
			}

			return countSat;
		}

 
		@Override
		public String toString() {
			return ""+this.affichage(this.solution);
			
		}
	}