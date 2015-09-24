package HMMEval;

import HMMObservation.Observation;
import HMMs.HMM;

public abstract class Evaluation {
	HMM hmm;
	Observation observation;
	int stateLength;
	int obsLength;
	double[][] alpha;
	
	public abstract double alpha(int obsIndex, int stateIndex);
	
	public abstract double forward();

}
