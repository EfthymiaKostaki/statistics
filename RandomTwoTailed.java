package quantitveMethods;

import java.util.ArrayList;
import java.util.Random;

public class RandomTwoTailed {
	ArrayList<Double> population1;
	ArrayList<Double> population2;
	int notRejectH0 = 0;

	public RandomTwoTailed() {
		createRandomPopulations();
		test();
	}

	private void test() {
		for (int i =0; i< 10000; i++) {
			ArrayList<Double> sample1 = new ArrayList<Double>();
			sample1 = selectRandomSample(population1);
			ArrayList<Double> sample2 = new ArrayList<Double>();
			sample2 = selectRandomSample(population2);
			UserInterface ui = new UserInterface();
			//we need to handle the IOecxeption here
			ui.requestDataForExercise2();
			Double significance = ui.getSignificance();
			Double meanDifference = ui.getMeanDifference();
			int[] sampleSize = new int[2];
			sampleSize[0] = 10;
			sampleSize[1] = 10;
			TwoTailed tt = new TwoTailed(significance, meanDifference, sample1, sample2, sampleSize);
			 if (tt.outcome = true) {
				 notRejectH0 += 1;
			 }
		}
	}

	private ArrayList<Double> selectRandomSample(ArrayList<Double> population) {
		ArrayList<Double> sample = new ArrayList<Double>();
		Random rand = new Random(); 
		
		for (int i =0; i< 10; i++) {
			int index = rand.nextInt(population.size());
			sample.add(population.get(index));
		}
		
		return sample;
	}

	private void createRandomPopulations() {
		//Population A
		java.util.Random r1 = new java.util.Random (1);
		for(int i=0; i<1000; i++)
		{
		 double stdNorm = r1. nextGaussian();
		 double popValue = 10 * stdNorm + 80;
		 population1.add(popValue);
		}
		
		java.util.Random r2 = new java.util.Random (2);
		//Population B
		for(int i=0; i<1000; i++)
		{
		 double stdNorm = r2. nextGaussian();
		 double popValue = 10 * stdNorm + 85;
		 population2.add(popValue);
		}

	}
}
