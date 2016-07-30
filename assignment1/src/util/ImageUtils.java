package util;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * Utility class for Sobel Operator
 * 
 * @author Harman Singh
 */
public class ImageUtils {

	/**
	 * The vertical convolution mask
	 * @return
	 */
	public static int[][] getHorizontalConvulationMask(){
    	int[][]  mask = { 
    			{ 1, 0,-1}, 
    			{ 2, 0,-2},
    			{ 1, 0,-1}};    	
    	
    	return mask;
    }
    
	/**
	 * The horizontal convulation mask
	 * @return
	 */
    public static int[][] getVerticalConvulationMask(){
    	int[][]  mask = { 
    			{-1,-2,-1}, 
    			{ 0, 0, 0},
    			{ 1, 2, 1}};
    	
    	return mask;
    }
    
    private static int getImageColourValue(final int rgb){
    	return new Color(rgb).getRed();
    }

    /**
     * Returns image matrix which is a 3x3 matrix of the pixel colour of image 
     * 
     * @param readImg
     * @param width
     * @param height
     * @return
     */
	public static int[][] getImageMatrix(final BufferedImage readImg, final int width, final int height) {
		int[][] imgMatrix = new int[3][3];
		imgMatrix[0][0] = ImageUtils.getImageColourValue(readImg.getRGB(width-1, height-1));
        imgMatrix[0][1] = ImageUtils.getImageColourValue(readImg.getRGB(width-1,height));
        imgMatrix[0][2] = ImageUtils.getImageColourValue(readImg.getRGB(width-1, height+1));
        imgMatrix[1][0] = ImageUtils.getImageColourValue(readImg.getRGB(width, height - 1));
        imgMatrix[1][2] = ImageUtils.getImageColourValue(readImg.getRGB(width, height+ 1));
        imgMatrix[2][0] = ImageUtils.getImageColourValue(readImg.getRGB(width+1, height - 1));
        imgMatrix[2][1] = ImageUtils.getImageColourValue(readImg.getRGB(width+1, height));
        imgMatrix[2][2] = ImageUtils.getImageColourValue(readImg.getRGB(width+1, height+1));
        return imgMatrix;
	}
}
