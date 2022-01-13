package at.ac.univie.unet.a01638800.raytracer.raytracedscene.surfaces;

import at.ac.univie.unet.a01638800.raytracer.geometry.Ray;
import at.ac.univie.unet.a01638800.raytracer.raytracedscene.intersection.Intersection;
import at.ac.univie.unet.a01638800.raytracer.raytracedscene.intersection.IntersectionTest;

/**
 * Represents a surface and implements an intersection test.
 */
public class Surface implements IntersectionTest {
    private at.ac.univie.unet.a01638800.raytracer.scene.Surface parsedSurface;

    public Surface() {
        // nothing here
    }

    public Surface(at.ac.univie.unet.a01638800.raytracer.scene.Surface surface) {
        this.parsedSurface = surface;
    }

    /**
     * Checks whether a ray intersects with the surface. The data of the intersection test including the intersection
     * point are stored in an intersection data object.
     *
     * @param ray Ray to test
     * @return intersection data object
     */
    @Override
    public Intersection intersectionDetected(Ray ray) {
        // implementation in subclasses

        return null;
    }
}
