package Test;

import HMMDecoding.DecodeErgodicHMM;
import HMMDecoding.Decoding;
import HMMEval.EvalErgodicHMM;
import HMMEval.Evaluation;
import HMMObservation.Observation;
import HMMTraining.TrainErgodicHMM;
import HMMTraining.Training;
import HMMs.ErgodicHMM;
import HMMs.HMM;

public class Test1 {
	
	public static void main(String[] args) {
		double[][] transitMatrix = {{0.7, 0.3}, {0.4, 0.6}};
		double[][] emissionMatrix = {{0.1, 0.4, 0.5}, {0.6, 0.3, 0.1}};
		double[] piMatrix = {0.6, 0.4};
		
		HMM ergodicHMM = new ErgodicHMM(transitMatrix, emissionMatrix, piMatrix);
		//HMM ergodicHMM = new ErgodicHMM(3, 3);
		
		/*<States>
		 *Rainy    : 1
		 *Sunny    : 2
		 *<Observations>
		 *Walking  : 1
		 *Shopping : 2
		 *Cleaning : 3*/
		
		int[] observationArray1 = {1, 1, 3, 2};
		Observation observation1 = new Observation(observationArray1);
		
		int[] observationArray2 = {2, 2, 3, 1, 2};
		Observation observation2 = new Observation(observationArray2);
		
		Training trainErgodicHMM = new TrainErgodicHMM(observation1, ergodicHMM);
		Evaluation evalErgodicHMM = new EvalErgodicHMM(observation1, ergodicHMM);
		Decoding decodeErgodicHMM = new DecodeErgodicHMM(observation2, ergodicHMM);
		
		System.out.println("Initial Evaluation Value: " + evalErgodicHMM.forward());
		System.out.println();
		
		for (int i = 0; i < 40; i++) {
			
			System.out.println("Iteration # " + (i + 1));
			System.out.println();
			trainErgodicHMM.BaumWelch();
			trainErgodicHMM.printUpdate();
			System.out.println();
			System.out.println();
			System.out.println("New Evaluation Value: " + evalErgodicHMM.forward());
			System.out.println("-------------------------------------------");
			
		}
		
		System.out.println();
		decodeErgodicHMM.Viterbi();
		decodeErgodicHMM.printUpdate();
	}
}
