package TestHMM;

import java.io.File;
import java.util.Scanner;

import HMMEval.EvalGLRHMM;
import HMMEval.Evaluation;
import HMMObservation.Observation;
import HMMObservation.Observations;
import HMMTraining.TrainGLRHMM;
import HMMTraining.Training;
import HMMs.GLRHMM;
import HMMs.HMM;

public class TimeSeries {
	private String fileName;
	private int obsNumber;
	private int obsLength;
	
	public TimeSeries(String fileName, int obsNumber, int obsLength) {
		this.fileName = fileName;
		this.obsLength = obsNumber;
		this.obsLength = obsLength;
	}
	
	public void run() {
		Observations obs = getObservation(fileName, obsNumber, obsLength);
		crossValidate(obs, 5, 3);
	}

	/**
	 * Reads into the text file and creates an instance of Observations
	 * containing the time series.
	 * <p>
	 * The file name must specify the type of file(e.g. name.txt)
	 * 
	 * @param filename
	 *            The name of the text file
	 * @param obsNumber
	 *            The number of observations
	 * @param obsLength
	 *            The length of time series
	 * @return An instance of Observations containing the time series
	 * 
	 */
	public Observations getObservation(String filename, int obsNumber, int obsLength) {

		int[][] result = new int[obsNumber][obsLength];

		try {
			File file = new File(filename);
			Scanner scan = new Scanner(file);

			while (scan.hasNext()) {

				for (int i = 0; i < result.length; i++) {
					for (int j = 0; j < result[0].length; j++) {
						String line = scan.next();
						int temp = Integer.parseInt(line);

						result[i][j] = temp;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Observations observations = new Observations();

		for (int i = 0; i < obsNumber; i++) {
			int[] obv = new int[obsLength];

			for (int j = 0; j < obsLength; j++) {
				obv[j] = result[i][j];
			}

			Observation observation = new Observation(obv);
			observations.add(observation);
		}

		return observations;
	}

	/**
	 * Reads into an instance of Observations and performs a cross-validation by
	 * training on each of the data.
	 * <p>
	 * General Left-to-Right topology is used, since time series is being dealt
	 * 
	 * @param obs
	 *            The instance of Observations that will be cross-validated
	 * @param stateLength
	 *            Number of states. Larger number will result in longer training
	 *            time.
	 * @param delta
	 *            Number of types of observations. In this case, 3.
	 */

	public void crossValidate(Observations obs, int stateLength, int delta) {

		for (int i = 0; i < obs.getSize(); i++) {
			System.out.println("Training Observation #" + (i + 1));

			HMM timeSeriesHMM = new GLRHMM("WeatherData", stateLength, delta);
			Training trainGLRHMM = new TrainGLRHMM(
					(Observation) obs.getChild(i), timeSeriesHMM);
			Evaluation evalGLRHMM = new EvalGLRHMM(
					(Observation) obs.getChild(i), timeSeriesHMM);

			System.out.println();
			System.out.println("Initial Evaluation Value: "
					+ evalGLRHMM.forward());

			for (int j = 0; j < 5; j++) {
				trainGLRHMM.BaumWelch();
			}

			System.out.println("Final Evaluation Value: "
					+ evalGLRHMM.forward());

			System.out.println();
			System.out.println("<Cross Validation>");
			System.out.println();
			for (int j = 0; j < obs.getSize(); j++) {
				Evaluation crossVal = new EvalGLRHMM(
						(Observation) obs.getChild(j), timeSeriesHMM);
				System.out.println("#" + (j + 1) + " : " + crossVal.forward());
			}

			System.out.println();
		}
	}
}
