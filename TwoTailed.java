package quantitveMethods;

public class TwoTailed {
	

	double significance, meanDifference;
	String samplePathNo1, samplePathNo2;
	
	public TwoTailed(float significance, float meanDifference, 
			String samplePathNo1, String samplePathNo2) {
		this.significance = significance;
		this.meanDifference = meanDifference;
		this.samplePathNo1 = samplePathNo1;
		this.samplePathNo2 = samplePathNo2;
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

	public String getSamplePathNo1() {
		return samplePathNo1;
	}
	

	public void setSamplePathNo1(String samplePathNo1) {
		this.samplePathNo1 = samplePathNo1;
	}

	public String getSamplePathNo2() {
		return samplePathNo2;
	}

	public void setSamplePathNo2(String samplePathNo2) {
		this.samplePathNo2 = samplePathNo2;
	}
	
	
}
