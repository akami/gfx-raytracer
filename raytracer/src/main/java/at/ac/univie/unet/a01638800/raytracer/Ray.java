package at.ac.univie.unet.a01638800.raytracer;

import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Point;
import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Vector;

public class Ray {
    private Point origin;
    private Vector direction;
    private double minDistance;
    private double maxDistance;

    public Ray() {
        this.origin = new Point();
        this.direction = new Vector();
    }

    public Ray(Point origin, Vector direction, double minDistance, double maxDistance) {
        this.origin = origin;
        this.direction = direction;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }
}
