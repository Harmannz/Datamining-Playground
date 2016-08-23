package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_DELIMITER = "\n";

	public static List<String> readFile(final String output, final String filePath) throws IOException {
		FileInputStream fileInputStream;
		fileInputStream = new FileInputStream(filePath);
		// outputStream = new FileInputStream(output);
		Scanner scan = new Scanner(fileInputStream);
		// Scanner outputScan = new Scanner(outputStream);
		List<String> fileData = new ArrayList<String>();
//		List<String> outputData = new ArrayList<String>();
		int i = 0;
		while (scan.hasNextLine()) {
			fileData.add(scan.nextLine());
			i++;
		}
		
		System.out.println("output file");
		System.out.println("lines read: " + i);
		// if(outputScan.hasNextLine()){
		// outputScan.nextLine();
		// }
		// while (outputScan.hasNextLine()) {
		// System.out.println(outputScan.nextLine());
		// fileData.add(outputScan.nextLine());
		// }
		//
		scan.close();
		// outputScan.close();
		// outputStream.close();
		fileInputStream.close();

		// writeToOutput(output, outputData, fileData);
		return fileData;
	}

	private static void writeFileHeader(String filename) {
		FileWriter pw = null;
		try {
			pw = new FileWriter(filename, true);
			for (int i = 1; i <= 649; i++) {
				pw.append("attribute" + i);
				pw.append(COMMA_DELIMITER);
			}
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

	public static void main(String[] args) {
		File output = new File("output.txt");
		File mfeatfac = new File("data/mfeat-digits/mfeat-fac");
		File mfeatfou = new File("data/mfeat-digits/mfeat-fou");
		File mfeatkar = new File("data/mfeat-digits/mfeat-kar");
		File mfeatmor = new File("data/mfeat-digits/mfeat-mor");
		File mfeatpix = new File("data/mfeat-digits/mfeat-pix");
		File mfeatzer = new File("data/mfeat-digits/mfeat-zer");

		// writeFileHeader("output.txt");
		try {
			List<String> mfeatfacData = readFile(output.getAbsolutePath(), mfeatfac.getAbsolutePath());
			List<String> mfeatfouData = readFile(output.getAbsolutePath(), mfeatfou.getAbsolutePath());
			List<String> mfeatkarData = readFile(output.getAbsolutePath(), mfeatkar.getAbsolutePath());
			List<String> mfeatmorData = readFile(output.getAbsolutePath(), mfeatmor.getAbsolutePath());
			List<String> mfeatpixData = readFile(output.getAbsolutePath(), mfeatpix.getAbsolutePath());
			List<String> mfeatzerData = readFile(output.getAbsolutePath(), mfeatzer.getAbsolutePath());

			writeToOutput(mfeatfacData, mfeatfouData, mfeatkarData, mfeatmorData, mfeatpixData, mfeatzerData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Successfully completed reading files");
	}

	private static void writeToOutput(List<String> mfeatfacData, List<String> mfeatfouData, List<String> mfeatkarData,
			List<String> mfeatmorData, List<String> mfeatpixData, List<String> mfeatzerData) throws IOException {
		
		FileWriter fw = null;
		try {
			fw = new FileWriter("outc.csv", true);
			for (int i = 1; i <= 6; i++) {
				fw.append("attribute:" + i);
				fw.append(COMMA_DELIMITER);
			}
			fw.append("Class");
			fw.append(NEW_LINE_DELIMITER);
			
			
			for(int i = 0; i < mfeatfacData.size(); i++){
//				String[] nums = mfeatfacData.get(i).split(" ");
//			    for(int j = 0; j < nums.length; j++){
//					if (nums[j].trim().length() > 0) {
//						fw.append(String.valueOf(Double.parseDouble(nums[j].trim())));
//						fw.append(COMMA_DELIMITER);
//					}
//				}
//			    
//			    String[] nums2 = mfeatfouData.get(i).split(" ");
//			    for(int j = 0; j < nums2.length; j++){
//					if (nums2[j].trim().length() > 0) {
//						fw.append(String.valueOf(Double.parseDouble(nums2[j].trim())));
//						fw.append(COMMA_DELIMITER);
//					}
//				}
//			    String[] nums3 = mfeatkarData.get(i).split(" ");
//			    for(int j = 0; j < nums3.length; j++){
//					if (nums3[j].trim().length() > 0) {
//						fw.append(String.valueOf(Double.parseDouble(nums3[j].trim())));
//						fw.append(COMMA_DELIMITER);
//					}
//				}
			    String[] nums4 = mfeatmorData.get(i).split(" ");
			    for(int j = 0; j < nums4.length; j++){
					if (nums4[j].trim().length() > 0) {
						fw.append(String.valueOf(Double.parseDouble(nums4[j].trim())));
						fw.append(COMMA_DELIMITER);
					}
				}
//			    String[] nums5 = mfeatpixData.get(i).split(" ");
//			    for(int j = 0; j < nums5.length; j++){
//					if (nums5[j].trim().length() > 0) {
//						fw.append(String.valueOf(Double.parseDouble(nums5[j].trim())));
//						fw.append(COMMA_DELIMITER);
//					}
//				}
//			    String[] nums6 = mfeatzerData.get(i).split(" ");
//			    for(int j = 0; j < nums6.length; j++){
//					if (nums6[j].trim().length() > 0) {
//						fw.append(String.valueOf(Double.parseDouble(nums6[j].trim())));
//						fw.append(COMMA_DELIMITER);
//					}
//				}
			    fw.append("Class" + (i/200) % 200);
			    fw.append(NEW_LINE_DELIMITER);
			    
			}
			
			System.out.println(mfeatfacData.size());
		} catch (IOException e) {
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}