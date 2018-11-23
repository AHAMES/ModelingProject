import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;


public class CustomerGraph {
	
	/*ArrayList<Range> ranges;
	ArrayList<Integer> counts;

	public CustomerGraph(int wantedInterval, int lastArrivalTime) {
		ranges = new ArrayList<>();
		counts = new ArrayList<>();
		for (int i = 0; i < lastArrivalTime; i += wantedInterval) {
			if ((i + wantedInterval) > lastArrivalTime) {
				ranges.add(new Range(i, lastArrivalTime));
				counts.add(0);
				continue;
			}
			ranges.add(new Range(i, i + wantedInterval));
			counts.add(0);
		}
	}

	public JFreeChart getGraph(ArrayList<SimulationTableRecord> record) {

		DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
		
		for (int i = 0; i < record.size(); i++) {
			int k = Range.getRange(ranges, record.get(i).getArrivalTime());
			int m = counts.get(k);
			m++;
			counts.set(k, m);
		}
		for (int i = 0; i < ranges.size(); i++) {
			dataset1.addValue(counts.get(i), ranges.get(i).first+"" , "Arrival Time");
		}
		JFreeChart barChart = ChartFactory.createBarChart("Arrival Stats", "Time of Arrival", "Number Of People", dataset1,
				PlotOrientation.VERTICAL, true, true, false);
		return barChart;
	}
	
	public static JFreeChart getGraph(CustomerGraph xCustomerGraph) {
		DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
		for (int i = 0; i < xCustomerGraph.ranges.size(); i++) {
			dataset1.addValue(xCustomerGraph.counts.get(i), xCustomerGraph.ranges.get(i).first+"" , "Arrival Time");
		}
		JFreeChart barChart = ChartFactory.createBarChart("Arrival Stats", "Time of Arrival", "Number Of People", dataset1,
				PlotOrientation.VERTICAL, true, true, false);
		return barChart;
	}*/

	public static JFreeChart getGraph(double [] dataset1) {
		HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.FREQUENCY);
        
		dataset.addSeries("Hist", dataset1, 20);
		
		JFreeChart barChart = ChartFactory.createHistogram("Arrival Stats", "Time of Arrival", "Number Of People", dataset, PlotOrientation.VERTICAL, true, true, false);
		return barChart;
	}
	
	/*public static CustomerGraph getAverage(ArrayList<CustomerGraph> customerGraphs,int highestRangeSize)
	{
		CustomerGraph xCustomerGraph=new CustomerGraph(20, highestRangeSize);
		
		for(int i=0;i<customerGraphs.size();i++)
		{
			
			for(int j = 0; j < customerGraphs.get(i).counts.size(); j++)
			{
				int x = customerGraphs.get(i).counts.get(j);
				int m = xCustomerGraph.counts.get(j);
				xCustomerGraph.counts.set(j, m + x );
			}
		}
		for(int i=0;i<xCustomerGraph.counts.size();i++)
		{
			int m = xCustomerGraph.counts.get(i);
			xCustomerGraph.counts.set(i, m);
		}
		
		return xCustomerGraph;
	}*/
}
