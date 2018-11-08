import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

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
		/*
		 * JFrame interArrivalFrame = new JFrame(); interArrivalFrame.setSize(500, 500);
		 * interArrivalFrame.add(interArrivalTable.table);
		 * interArrivalFrame.setVisible(true);
		 */

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
		/*
		 * Frame serviceTimeFrame = new JFrame(); serviceTimeFrame.setSize(500, 500);
		 * serviceTimeFrame.add(serviceTimeTable.table);
		 * serviceTimeFrame.setVisible(true);
		 */
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

		/*
		 * JFrame randomInterArrivalFrame = new JFrame();
		 * randomInterArrivalFrame.setSize(500, 500);
		 * randomInterArrivalFrame.add(interArrivalRandomTable.table);
		 * randomInterArrivalFrame.setVisible(true);
		 */

		int numberOfCustomers = 20;
		Table serviceTimeRandomTable = new Table(numberOfCustomers, 3);

		for (int i = 0; i < numberOfCustomers; i++) {
			float n = (float) (0.001 + random.nextFloat() * (1 - 0.001));
			int randomValue = Range.getRangeProbability(serviceTimeRange, n) + 1;
			serviceTimeRandomTable.setValue(i, 0, i + 1 + "");
			serviceTimeRandomTable.setValue(i, 1, n + "");
			serviceTimeRandomTable.setValue(i, 2, randomValue + "");
		}

		/*
		 * JFrame serviceTimeRandomFrame = new JFrame();
		 * serviceTimeRandomFrame.setSize(500, 500);
		 * serviceTimeRandomFrame.add(serviceTimeRandomTable.table);
		 * serviceTimeRandomFrame.setVisible(true);
		 */

		ArrayList<SimulationTableRecord> record1 = new ArrayList<SimulationTableRecord>();
		ArrayList<SimulationTableRecord> record2 = new ArrayList<SimulationTableRecord>();
		ArrayList<SimulationTableRecord> recordTotal = new ArrayList<SimulationTableRecord>();
		int sr = Integer.parseInt(serviceTimeRandomTable.getCell(0, 2));
		record1.add(new SimulationTableRecord(1, 0, 0, sr, 0, 0, sr, 0, 0));
		record2.add(new SimulationTableRecord(0, 0, 0, 0, 0, 0, 0, 0, 0));
		recordTotal.add(record1.get(0));
		int nextAvailableTime = sr;
		LinkedList<Integer> carQueue = new LinkedList<Integer>();
		carQueue.add(1);
		int arrivalTime = 0;
		for (int i = 1, j = 1, k = 1; k < numberOfCustomers; k++) {

			
			SimulationTableRecord record = new SimulationTableRecord();
			int interArrivalTime = Integer.parseInt(interArrivalRandomTable.getCell(k, 2));
			int CurrentArrivalTime = arrivalTime + interArrivalTime;
			if (nextAvailableTime <= CurrentArrivalTime) {
				carQueue.removeFirst();
			}

			if (carQueue.size() < 3) {
				record.customerNumber = k + 1;
				record.interArrivalTime = interArrivalTime;
				record.serviceTime = Integer.parseInt(serviceTimeRandomTable.getCell(k, 2));
				record.arrivalTime = CurrentArrivalTime;
				arrivalTime = record.arrivalTime;
				// time Service begins condition, if not busy start at once
				if (record1.get(i - 1).timeServiceEnds <= record.arrivalTime) {
					record.timeServiceBegins = record.arrivalTime;
					record.serverIdleTime = record.arrivalTime - record1.get(i - 1).timeServiceEnds;
				} else {
					record.timeServiceBegins = record1.get(i - 1).timeServiceEnds;
					record.serverIdleTime = 0;
				}
				record.waitingTimeInQueue = record.timeServiceBegins - record.arrivalTime;
				record.timeServiceEnds = record.timeServiceBegins + record.serviceTime;
				record.timeSpentInSystem = record.timeServiceEnds - record.arrivalTime;
				nextAvailableTime = record.timeServiceEnds;
				record1.add(record);
				carQueue.add(record.customerNumber);
				i++;
			} else {
				// Same code as the second else, i made it like that for the lack of proper goto
				// operator in java
				record.customerNumber = k + 1;
				record.interArrivalTime = interArrivalTime;
				record.serviceTime = Integer.parseInt(serviceTimeRandomTable.getCell(k, 2));
				record.arrivalTime = CurrentArrivalTime;
				arrivalTime = record.arrivalTime;
				// time Service begins condition, if not busy start at once
				if (record2.get(j - 1).timeServiceEnds <= record.arrivalTime) {
					record.timeServiceBegins = record.arrivalTime;
					record.serverIdleTime = record.arrivalTime - record2.get(j - 1).timeServiceEnds;
				} else {
					record.timeServiceBegins = record2.get(j - 1).timeServiceEnds;
					record.serverIdleTime = 0;
				}
				record.waitingTimeInQueue = record.timeServiceBegins - record.arrivalTime;
				record.timeServiceEnds = record.timeServiceBegins + record.serviceTime;
				record.timeSpentInSystem = record.timeServiceEnds - record.arrivalTime;

				record2.add(record);
				j++;
			}
			recordTotal.add(record);
		}
		//Table for the drive in Teller
		Table sim = SimulationTableRecord.getTableRepresentation(record1.toArray().length, record1);
		String headers3[] = { "Customer", "Inter-Arrival Time", "Arrival Time", "Service Time", "Service Begins",
				"Waiting", "Service ends", "Time Spent", "Idle" };
		sim.setTitles(headers3);
		
		//Table for the in bank teller
		Table sim2 = SimulationTableRecord.getTableRepresentation(record2.toArray().length, record2);
		sim2.setTitles(headers3);
		
		
		//Combined Table of tellers
		Table sim3 = SimulationTableRecord.getTableRepresentation(recordTotal.toArray().length, recordTotal);
		sim3.setTitles(headers3);
		

		JTabbedPane panel=new JTabbedPane();
		panel.addTab("Drive In", new JScrollPane(sim.table));
		panel.addTab("In Bank", new JScrollPane(sim2.table));
		panel.addTab("Combined", new JScrollPane(sim3.table));
		JFrame result =new JFrame("Results");
		result.setSize(1000, 500);
		result.add(panel);
		result.setVisible(true);
	}

}
