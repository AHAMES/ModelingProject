import java.util.ArrayList;

public class SimulationTableRecord {

	//This object is used to create a record in a table
	//Contains the components of a table
	private int customerNumber;
	private int interArrivalTime;
	private int arrivalTime;
	private int serviceTime;
	private int timeServiceBegins;
	private int waitingTimeInQueue;
	private int timeServiceEnds;
	private int timeSpentInSystem;
	private int serverIdleTime;
	private String whichQueue;
	//////////////////////////
	/// getters
	/////////////////////////

	public String getWhichQueue() {
		return whichQueue;
	}

	public int getCustomerNumber() {
		return customerNumber;
	}

	public int getInterArrivalTime() {
		return interArrivalTime;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getServiceTime() {
		return serviceTime;
	}

	public int getTimeServiceBegins() {
		return timeServiceBegins;
	}

	public int getWaitingTimeInQueue() {
		return waitingTimeInQueue;
	}

	public int getTimeServiceEnds() {
		return timeServiceEnds;
	}

	public int getServerIdleTime() {
		return serverIdleTime;
	}

	public int getTimeSpentInSystem() {
		return timeSpentInSystem;
	}

	//////////////////////////
	/// setters
	/////////////////////////
	public void setWhichQueue(String cn) {
		whichQueue = cn;
	}

	public void setCustomerNumber(int cn) {
		customerNumber = cn;
	}

	public void setInterArrivalTime(int IAT) {
		interArrivalTime = IAT;
	}

	public void setArrivalTime(int AT) {
		arrivalTime = AT;
	}

	public void setServiceTime(int AT) {
		serviceTime = AT;
	}

	public void setTimeServiceBegins(int AT) {
		timeServiceBegins = AT;
	}

	public void setWaitingTimeInQueue(int AT) {
		waitingTimeInQueue = AT;
	}

	public void setTimeServiceEnds(int AT) {
		timeServiceEnds = AT;
	}

	public void setServerIdleTime(int AT) {
		serverIdleTime = AT;
	}

	public void setTimeSpentInSystem(int AT) {
		timeSpentInSystem = AT;
	}

	//////////////////////////
	/// Constructor
	/////////////////////////
	public SimulationTableRecord() {

	}

	public SimulationTableRecord(String WQ, int CN, int IAT, int AT, int ST, int TSB, int WTQ, int TSE, int TSS,
			int SIT) {
		whichQueue = WQ;
		customerNumber = CN;
		interArrivalTime = IAT;
		arrivalTime = AT;
		serviceTime = ST;
		timeServiceBegins = TSB;
		waitingTimeInQueue = WTQ;
		timeServiceEnds = TSE;
		timeSpentInSystem = TSS;
		serverIdleTime = SIT;
	}

	// Creates and returns a table to for the records
	// Uses normal array
	public static Table getTableRepresentation(int numberOfCustomers, SimulationTableRecord records[]) {
		
		Table simulation = new Table(numberOfCustomers, 10);
		
		for (int i = 0; i < records.length; i++) {
			simulation.setValue(i, 0, records[i].whichQueue);
			simulation.setValue(i, 1, "" + records[i].customerNumber);
			simulation.setValue(i, 2, "" + records[i].interArrivalTime);
			simulation.setValue(i, 3, "" + records[i].arrivalTime);
			simulation.setValue(i, 4, "" + records[i].serviceTime);
			simulation.setValue(i, 5, "" + records[i].timeServiceBegins);
			simulation.setValue(i, 6, "" + records[i].waitingTimeInQueue);
			simulation.setValue(i, 7, "" + records[i].timeServiceEnds);
			simulation.setValue(i, 8, "" + records[i].timeSpentInSystem);
			simulation.setValue(i, 9, "" + records[i].serverIdleTime);
		}

		return simulation;
	}

	// Creates and returns a table to for the records
	// Uses array list
	public static Table getTableRepresentation(int numberOfCustomers, ArrayList<SimulationTableRecord> records) {
		Table simulation = new Table(numberOfCustomers, 10);
		for (int i = 0; i < records.size(); i++) {
			simulation.setValue(i, 0, records.get(i).whichQueue);
			simulation.setValue(i, 1, "" + records.get(i).customerNumber);
			simulation.setValue(i, 2, "" + records.get(i).interArrivalTime);
			simulation.setValue(i, 3, "" + records.get(i).arrivalTime);
			simulation.setValue(i, 4, "" + records.get(i).serviceTime);
			simulation.setValue(i, 5, "" + records.get(i).timeServiceBegins);
			simulation.setValue(i, 6, "" + records.get(i).waitingTimeInQueue);
			simulation.setValue(i, 7, "" + records.get(i).timeServiceEnds);
			simulation.setValue(i, 8, "" + records.get(i).timeSpentInSystem);
			simulation.setValue(i, 9, "" + records.get(i).serverIdleTime);
		}

		return simulation;
	}

	// Returns an Answer of required answers
	public static Answer getAnswers(ArrayList<SimulationTableRecord> driveIn, 
			                        ArrayList<SimulationTableRecord> inBank,
			                        ArrayList<SimulationTableRecord> total) {

		double averageServiceTime = 0;
		double averageWaitingTime = 0;
		for (int i = 0; i < driveIn.size(); i++) {
			averageServiceTime += driveIn.get(i).serviceTime;
			averageWaitingTime += driveIn.get(i).waitingTimeInQueue;
		}
		double driveInServiceTimeAvg = averageServiceTime / driveIn.size();
		double driveInWaitingTime = averageWaitingTime / driveIn.size();

		averageServiceTime = 0;
		averageWaitingTime = 0;
		double countWaiting = 0, countIdle = 0;

		int totalIdle=0;
		for(SimulationTableRecord x: total)
		{
			totalIdle+=x.serverIdleTime;
		}
		for (int i = 1; i < inBank.size(); i++) {
			averageServiceTime += inBank.get(i).serviceTime;
			averageWaitingTime += inBank.get(i).waitingTimeInQueue;
			if (inBank.get(i).waitingTimeInQueue > 0) {
				countWaiting++;
			}
			if (inBank.get(i).getServerIdleTime() > 0) {
				countIdle+=inBank.get(i).getServerIdleTime();
			}
		}

		int longestQueue = 1;
		int current = 1;

		for (int i = 1; i < inBank.size(); i++) {

			int x = inBank.get(i).getCustomerNumber();
			int y = inBank.get(i - 1).getCustomerNumber();
			if (x == (y + 1)) {
				current++;
			} else {
				if (current > longestQueue) {
					longestQueue = current;
				}
				current = 1;
			}
		}

	
		// The first inBank record is a dummy, so it is ignored and the real size is Size-1.

		//this condition is placed to prevent 0/0 NaN results when the bank queue size is 0.
		if ((inBank.size() - 1) == 0) {
			return new Answer(driveInServiceTimeAvg, driveInWaitingTime, 0, 0, 0, 0, 0);
		}
		return new Answer(driveInServiceTimeAvg, driveInWaitingTime, averageServiceTime / (inBank.size() - 1),
				averageWaitingTime / (inBank.size() - 1), countWaiting / (inBank.size() - 1),
				countIdle / totalIdle, longestQueue);
	}

}
