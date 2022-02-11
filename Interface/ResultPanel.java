package Interface;


import java.awt.Color;
import java.awt.Dimension;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.DefaultCategoryDataset;
import Search.ClauseSet;
import Search.NoeudSearch;
import Search.Solution;

public class ResultPanel extends JPanel{
	private static final long serialVersionUID = 1L;
 

	public class Result{ /* le gragh */
		private Solution solution;
		private float satisfiability;
		private long time;


		public Result(ClauseSet clset, Solution solution, long time) {
			this.solution = solution;
			this.satisfiability = (float)solution.satisfiedClauses(clset)/clset.getNumberclauses();
			this.time = time;
		}
		
		public Result(ClauseSet clset, NoeudSearch node, long time) {
			this.satisfiability = (float)clset.satisfiedClauses(node.getVal())/clset.getNumberclauses(); 
			this.time = time;
		}
		

		public Solution getSolution() { return solution; }
		public float getSatisfiability() { return satisfiability; }
		public long getTime() { return time; }
	}


	private DefaultCategoryDataset dataset;
	private JFreeChart barChart;
	private ArrayList<Result> resultData;

	public ResultPanel() {
		
			
		this.dataset = new DefaultCategoryDataset();
		this.resultData = new ArrayList<Result>();

		barChart = ChartFactory.createBarChart("", "", " Clauses Satisfaites", this.dataset, PlotOrientation.VERTICAL, false, false, false);
		Color color1 = new Color(0, 0, 0);// black color
		CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(color1);
		((BarRenderer) plot.getRenderer()).setBarPainter(new StandardBarPainter());
		Color color = new Color(150, 0, 150);
		((BarRenderer) plot.getRenderer()).setSeriesPaint(0, color);
		

		plot.getDomainAxis().setMaximumCategoryLabelLines(10);

		barChart.setBackgroundPaint(Color.decode("#000000"));

		CategoryItemRenderer CatRenderer = ((CategoryPlot) barChart.getPlot()).getRenderer();
		CatRenderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		CatRenderer.setDefaultItemLabelsVisible(true);
		ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.TOP_CENTER);
		CatRenderer.setDefaultPositiveItemLabelPosition(position);

		ChartPanel chartPanel = new ChartPanel( barChart );
		chartPanel.setDomainZoomable(false);
		chartPanel.setRangeZoomable(false);
		chartPanel.setPopupMenu(null);
		chartPanel.setLayout(null);
		chartPanel.setPreferredSize(new Dimension(683, 417));
		add(chartPanel);
		setBackground(Color.decode("#000000"));
	}


	public void setUpperBound(int upperBound) {
		barChart.getCategoryPlot().getRangeAxis().setUpperBound(upperBound);
	}


	public void addData(ClauseSet clset, Solution solution, long time, int numAttempt) {
		Result result = new Result(clset, solution, time);

		resultData.add(result);
		this.dataset.setValue(solution.satisfiedClauses(clset), "SAT", "Tentative "+numAttempt+"\n("+round(time/1000.0, 2)+"  sec)");
		
		
	}
	public void addDataDFS(ClauseSet clset, NoeudSearch node, long time, int numAttempt) {
		Result result = new Result(clset, node, time);

		resultData.add(result);
		this.dataset.setValue(clset.satisfiedClauses(node.getVal()), "SAT", "Tentative "+numAttempt+"\n("+round(time/1000.0, 2)+"  sec)");
	}
		

	public void clearData() {
		resultData.clear();
		dataset.clear();
	}


	public double getSatisfiabilityRate() {
		float sumSatisfiedClausesPerAttempt = 0;

		for(int i=0; i<resultData.size(); i++)
			sumSatisfiedClausesPerAttempt += resultData.get(i).getSatisfiability();

		return round(100*sumSatisfiedClausesPerAttempt/resultData.size(), 7);
	}


	public double getAverageSearchTime() {
		long sumSearchTimePerAttempt = 0;

		for(int i=0; i<resultData.size(); i++)
			sumSearchTimePerAttempt += resultData.get(i).getTime();

		return round(sumSearchTimePerAttempt/resultData.size(), 9);
	}


	public void makeTitle(String searchMethodName) {
		if(! resultData.isEmpty())
			this.barChart.setTitle("Clauses satisfaites par tentative (utilisant \""+searchMethodName+"\")\nSatisfiability rate :  "+getSatisfiabilityRate()+" %  ("
									+getAverageSearchTime()/1000+"  sec)");
		Color color2 = new Color(255, 255, 255);
		this.barChart.getTitle().setPaint(color2);
	}


	private double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
