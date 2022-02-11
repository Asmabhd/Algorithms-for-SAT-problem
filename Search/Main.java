package Search;
import java.lang.String;
/*public class Main {
 

	public static void main(String[] args) {
		try {

			String path = "E:\\fichiers\\Project_BIO\\Algorithms-for-SAT-master\\uf50-01.cnf";
			ClauseSet Clset = ClauseSet.ReadFile(path);

			System.out.println("\nfichier : " + path.substring(44));
			System.out.println("\nSAT  " + Clset.getNumberclauses() + " " + Clset.getNumberVariables());

			
			Search.AEtoile(Clset);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
*/
import Interface.Frame;

public class Main {
	public static void main(String[] args) {

		Frame frame = new Frame("Solveur SAT intelligent");
		frame.setVisible(true);
	}
}
