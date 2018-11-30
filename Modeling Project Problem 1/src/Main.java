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
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

public class Main {

	public static void main(String args[]) {

		ArrayList<Answer> answers = new ArrayList<Answer>();
		ArrayList<JTabbedPane> details = new ArrayList<>();
		ArrayList<TheoreticalAnswer> theoreticalAnswers = new ArrayList<>();

		int numberOfRuns = 30;
		int numberOfCustomers = 100;

		int queueLength = 2;
		int seed1 = 0;
		int seed2 = 1;
		try {
			numberOfRuns = Integer.parseInt(JOptionPane.showInputDialog("Input Number of Runs"));
			numberOfCustomers = Integer.parseInt(JOptionPane.showInputDialog("Number of Customers"));
			queueLength = Integer.parseInt(JOptionPane.showInputDialog("Length of the Drive in Queue"));
			seed1 = Integer.parseInt(JOptionPane.showInputDialog("Seed1"));
			seed2 = Integer.parseInt(JOptionPane.showInputDialog("Seed2"));
		} catch (Exception e) {

		}
		// Values used in charts
		int averagPeopleGoingInBank = 0;
		int averagePeopleGoingInDriveIn = 0;
		int waitingTimeBank = 0;
		int waitingTimeDrive = 0;
		int idleBankTime = 0;
		int idleDriveTime = 0;
		int BankTimeSpent = 0;
		int DriveTimeSpent = 0;
		int numberOfPeopleWaitingInBank = 0;
		int numberOfPeopleWaitingDriveIn = 0;
		ArrayList<Double> totalWaitingTimes = new ArrayList<>();
		ArrayList<Double> driveWaitingTimes = new ArrayList<>();
		ArrayList<Double> bankWaitingTimes = new ArrayList<>();
		ArrayList<Double> totalArrivalTimes = new ArrayList<>();
		ArrayList<Double> driveArrivalTimes = new ArrayList<>();
		ArrayList<Double> bankArrivalTimes = new ArrayList<>();
		double theoreticalInterArrivalTime = 0;
		double theoreticalServiceTime = 0;
		ArrayList<JFreeChart> barChartsPerRun = new ArrayList<>();
		ArrayList<JFreeChart> waitingChartPerRun = new ArrayList<>();

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

		double realInterArrivalDistributionAverage = 0;
		for (int i = 0; i < interArrivalTimeProbabilityList.length; i++) {
			realInterArrivalDistributionAverage += interArrivalTimeProbabilityList[i] * i;
		}

		// Initializing the service time table
		serviceTimeProbabilityList[0] = 0.20;
		serviceTimeProbabilityList[1] = 0.40;
		serviceTimeProbabilityList[2] = 0.28;
		serviceTimeProbabilityList[3] = 0.12;

		double realServiceTimeDistributionAverage = 0;
		for (int i = 0, j = 1; i < serviceTimeProbabilityList.length; i++, j++) {
			realServiceTimeDistributionAverage += serviceTimeProbabilityList[i] * j;
		}
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
			interArrivalRange[i].first = Double.parseDouble(df.format((cumulativeInterArrivalProbability))) * 100 + 1;
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
			serviceTimeRange[i].first = ((Double.parseDouble(df.format(cumulativeServiceTimeProbability))) * 100) + 1;
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

		for (int l = 0; l < numberOfRuns; l++) {

			//////////////////////////////////////////////////////
			/// Random values in the table
			//////////////////////////////////////////////////////
			double[] arrivalTimes = new double[numberOfCustomers];
			double[] waitingTimes = new double[numberOfCustomers];
			Table interArrivalRandomTable = new Table(numberOfCustomers, 3);
			Random random = new Random();
			random.setSeed(l + seed1);
			interArrivalRandomTable.setValue(0, 0, 1 + "");
			interArrivalRandomTable.setValue(0, 1, "-");
			interArrivalRandomTable.setValue(0, 2, "-");

			for (int i = 1; i < numberOfCustomers; i++) {
				double n = (0.001 + random.nextDouble() * (1 - 0.001));
				int randomValue = Range.getRangeProbability(interArrivalRange, n);
				interArrivalRandomTable.setValue(i, 0, i + 1 + "");
				interArrivalRandomTable.setValue(i, 1, n + "");
				interArrivalRandomTable.setValue(i, 2, randomValue + "");
				theoreticalInterArrivalTime += randomValue;
			}

			/*
			 * JFrame randomInterArrivalFrame = new JFrame();
			 * randomInterArrivalFrame.setSize(500, 500);
			 * randomInterArrivalFrame.add(interArrivalRandomTable.table);
			 * randomInterArrivalFrame.setVisible(true);
			 */

			Table serviceTimeRandomTable = new Table(numberOfCustomers, 3);
			Random random2 = new Random();
			random2.setSeed(l + seed2);
			for (int i = 0; i < numberOfCustomers; i++) {
				double n = (0.001 + random2.nextDouble() * (1 - 0.001));
				int randomValue = Range.getRangeProbability(serviceTimeRange, n) + 1;
				serviceTimeRandomTable.setValue(i, 0, i + 1 + "");
				serviceTimeRandomTable.setValue(i, 1, n + "");
				serviceTimeRandomTable.setValue(i, 2, randomValue + "");
				theoreticalServiceTime += randomValue;
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
			record1.add(new SimulationTableRecord("Drive", 1, 0, 0, sr, 0, 0, sr, sr, 0));
			record2.add(new SimulationTableRecord("Dummy", 0, 0, 0, 0, 0, 0, 0, 0, 0));
			recordTotal.add(record1.get(0));

			LinkedList<Integer> carQueue = new LinkedList<Integer>();
			carQueue.add(sr);
			double maximumWaitingTime = 0;
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
					driveWaitingTimes.add((double) record.getWaitingTimeInQueue());
					driveArrivalTimes.add((double) record.getArrivalTime());
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
					bankWaitingTimes.add((double) record.getWaitingTimeInQueue());
					bankArrivalTimes.add((double) record.getArrivalTime());
				}

				totalWaitingTimes.add((double) record.getWaitingTimeInQueue());
				totalArrivalTimes.add((double) record.getArrivalTime());
				recordTotal.add(record);
				arrivalTimes[k] = record.getArrivalTime();
				waitingTimes[k] = record.getWaitingTimeInQueue();
				if (waitingTimes[k] >= maximumWaitingTime) {
					maximumWaitingTime = (waitingTimes[k]);
				}
			}

			barChartsPerRun.add(CustomerGraph.getGraph(arrivalTimes, (int) Math.sqrt(numberOfCustomers),
					"Arrival Stats", "Time of arrival"));
			waitingChartPerRun.add(
					CustomerGraph.getGraph(waitingTimes, (int) maximumWaitingTime, "Waiting Stats", "Waiting Time"));
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
			/*
			 * TheoreticalAnswer
			 * xTheoreticalAnswer=TheoreticalAnswer.getTheoreticalTotal(numberOfCustomers,
			 * interArrivalRandomTable, serviceTimeRandomTable); for(int i =0; i<6;i++) {
			 * interArrivalDistributionTotal[i]=(int)
			 * xTheoreticalAnswer.interArrivalDistribution[i]; } for(int i =0; i<4;i++) {
			 * serviceTimeDistributionTotal[i]=(int)
			 * xTheoreticalAnswer.serviceTimeDistribution[i]; }
			 */
			// Storing the Answer object of this run in an ArrayList
			averagePeopleGoingInDriveIn += record1.size();
			averagPeopleGoingInBank += record2.size()-1;
			answers.add(answer);
		}

		Table answerTable = Answer.getTableRepresentation(numberOfRuns, answers);
		JTabbedPane finalPanel = new JTabbedPane();

		// Details tabbed panel
		// Fill with the tabbed Panel

		DefaultCategoryDataset serviceDistributionDataset = new DefaultCategoryDataset();
		TheoreticalAnswer xtTheoreticalAnswer = TheoreticalAnswer.getDistributions(theoreticalAnswers, 0);
		for (int i = 0; i < 4; i++) {
			serviceDistributionDataset.addValue(serviceTimeProbabilityList[i], "Original", i + 1 +"");
		}
		for (int i = 0; i < 4; i++) {
			serviceDistributionDataset.addValue(xtTheoreticalAnswer.serviceTimeDistribution[i], "Calculated", i + 1 + "");
		}
		
		DefaultCategoryDataset interArrivalDistributionDataset = new DefaultCategoryDataset();
		for (int i = 0; i < 6; i++) {
			interArrivalDistributionDataset.addValue(interArrivalTimeProbabilityList[i], "Original", i + "");
		}
		for (int i = 0; i < 6; i++) {
			interArrivalDistributionDataset.addValue(xtTheoreticalAnswer.interArrivalDistribution[i], "Calculated", i + "");
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
				"Bank", "% of people waiting");
		peopleWaitingDataset.setValue(
				(numberOfPeopleWaitingDriveIn * 100) / (averagePeopleGoingInDriveIn * numberOfRuns), "Drive",
				"% of people waiting");

		JFreeChart averagePeopleServedChart = ChartFactory.createPieChart("Average People In service",
				averagePeopleServedDataset, true, true, false);

		JFreeChart serviceDistributionChart = ChartFactory.createBarChart("Service Time Distributions", "Distribution",
				"Occurances", serviceDistributionDataset, PlotOrientation.VERTICAL, true, true, false);
		JFreeChart arrivalDistributionChart = ChartFactory.createBarChart("Inter Arrival Time Distributions", "Distribution",
				"Occurances", interArrivalDistributionDataset, PlotOrientation.VERTICAL, true, true, false);
		JFreeChart peopleWaitingChart = ChartFactory.createBarChart("% People Waiting In Bank",
				"Number Of People Waiting", "Percentage", peopleWaitingDataset, PlotOrientation.VERTICAL, true, true,
				false);
		JFreeChart statsChart = ChartFactory.createBarChart("Teller Stats", "Category", "Time", statsDataset,
				PlotOrientation.VERTICAL, true, true, false);

		JTabbedPane charts = new JTabbedPane();
		JTabbedPane detailsPanel = new JTabbedPane();
		JTabbedPane detailsGraphPanel = new JTabbedPane();
		JTabbedPane detailedWaitingGraphPanel = new JTabbedPane();
		for (int i = 0; i < numberOfRuns; i++) {
			detailsPanel.add("Run " + (i + 1), details.get(i));
			detailsGraphPanel.add("Run " + (i + 1), new ChartPanel(barChartsPerRun.get(i)));
			detailedWaitingGraphPanel.add("Run" + (i + 1), new ChartPanel(waitingChartPerRun.get(i)));
		}

		double waitingTimeAveragesBank[] = new double[numberOfRuns];
		int o = 0;
		for (Answer a : answers) {
			waitingTimeAveragesBank[o] = a.inBankWaitAvg;
			o++;
		}
		double waitingTimeAveragesDrive[] = new double[numberOfRuns];
		o = 0;
		for (Answer a : answers) {
			waitingTimeAveragesDrive[o] = a.driveInWaitAvg;
			o++;
		}
		HistogramDataset averageWaitingTimeDataset = new HistogramDataset();
		averageWaitingTimeDataset.setType(HistogramType.FREQUENCY);
		averageWaitingTimeDataset.addSeries("Drive", waitingTimeAveragesDrive,(int) Math.sqrt(numberOfRuns));
		averageWaitingTimeDataset.addSeries("Bank", waitingTimeAveragesBank,(int) Math.sqrt(numberOfRuns));
		JFreeChart averageBankWaitingTimeHistogram = ChartFactory.createHistogram("Average Waiting Time", "Waiting Time", "Number Of People",
				averageWaitingTimeDataset, PlotOrientation.VERTICAL, true, true, false);
		HistogramDataset dataset = new HistogramDataset();
		dataset.setType(HistogramType.FREQUENCY);
		double x[] = new double[totalWaitingTimes.size()];

		for (int i = 0; i < x.length; i++) {
			x[i] = totalWaitingTimes.get(i);
		}

		double y[] = new double[driveWaitingTimes.size()];
		for (int i = 0; i < y.length; i++) {
			y[i] = driveWaitingTimes.get(i);
		}

		double z[] = new double[bankWaitingTimes.size()];
		for (int i = 0; i < z.length; i++) {
			z[i] = bankWaitingTimes.get(i);
		}
		dataset.addSeries("Bank Number of People", z, 6);
		dataset.addSeries("Drive Number of People", y, 6);
		dataset.addSeries("Total Number of People", x, 6);

		JFreeChart waitingHistogram = ChartFactory.createHistogram("Waiting Time", "Waiting People", "Number Of People",
				dataset, PlotOrientation.VERTICAL, true, true, false);

		HistogramDataset ArrivalTimesHistogramDataset = new HistogramDataset();
		ArrivalTimesHistogramDataset.setType(HistogramType.FREQUENCY);
		x = new double[totalArrivalTimes.size()];

		for (int i = 0; i < x.length; i++) {
			x[i] = totalArrivalTimes.get(i);
		}

		y = new double[driveArrivalTimes.size()];
		for (int i = 0; i < y.length; i++) {
			y[i] = driveArrivalTimes.get(i);
		}

		z = new double[bankArrivalTimes.size()];
		for (int i = 0; i < z.length; i++) {
			z[i] = bankArrivalTimes.get(i);
		}
		ArrivalTimesHistogramDataset.addSeries("Bank Number of People", z, (int) Math.sqrt(x.length));
		ArrivalTimesHistogramDataset.addSeries("Drive Number of People", y, (int) Math.sqrt(x.length));
		ArrivalTimesHistogramDataset.addSeries("Total Number of People", x, (int) Math.sqrt(x.length));

		JFreeChart arrivalHistogram = ChartFactory.createHistogram("Arrival Time", "Arriving People",
				"Number Of People", ArrivalTimesHistogramDataset, PlotOrientation.VERTICAL, true, true, false);

		String answersHeaders[] = { "Run ID", "Drive In Serv Avg", "Drive In Wait Avg", "In Bank Serv Avg",
				"In Bank Wait Avg", "Probability Bank Waiting", "Probability Bank Idle", "Maximum Queue Length" };

		answerTable.setTitles(answersHeaders);
		JFrame finalFrame = new JFrame("Results");

		String finalAnswersHeaders[] = { "Drive In Serv Avg", "Drive In Wait Avg", "In Bank Serv Avg",
				"In Bank Wait Avg", "Probability Bank Waiting", "Probability Bank Idle", "Maximum Queue Length" };

		charts.add("Average of Users of System", new ChartPanel(averagePeopleServedChart));
		charts.add("Teller Stats", new ChartPanel(statsChart));
		charts.add("Arrival Time Per Run", detailsGraphPanel);
		charts.add("Waiting Time Per Run", detailedWaitingGraphPanel);
		charts.add("Percentage of People Waiting In Bank", new ChartPanel(peopleWaitingChart));
		charts.add("Waiting Time Total", new ChartPanel(waitingHistogram));
		charts.add("Arrival Time Total", new ChartPanel(arrivalHistogram));
		charts.add("Waiting Time Average", new ChartPanel(averageBankWaitingTimeHistogram));
		charts.add("Service Distribution Chart", new ChartPanel(serviceDistributionChart));
		charts.add("Inter Arrival Distribution Chart", new ChartPanel(arrivalDistributionChart));

		Table finalTable = Answer.getAverageOfAllRuns(numberOfRuns, answers);
		finalTable.setTitles(finalAnswersHeaders);
		Table theoreticalAverageOfDistribution = new Table(3, 3);
		theoreticalAverageOfDistribution.setValue(0, 1, "Calculated Value");
		theoreticalAverageOfDistribution.setValue(0, 2, "Real Value");
		theoreticalAverageOfDistribution.setValue(1, 0, "Inter-Arrival");
		theoreticalAverageOfDistribution.setValue(2, 0, "Service Time");
		theoreticalAverageOfDistribution.setValue(1, 1,
				theoreticalInterArrivalTime / (numberOfRuns * (numberOfCustomers)) + "");
		theoreticalAverageOfDistribution.setValue(2, 1,
				theoreticalServiceTime / (numberOfRuns * numberOfCustomers) + "");
		theoreticalAverageOfDistribution.setValue(1, 2, realInterArrivalDistributionAverage + "");
		theoreticalAverageOfDistribution.setValue(2, 2, realServiceTimeDistributionAverage + "");

		finalPanel.addTab("Run Results", new JScrollPane(answerTable.table));
		finalPanel.addTab("Details", new JScrollPane(detailsPanel));
		finalPanel.addTab("Final Answer", new JScrollPane(finalTable.table));
		finalPanel.addTab("Average Theoretical Distribution", theoreticalAverageOfDistribution.table);
		finalPanel.add("Real Probability Distribution", TheoreticalAnswer.getDistributions(theoreticalAnswers));
		finalPanel.add("Charts", charts);
		finalFrame.add(finalPanel);
		finalFrame.setSize(1000, 500);
		finalFrame.setVisible(true);
	}
}