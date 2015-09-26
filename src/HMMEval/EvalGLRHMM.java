package HMMEval;

import HMMObservation.Observation;
import HMMs.HMM;

public class EvalGLRHMM extends Evaluation {

	public EvalGLRHMM(Observation observation, HMM hmm) {
		super.hmm = hmm;
		super.observation = new Observation(observation.getObservations());
		super.stateLength = hmm.getStateLength();
		super.obsLength = observation.getVectorLength();
		super.alpha = new double[obsLength][stateLength];
	}
	
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
	
	public double forward() {
		double sum = 0;
		
		for (int i = 1; i <= stateLength; i++) {
			sum += alpha(obsLength, i);
		}
		
		return sum;
	}
}
