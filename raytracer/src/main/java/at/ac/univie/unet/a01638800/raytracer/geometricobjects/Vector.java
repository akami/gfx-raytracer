package at.ac.univie.unet.a01638800.raytracer.geometricobjects;

public class Vector {
    private double x;
    private double y;
    private double z;
    private double length;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.length = Math.sqrt((x * x + y * y + z * z));
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

    public Vector scaleByFactor(double factor) {
        return new Vector(this.x * factor, this.y * factor, this.z * factor);
    }

    public Vector addVector(Vector vector) {
        return new Vector(this.x + vector.x, this.y + vector.y, this.z + vector.z);
    }

    public Vector subtractVector(Vector vector) {
        return new Vector(this.x - vector.x, this.y - vector.y, this.z - vector.z);
    }

    public Point addPoint(Point point) {
        return new Point (this.x + point.getX(), this.y + point.getY(), this.z + point.getZ());
    }

    public double dotProduct(Vector vector) {
        return (this.x * vector.x + this.y * vector.y + this.z * vector.z);
    }

    public Vector crossProduct(Vector vector) {
        double x = this.y * vector.z - this.z * vector.y;
        double y = this.z * vector.x - this.x * vector.z;
        double z = this.x * vector.y - this.y * vector.x;

        return new Vector(x, y, z);
    }
}
