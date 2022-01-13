package at.ac.univie.unet.a01638800.raytracer.raytracedscene.intersection;

import at.ac.univie.unet.a01638800.raytracer.geometry.Ray;

/**
 * Classes implementing this interface are able to complete an intersection test with a Ray.
 *
 * @see Ray
 */
public interface IntersectionTest {
    Intersection intersectionDetected(Ray ray);
}
