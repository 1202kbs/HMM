package HMMTraining;

import HMMObservation.Observation;
import HMMs.HMM;

public abstract class Training {
	HMM hmm;
	Observation observation;
	int stateLength;
	int obsLength;
	int delta;
	double[] transitScaleFactor;
	double[] emissionScaleFactor;
	double piScaleFactor;
	double[][] newTransitMatrix;
	double[][] newEmissionMatrix;
	double[] newPiMatrix;
	
	public abstract void BaumWelch();
	
	public abstract double alpha(int obsIndex, int stateIndex);
	
	public abstract double beta(int obsIndex, int stateIndex);
	
	public abstract double sigma(int obsIndex, int stateIndex);
	
	public abstract double kappa(int obsIndex, int stateIndex1, int stateIndex2);
	
	public abstract void updateTransitProb(int stateIndex1, int stateIndex2);
	
	public abstract void updateEmissionProb(int stateIndex, int vectorIndex);
	
	public abstract void updatePi(int stateIndex);
	
	public abstract void scaleTransitMatrix();
	
	public abstract void scaleEmissionMatrix();
	
	public abstract void scalePiMatrix();
	
	public abstract void printUpdate();
	
	public abstract void finalize();
	
	public HMM getHMM() {
		return hmm;
	}
}
