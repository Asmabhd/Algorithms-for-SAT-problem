package Search;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClauseSet {
	private int numberclauses,numberVariables,lengthclauses;
	static int[][] ClsetMatrix;
	private ArrayList<Integer> literalsClause;
	
	private ArrayList<Clause> clauses = new ArrayList<Clause>();
	public ClauseSet(int numberclauses, int numberVariables, int lengthclauses) {
		
		this.numberclauses = numberclauses;
		this.numberVariables = numberVariables;
		this.lengthclauses = lengthclauses;
		ClauseSet.ClsetMatrix= new int[numberclauses][numberVariables];
		
		for(int i=0;i<numberclauses;i++) {
			for(int j=0;j<numberVariables;j++) {
				ClauseSet.ClsetMatrix[i][j] = 0;
			}
		}
	}
	
	public ClauseSet(String path) throws IOException {
		// Passer le chemin d'un fichier CNF (qui contient les clauses)
		final List<String> lines = Files.readAllLines(Paths.get(path), Charset.defaultCharset());
		// Liste pour récupérer les clauses
		List<String[]> clausesset = new ArrayList<>();
		ArrayList<Integer> literalsClause = new ArrayList<Integer>(); /* Stocker les littéraux de la clause actuelle */
		

		for (String line : lines) {
			if (line.length() > 0) {

				char firstChar = line.charAt(0);
				// Insertion des clauses
				if (firstChar == '-' || (firstChar >= '0' && firstChar <= '9')) {
					String[] tempClause = line.split(" ");
					
						for(int i=0; i<tempClause.length; i++) /* Extraire les littéraux de la clause réelle */
							literalsClause.add(Integer.parseInt(tempClause[i]));

						this.clauses.add(new Clause(literalsClause)); /* Ajouter une nouvelle clause dans "ensemble de clauses" */

					literalsClause.clear(); /* Effacer la liste des littéraux de la clause réelle pour la prochaine itération */
					clausesset.add(Arrays.copyOfRange(tempClause, 0, tempClause.length - 1));
					
					// Récupérer le nbr des clauses et le nbr des variables:
				} else if (firstChar == 'p') {
					String[] fileInfos = line.split(" +");
					this.numberclauses = Integer.parseInt(fileInfos[3]);
					this.numberVariables = Integer.parseInt(fileInfos[2]);
					
				}
			}
		}

		if (!clauses.isEmpty()) {
			/* Définir le nombre de littéraux par clause */
			this.lengthclauses = clausesset.get(0).length;

			int[][] ClsetMat = new int[this.numberclauses][this.numberVariables];

			for (int i = 0; i < this.numberclauses; i++) {
				for (int j = 0; j < this.numberVariables; j++) {
					ClsetMat[i][j] = -1;
				}
			}

			for (int i = 0; i < this.numberclauses; i++) {
				/* prendre chaque clause et fait extraire les litéreaux puis remlpir la matrice
				 SAT pour chaque clause: des "0" si le litéral est négatif et "1" sinon */
				String[] clause = clausesset.get(i);

				for (int j = 0; j < lengthclauses; j++) {

					/* Extraire les littéraux de chaque clause */
					int literal = Integer.parseInt(clause[j]);
					ClsetMat[i][Math.abs(literal) - 1] = literal < 0 ? 0 : 1;
				}
				
			}

			this.setClsetMatrix(ClsetMat);
			

		} 

	}
	
	public int getNumberclauses() {
		return this.numberclauses;
	}

	public int getNumberVariables() {
		return this.numberVariables;
	}
	
	public int getLengthclauses() {
		return this.lengthclauses;
	}
	
	public int[][] getClsetMatrix() {
		return ClauseSet.ClsetMatrix;
	}
	
	public void setClsetMatrix(int[][] sATMatrix) {
		ClauseSet.ClsetMatrix = sATMatrix;
	}
	public Clause getClause(int position) { /* Récupère une clause à la position "position" */
		return this.clauses.get(position);
	}
	
	public int getLiteral(int position) { /* Récupère un littéral à la position "position" */
		return this.literalsClause.get(position);
	}
	public ArrayList<Integer> getLiteralList() { /* Récupère un littéral à la position "position" */
		return this.literalsClause;
	}
	
	
	public int satisfiedClauses(ClauseSet clset) { /* Compte le nombre de clauses satisfaites par cette solution */
		int count = 0;  /* Compteur des clauses satisfaites */
		int literal; /* Stocke le littéral réel */


		for(int i=0; i<clset.getNumberclauses(); i++) {/* Parcourir toutes les clauses de "clauses set" */
			Clause clause = clset.getClause(i);  
			for(int j=0; j<clset.getLengthclauses(); j++) {  /* Parcourir toutes les literals de "clauses set" */
				literal = clause.getLiteral(j);  
 
				if(literal == this.getLiteral(Math.abs(literal)-1)) {
					count++;
					break; /* Au moins un littéral satisfait la clause ==> cette clause est satisfaite */
				}
			}
		}

		return count;
	}
	

	public int satisfiedClauses(int[] solution) { /* Compte le nombre de clauses satisfaites par cette solution */
		
		boolean[] b = new boolean[this.getClsetMatrix().length];
		int i = 0;
		int nbtrue = 0;

		if (solution.length == 0) {
			return 0;
		}
		for (int[] clause : this.getClsetMatrix()) {
			int count= 0; 

			for (int l : clause) {
				if (count < solution.length) {
					if (l == solution[count]) {
						b[i] = true; /* Au moins un littéral satisfait la clause ==> cette clause est satisfaite */
					}
					count++;

				}
			}
			i++;
		}
      
			for (boolean bool : b) {
			if (bool) {
				nbtrue++;/* Compteur de clauses satisfaites */
		}
		}
       
		return nbtrue;
	}
	

	@Override
	public String toString() {
		String string = "The list of clauses is : \n";

		for(int i=0; i<this.clauses.size(); i++)
			string += i+". "+this.clauses.get(i)+"\n";

		return string;
	}

	

}
