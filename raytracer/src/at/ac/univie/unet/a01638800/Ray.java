package at.ac.univie.unet.a01638800;

public class Ray {
    private Point origin;
    private Vector direction;
    private float minDistance;
    private float maxDistance;

    public Ray() {
        // empty
    }

    public Ray(Point origin, Vector direction, float minDistance, float maxDistance) {
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

    public float getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(float minDistance) {
        this.minDistance = minDistance;
    }

    public float getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(float maxDistance) {
        this.maxDistance = maxDistance;
    }
}
