package HMMState;

import java.util.Iterator;

public abstract class StateComponent {

	public void add(StateComponent stateComponent) {
		throw new UnsupportedOperationException();
	}
	
	public void remove(StateComponent menuComponent) {
		throw new UnsupportedOperationException();
	}
	
	public StateComponent getChild(int i) {
		throw new UnsupportedOperationException();
	}
	
	public int getSize() {
		throw new UnsupportedOperationException();
	}
	
	public void setPi(double pi) {
		throw new UnsupportedOperationException();
	}
	
	public void setEmissionProb(double[] emissionProb) {
		throw new UnsupportedOperationException();
	}
	
	public double getPi() {
		throw new UnsupportedOperationException();
	}
	
	public double[] getEmissionProb() {
		throw new UnsupportedOperationException();
	}
	
	public abstract Iterator createIterator();
}
