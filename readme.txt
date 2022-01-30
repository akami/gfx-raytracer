 ____________________________________
 | ------------ CLAIM ------------- |
 ------------------------------------

 - T1, T2, T3, T4, T5, T6
 - EF3
 - B6

- additional remarks:
-- tinted refractive objects/specular
    After implementing refraction/reflection, I noticed that the specular reflection (in example5 and up) is not as
    bright as it should be. Also, objects that have transmittance seem to are tinted slightly with the object's color.

    I checked my shading and the loop where I go through the lights in the raytracer multiple times and could not find the
    error. Lights are not skipped, all lights are considered when calculating the color. The ambient component is also only
    added once for the ambient light.

    However, I noticed that the reference images are 32-bit color images, whereas my output images are 24-bit color. I don't
    think that the error is here though.

-- EF3/B6: applied gridding for supersampling; there can be performance issues if the sample amount is set too high as
   Java is not the most performant language.

 __________________________________________________
 | ------------ TESTED ENVIRONMENTS ------------- |
 --------------------------------------------------

-- dev-computer: OS: Fedora 33 (Workstation Edition)
                Host: 20LJS02W00 ThinkPad X380 Yoga
                IDE: WebStorm
                Browser: Firefox

-- test-computer:

--- dev-computer
--- Macbook Pro, MacOS Monterey
--- PC, Windows10

-- tested server:

--- locally
--- almighty server

 ________________________________________
 | ------------ RESOURCES ------------- |
 ----------------------------------------

--- raytracing tutorial provided by the tutor:
---- how to generate camera rays
---- how to handle acne bias
---- triangle intersection(MT algorithm)/reflection/refraction/texture mapping + provided links to resources thereof

--- ray-sphere intersection: https://www.scratchapixel.com/code.php?id=10&origin=/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes
--- phong illumination: Angel book, lecture slides
--- xsd, xcj, JAXB, SAXB, ... : https://examples.javacodegeeks.com/core-java/xml/bind/jaxb-generate-classes-xsd/, https://docs.oracle.com/cd/B19306_01/appdev.102/b14252/adx_j_jaxb.htm

--- anti-aliasing/supersampling: tutorial slides, https://en.wikipedia.org/wiki/Supersampling

 _____________________________________________________
 | ---------- GENERAL REMARKS: STRUCTURE ----------- |
 -----------------------------------------------------
 remarks on general structure of the project and where to find things:

    |- input: provided data for input
    |- output: raytraced image files
    |- references: reference images provided on the course page
    |- application: raytracer application

 _____________________________________________________
 | ---------- GENERAL REMARKS: XML PARSER ---------- |
 -----------------------------------------------------
-- I have created the scene.xsd and scene.xjb files manually
    @see raytracer/src/main/resources

-- XML mapped classes are generated using the above files using the xjc trans-compiler
    @see raytracer/src/main/java/at/ac/univie/unet/a01638800/raytracer/parser/Parser.java
    @see raytracer/build/classes

-- Instead of writing classes for each xml element, the .xml classes are ready to use in the application

 _____________________________________
 | ------------ HOW TO ------------- |
 -------------------------------------
- locally:
 -- . ./gradlew build
 -- java -jar dist/lab3b-1.0.0.jar <path/to/example_file.xml>
 -- example files can be found in the input folder

 - on almighty:
 -- hoecknerk96/Public/gfx_ws21_a01638800/lab3b/
 -- java -jar raytracer.jar input/example6.xml

 -- or same as locally

 raytraced scenes can be found in the output folder.

 -- effects:
    - for anti-aliasing:
     -- java -jar dist/lab3a-1.0.0.jar <path/to/example_file.xml> anti=<sample amount>