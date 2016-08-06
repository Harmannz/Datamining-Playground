package main;

import java.awt.Color;

import util.Luminance;
import util.Picture;


public class test {

	public static void main(String[] args) {
        double threshold = 254/2;
        
        String[] IO_CONSTANTS = {"hubble.tif", "output_hubble.png"};
    	// read args
        for(int i = 0; i < args.length && i < IO_CONSTANTS.length; i++){
            IO_CONSTANTS[i] = args[i];
        }
        if (args.length == 1){
            IO_CONSTANTS[1] = "output_" + IO_CONSTANTS[0] + ".png";
        }
        if (System.getProperty("threshold") != null && !System.getProperty("threshold").isEmpty()){
        	threshold = Double.parseDouble(System.getProperty("threshold"));
        }
        if (System.getProperty("threshold") != null && !System.getProperty("threshold").isEmpty()){
        	threshold = Double.parseDouble(System.getProperty("threshold"));
        	System.out.println("Setting threshold to " + threshold);
        } else {
        	System.out.println("Setting threshold to default: " + threshold);
        }
		System.out.println("Performing image thresholding with threshold value: " + threshold);
        Picture pic = new Picture(IO_CONSTANTS[0]);
        for (int i = 0; i < pic.width(); i++) {
            for (int j = 0; j < pic.height(); j++) {
                Color color = pic.get(i, j);
                double lum = Luminance.lum(color);
                if (lum >= threshold){
                	pic.set(i, j, Color.BLACK);
                } else {
                	pic.set(i, j, Color.WHITE);
                }
            }
        }
        System.out.println("Writing to file: " + IO_CONSTANTS[1]);
        pic.save(IO_CONSTANTS[1]);
        System.out.println("Completed operation.");
    }
	
}
