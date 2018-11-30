import java.awt.Color;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class StorageGraph {
	
	int cycle;
	int storage;
	
	public StorageGraph()
	{
		cycle=0;
		storage=0;
	}
	
	public StorageGraph(int cycle, int storage)
	{
		this.cycle=cycle;
		this.storage=storage;
	}
	
	public static JFreeChart createGraph(StorageGraph[] graph)
	{
		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		
		for(int i=0;i<graph.length;i++)
		{
			dataset.addValue(graph[i].storage, "Storage", graph[i].cycle+"");
		}
		JFreeChart lineChart = ChartFactory.createLineChart(
		         "Storage Usage",
		         "Cycles","Cars in Storage",
		         dataset,
		         PlotOrientation.VERTICAL,
		         true,true,false);
		return lineChart;
	}
	
	public static JFreeChart createGraph(StorageGraph[][] graph)
	{
		DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
		
		for(int i=0;i<graph.length;i++)
		{
			dataset.addValue(graph[0][i].storage, "Storage", graph[0][i].cycle+"");
			dataset.addValue(graph[1][i].storage, "Demand", graph[1][i].cycle+"");
			dataset.addValue(graph[2][i].storage, "Order", graph[2][i].cycle+"");

		
		}
		JFreeChart lineChart = ChartFactory.createLineChart(
		         "Storage Usage",
		         "Cycles","Cars",
		         dataset,
		         PlotOrientation.VERTICAL,
		         true,true,false);
		lineChart.getPlot().setBackgroundPaint(Color.DARK_GRAY);
		return lineChart;
	}
	
	public static StorageGraph[] getAverage(ArrayList<StorageGraph[]> xStorageGraphs)
	{
		StorageGraph [] yStorageGraphs=new StorageGraph[xStorageGraphs.get(0).length];
		
		for(int i=0;i<xStorageGraphs.get(0).length;i++)
		{
			yStorageGraphs[i]=new StorageGraph();
			yStorageGraphs[i].cycle=i+1;
			for(int j=0;j<xStorageGraphs.size();j++)
			{
				yStorageGraphs[i].storage+= xStorageGraphs.get(j)[i].storage;
			}
			yStorageGraphs[i].storage/=xStorageGraphs.size();
		}
		return yStorageGraphs;
	}
}
