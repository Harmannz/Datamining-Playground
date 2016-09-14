package DAO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DataSet {
	private String filename;
	private final String[] attributeNames = { "x-value", "y-value", "class-label"};
	private List<Instance> instances = new ArrayList<>();
	public DataSet(final String filename){
		this.filename = filename;
	}
	/**
	 * Method that takes in a filename and loads the data from the file and
	 * returns an array of array of the data
	 */
	public List<Instance> loadDataSet() {
		try (Scanner din = new Scanner(new File(this.filename))) {
			
			while (din.hasNextLine()) {
				//if line contains any of the three class labels
				//then replace it with its replacement binary digits
				//
				String line = din.nextLine();
				addInstance(line.split(",")); 
				
			}

			for(Instance instance : instances){
				System.out.println(instance);
			}
			
		} catch (IOException e) {
			throw new RuntimeException("Data File caused IO exception");
		}
		return instances;
	}

	private void addInstance(String[] data){
		/*
		 * create hashmap of line.nextInt
		 * create new instance based on the hashmap
		 * add instance to the list
		 */
		Map<String, Integer> instanceData = new HashMap<String, Integer>();
		for(int i = 0; i < attributeNames.length; i++){
			instanceData.put(attributeNames[i], Integer.parseInt(data[i].trim()));
		}
		
		this.instances.add(new Instance(instanceData));
	}
	
	public static void main(String[] args) {
	
		new DataSet("training.txt").loadDataSet();
	}
}