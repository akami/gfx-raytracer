package at.ac.univie.unet.a01638800.raytracer.geometry;

/**
 * Represents the geometric object ray, which has an origin, a direction, as well as magnitude. The magnitude here
 * is represented by the min and max distance of the ray.
 */
public class Ray {

    private Point origin;
    private Vector direction;
    private double minDistance;
    private double maxDistance;

    public Ray() {
        origin = new Point();
        direction = new Vector();
        minDistance = 0.1;
        maxDistance = 100;
    }

    public Ray(final Point origin, final Vector direction, final double minDistance, final double maxDistance) {
        this.origin = origin;
        this.direction = direction;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
    }

    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(final Point origin) {
        this.origin = origin;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(final Vector direction) {
        this.direction = direction;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(final double minDistance) {
        this.minDistance = minDistance;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(final double maxDistance) {
        this.maxDistance = maxDistance;
    }

}
