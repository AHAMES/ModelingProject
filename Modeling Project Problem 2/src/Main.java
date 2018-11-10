import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Main {

	public static void main(String[] args) {

		// Initializing given probability tables
		float[] DemandProbabilityList = new float[5];
		float[] LeadTimeList = new float[3];

		// Initializing the Demand table
		DemandProbabilityList[0] = (float) 0.05;
		DemandProbabilityList[1] = (float) 0.28;
		DemandProbabilityList[2] = (float) 0.37;
		DemandProbabilityList[3] = (float) 0.20;
		DemandProbabilityList[4] = (float) 0.10;

		// Initializing the Lead time table
		LeadTimeList[0] = (float) 0.55;
		LeadTimeList[1] = (float) 0.35;
		LeadTimeList[2] = (float) 0.1;

		// Cumulative probability used in the tables
		float cumulativeDemandlProbability = (float) 0.001;
		float cumulativeLeadProbability = (float) 0.001;

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
			demandRange[i].first = (cumulativeDemandlProbability * 100) + 1;
			demandRange[i].first /= 100;
			cumulativeDemandlProbability += DemandProbabilityList[i];
			demandRange[i].second = cumulativeDemandlProbability;
			DemandTable.setValue(i, 0, i + "");
			DemandTable.setValue(i, 1, DemandProbabilityList[i] + "");
			DemandTable.setValue(i, 2, cumulativeDemandlProbability + "");
			DemandTable.setValue(i, 3, demandRange[i].toString());

		}

		// Showing the result in a JFrame

		/*JFrame DemandFrame = new JFrame("Demand Probability");
		DemandFrame.setSize(500, 500);
		DemandFrame.add(DemandTable.table);
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
			leadRange[i] = new Range();
			leadRange[i].first = (cumulativeLeadProbability * 100) + 1;
			leadRange[i].first /= 100;
			cumulativeLeadProbability += LeadTimeList[i];
			leadRange[i].second = cumulativeLeadProbability;
			leadTable.setValue(i, 0, i + 1 + "");
			leadTable.setValue(i, 1, LeadTimeList[i] + "");
			leadTable.setValue(i, 2, cumulativeLeadProbability + "");
			leadTable.setValue(i, 3, leadRange[i].toString());

		}
		// Showing the result in a JFrame

		/*JFrame leadFrame = new JFrame("Service Probability");
		leadFrame.setSize(500, 500);
		leadFrame.add(leadTable.table);
		leadFrame.setVisible(true);*/

		//////////////////////////////////////////////////////

		//////////////////////////////////////////////////////
		///Random values in the table
		//////////////////////////////////////////////////////

		int numberOfCycles = 10;
		int carsInStorage = 6;
		int orderSize = 5;
		int n = 2;
		int daysToNextOrder=2;
		Table demandRandomTable = new Table(n*numberOfCycles, 3);
		Random random = new Random();
		
		for (int i = 0; i < numberOfCycles*n; i++) {
		float x =(float) (0.001 + random.nextFloat() * (1 - 0.001));
		int randomValue = Range.getRangeProbability(demandRange, x);
		demandRandomTable.setValue(i, 0, i + 1 + "");
		demandRandomTable.setValue(i, 1, x + "");
		demandRandomTable.setValue(i, 2, randomValue + "");
		}

		/*JFrame randomDemandFrame = new JFrame("Demand Table");
		 randomDemandFrame.setSize(500, 500);
		 randomDemandFrame.add(demandRandomTable.table);
		 randomDemandFrame.setVisible(true);*/
		
		Table leadRandomTable = new Table(numberOfCycles, 3);

		for (int i = 0; i < numberOfCycles; i++) {
			float x = (float)(0.001 + random.nextFloat() * (1 - 0.001));
			int randomValue = Range.getRangeProbability(leadRange, x) + 1;
			leadRandomTable.setValue(i, 0, i + 1 + "");
			leadRandomTable.setValue(i, 1, x + "");
			leadRandomTable.setValue(i, 2, randomValue + "");
		}
		/*JFrame randomLeadFrame = new JFrame("Lead Table");
		randomLeadFrame.setSize(500, 500);
		randomLeadFrame.add(leadRandomTable.table);
		randomLeadFrame.setVisible(true);*/
		
		// Assume that the minimum is 3 cars because there is already an order for 5
		// when the inventory amount is 2, meaning that the minimum is either 2 or 3
		// 3 Sounds the most safe choice
		//Assume the number of cycles is 10 because it is unclear in the requirements
		
		ArrayList<SimulationTableRecord> record1=new ArrayList<SimulationTableRecord>();
		int firstDemand=Integer.parseInt(demandRandomTable.getCell(0, 2));
		SimulationTableRecord record=new SimulationTableRecord(1,1,carsInStorage,
				firstDemand,carsInStorage-firstDemand,0,orderSize, daysToNextOrder-1);
		record1.add(record);
		int k=0;
		for(int i =0; i<numberOfCycles;i++)
		{
			for(int j=0;j<n;j++)
			{
				
			}
		}
		
		Table storageSim = SimulationTableRecord.getTableRepresentation(n*numberOfCycles, record1);
		String headers3[]= {"Cycle","Day","Beginning Inventory","Demand","Ending Inventory"
				,"Shortage Quatity","Order Quantity","Days To Arrival"};
		storageSim.setTitles(headers3);
		JFrame resultsFrame = new JFrame("Results Table");
		resultsFrame.setSize(900, 500);
		resultsFrame.add(new JScrollPane(storageSim.table));
		resultsFrame.setVisible(true);
	}

}
