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

- T7: I tried implementing object transformations but failed. Transformations seem to work (I tried it out by
manually adding transformations to examples 5-7), but the normals are off. I could not find my error ,unfortunately.
Also, I have major performance issues with running example9 (it takes up to 10 minutes on my machine) and it also produces
a weird gray image.

- performance:
Choosing Java definitely contributed negatively to the performance of the raytracer. However, the logic I implemented
for the object transformations (checks if rays should be transformed or not, the matrix operations,...) also
is a huge factor why I got performance issues. Example 8 for instance takes around 4 seconds to run (with still having
the object transformation logic in the application). Before adding the logic for T7, the application ran in under one second.

I decided not to increase performance again by removing the logic for T7. I kept it instead as I would love to get feedback
to where things might have gone wrong.

 __________________________________________________
 | ------------ TESTED ENVIRONMENTS ------------- |
 --------------------------------------------------

-- dev-computer: OS: Fedora 33 (Workstation Edition)
                Host: 20LJS02W00 ThinkPad X380 Yoga
                IDE: IntelliJ Ultimate
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
---- triangle intersection(MT algorithm)/reflection/refraction/texture mapping + provided links to resources

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