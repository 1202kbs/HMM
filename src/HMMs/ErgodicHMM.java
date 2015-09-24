package HMMs;


public class ErgodicHMM extends HMM {

	// Used for training
	public ErgodicHMM(int stateLength, int delta) {
		super.stateLength = stateLength;
		super.delta = delta;
		initialize();
	}

	// Used for Decoding and Evaluation
	public ErgodicHMM(double[][] stateTransitProbMatrix,
			double[][] emissionProbMatrix, double[] piMatrix) {
		super.stateTransitProbMatrix = stateTransitProbMatrix;
		super.emissionProbMatrix = emissionProbMatrix;
		super.piMatrix = piMatrix;
		super.stateLength = stateTransitProbMatrix.length;
		super.delta = emissionProbMatrix[0].length;
	}
}
