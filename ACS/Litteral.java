package ACS;

public class Litteral {
	
     boolean indice; 
     int valeur;
     
     public Litteral(int valeur,boolean indice) {
	 this.indice=indice;
	 this.valeur=valeur;
      }

     public int getvaleur() {
     return valeur;
     }

     public void setvaleur(int valeur) {
     this.valeur = valeur;
     }

     public boolean isindice() {
     return indice;
     }

     public void setIndice(boolean indice) {
     this.indice = indice;
     }
}