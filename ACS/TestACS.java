package ACS;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import Interface.Frame;

public class TestACS {

	 public static ArrayList<Clause> charger(String filename){
	        ArrayList<Clause>instance=new ArrayList<>();
	        ArrayList< Litteral> Litteral;
	        Litteral litereau;
	        int a;
	        
	        BufferedReader reader  = null;
	        String ligne;
	        String[] lig;

	        try{
	        	reader  = new BufferedReader(new FileReader(filename));
	        	//pour sauter les premières lignes
	        	for (int i = 0; i < 8 ; i++) {
					reader.readLine();
				}
	        while ((ligne = reader.readLine()) != null) {
	            if( (ligne.contains("%"))) {break;}
	            	if(ligne.startsWith(" "))	ligne=ligne.replaceFirst(" ", "");
	            	lig=ligne.split(" ");
	            	Litteral=new ArrayList<>();
	                for(int i=0;i<3;i++) {
	                	a=Integer.parseInt(lig[i]);
	                	if(a<0) {
	                		litereau = new  Litteral(a,false);
	                	}else litereau = new  Litteral(a,true);
	                	
	                	Litteral.add(litereau);    
	                }
	                Clause c=new Clause(Litteral,75);
	              instance.add(c);
	            
	        }
	        reader.close();
	        }catch(Exception e){
	            System.out.println(e);
	        }
	    return instance;
	    }
		    
	  public static void ACSTest(long execTimeMillis) {
	  ArrayList<Clause> Clause = new ArrayList<>();

	     Clause = charger("bib/uf75-01.cnf");
	     Instance p = new Instance(Clause, Clause.size());
	     long tempsDebut = System.nanoTime();      		
	     ACS_Solution a = new ACS_Solution();
	     a.ACS_Algo(p,1, execTimeMillis);
	     Runtime run = Runtime.getRuntime();
	     double espace = run.totalMemory() - run.freeMemory();
	     espace = espace/1024/1024;
	     long tempsFin = System.nanoTime(); 
	     double seconds = (tempsFin - tempsDebut) / 1e9; 
	    
	     
		 String affichage1 = " \n Opération effectuée en " + seconds + " secondes.\n"
       	 		+ "avec un espace mémoire de: "+espace+" Mo."
        	 		;
        	
          Frame.OutputACS.append(affichage1
          		+ "\n ---------------------------------------------------------------------");
	            	
		}

}
