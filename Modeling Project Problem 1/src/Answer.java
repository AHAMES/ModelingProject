import java.util.ArrayList;

public class Answer {
	double driveInServiceAvg;
	double driveInWaitAvg;
	double inBankServiceAvg;
	double inBankWaitAvg;
	double probabilityBankWaiting;
	double probabilityBankIdle;
	double maximumQueueLength;

	public Answer() {

	}

	public Answer(double DISA, double DIWA, double IBSA, double IBWA, double PBW, double PBI, double MQL) {
		driveInServiceAvg = DISA;
		driveInWaitAvg = DIWA;
		inBankServiceAvg = IBSA;
		inBankWaitAvg = IBWA;
		probabilityBankWaiting = PBW;
		probabilityBankIdle = PBI;
		maximumQueueLength = MQL;
	}

	// Returns a table of the answers of all runs
	public static Table getTableRepresentation(int numberOfRuns, ArrayList<Answer> records) {
		Table simulation = new Table(numberOfRuns, 8);
		for (int i = 0; i < records.size(); i++) {
			simulation.setValue(i, 0, "" + (i + 1));
			simulation.setValue(i, 1, "" + records.get(i).driveInServiceAvg);
			simulation.setValue(i, 2, "" + records.get(i).driveInWaitAvg);
			simulation.setValue(i, 3, "" + records.get(i).inBankServiceAvg);
			simulation.setValue(i, 4, "" + records.get(i).inBankWaitAvg);
			simulation.setValue(i, 5, "" + records.get(i).probabilityBankWaiting);
			simulation.setValue(i, 6, "" + records.get(i).probabilityBankIdle);
			simulation.setValue(i, 7, "" + records.get(i).maximumQueueLength);

		}

		return simulation;
	}

	public static Table getAverageOfAllRuns(int numberOfRuns, ArrayList<Answer> records) {
		Table simulation = new Table(numberOfRuns, 7);

		double averageDriveInService = 0;
		double averageDriveInWait = 0;
		double averageInBankService = 0;
		double averageInBankWait = 0;
		double averageProbabilityBankWaiting = 0;
		double averageProbabilityBankIdle = 0;
		double averageMaximumLength = 0;
		for (int i = 0; i < records.size(); i++) {
			averageDriveInService += records.get(i).driveInServiceAvg;
			averageDriveInWait += records.get(i).driveInWaitAvg;
			averageInBankService += records.get(i).inBankServiceAvg;
			averageInBankWait += records.get(i).inBankWaitAvg;
			averageProbabilityBankWaiting += records.get(i).probabilityBankWaiting;
			averageProbabilityBankIdle += records.get(i).probabilityBankIdle;
			averageMaximumLength += records.get(i).maximumQueueLength;
		}

		simulation.setValue(0, 0, "" + averageDriveInService / numberOfRuns);
		simulation.setValue(0, 1, "" + averageDriveInWait / numberOfRuns);
		simulation.setValue(0, 2, "" + averageInBankService / numberOfRuns);
		simulation.setValue(0, 3, "" + averageInBankWait / numberOfRuns);
		simulation.setValue(0, 4, "" + averageProbabilityBankWaiting / numberOfRuns);
		simulation.setValue(0, 5, "" + averageProbabilityBankIdle / numberOfRuns);
		simulation.setValue(0, 6, "" + averageMaximumLength / numberOfRuns);
		return simulation;
	}
}
