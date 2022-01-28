package at.ac.univie.unet.a01638800.raytracer.scene.surfaces;

import at.ac.univie.unet.a01638800.raytracer.geometry.Point;
import at.ac.univie.unet.a01638800.raytracer.geometry.Ray;
import at.ac.univie.unet.a01638800.raytracer.geometry.Vector;
import at.ac.univie.unet.a01638800.raytracer.scene.intersection.Intersection;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlSurface;

public class Mesh extends Surface {
    /**
     * Acne-Bias offset.
     */
    private static final double EPSILON_OFFSET = 0.00000001;

    private final Face[] faces;

    public Mesh(final XmlSurface xmlSurface, final Face[] faces) {
        super(xmlSurface);

        this.faces = faces;
    }

    /**
     * The following intersection algorithm (Möller-Trumbore) and its implementation in the used methods were obtained from:
     * https://www.scratchapixel.com/lessons/3d-basic-rendering/ray-tracing-rendering-a-triangle/moller-trumbore-ray-triangle-intersection
     * https://www.scratchapixel.com/code.php?id=9&origin=/lessons/3d-basic-rendering/ray-tracing-rendering-a-triangle
     *
     * @param ray Ray to test intersection
     * @return intersection data object
     */
    @Override
    public Intersection intersectionDetected(final Ray ray) {
        Intersection intersection = null;

        for (final Face face : faces) {
            Intersection nextIntersection = this.detectFaceIntersection(ray, face);

            if (intersection == null && nextIntersection != null) {
                intersection = nextIntersection;
            } else if (intersection != null && nextIntersection != null) {
                if (nextIntersection.getT() < intersection.getT()) {
                    intersection = nextIntersection;
                }
            }
        }

        return intersection;
    }

    private Intersection detectFaceIntersection(Ray ray, Face face) {
        Point rayOrigin = ray.getOrigin();
        Vector rayDirection = ray.getDirection();

        Point vertexPosition1 = face.getVertices()[0].getPosition();
        Point vertexPosition2 = face.getVertices()[1].getPosition();
        Point vertexPosition3 = face.getVertices()[2].getPosition();

        // distance to intersection
        double t = 0;

        // barycentric coordinate weights
        double a = 0;
        double b = 0;

        // set up system variables
        Vector e1 = vertexPosition2.subtractPoint(vertexPosition1);
        Vector e2 = vertexPosition3.subtractPoint(vertexPosition1);
        Vector pVector = rayDirection.crossProduct(e2);
        double det = e1.dotProduct(pVector);

        // if determinant is negative or close to zero, triangle is backfacing or ray misses triangle
        if (!(det < EPSILON_OFFSET)) {
            double inverseDet = 1D / det;

            // calculate weight a
            Vector tVector = rayOrigin.subtractPoint(vertexPosition1);
            a = tVector.dotProduct(pVector) * inverseDet;

            if (a < 0 || a > 1) {
                return null;
            }

            // calculate weight b
            Vector qVector = tVector.crossProduct(e1);
            b = rayDirection.dotProduct(qVector) * inverseDet;

            if (b < 0 || a + b > 1) {
                return null;
            }

            t = e2.dotProduct(qVector) * inverseDet;
            if (t > 0) {
                Intersection intersection = new Intersection(rayOrigin, null, rayDirection, t);
                intersection.setNormal(face.getVertices()[0].getNormal());

                return intersection;
            }
        }

        return null;
    }
}
