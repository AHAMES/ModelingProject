import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class Main {

	public static void main(String[] args) {

		// Minimum number in storage after which we must order a new shipment

		// ArrayList that stores the answers to the questions
		ArrayList<Answer> answers = new ArrayList<Answer>();
		// ArrayList that stores a table of a single run
		ArrayList<JScrollPane> details = new ArrayList<>();
		// ArrayList that stores the practical probability distribution
		// (Named theoretical for initial confusing reasons, the name stuck)
		ArrayList<TheoreticalAnswer> theoreticalAnswers = new ArrayList<>();
		int numberOfRuns = 30;
		int minimumVehicle = 6;
		int n = 2;
		int numberOfCycles = 90;
		int seed1 = 0;
		int seed2 = 1;
		try {
			numberOfRuns = Integer.parseInt(JOptionPane.showInputDialog("Input Number of Runs"));
			minimumVehicle = Integer.parseInt(JOptionPane.showInputDialog("Input Minimum Vehicles"));
			n = Integer.parseInt(JOptionPane.showInputDialog("Input N"));
			numberOfCycles = Integer.parseInt(JOptionPane.showInputDialog("Input number of Cycles"));
			seed1 = Integer.parseInt(JOptionPane.showInputDialog("Seed1"));
			seed2 = Integer.parseInt(JOptionPane.showInputDialog("Seed2"));
		} catch (Exception e) {

		}
		// Values used in charts
		int numberOfTimesUnderMinimum = 0;
		int overallNumberOfTimesOrdered = 1;
		int demand = 0;
		int shortage = 0;
		double theoreticalDemandTime = 0;
		double theoreticalLeadTime = 0;
		double theoreticalRealLeadTime = 0;
		ArrayList<StorageGraph[]> xStorageGraphs = new ArrayList<>();
		ArrayList<StorageGraph[]> xDemandGraphs = new ArrayList<>();
		ArrayList<StorageGraph[]> xOrderGraphs = new ArrayList<>();
		ArrayList<JFreeChart> lineChartsPerRun = new ArrayList<>();
		/*
		 * int minimumVehicle = 4; int n = 3; int numberOfCycles = 50; int numberOfRuns
		 * = 100;
		 */

		// Initializing given probability tables
		double[] DemandProbabilityList = new double[5];
		double[] leadTimeList = new double[3];

		// Initializing the Demand table
		DemandProbabilityList[0] = (double) 0.05;
		DemandProbabilityList[1] = (double) 0.28;
		DemandProbabilityList[2] = (double) 0.37;
		DemandProbabilityList[3] = (double) 0.20;
		DemandProbabilityList[4] = (double) 0.10;

		double realDemandDistributionAverage = 0;
		for (int i = 0; i < DemandProbabilityList.length; i++) {
			realDemandDistributionAverage += DemandProbabilityList[i] * i;
		}
		// Initializing the Lead time table
		leadTimeList[0] = (double) 0.55;
		leadTimeList[1] = (double) 0.35;
		leadTimeList[2] = (double) 0.1;

		double realLeadTimeDistributionAverage = 0;
		for (int i = 0; i < leadTimeList.length; i++) {
			realLeadTimeDistributionAverage += leadTimeList[i] * (i + 1);
		}

		// Cumulative probability used in the tables
		double cumulativeDemandlProbability = (double) 0.001;
		double cumulativeLeadProbability = (double) 0.001;

		// probability table length
		int length1 = DemandProbabilityList.length;
		int length2 = leadTimeList.length;

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
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			demandRange[i].first = (Double.parseDouble((df.format(cumulativeDemandlProbability))) * 100) + 1;
			demandRange[i].first /= 100;
			cumulativeDemandlProbability += Double.parseDouble(df.format((DemandProbabilityList[i])));
			cumulativeDemandlProbability = Double.parseDouble(df.format((cumulativeDemandlProbability)));
			demandRange[i].second = Double.parseDouble(df.format((cumulativeDemandlProbability)));
			DemandTable.setValue(i, 0, i + "");
			DemandTable.setValue(i, 1, DemandProbabilityList[i] + "");
			DemandTable.setValue(i, 2, cumulativeDemandlProbability + "");
			DemandTable.setValue(i, 3, demandRange[i].toString());

		}

		// Showing the result in a JFrame

		/*
		 * JFrame DemandFrame = new JFrame("Demand Probability");
		 * DemandFrame.setSize(500, 500); DemandFrame.add(DemandTable.table);
		 * DemandFrame.setVisible(true);
		 */

		//////////////////////////////////////////////////////

		//////////////////////////////////////////////////////
		//////////// lead time table building/////////
		//////////////////////////////////////////////////////

		// lead time Probability Range
		Range[] leadRange = new Range[3];
		leadRange[0] = new Range();

		// Initializing the first range is done manually
		leadRange[0].first = cumulativeLeadProbability;
		leadRange[0].second = leadTimeList[0];

		// Initializing the lead probability table
		// First record is done manually
		Table leadTable = new Table(length2, 4);

		// Adding header to table
		String headers2[] = { "Lead", "Probability", "Cumulative Probability", "Range" };

		//
		leadTable.setTitles(headers2);
		leadTable.setValue(0, 0, 1 + "");
		leadTable.setValue(0, 1, leadTimeList[0] + "");
		leadTable.setValue(0, 2, leadTimeList[0] + "");
		leadTable.setValue(0, 3, leadRange[0].toString());
		cumulativeLeadProbability = leadTimeList[0];

		// Populating the rest of the table in a loop
		for (int i = 1; i < length2; i++) {
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			leadRange[i] = new Range();
			leadRange[i].first = Double.parseDouble((df.format((cumulativeLeadProbability) * 100))) + 1;
			leadRange[i].first /= 100;
			cumulativeLeadProbability += Double.parseDouble(df.format(leadTimeList[i]));
			cumulativeLeadProbability = Double.parseDouble((df.format(cumulativeLeadProbability)));
			leadRange[i].second = cumulativeLeadProbability;
			leadTable.setValue(i, 0, i + 1 + "");
			leadTable.setValue(i, 1, leadTimeList[i] + "");
			leadTable.setValue(i, 2, cumulativeLeadProbability + "");
			leadTable.setValue(i, 3, leadRange[i].toString());

		}
		// Showing the result in a JFrame

		/*
		 * JFrame leadFrame = new JFrame("Lead Probability"); leadFrame.setSize(500,
		 * 500); leadFrame.add(leadTable.table); leadFrame.setVisible(true);
		 */

		for (int l = 0; l < numberOfRuns; l++) {

			//////////////////////////////////////////////////////
			/// Random values in the demand table
			//////////////////////////////////////////////////////

			int carsInStorage = 6;
			int orderSize = 5;

			int daysToNextOrder = 2;
			Table demandRandomTable = new Table(n * numberOfCycles, 3);
			Random random = new Random();
			random.setSeed(l + seed1);
			// assign the random demand numbers for each
			for (int i = 0; i < numberOfCycles * n; i++) {
				double x = (double) (0.001 + random.nextDouble() * (1 - 0.001));
				int randomValue = Range.getRangeProbability(demandRange, x);
				demandRandomTable.setValue(i, 0, i + 1 + "");
				demandRandomTable.setValue(i, 1, x + "");
				demandRandomTable.setValue(i, 2, randomValue + "");
				theoreticalDemandTime += randomValue;
			}

			/*
			 * JFrame randomDemandFrame = new JFrame("Demand Table");
			 * randomDemandFrame.setSize(500, 500);
			 * randomDemandFrame.add(demandRandomTable.table);
			 * randomDemandFrame.setVisible(true);
			 */
			//////////////////////////////////////////////////////

			//////////////////////////////////////////////////////
			/// Random values in the lead Table
			//////////////////////////////////////////////////////
			Table leadRandomTable = new Table(numberOfCycles, 3);

			// First record is done manually because of the requirement to start with lead
			// time 2
			Random random2 = new Random();
			random2.setSeed(l + seed2);
			leadRandomTable.setValue(0, 0, 1 + "");
			leadRandomTable.setValue(0, 1, "");
			leadRandomTable.setValue(0, 2, 2 + "");
			for (int i = 1; i < numberOfCycles; i++) {
				double x = (double) (0.001 + random2.nextDouble() * (1 - 0.001));
				int randomValue = Range.getRangeProbability(leadRange, x) + 1;
				leadRandomTable.setValue(i, 0, i + 1 + "");
				leadRandomTable.setValue(i, 1, x + "");
				leadRandomTable.setValue(i, 2, randomValue + "");
				theoreticalLeadTime += randomValue;
			}
			/*
			 * JFrame randomLeadFrame = new JFrame("Lead Table");
			 * randomLeadFrame.setSize(500, 500);
			 * randomLeadFrame.add(leadRandomTable.table); randomLeadFrame.setVisible(true);
			 */

			// ArrayList that contains the records in the run
			ArrayList<SimulationTableRecord> record1 = new ArrayList<SimulationTableRecord>();
			int firstDemand = Integer.parseInt(demandRandomTable.getCell(0, 2));
			SimulationTableRecord record = new SimulationTableRecord(1, 1, carsInStorage, firstDemand,
					carsInStorage - firstDemand, 0, orderSize, daysToNextOrder);
			record1.add(record);
			demand += firstDemand;
			// This queue of orders keeps track of the orders sent for cars
			// The assumption is that you cannot order cars before
			// the current order arrives

			int k = 1;
			// Values used for the practical distribution
			double[] leadCreated = { 0, 1, 0 };
			double[] leadFromTable = { 0, 0, 0 };
			double[] demandFromTable = { 0, 0, 0, 0, 0 };
			int numberOfTimesOrdered = 1;
			StorageGraph[] graphUnit = new StorageGraph[numberOfCycles];
			StorageGraph[] graphUnit2 = new StorageGraph[numberOfCycles];
			StorageGraph[] graphUnit3 = new StorageGraph[numberOfCycles];
			graphUnit3[0] = new StorageGraph();
			graphUnit3[0].storage = orderSize;
			graphUnit3[0].cycle += 1;
			graphUnit2[0] = new StorageGraph();
			graphUnit2[0].storage = demand;
			graphUnit2[0].cycle += 1;
			graphUnit[0] = new StorageGraph();
			graphUnit[0].storage += 6;
			graphUnit[0].cycle += 1;

			for (int i = 0; i < numberOfCycles; i++) {

				// increase the number of lead From table at each cycle
				// to calculate the practical distribution at each cycle
				// (assuming that we always order each cycle no matter what)
				int currentLead = Integer.parseInt(leadRandomTable.getCell(i, 2));
				leadFromTable[currentLead - 1]++;
				if (graphUnit[i] == null) {
					graphUnit[i] = new StorageGraph();
					graphUnit2[i] = new StorageGraph();
					graphUnit3[i] = new StorageGraph();
				}
				graphUnit[i].cycle = i + 1;
				graphUnit2[i].cycle = i + 1;
				graphUnit3[i].cycle = i + 1;
				for (int j = 0; j < n; j++) {

					record = new SimulationTableRecord();

					// First record is added manually
					// Ignore first run
					if (i == 0 && j == 0) {

						// Increase the demand number at the first day
						int currentDemand = Integer.parseInt(demandRandomTable.getCell(k, 2));
						demandFromTable[currentDemand]++;
						continue;

					}
					// add to the day and cycle
					// cycle changes depending on outer loop with i
					// day change in this loop with j
					record.setCycle(i + 1);
					record.setDay(j + 1);

					// Decrement the days to the next order until it is = 0
					if (daysToNextOrder != 0) {

						record.setDaysToArrival(--daysToNextOrder);
					}
					// The current record's beginning inventory= previous ending inventory (no
					// matter what)
					SimulationTableRecord previousRecord = record1.get(k - 1);
					record.setBeginningInventory(previousRecord.getEndingInventory());

					// When the dayToNextOrder=0, increase the beginningInventory with the order
					// size
					// set orderSize=0 to avoid repeating the order afterwards
					if (daysToNextOrder == 0) {
						record.setBeginningInventory(record.getBeginningInventory() + orderSize);
						orderSize = 0;
					}

					// Increase the demand number at the each day
					int currentDemand = Integer.parseInt(demandRandomTable.getCell(k, 2));
					demandFromTable[currentDemand]++;

					// add the demand each day
					record.setDemand(currentDemand);
					demand += currentDemand;
					// If the current inventory does have enough cars
					// set the inventory to 0 and record the shortage
					// This commented portions of the code in these conditions
					// are used as an alternative assumption if shortage is added to the demand
					// and taken later from inventory
					if ((record.getBeginningInventory() - (currentDemand + previousRecord.getShortageQuatity())) < 0) {

						/*
						 * record.setEndingInventory(0);
						 * record.setShortageQuatity(Math.abs(record.getBeginningInventory() -
						 * currentDemand));
						 */

						record.setShortageQuatity(Math.abs(record.getBeginningInventory()
								- (currentDemand + previousRecord.getShortageQuatity())));

						shortage += record.getShortageQuatity();
					}
					// Otherwise decrease the current inventory and add it to the ending inventory
					// and make shortage = 0
					else {
						//record.setEndingInventory(record.getBeginningInventory() - currentDemand);

						
						 record.setEndingInventory( record.getBeginningInventory() - currentDemand -
						 previousRecord.getShortageQuatity());
						 
						record.setShortageQuatity(0);
					}

					if (record.getEndingInventory() <= minimumVehicle) {
						numberOfTimesUnderMinimum++;
					}
					// if at the end of the cycle (j==n-1)
					// and no orders currently available (orderSize==0)
					// and the ending inventory at this run is less then the minimum

					if (j == n - 1 && orderSize == 0 && record.getEndingInventory() <= minimumVehicle) {
						// make a new order where orderSize=12-currentEndingInventory
						orderSize = 12 - record.getEndingInventory();
						// Set day to arrival = lead time from the table
						daysToNextOrder = Integer.parseInt(leadRandomTable.getCell(i, 2));
						// put the order size in record
						record.setOrderQuantity(orderSize);
						// put the day to arrival in record
						record.setDaysToArrival(daysToNextOrder);
						// Increase the lead number at time a new order is made
						leadCreated[daysToNextOrder - 1]++;
						numberOfTimesOrdered++;
						overallNumberOfTimesOrdered++;
						theoreticalRealLeadTime += daysToNextOrder;
					}
					graphUnit[i].storage = record.getEndingInventory();
					graphUnit2[i].storage = record.getDemand();
					graphUnit3[i].storage = record.getOrderQuantity();
					////////////////////////////////
					////////////////////////////////
					// Add the record to the arrayList in the run
					k++;
					record1.add(record);
				}
			}
			// Add the answers from the record to the arrayList in the run
			answers.add(SimulationTableRecord.getAnswers(record1));

			Table storageSim = SimulationTableRecord.getTableRepresentation(n * numberOfCycles, record1);
			String headers3[] = { "Cycle", "Day", "Beginning Inventory", "Demand", "Ending Inventory",
					"Shortage Quatity", "Order Quantity", "Days To Arrival" };

			storageSim.setTitles(headers3);
			// Store the record as a table in JScrollPane
			details.add(new JScrollPane(storageSim.table));
			StorageGraph[][] xGraphs = new StorageGraph[graphUnit.length][3];
			xGraphs[0] = graphUnit;
			xGraphs[1] = graphUnit2;
			xGraphs[2] = graphUnit3;
			lineChartsPerRun.add(StorageGraph.createGraph(xGraphs));
			// Save practical distribution for this run
			theoreticalAnswers.add(TheoreticalAnswer.getTheoreticalAnswer(leadFromTable, demandFromTable, leadCreated,
					numberOfTimesOrdered, n, numberOfCycles));
			xStorageGraphs.add(graphUnit);
			xDemandGraphs.add(graphUnit2);
			xOrderGraphs.add(graphUnit3);
			/*
			 * JFrame resultsFrame = new JFrame("Results Table"); resultsFrame.setSize(900,
			 * 500); resultsFrame.add(new JScrollPane(storageSim.table));
			 * resultsFrame.setVisible(true);
			 */
		}

		// The following code is dedicated to the GUI

		// Table containing all the answers
		Table answerTable = Answer.getTableRepresentation(numberOfRuns, answers);
		// Tabbed Pane to contain the runs, answers, practical answers and final answers
		JTabbedPane finalPanel = new JTabbedPane();
		// Tabbed pane that contains the runs

		JTabbedPane detailsPanel = new JTabbedPane();
		JTabbedPane detailsGraphPanel = new JTabbedPane();

		// The following code is made for graph
		DefaultCategoryDataset demandDistributionDataset = new DefaultCategoryDataset();
		TheoreticalAnswer xtTheoreticalAnswer = TheoreticalAnswer.getDistributions(theoreticalAnswers, 0);
		for (int i = 0; i < DemandProbabilityList.length; i++) {
			demandDistributionDataset.addValue(DemandProbabilityList[i], "Original", i + 1 + "");
		}
		for (int i = 0; i < DemandProbabilityList.length; i++) {
			demandDistributionDataset.addValue(xtTheoreticalAnswer.demandDistribution[i], "Calculated", i + 1 + "");
		}

		DefaultCategoryDataset leadDistributionDataset = new DefaultCategoryDataset();
		for (int i = 0; i < leadTimeList.length; i++) {
			leadDistributionDataset.addValue(leadTimeList[i], "Original", i + "");
		}
		for (int i = 0; i < leadTimeList.length; i++) {
			leadDistributionDataset.addValue(xtTheoreticalAnswer.leadDistribution[i], "Calculated", i + "");
			leadDistributionDataset.addValue(xtTheoreticalAnswer.realLeadDistribution[i], "Realistic", i + "");
		}

		DefaultPieDataset dataset2 = new DefaultPieDataset();
		int percentageOfTimesNotOrdering = (100 * overallNumberOfTimesOrdered) / numberOfTimesUnderMinimum;
		dataset2.setValue("Percentage of Times Ordering", percentageOfTimesNotOrdering);
		dataset2.setValue("Percentage of Times Not Ordering", 100 - percentageOfTimesNotOrdering);

		DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
		dataset1.addValue(numberOfTimesUnderMinimum / numberOfRuns, "Storage Minimum", "Ordering");
		dataset1.addValue(overallNumberOfTimesOrdered / numberOfRuns, "Times Ordered", "Ordering");
		dataset1.addValue((numberOfTimesUnderMinimum - overallNumberOfTimesOrdered) / numberOfRuns, "Times Not Ordered",
				"Ordering");

		DefaultCategoryDataset dataset3 = new DefaultCategoryDataset();
		dataset3.addValue(demand / numberOfRuns, "Demand", "Demand");
		dataset3.addValue(shortage / numberOfRuns, "Shortage", "Demand");

		JFreeChart pieChart1 = ChartFactory.createPieChart("Percentage of Ordering", dataset2, true, true, false);
		JFreeChart barChart1 = ChartFactory.createBarChart("Ordering Stats", "", "Occurences", dataset1,
				PlotOrientation.VERTICAL, true, true, false);
		JFreeChart barChart2 = ChartFactory.createBarChart("Demand Stats", "", "Number Of Cars", dataset3,
				PlotOrientation.VERTICAL, true, true, false);

		JFreeChart demandDistributionChart = ChartFactory.createBarChart("Demand Distributions", "Distribution",
				"Occurances", demandDistributionDataset, PlotOrientation.VERTICAL, true, true, false);
		JFreeChart leadTimeDistributionChart = ChartFactory.createBarChart("Lead Time Distributions", "Distribution",
				"Occurances", leadDistributionDataset, PlotOrientation.VERTICAL, true, true, false);
		DefaultCategoryDataset dataset4 = new DefaultCategoryDataset();
		StorageGraph[] finalStorageGraph = StorageGraph.getAverage(xStorageGraphs);
		StorageGraph[] finalDemandGraph = StorageGraph.getAverage(xDemandGraphs);
		StorageGraph[] finalOrderGraph = StorageGraph.getAverage(xOrderGraphs);

		for (int i = 0; i < finalStorageGraph.length; i++) {
			dataset4.addValue(finalStorageGraph[i].storage, "Storage", finalStorageGraph[i].cycle + "");
			dataset4.addValue(finalDemandGraph[i].storage, "Demand", finalDemandGraph[i].cycle + "");
			dataset4.addValue(finalOrderGraph[i].storage, "Order", finalOrderGraph[i].cycle + "");
		}

		JFreeChart lineChart = ChartFactory.createLineChart("Storage Usage", "Cycles", "Cars", dataset4,
				PlotOrientation.VERTICAL, true, true, false);
		lineChart.getPlot().setBackgroundPaint(Color.DARK_GRAY);
		// adding runs to the detail Tabbed Pane

		for (int i = 0; i < numberOfRuns; i++) {
			detailsPanel.add("Run " + (i + 1), details.get(i));
		}
		for (int i = 0; i < numberOfRuns; i++) {
			detailsGraphPanel.add("Run " + (i + 1), new ChartPanel(lineChartsPerRun.get(i)));
		}
		String answersHeaders[] = { "Run ID", "Showroom Avg", "Storage Avg", "Shortage Number" };

		answerTable.setTitles(answersHeaders);
		// JFrame that contains all the results
		JTabbedPane charts = new JTabbedPane();
		charts.add("Ordering Percentages", new ChartPanel(pieChart1));
		charts.add("Ordering Stats", new ChartPanel(barChart1));
		charts.add("Demand Stats", new ChartPanel(barChart2));
		charts.add("Storage Status", new ChartPanel(lineChart));
		charts.add("Storage Status Per Run", detailsGraphPanel);
		charts.add("Demand Distribution Chart", new ChartPanel(demandDistributionChart));
		charts.add("Lead Time Distribution Chart", new ChartPanel(leadTimeDistributionChart));
		JFrame finalFrame = new JFrame("Results");

		Table theoreticalAverageOfDistribution = new Table(4, 3);
		theoreticalAverageOfDistribution.setValue(0, 1, "Calculated Value");
		theoreticalAverageOfDistribution.setValue(0, 2, "Real Value");
		theoreticalAverageOfDistribution.setValue(1, 0, "Demand");
		theoreticalAverageOfDistribution.setValue(2, 0, "Lead Time");
		theoreticalAverageOfDistribution.setValue(3, 0, "Real Lead Time");
		theoreticalAverageOfDistribution.setValue(1, 1,
				theoreticalDemandTime / (numberOfRuns * numberOfCycles * n) + "");
		theoreticalAverageOfDistribution.setValue(2, 1, theoreticalLeadTime / (numberOfRuns * numberOfCycles) + "");
		theoreticalAverageOfDistribution.setValue(3, 1, theoreticalRealLeadTime / (numberOfRuns * numberOfCycles) + "");
		theoreticalAverageOfDistribution.setValue(1, 2, realDemandDistributionAverage + "");
		theoreticalAverageOfDistribution.setValue(2, 2, realLeadTimeDistributionAverage + "");
		theoreticalAverageOfDistribution.setValue(3, 2, realLeadTimeDistributionAverage + "");
		String finalAnswersHeaders[] = { "Showroom Avg", "Storage Avg", "Shortage Number" };

		// Getting the final answer table
		Table finalTable = Answer.getAverageOfAllRuns(numberOfRuns, answers);
		finalTable.setTitles(finalAnswersHeaders);

		// Adding all the tabs to the tabbed Pane
		finalPanel.addTab("Run Results", new JScrollPane(answerTable.table));
		finalPanel.addTab("Details", new JScrollPane(detailsPanel));
		finalPanel.addTab("Final Answer", new JScrollPane(finalTable.table));
		finalPanel.addTab("Average Theoretical Distribution", theoreticalAverageOfDistribution.table);
		finalPanel.add("Real Probability Distribution", TheoreticalAnswer.getDistributions(theoreticalAnswers));
		finalPanel.add("Charts", charts);
		// Adding the tabbed pane to the Frame, showing all the results
		finalFrame.add(finalPanel);
		finalFrame.setSize(1000, 500);
		finalFrame.setVisible(true);
	}
}
