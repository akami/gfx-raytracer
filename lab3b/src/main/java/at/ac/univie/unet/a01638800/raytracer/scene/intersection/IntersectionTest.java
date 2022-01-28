package at.ac.univie.unet.a01638800.raytracer.scene.intersection;

import at.ac.univie.unet.a01638800.raytracer.geometry.Ray;

/**
 * Classes implementing this interface are able to complete an intersection test with a Ray.
 *
 * @see Ray
 */
public interface IntersectionTest {

    /**
     * Checks whether a ray intersects with the surface. The data of the intersection test including the intersection
     * point are stored in an intersection data object.
     *
     * @param ray Ray to test
     * @return intersection data object
     */
    Intersection intersectionDetected(Ray ray);

}
