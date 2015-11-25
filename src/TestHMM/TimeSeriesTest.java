package TestHMM;

public class TimeSeriesTest {
	
	public static void main(String[] args) {
		TimeSeries test = new TimeSeries("fileName.txt", numberOfObservations, ObservationLength);
		test.run();
	}
}
