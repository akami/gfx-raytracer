package at.ac.univie.unet.a01638800.raytracer.scene.surfaces;

import at.ac.univie.unet.a01638800.raytracer.geometry.Color;
import at.ac.univie.unet.a01638800.raytracer.geometry.Point;
import at.ac.univie.unet.a01638800.raytracer.scene.intersection.Intersection;
import at.ac.univie.unet.a01638800.raytracer.scene.intersection.IntersectionTest;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.material.Material;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlSurface;

/**
 * Represents a surface and implements an intersection test.
 */
public abstract class Surface implements IntersectionTest {
    private final XmlSurface xmlSurface;
    private Material material;

    protected Surface(final XmlSurface surface) {
        this.xmlSurface = surface;
    }

    public XmlSurface getXmlSurface() {
        return this.xmlSurface;
    }

    public Material getMaterial() {
        return this.material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public abstract Color getColor(Intersection intersection);
}
