 ____________________________________
 | ------------ CLAIM ------------- |
 ------------------------------------

 - T1, T2, T3, T4, T5


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

--- ray-sphere intersection: https://www.scratchapixel.com/code.php?id=10&origin=/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes
--- phong illumination: Angel book, lecture slides
--- xsd, xcj, JAXB, SAXB, ... : https://examples.javacodegeeks.com/core-java/xml/bind/jaxb-generate-classes-xsd/, https://docs.oracle.com/cd/B19306_01/appdev.102/b14252/adx_j_jaxb.htm

 _____________________________________________________
 | ---------- GENERAL REMARKS: STRUCTURE ----------- |
 -----------------------------------------------------
 remarks on general structure of the project and where to find things:

    |- input: provided data for input
    |- output: raytraced image files
    |- application: raytracer application

 _____________________________________________________
 | ---------- GENERAL REMARKS: XML PARSER ---------- |
 -----------------------------------------------------
-- I have created the scene.xsd and scene.xjb files manually
    @see raytracer/src/main/resources

-- XML mapped classes are generated using the above files using the xjc trans-compiler
    @see raytracer/src/main/java/at/ac/univie/unet/a01638800/raytracer/parser/Parser.java
    @see raytracer/build/classes

-- Instead of writing classes for each xml element, the generated classes are ready to use in the application

 _____________________________________
 | ------------ HOW TO ------------- |
 -------------------------------------
- locally:
 -- . ./gradlew build
 -- java -jar dist/lab3a-1.0.0.jar <path/to/example_file.xml>
 -- example files can be found in the input folder

 - on almighty:
 -- hoecknerk96/Public/gfx_ws21_a01638800/lab3a/
 -- java -jar raytracer.jar input/example3.xml

 -- or same as locally


 raytraced scenes can be found in the output folder.