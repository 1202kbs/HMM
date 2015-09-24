package HMMState;

import java.util.Iterator;


public class State extends StateComponent {
	double pi;
	int obsTypeNum;
	double[] emissionProb;
	
	public State(double pi, double[] emissionProb) {
		this.pi = pi;
		this.emissionProb = emissionProb;
		this.obsTypeNum = emissionProb.length;
		
	}
	
	public void setPi(double pi) {
		this.pi = pi;
	}
	
	public void setEmissionProb(double[] emissionProb) {
		this.emissionProb = emissionProb;
	}
	
	public double getPi() {
		return pi;
	}
	
	public double[] getEmissionProb() {
		return emissionProb;
	}

	@Override
	public Iterator createIterator() {
		return new NullIterator();
	}
}
