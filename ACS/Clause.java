package ACS;
import java.util.ArrayList;

public class Clause {
	
	  ArrayList<Litteral> literaux;
	  int nbr_litteral;


	    public Clause(ArrayList<Litteral> literaux, int numbr_litereaux) {
	        this.literaux = literaux;
	        this.nbr_litteral = numbr_litereaux;
	    }

	    public ArrayList<Litteral> getLiteral() {
	        return literaux;
	    }

	    public void setLiteraux(ArrayList<Litteral> literaux) {
	        this.literaux = literaux;
	    }

	    public int getNumbr_litereaux() {
	        return nbr_litteral;
	    }

	    public void setNumbr_litereaux(int numbr_litereaux) {
	        this.nbr_litteral = numbr_litereaux;
	    }
}
