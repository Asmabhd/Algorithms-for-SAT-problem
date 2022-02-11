package Genetic;
import java.util.ArrayList;

import Interface.Frame;



public class Genetic_Test {

	static long tempsDebut;
	static ArrayList<int[]> Clauses;
    static int[] pram = {0,0};
    static int genesNb = 75;
    static int clausesNb = 325;
    static String fileName;
    
    public static void TestGenetic(long execTimeMillis) {
        
        double totalTime = 0;
        long startTime = System.currentTimeMillis(); /* Enregistrer le temps de début de la recherche */
        	tempsDebut = System.nanoTime();
        	//Creating the genetic algorithm
            Genetic_Algo ga = new Genetic_Algo(4000, 0.01, 0.95, 200);
            
            //Initializing the population
            Population population = ga.initPopulation(genesNb);
            
            fileName = "bib/uf75-01.cnf";
            Genetic_Algo.parseFile(fileName);
            
            ga.evalPopulation(population);
            
            int generation = 1;
            while (ga.termination(population)==false && generation < 350 ) {
            	if((System.currentTimeMillis() - startTime) >= execTimeMillis) 
    				break; /* Si le temps de recherche a atteint (ou dépassé) le temps d'exécution autorisé, terminer la recherche */
    			

            	// Applying crossover
            	population = ga.crossoverPopulation(population);
            	//population = ga.reproduce(population);
            	// Applying mutation
            	population = ga.mutatePopulation(population);
            	// Evaluating population
            	ga.evalPopulation(population);
            	// Increment the current generation
            	generation++;
            }
            //if we found the solution that satisfies all the clauses
            if(ga.termination(population)==false) {
            	
           	 String affichage = "Aucune solution trouvée en " + generation + " générations \n"
           	 		+ " Meilleure solution est: \n" + population.getFittest(0).toString()
           	 		+ "\n avec une fitness =" + population.getFittest(0).getFitness() + " / " +"clausesNb"
           	 		+ "\n ---------------------------------------------------------------------";
           	
            	Frame.OutputGenetic.append(affichage);
            	
            	}
            else {
            	
            	String affichage ="La Solution est trouvée en génération \n" + generation 
            			+ "\n Meilleure solution est: " + population.getFittest(0).toString()
            			+ "\n avec une fitness = " + population.getFittest(0).getFitness() + " / " + clausesNb
            			+ "\n ---------------------------------------------------------------------";
            
                Frame.OutputGenetic.append(affichage);
            }
            
            // l'espace mémoire occupé 
            
        	Runtime run = Runtime.getRuntime();
            double usedMem = run.totalMemory()-run.freeMemory();
            usedMem = usedMem / 1024 / 1024;
            
            Frame.OutputGenetic.append("\n Espace mémoire occupé : " + usedMem + " MB.");
          
          
            // Time complexity 
            
            long tempsFin = System.nanoTime();
        	double seconds = (tempsFin - tempsDebut) / 1e9;
        	totalTime = totalTime + seconds;
        	Frame.OutputGenetic.append("\n Opération effectuée en : " + seconds + " secondes.");
        
	}
}