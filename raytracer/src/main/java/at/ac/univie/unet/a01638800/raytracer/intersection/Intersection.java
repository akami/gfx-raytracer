package at.ac.univie.unet.a01638800.raytracer.intersection;

import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Normal;
import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Point;
import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Vector;

public class Intersection {
    private Point intersectionPoint;
    private Point rayOrigin;
    private Point objectCenter;

    private Vector distance;
    private Normal normal;

    public Intersection() {
        // nothing here
    }

    public Intersection(Point origin, Point center, Vector distance, double t) {
        this.rayOrigin = origin;
        this.objectCenter = center;
        this.distance = distance;

        this.intersectionPoint = new Point(origin, distance, t);

        this.normal = this.computeNormal();
    }

    public Point getRayOrigin() {
        return rayOrigin;
    }

    public void setRayOrigin(Point rayOrigin) {
        this.rayOrigin = rayOrigin;
    }

    public Vector getDistance() {
        return distance;
    }

    public void setDistance(Vector distance) {
        this.distance = distance;
    }

    public Normal getNormal() {
        return normal;
    }

    public void setNormal(Normal normal) {
        this.normal = normal;
    }

    private Normal computeNormal() {
        Vector vector = this.intersectionPoint.subtractPoint(this.objectCenter);

        return new Normal(vector);
    }
}
