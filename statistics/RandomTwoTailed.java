package statistics;

import java.util.ArrayList;
import java.util.Random;

public class RandomTwoTailed {
	double significance, theta;
	ArrayList<Double> population1 = new ArrayList<Double>();
	ArrayList<Double> population2 = new ArrayList<Double>();
	double[] sample1, sample2 = new double[10];
	public int notRejectH0 = 0;
	public int rejectH0 = 0;

	public RandomTwoTailed(double significance, double theta) {
		this.significance = significance;
		this.theta = theta;
		createRandomPopulations();
		test();
	}

	private void test() {
		for (int i = 0; i < 10000; i++) {
			sample1 = selectRandomSample(population1);
			sample2 = selectRandomSample(population2);
			TwoTailed twoTailed =
				new TwoTailed(significance, theta, sample1, sample2);
			double pValue = twoTailed.testCases(sample1, sample2);
			if (twoTailed.printResults(pValue, significance)) {
				notRejectH0++;
			} else {
				rejectH0++;
			}
		}
		System.out.println("H0 has not been rejected " + notRejectH0
		                + " times");
		System.out.println("H0 has been rejected " + rejectH0 + " times");
	}

	private double[] selectRandomSample(ArrayList<Double> population) {
		double[] sample = new double[10];
		Random rand = new Random();

		for (int i = 0; i < 10; i++) {
			int index = rand.nextInt(population.size());
			sample[i] = population.get(index);
		}

		return sample;
	}

	private void createRandomPopulations() {
		// Population A
		Random r1 = new Random(1);
		for (int i = 0; i < 1000; i++) {
			double stdNorm = r1.nextGaussian();
			double popValue = 10 * stdNorm + 80;
			population1.add(popValue);
		}

		// Population B
		Random r2 = new Random(2);
		for (int i = 0; i < 1000; i++) {
			double stdNorm = r2.nextGaussian();
			double popValue = 10 * stdNorm + 85;
			population2.add(popValue);
		}

	}
}
