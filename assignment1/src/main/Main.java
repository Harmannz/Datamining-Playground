package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import util.ImageUtils;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * Performs Sobel Operator on image given as the first argument. 
 * If first argument is not provided then the example image is used.
 * 
 * The second argument is optional and is used to define the filename of the resulting image name.
 * If second argument is not provided then it is set by prepending output_ to input image name.
 * 
 * The third argument is optional and is used to define the type of the resulting image name.
 * If third argument is not provided then it is defaulted to Tiff format 
 * 
 *  
 * Created by Harman Singh on 29/07/16.
 */
public class Main {

	private static String[] IO_CONSTANTS = {"test-pattern.tif", "output_test-pattern.tif", "tiff"};
	/**
	 * Performs the Sobel operation on the input image.
	 * 
	 * @param inputImg
	 * @return
	 */
    private static BufferedImage performSobelOperation(final BufferedImage readImg){
    	BufferedImage writeImg=new BufferedImage(readImg.getWidth(),readImg.getHeight(),TYPE_INT_RGB);
    	// read the input image pixels
        for(int x=1; x < readImg.getWidth()-1; x++){
            for(int y=1; y < readImg.getHeight()-1; y++){
            	// perform sobel operator on the convolution
                int weight = (int) performConvolution(ImageUtils.getImageMatrix(readImg, x, y));
                
                if (weight > 225){
                	weight = 225;
                }
                // set the new pixel value
                writeImg.setRGB(x,y,new Color(weight,weight,weight).getRGB());
            }
        }
        return writeImg;
    }
    
    /**
     * Performs Sobel operator on horizontal and vertical convolution masks.
     * Then returns the magnitude of the edge
     *  
     * @param imageMatrix
     * @return
     */
    private static double performConvolution(int[][] imageMatrix){
    	int verticalSum = 0;
    	int horizontalSum = 0;
    	int[][] horizontalMask = ImageUtils.getHorizontalConvulationMask();
    	int[][] verticalMask = ImageUtils.getVerticalConvulationMask();
    	for(int i = 0; i < 3; i++){
    		for(int j = 0; j < 3; j++){
    			verticalSum += (imageMatrix[i][j] * verticalMask[i][j]);
    			horizontalSum += (imageMatrix[i][j] * horizontalMask[i][j]);
    		}
    	}
        return Math.sqrt(Math.pow(verticalSum,2)+Math.pow(horizontalSum,2));
    }
    
    public static void main(String[] args) {
    	for(int i = 0; i < args.length; i++){
    		IO_CONSTANTS[i] = args[i];
    	}
    	if (args.length == 1){
    		IO_CONSTANTS[1] = "output_" + IO_CONSTANTS[0];
    	}
    	String inputFilename = IO_CONSTANTS[0];
    	String outputFilename = IO_CONSTANTS[1];
    	String outputFiletype = IO_CONSTANTS[2];
    	
        try {
        	BufferedImage readImg = ImageIO.read(new File(inputFilename));
            System.out.println("Successfully read from image: " + inputFilename);
            // create image response        
            BufferedImage result = performSobelOperation(readImg);
            
            System.out.println("Writing to file: " + outputFilename);
            File outputfile = new File(outputFilename);
            ImageIO.write(result, outputFiletype, outputfile);
            System.out.println("Completed operation.");
        } catch (IOException ex) {System.err.println("Some error occurred. Please try again");}
    }
}
