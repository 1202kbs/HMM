package HMMDecoding;

import HMMObservation.Observation;
import HMMState.States;
import HMMs.HMM;

public class DecodeBakisHMM extends Decoding {

	public DecodeBakisHMM(Observation observation, HMM hmm) {
		super.hmm = hmm;
		super.observation = observation;
		super.obsLength= observation.getVectorLength();
		super.stateLength = hmm.getStateLength();
		super.newStateMatrix = new int[obsLength];
	}
	
	public void Viterbi() {
		updateState();
	}

	public double chi(int obsIndex, int stateIndex) {

		if (obsIndex == 1) {
			return hmm.getPiMatrix(stateIndex)
					* hmm.getEmissionProbMatrix(stateIndex,
							observation.getVector(1));
		} else {
			double max = 0;

			for (int j = 1; j <= stateLength; j++) {

				if (chi(obsIndex - 1, j)
						* hmm.getStateTransitProbMatrix(j, stateIndex) > max) {
					max = chi(obsIndex - 1, j)
							* hmm.getStateTransitProbMatrix(j, stateIndex);
				}
			}

			return max * hmm.getEmissionProbMatrix(stateIndex, observation.getVector(obsIndex));
		}
	}

	public int tau(int obsIndex, int stateIndex) {
		int maxIndex = 0;
		double max = 0;

		for (int j = 1; j <= stateLength; j++) {

			if (chi(obsIndex - 1, j)
					* hmm.getStateTransitProbMatrix(j, stateIndex) > max) {
				maxIndex = j;
				max = chi(obsIndex - 1, j)
						* hmm.getStateTransitProbMatrix(j, stateIndex);
			}
		}

		return maxIndex;
	}
	
	public int getOptimalState(int obsIndex) {
		
		if (obsIndex == obsLength) {
			double max = 0;
			int maxIndex = 0;
			
			for (int j = 1; j <= stateLength; j++) {
				
				if(chi(obsLength, j) > max) {
					max = chi(obsLength, j);
					maxIndex = j;
				}
			}
			
			return maxIndex;
		} else {
			
			return tau(obsIndex + 1, getOptimalState(obsIndex + 1));
		}
	}

	public void updateState() {
		
		for (int i = 0; i < obsLength; i++) {
			newStateMatrix[i] = getOptimalState(i + 1);
		}
	}
	
	public int getState(int index) {
		return newStateMatrix[index - 1];
	}
	
	public void printUpdate() {
		System.out.println("<Optimal States>");
		
		System.out.print("[ ");
		for (int i = 1; i <= obsLength; i++) {
			System.out.print(getState(i) + " ");
		}
		System.out.print("]");
	}
}
