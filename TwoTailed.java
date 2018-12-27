package quantitveMethods;

import java.util.ArrayList;
//import org.apache.commons.math3.distribution;

public class TwoTailed {
	

	private double significance, meanDifference;
	private ArrayList<Double> sample1, sample2;
	private double X1, X2; 
	private int[] sampleSize = new int[2];
	private double var1, var2;
	public boolean outcome;
	
	public TwoTailed(double significance, double meanDifference, 
			ArrayList<Double> sample1, ArrayList<Double> sample2, int[] sampleSize) {
		this.significance = significance;
		this.meanDifference = meanDifference;
		this.sample1 = sample1;
		this.sample2 = sample2;
		this.sampleSize = sampleSize;
		hypothesisPrint();
		calculateMeans();
		calculateVariances();
	}
	
	private void hypothesisPrint() {
		System.out.println("We are going to test for the following:");
		System.out.println("H0: μ1 - μ2 = " + meanDifference);
		System.out.println("H1: μ1 - μ2 ≠" + meanDifference);
	}


	public void calculateMeans() {
		double sum = 0;
		
		for (Double value: sample1) {
		    sum += value;
		}
		
		X1 = sum / sampleSize[0];
		sum = 0;
		
		for (Double value: sample2) {
		    sum += value;
		}
		
		X2 = sum / sampleSize[1];
	}
	
	public void calculateVariances() {
		double sum = 0;
		
		for (Double value: sample1) {
			sum += (value - X1) * (value - X1) ;
		}
		
		var1 = sum / sampleSize[0];
		sum = 0;
		
		for (Double value: sample2) {
			sum += (value - X2) * (value - X2) ;
		}
		
		var2 = sum / sampleSize[1];
	}
	
	public void testCases() {
		if (sampleSize[0] > 30 && sampleSize[1] > 30) {
			Zdistribution();
		} else {
			TStudentDistribution();
		}
	}
	
	private void TStudentDistribution() {
		double sp, numerator, denominator;
		numerator = (sampleSize[0] -1) * (var1 * var1) + (sampleSize[1] -1) * (var2 * var2);
		denominator = (sampleSize[0] -1) + (sampleSize[1] -1);
		sp = numerator / denominator;
		double sX1X2 = Math.sqrt(Math.pow(sp, 2.0) * (1 / sampleSize[0] + 1 / sampleSize[1]));
		double t = 0; 
		
		if (sX1X2 != 0) {
			t = ((X1 - X2) - meanDifference) / sX1X2;
		} else { 
			t = 99999;
		}
		int n = sampleSize[0] + sampleSize[1] - 2; 
		//use imported distribution to calculate tn
		double tn = 0;//we give its value here
		if (Math.abs(tn) > Math.abs(t)) {
			outcome = true;
			results(true);
		} else {
			outcome = false;
			results(false);
		}
	}


	private  void Zdistribution() {
		double twoSampleVariance;
		twoSampleVariance = Math.sqrt((var1 * var1) / sampleSize[0] + (var2 * var2) / sampleSize[1]);
		double z = 0;
		if (twoSampleVariance != 0)
			z = ((X1 - X2) - meanDifference) / twoSampleVariance;
		else
			z = 99999;
		//use imported distribution to calculate zn
		double zn = 0;//we give its value here
		if (Math.abs(zn) > Math.abs(z)) {
			outcome = true;
			results(true);
		} else {
			outcome = false;
			results(false);
		}
	}
	
	private void results(boolean outcome) {
		if (outcome == true) {
			System.out.println("We do not reject the hypothesis H0");
		} else {
			System.out.println("We reject the hypothesis H0 and we accept the Hypothesis H1");
		}
	}


	public double getSignificance() {
		return significance;
	}
	
	public void setSignificance(float significance) {
		this.significance = significance;
	}
	
	public double getMeanDifference() {
		return meanDifference;
	}

	public void setMeanDifference(float meanDifference) {
		this.meanDifference = meanDifference;
	}

	public ArrayList<Double> getSample1() {
		return sample1;
	}
	

	public void setSamplePathNo1(ArrayList<Double> sample1) {
		this.sample1 = sample1;
	}

	public ArrayList<Double> getSample2() {
		return sample2;
	}

	public void setSamplePathNo2(ArrayList<Double> sample2) {
		this.sample2 = sample2;
	}
	
	
}
