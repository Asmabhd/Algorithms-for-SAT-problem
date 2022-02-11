package ACS;
import java.util.Arrays;

import Interface.Frame;

public class ACS_Solution {
	   Ant ant = new Ant();
	   Pheromone pheromone;
	   public static int Max_Iteration = 40;
	   public static int NbrFourmis = 20;
	   public static double ro = 1,t0=0.7; 
		
	   public void ACS_Algo(Instance p,int cpt,long execTimeMillis) {
		   
		  long startTime = System.currentTimeMillis(); 
		  pheromone=new Pheromone(t0, ro);
		  Solution best = new Solution();
			for (int m = 0; m < Max_Iteration; m++) {
				if((System.currentTimeMillis() - startTime) >= execTimeMillis) 
					break; /* Si le temps de recherche a atteint (ou dépassé) le temps d'exécution autorisé, terminer la recherche */
		
				
				for (int i = 0; i < NbrFourmis; i++) {
					
				Solution init = ant.genererSolution(p);
			        for (int l = 0; l < 75; l++)
						ant.constructionSol(init, p, pheromone);
			        
					if (init.fitnes > best.fitnes)
						best = init;
					pheromone.online(init);
				} 
				pheromone.offline(best);	
				
			}
			 String affichage3 ="\nResultat:\ninstance"+cpt+"\n Solution = "  + Arrays.toString(best.sat)
    	 		+ "\nfitnesse = " + best.fitnes+ " / " +"clausesNb";
				 Frame.OutputACS.append(affichage3);
		//	System.out.println("\nResultat:\ninstance"+cpt+"\n Solution = " + Arrays.toString(best.sat));
		//	System.out.println("fitnesse = " + best.fitnes);
			
		}
}