package Search;



public class Noeud {
    protected int[] Val ;

    public Noeud(int [] vals  ) {
        this.Val =vals;
    }
 
    public int[] getVal() {
        return Val;
    }

    public void setEnsVal(int[] vals) {
        this.Val = vals;
    }
    
    public String toString(){
        String s = "[";
        if(Val.length>0) s+=Val[0];
        for (int i = 1; i < Val.length; i++) {
            s+=","+Val[i];
        }
        return s+"]";
    }
    
   }