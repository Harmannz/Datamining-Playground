package featureextraction;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import dao.FeatureVector;
import dao.FeatureVector.Feature;

public class Main {
	
	private static final String FILE_HEADER = "rightEyeMean, leftEyeMean";
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_DELIMITER = "\n";

	public static void readFiles(final File folder, boolean isFace) throws IOException {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				readFiles(fileEntry, isFace);
			} else {
				readFile(fileEntry.getAbsolutePath(), isFace);
			}
		}
	}

	public static void readFile(final String filePath, boolean isFace) throws IOException {
		FileInputStream fileInputStream;
		fileInputStream = new FileInputStream(filePath);
		Scanner scan = new Scanner(fileInputStream);
		// Discard the magic number
		scan.nextLine();
		// Read pic width, height and max value
		int picWidth = scan.nextInt();
		int picHeight = scan.nextInt();
		int maxvalue = scan.nextInt();

		fileInputStream.close();

		// Now parse the file as binary data
		fileInputStream = new FileInputStream(filePath);
		DataInputStream dis = new DataInputStream(fileInputStream);

		// read the image data
		int[][] data2D = new int[picHeight][picWidth];
		for (int row = 0; row < picHeight; row++) {
			for (int col = 0; col < picWidth; col++) {
				data2D[row][col] = dis.readUnsignedByte();
			}
		}
		
		System.out.println("Extracting features from: " + filePath);
		FeatureVector featureVector = extractFeatures(data2D, isFace);
		//return feature vector 
		//write feature vector to file
		writeFeatureVectorToFile(featureVector);
		scan.close();
		dis.close();
		fileInputStream.close();
	}

	private static void writeFeatureVectorToFile(FeatureVector featureVector) throws IOException{
		FileWriter pw = null; 
		try {
			pw = new FileWriter("data.csv",true);
			Map<Feature, Double> features = featureVector.getFeatures();
			pw.append(String.valueOf(features.get(Feature.RightEyeMean)));
			pw.append(COMMA_DELIMITER);
			pw.append(String.valueOf(features.get(Feature.LeftEyeMean)));
			pw.append(NEW_LINE_DELIMITER);
			
		} finally {
			if (pw != null) {
					pw.close();
			}
		}
	}
	private static FeatureVector extractFeatures(int[][] data, boolean isFace){
		
		// write feature vector to file
		double leftEyeMean = 0.0, rightEyeMean = 0.0;
		double leftEyeMeanVal = 0.0, rightEyeMeanVal = 0.0;
		int leftCount = 0, rightCount = 0;
		for(int row = 0; row < data.length/2; row++){
			for(int col = 0; col < data[row].length/2; col++){
				leftCount++;
				leftEyeMean += data[row][col];
			}
			for(int col = data[row].length/2; col < data[row].length; col++){
				rightCount++;
				rightEyeMean += data[row][col];
			}
		}
		leftEyeMeanVal = leftEyeMean/leftCount;
		rightEyeMeanVal = rightEyeMean/rightCount;
		Map<Feature, Double> featureVectors = new HashMap<Feature, Double>();
		featureVectors.put(Feature.LeftEyeMean, leftEyeMeanVal);
		featureVectors.put(Feature.RightEyeMean, rightEyeMeanVal);
		return new FeatureVector(featureVectors, isFace);
	}

	public static void main(String[] args) {
		File trainingFolder = new File("data/train/face");
//		File testFolder = new File("data/test");
		try {
			readFiles(trainingFolder, true);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
