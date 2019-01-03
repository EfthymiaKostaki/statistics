package quantitveMethods;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.distribution.FDistribution;

public class TwoTailed {
	
	private double significance, theta , meanDifference, stdDifference, var1 , var2;
	private double[] sample1, sample2;
	private int outcome;
	
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
		var1 = StatUtils.variance(sample1); 
		var2 = StatUtils.variance(sample2);
		testCases(sample1, sample2);
	}
	

	private void hypothesisPrint() {
		System.out.println("We are going to test for the following:");
		System.out.println("H0: μ1 - μ2 = " + theta);
		System.out.println("H1: μ1 - μ2 ≠ " + theta);
		System.out.println("Significance level:" + significance);
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
		} else 
			System.out.println("We are going to conduct a F-test to see is population variances are equal");
			boolean equalVariances = ftest();
			if (equalVariances) {
				tStudentDistribution(sample1, sample2);
			} else {
				tSudentDistributionNotEqualVar(sample1, sample2);
			}
		}

	private boolean ftest() {
		hypothesisFtest();
		double f =  var1 / var2;
		FDistribution fd = new FDistribution(sample1.length - 1, sample2.length -1);
		double upper_limit =fd.inverseCumulativeProbability(1 - significance / 2);
		if (Math.abs(f) < Math.abs(upper_limit)) {
			return true;
		} else {
			return false;
		}
	}
	
	private void hypothesisFtest() {
		System.out.println("We are going to test for the following hypothesis:");
		System.out.println("H0: σ1²/σ2² = 1");
		System.out.println("H1: σ1²/σ2² ≠ 1");
		System.out.println("Significance level = " + significance);
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
		double tn = (meanDifference - theta) / sX1X2;
		int pdf = sample1.length + sample2.length - 2; 
		TDistribution tDist = new TDistribution(pdf);
		double upper_limit = tDist.inverseCumulativeProbability(1 - significance / 2);
		printResults(tn, upper_limit);
	}
	
	private void tSudentDistributionNotEqualVar(double[] sample12, double[] sample22) {
		double t = (meanDifference - theta ) / stdDifference;
		double degreesOfFreedom = Math.sqrt(var1 / sample1.length + var2 / sample2.length) / 
				(Math.sqrt(var1 / sample1.length) / (sample1.length - 1) 
						+ Math.sqrt(var2 / sample2.length) / (sample2.length - 1)) ;//square root
		TDistribution tDist = new TDistribution(degreesOfFreedom);
		double upper_limit = tDist.inverseCumulativeProbability(1 - significance / 2);
		printResults(t, upper_limit);
	}

	
	private int printResults (double variable, double upper_limit) {
		if (variable < upper_limit && variable > -upper_limit) {
			System.out.println("We cannot reject H0.");
			outcome = 0;
			return 0;
		} else {
			System.out.println("H0 is rejected and H1 is accepted.");
			outcome = 1;
			return 1;
		}
	}


	public double getSignificance() {
		return significance;
	}
	
	public void setSignificance(double significance) {
		this.significance = significance;
	}
	
	public double getMeanDifference() {
		return theta;
	}

	public void setMeanDifference(double meanDifference) {
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
	
	public int getOutcome() {
		return outcome;
	}
}
