package Search;



public class NoeudSearch extends Noeud implements Comparable<Object> {

	
	private static ClauseSet clset;
	// value is the f(x) value
    //private  int value; 
    private int valHeuristique ;
    public NoeudSearch(int[]  values) {
        super(values);
        valHeuristique = clset.getNumberclauses()-clset.satisfiedClauses(values);

    }

    public static ClauseSet getclset() {
        return clset;
    }

    public static void setclset(ClauseSet clset) {
    	NoeudSearch.clset = clset;
    }

    public int getValHeuristique() {
        return valHeuristique;
    }

    public void setValHeuristique(int valHeuristique) {
        this.valHeuristique = valHeuristique;
    }
   
    
    public boolean isSolution(){
        return valHeuristique==0 ;
    
    }
    public int compareTo(Object o){
        if(o==null){return 1;}
        if(!( o instanceof NoeudSearch)){return 1;}
        NoeudSearch np = (NoeudSearch)o;
        if(getValHeuristique()>np.getValHeuristique()) return 1;
        if(getValHeuristique()<np.getValHeuristique()) return -1;
        return 0;
        }
    public NoeudSearch getFils0(){
        if(getVal()==null) return new NoeudSearch(new int[]{0});
        int[] a = new int[getVal().length+1];
        for (int i = 0; i <getVal().length; i++) {
            a[i]=getVal()[i];
        }
        a[a.length-1]=0;
    return new NoeudSearch(a);
    }
    
    
    public NoeudSearch getFils1(){
        if(getVal()==null) return new NoeudSearch(new int[]{1});
        int[] a = new int[getVal().length+1];
        for (int i = 0; i <getVal().length; i++) {
            a[i]=getVal()[i];
        }
        a[a.length-1]=1;
        return new NoeudSearch(a);
    }
    

    
    
}