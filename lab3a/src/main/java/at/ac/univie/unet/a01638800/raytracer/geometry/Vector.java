package at.ac.univie.unet.a01638800.raytracer.geometry;

/**
 * Represents the geometric object "Vector" and its mathematical operations.
 */
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

    /**
     * Returns a new Vector which is this vector scaled by a factor (scalar)
     */
    public Vector scaleByFactor(double factor) {
        return new Vector(this.getX() * factor, this.getY() * factor, this.getZ() * factor);
    }

    /**
     * Returns a new Vector as the result of the vector addition between this vector and another vector.
     */
    public Vector addVector(Vector vector) {
        return new Vector(this.getX() + vector.getX(), this.getY() + vector.getY(), this.getZ() + vector.getZ());
    }

    /**
     * Returns a new Vector as the result of the vector subtraction between this vector and another vector.
     */
    public Vector subtractVector(Vector vector) {
        return new Vector(this.getX() - vector.getX(), this.getY() - vector.getY(), this.getZ() - vector.getZ());
    }

    /**
     * Returns a new point as the result of the vector addition between this vector and a point.
     */
    public Point addPoint(Point point) {
        return new Point(this.getX() + point.getX(), this.getY() + point.getY(), this.getZ() + point.getZ());
    }

    /**
     * Returns a scalar as the result of the dot product between this vector and another vector.
     */
    public double dotProduct(Vector vector) {
        return (this.getX() * vector.getX() + this.getY() * vector.getY() + this.getZ() * vector.getZ());
    }

    /**
     * Returns a new vector as the result of the cross product between this vector and another vector.
     */
    public Vector crossProduct(Vector vector) {
        double x = this.getY() * vector.getZ() - this.getZ() * vector.getY();
        double y = this.getZ() * vector.getX() - this.getX() * vector.getZ();
        double z = this.getX() * vector.getY() - this.getY() * vector.getX();

        return new Vector(x, y, z);
    }

    /**
     * Returns a new vector as the result of the inversion (flipping) of this vector.
     */
    public Vector invert() {
        return new Vector(-this.getX(), -this.getY(), -this.getZ());
    }

    /**
     * Returns a new vector of length one (normalized) based on this vector.
     */
    public Vector normalize() {
        double length = this.getLength();

        if (Double.compare(length, 0.0) == 0) {
            return new Vector(this.getCoordinate());
        }

        return new Vector(this.getX() / length, this.getY() / length, this.getZ() / length);
    }

    /**
     * Returns the magnitude or length of this vector.
     */
    private double getLength() {
        return Math.sqrt((this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ()));
    }
}
