package HMMs;


public abstract class HMM {
	double[][] stateTransitProbMatrix;
	double[][] emissionProbMatrix;
	double[] piMatrix;
	int stateLength;
	int delta;
	
	public void initialize() {
		stateTransitProbMatrix = new double[stateLength][stateLength];
		emissionProbMatrix = new double[stateLength][delta];
		piMatrix = new double[stateLength];
		
		for (int i = 0; i < stateLength; i++) {
			double sum = 0;
			
			for (int j = 0; j < stateLength; j++) {
				sum += stateTransitProbMatrix[i][j] = Math.random();
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
	
	public double getStateTransitProbMatrix(int stateIndex1, int stateIndex2) {
		return stateTransitProbMatrix[stateIndex1 - 1][stateIndex2 - 1];
	}
	
	public double getEmissionProbMatrix(int stateIndex, int obsIndex) {
		return emissionProbMatrix[stateIndex - 1][obsIndex - 1];
	}
	
	public double getPiMatrix(int stateIndex) {
		return piMatrix[stateIndex - 1];
	}
	
	public int getStateLength() {
		return stateLength;
	}
	
	public int getDelta() {
		return delta;
	}
	
	public void setStateTransitProbMatrix(double transitionProb, int i, int j) {
		this.stateTransitProbMatrix[i - 1][j - 1] = transitionProb ;
	}
	
	public void setStateTransitProbMatrix(double[][] stateTransitProbMatrix) {
		this.stateTransitProbMatrix = stateTransitProbMatrix;
	}
	
	public void setEmissionProbMatrix(double emissionProb, int stateIndex, int vectorIndex) {
		this.emissionProbMatrix[stateIndex - 1][vectorIndex - 1] = emissionProb;
	}
	
	public void setEmissionProbMatrix(double[][] emissionProbMatrix) {
		this.emissionProbMatrix = emissionProbMatrix;
	}
	
	public void setPiMatrix(double pi, int i) {
		this.piMatrix[i - 1] = pi;
	}
	
	public void setPiMatrix(double[] piMatrix) {
		this.piMatrix = piMatrix;
	}
}
