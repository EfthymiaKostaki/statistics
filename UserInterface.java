package quantitveMethods;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class UserInterface {
	private double significance, theta;
	private int sampleCounter, sampleSize;
	double[] sample1, sample2;
	Scanner input = new Scanner(System.in);
	
	public void requestDataForExercise1() throws IOException {
		System.out.println("Welcome to the world of two-tailed test");
		significance = insertSignificance();
		theta = insertMeanDifference();
		sample1 = insertSamplePath();
		sample2 = insertSamplePath();
		new TwoTailed(significance, theta, sample1, sample2);
	}
	
	public void requestDataForExercise2() {
		significance = insertSignificance();
		theta = insertMeanDifference();
		new RandomTwoTailed(significance, theta);
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

	public double insertSignificance() {
		double significance;
		System.out.println("Please insert significance: (with a comma)");
		for(;;) {
			significance = input.nextDouble();
			if (significance > 0 && significance < 1) {
				break;
			} else {
				System.out.println("Significance needs to be greater than 0 and less than 1");
				System.out.println("Please insert significance again:");
			}
		}
		System.out.println();
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
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(samplePath), "windows-1253"));
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
	
	public static void main(String[] args) throws IOException {
		UserInterface caller = new UserInterface();
		//caller.requestDataForExercise1();
		caller.requestDataForExercise2();
	}
}
