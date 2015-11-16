package TestHMM;

import java.io.File;
import java.util.Scanner;

import HMMEval.EvalLinearHMM;
import HMMEval.Evaluation;
import HMMObservation.Observation;
import HMMObservation.Observations;
import HMMTraining.TrainLinearHMM;
import HMMTraining.Training;
import HMMs.HMM;
import HMMs.LinearHMM;

public class WeatherTest {

	public static void main(String[] args) {
		
		WeatherTest test1 = new WeatherTest();
		int[][] temp = test1.readFile("수원.txt");
		
		Observations observations = new Observations();
		
		for (int i = 0; i < 10; i++) {
			int[] obv = new int[11];
	
			for (int j = 0; j < 11; j++) {	
				obv[j] = temp[i][j];				
			}
			
			Observation observation = new Observation(obv);
			observations.add(observation);
		}
		
		test1.crossValidate(observations);
	}

	public int[][] readFile(String filename) {
		double[][] result = new double[10][12];
		int[][] intResult = new int[10][11];
		
		try {
			File file = new File(filename);
			Scanner scan = new Scanner(file);

			while (scan.hasNextLine()) {

				for (int i = 0; i < 10; i++) {
					for (int j = 0; j < 12; j++) {
						String line = scan.next();
						double temp = Double.parseDouble(line);
						
						result[i][j] = temp;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 * <Observations>
		 * Same     = 1
		 * Decrease = 2
		 * Increase = 3
		 */
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 11; j++) {
				
				if (result[i][j] < result[i][j + 1]) {
					intResult[i][j] = 2;
				} else if (result[i][j] > result[i][j + 1]) {
					intResult[i][j] = 3;
				} else {
					intResult[i][j] = 1;
				}
			}
		}
		
		return intResult;
	}
	
	public void crossValidate(Observations obs) {
		HMM linearHMM = new LinearHMM("WeatherData", 6, 3);
		
		for (int i = 0; i < obs.getSize(); i++) {
			System.out.println("Training Observation #" + (i + 1));
			Training trainLinearHMM = new TrainLinearHMM((Observation)obs.getChild(i), linearHMM);
			Evaluation evalLinearHMM = new EvalLinearHMM((Observation)obs.getChild(i), linearHMM);
			
			System.out.println();
			System.out.println("Initial Evaluation Value: "
					+ evalLinearHMM.forward());
			System.out.println();
			
			for (int j = 0; j < 30; j++) {
				System.out.println("Iteration # " + (j + 1));
				System.out.println();
				trainLinearHMM.BaumWelch();
				System.out.println();
				System.out.println();
				System.out.println("New Evaluation Value: "
						+ evalLinearHMM.forward());
				System.out.println("-------------------------------------------");
			}
			
			for (int j = 0; j < obs.getSize(); j++) {
				System.out.println("Cross Validation");
				Evaluation crossVal = new EvalLinearHMM((Observation)obs.getChild(j), linearHMM);
				System.out.println("#" + (j + 1) + " : " + crossVal.forward());
			}
		}
	}
}
