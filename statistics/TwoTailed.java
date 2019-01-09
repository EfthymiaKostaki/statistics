package statistics;

import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.stat.StatUtils;

public class TwoTailed {

	private double significance, theta , meanDifference, standardError, var1 , var2;
	private double[] sample1, sample2;
	private boolean outcome;

	public TwoTailed(double significance, double theta,
			double[] sample1, double[] sample2) {
		this.significance = significance;
		this.theta = theta;
		this.sample1 = sample1;
		this.sample2 = sample2;
		calculations();
	}

	public void calculations() {
		var1 = StatUtils.variance(sample1);
		var2 = StatUtils.variance(sample2);
		meanDifference = findMeanDifferenceOfTwoSamples(sample1, sample2);
		standardError = calculateStandardError(sample1, sample2);
	}


	public void hypothesisPrint() {
		System.out.println("We are going to test for the following:");
		System.out.println("H0: μ1 - μ2 = " + theta);
		System.out.println("H1: μ1 - μ2 ≠ " + theta);
		System.out.println("Significance level:" + significance);
		System.out.println();
	}

	public double findMeanDifferenceOfTwoSamples(double[] sample1, double[] sample2) {
		return StatUtils.mean(sample1) - StatUtils.mean(sample2);
	}

	public double calculateStandardError(double[] sample1, double[] sample2) {
		return Math.sqrt((var1/sample1.length) + (var2/sample2.length));
	}

	public double testCases(double[] sample1, double[] sample2) {
		boolean equalVariances = false;
		if (sample1.length > 30 && sample2.length > 30) {
			return zDistribution(meanDifference, standardError);
		} else
			System.out.println("We are going to conduct a F-test to see if population variances are equal");
			equalVariances = ftest();
			if (equalVariances) {
				return tStudentDistribution(sample1, sample2);
			} else {
				return tSudentDistributionNotEqualVar(sample1, sample2);
			}
		}

	private boolean ftest() {
		hypothesisFtest();
		double f =  var1 / var2;
		FDistribution fd = new FDistribution(sample1.length - 1, sample2.length -1);
		double upper_limit = fd.inverseCumulativeProbability(1 - significance / 2);
		if (f < upper_limit) {
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

	private double zDistribution(double meanDifference, double standardError) {

		double Z = (meanDifference - theta) / standardError;
		NormalDistribution normalDist = new NormalDistribution();
		double pValue = 2 * normalDist.cumulativeProbability(Z);
		return pValue;
	}


	private double tStudentDistribution(double[] sample1, double[] sample2) {
		//assuming equal variances
		double sp, numerator, denominator;

		numerator = ((sample1.length - 1) * var1) + ((sample2.length - 1) * var2);

		denominator = sample1.length + sample2.length - 2;
		sp = numerator / denominator; //common variance

		double sX1X2 = (sp / sample1.length) + (sp / sample2.length);
		sX1X2 = Math.sqrt(sX1X2);
		double tn = (meanDifference - theta) / sX1X2;
		int degreesOfFreedom = sample1.length + sample2.length - 2;
		TDistribution tDist = new TDistribution(degreesOfFreedom);
		double pValue = 2 * tDist.cumulativeProbability(tn);
		return pValue;
	}

	private double tSudentDistributionNotEqualVar(double[] sample1, double[] sample2) {
		double t = (meanDifference - theta ) / standardError;
		double numerator, denominator;
		numerator = Math.pow((var1 / sample1.length) + (var2 / sample2.length), 2);
		denominator = Math.pow(var1 / sample1.length, 2) / (sample1.length - 1) +
						Math.pow(var2 / sample2.length, 2) / (sample2.length - 1);
		double degreesOfFreedom = numerator / denominator;
		TDistribution tDist = new TDistribution(degreesOfFreedom);
		double pValue = 2 * tDist.cumulativeProbability(t);
		return pValue;
	}

	/**@param pValue
	 * @param significance
	 * @return true if H0 cannot be rejected. Otherwise (in case H0 is rejected)
	 * return false;
	 */
	protected boolean printResults (double pValue, double significance) {
		if (pValue > significance) {
			return true;
		} else {
			return false;
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

	public boolean getOutcome() {
		return outcome;
	}
}
