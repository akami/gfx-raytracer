package at.ac.univie.unet.a01638800.raytracer.geometry;

/**
 * Represents the geometric object ray, which has an origin, a direction, as well as magnitude. The magnitude here
 * is represented by the min and max distance of the ray.
 */
public class Ray {

    public static final double MIN_DISTANCE = 0.1;
    public static final double MAX_DISTANCE = 100D;

    private Point origin;
    private Vector direction;
    private double minDistance;
    private double maxDistance;

    public Ray() {
        origin = new Point();
        direction = new Vector();
        minDistance = MIN_DISTANCE;
        maxDistance = MAX_DISTANCE;
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
