package quantitveMethods;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.TDistribution;

public class TwoTailed {
	
	private double significance, theta , meanDifference, stdDifference, var1 , var2;
	private double[] sample1, sample2;
	
	public TwoTailed(double significance, double theta, 
			double[] sample1, double[] sample2) {
		this.significance = significance;
		this.theta = theta;
		this.sample1 = sample1;
		this.sample2 = sample2;
		calculations();
	}
	
	public void calculations() {
		hypothesisPrint();
		meanDifference = findMeanDifferenceOfTwoSamples(sample1, sample2);
		stdDifference = calculateStdDifference(sample1, sample2); //S_(x1-x2)
		testCases(sample1, sample2);
	}
	

	private void hypothesisPrint() {
		System.out.println("We are going to test for the following:");
		System.out.println("H0: μ1 - μ2 = " + theta);
		System.out.println("H1: μ1 - μ2 ≠ " + theta);
	}
	
	public double findMeanDifferenceOfTwoSamples(double[] sample1, double[] sample2) {
		return StatUtils.mean(sample1) - StatUtils.mean(sample2);
	}
	
	public double calculateStdDifference(double[] sample1, double[] sample2) {
		return Math.sqrt((StatUtils.variance(sample1)/sample1.length) +
				(StatUtils.variance(sample2)/sample2.length));
	}
	
	public void testCases(double[] sample1, double[] sample2) {
		if (sample1.length > 30 && sample2.length > 30) {
			zDistribution(meanDifference, stdDifference, theta, significance);
		} else {
			tStudentDistribution(sample1, sample2);
		}
	}
	
	private void zDistribution(double meanDifference, double stdDifference,
			double theta, double significance) {
		double Z = (meanDifference - theta) / stdDifference;
		NormalDistribution normalDist = new NormalDistribution();
		double upper_limit = normalDist.inverseCumulativeProbability(1 - significance / 2);
		printResults(Z, upper_limit);
	}
	
	private void tStudentDistribution(double[] sample1, double[] sample2) { //assuming equal variances
		double sp, numerator, denominator;
		numerator = (sample1.length -1) * (Math.pow(StatUtils.variance(sample1), 2)) + 
				(sample2.length -1) * (Math.pow(StatUtils.variance(sample2), 2));
		denominator = sample1.length + sample2.length - 2;
		sp = numerator / denominator;
		double sX1X2 = Math.sqrt(Math.pow(sp, 2.0) * (1 / sample1.length + 1 / sample2.length));
		double tn = ((meanDifference) - theta) / sX1X2;
		int pdf = sample1.length + sample2.length - 2; 
		TDistribution tDist = new TDistribution(pdf);
		double upper_limit = tDist.inverseCumulativeProbability(1 - significance / 2);
		printResults(tn, upper_limit);
	}
	
	private void printResults (double variable, double upper_limit) {
		if (Math.abs(variable) < upper_limit) {
			System.out.println("We cannot reject H0.");
		} else {
			System.out.println("H0 is rejected and H1 is accepted.");
		}
	}


	public double getSignificance() {
		return significance;
	}
	
	public void setSignificance(float significance) {
		this.significance = significance;
	}
	
	public double getMeanDifference() {
		return theta;
	}

	public void setMeanDifference(float meanDifference) {
		this.theta = meanDifference;
	}


	public double[] getSample1() {
		return sample1;
	}


	public void setSample1(double[] sample1) {
		this.sample1 = sample1;
	}


	public double[] getSample2() {
		return sample2;
	}


	public void setSample2(double[] sample2) {
		this.sample2 = sample2;
	}
	
	
	
}
