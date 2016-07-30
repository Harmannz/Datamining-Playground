package main;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import util.ImageUtils;

/**
 * 
 * @author Harman Singh
 */
public class LaplacianFilter {
	/**
	 * Performs the Laplacian Filter on the input image.
	 * 
	 * @param readImg
	 * @return
	 */
    private static BufferedImage performLaplacianFilter(final BufferedImage readImg){
    	BufferedImage writeImg=new BufferedImage(readImg.getWidth(),readImg.getHeight(),TYPE_INT_RGB);
    	// read the input image pixels
        for(int x = 1; x < readImg.getWidth() - 1; x++){
            for(int y = 1; y < readImg.getHeight() - 1; y++){
            	// perform laplacion filter on the image using convolution mask
                int weight = (int) performConvolution(ImageUtils.getImageMatrix(readImg, x, y));
                // set the new pixel value of the mean
                writeImg.setRGB(x,y, weight);
            }
        }
        return writeImg;
    }
    
    /**
     * Performs laplacian filter based on the convolution mask
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
    	// limit the RGB colour values within a byte
    	red = Math.min(255, Math.max(0, red));
    	blue = Math.min(255, Math.max(0, blue));
    	green = Math.min(255, Math.max(0, green));
        return new Color((int)red, (int)green, (int)blue).getRGB();
    }
    
    public static void main(String[] args) {
    	String[] argConstants = {"blurry-moon.tif", "output_blurry-moon.tif", "tiff"};
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
            BufferedImage result = performLaplacianFilter(readImg);
            
            System.out.println("Writing to file: " + outputFilename);
            File outputfile = new File(outputFilename);
            ImageIO.write(result, outputFiletype, outputfile);
            System.out.println("Completed operation.");
        } catch (IOException ex) {System.err.println("Unable to read image. Please try again");}
    }
}
