package Genetic;

import java.util.Arrays;
import java.util.Comparator;

public class Population {
	
	private Individu[] population;
	private int populationFitness = -1;// important for the implementation of the selection method
	
	//1st Constructor using the population size
	public Population(int populationSize) {
		this.population = new Individu[populationSize];
	}
	
	//2nd Constructor using the population size and a specified chromosome length
	public Population(int populationSize, int chromosomeLength) {
		this.population = new Individu[populationSize];
		
		for(int indivCnt = 0; indivCnt < populationSize; indivCnt++) {
			Individu indiv = new Individu(chromosomeLength);
			this.population[indivCnt] = indiv;
		}
	}
	
	//Getters and Setters
	
	public Individu[] getIndividuals() {
		return this.population;
	}
	
	public void setPopulationFitness(int fitness) {
		this.populationFitness = fitness;
	}
	
	public int getPopulationFitness() {
		return this.populationFitness;
	}
	
	public Individu setIndividual(int offset, Individu individual) {
		return population[offset] = individual;
	}
	
	public Individu getIndividual(int offset) {
		return population[offset];
	}
	
	public int size() {
		return this.population.length;
	}
	
	// Sorting the population's individuals by fitness
	public Individu getFittest(int offset) {
		Arrays.sort(this.population, new Comparator<Individu>() {
			@Override
			public int compare(Individu indiv1, Individu indiv2) {
			if (indiv1.getFitness() > indiv2.getFitness()) {
				return -1;
			}
			else
				if (indiv1.getFitness() < indiv2.getFitness()) {
					return 1;
				}
			return 0;
			}
			
		});
		return this.population[offset];
	}
	
}

