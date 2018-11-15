import java.util.ArrayList;

public class Answer {
	double showRoomEndingUnitAvg;
	double storageEndingUnitAvg;
	double shortageNumber;
	
	public Answer() {

	}

	public Answer(double SREUA,double SEUA,int SN) {
		showRoomEndingUnitAvg=SREUA;
		storageEndingUnitAvg=SEUA;
		shortageNumber=SN;
	}

	// Returns a table of the answers of all runs
	public static Table getTableRepresentation(int numberOfRuns, ArrayList<Answer> records) {
		Table simulation = new Table(numberOfRuns, 4);
		for (int i = 0; i < records.size(); i++) {
			simulation.setValue(i, 0, "" + (i + 1));
			simulation.setValue(i, 1, "" + records.get(i).showRoomEndingUnitAvg);
			simulation.setValue(i, 2, "" + records.get(i).storageEndingUnitAvg);
			simulation.setValue(i, 3, "" + records.get(i).shortageNumber);

		}

		return simulation;
	}

	public static Table getAverageOfAllRuns(int numberOfRuns, ArrayList<Answer> records) {
		Table simulation = new Table(numberOfRuns, 3);

		double averageShowRoom = 0;
		double averageStorage = 0;
		double averageShortage = 0;
		for (int i = 0; i < records.size(); i++) {
			averageShowRoom += records.get(i).showRoomEndingUnitAvg;
			averageStorage += records.get(i).storageEndingUnitAvg;
			averageShortage += records.get(i).shortageNumber;
		}

		simulation.setValue(0, 0, "" + averageShowRoom / numberOfRuns);
		simulation.setValue(0, 1, "" + averageStorage / numberOfRuns);
		simulation.setValue(0, 2, "" + averageShortage / numberOfRuns);
		return simulation;
	}
}
