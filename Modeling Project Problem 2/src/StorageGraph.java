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
	
	public static StorageGraph[] getAverage(ArrayList<StorageGraph[]> xStorageGraphs)
	{
		StorageGraph [] yStorageGraphs=new StorageGraph[xStorageGraphs.size()];
		
		for(int i=0;i<xStorageGraphs.size();i++)
		{
			yStorageGraphs[i]=new StorageGraph();
			yStorageGraphs[i].cycle=i+1;
			for(int j=0;j<xStorageGraphs.get(i).length;j++)
			{
				yStorageGraphs[i].storage+=xStorageGraphs.get(i)[j].storage;
			}
			yStorageGraphs[i].storage = yStorageGraphs[i].storage/xStorageGraphs.get(i).length;
		}
		
		return yStorageGraphs;
	}
}
