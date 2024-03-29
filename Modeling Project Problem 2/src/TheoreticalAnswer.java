
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class TheoreticalAnswer {

	//This class contains the practical distributions
	//requested in the problem
	double[] leadDistribution;
	double[] demandDistribution;
	
	//This is an extra distribution assuming the number of time we actually made an order
	double[] realLeadDistribution;

	public TheoreticalAnswer() {
		leadDistribution = new double[3];
		demandDistribution = new double[5];
		realLeadDistribution = new double[3];
	}

	//Returns the practical answer of this problem
	//The method used here is different from problem 1 after reconsideration
	//caused by wrong calculations, the problem 1 works so it was kept in the original way
	public static TheoreticalAnswer getTheoreticalAnswer(double [] leadFromTable, double [] demandFromTable,
			double[] realLead, int numberOfTimesOrdered,double N, double numberOfCycles) {
		TheoreticalAnswer TA = new TheoreticalAnswer();
	
		TA.realLeadDistribution = realLead.clone();
		TA.demandDistribution = demandFromTable.clone();
		TA.leadDistribution = leadFromTable.clone();
		
		for (int i = 0; i < 5; i++) {
			TA.demandDistribution[i] = TA.demandDistribution[i] / (N*numberOfCycles);
		}

		for (int i = 0; i < 3; i++) {
			TA.leadDistribution[i] = TA.leadDistribution[i] / numberOfCycles;
		}
		for (int i = 0; i < 3; i++) {
			TA.realLeadDistribution[i] = realLead[i] / numberOfTimesOrdered;
		}

		return TA;
	}

	
	//Calculate the average of all the distributions
	public static JTabbedPane getDistributions(ArrayList<TheoreticalAnswer> answers) {
		Table table1 = new Table(3, 2);
		Table table2 = new Table(5, 2);
		Table table3 = new Table(3, 2);
		TheoreticalAnswer x = new TheoreticalAnswer();
		x.demandDistribution = answers.get(0).demandDistribution.clone();
		x.leadDistribution = answers.get(0).leadDistribution.clone();
		x.realLeadDistribution = answers.get(0).realLeadDistribution.clone();
		for (int k = 1; k < answers.size(); k++) {
			for (int i = 0; i < 3; i++) {
				x.leadDistribution[i] += answers.get(k).leadDistribution[i];
				x.realLeadDistribution[i] += answers.get(k).realLeadDistribution[i];
			}
			for (int i = 0; i < 5; i++) {

				x.demandDistribution[i] += answers.get(k).demandDistribution[i];
			}
		}
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		for (int i = 0; i < 3; i++) {

			table1.setValue(i, 0, (i + 1) + "");
			table1.setValue(i, 1, df.format(x.leadDistribution[i] / answers.size()));
		}

		for (int i = 0; i < 3; i++) {

			table3.setValue(i, 0, (i + 1) + "");
			table3.setValue(i, 1, df.format(x.realLeadDistribution[i] / answers.size()));
		}
		for (int i = 0; i < 5; i++) {
			table2.setValue(i, 0, i + "");
			table2.setValue(i, 1, df.format(x.demandDistribution[i] / answers.size()) + "");
		}
		String[] header = { "Lead Time", "Probability" };
		table1.setTitles(header);
		String[] header2 = { "Demand", "Probability" };
		table2.setTitles(header2);
		table3.setTitles(header);
		JTabbedPane xJTabbedPane = new JTabbedPane();
		xJTabbedPane.add("Lead Time Distribution", new JScrollPane(table1.table));
		xJTabbedPane.add("Real Lead Time Distribution", new JScrollPane(table3.table));
		xJTabbedPane.add("Demand Time Distribution", new JScrollPane(table2.table));
		return xJTabbedPane;
	}
	
	public static TheoreticalAnswer getDistributions(ArrayList<TheoreticalAnswer> answers,int m) {
		TheoreticalAnswer x = new TheoreticalAnswer();
		x.demandDistribution = answers.get(0).demandDistribution.clone();
		x.leadDistribution = answers.get(0).leadDistribution.clone();
		x.realLeadDistribution = answers.get(0).realLeadDistribution.clone();
		for (int k = 1; k < answers.size(); k++) {
			for (int i = 0; i < 5; i++) {
				x.demandDistribution[i] += answers.get(k).demandDistribution[i];
			}
			for (int i = 0; i < 3; i++) {

				x.realLeadDistribution[i] += answers.get(k).realLeadDistribution[i];
				x.leadDistribution[i] += answers.get(k).leadDistribution[i];
			}
		}
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		for (int i = 0; i < 5; i++) {

			x.demandDistribution[i]=x.demandDistribution[i] / answers.size();
		}
		for (int i = 0; i < 3; i++) {
			x.leadDistribution[i]=x.leadDistribution[i] / answers.size();
			x.realLeadDistribution[i]=x.realLeadDistribution[i] / answers.size();
		}
		
		return x;
	}

}
