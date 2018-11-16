
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class TheoreticalAnswer {

	double[] leadDistribution;
	double[] demandDistribution;
	double [] realLeadDistribution;
	public TheoreticalAnswer() {
		leadDistribution = new double[3];
		demandDistribution = new double[5];
		realLeadDistribution=new double[3];
	}

	public static TheoreticalAnswer getTheoreticalAnswer(Table demandRandomTable,
			Table leadRandomTable,double [] realLead,int numberOfTimesOrdered) {
		TheoreticalAnswer TA=new TheoreticalAnswer();
		for (int k = 0; k < demandRandomTable.table.getRowCount(); k++) {
			if(demandRandomTable.getCell(k, 1)!="-")
			{
				int IA = Integer.parseInt(demandRandomTable.getCell(k, 2));
				TA.demandDistribution[IA]++;
			
			}
		}
		TA.realLeadDistribution=realLead.clone();
		for (int k = 0; k < leadRandomTable.table.getRowCount(); k++) {
				int ST = Integer.parseInt(leadRandomTable.getCell(k, 2));
				TA.leadDistribution[ST - 1]++;
		}
		for (int i = 0; i < TA.demandDistribution.length; i++) {
			TA.demandDistribution[i] = TA.demandDistribution[i] / demandRandomTable.table.getRowCount();
		}

		for (int i = 0; i < TA.leadDistribution.length; i++) {
			TA.leadDistribution[i] = TA.leadDistribution[i] / leadRandomTable.table.getRowCount();
		}
		for(int i=0;i<3;i++)
		{
			TA.realLeadDistribution[i]=realLead[i]/numberOfTimesOrdered;
		}
		
		return TA;
	}

	public static JTabbedPane getDistributions(ArrayList<TheoreticalAnswer> answers) {
		Table table1 = new Table(3, 2);
		Table table2 = new Table(5, 2);
		Table table3 = new Table(3, 2);
		TheoreticalAnswer x = new TheoreticalAnswer();
		x.demandDistribution = answers.get(0).demandDistribution.clone();
		x.leadDistribution = answers.get(0).leadDistribution.clone();
		for (int k = 1; k < answers.size(); k++) {
			for (int i = 0; i < 3; i++) {
				x.leadDistribution[i] += answers.get(k).leadDistribution[i];
				x.realLeadDistribution[i]+=answers.get(k).realLeadDistribution[i];
			}
			for (int i = 0; i < 5; i++) {

				x.demandDistribution[i] += answers.get(k).demandDistribution[i];
			}
		}
		DecimalFormat df=new DecimalFormat();
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

}
