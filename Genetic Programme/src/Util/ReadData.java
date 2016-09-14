package Util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;

public class ReadData {
	/**
	 * Method that takes in a filename and loads the data from the file and
	 * returns an array of array of the data
	 */
	public static void loadDataSet(boolean isTraining, final String filename) {
		int[][] data = new int[3][4];

		System.out.println("Reading data from file " + filename);
		try (Scanner din = new Scanner(new File(filename))) {
			int i = 0;
			while (din.hasNextLine()) {
				String line = din.nextLine();
				if(StringUtils.isBlank(line)){
					continue;
				}
				String[] lineArr = line.split(",");
				for(int j = 0; j < lineArr.length; j++){
					data[j][i] = Integer.parseInt(lineArr[j].trim());
				}
				i++;
			}
			for(i = 0; i < data.length; i++){
				for(int j = data[i].length - 1; j >=0; j--){
					System.out.printf("%d ",data[i][j]);
				}
				System.out.println("");
			}

		} catch (IOException e) {
			throw new RuntimeException("Data File caused IO exception");
		}
	}

	public static void main(String[] args) {
		loadDataSet(true, "training.txt");
	}
}