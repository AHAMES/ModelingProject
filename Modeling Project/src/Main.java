import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Main {

	public static void main(String args[]) {

		// If float problem happens

		// Initializing given probability tables
		float[] interArrivalTimeProbabilityList = new float[6];
		float[] serviceTimeProbabilityList = new float[4];

		// Initializing the inter-arrival time table
		interArrivalTimeProbabilityList[0] = (float) 0.09;
		interArrivalTimeProbabilityList[1] = (float) 0.17;
		interArrivalTimeProbabilityList[2] = (float) 0.27;
		interArrivalTimeProbabilityList[3] = (float) 0.20;
		interArrivalTimeProbabilityList[4] = (float) 0.15;
		interArrivalTimeProbabilityList[5] = (float) 0.12;

		// Initializing the service time table
		serviceTimeProbabilityList[0] = (float) 0.20;
		serviceTimeProbabilityList[1] = (float) 0.40;
		serviceTimeProbabilityList[2] = (float) 0.28;
		serviceTimeProbabilityList[3] = (float) 0.12;

		// Cumulative probability used in the tables
		float cumulativeInterArrivalProbability = (float) 0.001;
		float cumulativeServiceTimeProbability = (float) 0.001;

		// probability table length
		int length1 = interArrivalTimeProbabilityList.length;
		int length2 = serviceTimeProbabilityList.length;

		//////////// Inter-Arrival time table building/////////
		//////////////////////////////////////////////////////

		// Inter-Arrival Probability Range
		Range[] interArrivalRange = new Range[6];
		interArrivalRange[0] = new Range();

		// Initializing the first range is done manually
		interArrivalRange[0].first = cumulativeInterArrivalProbability;
		interArrivalRange[0].second = interArrivalTimeProbabilityList[0];

		// Initializing the inter-arrival probability table
		// First record is done manually
		Table interArrivalTable = new Table(length1, 4);
		// Adding header to table
		String headers[] = { "Inter-Arrival Time", "Probability", "Cumulative Probability", "Range" };
		interArrivalTable.setTitles(headers);
		interArrivalTable.setValue(0, 0, 0 + "");
		interArrivalTable.setValue(0, 1, interArrivalTimeProbabilityList[0] + "");
		interArrivalTable.setValue(0, 2, interArrivalTimeProbabilityList[0] + "");
		interArrivalTable.setValue(0, 3, interArrivalRange[0].toString());
		cumulativeInterArrivalProbability = interArrivalTimeProbabilityList[0];

		// Populating the rest of the table in a loop
		for (int i = 1; i < length1; i++) {
			interArrivalRange[i] = new Range();
			interArrivalRange[i].first = (cumulativeInterArrivalProbability * 100) + 1;
			interArrivalRange[i].first /= 100;
			cumulativeInterArrivalProbability += interArrivalTimeProbabilityList[i];
			interArrivalRange[i].second = cumulativeInterArrivalProbability;
			interArrivalTable.setValue(i, 0, i + "");
			interArrivalTable.setValue(i, 1, interArrivalTimeProbabilityList[i] + "");
			interArrivalTable.setValue(i, 2, cumulativeInterArrivalProbability + "");
			interArrivalTable.setValue(i, 3, interArrivalRange[i].toString());

		}

		// Showing the result in a JFrame
		JFrame interArrivalFrame = new JFrame();
		interArrivalFrame.setSize(500, 500);
		interArrivalFrame.add(interArrivalTable.table);
		interArrivalFrame.setVisible(true);

		//////////////////////////////////////////////////////

		//////////// Service time table building/////////
		//////////////////////////////////////////////////////

		// Service time Probability Range
		Range[] serviceTimeRange = new Range[4];
		serviceTimeRange[0] = new Range();

		// Initializing the first range is done manually
		serviceTimeRange[0].first = cumulativeServiceTimeProbability;
		serviceTimeRange[0].second = serviceTimeProbabilityList[0];

		// Initializing the inter-arrival probability table
		// First record is done manually
		Table serviceTimeTable = new Table(length2, 4);

		// Adding header to table
		String headers2[] = { "Service Time", "Probability", "Cumulative Probability", "Range" };

		serviceTimeTable.setTitles(headers2);
		serviceTimeTable.setValue(0, 0, 1 + "");
		serviceTimeTable.setValue(0, 1, serviceTimeProbabilityList[0] + "");
		serviceTimeTable.setValue(0, 2, serviceTimeProbabilityList[0] + "");
		serviceTimeTable.setValue(0, 3, serviceTimeRange[0].toString());
		cumulativeServiceTimeProbability = serviceTimeProbabilityList[0];

		// Populating the rest of the table in a loop
		for (int i = 1; i < length2; i++) {
			serviceTimeRange[i] = new Range();
			serviceTimeRange[i].first = (cumulativeServiceTimeProbability * 100) + 1;
			serviceTimeRange[i].first /= 100;
			cumulativeServiceTimeProbability += serviceTimeProbabilityList[i];
			serviceTimeRange[i].second = cumulativeServiceTimeProbability;
			serviceTimeTable.setValue(i, 0, i + 1 + "");
			serviceTimeTable.setValue(i, 1, serviceTimeProbabilityList[i] + "");
			serviceTimeTable.setValue(i, 2, cumulativeServiceTimeProbability + "");
			serviceTimeTable.setValue(i, 3, serviceTimeRange[i].toString());

		}
		// Showing the result in a JFrame
		JFrame serviceTimeFrame = new JFrame();
		serviceTimeFrame.setSize(500, 500);
		serviceTimeFrame.add(serviceTimeTable.table);
		serviceTimeFrame.setVisible(true);
		//////////////////////////////////////////////////////

		Table interArrivalRandomTable = new Table(20, 3);
		Random random = new Random();
		interArrivalRandomTable.setValue(0, 0, 1 + "");
		interArrivalRandomTable.setValue(0, 1, "-");
		interArrivalRandomTable.setValue(0, 2, "-");

		for (int i = 1; i < 20; i++) {
			float n = (float) (0.001 + random.nextFloat() * (1 - 0.001));
			int randomValue = Range.getRangeProbability(interArrivalRange, n);
			interArrivalRandomTable.setValue(i, 0, i + 1 + "");
			interArrivalRandomTable.setValue(i, 1, n + "");
			interArrivalRandomTable.setValue(i, 2, randomValue + "");
		}

		JFrame randomInterArrivalFrame = new JFrame();
		randomInterArrivalFrame.setSize(500, 500);
		randomInterArrivalFrame.add(interArrivalRandomTable.table);
		randomInterArrivalFrame.setVisible(true);

		int numberOfCustomers = 10;
		Table serviceTimeRandomTable = new Table(numberOfCustomers, 3);

		for (int i = 0; i < numberOfCustomers; i++) {
			float n = (float) (0.001 + random.nextFloat() * (1 - 0.001));
			int randomValue = Range.getRangeProbability(serviceTimeRange, n) + 1;
			serviceTimeRandomTable.setValue(i, 0, i + 1 + "");
			serviceTimeRandomTable.setValue(i, 1, n + "");
			serviceTimeRandomTable.setValue(i, 2, randomValue + "");
		}

		JFrame serviceTimeRandomFrame = new JFrame();
		serviceTimeRandomFrame.setSize(500, 500);
		serviceTimeRandomFrame.add(serviceTimeRandomTable.table);
		serviceTimeRandomFrame.setVisible(true);

		SimulationTableRecord[] record = new SimulationTableRecord[numberOfCustomers];
		record[0] = new SimulationTableRecord();
		record[0].customerNumber = 1;
		record[0].interArrivalTime = 0;
		record[0].arrivalTime = 0;
		record[0].serviceTime = Integer.parseInt(serviceTimeRandomTable.getCell(0, 2));
		record[0].timeServiceBegins = 0;
		record[0].waitingTimeInQueue = 0;
		record[0].timeServiceEnds = record[0].serviceTime;
		record[0].timeSpentInSystem = record[0].serviceTime;
		record[0].serverIdleTime = 0;
		Queue<SimulationTableRecord> queue= new LinkedList<SimulationTableRecord>();
		int customerInQueue = 1;
		int customerInBank = 1;
		queue.add(record[0]);
		for (int i = 1; i < numberOfCustomers; i++) {
			record[i] = new SimulationTableRecord();
			record[i].customerNumber = i + 1;
			record[i].interArrivalTime = Integer.parseInt(interArrivalRandomTable.getCell(i, 2));

			record[i].serviceTime = Integer.parseInt(serviceTimeRandomTable.getCell(i, 2));
			record[i].arrivalTime = record[i].interArrivalTime + record[i - 1].arrivalTime;

			int x = record[i].arrivalTime;
			int y = record[customerInQueue - 1].timeServiceEnds;

			if(!queue.isEmpty())
			if(queue.peek().timeServiceEnds<=record[i].arrivalTime)
			{
				queue.remove();
			}
			if (queue.size()<2) {

				if (record[customerInQueue - 1].timeServiceEnds >= record[i].arrivalTime) {
					record[i].timeServiceBegins = record[customerInQueue - 1].timeServiceEnds;
					record[i].serverIdleTime = 0;
					queue.add(record[i]);
					
				} else {
					record[i].timeServiceBegins = record[i].arrivalTime;
					record[i].serverIdleTime = record[i].arrivalTime 
							- record[customerInQueue - 1].timeServiceEnds;
					queue.remove();
				}
				customerInQueue = i;
				record[i].timeServiceEnds = record[i].serviceTime + record[i].timeServiceBegins;
			} 
			else {

				System.out.println("Customer #" + record[i].customerNumber + " is in the bank");
				if (record[customerInBank - 1].timeServiceEnds >= record[i].arrivalTime) {
					record[i].timeServiceBegins = record[customerInBank - 1].timeServiceEnds;
					record[i].serverIdleTime = record[i].arrivalTime - record[customerInBank - 1].timeServiceEnds;
				} else {
					record[i].timeServiceBegins = record[i].arrivalTime;
					record[i].serverIdleTime = 0;
				}
				record[i].timeServiceEnds = record[i].serviceTime + record[i].timeServiceBegins;
			
			}
			customerInBank++;
			record[i].waitingTimeInQueue = record[i].timeServiceBegins - record[i].arrivalTime;
			record[i].timeSpentInSystem = record[i].serviceTime + record[i].waitingTimeInQueue;

		}
		JFrame simulationFrame = new JFrame();
		simulationFrame.setSize(1000, 500);
		Table sim = SimulationTableRecord.getTableRepresentation(numberOfCustomers, record);
		String headers3[] = { "Customer", "Inter-Arrival Time", "Arrival Time", "Service Time", "Service Begins",
				"Waiting", "Service ends", "Time Spent", "Idle" };
		sim.setTitles(headers3);
		simulationFrame.add(new JScrollPane(sim.table));
		simulationFrame.setVisible(true);

	}

}
