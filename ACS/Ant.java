package ACS;
import java.util.*;
public class Ant {
	 
	public static double q0 = 0.1;
	public static  int evaluation = 325;
    double[][] Proba = new double[75][2];
	public static double alpha = 1, betha = 1;
    
	public Solution genererSolution(Instance p) {
		int Sol_initiale[];
		Solution sat = new Solution();
	
			Sol_initiale = new int[75];
			for (int j = 0; j < 75; j++)
				Sol_initiale[j] = -1;
			int random = (int) (Math.random() * 100) % 75;
			double c=Math.random();
			if ( c< 0.5)
				Sol_initiale[random] = 1;
			else
				Sol_initiale[random] = 0;
			sat.setInit(Sol_initiale);
			sat.setFitnes(new Solution().num_clause_satisfied(Sol_initiale,p));
		
		return sat;
	}
	
	 public void constructionSol(Solution sol, Instance p,Pheromone ph) {
		ArrayList<Double> list = TransitionRule( sol, alpha, betha, p, ph);
		double q=Math.random();
		if ( q> q0) {
			double probabilite = 0;
	     	int	j = 0;
			int index;
			for (double i : list) {
				probabilite += i;
				if (probabilite > Math.random()) {
					if ((j + 1) % 2 == 1) {
						index = (int) ((j + 1) / 2);
						sol.sat[index] = 1;
					} 
					else {
						index = (int) (j / 2);
						sol.sat[index] = 0;
					}
					sol.fitnes= new Solution().num_clause_satisfied(sol.sat, p);
					break;
				}
				j++;
			}
		} 
		else {
			int[] t = argsMax();
			if (t[1] == 1)
				sol.sat[t[0]] = 1;
			else
				sol.sat[t[0]] = 0;
			sol.fitnes = new Solution().num_clause_satisfied(sol.sat, p);
		}
	}
	  
	  public int[] argsMax() {
        double max = Proba[0][0];
		int ligne = 0, colone = 0;
		for (int i = 0; i < 75; i++) {
			for (int j = 0; j < 2; j++) {
				if (Proba[i][j] > max) {
					max = Proba[i][j];
					ligne = i;
					colone = j;
				}
			}
		}
		int t[] = { ligne, colone };
		return t;
	   }
	
	  public ArrayList<Double> TransitionRule(Solution sol,double alpha,double betha,Instance p,Pheromone ph) {
        double sum = 0;
		int t1[] = new int[75];
		for (int i = 0; i < 75; i++)
			t1[i] = -1;
		ArrayList<Double> ProbaTable = new ArrayList<Double>();
		for (int k = 0; k < 75; k++) {
			int t2[] = sol.sat.clone();
			if (t2[k] == -1) {
				t2[k] = t1[k] = 1;
				
			double ti1=ph.pheromonMatrice[k][1];
			ProbaTable.add((Math.pow(ti1, alpha) * Math.pow(new Solution().num_clause_satisfied(t1, p), betha)));
			sum += ProbaTable.get(k * 2);
			t2[k] = t1[k] = 0;
			double ti0=ph.pheromonMatrice[k][0];
			ProbaTable.add((Math.pow(ti0, alpha) * Math.pow(new Solution().num_clause_satisfied(t1, p), betha)));
			sum += ProbaTable.get(k * 2 + 1);
			} 
			else {
				ProbaTable.add((double) 0);
				ProbaTable.add((double) 0);
			}
		}
		int j = 0;
		for (int i = 0; i < ProbaTable.size(); i = i + 2) {
			if (ProbaTable.get(i) == 0) {
				Proba[j][1] = 0;
				Proba[j][0] = 0;
			} 
			else {
				Proba[j][1] = ProbaTable.get(i) / sum;
				Proba[j][0] = ProbaTable.get(i + 1) / sum;
			}
			j++;
		}
      return ProbaTable;
	}
}