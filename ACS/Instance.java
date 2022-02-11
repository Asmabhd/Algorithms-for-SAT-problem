package ACS;
import java.util.ArrayList;

public class Instance {
	
	public ArrayList<Clause> Clause = new ArrayList<>();
	int nbr_clause;


	public int getNumber_clause() {
		return nbr_clause;
	}

	public void setNumber_clause(int number_clause) {
		this.nbr_clause = number_clause;
	}

	public ArrayList<Clause> getClause() {
		return Clause;
	}

	public void setClause(ArrayList<Clause> Clause) {
		this.Clause = Clause;
	}

	public Instance(ArrayList<Clause> Clause, int nbr_clause) {
		this.Clause = Clause;
		this.nbr_clause = nbr_clause;
	}
}
