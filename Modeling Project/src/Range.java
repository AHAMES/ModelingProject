public class Range {

	float first;
	float second;

	public Range() {
		first = 0;
		second = 0;
	}

	public Range(float f, float s) {
		first = f;
		second = s;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return first + "-" + second;
	}

	static int getRangeProbability(Range[] ranges, float currentRange) {
		for (int i = 0, l = ranges.length; i < l; i++) {
			if(currentRange>=ranges[i].first && currentRange<=ranges[i].second)
			{
				return i;
			}
		}
		return 0;
	}
}
