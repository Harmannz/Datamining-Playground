package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class DataUtils {
	public static final int CLASSLABELS = 9; //start from 0
	public static final int ATTRIBUTES = 49;
	public static final char COMMA = ',';
	public static final String NEW_LINE = "\n";
	
	public static void toCSV(final String filename) {
		String outputFilename = "csv_" + filename + ".csv";
		System.out.println("Reading file: " + filename);
		try (Scanner din = new Scanner(new File(filename)); FileWriter fw = new FileWriter(outputFilename, true);) {
			for(int i = 1; i <= ATTRIBUTES; i++) {
				fw.append("Attribute" + i);
				fw.append(COMMA);
			}
			fw.append("Class");
			fw.append(NEW_LINE);
			while (din.hasNextLine()) {
				// if line contains any of the three class labels
				// then replace it with its replacement binary digits
				//
				Scanner line = new Scanner(din.nextLine());
				while (line.hasNextInt()) {
					Integer value = line.nextInt();
					fw.append(value.toString());
					if (line.hasNextInt()) {
						fw.append(COMMA);
					}
				}
				line.close();
				fw.append(NEW_LINE);

			}
			System.out.println("File converted to cvs: " + outputFilename);
		} catch (IOException e) {
			throw new RuntimeException("Data File caused IO exception");
		}
	}
	
	public static void toBPNNFormat(final String filename) {
		String outputFilename = "bpnn_" + filename + ".pat";
		System.out.println("Reading file: " + filename);
		try (Scanner din = new Scanner(new File(filename)); FileWriter fw = new FileWriter(outputFilename, true);) {
			while (din.hasNextLine()) {
				// if line contains any of the three class labels
				// then replace it with its replacement binary digits
				//
				Scanner line = new Scanner(din.nextLine());
				while (line.hasNextInt()) {
					Integer value = line.nextInt();
					if (line.hasNextInt()) {
						fw.append(value.toString());
						fw.append(" ");
					} else{
						// read integer 
						// convert to neural network output
						// if 10 then class output = 0 0 0 0 1 0 0 0 0 0 = 6th class label
						
						for(int i = CLASSLABELS; i > value; i--){
							fw.append("0 ");
						}
						fw.append("1");
						for(int i = value; i > 0; i--){
							fw.append(" 0");
						}
					}
				}
				line.close();
				fw.append("\n");

			}
			System.out.println("File converted to bpnn formmat: " + outputFilename);
		} catch (IOException e) {
			throw new RuntimeException("Data File caused IO exception");
		}
	}
	
	public static void main(String[] args) {
		String[] filenames = {"digits00", "digits05", "digits10", "digits15", "digits20", "digits30", "digits40", "digits50", "digits60"};
		
		Arrays.stream(filenames).forEach(fname -> toCSV(fname));
	}
}
