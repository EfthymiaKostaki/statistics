package quantitveMethods;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class UserInterface {
	double significance, meanDifference;
	int[] sampleSize = new int[2];
	int i = 0;
	ArrayList<Double> sample1 , sample2;
	Scanner input = new Scanner(System.in);
	
	public void requestDataForExercise1() throws IOException {
		System.out.println("Welcome to the world of two-tailed test");
		significance = insertSignificance();
		meanDifference = insertMeanDifference();
		sample1 = insertSamplePath();
		sample2 = insertSamplePath();
		System.out.println(sample1);
	}
	
	public void requestDataForExercise2() throws IOException {
		System.out.println("Welcome to the world of two-tailed test");
		significance = insertSignificance();
		meanDifference = insertMeanDifference();
	}
	

	public double insertSignificance() {
		double significance;
		System.out.println("Please insert significance: (With a comma not a dot)");
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
		double meanDifference;
		System.out.println("Please insert the difference of means");
		meanDifference = input.nextDouble();
		System.out.println();
		return meanDifference;
	}
	
	public ArrayList<Double> insertSamplePath() throws IOException {
		ArrayList<Double> sample = new ArrayList<Double>();
		String samplePath, line;
		int linesRead = 0;
		System.out.println("Please insert the path of the file where the sample exists");
		for(;;) {
			samplePath = input.next();
			if (new File(samplePath).exists()) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(samplePath), "windows-1253"));
				while((line = reader.readLine()) != null) {
					linesRead++;
					if (linesRead == 1) {
						sampleSize[i] = Integer.parseInt(line); //it does not return :(
						i += 1;
					} else {
						sample.add(Double.parseDouble(line));
					}
				}
				reader.close();
				return sample;
			} else {
				System.out.println("This path is invalid. Please insert a valid sample path");
			}
		}
	
	}
	
	
	
	public double getSignificance() {
		return significance;
	}

	public double getMeanDifference() {
		return meanDifference;
	}


	public static void main(String[] args) throws IOException {
		UserInterface caller = new UserInterface();
		caller.requestDataForExercise1();
	}
}
