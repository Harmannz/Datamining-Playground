package featureextraction;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import dao.FeatureVector;
import dao.FeatureVector.Feature;

public class Main {
	
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_DELIMITER = "\n";
	
	public static void readFiles(final File folder, boolean isFace, boolean isTest) throws IOException {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				readFiles(fileEntry, isFace, isTest);
			} else {
				readFile(fileEntry.getAbsolutePath(), isFace, isTest);
			}
		}
	}

	public static void readFile(final String filePath, boolean isFace, boolean isTest) throws IOException {
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
		FeatureVector featureVector = extractFeatures(data2D);
		//return feature vector 
		//write feature vector to file
		writeFeatureVectorToFile(featureVector, isFace, isTest);
		scan.close();
		dis.close();
		fileInputStream.close();
	}

	private static void writeFeatureVectorToFile(FeatureVector featureVector, boolean isFace, boolean isTest) throws IOException{
		String filename = "train.csv";
		FileWriter pw = null; 
		try {
			if(isTest){
				filename = "test.csv";
			}
			pw = new FileWriter(filename,true);
			Map<Feature, Double> features = featureVector.getFeatures();
			pw.append(String.valueOf(features.get(Feature.RightEyeMean)));
			pw.append(COMMA_DELIMITER);
			pw.append(String.valueOf(features.get(Feature.RightEyeSD)));
			pw.append(COMMA_DELIMITER);
			pw.append(String.valueOf(features.get(Feature.LeftEyeMean)));
			pw.append(COMMA_DELIMITER);
			pw.append(String.valueOf(features.get(Feature.LeftEyeSD)));
			pw.append(COMMA_DELIMITER);
			pw.append(String.valueOf(features.get(Feature.NoseMean)));
			pw.append(COMMA_DELIMITER);
			pw.append(String.valueOf(features.get(Feature.NoseSD)));
			pw.append(COMMA_DELIMITER);
			pw.append(String.valueOf(features.get(Feature.LipsMean)));
			pw.append(COMMA_DELIMITER);
			pw.append(String.valueOf(features.get(Feature.LipsSD)));
			pw.append(COMMA_DELIMITER);
			if (isFace){
				pw.append("true");
			} else {
				pw.append("false");
			}
			pw.append(NEW_LINE_DELIMITER);
			
		} finally {
			if (pw != null) {
					pw.close();
			}
		}
	}
	
	private static void writeFileHeader(String filename) {
		FileWriter pw = null;
		try {
			pw = new FileWriter(filename, true);
			pw.append(String.valueOf(Feature.RightEyeMean));
			pw.append(COMMA_DELIMITER);
			pw.append(String.valueOf(Feature.RightEyeSD));
			pw.append(COMMA_DELIMITER);
			pw.append(String.valueOf(Feature.LeftEyeMean));
			pw.append(COMMA_DELIMITER);
			pw.append(String.valueOf(Feature.LeftEyeSD));
			pw.append(COMMA_DELIMITER);
			pw.append(String.valueOf(Feature.NoseMean));
			pw.append(COMMA_DELIMITER);
			pw.append(String.valueOf(Feature.NoseSD));
			pw.append(COMMA_DELIMITER);
			pw.append(String.valueOf(Feature.LipsMean));
			pw.append(COMMA_DELIMITER);
			pw.append(String.valueOf(Feature.LipsSD));
			pw.append(COMMA_DELIMITER);
			pw.append("Class");
			pw.append(NEW_LINE_DELIMITER);
		} catch (IOException e) {
			System.out.println("Error occurred when writing file header for: " + filename);
		} finally {
			if (pw != null) {
				try {
					pw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static FeatureVector extractFeatures(int[][] data){
		List<Integer> leftEye = new ArrayList<Integer>();
		List<Integer> rightEye = new ArrayList<Integer>();
		List<Integer> nose = new ArrayList<Integer>();
		List<Integer> lips = new ArrayList<Integer>();
		
		double noseSum = 0.0, rightEyeSum = 0.0, leftEyeSum = 0.0, lipsSum = 0.0;

		for(int row = 0; row < data.length/4; row++){
			for(int col = 0; col < data[row].length/2; col++){
				leftEye.add(data[row][col]);
				leftEyeSum += data[row][col];
			}
			for(int col = data[row].length/2; col < data[row].length; col++){
				rightEye.add(data[row][col]);
				rightEyeSum += data[row][col];
			}
		}

		for(int row = data.length/3; row < data.length*2/3; row++){
			for(int col = data[row].length/4; col < data[row].length*3/4; col++){
				nose.add(data[row][col]);
				noseSum += data[row][col];
			}
		}
		for(int row = data.length*2/3; row < data.length; row++){
			for(int col = 0; col < data[row].length; col++){
				lips.add(data[row][col]);
				lipsSum += data[row][col]; 
			}
		}

		Map<Feature, Double> featureVectors = new HashMap<Feature, Double>();
		
		featureVectors.put(Feature.NoseMean, noseSum/(1.0 * nose.size()));
		featureVectors.put(Feature.NoseSD, Math.sqrt(var(nose)));
		featureVectors.put(Feature.LipsMean,  lipsSum/(1.0 * lips.size()));
		featureVectors.put(Feature.LipsSD, Math.sqrt(var(lips)));
		featureVectors.put(Feature.LeftEyeMean, leftEyeSum/(1.0 * leftEye.size()));
		featureVectors.put(Feature.LeftEyeSD, Math.sqrt(var(leftEye)));
		featureVectors.put(Feature.RightEyeMean, rightEyeSum/(1.0 * rightEye.size()));
		featureVectors.put(Feature.RightEyeSD, Math.sqrt(var(rightEye)));
		
		return new FeatureVector(featureVectors);
	}
	
    /**
     * Calculate the mean.
     */
    public static double mean(List<Integer> values) {
		int sum = 0;
		for (int i = 0; i < values.size(); i++) {
			sum += values.get(i);
		}
        return 1.0 * sum / values.size();
    }
    
    /**
     * Calculate variance
     */
    public static double var(List<Integer> values) {
        double avg = mean(values);
        double sum = 0.0;
        for (int i = 0; i < values.size(); i++) {
            sum += Math.pow(values.get(i) - avg, 2);
        }
        return sum / (values.size() - 1);
    }


	public static void main(String[] args) {
		File trainingFolderFace = new File("data/train/face");
		File trainingFolderNonFace = new File("data/train/non-face");
		File testFolderNonFace = new File("data/test/non-face");
		File testFolderFace = new File("data/test/face");
		
		try {
			writeFileHeader("test.csv");
			writeFileHeader("train.csv");
			readFiles(trainingFolderFace, true, false);
			readFiles(trainingFolderNonFace, false, false);
			readFiles(testFolderFace, true, true);
			readFiles(testFolderNonFace, false, true);
			System.out.println("Successfully completed reading files");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
