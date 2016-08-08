package featureextraction;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {

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
		extractFeatures(data2D, isFace);
		dis.close();
		fileInputStream.close();
	}

	private static void extractFeatures(int[][] data, boolean isFace){
		// write feature vector to file
		double leftEyeMean = 0.0, rightEyeMean = 0.0;
		double leftEyeMeanVal = 0.0, rightEyeMeanVal = 0.0;
		int count = 0;
		for(int row = 0; row < data.length/2; row++){
			for(int col = 0; col < data[row].length/2; col++){
				count++;
				leftEyeMean += data[row][col];
			}
			leftEyeMeanVal = leftEyeMean/count;
			count = 0;
			for(int col = data[row].length/2; col < data[row].length; col++){
				count++;
				rightEyeMean += data[row][col];
			}
			rightEyeMeanVal = rightEyeMean/count;
		}
		System.out.println("left eye mean: " + leftEyeMeanVal);
		System.out.println("right eye mean: " + rightEyeMeanVal);
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
