package Genetic;

public class Individu {
// hada il représente une solution 
// chaque chromosome rah ykon fih 75 genes 

	private int[] chromosome;
	private int fitness = -1;// to keep track of the individual's fitness
	
	//1st Constructor using an integer array as the individual itself
	public Individu(int[] chr) {
		chromosome = chr;
	}
	
	//2nd Constructor using a chromosome's length to initialize the individuals randomly 
	public Individu(int chromosomeLength) {
		this.chromosome = new int[chromosomeLength];
		for (int gene = 0; gene < chromosomeLength; gene++) {
			if (0.5 < Math.random()) {
				this.setGene(gene, 1);
				}
			else {
				this.setGene(gene, 0);
				}
			}
		}
	
	//Getters and Setters
	public int[] getChromosome() {
		return this.chromosome;
		}
	
	public int getChromosomeLength() {
		return this.chromosome.length;
		}
	
	public void setGene(int offset, int gene) {
		this.chromosome[offset] = gene;
		}
	
	public int getGene(int offset) {
		return this.chromosome[offset];
		}
	
	public void setFitness(int fitness) { 
		this.fitness = fitness;
		}
	
	public int getFitness() {
		return this.fitness;
		}
	
	//calculating fitness 
    public int calcFitness() {
    	int fit = Genetic_Algo.verify(chromosome);
    	this.setFitness(fit);
    	return fit;
    	}

	
	//Print method
	public String toString() {
		String output = " ";
		for (int gene = 0; gene < this.chromosome.length; gene++) {
			output += this.chromosome[gene];
			}
			return output;
		}
}
