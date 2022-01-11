package at.ac.univie.unet.a01638800.raytracer.intersection;

import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Normal;
import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Point;
import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Vector;

public class Intersection {
    private Point intersectionPoint;
    private Point rayOrigin;
    private Point objectCenter;

    private Vector rayDirection;
    private Normal normal;

    public Intersection() {
        // nothing here
    }

    public Intersection(Point origin, Point center, Vector rayDirection, double t) {
        this.rayOrigin = origin;
        this.objectCenter = center;
        this.rayDirection = rayDirection;

        this.intersectionPoint = new Point(origin, rayDirection, t);

        this.normal = this.computeNormal();
    }

    public Point getRayOrigin() {
        return rayOrigin;
    }

    public void setRayOrigin(Point rayOrigin) {
        this.rayOrigin = rayOrigin;
    }

    public Vector getRayDirection() {
        return rayDirection;
    }

    public void setRayDirection(Vector rayDirection) {
        this.rayDirection = rayDirection;
    }

    public Point getIntersectionPoint() {
        return intersectionPoint;
    }

    public void setIntersectionPoint(Point intersectionPoint) {
        this.intersectionPoint = intersectionPoint;
    }

    public Point getObjectCenter() {
        return objectCenter;
    }

    public void setObjectCenter(Point objectCenter) {
        this.objectCenter = objectCenter;
    }

    public Normal getNormal() {
        return normal;
    }

    public void setNormal(Normal normal) {
        this.normal = normal;
    }

    private Normal computeNormal() {
        return new Normal(this.intersectionPoint.subtractPoint(this.objectCenter));
    }
}
