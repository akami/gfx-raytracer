package at.ac.univie.unet.a01638800.raytracer.scene.surfaces;

import at.ac.univie.unet.a01638800.raytracer.scene.intersection.IntersectionTest;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlSurface;

/**
 * Represents a surface and implements an intersection test.
 */
public abstract class Surface implements IntersectionTest {

    private final XmlSurface xmlSurface;

    protected Surface(final XmlSurface surface) {
        xmlSurface = surface;
    }

    public XmlSurface getXmlSurface() {
        return xmlSurface;
    }

}
