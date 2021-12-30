package at.ac.univie.unet.a01638800.raytracer.geometricobjects;

public class Point {
    private double x;
    private double y;
    private double z;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Point addVector(Vector vector) {
        return new Point(this.x + vector.getX(), this.y + vector.getY(), this.z + vector.getZ());
    }

    public Vector subtractPoint(Point point) {
        return new Vector(this.x - point.x, this.y + point.y, this.z - point.z);
    }
}
