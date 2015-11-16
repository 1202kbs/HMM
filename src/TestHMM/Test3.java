package TestHMM;

import HMMEval.EvalLinearHMM;
import HMMEval.Evaluation;
import HMMObservation.Observation;
import HMMTraining.TrainLinearHMM;
import HMMTraining.Training;
import HMMs.HMM;
import HMMs.LinearHMM;

public class Test3 {

	public static void main(String[] args) {
		double[][] transitMatrix_E = { { 0, 1, 0 }, { 0, 0.739, 0.261 },
				{ 0, 0, 1 } };
		double[][] emissionMatrix_E = { { 1, 0, 0 }, { 0, 0, 1 }, { 0, 0, 1 } };

		HMM linearHMM_E = new LinearHMM("E", 3, 3);

		int[] obsArray = { 1, 3, 3, 3 };
		Observation observation = new Observation(obsArray);

		Training trainLinearHMM_E = new TrainLinearHMM(observation, linearHMM_E);
		Evaluation evalLinearHMM_E = new EvalLinearHMM(observation, linearHMM_E);

		System.out.println("Initial Evaluation Value: "
				+ evalLinearHMM_E.forward());
		System.out.println();

		for (int i = 0; i < 60; i++) {

			System.out.println("Iteration # " + (i + 1));
			System.out.println();
			trainLinearHMM_E.BaumWelch();
			trainLinearHMM_E.printUpdate();
			System.out.println();
			System.out.println();
			System.out.println("New Evaluation Value: "
					+ evalLinearHMM_E.forward());
			System.out.println("-------------------------------------------");

		}

	}
}
