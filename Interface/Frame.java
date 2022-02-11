package Interface;


	import java.awt.CardLayout;
	import java.awt.Color;
	import java.awt.Font;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.io.File;
	import java.io.IOException;

	import javax.swing.BorderFactory;
	import javax.swing.DefaultComboBoxModel;
	import javax.swing.ImageIcon;
	import javax.swing.JButton;
	import javax.swing.JComboBox;
	import javax.swing.JFileChooser;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JPanel;
	import javax.swing.JScrollPane;
	import javax.swing.JSpinner;
	import javax.swing.JTabbedPane;
	import javax.swing.JTextArea;
	import javax.swing.JTextField;
	import javax.swing.SpinnerNumberModel;
	import javax.swing.UIManager;
	import javax.swing.border.Border;
	import javax.swing.border.EmptyBorder;
	import javax.swing.filechooser.FileNameExtensionFilter;

	import ACS.ACS_Algorithm;
	import ACS.ACS_Solution;
	import ACS.Ant;
	import ACS.TestACS;
	import Genetic.Genetic_Test;
	import Genetic.Genetic_Algo;
	
	import Search.Search;
	import Search.ClauseSet;
	
	

	public class Frame extends JFrame {
		
		private static final long serialVersionUID = 1L;
		private JPanel contentPane;
		public static JTextArea OutputGenetic;
		public static JTextArea OutputACS ;
		

		public Frame(String title) {
			setTitle(title);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 1000, 550);
			setResizable(false);
		
			
		try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			} catch(Exception ignored){}

			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);

			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.setBounds(0, 0, 1000, 550);
			contentPane.add(tabbedPane);	
				
			JPanel dataPanel = new JPanel();
			tabbedPane.addTab("DFS et A*", null, dataPanel, null);
			dataPanel.setLayout(null);

			ResultPanel resultPanel = new ResultPanel();
			tabbedPane.addTab("Résultats et statistiques", null, resultPanel, null);
			tabbedPane.setEnabledAt(1, false);

			JPanel GAPanel = new JPanel();
			tabbedPane.addTab(" Algorithme génétique ", null, GAPanel, null);
			GAPanel.setLayout(null);
		

			JPanel ACSPanel = new JPanel();
			tabbedPane.addTab(" Algorithme ACS ", null, ACSPanel, null);
			ACSPanel.setLayout(null);
			
			JLabel informationLabel2 = new JLabel("Bienvenue dans le solveur SAT");
			informationLabel2.setFont(new Font("Dialog", Font.BOLD, 22));
			informationLabel2.setForeground(Color.decode("#FFFFFF"));
			informationLabel2.setBounds(340, 5, 800, 25);
			dataPanel.add(informationLabel2);
			
			
			ImageIcon bg = new ImageIcon(this.getClass().getResource("fond.png"));	

			JLabel fond = new JLabel("",bg,JLabel.CENTER);
			fond.setBounds(0,0,1000,40);

			JLabel b = new JLabel("");
			b.setBounds(0,0, 1000, 40);
			b.add(fond);
			dataPanel.add(b);
			
			
			
			JLabel informationLabel = new JLabel("");
			informationLabel.setFont(new Font("Dialog", Font.BOLD, 12));
			informationLabel.setForeground(Color.decode("#370028"));
			informationLabel.setBounds(505, 28, 800, 50);
			dataPanel.add(informationLabel);
			
			

			ClausePanel clausesPanel = new ClausePanel();
			dataPanel.add(clausesPanel);
			
			JLabel informationLabel3 = new JLabel("Veuillez ajouter un fichier CNF Benchmark :");
			informationLabel3.setFont(new Font("Dialog", Font.PLAIN, 15));
			informationLabel3.setForeground(Color.decode("#370028"));
			informationLabel3.setBounds(70, 70, 300, 16);
			dataPanel.add(informationLabel3);
			
			JButton importFileButton = new JButton("Importer le fichier");
			importFileButton.setBounds(120, 100, 150, 38);  
			dataPanel.add(importFileButton);
			
			
            JLabel selectMethodLabel = new JLabel("Sélectionnez la méthode de résolution SAT:");
            selectMethodLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
			selectMethodLabel.setBounds(70, 160, 300, 16);
			dataPanel.add(selectMethodLabel);
			
			JComboBox<String> resMethodComboBox = new JComboBox<String>();
			resMethodComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Depth-First Search (DFS)",  "Heuristic Search (A*)"}));
			resMethodComboBox.setBounds(80, 200, 237, 25);
			dataPanel.add(resMethodComboBox);

			JLabel demarerLabel = new JLabel("Démarrer la résolution :");
			demarerLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
			demarerLabel.setBounds(70, 250, 260, 16);
			dataPanel.add(demarerLabel);

			JButton startResButton = new JButton("Démarrer");
			startResButton.setEnabled(false);
			startResButton.setBounds(120, 290, 150, 38);
			dataPanel.add(startResButton);
			
			JLabel numAttemptsLabel = new JLabel("Nombre de Tentatives :");
			numAttemptsLabel.setBounds(70, 360, 130, 25);
			dataPanel.add(numAttemptsLabel);
			
			
	        JSpinner numAttemptsSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 100, 1));
			numAttemptsSpinner.setBounds(260, 360, 50, 23);
			dataPanel.add(numAttemptsSpinner);
			
			
			JLabel timeAttemptLabel = new JLabel("Temps par tentative (sec) :");
			timeAttemptLabel.setBounds(70, 410, 170, 25);
			dataPanel.add(timeAttemptLabel);

			JSpinner timeAttemptSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 3600, 1));
			timeAttemptSpinner.setBounds(260, 410, 50, 23);
			dataPanel.add(timeAttemptSpinner);

			
			JPanel optionsPanel = new JPanel();
			optionsPanel.setBounds(348, 74, 281, 196);
			dataPanel.add(optionsPanel);
			optionsPanel.setLayout(new CardLayout(0, 0));
						
            
			
			resMethodComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CardLayout cardLayout = (CardLayout) optionsPanel.getLayout();

					switch(resMethodComboBox.getSelectedIndex()) {
						// case 2:
							// cardLayout.show(optionsPanel, "heuristic"); /* "heuristicOptions" was disabled until other heuristics will be added */
							// break;
						case 3:
							cardLayout.show(optionsPanel, "ga");
							break;
						case 4:
							cardLayout.show(optionsPanel, "Ant");
							break;
						
						default:
							cardLayout.show(optionsPanel, "default");
					}
				}
			});

			importFileButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
					fileChooser.setAcceptAllFileFilterUsed(false);
					fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Conjunctive Normal Form (.cnf)", "cnf"));
					fileChooser.showOpenDialog(null);

					try {
						informationLabel.setText(clausesPanel.loadClausesSet(fileChooser.getSelectedFile().getAbsolutePath()));
						startResButton.setEnabled(true);
					} catch (NullPointerException | IOException ignore) {}
				}
			});

			startResButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					resultPanel.clearData();

					long startResolution; 
					long timeAttempt = Long.parseLong(timeAttemptSpinner.getValue().toString())*1000;
					ClauseSet clset = clausesPanel.getClausesSet();
					String methodName = "";

					resultPanel.setUpperBound(clset.getNumberclauses());

					switch(resMethodComboBox.getSelectedIndex()) {
						case 0:
							informationLabel.setText("Instance SAT résolue à l'aide de \"Depth-First Search (DFS)\"");
							methodName = "DFS";
 
							for(int i=0; i< Integer.parseInt(numAttemptsSpinner.getValue().toString()); i++) {
								startResolution = System.currentTimeMillis();
								resultPanel.addDataDFS(clset, Search.DepthFirstSearch(clset, timeAttempt),
													System.currentTimeMillis() - startResolution > timeAttempt ? timeAttempt : System.currentTimeMillis() - startResolution, i+1);
							}

							break;

 
						case 1:
							/* "heuristicOptions" was disabled until other heuristics will be added */
							// informationLabel.setText("SAT instance resolved using \"Heuristic "+heuristicOption.getSelectedHeuristicRadio()+"\"");
							informationLabel.setText("Instance SAT résolue à l'aide de \"Recherche Heuristique (A*)\"");
							methodName = "A*";

							for(int i=0; i< Integer.parseInt(numAttemptsSpinner.getValue().toString()); i++) {
								startResolution = System.currentTimeMillis();
								resultPanel.addData(clset, Search.AEtoile(clset, timeAttempt),
										System.currentTimeMillis() - startResolution > timeAttempt ? timeAttempt : System.currentTimeMillis() - startResolution, i+1);
							}

							break;
							
					}

					resultPanel.makeTitle(methodName);

					tabbedPane.setEnabledAt(1, true);
				}
			});
		
	 ////////////////////////////////////////////// GENETIC ////////////////////////////////////////////////////////
  
		
		
		JLabel fondG = new JLabel("",bg,JLabel.CENTER);
		fondG.setBounds(490, 20, 460, 440);
		
		JLabel bG = new JLabel("");
    	bG.setBounds(490, 20, 460, 440);
    	
    	
    	// params
    	
    	 JLabel title1 = new JLabel("Veuillez saisir les paramètres :");
		 title1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
		 title1.setBounds(40, 50, 300, 20);
		 GAPanel.add(title1);
		
		 JTextArea pop = new JTextArea("La Taille de la Population");
		 pop.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
		 pop.setEditable(false);
		 pop.setOpaque(false);
		 pop.setBounds(60, 100, 200, 40);
		 GAPanel.add(pop);
		 
		 JTextField txtPopSize = new JTextField("50");
		 txtPopSize.setBounds(250, 100, 70, 20);
		 GAPanel.add(txtPopSize);
		 txtPopSize.setColumns(10);
		 
		 
		 JTextArea cross = new JTextArea("Le Taux de Croissement");
		 cross.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
		 cross.setEditable(false);
		 cross.setOpaque(false);
		 cross.setBounds(60, 150, 200, 40);
		 GAPanel.add(cross);
		  
		 JTextField txtCrsovrR = new JTextField("0.01");
		 txtCrsovrR.setBounds(250, 150, 70, 20);
		 GAPanel.add(txtCrsovrR);
		 txtCrsovrR.setColumns(10);
		 
		 JTextArea muta = new JTextArea("Le Taux de Mutation");
		 muta.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
		 muta.setEditable(false);
		 muta.setOpaque(false);
		 muta.setBounds(60, 200, 200, 40);
		 GAPanel.add(muta);
		 
		 JTextField txtMutationR = new JTextField("0.95");
		 txtMutationR.setBounds(250, 200, 70, 20);
		 GAPanel.add(txtMutationR);
		 txtMutationR.setColumns(10);
		 
		 JTextArea elite = new JTextArea("Nombre d'Elitisme");
		 elite.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
		 elite.setEditable(false);
		 elite.setOpaque(false);
		 elite.setBounds(60, 250, 200, 40);
		 GAPanel.add(elite);
		  
		 JTextField txtEliteNb = new JTextField("200");
		 txtEliteNb.setBounds(250, 250, 70, 20);
		 GAPanel.add(txtEliteNb);
		 txtEliteNb.setColumns(10);
		// bouton temps d'execution
		 JLabel timeAttemptLabel1 = new JLabel("Temps par (sec) :");
			timeAttemptLabel1.setBounds(70, 410, 170, 25);
			GAPanel.add(timeAttemptLabel1);

		JSpinner timeAttemptSpinner1 = new JSpinner(new SpinnerNumberModel(1, 1, 3600, 1));
			timeAttemptSpinner1.setBounds(260, 410, 50, 23);
			GAPanel.add(timeAttemptSpinner1);
		 
		 
	 // bouton 1 instance
		 JButton test = new JButton("Tester");
		 test.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 		  OutputGenetic.setText("\"********************* ALGORITHME GENETIQUE *********************\r\n"
			 	    		+ "	------------------------------------------------------------------------------------------\n");
		 		 long timeAttempt = Long.parseLong(timeAttemptSpinner1.getValue().toString())*1000;
		 		// les valeurs par défaut
		 		
		 		if(!txtPopSize.getText().isEmpty()) {
		 			Genetic_Algo.PS = Integer.parseInt(txtPopSize.getText().trim());
		 		}else Genetic_Algo.PS = 4000;
		 		
		 		if(!txtMutationR.getText().isEmpty()) {
		 			Genetic_Algo.MR = Double.parseDouble(txtMutationR.getText().trim());
		 		}else Genetic_Algo.MR = 0.01;
		 		
		 		if(!txtCrsovrR.getText().isEmpty()) {
		 			Genetic_Algo.CR = Double.parseDouble(txtCrsovrR.getText().trim());
		 		}else Genetic_Algo.CR = 0.95;
		 		
		 		if(!txtEliteNb.getText().isEmpty()) {
		 			Genetic_Algo.EC = Integer.parseInt(txtEliteNb.getText().trim());
		 		}else Genetic_Algo.EC = 200;
		 		
		 		Genetic_Test.TestGenetic(timeAttempt);
		 	}
		 });
		 
		 test.setFont(new Font("Arial", Font.BOLD, 12));
		 test.setBounds(140, 300, 90, 25);
		 GAPanel.add(test);
		 
		 JLabel lbl1instance_1_1 = new JLabel("(une instance)");
		 lbl1instance_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
		 lbl1instance_1_1.setBounds(250, 300, 100, 20);
		 GAPanel.add(lbl1instance_1_1);
		 
		
			
		 //bouton 5 instances
		 		 JButton excuteButton = new JButton("Exécuter");
		 		excuteButton.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 	    OutputGenetic.setText("\"********************* ALGORITHME GENETIQUE *********************\r\n"
		 	    		+ "	------------------------------------------------------------------------------------------\n");
		 	   long timeAttempt = Long.parseLong(timeAttemptSpinner1.getValue().toString())*1000;
		 		if(!txtPopSize.getText().isEmpty()) {
		 			Genetic_Algo.PS = Integer.parseInt(txtPopSize.getText().trim());
		 		}else Genetic_Algo.PS = 4000;
		 		
		 		if(!txtMutationR.getText().isEmpty()) {
		 			Genetic_Algo.MR = Double.parseDouble(txtMutationR.getText().trim());
		 		}else Genetic_Algo.MR = 0.01;
		 		
		 		if(!txtCrsovrR.getText().isEmpty()) {
		 			Genetic_Algo.CR = Double.parseDouble(txtCrsovrR.getText().trim());
		 		}else Genetic_Algo.CR = 0.95;
		 		
		 		if(!txtEliteNb.getText().isEmpty()) {
		 			Genetic_Algo.EC = Integer.parseInt(txtEliteNb.getText().trim());
		 		}else Genetic_Algo.EC = 200;
		 		
		 		Genetic_Algo.mainGenetic(timeAttempt);
		 	}
		 });
		 excuteButton.setFont(new Font("Arial", Font.BOLD, 12));
		 excuteButton.setBounds(140, 370, 90, 25);
		 GAPanel.add(excuteButton);
		 
		 JLabel lblAllinstance_1_1 = new JLabel("(5 instances)");
		 lblAllinstance_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
		 lblAllinstance_1_1.setBounds(250, 370, 100, 20);
		 GAPanel.add(lblAllinstance_1_1);
		
		Border border = BorderFactory.createLineBorder(Color.PINK);
		
		     OutputGenetic = new JTextArea("********************* ALGORITHME GENETIQUE *********************\r\n"
			 		+ "------------------------------------------------------------------------------------------\n");
			 
		     OutputGenetic.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
			 OutputGenetic.setBounds(500, 20, 450, 440);
			 OutputGenetic.setEditable(false);
			
			 JScrollPane scrollResult = new JScrollPane(OutputGenetic);
			 scrollResult.setSize(400, 400);
			 scrollResult.setLocation(520, 40);
			 
			 GAPanel.add(scrollResult);
		     GAPanel.add(fondG);
		     
		 	
////////////////////////////////////////////// ACS ////////////////////////////////////////////////////////
		  		
	
	
		JLabel fondA = new JLabel("",bg,JLabel.CENTER);
		fondA.setBounds(490, 20, 460, 440);
		
		JLabel bA = new JLabel("");
    	bA.setBounds(490, 20, 460, 440);
    	
    
    	// params
    	
    	 JLabel titleA = new JLabel("Veuillez saisir les paramètres :");
    	 titleA.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 18));
    	 titleA.setBounds(40, 50, 300, 20);
    	 ACSPanel.add(titleA);
		
		
		 
		
		 
		 
		 JTextArea lblT0 = new JTextArea("Valeur initiale Phénomène :");
		 lblT0.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
		 lblT0.setEditable(false);
		 lblT0.setOpaque(false);
		 lblT0.setBounds(60, 100, 200, 40);
		 ACSPanel.add(lblT0);
		 
		 JTextArea lblT00 = new JTextArea("Valeur T0 :");
		 lblT00.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
		 JTextField txtVphT0 = new JTextField("0.7");
		 txtVphT0.setColumns(10);
		 txtVphT0.setBounds(250, 100, 70, 20);
		 ACSPanel.add(txtVphT0);
		
		 
		 JTextArea alpha = new JTextArea("Taux Alpha : ");
		 alpha.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
		 alpha.setEditable(false);
		 alpha.setOpaque(false);
		 alpha.setBounds(60, 150, 200, 40);
		 ACSPanel.add(alpha);
		  
		 JTextField txtalpha = new JTextField("1");
		 txtalpha.setBounds(250, 150, 70, 20);
		 ACSPanel.add(txtalpha);
		 txtalpha.setColumns(10);
		 
		 JTextArea betha = new JTextArea("Taux Betha : ");
		 betha.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
		 betha.setEditable(false);
		 betha.setOpaque(false);
		 betha.setBounds(60, 200, 200, 40);
		 ACSPanel.add(betha);
		 
		 JTextField txtbetha = new JTextField("1");
		 txtbetha.setBounds(250, 200, 70, 20);
		 ACSPanel.add(txtbetha);
		 txtbetha.setColumns(10);
		 
		 JTextArea eva = new JTextArea("Taux d'évaporation : ");
		 eva.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
		 eva.setEditable(false);
		 eva.setOpaque(false);
		 eva.setBounds(60, 250, 200, 40);
		 ACSPanel.add(eva);
		  
		 JTextField txteva = new JTextField("1");
		 txteva.setBounds(250, 250, 70, 20);
		 ACSPanel.add(txteva);
		 txteva.setColumns(10);
		 
		 // bouton temps d'exec
		 JLabel timeAttemptLabel11 = new JLabel("Temps par (sec) :");
			timeAttemptLabel11.setBounds(70, 410, 170, 25);
			ACSPanel.add(timeAttemptLabel11);

			JSpinner timeAttemptSpinner11 = new JSpinner(new SpinnerNumberModel(1, 1, 3600, 1));
			timeAttemptSpinner11.setBounds(260, 410, 50, 23);
			ACSPanel.add(timeAttemptSpinner11);
			
	 // bouton 1 instance
		 JButton testA = new JButton("Tester");
		 testA.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 		
		 		OutputACS.setText("\"********************* ALGORITHME ACS *********************\r\n"
			 	    		+ "	------------------------------------------------------------------------------------------\n");
		 		long timeAttempt = Long.parseLong(timeAttemptSpinner11.getValue().toString())*1000;	
		 		// les valeurs par défaut
		 		
		 		if(!txtVphT0.getText().isEmpty()) {
		 			// valeur initial phenoeme 
		 			ACS_Solution.t0 = Double.parseDouble(txtVphT0.getText().trim());
		 		}else ACS_Solution.t0 = 0.7;
		 		
		 		
		 		
		 		if(!txtalpha.getText().isEmpty()) {
		 			
		 		//  variable  alpha 
		 			
		 			Ant.alpha = Double.parseDouble(txtalpha.getText().trim());
		 		}else Ant.alpha = 1;
		 		
		 	// variable  betha 
	 			
		 		if(!txtbetha.getText().isEmpty()) {
		 			Ant.betha = Double.parseDouble(txtbetha.getText().trim());
		 		}else Ant.betha = 1;
		 		
		 		if(!txteva.getText().isEmpty()) {
		 		//  variable de taux d'evaluation
		 			
		 			Ant.evaluation = Integer.parseInt(txteva.getText().trim());
		 		}else Ant.evaluation = 325;
		 		
		 		//  une instnce
		 		/* Genetic_Test.TestGenetic();*/
		 		
		 		TestACS.ACSTest(timeAttempt);
		 	}
		 });
		 
		 testA.setFont(new Font("Arial", Font.BOLD, 12));
		 testA.setBounds(140, 300, 90, 25);
		 ACSPanel.add(testA);
		 
		 JLabel Oneinstance = new JLabel("(une instance)");
		 Oneinstance.setFont(new Font("Arial", Font.PLAIN, 12));
		 Oneinstance.setBounds(250, 300, 100, 20);
		 ACSPanel.add(Oneinstance);
		 
		 
		 
		 //bouton 5 instances
		 		 JButton excuteButtonACP = new JButton("Exécuter");
		 		excuteButtonACP.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		
		 		OutputACS.setText("\"********************* ALGORITHME ACS *********************\r\n"
		 	    		+ "	------------------------------------------------------------------------------------------\n");
		 		
				
// les valeurs par défaut
		 		long timeAttempt = Long.parseLong(timeAttemptSpinner11.getValue().toString())*1000;
		 		if(!txtVphT0.getText().isEmpty()) {
		 			// valeur initial phenoeme 
		 			ACS_Solution.t0 = Double.parseDouble(txtVphT0.getText().trim());
		 		}else ACS_Solution.t0 = 0.7;
		 		
		 		
		 		if(!txtalpha.getText().isEmpty()) {
		 			
		 		//  variable  alpha 
		 			
		 			Ant.alpha = Double.parseDouble(txtalpha.getText().trim());
		 		}else Ant.alpha = 1;
		 		
		 	// variable  betha 
	 			
		 		if(!txtbetha.getText().isEmpty()) {
		 			Ant.betha = Double.parseDouble(txtbetha.getText().trim());
		 		}else Ant.betha = 1;
		 		
		 		if(!txteva.getText().isEmpty()) {
		 		//  variable de taux d'evaluation
		 			
		 			Ant.evaluation = Integer.parseInt(txteva.getText().trim());
		 		}else Ant.evaluation = 325;
		 		
		 		
		 	//la methode de  5 instnce
		 		ACS_Algorithm.mainACS(timeAttempt);
		 	}
		 });
		 excuteButtonACP.setFont(new Font("Arial", Font.BOLD, 12));
		 excuteButtonACP.setBounds(140, 370, 90, 25);
		 ACSPanel.add(excuteButtonACP);
		 
		 JLabel fiveinstances = new JLabel("(5 instances)");
		 fiveinstances.setFont(new Font("Arial", Font.PLAIN, 12));
		 fiveinstances.setBounds(250, 370, 100, 20);
		 ACSPanel.add(fiveinstances);
		
		
		     OutputACS = new JTextArea("**************************** ALGORITHME ACS ************************\r\n"
			 		+ "------------------------------------------------------------------------------------------\n");
			 
		     OutputACS.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		     OutputACS.setBounds(500, 20, 450, 440);
		     OutputACS.setEditable(false);
			
			 JScrollPane scrollACS = new JScrollPane(OutputACS);
			 scrollACS.setSize(400, 400);
			 scrollACS.setLocation(520, 40);
			 
			 ACSPanel.add(scrollACS);
			 ACSPanel.add(fondA);
		     
		
	}
}
