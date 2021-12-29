- short guide:
-- . ./gradlew build
-- java -jar dist/preplab2-1.0.0.jar <path/to/example_file.xml>

- tested environments:
-- dev-computer: OS: Fedora 33 (Workstation Edition)
                Host: 20LJS02W00 ThinkPad X380 Yoga
                IDE: IntelliJ
                JRE/JDK: OpenJDK 11.0.10 64bit

-- test-computer:
--- dev-computer

- general remarks:
-- I've created the scene.xsd and scene.xjb files
-- XML mapped classes are generated using the above files using the xjc trans-compiler
-- for the command line output I used a separate library (Apache Commons Lang) to avoid writing toString() methods into the generated classes
