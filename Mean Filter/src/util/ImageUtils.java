package util;

import java.awt.image.BufferedImage;

/**
 * Utility class for Mean Filter
 * 
 * @author Harman Singh
 */
public class ImageUtils {

	/**
	 * The convolution mask
	 * @return
	 */
	public static double[][] getConvolationMask(){
    	double[][]  mask = { 
    			{ 1/9.0, 1/9.0, 1/9.0}, 
    			{ 1/9.0, 1/9.0, 1/9.0},
    			{ 1/9.0, 1/9.0, 1/9.0}};    	
    	
    	return mask;
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
		imgMatrix[0][0] = readImg.getRGB(width - 1, height - 1);
        imgMatrix[0][1] = readImg.getRGB(width - 1,height);
        imgMatrix[0][2] = readImg.getRGB(width - 1, height + 1);
        imgMatrix[1][0] = readImg.getRGB(width, height - 1);
        imgMatrix[1][1] = readImg.getRGB(width, height);
        imgMatrix[1][2] = readImg.getRGB(width, height + 1);
        imgMatrix[2][0] = readImg.getRGB(width + 1, height - 1);
        imgMatrix[2][1] = readImg.getRGB(width + 1, height);
        imgMatrix[2][2] = readImg.getRGB(width + 1, height + 1);
        return imgMatrix;
	}
}
