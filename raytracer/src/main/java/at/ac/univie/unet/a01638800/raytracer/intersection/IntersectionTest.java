package at.ac.univie.unet.a01638800.raytracer.intersection;

import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Ray;

public interface IntersectionTest {
    Intersection intersectionDetected(Ray ray);
}
