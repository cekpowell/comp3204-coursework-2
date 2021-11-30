# Template Convolution and Hybrid Images
## COMP3204: Computer Vision
---
## Introduction

### Task Description

- Write programs to:
  - **1** - Perform [Template Convolution](http://comp3204.ecs.soton.ac.uk/cw/template_convolution_4th_edition.pdf) on images.
  - **2** - Create [Hybrid Images](https://stanford.edu/class/ee367/reading/OlivaTorralb_Hybrid_Siggraph06.pdf) by combining the low frequencies of one image with the high frequencies of another.

- Using the [Open-IMAJ](http://openimaj.org/) library.

**<u>*Image here*</u>**

### Project Contents

- Contained in the project are two java files:
  - `MyConvolution.java` : Used to **perform template convolution** on a given image.
  - `MyHybridImages.java` : Used to **create a hybrid image** by combining the low frequencies of one image with the high frequencies of another.
- The project is not runnable - you must reference the above classes and their methods within your own code to make use of them.

---
## Usage

### Performing Template Convolution

- The `MyConvolution.java` class is used to perform template convolution.
- Instances of `MyConvolution.java` are parameterised on the template kernel, which is a 2D array of float values.
- The `processImage()` method of `MyConvolution.java` takes in an `FImage` instance as a parameter, and performs template convolution on the image with the `MyConvolution` instance's kernel.

```java
public void processImage(FImage image) {
        ...
}
```

- The image processing is done in place - i.e., the `FImage` passed into the `processImage` method will have it's pixel values changed rather than a new image being returned.

#### Example

- Here is an example image before and after template convolution was applied.
- In this case, a Gaussian Averging kernel was used to smooth the image (*i.e., remove noise*).

**<u>*Image here*</u>**

### Creating Hybrid Images

- The `MyHybridImages.java` class is used to create hybrid images.
- `MyHybridImages.java` contains a single public-static method `makeHybrid` that takes in two `MBFImages`, along with the sigma values that denote the proportions of low and high frequencies that will be placed into the resulting hybrid image.

```java
public static MBFImage makeHybrid(MBFImage lowImage, float lowSigma, MBFImage highImage, float highSigma) {
        ...
}
```

- The `makeHybrid()` method returns a new `MBFImage` that is a combination of the low frequencies from the 'low-image' and the high frequencies from the 'high-image'.

#### Example

- Here is an example hybrid image created by combining the low frequencies of one image with the high frequencies of another.

**<u>*Image here*</u>**

---
