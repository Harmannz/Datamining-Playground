package main;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import util.ImageUtils;

/**
 * Performs Mean Filter on image given as the first argument. 
 * If first argument is not provided then the example image is used.
 * 
 * The second argument is optional and is used to define the filename of the resulting image name.
 * If second argument is not provided then it is set by prepending output_ to input image name.
 * 
 * The third argument is optional and is used to define the type of the resulting image name.
 * If third argument is not provided then it is defaulted to Tiff format 
 * 
 *  
 * Created by Harman Singh on 30/07/16.
 */
public class MeanFilter {

	/**
	 * Performs the Mean Filter on the input image.
	 * 
	 * @param readImg
	 * @return
	 */
    private static BufferedImage performMeanFilter(final BufferedImage readImg){
    	BufferedImage writeImg=new BufferedImage(readImg.getWidth(),readImg.getHeight(),TYPE_INT_RGB);
    	// read the input image pixels
        for(int x = 1; x < readImg.getWidth() - 1; x++){
            for(int y = 1; y < readImg.getHeight() - 1; y++){
            	// perform mean filter on the image
                int mean = (int) performConvolution(ImageUtils.getImageMatrix(readImg, x, y));
                // set the new pixel value of the mean
                writeImg.setRGB(x,y, mean);
            }
        }
        return writeImg;
    }
    
    /**
     * Performs mean filter based on the convolution mask
     * returns the mean RGB color value of the image matrix
     *  
     * @param imageMatrix
     * @return
     */
    private static int performConvolution(int[][] imageMatrix){
    	double red = 0.0, green = 0.0, blue = 0.0;
    	double[][] mask = ImageUtils.getConvolationMask();
    	// compute the average of RGB colours
    	for(int i = 0; i < 3; i++){
    		for(int j = 0; j < 3; j++){
    			red += (new Color(imageMatrix[i][j]).getRed() * mask[i][j]);
    			blue += (new Color(imageMatrix[i][j]).getBlue() * mask[i][j]);
    			green += (new Color(imageMatrix[i][j]).getGreen() * mask[i][j]);
    		}
    	}
    	
    	double sum = 1; // TODO: ASK Harith if sum is sum of convolution mask or the sum of the pixels?
    	
    	double redAvg = red/sum;
    	double blueAvg = blue/sum;
    	double greenAvg = green/sum;
        return new Color((int)redAvg, (int)greenAvg, (int)blueAvg).getRGB();
    }
    
    public static void main(String[] args) {
    	String[] argConstants = {"ckt-board-saltpep.tif", "output_ckt-board-saltpep.tif", "tiff"};
    	for(int i = 0; i < args.length; i++){
    		argConstants[i] = args[i];
    	}
    	if (args.length == 1){
    		argConstants[1] = "output_" + argConstants[0];
    	}
    	String inputFilename = argConstants[0];
    	String outputFilename = argConstants[1];
    	String outputFiletype = argConstants[2];
    	
        try {
        	BufferedImage readImg = ImageIO.read(new File(inputFilename));
            System.out.println("Successfully read from image: " + inputFilename);
            // create image response        
            BufferedImage result = performMeanFilter(readImg);
            
            System.out.println("Writing to file: " + outputFilename);
            File outputfile = new File(outputFilename);
            ImageIO.write(result, outputFiletype, outputfile);
            System.out.println("Completed operation.");
        } catch (IOException ex) {System.err.println("Unable to read image. Please try again");}
    }
}
