package HMMs;


public class GLRHMM extends HMM{

	// Used for training
		public GLRHMM(String name, int stateLength, int delta) {
			super.stateLength = stateLength;
			super.delta = delta;
			super.name = name;
			initialize();
		}

		// Used for Decoding and Evaluation
		public GLRHMM(String name, double[][] stateTransitProbMatrix,
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
					
					if (j < i) {
						stateTransitProbMatrix[i][j] = 0;
					} else {
						sum += stateTransitProbMatrix[i][j] = Math.random();
					}
				}
				
				for (int j = 0; j < stateLength; j++) {
					stateTransitProbMatrix[i][j] = stateTransitProbMatrix[i][j] / sum;
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
			
			double sum = 0;
			
			for (int i = 0; i < stateLength; i++) {
				sum += piMatrix[i] = Math.random();
			}
			
			for (int i = 0; i < stateLength; i++) {
				piMatrix[i] = piMatrix[i] / sum;
			}
		}
}
