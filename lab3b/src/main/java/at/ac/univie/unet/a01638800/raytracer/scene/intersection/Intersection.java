package at.ac.univie.unet.a01638800.raytracer.scene.intersection;

import at.ac.univie.unet.a01638800.raytracer.geometry.Point;
import at.ac.univie.unet.a01638800.raytracer.geometry.Vector;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.Face;

/**
 * Stores all data that was obtained while completing an intersection test.
 */
public class Intersection {

    private Point intersectionPoint;
    private Point rayOrigin;
    private Point objectCenter;

    private Vector rayDirection;
    private Vector normal;

    private Face face;

    private double t;
    private double barycentricWeightA;
    private double barycentricWeightB;

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

    public double getBarycentricWeightA() {
        return barycentricWeightA;
    }

    public void setBarycentricWeightA(double barycentricWeightA) {
        this.barycentricWeightA = barycentricWeightA;
    }

    public double getBarycentricWeightB() {
        return barycentricWeightB;
    }

    public void setBarycentricWeightB(double barycentricWeightB) {
        this.barycentricWeightB = barycentricWeightB;
    }

    public Face getFace() {
        return face;
    }

    public void setFace(Face face) {
        this.face = face;
    }

    private Vector computeNormal() {
        final Vector normalVector = intersectionPoint.subtractPoint(objectCenter);
        return normalVector.normalize();
    }
}
