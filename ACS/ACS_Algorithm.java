package ACS;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import Interface.Frame;

public class ACS_Algorithm {

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
                	}
                	else litereau = new  Litteral(a,true);
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
	 static long tempsDebut = System.nanoTime(); 
      public static void mainACS(long execTimeMillis) {
          double totalTime = 0;
           ArrayList<Clause> Clause = new ArrayList<>();
  
		for(int i=1;i<=5;i++) {
			
			 
           Clause = charger("bib/uf75-0"+(i+1)+".cnf");
           Instance p = new Instance(Clause, Clause.size());
           long tempsDebut = System.nanoTime(); 
          
           ACS_Solution sol = new ACS_Solution();
           sol.ACS_Algo(p,i,execTimeMillis);
           
           Runtime run = Runtime.getRuntime(); // Space complexity 
           double espace = run.totalMemory() - run.freeMemory();
           espace = espace/1024/1024;
           long tempsFin = System.nanoTime();  // Time complexity 
           double seconds = (tempsFin - tempsDebut) / 1e9; 
           //System.out.println("\n Opération effectuée en: "+ seconds + " secondes.\n avec un espace mémoire de: "+espace+" Mo.");
           //if we found the solution that satisfies all the clauses
            
          	 String affichage1 = " \n Opération effectuée en " + seconds + " secondes.\n"
          	 		+ "avec un espace mémoire de: "+espace+" Mo."
           	 		;
           	
             Frame.OutputACS.append(affichage1
             		+ "\n ---------------------------------------------------------------------");
             }
		
		long tempsFin = System.nanoTime();
     	double seconds = (tempsFin - tempsDebut) / 1e9;
     	totalTime = totalTime + seconds;
     	
		    Runtime run = Runtime.getRuntime();
	        double usedMem = run.totalMemory()-run.freeMemory();
	        usedMem = usedMem / 1024 / 1024; 
	        
	        
	        
	      
	        Frame.OutputACS.append("\n Total Espace mémoire occupé : " + usedMem + " MB.");
	        Frame.OutputACS.append("\n Espace mémoire moyen par instance : " + (usedMem/100) + " MB."
	        		+"\n --------------------------------------------------------------------- \n");
		
}
}
