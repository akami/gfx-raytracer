package at.ac.univie.unet.a01638800.raytracer.surfaces;

import at.ac.univie.unet.a01638800.raytracer.intersection.Intersection;
import at.ac.univie.unet.a01638800.raytracer.intersection.IntersectionTest;
import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Ray;

public class Surface implements IntersectionTest {
    private at.ac.univie.unet.a01638800.raytracer.scene.Surface parsedSurface;

    public Surface() {
        // nothing here
    }

    public Surface(at.ac.univie.unet.a01638800.raytracer.scene.Surface surface) {
        this.parsedSurface = surface;
    }

    @Override
    public Intersection intersectionDetected(Ray ray) {
        // implementation in subclasses

        return null;
    }
}
