package statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
	private double significance, theta;
	private int sampleCounter, sampleSize;
	double[] sample1, sample2;
	Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		UserInterface caller = new UserInterface();
		System.out.println("You need to choose one of the following: ");
		System.out.println("1) Perform a two tailed test "
						+ "based on two samples inserted (.txt files).");

		System.out.println("2) Perform a two tailed test 10 thousand times"
						+ "based on randomly created populations.");
		System.out.println();
		System.out.println("Now, please make your choice (either 1 or 2):");
		Scanner input = new Scanner(System.in);
		int choice = input.nextInt();
		if (choice == 1) {
			caller.requestDataForExercise1();
			caller.doExercise1();
		} else if (choice == 2) {
			caller.requestDataForExercise2();
			caller.doExercise2();
		}
		input.close();
	}

	public void requestDataForExercise1() {
		try {
			significance = insertSignificance();
			theta = insertMeanDifference();
			sample1 = insertSamplePath();
			sample2 = insertSamplePath();

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void doExercise1() {
		TwoTailed twoTailed =
			new TwoTailed(significance, theta, sample1, sample2);
		twoTailed.hypothesisPrint();
		double pValue = twoTailed.testCases(sample1, sample2);
		if (pValue > significance) {
			System.out.println("pValue " + Math.round(pValue)
							+ " is greater than the inserted "
							+ "significance\n" );
			System.out.println("H0 cannot be rejected!");
		} else {
			System.out.println("pValue " + Math.round(pValue)
							+ " is less than the inserted "
							+ "significance\n");
			System.out.println("H0 is rejected and therefore, H1 is accepted!");
		}
	}

	public void requestDataForExercise2() {
		significance = insertSignificance();
		theta = insertMeanDifference();
	}

	public void doExercise2() {
		new RandomTwoTailed(significance, theta);
	}


	public double insertSignificance() {
		double significance;
		System.out.println("Please insert significance (with a comma instead of dot):");
		for (;;) {
			significance = input.nextDouble();
			if (significance > 0 && significance < 1) {
				break;
			} else {
				System.out.println("Significance needs to be greater than 0 and less than 1");
				System.out.println("Please insert significance again.");
			}
		}
		return significance;
	}

	public double insertMeanDifference() {
		double theta;
		System.out.println("Please insert the difference of means");
		theta = input.nextDouble();
		System.out.println();
		return theta;
	}

	public double[] insertSamplePath() throws IOException {
		ArrayList<Double> initialSample = new ArrayList<Double>();
		double[] sample;
		String samplePath, line;
		int linesRead = 0;
		System.out.println("Please insert the path of the file where sample " + (++sampleCounter) +
						" exists");
		for(;;) {
			samplePath = input.next();
			BufferedReader reader =
							new BufferedReader(new InputStreamReader(
											new FileInputStream(samplePath), "windows-1253"));

			if (new File(samplePath).exists()) {
				while((line = reader.readLine()) != null) {
					if (linesRead == 0) {
						sampleSize = Integer.parseInt(line);
					} else {
						initialSample.add(Double.parseDouble(line));
					}
					linesRead++;
				}
				if (initialSample.size() == sampleSize) {
					sample = new double[initialSample.size()];
					for (int i = 0; i < initialSample.size(); i++) {
						sample[i] = initialSample.get(i);
					}
					break;
				}
			} else {
				System.out.println("This path is invalid. Please insert a valid sample path");
			}
			reader.close();
		}
		return sample;
	}

	public double getSignificance() {
		return significance;
	}

	public void setSignificance(double significance) {
		this.significance = significance;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

}
