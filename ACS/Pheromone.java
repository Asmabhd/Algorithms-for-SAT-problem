package ACS;
public class Pheromone {
	
	public Double[][]pheromonMatrice=new Double[75][2];
	public  double t0;
	public  double ro;
	
    public Pheromone(double t0,double ro) {
	   this.ro=ro;
       this.t0 = t0;
       for (int i = 0; i < 75; i++)
        pheromonMatrice[i][0] = pheromonMatrice[i][1] = 0.1;
     }	

    public void offline(Solution best) {
	  for (int j = 0; j < 75; j++) {
		if (best.sat[j] == 0) {
			pheromonMatrice[j][0] = (1 - ro) * pheromonMatrice[j][0] + ro * (325- best.fitnes);
			pheromonMatrice[j][1] = (1 - ro) * pheromonMatrice[j][1];
			 }
		    else {
			pheromonMatrice[j][0] = (1 - ro) * pheromonMatrice[j][0];
			pheromonMatrice[j][1] = (1 - ro) * pheromonMatrice[j][1] + ro* (325 - best.fitnes);
		    }
	    }
     }

     public void online(Solution best) {
	  for (int j = 0; j < 75; j++) {
		if (best.sat[j] == 0) {
			pheromonMatrice[j][0] = (1 - ro) * pheromonMatrice[j][0] + ro * t0;
			pheromonMatrice[j][1] = (1 - ro) * pheromonMatrice[j][1];
			} 
		    else {
		    pheromonMatrice[j][1] = (1 - ro) * pheromonMatrice[j][1] + ro * t0;
			pheromonMatrice[j][0] = (1 - ro) * pheromonMatrice[j][0];
		    }
	    }
     }
} 
	
