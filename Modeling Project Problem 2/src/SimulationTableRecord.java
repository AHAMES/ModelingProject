import java.util.ArrayList;

public class SimulationTableRecord {

	private int cycle;
	private int day;
	private int beginningInventory;
	private int demand;
	private int endingInventory;
	private int shortageQuatity;
	private int orderQuantity;
	private int daysToArrival;
	//////////////////////////
	///getters
	/////////////////////////
	public int getCycle()
	{
		return cycle;
	}
	public int getDay()
	{
		return day;
	}
	public int getBeginningInventory()
	{
		return beginningInventory;
	}
	public int getDemand()
	{
		return demand;
	}
	public int getEndingInventory()
	{
		return endingInventory;
	}
	
	public int getShortageQuatity()
	{
		return shortageQuatity;
	}
	
	public int getOrderQuantity()
	{
		return orderQuantity;
	}
	
	public int getDaysToArrival()
	{
		return daysToArrival;
	}
	
	
	//////////////////////////
	///setters
	/////////////////////////
	public void setCycle(int x)
	{
		cycle = x;
	}
	public void setDay(int x)
	{
		day = x;
	}
	public void setBeginningInventory(int x)
	{
		beginningInventory = x;
	}
	public void setDemand(int x)
	{
		demand = x;
	}
	public void setEndingInventory(int x)
	{
		endingInventory = x;
	}
	
	public void setShortageQuatity(int x)
	{
		shortageQuatity = x;
	}
	
	public void setOrderQuantity(int x)
	{
		orderQuantity = x;
	}
	
	public void setDaysToArrival(int x)
	{
		daysToArrival = x;
	}
	
	
	
	//////////////////////////
	///Constructor
	/////////////////////////
	public SimulationTableRecord()
	{
		
	}
	
	public SimulationTableRecord(int CYC,int DAY,int BI,
			int DEM, int END, int SHQ,int ODQ, int DTA)
	{
		day=DAY;
		cycle=CYC;
		beginningInventory=BI;
		demand=DEM;
		endingInventory=END;
		shortageQuatity=SHQ;
		orderQuantity=ODQ;
		daysToArrival=DTA;
	}

	//Creates and returns a table to for the records
	//Uses normal array
	public static Table getTableRepresentation(int numberOfCustomers,
			SimulationTableRecord records[])
	{
		Table simulation = new Table(numberOfCustomers, 9);
		for(int i=0;i<records.length;i++)
		{
			simulation.setValue(i, 0, ""+records[i].cycle);
			simulation.setValue(i, 1, ""+records[i].day);
			simulation.setValue(i, 2, ""+records[i].beginningInventory);
			simulation.setValue(i, 3, ""+records[i].demand);
			simulation.setValue(i, 4, ""+records[i].endingInventory);
			simulation.setValue(i, 5, ""+records[i].shortageQuatity);
			simulation.setValue(i, 6, ""+records[i].orderQuantity);
			simulation.setValue(i, 7, ""+records[i].daysToArrival);
		}
		
		
		return simulation;
	}
	
	
	//Creates and returns a table to for the records
	//Uses array list
	public static Table getTableRepresentation(int numberOfCustomers,
			ArrayList<SimulationTableRecord> records)
	{
		Table simulation = new Table(numberOfCustomers, 8);
		for(int i=0;i<records.size();i++)
		{
			simulation.setValue(i, 0, ""+records.get(i).cycle);
			simulation.setValue(i, 1, ""+records.get(i).day);
			simulation.setValue(i, 2, ""+records.get(i).beginningInventory);
			simulation.setValue(i, 3, ""+records.get(i).demand);
			simulation.setValue(i, 4, ""+records.get(i).endingInventory);
			simulation.setValue(i, 5, ""+records.get(i).shortageQuatity);
			simulation.setValue(i, 6, ""+records.get(i).orderQuantity);
			if(records.get(i).daysToArrival=='-')
			simulation.setValue(i, 7, ""+(char)records.get(i).daysToArrival);
			else
			simulation.setValue(i, 7, ""+records.get(i).daysToArrival);
			
		}
		
		
		return simulation;
	}

}
