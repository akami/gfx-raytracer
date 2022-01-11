package at.ac.univie.unet.a01638800.raytracer.geometricobjects;

public class Vector {
    private Coordinate coordinate;

    public Vector() {
        double[] xyzValues = new double[3];

        xyzValues[0] = 0.0;
        xyzValues[1] = 0.0;
        xyzValues[2] = 0.0;

        this.coordinate = new Coordinate(xyzValues);
    }

    public Vector(double x, double y, double z) {
       double[] xyzValues = new double[3];

       xyzValues[0] = x;
       xyzValues[1] = y;
       xyzValues[2] = z;

       this.coordinate = new Coordinate(xyzValues);
    }

    public Vector(Point origin, double x, double y, double z) {
        double[] xyzValues = new double[3];

        xyzValues[0] = origin.getX() + x;
        xyzValues[1] = origin.getY() + y;
        xyzValues[2] = origin.getZ() + z;

        this.coordinate = new Coordinate(xyzValues);
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

    public double dotProduct(Normal normal) {
        return (this.getX() * normal.getX() + this.getY() * normal.getY() + this.getZ() * normal.getZ());
    }

    public Vector crossProduct(Vector vector) {
        double x = this.getY() * vector.getZ() - this.getZ() * vector.getY();
        double y = this.getZ() * vector.getX() - this.getX() * vector.getZ();
        double z = this.getX() * vector.getY() - this.getY() * vector.getX();

        return new Vector(x, y, z);
    }

    public void invert() {
        this.coordinate.getXyzValues()[0] = -this.getX();
        this.coordinate.getXyzValues()[1] = -this.getY();
        this.coordinate.getXyzValues()[2] = -this.getZ();
    }

    public void normalize() {
        double length = this.getLength();

        this.coordinate.getXyzValues()[0] = this.getX() / length;
        this.coordinate.getXyzValues()[1] = this.getY() / length;
        this.coordinate.getXyzValues()[2] = this.getZ() / length;
    }

    private double getLength() {
        return Math.sqrt((this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ()));
    }
}
