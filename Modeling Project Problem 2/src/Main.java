import java.awt.ScrollPane;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.xml.ws.AsyncHandler;

public class Main {

	public static void main(String[] args) {

		//Three assumptions to ask Carl
		//1) The minimum is 4 or any number of my liking
		//2) The lead time is calculated per cycle
		//meaning that if i get 5 leaed times, but i order at cycle 3, 
		//I will ignore the 2 before it
		//3) I only order at the beginning of the cycle
		//Meaning that even if i get shortage in the middle of the cycle
		//I will still ignore it and wait 
		
		//Minimum number in storage after which we must order a new shipment
		int minimumVehicle=Integer.parseInt(JOptionPane.showInputDialog("Input Minimum Vehicles"));
		int n = Integer.parseInt(JOptionPane.showInputDialog("Input N"));
		int numberOfCycles = Integer.parseInt(JOptionPane.showInputDialog("Input number of Cycles"));
		
		// Initializing given probability tables
		double[] DemandProbabilityList = new double[5];
		double[] LeadTimeList = new double[3];

		// Initializing the Demand table
		DemandProbabilityList[0] = (double) 0.05;
		DemandProbabilityList[1] = (double) 0.28;
		DemandProbabilityList[2] = (double) 0.37;
		DemandProbabilityList[3] = (double) 0.20;
		DemandProbabilityList[4] = (double) 0.10;

		// Initializing the Lead time table
		LeadTimeList[0] = (double) 0.55;
		LeadTimeList[1] = (double) 0.35;
		LeadTimeList[2] = (double) 0.1;

		// Cumulative probability used in the tables
		double cumulativeDemandlProbability = (double) 0.001;
		double cumulativeLeadProbability = (double) 0.001;

		// probability table length
		int length1 = DemandProbabilityList.length;
		int length2 = LeadTimeList.length;

		//////////// Demand Probability table building/////////
		//////////////////////////////////////////////////////

		// Demand Probability Range
		Range[] demandRange = new Range[5];
		demandRange[0] = new Range();

		// Initializing the first range is done manually
		demandRange[0].first = cumulativeDemandlProbability;
		demandRange[0].second = DemandProbabilityList[0];

		// Initializing the demand probability table
		// First record is done manually
		Table DemandTable = new Table(length1, 4);
		// Adding header to table
		String headers[] = { "Demand", "Probability", "Cumulative Probability", "Range" };
		DemandTable.setTitles(headers);
		DemandTable.setValue(0, 0, 0 + "");
		DemandTable.setValue(0, 1, DemandProbabilityList[0] + "");
		DemandTable.setValue(0, 2, DemandProbabilityList[0] + "");
		DemandTable.setValue(0, 3, demandRange[0].toString());
		cumulativeDemandlProbability = DemandProbabilityList[0];

		// Populating the rest of the table in a loop
		for (int i = 1; i < length1; i++) {
			demandRange[i] = new Range();
			DecimalFormat df=new DecimalFormat();
			df.setMaximumFractionDigits(2);
			demandRange[i].first = (Double.parseDouble((df.format(cumulativeDemandlProbability))) * 100) + 1;
			demandRange[i].first /= 100;
			cumulativeDemandlProbability += Double.parseDouble(df.format((DemandProbabilityList[i])));
			cumulativeDemandlProbability =Double.parseDouble(df.format((cumulativeDemandlProbability)));
			demandRange[i].second = Double.parseDouble(df.format((cumulativeDemandlProbability)));
			DemandTable.setValue(i, 0, i + "");
			DemandTable.setValue(i, 1, DemandProbabilityList[i] + "");
			DemandTable.setValue(i, 2, cumulativeDemandlProbability + "");
			DemandTable.setValue(i, 3, demandRange[i].toString());

		}

		// Showing the result in a JFrame

			
		 /*JFrame DemandFrame = new JFrame("Demand Probability");
		 DemandFrame.setSize(500, 500); DemandFrame.add(DemandTable.table);
		 DemandFrame.setVisible(true);*/
		 

		//////////////////////////////////////////////////////

		//////////////////////////////////////////////////////
		//////////// Service time table building/////////
		//////////////////////////////////////////////////////

		// Service time Probability Range
		Range[] leadRange = new Range[3];
		leadRange[0] = new Range();

		// Initializing the first range is done manually
		leadRange[0].first = cumulativeLeadProbability;
		leadRange[0].second = LeadTimeList[0];

		// Initializing the inter-arrival probability table
		// First record is done manually
		Table leadTable = new Table(length2, 4);

		// Adding header to table
		String headers2[] = { "Lead", "Probability", "Cumulative Probability", "Range" };

		leadTable.setTitles(headers2);
		leadTable.setValue(0, 0, 1 + "");
		leadTable.setValue(0, 1, LeadTimeList[0] + "");
		leadTable.setValue(0, 2, LeadTimeList[0] + "");
		leadTable.setValue(0, 3, leadRange[0].toString());
		cumulativeLeadProbability = LeadTimeList[0];

		// Populating the rest of the table in a loop
		for (int i = 1; i < length2; i++) {
			DecimalFormat df=new DecimalFormat();
			df.setMaximumFractionDigits(2);
			leadRange[i] = new Range();
			leadRange[i].first = Double.parseDouble((df.format((cumulativeLeadProbability) * 100))) + 1;
			leadRange[i].first /= 100;
			cumulativeLeadProbability += Double.parseDouble(df.format(LeadTimeList[i]));
			cumulativeLeadProbability = Double.parseDouble((df.format(cumulativeLeadProbability))) ;
			leadRange[i].second = cumulativeLeadProbability;
			leadTable.setValue(i, 0, i + 1 + "");
			leadTable.setValue(i, 1, LeadTimeList[i] + "");
			leadTable.setValue(i, 2, cumulativeLeadProbability + "");
			leadTable.setValue(i, 3, leadRange[i].toString());

		}
		// Showing the result in a JFrame

		
		 JFrame leadFrame = new JFrame("Service Probability"); leadFrame.setSize(500,
		 500); leadFrame.add(leadTable.table); leadFrame.setVisible(true);
		 

		//////////////////////////////////////////////////////

		//////////////////////////////////////////////////////
		/// Random values in the table
		//////////////////////////////////////////////////////

	
		int carsInStorage = 6;
		int orderSize = 5;
		
		int daysToNextOrder = 2;
		Table demandRandomTable = new Table(n * numberOfCycles, 3);
		Random random = new Random();

		for (int i = 0; i < numberOfCycles * n; i++) {
			double x = (double) (0.001 + random.nextDouble() * (1 - 0.001));
			int randomValue = Range.getRangeProbability(demandRange, x);
			demandRandomTable.setValue(i, 0, i + 1 + "");
			demandRandomTable.setValue(i, 1, x + "");
			demandRandomTable.setValue(i, 2, randomValue + "");
		}

		/*
		 * JFrame randomDemandFrame = new JFrame("Demand Table");
		 * randomDemandFrame.setSize(500, 500);
		 * randomDemandFrame.add(demandRandomTable.table);
		 * randomDemandFrame.setVisible(true);
		 */

		Table leadRandomTable = new Table(numberOfCycles, 3);

		for (int i = 0; i < numberOfCycles; i++) {
			double x = (double) (0.001 + random.nextDouble() * (1 - 0.001));
			int randomValue = Range.getRangeProbability(leadRange, x) + 1;
			leadRandomTable.setValue(i, 0, i + 1 + "");
			leadRandomTable.setValue(i, 1, x + "");
			leadRandomTable.setValue(i, 2, randomValue + "");
		}
		/*
		 * JFrame randomLeadFrame = new JFrame("Lead Table");
		 * randomLeadFrame.setSize(500, 500);
		 * randomLeadFrame.add(leadRandomTable.table); randomLeadFrame.setVisible(true);
		 */

		// Assume that the minimum is 3 cars because there is already an order for 5
		// when the inventory amount is 2, meaning that the minimum is either 2 or 3
		// 3 Sounds the most safe choice
		// Assume the number of cycles is 10 because it is unclear in the requirements

		ArrayList<SimulationTableRecord> record1 = new ArrayList<SimulationTableRecord>();
		int firstDemand = Integer.parseInt(demandRandomTable.getCell(0, 2));
		SimulationTableRecord record = new SimulationTableRecord(1, 1, carsInStorage, firstDemand,
				carsInStorage - firstDemand, 0, orderSize, daysToNextOrder);
		record1.add(record);
		
		//This queue of orders keeps track of the orders sent for cars
		//The assumption is that you cannot order cars before 
		//the current order arrives
		
		
		int k = 1;
		for (int i = 0; i < numberOfCycles; i++) {

			for (int j = 0; j < n; j++) {

				record = new SimulationTableRecord();
				if (i == 0 && j == 0) {
					continue;
				}
				record.setCycle(i + 1);
				record.setDay(j + 1);
				////////////////////////////////
				//This parts doesn't work well
				//Beginning Inventory
				////////////////////////////////
				if(daysToNextOrder!=0)
				{
					
					record.setDaysToArrival(--daysToNextOrder);
				}
				SimulationTableRecord previousRecord = record1.get(k - 1);
				record.setBeginningInventory(previousRecord.getEndingInventory());
				if(daysToNextOrder==0)
				{
					record.setBeginningInventory(record.getBeginningInventory()+orderSize);
					orderSize=0;
				}
				
				////////////////////////////////
				//This parts works well
				//Ending Inventory and demand
				////////////////////////////////
				
				int currentDemand = Integer.parseInt(demandRandomTable.getCell(k, 2));
				record.setDemand(currentDemand);
				
				if((record.getBeginningInventory()-currentDemand)<0)
				{
					record.setEndingInventory(0);
					record.setShortageQuatity(Math.abs(record.getBeginningInventory()-currentDemand)+previousRecord.getShortageQuatity());
				}
				else {
					record.setEndingInventory(record.getBeginningInventory()-currentDemand);
					record.setShortageQuatity(0);
				}
				
				if(record.getCycle()>previousRecord.getCycle() &&
						orderSize==0 && record.getEndingInventory()<=minimumVehicle)
				{
					orderSize=12-record.getEndingInventory();
					daysToNextOrder=Integer.parseInt(leadRandomTable.getCell(i, 2));
					record.setOrderQuantity(orderSize);
					record.setDaysToArrival(daysToNextOrder);
				}
				
				////////////////////////////////
				////////////////////////////////
				
				
				
				k++;
				record1.add(record);
			}
		}

		Table storageSim = SimulationTableRecord.getTableRepresentation(n * numberOfCycles, record1);
		String headers3[] = { "Cycle", "Day", "Beginning Inventory", "Demand", "Ending Inventory", "Shortage Quatity",
				"Order Quantity", "Days To Arrival" };
		storageSim.setTitles(headers3);
		JFrame resultsFrame = new JFrame("Results Table");
		resultsFrame.setSize(900, 500);
		resultsFrame.add(new JScrollPane(storageSim.table));
		resultsFrame.setVisible(true);
	}

}
