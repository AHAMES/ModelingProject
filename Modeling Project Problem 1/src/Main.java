import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
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

	public static void main(String args[]) {

		ArrayList<Answer> answers = new ArrayList<Answer>();
		ArrayList<JTabbedPane> details = new ArrayList<>();
		ArrayList<TheoreticalAnswer> theoreticalAnswers = new ArrayList<>();

		int numberOfRuns = Integer.parseInt(JOptionPane.showInputDialog("Input Number of Runs"));
		int numberOfCustomers = Integer.parseInt(JOptionPane.showInputDialog("Number of Customers"));

		int queueLength = Integer.parseInt(JOptionPane.showInputDialog("Length of the Drive in Queue"));

		// Values used in charts
		int averagPeopleGoingInBank = 0;
		int averagePeopleGoingInDriveIn = 0;
		int waitingTimeBank = 0;
		int waitingTimeDrive = 0;
		int idleBankTime = 0;
		int idleDriveTime = 0;
		int BankTimeSpent = 0;
		int DriveTimeSpent = 0;
		int highestRangeSize = 0;
		int numberOfPeopleWaitingInBank = 0;
		int numberOfPeopleWaitingDriveIn = 0;
		int interArrivalDistributionTotal[]=new int[6];
		int serviceTimeDistributionTotal[]=new int[4];
		ArrayList<JFreeChart> barChartsPerRun = new ArrayList<>();
		ArrayList<CustomerGraph> customerGraphs = new ArrayList<>();

		for (int l = 0; l < numberOfRuns; l++) {// If float problem happens

			// Initializing given probability tables
			double[] interArrivalTimeProbabilityList = new double[6];
			double[] serviceTimeProbabilityList = new double[4];

			// Initializing the inter arrival time table
			interArrivalTimeProbabilityList[0] = 0.09;
			interArrivalTimeProbabilityList[1] = 0.17;
			interArrivalTimeProbabilityList[2] = 0.27;
			interArrivalTimeProbabilityList[3] = 0.20;
			interArrivalTimeProbabilityList[4] = 0.15;
			interArrivalTimeProbabilityList[5] = 0.12;

			// Initializing the service time table
			serviceTimeProbabilityList[0] = 0.20;
			serviceTimeProbabilityList[1] = 0.40;
			serviceTimeProbabilityList[2] = 0.28;
			serviceTimeProbabilityList[3] = 0.12;

			// Cumulative probability used in the tables
			double cumulativeInterArrivalProbability = 0.001;
			double cumulativeServiceTimeProbability = 0.001;

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
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				interArrivalRange[i].first = Double.parseDouble(df.format((cumulativeInterArrivalProbability))) * 100
						+ 1;
				interArrivalRange[i].first /= 100;
				cumulativeInterArrivalProbability = Double.parseDouble(df.format(cumulativeInterArrivalProbability));
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
				DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				serviceTimeRange[i] = new Range();
				serviceTimeRange[i].first = ((Double.parseDouble(df.format(cumulativeServiceTimeProbability))) * 100)
						+ 1;
				serviceTimeRange[i].first /= 100;
				cumulativeServiceTimeProbability += Double.parseDouble(df.format(serviceTimeProbabilityList[i]));
				cumulativeServiceTimeProbability = Double.parseDouble(df.format(cumulativeServiceTimeProbability));
				serviceTimeRange[i].second = cumulativeServiceTimeProbability;
				serviceTimeTable.setValue(i, 0, i + 1 + "");
				serviceTimeTable.setValue(i, 1, serviceTimeProbabilityList[i] + "");
				serviceTimeTable.setValue(i, 2, cumulativeServiceTimeProbability + "");
				serviceTimeTable.setValue(i, 3, serviceTimeRange[i].toString());

			}
			// Showing the result in a JFrame

			/*
			 * JFrame serviceTimeFrame = new JFrame(); serviceTimeFrame.setSize(500, 500);
			 * serviceTimeFrame.add(serviceTimeTable.table);
			 * serviceTimeFrame.setVisible(true);
			 */

			//////////////////////////////////////////////////////

			//////////////////////////////////////////////////////
			/// Random values in the table
			//////////////////////////////////////////////////////

			Table interArrivalRandomTable = new Table(numberOfCustomers, 3);
			Random random = new Random();
			interArrivalRandomTable.setValue(0, 0, 1 + "");
			interArrivalRandomTable.setValue(0, 1, "-");
			interArrivalRandomTable.setValue(0, 2, "-");

			for (int i = 1; i < numberOfCustomers; i++) {
				double n = (0.001 + random.nextDouble() * (1 - 0.001));
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

			Table serviceTimeRandomTable = new Table(numberOfCustomers, 3);

			for (int i = 0; i < numberOfCustomers; i++) {
				double n = (0.001 + random.nextDouble() * (1 - 0.001));
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
			record1.add(new SimulationTableRecord("Drive", 1, 0, 0, sr, 0, 0, sr, 0, 0));
			record2.add(new SimulationTableRecord("Dummy", 0, 0, 0, 0, 0, 0, 0, 0, 0));
			recordTotal.add(record1.get(0));

			LinkedList<Integer> carQueue = new LinkedList<Integer>();
			carQueue.add(sr);

			int arrivalTime = 0;
			for (int i = 1, j = 1, k = 1; k < numberOfCustomers; k++) {

				SimulationTableRecord record = new SimulationTableRecord();
				int interArrivalTime = Integer.parseInt(interArrivalRandomTable.getCell(k, 2));
				int CurrentArrivalTime = arrivalTime + interArrivalTime;

				while (carQueue.isEmpty() == false && carQueue.getFirst() <= CurrentArrivalTime) {
					carQueue.removeFirst();
				}
				record.setCustomerNumber(k + 1);
				record.setInterArrivalTime(interArrivalTime);
				record.setServiceTime(Integer.parseInt(serviceTimeRandomTable.getCell(k, 2)));
				record.setArrivalTime(CurrentArrivalTime);
				arrivalTime = record.getArrivalTime();
				if (carQueue.size() < queueLength) {

					// time Service begins condition, if not busy start at once
					if (record1.get(i - 1).getTimeServiceEnds() <= record.getArrivalTime()) {
						record.setTimeServiceBegins(record.getArrivalTime());
						record.setServerIdleTime(record.getArrivalTime() - record1.get(i - 1).getTimeServiceEnds());
					} else {
						record.setTimeServiceBegins(record1.get(i - 1).getTimeServiceEnds());
						record.setServerIdleTime(0);
					}
					record.setWaitingTimeInQueue(record.getTimeServiceBegins() - record.getArrivalTime());
					if (record.getWaitingTimeInQueue() > 0) {
						numberOfPeopleWaitingDriveIn++;

					}
					record.setTimeServiceEnds(record.getTimeServiceBegins() + record.getServiceTime());
					record.setTimeSpentInSystem(record.getTimeServiceEnds() - record.getArrivalTime());

					record.setWhichQueue("Drive");
					record1.add(record);
					carQueue.add(record.getTimeServiceEnds());
					waitingTimeDrive += record1.get(i).getWaitingTimeInQueue();
					idleDriveTime += record1.get(i).getServerIdleTime();
					DriveTimeSpent += record1.get(i).getTimeSpentInSystem();
					i++;
				} else {
					// Same code as the second else, i made it like that for the lack of proper goto
					// operator in java
					// time Service begins condition, if not busy start at once
					if (record2.get(j - 1).getTimeServiceEnds() <= record.getArrivalTime()) {
						record.setTimeServiceBegins(record.getArrivalTime());
						record.setServerIdleTime(record.getArrivalTime() - record2.get(j - 1).getTimeServiceEnds());
					} else {
						record.setTimeServiceBegins(record2.get(j - 1).getTimeServiceEnds());
						record.setServerIdleTime(0);
					}
					record.setWaitingTimeInQueue(record.getTimeServiceBegins() - record.getArrivalTime());
					if (record.getWaitingTimeInQueue() > 0) {
						numberOfPeopleWaitingInBank++;
					}
					record.setTimeServiceEnds(record.getTimeServiceBegins() + record.getServiceTime());
					record.setTimeSpentInSystem(record.getTimeServiceEnds() - record.getArrivalTime());

					record.setWhichQueue("Bank");
					record2.add(record);
					waitingTimeBank += record2.get(j).getWaitingTimeInQueue();
					idleBankTime += record2.get(j).getServerIdleTime();
					BankTimeSpent += record2.get(j).getTimeSpentInSystem();
					j++;
				}

				recordTotal.add(record);
			}

			CustomerGraph graphUnit = new CustomerGraph(20, recordTotal.get(recordTotal.size() - 1).getArrivalTime());
			customerGraphs.add(graphUnit);

			if (highestRangeSize < graphUnit.ranges.get(graphUnit.ranges.size() - 1).second) {
				highestRangeSize = (int) graphUnit.ranges.get(graphUnit.ranges.size() - 1).second;
			}

			barChartsPerRun.add(graphUnit.getGraph(recordTotal));
			// Table for the drive in Teller
			Table driveInSim = SimulationTableRecord.getTableRepresentation(record1.toArray().length, record1);
			String headers3[] = { "Queue", "Customer", "Inter-Arrival Time", "Arrival Time", "Service Time",
					"Service Begins", "Waiting", "Service ends", "Time Spent", "Idle" };
			driveInSim.setTitles(headers3);

			// Table for the in bank teller
			Table inBankSim = SimulationTableRecord.getTableRepresentation(record2.toArray().length, record2);
			inBankSim.setTitles(headers3);

			// Combined Table of tellers
			Table totalSim = SimulationTableRecord.getTableRepresentation(recordTotal.toArray().length, recordTotal);
			totalSim.setTitles(headers3);

			// Storing the answers of this run in a Answer object
			Answer answer = SimulationTableRecord.getAnswers(record1, record2, recordTotal);
			/*
			 * String answersHeaders[] = { "Drive In Serv Avg", "Drive In Wait Avg",
			 * "In Bank Serv Avg", "In Bank Wait Avg",
			 * "Probability Bank Waiting","Probability Bank Idle", "Maximum Queue Length" };
			 */

			// Storing the gained value for this run in details panel
			JTabbedPane panel = new JTabbedPane();

			panel.addTab("Drive In", new JScrollPane(driveInSim.table));
			panel.addTab("In Bank", new JScrollPane(inBankSim.table));
			panel.addTab("Combined", new JScrollPane(totalSim.table));
			details.add(panel);
			theoreticalAnswers.add(TheoreticalAnswer.getTheoreticalAnswer(numberOfCustomers, interArrivalRandomTable,
					serviceTimeRandomTable));
			TheoreticalAnswer xTheoreticalAnswer=TheoreticalAnswer.getTheoreticalTotal(numberOfCustomers, interArrivalRandomTable, serviceTimeRandomTable);
			for(int i =0; i<6;i++)
			{
				interArrivalDistributionTotal[i]=(int) xTheoreticalAnswer.interArrivalDistribution[i];
			}
			for(int i =0; i<4;i++)
			{
				serviceTimeDistributionTotal[i]=(int) xTheoreticalAnswer.serviceTimeDistribution[i];
			}
			// Storing the Answer object of this run in an ArrayList
			averagePeopleGoingInDriveIn += record1.size();
			averagPeopleGoingInBank += record2.size();
			answers.add(answer);
		}

		Table answerTable = Answer.getTableRepresentation(numberOfRuns, answers);
		JTabbedPane finalPanel = new JTabbedPane();

		// Details tabbed panel
		// Fill with the tabbed Panel

		DefaultCategoryDataset distributionDataset=new DefaultCategoryDataset();
		for(int i=0;i<6;i++)
		{
			distributionDataset.addValue(interArrivalDistributionTotal[i], "Inter-Arrival", i+"");
		}
		for(int i=0;i<4;i++)
		{
			distributionDataset.addValue(serviceTimeDistributionTotal[i], "Service Time", i+1+"");
		}
		DefaultCategoryDataset statsDataset = new DefaultCategoryDataset();
		statsDataset.addValue(waitingTimeBank / numberOfRuns, "Bank", "Waiting Time");
		statsDataset.addValue(waitingTimeDrive / numberOfRuns, "Drive", "Waiting Time");
		statsDataset.addValue(idleBankTime / numberOfRuns, "Bank", "Idle Time");
		statsDataset.addValue(idleDriveTime / numberOfRuns, "Drive", "Idle Time");
		statsDataset.addValue(BankTimeSpent / numberOfRuns, "Bank", "Time Spent");
		statsDataset.addValue(DriveTimeSpent / numberOfRuns, "Drive", "Time Spent");

		averagPeopleGoingInBank = averagPeopleGoingInBank / (numberOfRuns);
		averagePeopleGoingInDriveIn = averagePeopleGoingInDriveIn / (numberOfRuns);
		DefaultPieDataset averagePeopleServedDataset = new DefaultPieDataset();
		DefaultCategoryDataset peopleWaitingDataset = new DefaultCategoryDataset();

		averagePeopleServedDataset.setValue("Number of People Going to Bank", averagPeopleGoingInBank);
		averagePeopleServedDataset.setValue("Number of People Going to Drive In", averagePeopleGoingInDriveIn);

		peopleWaitingDataset.setValue((numberOfPeopleWaitingInBank * 100) / (averagPeopleGoingInBank * numberOfRuns),
				"% of people waiting In Bank", "Bank");
		peopleWaitingDataset.setValue(
				(numberOfPeopleWaitingDriveIn * 100) / (averagePeopleGoingInDriveIn * numberOfRuns),
				"% of people waiting Drive In", "Drive");

		JFreeChart averagePeopleServedChart = ChartFactory.createPieChart("Average People In service",
				averagePeopleServedDataset, true, true, false);

		JFreeChart distributionChart = ChartFactory.createBarChart("Theoretical Distributions",
				"Distribution", "Occurances", distributionDataset, PlotOrientation.VERTICAL, true, true, false);
		JFreeChart peopleWaitingChart = ChartFactory.createBarChart("% People Waiting In Bank",
				"Number Of People Waiting", "Percentage", peopleWaitingDataset, PlotOrientation.VERTICAL, true, true,
				false);
		JFreeChart statsChart = ChartFactory.createBarChart("Teller Stats", "Category", "Time", statsDataset,
				PlotOrientation.VERTICAL, true, true, false);

		JTabbedPane charts = new JTabbedPane();
		JTabbedPane detailsPanel = new JTabbedPane();
		JTabbedPane detailsGraphPanel = new JTabbedPane();

		for (int i = 0; i < numberOfRuns; i++) {
			detailsPanel.add("Run " + (i + 1), details.get(i));
		}
		for (int i = 0; i < numberOfRuns; i++) {
			detailsGraphPanel.add("Run " + (i + 1), new ChartPanel(barChartsPerRun.get(i)));
		}

		String answersHeaders[] = { "Run ID", "Drive In Serv Avg", "Drive In Wait Avg", "In Bank Serv Avg",
				"In Bank Wait Avg", "Probability Bank Waiting", "Probability Bank Idle", "Maximum Queue Length" };
		CustomerGraph averageCustomerGraph = CustomerGraph.getAverage(customerGraphs, highestRangeSize);
		JFreeChart averageCustomerChart = CustomerGraph.getGraph(averageCustomerGraph);
		answerTable.setTitles(answersHeaders);
		JFrame finalFrame = new JFrame("Results");

		String finalAnswersHeaders[] = { "Drive In Serv Avg", "Drive In Wait Avg", "In Bank Serv Avg",
				"In Bank Wait Avg", "Probability Bank Waiting", "Probability Bank Idle", "Maximum Queue Length" };

		charts.add("Average of Users of System", new ChartPanel(averagePeopleServedChart));
		charts.add("Teller Stats", new ChartPanel(statsChart));
		charts.add("Arrival Time Per Run", detailsGraphPanel);
		charts.add("Total Arrival Time", new ChartPanel(averageCustomerChart));
		charts.add("Percentage of People Waiting In Bank", new ChartPanel(peopleWaitingChart));
		charts.add("Distribution Charts", new ChartPanel(distributionChart));
		Table finalTable = Answer.getAverageOfAllRuns(numberOfRuns, answers);
		finalTable.setTitles(finalAnswersHeaders);

		finalPanel.addTab("Run Results", new JScrollPane(answerTable.table));
		finalPanel.addTab("Details", new JScrollPane(detailsPanel));
		finalPanel.addTab("Final Answer", new JScrollPane(finalTable.table));
		finalPanel.add("Real Probability Distribution", TheoreticalAnswer.getDistributions(theoreticalAnswers));
		finalPanel.add("Charts", charts);
		finalFrame.add(finalPanel);
		finalFrame.setSize(1000, 500);
		finalFrame.setVisible(true);
	}
}