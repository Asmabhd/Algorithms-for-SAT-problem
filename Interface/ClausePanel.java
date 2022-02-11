package Interface;


	import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JPanel;
	import javax.swing.JScrollPane;
	import javax.swing.JTable;
	import javax.swing.table.DefaultTableModel;
	import Search.ClauseSet;
  // le tableau 
	public class ClausePanel extends JPanel{
		private static final long serialVersionUID = 1L;

		private JTable clausesTable;
		private ClauseSet clset;

		public ClausePanel() {
			setBounds(500, 70, 450, 390);
			setLayout(new BorderLayout(0, 0));

			clausesTable = new JTable();
			clausesTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
			clausesTable.setEnabled(false);
			clausesTable.setRowSelectionAllowed(false);
			add(clausesTable, BorderLayout.CENTER);
			add(new JScrollPane(clausesTable));
		}


		public String loadClausesSet(String cnf_filePath) throws IOException {
			
			clset = new ClauseSet(cnf_filePath);
			
			DefaultTableModel tableModel = new DefaultTableModel();
			tableModel.addColumn("Clause");

			for(int i=0; i<clset.getLengthclauses(); i++)
				tableModel.addColumn("Literal "+(i+1));

			String[] tableRow = new String[clset.getLengthclauses()+1];

			for(int i=0; i<clset.getNumberclauses(); i++) {
				tableRow[0] = String.valueOf(i);

				for(int j=1; j<=clset.getLengthclauses(); j++)
					tableRow[j] = String.valueOf(clset.getClause(i).getLiteral(j-1));

				tableModel.addRow(tableRow);
			}

			clausesTable.setModel(tableModel);

			return "Instance SAT chargée :  "+clset.getNumberclauses()+"  clauses,  "+clset.getNumberVariables()+"  variables,  "+clset.getLengthclauses()+"  variables/clause";
		}


		public ClauseSet getClausesSet() { return clset; }
	}