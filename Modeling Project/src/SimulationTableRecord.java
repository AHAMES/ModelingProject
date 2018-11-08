import java.util.ArrayList;

public class SimulationTableRecord {

	int customerNumber;
	int interArrivalTime;
	int arrivalTime;
	int serviceTime;
	int timeServiceBegins;
	int waitingTimeInQueue;
	int timeServiceEnds;
	int timeSpentInSystem;
	int serverIdleTime;
	public SimulationTableRecord()
	{
		
	}
	
	public SimulationTableRecord(int CN,int IAT,int AT,
			int ST, int TSB, int WTQ,int TSE, int TSS,
			int SIT)
	{
		customerNumber=CN;
		interArrivalTime=IAT;
		arrivalTime=AT;
		serviceTime=ST;
		timeServiceBegins=TSB;
		waitingTimeInQueue=WTQ;
		timeServiceEnds=TSE;
		timeSpentInSystem=TSS;
		serverIdleTime=SIT;
	}
	
	public static Table getTableRepresentation(int numberOfCustomers,
			SimulationTableRecord records[])
	{
		Table simulation = new Table(numberOfCustomers, 9);
		for(int i=0;i<records.length;i++)
		{
			simulation.setValue(i, 0, ""+records[i].customerNumber);
			simulation.setValue(i, 1, ""+records[i].interArrivalTime);
			simulation.setValue(i, 2, ""+records[i].arrivalTime);
			simulation.setValue(i, 3, ""+records[i].serviceTime);
			simulation.setValue(i, 4, ""+records[i].timeServiceBegins);
			simulation.setValue(i, 5, ""+records[i].waitingTimeInQueue);
			simulation.setValue(i, 6, ""+records[i].timeServiceEnds);
			simulation.setValue(i, 7, ""+records[i].timeSpentInSystem);
			simulation.setValue(i, 8, ""+records[i].serverIdleTime);
		}
		
		
		return simulation;
	}
	
	public static Table getTableRepresentation(int numberOfCustomers,
			ArrayList<SimulationTableRecord> records)
	{
		Table simulation = new Table(numberOfCustomers, 9);
		for(int i=0;i<records.size();i++)
		{
			simulation.setValue(i, 0, ""+records.get(i).customerNumber);
			simulation.setValue(i, 1, ""+records.get(i).interArrivalTime);
			simulation.setValue(i, 2, ""+records.get(i).arrivalTime);
			simulation.setValue(i, 3, ""+records.get(i).serviceTime);
			simulation.setValue(i, 4, ""+records.get(i).timeServiceBegins);
			simulation.setValue(i, 5, ""+records.get(i).waitingTimeInQueue);
			simulation.setValue(i, 6, ""+records.get(i).timeServiceEnds);
			simulation.setValue(i, 7, ""+records.get(i).timeSpentInSystem);
			simulation.setValue(i, 8, ""+records.get(i).serverIdleTime);
		}
		
		
		return simulation;
	}


}
