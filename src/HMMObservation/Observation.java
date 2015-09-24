package HMMObservation;

import java.util.Iterator;

public class Observation extends ObserveComponent {
	int vectorLength;
	int[] vector;
	
	public Observation(int[] vector) {
		this.vectorLength = vector.length;
		this.vector = vector;
	}
	
	public int getVectorLength() {
		return vectorLength;
	}
	
	public int getVector(int index) {
		return vector[index - 1];
	}
	
	public int[] getObservations() {
		return vector;
	}
	
	public Iterator createIterator() {
		return new NullIterator();
	}
}
