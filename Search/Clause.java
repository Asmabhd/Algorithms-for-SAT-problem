package Search;

import java.util.ArrayList;

public class Clause {

		private ArrayList<Integer> literals = new ArrayList<Integer>();


		public Clause(ArrayList<Integer> literals) {
			for(int literal : literals)
				this.literals.add(literal);
		}

	

		public int getLiteral(int position){
			return this.literals.get(position);
		}
		

		public int getNumbreLiterals() {
			return this.literals.size();
		}


		public boolean changeLiteral(int position, int literal) { 
			if((position < 0) || (position >= this.literals.size())) /* Erreur : index hors limites du tableau */
				return false;

			this.literals.set(position, literal);

			return true;
		}


		@Override
		public String toString() {
			return "" + literals + "";
		}


	}