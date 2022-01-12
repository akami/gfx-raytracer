package at.ac.univie.unet.a01638800.raytracer.geometricobjects;

public class Vector {
    private Coordinate coordinate;

    public Vector() {
        this.coordinate = new Coordinate(new double[]{0.0, 0.0, 0.0});
    }

    public Vector(double x, double y, double z) {
       this.coordinate = new Coordinate(new double[]{x, y, z});
    }

    public Vector(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public double getX() {
        return this.coordinate.getXyzValues()[0];
    }

    public void setX(double x) {
        this.coordinate.getXyzValues()[0] = x;
    }

    public double getY() {
        return this.coordinate.getXyzValues()[1];
    }

    public void setY(double y) {
        this.coordinate.getXyzValues()[1] = y;
    }

    public double getZ() {
        return this.coordinate.getXyzValues()[2];
    }

    public void setZ(double z) {
        this.coordinate.getXyzValues()[2] = z;
    }

    public Vector scaleByFactor(double factor) {
        return new Vector(this.getX() * factor, this.getY() * factor, this.getZ() * factor);
    }

    public Vector addVector(Vector vector) {
        return new Vector(this.getX() + vector.getX(), this.getY() + vector.getY(), this.getZ() + vector.getZ());
    }

    public Vector subtractVector(Vector vector) {
        return new Vector(this.getX() - vector.getX(), this.getY() - vector.getY(), this.getZ() - vector.getZ());
    }

    public Point addPoint(Point point) {
        return new Point (this.getX() + point.getX(), this.getY() + point.getY(), this.getZ() + point.getZ());
    }

    public double dotProduct(Vector vector) {
        return (this.getX() * vector.getX() + this.getY() * vector.getY() + this.getZ() * vector.getZ());
    }

    public Vector crossProduct(Vector vector) {
        double x = this.getY() * vector.getZ() - this.getZ() * vector.getY();
        double y = this.getZ() * vector.getX() - this.getX() * vector.getZ();
        double z = this.getX() * vector.getY() - this.getY() * vector.getX();

        return new Vector(x, y, z);
    }

    public Vector invert() {
        return new Vector(-this.getX(), -this.getY(), -this.getZ());
    }

    public Vector normalize() {
        double length = this.getLength();

        return new Vector(this.getX() / length, this.getY() / length, this.getZ() / length);
    }

    private double getLength() {
        return Math.sqrt((this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ()));
    }
}
