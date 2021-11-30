# Template Convolution and Hybrid Images
## COMP3204: Computer Vision
---
## Contents

- **[Introduction](#introduction)**
  * **[Task Description](#task-description)**
  * **[Project Contents](#project-contents)**
- **[Usage](#usage)**
  * **[Performing Template Convolution](#performing-template-convolution)**
    * **[Example](#example)**
  * **[Creating Hybrid Images](#creating-hybrid-images)**
    * **[Example](#example)**

---

## Introduction

### Task Description

- Use the [Open-IMAJ](http://openimaj.org/) library to write programs to:
  - **1** - Perform [Template Convolution](http://comp3204.ecs.soton.ac.uk/cw/template_convolution_4th_edition.pdf) on images.

<p align="center"> <img width="400" alt="Screenshot 2021-11-30 at 21 54 13" src="https://user-images.githubusercontent.com/60888912/144134909-c23ea353-d68a-43bf-9c79-2346b41152c6.png"></p>

  - **2** - Create [Hybrid Images](https://stanford.edu/class/ee367/reading/OlivaTorralb_Hybrid_Siggraph06.pdf) by combining the low frequencies of one image with the high frequencies of another.

<p align="center"> <img width="250" alt="Screenshot 2021-11-30 at 21 54 13" src="https://user-images.githubusercontent.com/60888912/144134132-249ffdaf-f87e-43de-84ce-d08cc7b0e0f8.png"></p>

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

- The original image:

<p align="center"> <img width="250" alt="Screenshot 2021-11-30 at 21 44 54" src="https://user-images.githubusercontent.com/60888912/144133804-eebed193-83f4-4ec6-acb1-1d6f886328ac.png"></p>

- The effect of applying the Gaussian Averaging to the image with different sized Gaussian Kernels:

<p align="center"> <img width="600" alt="Screenshot 2021-11-30 at 21 44 54" src="https://user-images.githubusercontent.com/60888912/144133944-66247711-c48b-4d96-afd9-dcb15024733c.png"></p>

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

- Here is an example hybrid image created by combining the low frequencies of an image of a dog with the high frequencies of an image of a cat.
- The original images:

<p align="center"> <img width="500" alt="Screenshot 2021-11-30 at 21 44 54" src="https://user-images.githubusercontent.com/60888912/144133008-a21887e2-52fd-4f89-84d5-b4d9d85aeb1c.png"></p>

- The filtered versions of the image (low-frequencies of dog and high frequencies of cat):

<p align="center"> <img width="500" alt="Screenshot 2021-11-30 at 21 46 49" src="https://user-images.githubusercontent.com/60888912/144133173-502219a7-1b1f-4f3a-99b3-e23a749d777a.png"></p>

- The hybrid image formed by combining the two filtered versions:

<p align="center"> <img width="500" alt="Screenshot 2021-11-30 at 21 47 48" src="https://user-images.githubusercontent.com/60888912/144133299-e5484412-fc26-4341-9835-99d9067bcd10.png"></p>

---
