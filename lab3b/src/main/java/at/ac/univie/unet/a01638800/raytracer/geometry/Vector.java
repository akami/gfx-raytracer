package at.ac.univie.unet.a01638800.raytracer.geometry;

/**
 * Represents the geometric object "Vector" and its mathematical operations.
 */
public class Vector {

    private Coordinate coordinate;

    public Vector() {
        coordinate = new Coordinate(new double[]{0.0, 0.0, 0.0});
    }

    public Vector(final double x, final double y, final double z) {
        coordinate = new Coordinate(new double[]{x, y, z});
    }

    public Vector(final Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(final Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public double getX() {
        return coordinate.getXyzValues()[0];
    }

    public void setX(final double x) {
        coordinate.getXyzValues()[0] = x;
    }

    public double getY() {
        return coordinate.getXyzValues()[1];
    }

    public void setY(final double y) {
        coordinate.getXyzValues()[1] = y;
    }

    public double getZ() {
        return coordinate.getXyzValues()[2];
    }

    public void setZ(final double z) {
        coordinate.getXyzValues()[2] = z;
    }

    /**
     * Returns a new Vector which is this vector scaled by a factor (scalar)
     */
    public Vector scaleByFactor(final double factor) {
        return new Vector(getX() * factor, getY() * factor, getZ() * factor);
    }

    /**
     * Returns a new Vector as the result of the vector addition between this vector and another vector.
     */
    public Vector addVector(final Vector vector) {
        return new Vector(getX() + vector.getX(), getY() + vector.getY(), getZ() + vector.getZ());
    }

    /**
     * Returns a new Vector as the result of the vector subtraction between this vector and another vector.
     */
    public Vector subtractVector(final Vector vector) {
        return new Vector(getX() - vector.getX(), getY() - vector.getY(), getZ() - vector.getZ());
    }

    /**
     * Returns a new point as the result of the vector addition between this vector and a point.
     */
    public Point addPoint(final Point point) {
        return new Point(getX() + point.getX(), getY() + point.getY(), getZ() + point.getZ());
    }

    /**
     * Returns a scalar as the result of the dot product between this vector and another vector.
     */
    public double dotProduct(final Vector vector) {
        return (getX() * vector.getX() + getY() * vector.getY() + getZ() * vector.getZ());
    }

    /**
     * Returns a new vector as the result of the cross product between this vector and another vector.
     */
    public Vector crossProduct(final Vector vector) {
        final double x = getY() * vector.getZ() - getZ() * vector.getY();
        final double y = getZ() * vector.getX() - getX() * vector.getZ();
        final double z = getX() * vector.getY() - getY() * vector.getX();

        return new Vector(x, y, z);
    }

    /**
     * Returns a new vector as the result of the inversion (flipping) of this vector.
     */
    public Vector invert() {
        return new Vector(-getX(), -getY(), -getZ());
    }

    /**
     * Returns a new vector of length one (normalized) based on this vector.
     */
    public Vector normalize() {
        final double length = getLength();

        if (Double.compare(length, 0.0) == 0) {
            return new Vector(getCoordinate());
        }

        return new Vector(getX() / length, getY() / length, getZ() / length);
    }

    /**
     * Returns the magnitude or length of this vector.
     */
    public double getLength() {
        return Math.sqrt((getX() * getX() + getY() * getY() + getZ() * getZ()));
    }
}
