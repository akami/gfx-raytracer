package at.ac.univie.unet.a01638800.raytracer.scene.surfaces;

import at.ac.univie.unet.a01638800.raytracer.geometry.Point;
import at.ac.univie.unet.a01638800.raytracer.geometry.Ray;
import at.ac.univie.unet.a01638800.raytracer.geometry.Vector;
import at.ac.univie.unet.a01638800.raytracer.scene.intersection.Intersection;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlSphere;

/**
 * Represents a Surface of type Sphere. Extending the surface class means implementing an intersection test algorithm
 * to determine whether a ray intersects with the surface or not.
 */
public class Sphere extends Surface {

    /**
     * Acne-Bias offset.
     */
    private static final double EPSILON_OFFSET = 0.00000001;

    private Point center;
    private double radius;
    private double radius2;

    public Sphere(final XmlSphere sphere) {
        super(sphere);

        center = new Point(
                Double.parseDouble(sphere.getPosition().getX()),
                Double.parseDouble(sphere.getPosition().getY()),
                Double.parseDouble(sphere.getPosition().getZ())
        );
        radius = Double.parseDouble(sphere.getRadius());
        radius2 = radius * radius;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(final Point center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(final double radius) {
        this.radius = radius;
    }

    public double getRadius2() {
        return radius2;
    }

    public void setRadius2(final double radius2) {
        this.radius2 = radius2;
    }

    /**
     * The following intersection algorithm including the private method generalSolutionFormula() were obtained from:
     * https://www.scratchapixel.com/code.php?id=10&origin=/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes
     *
     * @param ray Ray to test intersection
     * @return intersection data object
     */
    @Override
    public Intersection intersectionDetected(final Ray ray) {
        final Point rayOrigin = ray.getOrigin();
        final Vector rayDirection = ray.getDirection();

        final Point sphereCenter = center;
        final double sphereRadiusSquared = radius2;

        // analytic solution
        final Vector raySphereCenterToOrigin = rayOrigin.subtractPoint(sphereCenter);

        final double a = rayDirection.dotProduct(rayDirection);
        final double b = 2.0 * rayDirection.dotProduct(raySphereCenterToOrigin);
        final double c = raySphereCenterToOrigin.dotProduct(raySphereCenterToOrigin) - sphereRadiusSquared;

        final double t1 = 0.0;
        final double t2 = 0.0;

        // get t (the distance) from ray origin to intersection point
        final double[] solution = generalSolutionFormula(a, b, c, t1, t2);

        // only consider smaller t (= the nearest intersection) and positive t
        if (solution == null) {
            return null;
        } else if (solution[0] < EPSILON_OFFSET) {
            if (solution[1] >= EPSILON_OFFSET) {
                return new Intersection(rayOrigin, center, rayDirection, solution[1]);
            }
        } else if (solution[1] < EPSILON_OFFSET) {
            if (solution[0] >= EPSILON_OFFSET) {
                return new Intersection(rayOrigin, center, rayDirection, solution[0]);
            }
        } else {
            return new Intersection(rayOrigin, center, rayDirection, Math.min(solution[0], solution[1]));
        }

        return null;
    }

    private double[] generalSolutionFormula(final double a, final double b, final double c, double t1, double t2) {
        final double[] solution = new double[2];

        final double discriminant = b * b - 4.0 * a * c;

        // no intersection was detected
        if (discriminant < 0.0) {
            return null;
        } else if (discriminant == 0.0) { // one intersection was detected
            t1 = -0.5 * b / a;
            t2 = t1;
        } else if (discriminant > 0.0) { // multiple intersections were detected
            final double q = (b > 0.0) ?
                    -0.5 * (b + Math.sqrt(discriminant)) :
                    -0.5 * (b - Math.sqrt(discriminant));

            t1 = q / a;
            t2 = c / q;
        }

        solution[0] = t1;
        solution[1] = t2;

        return solution;
    }

}
