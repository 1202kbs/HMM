package TestHMM;

import java.util.Scanner;

import HMMEval.EvalLinearHMM;
import HMMEval.Evaluation;
import HMMObservation.Observation;
import HMMs.HMM;
import HMMs.LinearHMM;

public class Test2 {

	public static void main(String[] args) {
		double[][] transitMatrix_A = { { 0.8, 0.2, 0 }, { 0, 0.8, 0.2 },
				{ 0, 0, 1.0 } };
		double[][] emissionMatrix_A = { { 0.9, 0.1, 0 }, { 0.1, 0.8, 0.1 },
				{ 0.9, 0.1, 0 } };
		double[][] transitMatrix_B = { { 0.8, 0.2, 0 }, { 0, 0.8, 0.2 },
				{ 0, 0, 1.0 } };
		double[][] emissionMatrix_B = { { 0.9, 0.1, 0 }, { 0, 0.2, 0.8 },
				{ 0.6, 0.4, 0 } };
		double[][] transitMatrix_C = { { 0, 1, 0 }, { 0, 0.018, 0.982 },
				{ 0, 0, 1 } };
		double[][] emissionMatrix_C = { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 1, 0 } };
		double[][] transitMatrix_D = { { 0.25, 0.75, 0 }, { 0, 0.251, 0.749 },
				{ 0, 0, 1 } };
		double[][] emissionMatrix_D = { { 0, 1, 0 }, { 0, 1, 0 },
				{ 0.751, 0.249, 0 } };
		double[][] transitMatrix_E = { { 0, 1, 0 }, { 0, 0.739, 0.261 },
				{ 0, 0, 1 } };
		double[][] emissionMatrix_E = { { 1, 0, 0 }, { 0, 0, 1 }, { 0, 0, 1 } };

		double[] piMatrix = { 1.0, 0, 0 };

		HMM linearHMM_A = new LinearHMM("A", transitMatrix_A, emissionMatrix_A,
				piMatrix);
		HMM linearHMM_B = new LinearHMM("B", transitMatrix_B, emissionMatrix_B,
				piMatrix);
		HMM linearHMM_C = new LinearHMM("C", transitMatrix_C, emissionMatrix_C,
				piMatrix);
		HMM linearHMM_D = new LinearHMM("D", transitMatrix_D, emissionMatrix_D,
				piMatrix);
		HMM linearHMM_E = new LinearHMM("E", transitMatrix_E, emissionMatrix_E,
				piMatrix);

		Scanner scan = new Scanner(System.in);
		System.out.println("Input number of Observation Vectors: ");
		int vectorLength = scan.nextInt();
		int[] observationArray1 = new int[vectorLength];

		System.out.println("Input Vectors: ");
		for (int i = 0; i < vectorLength; i++) {
			observationArray1[i] = scan.nextInt();
		}

		Observation observation1 = new Observation(observationArray1);

		HMM[] hmmArray = { linearHMM_A, linearHMM_B, linearHMM_C, linearHMM_D, linearHMM_E };

		getWinner(hmmArray, observation1);

		/*
		 * Evaluation evalLinearHMM_A = new EvalLinearHMM(observation1,
		 * linearHMM_A); Evaluation evalLinearHMM_B = new
		 * EvalLinearHMM(observation1, linearHMM_B);
		 * 
		 * System.out.println(evalLinearHMM_A.forward());
		 * System.out.println(evalLinearHMM_B.forward());
		 * 
		 * Training trainLinearHMM_D = new TrainErgodicHMM(observation1,
		 * linearHMM_D); Evaluation evalLinearHMM_D = new
		 * EvalErgodicHMM(observation1, linearHMM_D);
		 *  
		 * System.out.println("Initial Evaluation Value: " +
		 * evalLinearHMM_D.forward()); System.out.println();
		 * 
		 * for (int i = 0; i < 20; i++) {
		 * 
		 * System.out.println("Iteration # " + (i + 1)); System.out.println();
		 * trainLinearHMM_D.BaumWelch(); trainLinearHMM_D.printUpdate();
		 * System.out.println(); System.out.println();
		 * System.out.println("New Evaluation Value: " +
		 * evalLinearHMM_D.forward());
		 * System.out.println("-------------------------------------------");
		 * 
		 * }
		 */
	}

	public static void getWinner(HMM[] hmmArray, Observation observation) {
		double max = 0;
		HMM maxHMM = null;
		Evaluation evalLinearHMM;

		for (int i = 0; i < hmmArray.length; i++) {
			evalLinearHMM = new EvalLinearHMM(observation, hmmArray[i]);

			if (evalLinearHMM.forward() >= max) {
				max = evalLinearHMM.forward();
				maxHMM = hmmArray[i];
			}
		}

		System.out.println("Letter Recognized: " + maxHMM.getName());
	}
}
