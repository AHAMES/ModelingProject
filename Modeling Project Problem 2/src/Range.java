public class Range {

	//A class that contains the ranges
	//Used to find random numbers
	
	double first;
	double second;

	public Range() {
		first = 0;
		second = 0;
	}

	public Range(double f, double s) {
		first = f;
		second = s;
	}

	@Override
	public String toString() {
		return first + "-" + second;
	}

	
	//Searches an array of ranges to check which index do it belong to in a table
	static int getRangeProbability(Range[] ranges, double currentRange) {
		for (int i = 0, l = ranges.length; i < l; i++) {
			if(currentRange>=ranges[i].first && currentRange<=ranges[i].second)
			{
				return i;
			}
		}
		return 0;
	}
}
