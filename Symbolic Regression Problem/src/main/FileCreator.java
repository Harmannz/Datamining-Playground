package main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class FileCreator {

	public static final char COMMA = ',';
	public static final String NEW_LINE = "\n";
	public static final int INSTANCES = 400;
	
	public static void generateFile(String fname){
		
		String filename = fname + ".csv";
		try (FileWriter fw = new FileWriter(filename, true);) {
			fw.append("X");
			fw.append(COMMA);
			fw.append("Y");
			fw.append(NEW_LINE);
			
			Random rand = new Random();
			int value;
			double result;
			for(int i = 0; i < INSTANCES; i++){
				value = rand.nextInt(40) - 20;
				fw.append("" + value);
				fw.append(COMMA);
				System.out.println("value:" + value);
				if(value > 0){
					result = evaluatePositive(value);
				} else {
					result = evaluateNegative(value);
				}
				fw.append(""+ result);
				fw.append(NEW_LINE);
			}
			
			System.out.println("File written to: " +filename);
		} catch (IOException e) {
			throw new RuntimeException("Error occurred while writing to file: " + filename);
		}
	}
	
	private static double evaluatePositive(int i){
		double value = (double) i;
		return (1/value) * Math.sin(value);
	}
	
	private static double evaluateNegative(int i){
		double value = (double) i;
		return 2*value + value * value + 3.0;
	}
	public static void main(String[] args){
		generateFile("training");
		generateFile("test");
	}
}
