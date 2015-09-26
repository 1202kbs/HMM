package HMMTraining;

import HMMObservation.Observation;
import HMMs.HMM;

public class TrainGLRHMM extends Training {

	public TrainGLRHMM(Observation observation, HMM hmm) {
		super.observation = observation;
		super.hmm = hmm;
		super.stateLength = hmm.getStateLength();
		super.obsLength = observation.getVectorLength();
		super.delta = hmm.getDelta();
		super.transitScaleFactor = new double[obsLength];
		super.emissionScaleFactor = new double[obsLength];
		super.newTransitMatrix = new double[stateLength][stateLength];
		super.newEmissionMatrix = new double[stateLength][delta];
		super.newPiMatrix = new double[stateLength];
	}

	@Override
	public void BaumWelch() {
		for (int i = 1; i <= stateLength; i++) {
			for (int j = 1; j <= stateLength; j++) {

				updateTransitProb(i, j);

			}
		}
		
		for (int i = 1; i <= stateLength; i++) {
			for (int j = 1; j <= delta; j++) {

				updateEmissionProb(i, j);

			}
		}

		for (int i = 1; i <= stateLength; i++) {

			updatePi(i);
		}
		
		finalize();
	}

	@Override
	public double alpha(int obsIndex, int stateIndex) {

		if (obsIndex == 1) {
			return hmm.getPiMatrix(stateIndex)
					* hmm.getEmissionProbMatrix(stateIndex,
							observation.getVector(1));
		} else {
			double sum = 0;

			for (int j = 1; j <= stateLength; j++) {
				sum += hmm.getStateTransitProbMatrix(j, stateIndex)
						* alpha(obsIndex - 1, j);
			}

			return sum
					* hmm.getEmissionProbMatrix(stateIndex,
							observation.getVector(obsIndex));
		}
	}

	@Override
	public double beta(int obsIndex, int stateIndex) {

		if (obsIndex == obsLength) {

			return 1;
		} else {
			double sum = 0;

			for (int j = 1; j <= stateLength; j++) {
				sum += hmm.getStateTransitProbMatrix(stateIndex, j)
						* hmm.getEmissionProbMatrix(j,
								observation.getVector(obsIndex + 1))
						* beta(obsIndex + 1, j);
			}

			return sum;
		}
	}

	public double sigma(int obsIndex, int stateIndex) {
		double numerator = alpha(obsIndex, stateIndex)
				* beta(obsIndex, stateIndex);
		double denominator = 0;

		for (int j = 1; j <= stateLength; j++) {
			denominator += alpha(obsIndex, j) * beta(obsIndex, j);
		}

		return numerator / denominator;
	}

	public double kappa(int obsIndex, int stateIndex1, int stateIndex2) {

		double numerator = alpha(obsIndex, stateIndex1)
				* hmm.getStateTransitProbMatrix(stateIndex1, stateIndex2)
				* hmm.getEmissionProbMatrix(stateIndex2, observation.getVector(obsIndex + 1))
				* beta(obsIndex + 1, stateIndex2);

		double denominator = 0;

		for (int k = 1; k <= stateLength; k++) {
			for (int l = 1; l <= stateLength; l++) {
				denominator += alpha(obsIndex, k)
						* hmm.getStateTransitProbMatrix(k, l)
						* hmm.getEmissionProbMatrix(l, observation.getVector(obsIndex + 1))
						* beta(obsIndex + 1, l);
			}
		}

		return numerator / denominator;
	}

	public void updateTransitProb(int stateIndex1, int stateIndex2) {
		double numerator = 0;
		double denominator = 0;
		double newTransitProb;

		for (int t = 1; t <= obsLength - 1; t++) {
			numerator += kappa(t, stateIndex1, stateIndex2);
		}

		for (int t = 1; t <= obsLength - 1; t++) {
			denominator += sigma(t , stateIndex1);
		}

		newTransitProb = numerator / denominator;

		newTransitMatrix[stateIndex1 - 1][stateIndex2 - 1] = newTransitProb;
	}

	public void updateEmissionProb(int stateIndex, int vectorIndex) {
		double numerator = 0;
		double denominator = 0;
		double newEmissionProb;

		for (int t = 1; t <= obsLength; t++) {

			if (observation.getVector(t) == vectorIndex) {
				numerator += sigma(t, stateIndex);
			}
		}

		for (int t = 1; t <= obsLength; t++) {

			denominator += sigma(t, stateIndex);
		}

		newEmissionProb = numerator / denominator;

		newEmissionMatrix[stateIndex - 1][vectorIndex - 1] = newEmissionProb;
	}

	public void updatePi(int stateIndex) {

		newPiMatrix[stateIndex - 1] = sigma(1, stateIndex);
	}

	public void scaleTransitMatrix() {

		for (int i = 0; i < stateLength; i++) {
			for (int j = 0; j < stateLength; j++) {
				transitScaleFactor[i] += newTransitMatrix[i][j];
			}
		}

		for (int i = 0; i < stateLength; i++) {
			transitScaleFactor[i] = 1 / transitScaleFactor[i];
		}

		for (int i = 0; i < stateLength; i++) {
			for (int j = 0; j < stateLength; j++) {
				newTransitMatrix[i][j] = newTransitMatrix[i][j]
						* transitScaleFactor[i];
			}
		}
	}

	public void scaleEmissionMatrix() {

		for (int i = 0; i < stateLength; i++) {
			for (int j = 0; j < delta; j++) {
				emissionScaleFactor[i] += newEmissionMatrix[i][j];
			}
		}

		for (int i = 0; i < stateLength; i++) {
			emissionScaleFactor[i] = 1 / emissionScaleFactor[i];
		}

		for (int i = 0; i < stateLength; i++) {
			for (int j = 0; j < delta; j++) {
				newEmissionMatrix[i][j] = newEmissionMatrix[i][j]
						* emissionScaleFactor[i];
			}
		}
	}

	public void scalePiMatrix() {

		for (int i = 0; i < stateLength; i++) {
			piScaleFactor += newPiMatrix[i];
		}

		piScaleFactor = 1 / piScaleFactor;

		for (int i = 0; i < stateLength; i++) {
			newPiMatrix[i] = newPiMatrix[i] * piScaleFactor;
		}
	}

	public void printUpdate() {
		System.out.println("[State Transition Probability Matrix]");

		for (int i = 0; i < stateLength; i++) {
			for (int j = 0; j < stateLength; j++) {
				System.out.printf("%-7.3f",
						hmm.getStateTransitProbMatrix(i + 1, j + 1));
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println("[Emission Probability Matrix]");

		for (int i = 0; i < stateLength; i++) {
			for (int j = 0; j < delta; j++) {
				System.out.printf("%-7.3f",
						hmm.getEmissionProbMatrix(i + 1, j + 1));
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println("[Pi Matrix]");

		for (int i = 0; i < stateLength; i++) {
			System.out.printf("%-7.3f", hmm.getPiMatrix(i + 1));
		}
	}

	public void finalize() {
		hmm.setStateTransitProbMatrix(newTransitMatrix);
		hmm.setEmissionProbMatrix(newEmissionMatrix);
		hmm.setPiMatrix(newPiMatrix);
	}
}
