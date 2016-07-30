package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * Created by Harman on 30/07/2016.
 */
public class MedianFilter {
    private static String[] IO_CONSTANTS = {"ckt-board-saltpep.tif", "output_ckt-board-saltpep.tif", "tiff"};

    private static BufferedImage performMedianFilter(final BufferedImage readImg){
        BufferedImage writeImg=new BufferedImage(readImg.getWidth(),readImg.getHeight(),TYPE_INT_RGB);
        // read the input image pixels
        for(int x=1; x < readImg.getWidth()-1; x++){
            for(int y=1; y < readImg.getHeight()-1; y++){
                int[] pixels = getImagePixels(readImg, x, y);
                //split the pixels in to Red Green Blue
                int[] redColours = new int[pixels.length];
                int[] blueColours = new int[pixels.length];
                int[] greenColours = new int[pixels.length];
                for(int i = 0; i < pixels.length; i++){
                    redColours[i] = new Color(pixels[i]).getRed();
                    blueColours[i] = new Color(pixels[i]).getBlue();
                    greenColours[i] = new Color(pixels[i]).getGreen();
                }
                Arrays.sort(redColours);
                Arrays.sort(blueColours);
                Arrays.sort(greenColours);

                // set the new pixel value
                writeImg.setRGB(x,y,new Color(redColours[4], greenColours[4], blueColours[4]).getRGB());
            }
        }
        return writeImg;
    }

    private static int[] getImagePixels(final BufferedImage readImg, final int width, final int height) {
        int[] pixels = new int[9];
        int k = 0;
        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                pixels[k] = readImg.getRGB(width + i, height + j);
                k++;
            }
        }
        return pixels;
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
            BufferedImage result = performMedianFilter(readImg);

            System.out.println("Writing to file: " + outputFilename);
            File outputfile = new File(outputFilename);
            ImageIO.write(result, outputFiletype, outputfile);
            System.out.println("Completed operation.");
        } catch (IOException ex) {System.err.println("Some error occurred. Please try again");}
    }
}
