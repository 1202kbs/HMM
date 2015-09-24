package HMMDecoding;

import HMMObservation.Observation;
import HMMs.HMM;

public abstract class Decoding {
	HMM hmm;
	Observation observation;
	int obsLength;
	int stateLength;
	int[] newStateMatrix;
	
	public abstract void Viterbi();
	
	public abstract double chi(int obsIndex, int stateIndex);
	
	public abstract int tau(int obsIndex, int stateIndex);
	
	public abstract void updateState();
	
	public abstract int getOptimalState(int index);
	
	public abstract int getState(int index);
	
	public abstract void printUpdate();
}
