package at.ac.univie.unet.a01638800.raytracer;

import at.ac.univie.unet.a01638800.raytracer.parser.Parser;
import at.ac.univie.unet.a01638800.raytracer.parser.ParserException;
import at.ac.univie.unet.a01638800.raytracer.scene.Scene;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, ParserException {
        // accept file path as input parameter
        String filePath = args[0];
        Parser parser = Parser.getInstance();

        Scene scene = parser.parseFile(new FileInputStream(filePath));
        File file = parser.parseSceneToFile(scene, DebugMode.NORMALS);

        System.out.println(file);
    }
}
