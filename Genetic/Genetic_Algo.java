package Genetic;
import java.util.*;
import java.io.*;
import Interface.Frame;

public class Genetic_Algo {
	
	private int PopSize;
	private double MutationRate;
	private double CrossOverRate;
	private int ElitismCount;
	
	//static int[][] clauses;
	static long tempsDebut;
	static ArrayList<int[]> Clauses;
    static int[] pram = {0,0};
    static int genesNb = 75;
    static int clausesNb = 325;
    static String fileName;
	
    public static int PS = 4000; //population size par default
	public static double MR = 0.01; // mutation rate
	public static double CR = 0.95; // crossover rate
	public static int EC = 200;
	
	//Constructor of the genetic algorithm
	public Genetic_Algo(int p, double m, double c, int e) {
		PopSize = p;
		MutationRate = m;
		CrossOverRate = c;
		ElitismCount = e;
	}
	
	//Initializing the population method
	public Population initPopulation(int chromosomeLength) {
		Population population = new Population(this.PopSize, chromosomeLength);
		return population;
	}
	
	//Reading cnf Files method
    public static void parseFile(String fileName) {
        try {
            //the file to be opened for reading
            FileInputStream file = new FileInputStream(fileName);
            Scanner sc=new Scanner(file);

            if (sc.hasNextLine()) {
            	//to remove the first 8 lines 
                for (int i = 0; i < 8 ; i++) {
                    sc.nextLine();
                }
                pram[0] = genesNb;
				pram[1] = clausesNb;
                //clauses = new int[pram[1]][pram[0]];
                Clauses = new ArrayList<int[]>();
                //System.out.println("[!] Creating SAT matrix: " + pram[0] + " x " + pram[1]);
                int st = 0;
                for (int i = 0; i < pram[1]; i++) {
                	int[] tab = {0,0,0};
                    String[] vars = sc.nextLine().split(" ");     
                    if( st == 0) { 
                    	int iv1 = Integer.parseInt(vars[1]); tab[0] = iv1;
                    	int iv2 = Integer.parseInt(vars[2]); tab[1] = iv2;
                    	int iv3 = Integer.parseInt(vars[3]); tab[2] = iv3;
                    	}
                    else {
                    	int iv1 = Integer.parseInt(vars[0]); tab[0] = iv1;
                    	int iv2 = Integer.parseInt(vars[1]); tab[1] = iv2;
                    	int iv3 = Integer.parseInt(vars[2]); tab[2] = iv3;
                    	}
                    st++;
                 
                    Clauses.add(tab);
                }
                System.out.println("");
            }
            sc.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
 // verifier les clauses satisf
    
    public static int verify(int[] solution) {
    	int cpt = 0;
    	for(int[] clause : Clauses) {
    		int resultat = 0;
    		for(int literal = 0;literal < 3; literal++) {
    			// if the literal has the negation operator
    			if(clause[literal]<0) {
    				// we get rid of the (-) sign
    				int index = (Math.abs(clause[literal]))-1;
    				if(solution[index]== 1) {
    					resultat += 0;
    				}
    				else resultat += 1;
    			}
    			else {
    				int index = clause[literal]-1;
    				resultat += solution[index];
    				}
    		}
    		if(resultat > 0) {
    			cpt++;
    		}
    	}
    	
    	return cpt;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	
	//Roulette Wheel Selection method
    public Individu rouletteWheelSelection(Population population) {
    	// Get individuals
    	Individu individuals[] = population.getIndividuals();
    	
    	// Spin the Roulette Wheel
    	double populationFitness =(double) population.getPopulationFitness();
    	double rouletteWheelPosition = Math.random() * populationFitness;
    	
    	// Find a parent
    	double spinWheel = 0;
    	for (Individu individual : individuals) {
    		spinWheel += individual.getFitness();
    		if (spinWheel >= rouletteWheelPosition) {
    			return individual;
    		}
    	}
    	return individuals[population.size() - 1];
    }

     /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Crossover method 
    public Population crossoverPopulation(Population pop) {
    	Population newpop = new Population(pop.size());
    	
    	// Loop over current population by fitness
    	for (int populationIndex = 0; populationIndex < pop.size(); populationIndex++) {
    		Individu parent1 = pop.getFittest(populationIndex);
    		//System.out.println("fittest in this "+parent1.getFitness());
    		// Apply crossover to this individual?
    		if (this.CrossOverRate > Math.random() && populationIndex > this.ElitismCount) {
    			// Initialize offspring
    			Individu offspring = new Individu(parent1.getChromosomeLength());
    			// Find second parent
    			Individu parent2 = rouletteWheelSelection(pop);	
    			// Initialize random crossover point
    			int coPoint = (int) (Math.random() * genesNb );
    			
    			for (int geneIndex = 0; geneIndex < parent1.getChromosomeLength(); geneIndex++) {
    				//  Use some of parent1's genes and the rest of it from parent2's genes
    				if (geneIndex < coPoint) {
    					
    					offspring.setGene(geneIndex, parent1.getGene(geneIndex));
    					
    				} 
    				else {
    					
        					offspring.setGene(geneIndex, parent2.getGene(geneIndex));
        					
    				}
    			}
    			// Add offspring to new population
    			newpop.setIndividual(populationIndex, offspring);
    			} 
    		else {
    			// Add individual to new population without applying crossover
    			newpop.setIndividual(populationIndex, parent1);
    			}
    		}
    	
    	return newpop;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
	//Mutation method
    
    public Population mutatePopulation(Population population) {
    	// Initialize new population
    	Population newPopulation = new Population(this.PopSize);
    	// Loop over current population by fitness
    	for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
    		Individu individual = population.getFittest(populationIndex);
    		// Loop over individual's genes
    		for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
    			// Skip mutation if this is an elite individual
    			if (populationIndex >= this.ElitismCount) {
    				
    				// Does this gene need mutation?
    				if (this.MutationRate > Math.random()) {
    					// Get new gene
    					int newGene = 0;
    					if (individual.getGene(geneIndex) == 0) {
    						newGene = 1;
    					}
    					// Mutate gene
    					individual.setGene(geneIndex, newGene);
    				}
    			}
    		}
    		// Add individual to population
    		newPopulation.setIndividual(populationIndex, individual);
    	}
    	// Return mutated population
    	return newPopulation;
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    //evaluate population's individuals fitness and the total population fitness
    public void evalPopulation(Population p) {
    	int populationFitness = 0;
    	for (Individu individual : p.getIndividuals()) {
    		populationFitness += individual.calcFitness();
    	}
    	p.setPopulationFitness(populationFitness);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
    //Termination detection method
    public boolean termination(Population population) {
    	for (Individu individual : population.getIndividuals()) {
    		if (individual.getFitness() == clausesNb) {
    			
    			 String affichage = " \n the solution ! "+individual.toString()
    	           	 		+ "\n ---------------------------------------------------------------------";
    	 
    	            	    Frame.OutputGenetic.append(affichage);
    	     
    			return true;
    		}
    	}
    	return false;
    }
    
    //*************************************************************************************************************//

    //*************************************************************************************************************//
    
	// main method ( exécuter 5 instances)
	public static void mainGenetic(long execTimeMillis) {
        
        double totalTime = 0;
        long startTime = System.currentTimeMillis(); /* Enregistrer le temps de début de la recherche */
        for(int i = 0; i < 5 ; i++) {
        	if((System.currentTimeMillis() - startTime) >= execTimeMillis) 
				break; /* Si le temps de recherche a atteint (ou dépassé) le temps d'exécution autorisé, terminer la recherche */
			
        	tempsDebut = System.nanoTime();
        	
        	//Creation de l'algorithme genetique
        	
     	    Frame.OutputGenetic.append("\n Instance n°: "+(i+1)+"\n");        	
            Genetic_Algo ga = new Genetic_Algo(PS,MR,CR,EC);
            
            //Initializing the population
            Population population = ga.initPopulation(genesNb);
            
            //reading file of the instance i 
            fileName = "bib/uf75-0"+(i+1)+".cnf";
       
            parseFile(fileName);
            
            ga.evalPopulation(population);
            
            int generation = 1;
            while (ga.termination(population)==false && generation < 350 ) {

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
            	
           	 String affichage = " \n Aucune solution trouvée en " + generation + " générations \n"
            	 		+ " Meilleure solution est: \n" + population.getFittest(0).toString()
            	 		+ " \n avec une fitness =" + population.getFittest(0).getFitness() + " / " +"clausesNb"
            	 		;
            	
             	Frame.OutputGenetic.append(affichage);
             }
            else {

            	String affichage ="\n La Solution est trouvée en génération \n" + generation 
            			+ "\n Meilleure solution est: " + population.getFittest(0).toString()
            			+ "\n avec une fitness = " + population.getFittest(0).getFitness() + " / " + clausesNb
            			;
            
                Frame.OutputGenetic.append(affichage);
               }
            
            // Time complexity 
            long tempsFin = System.nanoTime();
        	double seconds = (tempsFin - tempsDebut) / 1e9;
        	totalTime = totalTime + seconds;
        	
        	 Frame.OutputGenetic.append("\n Opération effectuée en : " + seconds + " secondes.");
        	        	
        	// Space complexity 
        	Runtime run = Runtime.getRuntime();
            double usedMem = run.totalMemory()-run.freeMemory();
            usedMem = usedMem / 1024 / 1024;
            
            Frame.OutputGenetic.append("\n Espace mémoire occupé : " + usedMem + " MB."
            		+ "\n ---------------------------------------------------------------------");
            }
        
        Runtime run = Runtime.getRuntime();
        double usedMem = run.totalMemory()-run.freeMemory();
        usedMem = usedMem / 1024 / 1024; 
        
        
        
        Frame.OutputGenetic.append("\n --------------------------------------------------------------------- \n"
        		+ "\n Temps Total : " + totalTime + " en secondes || " + (totalTime/60) + " en minutes." );
        Frame.OutputGenetic.append("\n Temps moyen par instance : " + (totalTime/100) + " secondes.");
        Frame.OutputGenetic.append("\n Total Espace mémoire occupé : " + usedMem + " MB.");
        Frame.OutputGenetic.append("\n Espace mémoire moyen par instance : " + (usedMem/100) + " MB."
        		+"\n --------------------------------------------------------------------- \n"); 

	}
}