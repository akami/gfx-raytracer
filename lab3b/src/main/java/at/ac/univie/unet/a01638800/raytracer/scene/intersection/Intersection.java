package at.ac.univie.unet.a01638800.raytracer.scene.intersection;

import at.ac.univie.unet.a01638800.raytracer.geometry.Point;
import at.ac.univie.unet.a01638800.raytracer.geometry.Vector;

/**
 * Stores all data that was obtained while completing an intersection test.
 */
public class Intersection {

    private Point intersectionPoint;
    private Point rayOrigin;
    private Point objectCenter;

    private Vector rayDirection;
    private Vector normal;

    private double t;

    public Intersection() {
        // nothing here
    }

    public Intersection(final Point origin, final Point center, final Vector rayDirection, final double t) {
        rayOrigin = origin;
        objectCenter = center;
        this.rayDirection = rayDirection;

        intersectionPoint = new Point(origin, rayDirection, t);

        if (center != null) {
            normal = computeNormal();
        }

        this.t = t;
    }

    public Point getRayOrigin() {
        return rayOrigin;
    }

    public void setRayOrigin(final Point rayOrigin) {
        this.rayOrigin = rayOrigin;
    }

    public Vector getRayDirection() {
        return rayDirection;
    }

    public void setRayDirection(final Vector rayDirection) {
        this.rayDirection = rayDirection;
    }

    public Point getIntersectionPoint() {
        return intersectionPoint;
    }

    public void setIntersectionPoint(final Point intersectionPoint) {
        this.intersectionPoint = intersectionPoint;
    }

    public Point getObjectCenter() {
        return objectCenter;
    }

    public void setObjectCenter(final Point objectCenter) {
        this.objectCenter = objectCenter;
    }

    public Vector getNormal() {
        return normal;
    }

    public void setNormal(final Vector normal) {
        this.normal = normal;
    }

    public double getT() {
        return t;
    }

    public void setT(double t) {
        this.t = t;
    }

    private Vector computeNormal() {
        final Vector normalVector = intersectionPoint.subtractPoint(objectCenter);
        return normalVector.normalize();
    }

}
