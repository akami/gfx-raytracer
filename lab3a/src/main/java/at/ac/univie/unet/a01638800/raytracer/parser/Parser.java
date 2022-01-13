package at.ac.univie.unet.a01638800.raytracer.parser;

import at.ac.univie.unet.a01638800.raytracer.DebugMode;
import at.ac.univie.unet.a01638800.raytracer.raytracedscene.RaytracedScene;
import at.ac.univie.unet.a01638800.raytracer.scene.ObjectFactory;
import at.ac.univie.unet.a01638800.raytracer.scene.Scene;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

/**
 * Parses a given dtd input file and generates classes using SAX, JAXB and xcj.
 */
public class Parser {
    private static Parser PARSER;
    private final XMLReader xmlReader;
    private final Unmarshaller unmarshaller;
    private final Marshaller marshaller;

    /**
     * Sets up the SAXParserFactory in order to load an external dtd file and generate classes out of it.
     *
     * @throws ParserException
     */
    private Parser() throws ParserException {
        try {

            /*
            * Set up SAX to disable dtd validation. This was done in case no scene.dtd file as referenced by the xml
            * file is given.
            */
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            saxParserFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

            xmlReader = saxParserFactory.newSAXParser().getXMLReader();

            // setup of JAXB and unmarshaller
            JAXBContext jaxbContext = JAXBContext.newInstance("at.ac.univie.unet.a01638800.raytracer.scene", ObjectFactory.class.getClassLoader());
            unmarshaller = jaxbContext.createUnmarshaller();
            marshaller = jaxbContext.createMarshaller();
        } catch (JAXBException | ParserConfigurationException | SAXException ex) {
            throw new ParserException("Could not create JAXB context!", ex);
        }
    }

    public static Parser getInstance() throws ParserException {
        if (PARSER == null) {
            PARSER = new Parser();
        }

        return PARSER;
    }

    /**
     * Parses an xml input file and converts it to a scene object.
     *
     * @param inputStream the file input stream (file path) of the scene xml file
     * @return scene object that can be used in the application
     * @throws ParserException
     */
    public Scene parseFile(InputStream inputStream) throws ParserException {
        try {
            SAXSource source = new SAXSource(xmlReader, new InputSource(inputStream));

            return (Scene) unmarshaller.unmarshal(source);
        } catch (JAXBException ex) {
            throw new ParserException("Could not parse input stream!", ex);
        }
    }

    /**
     * Parses the scene to the raytracer and outputs the result as a PNG file.
     *
     * @param scene     the parsed Scene object
     * @param debugMode type of debug mode
     * @return PNG image file
     * @throws ParserException
     */
    public File parseSceneToFile(Scene scene, DebugMode debugMode) throws ParserException {
        try {
            File directory = new File("./output");
            directory.mkdirs();

            File file = new File("./output/" + scene.getOutputFile());

            RaytracedScene raytracedScene = new RaytracedScene(scene, debugMode);

            BufferedImage image = raytracedScene.getImage();
            ImageIO.write(image, "PNG", file);

            return file;
        } catch (Exception ex) {
            throw new ParserException("Could not output file!", ex);
        }
    }
}
