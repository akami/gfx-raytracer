package at.ac.univie.unet.a01638800.raytracer.parser.scene;

import at.ac.univie.unet.a01638800.raytracer.xml.scene.ObjectFactory;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlScene;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * Parses a given dtd input file and generates classes using SAX, JAXB and xcj.
 */
public class SceneParser {

    private static SceneParser INSTANCE;
    private final XMLReader xmlReader;
    private final Unmarshaller unmarshaller;

    /**
     * Sets up the SAXParserFactory in order to load an external dtd file and generate classes out of it.
     *
     * @throws SceneParserException
     */
    private SceneParser() throws SceneParserException {
        try {

            /*
             * Set up SAX to disable dtd validation. This was done in case no scene.dtd file as referenced by the xml
             * file is given.
             */
            final SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            saxParserFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

            xmlReader = saxParserFactory.newSAXParser().getXMLReader();

            // setup of JAXB and unmarshaller
            final JAXBContext jaxbContext = JAXBContext.newInstance("at.ac.univie.unet.a01638800.raytracer.xml.scene", ObjectFactory.class.getClassLoader());
            unmarshaller = jaxbContext.createUnmarshaller();
        } catch (final JAXBException | ParserConfigurationException | SAXException ex) {
            throw new SceneParserException("Could not create JAXB context!", ex);
        }
    }

    public static SceneParser getInstance() throws SceneParserException {
        if (INSTANCE == null) {
            INSTANCE = new SceneParser();
        }

        return INSTANCE;
    }

    /**
     * Parses an XML input file and converts it to a scene object.
     *
     * @param filePath the path to the scene file
     * @return scene object that can be used in the application
     * @throws SceneParserException
     */
    public XmlScene parseFile(final String filePath) throws SceneParserException {
        try {
            final SAXSource source = new SAXSource(xmlReader, new InputSource(new FileInputStream(filePath)));

            return (XmlScene) unmarshaller.unmarshal(source);
        } catch (final JAXBException | FileNotFoundException ex) {
            throw new SceneParserException("Could not parse input stream!", ex);
        }
    }

}
