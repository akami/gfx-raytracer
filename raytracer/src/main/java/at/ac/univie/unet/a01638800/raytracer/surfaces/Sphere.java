package at.ac.univie.unet.a01638800.raytracer.surfaces;

import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Ray;
import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Point;
import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Vector;
import at.ac.univie.unet.a01638800.raytracer.intersection.Intersection;

public class Sphere extends Surface {
    private at.ac.univie.unet.a01638800.raytracer.scene.Sphere parsedSphere;
    private Point center;
    private double radius;
    private double radius2;

    public Sphere(Point center, double radius) {
        super();
        this.radius = radius;
        this.radius2 = radius * radius;
    }

    public Sphere(at.ac.univie.unet.a01638800.raytracer.scene.Sphere sphere) {
        this.parsedSphere = sphere;

        this.center = new Point(
                Double.parseDouble(sphere.getPosition().getX()),
                Double.parseDouble(sphere.getPosition().getY()),
                Double.parseDouble(sphere.getPosition().getZ())
        );
        this.radius = Double.parseDouble(sphere.getRadius());
        this.radius2 = this.radius * this.radius;
    }

    public at.ac.univie.unet.a01638800.raytracer.scene.Sphere getParsedSphere() {
        return parsedSphere;
    }

    public void setParsedSphere(at.ac.univie.unet.a01638800.raytracer.scene.Sphere parsedSphere) {
        this.parsedSphere = parsedSphere;
    }

    public Point getCenter() {
        return center;
    }

    public void setCenter(Point center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadius2() {
        return radius2;
    }

    public void setRadius2(double radius2) {
        this.radius2 = radius2;
    }

    @Override
    public Intersection intersectionDetected(Ray ray) {
        Point rayOrigin = ray.getOrigin();
        Vector rayDirection = ray.getDirection();

        Point sphereCenter = this.center;
        double sphereRadiusSquared = this.radius2;

        // analytic solution
        Vector raySphereCenterToOrigin = rayOrigin.subtractPoint(sphereCenter);

        double a = rayDirection.dotProduct(rayDirection);
        double b = 2.0 * rayDirection.dotProduct(raySphereCenterToOrigin);
        double c = raySphereCenterToOrigin.dotProduct(raySphereCenterToOrigin) - sphereRadiusSquared;

        double t1 = 0.0;
        double t2 = 0.0;

        double[] solution = generalSolutionFormula(a, b, c, t1, t2);

        if(solution == null) {
            return null;
        } else if(solution[0] < 0.0){
            if(solution[1] >= 0.0) {
                return new Intersection(rayOrigin, this.center, rayDirection, solution[1]);
            }
        } else if(solution[1] < 0.0){
            if(solution[0] >= 0.0) {
                return new Intersection(rayOrigin, this.center, rayDirection, solution[0]);
            }
        } else {
            return new Intersection(rayOrigin, this.center, rayDirection, Math.min(solution[0], solution[1]));
        }

        return null;
    }

    // https://www.scratchapixel.com/code.php?id=10&origin=/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes
    private double[] generalSolutionFormula(double a, double b, double c, double t1, double t2) {
        double[] solution = new double[2];

        double discriminant = b * b - 4.0 * a * c;

        if (discriminant < 0.0) { // no intersection was detected
           return null;
        } else if (discriminant == 0.0) { // one intersection was detected
            t1 = - 0.5 * b / a;
            t2 = t1;
        } else if (discriminant > 0.0 ) { // multiple intersections were detected
            double q = (b > 0.0) ?
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
