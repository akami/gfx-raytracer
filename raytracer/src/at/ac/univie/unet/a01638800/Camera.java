package at.ac.univie.unet.a01638800;

public class Camera {
    private Coordinate position;
    private Coordinate lookAt;
    private Coordinate up;
    private float horizontalFOV;
    private Resolution resolution;
    private int maxBounces;

    public Camera(Coordinate position, Coordinate lookAt, Coordinate up, float horizontalFOV, Resolution resolution, int maxBounces) {
        this.position = position;
        this.lookAt = lookAt;
        this.up = up;
        this.horizontalFOV = horizontalFOV;
        this.resolution = resolution;
        this.maxBounces = maxBounces;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public Coordinate getLookAt() {
        return lookAt;
    }

    public void setLookAt(Coordinate lookAt) {
        this.lookAt = lookAt;
    }

    public Coordinate getUp() {
        return up;
    }

    public void setUp(Coordinate up) {
        this.up = up;
    }

    public float getHorizontalFOV() {
        return horizontalFOV;
    }

    public void setHorizontalFOV(float horizontalFOV) {
        this.horizontalFOV = horizontalFOV;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    public int getMaxBounces() {
        return maxBounces;
    }

    public void setMaxBounces(int maxBounces) {
        this.maxBounces = maxBounces;
    }
}
