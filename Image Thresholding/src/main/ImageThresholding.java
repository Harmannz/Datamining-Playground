package main;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageThresholding {
	private static double threshold = 0;
	
    private static BufferedImage performImageThresholding(final BufferedImage image){
        BufferedImage writeImg=new BufferedImage(image.getWidth(),image.getHeight(),TYPE_INT_RGB);
    	byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		
        int w = image.getWidth();
        int h = image.getHeight();
        byte data[] = pixels;
        int lowest = 255;
        int heighest = -255;
        for (int y=0; y<h; y++)
        {
            for (int x=0; x<w; x++)
            {
                int arrayIndex = x + (y * w);
                int colorIndex = data[arrayIndex];
                if (colorIndex < lowest){
                	lowest = colorIndex;
                }
                if (colorIndex > heighest){
                	heighest = colorIndex;
                }
                System.out.println(colorIndex);
                if (colorIndex <= threshold){
                	writeImg.setRGB(x, y, new Color(255, 255, 255).getRGB());
                } else {
                	writeImg.setRGB(x, y, new Color(0, 0, 0).getRGB());
                }
            }
        }
        System.out.println("Heighest: " + heighest);
        System.out.println("Lowest: " + lowest);
        System.out.println("Difference: " + (heighest - lowest));
        return writeImg;
    }

    public static void main(String[] args) {
    	String[] IO_CONSTANTS = {"hubble.tif", "output_hubble.tif", "tiff"};
    	// read args
        for(int i = 0; i < args.length; i++){
            IO_CONSTANTS[i] = args[i];
        }
        if (args.length == 1){
            IO_CONSTANTS[1] = "output_" + IO_CONSTANTS[0];
        }
        String inputFilename = IO_CONSTANTS[0];
        String outputFilename = IO_CONSTANTS[1];
        String outputFiletype = IO_CONSTANTS[2];
        if (System.getProperty("threshold") != null && !System.getProperty("threshold").isEmpty()){
        	threshold = Double.parseDouble(System.getProperty("threshold"));
        }
        System.out.println("Setting threshold to " + threshold);
      
    	try {
    	BufferedImage image = ImageIO.read(new File(inputFilename));
		System.out.println("Successfully read from image: " + inputFilename);
		System.out.println("Performing image thresholding with threshold value: " + threshold);
		BufferedImage result = performImageThresholding(image);
		System.out.println("Writing to file: " + outputFilename);
		File outputfile = new File(outputFilename);
		ImageIO.write(result, outputFiletype, outputfile);
		System.out.println("Completed operation.");
    	} catch (IOException ex) {System.err.println("Some error occurred. Please try again");}
    }
}



