package at.ac.univie.unet.a01638800.raytracer.scene.surfaces;

import at.ac.univie.unet.a01638800.raytracer.geometry.Color;
import at.ac.univie.unet.a01638800.raytracer.geometry.Point;
import at.ac.univie.unet.a01638800.raytracer.geometry.Ray;
import at.ac.univie.unet.a01638800.raytracer.geometry.Vector;
import at.ac.univie.unet.a01638800.raytracer.geometry.Vertex;
import at.ac.univie.unet.a01638800.raytracer.scene.intersection.Intersection;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.transformation.Transformation;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlSurface;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Class representing a mesh
 */
public class Mesh extends Surface {

    /**
     * Acne-Bias offset.
     */
    private static final double EPSILON_OFFSET = 0.0000001;

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
            final Intersection nextIntersection = detectFaceIntersection(ray, face);

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

    private Intersection detectFaceIntersection(final Ray ray, final Face face) {
        Vector originalRayDirection = ray.getDirection();
        Point originalRayOrigin = ray.getOrigin();

        Ray transformedRay;
        boolean transformed = false;

        Transformation transformation = getTransformation();
        if (transformation != null) {
            Pair<Ray, Boolean> transformedRayAndTransformed = transformation.transform(ray);

            transformedRay = transformedRayAndTransformed.getLeft();
            transformed = transformedRayAndTransformed.getRight();
        } else {
            transformedRay = ray;
        }

        final Point rayOrigin = transformedRay.getOrigin();
        final Vector rayDirection = transformedRay.getDirection();

        final Point vertexPosition0 = face.getVertices()[0].getPosition();
        final Point vertexPosition1 = face.getVertices()[1].getPosition();
        final Point vertexPosition2 = face.getVertices()[2].getPosition();

        // distance to intersection
        double t = 0;

        // barycentric coordinate weights
        double barycentricWeightA = 0D;
        double barycentricWeightB = 0D;

        // set up system variables
        final Vector e1 = vertexPosition1.subtractPoint(vertexPosition0);
        final Vector e2 = vertexPosition2.subtractPoint(vertexPosition0);

        final Vector pVector = rayDirection.crossProduct(e2);

        final double det = e1.dotProduct(pVector);

        // if determinant is negative or close to zero, triangle is back facing or ray misses triangle
        if (det > -EPSILON_OFFSET && det < EPSILON_OFFSET) {
            return null;
        }

        final double inverseDet = 1D / det;

        // calculate weight a
        final Vector tVector = rayOrigin.subtractPoint(vertexPosition0);
        barycentricWeightA = tVector.dotProduct(pVector) * inverseDet;

        if (barycentricWeightA < 0D || barycentricWeightA > 1D) {
            return null;
        }

        // calculate weight b
        final Vector qVector = tVector.crossProduct(e1);
        barycentricWeightB = rayDirection.dotProduct(qVector) * inverseDet;

        if (barycentricWeightB < 0D || barycentricWeightA + barycentricWeightB > 1D) {
            return null;
        }

        t = e2.dotProduct(qVector) * inverseDet;

        if (t > EPSILON_OFFSET) {
            final Intersection intersection = new Intersection(rayOrigin, null, rayDirection, t);
            intersection.setNormal(face.getVertices()[0].getNormal());
            intersection.setFace(face);
            intersection.setBarycentricWeightA(barycentricWeightA);
            intersection.setBarycentricWeightB(barycentricWeightB);

            if(transformed) {
                Vector transformedNormal = transformation.transform(intersection.getNormal());
                intersection.setNormal(transformedNormal);
            }

            return intersection;
        }

        return null;
    }

    @Override
    public Color getColor(final Intersection intersection) {
        final Vertex[] vertices = intersection.getFace().getVertices();

        final Point texture0 = vertices[0].getTexture();
        final Point texture1 = vertices[1].getTexture();
        final Point texture2 = vertices[2].getTexture();

        final double barycentricWeightA = intersection.getBarycentricWeightA();
        final double barycentricWeightB = intersection.getBarycentricWeightB();

        final double u = (1 - barycentricWeightA - barycentricWeightB) * texture0.getX()
                + barycentricWeightA * texture1.getX()
                + barycentricWeightB * texture2.getX();

        final double v = (1 - barycentricWeightA - barycentricWeightB) * texture0.getY()
                + barycentricWeightA * texture1.getY()
                + barycentricWeightB * texture2.getY();

        return getMaterial().getColor(u, v);
    }

}
