package uk.ac.soton.ecs.cp6g18.hybridimages;

import java.net.URL;
import java.util.Arrays;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;

/**
 * Tester class for project.
 */
public class Tester {

    /////////////////  
    // MAIN METHOD //
    /////////////////

    /**
     * Main method.
     * 
     * @param args System arguments.
     */
    public static void main( String[] args ) throws Exception {
        //Tester.testMyConvolution();

        Tester.testMyHybridImages();
    }

    ///////////////////
    // MYCONVOLUTION //
    ///////////////////

    /**
     * Tester method for MyConvolution class.
     */
    public static void testMyConvolution() throws Exception{
        ////////////////////
        // SAMPLE KERNELS //
        ////////////////////

        // sobel
        float[][] sobelKernel = {
                                {2,2,0},
                                {2,0,-2},
                                {0,-2,-2}
                                };

        // averaging
        float[][] meanAverageKernel = {
                                      {0.04F,0.04F,0.04F,0.04F,0.04F},
                                      {0.04F,0.04F,0.04F,0.04F,0.04F},
                                      {0.04F,0.04F,0.04F,0.04F,0.04F},
                                      {0.04F,0.04F,0.04F,0.04F,0.04F},
                                      {0.04F,0.04F,0.04F,0.04F,0.04F}
                                      };

        // non-uniform
        float[][] nonUniformKernel = {
                                     {1,2,3,4,5},
                                     {1,2,3,4,5},
                                     {1,2,3,4,5}
                                     };
        
        // column shift
        float[][] colShiftKernel = {
                                      {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
                                      };
        
        // column shift
        float[][] rowShiftKernel = {
                                    {0}, {0}, {0}, {0},{0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {1}
                                    };

        //////////////////
        // SAMPLE IMAGE //
        //////////////////

        // creating image object
        FImage image;

        // loading image from url
        image = ImageUtilities.readF(new URL("https://www.researchgate.net/profile/Cataldo-Guaragnella/publication/235406965/figure/fig1/AS:393405046771720@1470806480985/Original-image-256x256-pixels.png"));

        // copy of sample image
        FImage rawImage = image.clone();

        //////////////////////
        // PROCESSING IMAGE //
        //////////////////////

        // my convolution instance
        MyConvolution myConvolution = new MyConvolution(rowShiftKernel);

        // processing the sample image
        myConvolution.processImage(image);

        // displaying the raw and processed image
        DisplayUtilities.display(rawImage, "Raw Image");
        DisplayUtilities.display(image, "Processed Image");
    }

    /////////////////////
    // MYHYBRID IMAGES //
    /////////////////////

    /**
     * Tester method for MyHybridImages class.
     */
    public static void testMyHybridImages() throws Exception{
        ///////////////
        // LOW IMAGE //
        ///////////////

        // creating image object
        MBFImage lowImage;

        // loading image from url
        lowImage = ImageUtilities.readMBF(new URL("http://comp3204.ecs.soton.ac.uk/cw/dog.jpg"));

        ////////////////
        // HIGH IMAGE //
        ////////////////

        // creating image object
        MBFImage highImage;

        // loading image from url
        highImage = ImageUtilities.readMBF(new URL("http://comp3204.ecs.soton.ac.uk/cw/cat.jpg"));

        ///////////////////////////
        // CREATING HYBRID IMAGE //
        ///////////////////////////

        MBFImage hybridImage = MyHybridImages.makeHybrid(lowImage, 5.0F, highImage, 5.0F);

        DisplayUtilities.display(hybridImage);
    }

    ////////////////////
    // HELPER METHODS //
    ////////////////////

    /**
     * Prints a matrix to the screen.
     * 
     * @param matrix The matrix being printed.
     */
    public static void printMatrix(float[][] matrix){
        for(float[] row : matrix){
            System.out.println(Arrays.toString(row));
        }
    }
}

/**
 * Woman Image - https://www.researchgate.net/profile/Cataldo-Guaragnella/publication/235406965/figure/fig1/AS:393405046771720@1470806480985/Original-image-256x256-pixels.png
 * Discord Image - https://www.freepnglogos.com/uploads/discord-logo-png/papirus-apps-iconset-papirus-development-team-discord-icon-14.png
 * Cat image - http://comp3204.ecs.soton.ac.uk/cw/cat.jpg
 * Dog image - http://comp3204.ecs.soton.ac.uk/cw/dog.jpg
 * */ 


// METHOD FOR PRODUCING GAUSSIAN KERNEL //


 // /**
//      * Produces a Gaussian matrix of the provided dimension using the provided sigma value.
//      * 
//      * @param width The width of the Gaussian matrix.
//      * @param height The height of the Gaussian matrix.
//      * @param sigma The sigma value for the Gaussian matrix.
//      * @return The Gaussian matrix of the requested matrix and sigma value.
//      * 
//      * // TODO this method assumes the width and height are both odd (i.e., that there is a center point to the matrix)
//      */
//     public static float[][] getGaussianMatrix(int width, int height, float sigma){
//         // initializing matrix
//         float[][] gaussianMatrix = new float[width][height];

//         // how much to reach left/right and up/down from center point (half size of kernel)
//         int reachCol = Math.floorDiv(width, 2);
//         int reachRow = Math.floorDiv(height, 2);

//         /**
//          * Populating matrix
//          */
//         for(int row = 0; row < height; row++){
//             for(int col = 0; col < width; col++){
//                 // x and y values
//                 int x = -reachCol + col;
//                 int y = -reachRow + row;

//                 // calculating value using gaussian distribution formula
//                 float leftTerm = (1 / (2 * (float) Math.PI * sigma * sigma));
//                 float exponentNumer = - (x*x) - (y*y);
//                 float exponentDenom = 2 * sigma * sigma;
//                 float rightTerm = (float) Math.pow(Math.E, exponentNumer / exponentDenom);
//                 float value = leftTerm * rightTerm;

//                 // setting value into gaussian matrix
//                 gaussianMatrix[row][col] = value;
//             }
//         }

//         // returning produced gaussian matrix
//         return gaussianMatrix;
//     }