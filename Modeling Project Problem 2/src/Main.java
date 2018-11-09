import javax.swing.JFrame;

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
		Range[] DemandRange = new Range[5];
		DemandRange[0] = new Range();

		// Initializing the first range is done manually
		DemandRange[0].first = cumulativeDemandlProbability;
		DemandRange[0].second = DemandProbabilityList[0];

		// Initializing the demand probability table
		// First record is done manually
		Table DemandTable = new Table(length1, 4);
		// Adding header to table
		String headers[] = { "Demand", "Probability", "Cumulative Probability", "Range" };
		DemandTable.setTitles(headers);
		DemandTable.setValue(0, 0, 0 + "");
		DemandTable.setValue(0, 1, DemandProbabilityList[0] + "");
		DemandTable.setValue(0, 2, DemandProbabilityList[0] + "");
		DemandTable.setValue(0, 3, DemandRange[0].toString());
		cumulativeDemandlProbability = DemandProbabilityList[0];

		// Populating the rest of the table in a loop
		for (int i = 1; i < length1; i++) {
			DemandRange[i] = new Range();
			DemandRange[i].first = (cumulativeDemandlProbability * 100) + 1;
			DemandRange[i].first /= 100;
			cumulativeDemandlProbability += DemandProbabilityList[i];
			DemandRange[i].second = cumulativeDemandlProbability;
			DemandTable.setValue(i, 0, i + "");
			DemandTable.setValue(i, 1, DemandProbabilityList[i] + "");
			DemandTable.setValue(i, 2, cumulativeDemandlProbability + "");
			DemandTable.setValue(i, 3, DemandRange[i].toString());

		}

		// Showing the result in a JFrame

		JFrame DemandFrame = new JFrame("DemandProbability");
		DemandFrame.setSize(500, 500);
		DemandFrame.add(DemandTable.table);
		DemandFrame.setVisible(true);

		//////////////////////////////////////////////////////

		//////////////////////////////////////////////////////
		//////////// Service time table building/////////
		//////////////////////////////////////////////////////

		// Service time Probability Range
		Range[] LeadRange = new Range[3];
		LeadRange[0] = new Range();

		// Initializing the first range is done manually
		LeadRange[0].first = cumulativeLeadProbability;
		LeadRange[0].second = LeadTimeList[0];

		// Initializing the inter-arrival probability table
		// First record is done manually
		Table leadTable = new Table(length2, 4);

		// Adding header to table
		String headers2[] = { "Lead", "Probability", "Cumulative Probability", "Range" };

		leadTable.setTitles(headers2);
		leadTable.setValue(0, 0, 1 + "");
		leadTable.setValue(0, 1, LeadTimeList[0] + "");
		leadTable.setValue(0, 2, LeadTimeList[0] + "");
		leadTable.setValue(0, 3, LeadRange[0].toString());
		cumulativeLeadProbability = LeadTimeList[0];

		// Populating the rest of the table in a loop
		for (int i = 1; i < length2; i++) {
			LeadRange[i] = new Range();
			LeadRange[i].first = (cumulativeLeadProbability * 100) + 1;
			LeadRange[i].first /= 100;
			cumulativeLeadProbability += LeadTimeList[i];
			LeadRange[i].second = cumulativeLeadProbability;
			leadTable.setValue(i, 0, i + 1 + "");
			leadTable.setValue(i, 1, LeadTimeList[i] + "");
			leadTable.setValue(i, 2, cumulativeLeadProbability + "");
			leadTable.setValue(i, 3, LeadRange[i].toString());

		}
		// Showing the result in a JFrame
		
		 JFrame leadFrame = new JFrame(); leadFrame.setSize(500, 500);
		 leadFrame.add(leadTable.table);
		 leadFrame.setVisible(true);
		 
		//////////////////////////////////////////////////////

	}

}
