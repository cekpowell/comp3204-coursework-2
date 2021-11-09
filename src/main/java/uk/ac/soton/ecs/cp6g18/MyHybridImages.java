package uk.ac.soton.ecs.cp6g18;

import org.openimaj.image.FImage;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.convolution.Gaussian2D;

/**
 * COMP3204: Computer Vision
 * 
 * Coursework 2: Exercise 2
 * 
 * Combining images together to create a hybrid image.
 */
public class MyHybridImages {

    /////////////////////////
    // MAKING HYBRID IMAGE //
    /////////////////////////

    /**
	 * Computes a hybrid image combining low-pass and high-pass filtered images
	 *
	 * @param lowImage The image to which apply the low pass filter
	 * @param lowSigma The standard deviation of the low-pass filter
	 * @param highImage The image to which apply the high pass filter
	 * @param highSigma The standard deviation of the low-pass component of computing the
	 * high-pass filtered image
	 * @return The computed hybrid image
     * 
     * // TODO this method assumes both input images are the same size
	 */
	public static MBFImage makeHybrid(MBFImage lowImage, float lowSigma, MBFImage highImage, float highSigma) {
        /**
         * Calculate low-pass version of low-image
         */
        MBFImage lowPassImage = MyHybridImages.getLowPassVersion(lowImage, lowSigma);

        /**
         * Calculate high-pass version of high-image
         */
        MBFImage highPassImage = MyHybridImages.getHighPassVersion(highImage, highSigma);

        /**
         * Combine low- and high-pass images together.
         */
        MBFImage hybridImage = MyHybridImages.addMBFImages(lowPassImage, highPassImage);

        // returning the hybrid image
        return hybridImage;
	}

    /**
     * Returns a low-pass version of the image. The low pass version of the image
     * is the image with all of the high-frequencies removed.
     * 
     * @param image The image for which the low pass version is being gathered.
     * @param sigma The sigma value used in the Gaussian filtering.
     * @return A low pass version of the image.
     */
    private static MBFImage getLowPassVersion(MBFImage image, float sigma){

        // creating a copy of the image (to become low pass version)
        MBFImage lowPassImage = image.clone();
        
        /**
         * Creating Gaussian Filter.
         */

        // determining size of filter
        int size = (int) (8.0f * sigma + 1.0f); // (this implies the window is +/- 4 sigmas from the centre of the Gaussian)
        if (size % 2 == 0) size++; // size must be odd

        // creating the gaussian filter
        FImage filter = Gaussian2D.createKernelImage(size, sigma);

        /**
         * Applying Gaussian filter to image.
         */

        // creating image processor
        MyConvolution myConvolution = new MyConvolution(filter.pixels);

        // applying image processor
        lowPassImage.processInplace(myConvolution);

        // returning the low pass version of the image.
        return lowPassImage;
    }

    /**
     * Returns a high-pass version of the image. The high-=pass version of the image
     * is the image with all of the low-frequencies removed.
     * 
     * @param image The image for which the high-pass version is being gathered.
     * @param sigma The sigma value used in the Gaussian filtering.
     * @return A high-pass version of the image.
     */
    private static MBFImage getHighPassVersion(MBFImage image, float sigma){

        // creating a copy of the image (to become low pass version)
        MBFImage highPassImage = image.clone();

        // getting low pass version of image
        MBFImage lowPassImage = MyHybridImages.getLowPassVersion(image, sigma);
        
        /**
         * Calculating difference between normal image and low-pass version.
         */

        highPassImage = MyHybridImages.subtractMBFImages(image, lowPassImage);

        // returning the high-pass version of the image.
        return highPassImage;
    }

    ////////////////////
    // HELPER METHODS //
    ////////////////////

    /**
     * Adds two MBFImages together by adding all of their individual pixel values
     * across all image bands.
     * 
     * @param image1 The first image being added.
     * @param image2 The second image being added.
     * @return An image equal to the addition  of the two provided images.
     * //TODO this method assumes the images are the same size and are in the same colour space.
     */
    private static MBFImage addMBFImages(MBFImage image1, MBFImage image2){
        // creating new MBFImage
        MBFImage result = new MBFImage(image1.getWidth(), image1.getHeight());

        // iterating through every band in the images
        for(int currentBand = 0; currentBand < image1.bands.size(); currentBand++){
            // getting the current band for each image
            FImage image1Band = image1.getBand(currentBand);
            FImage image2Band = image2.getBand(currentBand);

            // adding bands together
            FImage addedBands = MyHybridImages.addFImages(image1Band, image2Band);

            // setting added band into result
            result.getBand(currentBand).pixels = addedBands.pixels;
        }

        // returning result
        return result;
    }

    /**
     * Subtracts one MBFIMage from the other by subtracting the individual pixel values
     * from eachother across all image bands.
     * 
     * @param image1 The image being subtracted from.
     * @param image2 The image being subtracted.
     * @return An image equal to the subtraction of one image from the other.
     * // TODO this method assumes the images are the same size and are in the same colour space.
     */
    private static MBFImage subtractMBFImages(MBFImage image1, MBFImage image2){
        // creating new MBFImage
        MBFImage result = new MBFImage(image1.getWidth(), image1.getHeight());

        // iterating through every band in the images
        for(int currentBand = 0; currentBand < image1.bands.size(); currentBand++){
            // getting the current band for each image
            FImage image1Band = image1.getBand(currentBand);
            FImage image2Band = image2.getBand(currentBand);

            // adding bands together
            FImage subtractedBands = MyHybridImages.subtractFImages(image1Band, image2Band);

            // setting added band into result
            result.getBand(currentBand).pixels = subtractedBands.pixels;
        }

        // returning result
        return result;
    }

    /**
     * Adds two FImages together by adding all of their individual pixel values.
     * 
     * @param image1 The first image being added.
     * @param image2 The second image being added.
     * @return An image equal to the addition  of the two provided images.
     * //TODO this method assumes the images are the same size.
     */
    private static FImage addFImages(FImage image1, FImage image2){
        // creating addition matrix
        float[][] result = new float[image1.height][image1.width];

        // calculating the addition of the two matricies
        for(int row = 0; row < MyHybridImages.getMatrixHeight(result); row++){
            for(int col = 0; col < MyHybridImages.getMatrixWidth(result); col++){
                result[row][col] = image1.pixels[row][col] + image2.pixels[row][col];
                
                // TODO handle case where value is less than zero?
            }
        }

        // creating image from the resulting pixel values
        return new FImage(result);
    }

    /**
     * Subtracts one FImage from the other by subtracting the individual pixel values.
     * 
     * @param image1 The image being subtracted from.
     * @param image2 The image being subtracted.
     * @return An image equal to the subtraction of one image from the other.
     * // TODO this method assumes the images are the same size.
     */
    private static FImage subtractFImages(FImage image1, FImage image2){
        // creating addition matrix
        float[][] result = new float[image1.height][image1.width];

        // calculating the addition of the two matricies
        for(int row = 0; row < MyHybridImages.getMatrixHeight(result); row++){
            for(int col = 0; col < MyHybridImages.getMatrixWidth(result); col++){
                result[row][col] = image1.pixels[row][col] - image2.pixels[row][col];
                
                // TODO handle case where value is less than zero?
            }
        }

        // creating image from the resulting pixel values
        return new FImage(result);
    }

    /**
     * Performs addition between two matricies. Each element in the first matrix is 
     * added to the equivelent element in the second matrix. 
     * 
     * @param matrixA The first matrix involved in the addition.
     * @param matrixB The second matrix involved in the addition.
     * @return A matrix where each element is equal to the addition of the same elements 
     * in the provided matricies.
     * 
     * //TODO this method assumes the matricies are the same size.
     */
    private static float[][] addMatricies(float[][] matrixA, float[][] matrixB){
        // creating addition matrix
        float[][] result = new float[MyHybridImages.getMatrixHeight(matrixA)][MyHybridImages.getMatrixWidth(matrixA)];

        // calculating the addition of the two matricies
        for(int row = 0; row < MyHybridImages.getMatrixHeight(result); row++){
            for(int col = 0; col < MyHybridImages.getMatrixWidth(result); col++){
                result[row][col] = matrixA[row][col] + matrixB[row][col];

                // TODO handle case where value is > max value
            }
        }

        // returning the added matrix
        return result;
    }

    /**
     * Performs subtraction between two matricies. Subtracts each element in the second
     * matrix from the same element in the first matrix.
     * 
     * @param matrixA The matrix being subtracted from.
     * @param matrixB The matrix being subtracted.
     * @return A matrix where each element is equal to the subtraction of the same 
     * elements in the provided matricies.
     * 
     * //TODO this method assumes the matricies are the same size.
     */
    private static float[][] subtractMatricies(float[][] matrixA, float[][] matrixB){
        // creating addition matrix
        float[][] result = new float[MyHybridImages.getMatrixHeight(matrixA)][MyHybridImages.getMatrixWidth(matrixA)];

        // calculating the addition of the two matricies
        for(int row = 0; row < MyHybridImages.getMatrixHeight(result); row++){
            for(int col = 0; col < MyHybridImages.getMatrixWidth(result); col++){
                result[row][col] = matrixA[row][col] - matrixB[row][col];
                
                // TODO handle case where value is less than zero?
            }
        }

        // returning the added matrix
        return result;
    }

    /**
     * Returns the height of the matrix - i.e., the number of
     * rows in the matrix.
     * 
     * @param matrix The matrix for which the height is being gathered.
     * @return The height of the matrix - i.e., the number of rows in the matrix.
     */
    private static int getMatrixHeight(float[][] matrix){
        return matrix.length;
    }

    /**
     * Returns the width of the provided matrix - i.e., the number of
     * columns in the matrix.
     * 
     * @param matrix The matrix for which the width is being gathered.
     * @return The width of the matrix - the number of rows in the matrix.
     * 
     * // TODO this method assumes the matrix is initialized with at least 1 element and is uniform.
     */
    private static int getMatrixWidth(float[][] matrix){
        return matrix[0].length;
    }
}

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