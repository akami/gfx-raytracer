package at.ac.univie.unet.a01638800.raytracer.parser;

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
import java.io.StringWriter;
import java.io.Writer;

public class Parser {
    private static Parser PARSER;

    public static Parser getInstance() throws ParserException {
        if (PARSER == null) {
            PARSER = new Parser();
        }

        return PARSER;
    }

    private final XMLReader xmlReader;

    private final Unmarshaller unmarshaller;
    private final Marshaller marshaller;

    private Parser() throws ParserException {
        try {
            // setup of SAX to disable DTD validation
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

    public Scene parseFile(InputStream inputStream) throws ParserException {
        try {
            SAXSource source = new SAXSource(xmlReader, new InputSource(inputStream));

            return (Scene) unmarshaller.unmarshal(source);
        } catch (JAXBException ex) {
            throw new ParserException("Could not parse input stream!", ex);
        }
    }

    public String parseScene(Scene scene) throws ParserException {
        try {
            Writer writer = new StringWriter();
            marshaller.marshal(scene, writer);

            return writer.toString();
        } catch (JAXBException ex) {
            throw new ParserException("Could not parse object!", ex);
        }
    }

    public File parseSceneToFile(Scene scene) throws ParserException {
        try {
            File file = new File("./output/" + scene.getOutputFile());

            at.ac.univie.unet.a01638800.raytracer.Scene raytracedScene = new at.ac.univie.unet.a01638800.raytracer.Scene(scene);

            BufferedImage image = raytracedScene.getImage();;
            ImageIO.write(image, "PNG", file);

            return file;
        } catch (Exception ex) {
            throw new ParserException("Could not output file!", ex);
        }
    }
}
