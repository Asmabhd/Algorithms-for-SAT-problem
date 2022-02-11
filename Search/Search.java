package Search;
import java.util.ArrayList;
import java.util.Collections;
import Search.Solution;
public class Search { 

	
	public static NoeudSearch DepthFirstSearch(ClauseSet clset, long execTimeMillis) {

		System.out.println("------------------ Algorithme recherche en ProfondeurDab -------------------\n");
		if (clset == null) return null;NoeudSearch.setclset(clset);
		ArrayList <NoeudSearch> open = new ArrayList <NoeudSearch>();
		NoeudSearch noeud, noeud0, noeud1,bestNoued;
		noeud = new NoeudSearch(new int[]{});// l'état initial 
		open.add(noeud); 
		 //System.out.println(noeud+" "+" Clauses verifiés :"+clset.satisfiedClauses(noeud.getVal()));
		int noeudclset = clset.satisfiedClauses(noeud.getVal()); 
		 bestNoued = new NoeudSearch(noeud.getVal());
		
		long startTime = System.currentTimeMillis(); /* Enregistrer le temps de début de la recherche */
		while (!open.isEmpty()) {
			if((System.currentTimeMillis() - startTime) >= execTimeMillis) 
				break; /* Si le temps de recherche a atteint (ou dépassé) le temps d'exécution autorisé, terminer la recherche */
			
			// S'il y a des "nœuds" dans "list open" à développer
			noeud = open.get(0); /* Récupère le premier élément (head, last added) de la list "open" */
			open.remove(0); 
			
            // calculer le fils n0: variable instancié avec la valeur= 0
			noeud0 = noeud.getFils0();
			int noeud0clset= clset.satisfiedClauses(noeud0.getVal());
			
			if( noeud0clset> noeudclset & noeud0.getVal().length == clset.getNumberVariables())   /* Si la solution actuelle est meilleure, mettre à jour la meilleure solution */
				bestNoued = new NoeudSearch(noeud0.getVal()); 
			
			if (noeud0.isSolution() )  //Si le nombre de clauses trouvés satisfaites = aux nombres de clauses total = solution trouvée
				return noeud0;   
		    // calculer le fils n1 : variable instancié avec la valeur= 1
			noeud1 = noeud.getFils1();
			int noeud1clset= clset.satisfiedClauses(noeud1.getVal());
			
			if( noeud1clset > noeudclset & noeud1.getVal().length == clset.getNumberVariables() ) /* Si la solution actuelle est meilleure, mettre à jour la meilleure solution */
				bestNoued = new NoeudSearch(noeud1.getVal());
		
			if (noeud1.isSolution() )
				return noeud1;   																			
				
			// s'il a des fils a developper on l'ajoute																						
			
			if (noeud0.getVal().length < clset.getNumberVariables() ) //verifier que le seuil est inferieur ou égale au seuil
			  open.add(0,noeud0); /* développer le dernier nœud inséré: empiler la variable recuperer avec valeur = 0 dans la pile ouverte */
			
			
			if ( noeud1.getVal().length < clset.getNumberVariables()) //verifier que le seuil est inferieur ou égale au seuil
			  open.add(0,noeud1);/* développer le dernier nœud inséré: empiler la variable recuperer avec valeur = 0 dans la pile ouverte */
	}
		
	return bestNoued;
	

	}
	

	

	 public static Solution AEtoile(ClauseSet clset, long execTimeMillis){
	        
	        System.out.println("------------------ Algorithme A* -------------------\n");
	        if(clset==null)return null;NoeudHeuristic.setClset(clset); 
	        
	        int clsetSat = clset.getNumberclauses(); 
	        
			ArrayList<NoeudHeuristic> open = new ArrayList<NoeudHeuristic>(); 

			Solution actuelSol = new Solution(clset.getNumberVariables()); 
			int actuelSolSat = actuelSol.satisfiedClauses(clset); /* Nombre de clauses satisfaites par solution courante, pour optimiser le temps d'exécution */

			NoeudHeuristic currentNoeud = new NoeudHeuristic(actuelSol, 0, clsetSat-actuelSolSat);

			Solution bestSolution = new Solution(actuelSol);
			int bestSolutionSat = actuelSolSat; /* Nombre de clauses satisfaites par la meilleure solution */

			int randomLiteral;
			//open.add(currentNoeud);
			
			long startTime = System.currentTimeMillis(); /* Enregistrer le temps de début de la recherche */
			
			do{
				if((System.currentTimeMillis() - startTime) >= execTimeMillis)
					break; /* If the search time has reached (or exceeded) the allowed run time, finish the search */
				
				Collections.sort(open); /* Trier par ordre croissant les nœuds à l'aide de la fonction d'évaluation (f = g + h) */

				if(! open.isEmpty()) {
					currentNoeud = open.remove(0); /* Supprime le premier élément de la liste "open" */
					actuelSol = new Solution(currentNoeud.getSolution());
				}
				
				

				if(actuelSol.getActiveLiterals() == clset.getNumberVariables())
					continue; /* Nombre maximum de littéraux dans la solution atteint, de plus ce n'est pas la solution du problème SAT */

				randomLiteral = actuelSol.randomLiteral(clset.getNumberVariables());

				for(int i=0; i<2; i++) { /* Boucle DEUX fois pour le littéral choisi (enfant de gauche) et sa négation (enfant de droite) */
					actuelSol.changeLiteral(Math.abs(randomLiteral)-1, i==0 ? randomLiteral : -randomLiteral);
					actuelSolSat = actuelSol.satisfiedClauses(clset);
					
					if(actuelSolSat > bestSolutionSat) /* Si la solution actuelle est meilleure, mettre à jour la meilleure solution */
						bestSolution = new Solution(actuelSol);
				/*	System.out.println("Solution satisfaite : " + bestSolution+ "\nClauses vérifiées :"
							+ currentSolSat ); */
					    
					if(actuelSolSat == clsetSat) /* Si cette solution satisfait toutes les clauses de "closet", retournez-la */
						return bestSolution; 

					open.add(new NoeudHeuristic(new Solution(actuelSol), currentNoeud.getSolution().sameSatisfiedClausesAsLiteral(clset,
							i==0 ? randomLiteral : -randomLiteral), clsetSat-actuelSolSat)); 
				}
				
				
			}while(! open.isEmpty()) ;
			
	        return bestSolution ;
	        
	        
			
	    }
	    
	    

}