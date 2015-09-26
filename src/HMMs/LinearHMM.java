package HMMs;


public class LinearHMM extends HMM {

	public LinearHMM(String name, int stateLength, int delta) {
		super.stateLength = stateLength;
		super.delta = delta;
		super.name = name;
		initialize();
	}

	public LinearHMM(String name, double[][] stateTransitProbMatrix,
			double[][] emissionProbMatrix, double[] piMatrix) {
		super.stateTransitProbMatrix = stateTransitProbMatrix;
		super.emissionProbMatrix = emissionProbMatrix;
		super.piMatrix = piMatrix;
		super.stateLength = stateTransitProbMatrix.length;
		super.delta = emissionProbMatrix[0].length;
		super.name = name;
	}

	@Override
	public void initialize() {

		stateTransitProbMatrix = new double[stateLength][stateLength];
		emissionProbMatrix = new double[stateLength][delta];
		piMatrix = new double[stateLength];

		for (int i = 0; i < stateLength; i++) {
			double sum = 0;

			for (int j = 0; j < stateLength; j++) {
				
				if (j == i || j == i + 1) {
					sum += stateTransitProbMatrix[i][j] = Math.random();
				} else {
					stateTransitProbMatrix[i][j] = 0;
				}
			}

			for (int j = 0; j < stateLength; j++) {
				stateTransitProbMatrix[i][j] = stateTransitProbMatrix[i][j]
						/ sum;
			}
		}

		for (int i = 0; i < stateLength; i++) {
			double sum = 0;

			for (int j = 0; j < delta; j++) {
				sum += emissionProbMatrix[i][j] = Math.random();
			}

			for (int j = 0; j < delta; j++) {
				emissionProbMatrix[i][j] = emissionProbMatrix[i][j] / sum;
			}
		}

		piMatrix[0] = 1;
		
		for (int i = 1; i < stateLength; i++) {
			piMatrix[i] = 0;
		}
	}
}
