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
		noeud = new NoeudSearch(new int[]{});// l'�tat initial 
		open.add(noeud); 
		 //System.out.println(noeud+" "+" Clauses verifi�s :"+clset.satisfiedClauses(noeud.getVal()));
		int noeudclset = clset.satisfiedClauses(noeud.getVal()); 
		 bestNoued = new NoeudSearch(noeud.getVal());
		
		long startTime = System.currentTimeMillis(); /* Enregistrer le temps de d�but de la recherche */
		while (!open.isEmpty()) {
			if((System.currentTimeMillis() - startTime) >= execTimeMillis) 
				break; /* Si le temps de recherche a atteint (ou d�pass�) le temps d'ex�cution autoris�, terminer la recherche */
			
			// S'il y a des "n�uds" dans "list open" � d�velopper
			noeud = open.get(0); /* R�cup�re le premier �l�ment (head, last added) de la list "open" */
			open.remove(0); 
			
            // calculer le fils n0: variable instanci� avec la valeur= 0
			noeud0 = noeud.getFils0();
			int noeud0clset= clset.satisfiedClauses(noeud0.getVal());
			
			if( noeud0clset> noeudclset & noeud0.getVal().length == clset.getNumberVariables())   /* Si la solution actuelle est meilleure, mettre � jour la meilleure solution */
				bestNoued = new NoeudSearch(noeud0.getVal()); 
			
			if (noeud0.isSolution() )  //Si le nombre de clauses trouv�s satisfaites = aux nombres de clauses total = solution trouv�e
				return noeud0;   
		    // calculer le fils n1 : variable instanci� avec la valeur= 1
			noeud1 = noeud.getFils1();
			int noeud1clset= clset.satisfiedClauses(noeud1.getVal());
			
			if( noeud1clset > noeudclset & noeud1.getVal().length == clset.getNumberVariables() ) /* Si la solution actuelle est meilleure, mettre � jour la meilleure solution */
				bestNoued = new NoeudSearch(noeud1.getVal());
		
			if (noeud1.isSolution() )
				return noeud1;   																			
				
			// s'il a des fils a developper on l'ajoute																						
			
			if (noeud0.getVal().length < clset.getNumberVariables() ) //verifier que le seuil est inferieur ou �gale au seuil
			  open.add(0,noeud0); /* d�velopper le dernier n�ud ins�r�: empiler la variable recuperer avec valeur = 0 dans la pile ouverte */
			
			
			if ( noeud1.getVal().length < clset.getNumberVariables()) //verifier que le seuil est inferieur ou �gale au seuil
			  open.add(0,noeud1);/* d�velopper le dernier n�ud ins�r�: empiler la variable recuperer avec valeur = 0 dans la pile ouverte */
	}
		
	return bestNoued;
	

	}
	

	

	 public static Solution AEtoile(ClauseSet clset, long execTimeMillis){
	        
	        System.out.println("------------------ Algorithme A* -------------------\n");
	        if(clset==null)return null;NoeudHeuristic.setClset(clset); 
	        
	        int clsetSat = clset.getNumberclauses(); 
	        
			ArrayList<NoeudHeuristic> open = new ArrayList<NoeudHeuristic>(); 

			Solution actuelSol = new Solution(clset.getNumberVariables()); 
			int actuelSolSat = actuelSol.satisfiedClauses(clset); /* Nombre de clauses satisfaites par solution courante, pour optimiser le temps d'ex�cution */

			NoeudHeuristic currentNoeud = new NoeudHeuristic(actuelSol, 0, clsetSat-actuelSolSat);

			Solution bestSolution = new Solution(actuelSol);
			int bestSolutionSat = actuelSolSat; /* Nombre de clauses satisfaites par la meilleure solution */

			int randomLiteral;
			//open.add(currentNoeud);
			
			long startTime = System.currentTimeMillis(); /* Enregistrer le temps de d�but de la recherche */
			
			do{
				if((System.currentTimeMillis() - startTime) >= execTimeMillis)
					break; /* If the search time has reached (or exceeded) the allowed run time, finish the search */
				
				Collections.sort(open); /* Trier par ordre croissant les n�uds � l'aide de la fonction d'�valuation (f = g + h) */

				if(! open.isEmpty()) {
					currentNoeud = open.remove(0); /* Supprime le premier �l�ment de la liste "open" */
					actuelSol = new Solution(currentNoeud.getSolution());
				}
				
				

				if(actuelSol.getActiveLiterals() == clset.getNumberVariables())
					continue; /* Nombre maximum de litt�raux dans la solution atteint, de plus ce n'est pas la solution du probl�me SAT */

				randomLiteral = actuelSol.randomLiteral(clset.getNumberVariables());

				for(int i=0; i<2; i++) { /* Boucle DEUX fois pour le litt�ral choisi (enfant de gauche) et sa n�gation (enfant de droite) */
					actuelSol.changeLiteral(Math.abs(randomLiteral)-1, i==0 ? randomLiteral : -randomLiteral);
					actuelSolSat = actuelSol.satisfiedClauses(clset);
					
					if(actuelSolSat > bestSolutionSat) /* Si la solution actuelle est meilleure, mettre � jour la meilleure solution */
						bestSolution = new Solution(actuelSol);
				/*	System.out.println("Solution satisfaite : " + bestSolution+ "\nClauses v�rifi�es :"
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