
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class TheoreticalAnswer {

	//This class contains the practical distributions
	//requested in the problem
	
	double[] serviceTimeDistribution;
	double[] interArrivalDistribution;

	public TheoreticalAnswer() {
		serviceTimeDistribution = new double[4];
		interArrivalDistribution = new double[6];
	}

	//Returns the practical answer of this problem
	//The method used here is different from problem 2 after reconsideration
	//As this was the original implementation for both
	public static TheoreticalAnswer getTheoreticalTotal(int numberOfCustomers, Table interArrivalRandomTable,
			Table serviceTimeRandomTable) {
		TheoreticalAnswer TA = new TheoreticalAnswer();
		for (int k = 0; k < numberOfCustomers; k++) {
			if (interArrivalRandomTable.getCell(k, 1) == "-") {
				TA.interArrivalDistribution[0]++;
			}
			else
			{
				int IA = Integer.parseInt(interArrivalRandomTable.getCell(k, 2));
				TA.interArrivalDistribution[IA]++;
			}

			int ST = Integer.parseInt(serviceTimeRandomTable.getCell(k, 2));
			TA.serviceTimeDistribution[ST - 1]++;
		}
		return TA;
	}
	
	public static TheoreticalAnswer getTheoreticalAnswer(int numberOfCustomers, Table interArrivalRandomTable,
			Table serviceTimeRandomTable) {
		TheoreticalAnswer TA = new TheoreticalAnswer();
		for (int k = 0; k < numberOfCustomers; k++) {
			if (interArrivalRandomTable.getCell(k, 1) == "-") {
				TA.interArrivalDistribution[0]++;
			}
			else
			{
				int IA = Integer.parseInt(interArrivalRandomTable.getCell(k, 2));
				TA.interArrivalDistribution[IA]++;
			}

			int ST = Integer.parseInt(serviceTimeRandomTable.getCell(k, 2));
			TA.serviceTimeDistribution[ST - 1]++;
		}
		for (int i = 0; i < TA.interArrivalDistribution.length; i++) {
			TA.interArrivalDistribution[i] = TA.interArrivalDistribution[i] / numberOfCustomers;
		}

		for (int i = 0; i < TA.serviceTimeDistribution.length; i++) {
			TA.serviceTimeDistribution[i] = TA.serviceTimeDistribution[i] / numberOfCustomers;
		}
		return TA;
	}
	//Calculate the average of all the distributions
	public static JTabbedPane getDistributions(ArrayList<TheoreticalAnswer> answers) {
		Table table1 = new Table(4, 2);
		Table table2 = new Table(6, 2);
		TheoreticalAnswer x = new TheoreticalAnswer();
		x.interArrivalDistribution = answers.get(0).interArrivalDistribution.clone();
		x.serviceTimeDistribution = answers.get(0).serviceTimeDistribution.clone();
		for (int k = 1; k < answers.size(); k++) {
			for (int i = 0; i < 4; i++) {
				x.serviceTimeDistribution[i] += answers.get(k).serviceTimeDistribution[i];
			}
			for (int i = 0; i < 6; i++) {

				x.interArrivalDistribution[i] += answers.get(k).interArrivalDistribution[i];
			}
		}
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		for (int i = 0; i < 4; i++) {

			table1.setValue(i, 0, (i + 1) + "");
			table1.setValue(i, 1, df.format(x.serviceTimeDistribution[i] / answers.size()));
		}
		for (int i = 0; i < 6; i++) {
			table2.setValue(i, 0, i + "");
			table2.setValue(i, 1, df.format(x.interArrivalDistribution[i] / answers.size()) + "");
		}
		String[] header = { "Service Time", "Probability" };
		table1.setTitles(header);
		String[] header2 = { "Interarrival Time", "Probability" };
		table2.setTitles(header2);
		JTabbedPane xJTabbedPane = new JTabbedPane();
		xJTabbedPane.add("Service Time Distribution", new JScrollPane(table1.table));
		xJTabbedPane.add("Interarrival Time Distribution", new JScrollPane(table2.table));
		return xJTabbedPane;
	}
	
	public static TheoreticalAnswer getDistributions(ArrayList<TheoreticalAnswer> answers,int m) {
		TheoreticalAnswer x = new TheoreticalAnswer();
		x.interArrivalDistribution = answers.get(0).interArrivalDistribution.clone();
		x.serviceTimeDistribution = answers.get(0).serviceTimeDistribution.clone();
		for (int k = 1; k < answers.size(); k++) {
			for (int i = 0; i < 4; i++) {
				x.serviceTimeDistribution[i] += answers.get(k).serviceTimeDistribution[i];
			}
			for (int i = 0; i < 6; i++) {

				x.interArrivalDistribution[i] += answers.get(k).interArrivalDistribution[i];
			}
		}
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		for (int i = 0; i < 4; i++) {

			x.serviceTimeDistribution[i]=x.serviceTimeDistribution[i] / answers.size();
		}
		for (int i = 0; i < 6; i++) {
			x.interArrivalDistribution[i]=x.interArrivalDistribution[i] / answers.size();
		}
		
		return x;
	}

}
