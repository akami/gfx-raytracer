package at.ac.univie.unet.a01638800.raytracer.geometry;

/**
 * Represents the geometric object "Point" as a coordinate. Mathematical operations that can be done with Points
 * are declared here.
 *
 * @see Coordinate
 */
public class Point {

    private final Coordinate coordinate;

    public Point() {
        coordinate = new Coordinate(new double[]{0.0, 0.0, 0.0});
    }

    public Point(final double x, final double y, final double z) {
        coordinate = new Coordinate(new double[]{x, y, z});
    }

    public Point(final Point origin, final Vector direction, final double t) {
        final double[] xyzValues = new double[3];

        xyzValues[0] = origin.getX() + t * direction.getX();
        xyzValues[1] = origin.getY() + t * direction.getY();
        xyzValues[2] = origin.getZ() + t * direction.getZ();

        coordinate = new Coordinate(xyzValues);
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
     * Adds a Vector to the Point. The result of this operation is a new Point in the direction of the Vector.
     *
     * @param vector geometric object vector
     * @return a new point
     */
    public Point addVector(final Vector vector) {
        return new Point(getX() + vector.getX(), getY() + vector.getY(), getZ() + vector.getZ());
    }

    /**
     * Subtracts a point of the point. The result of this operation is a new Vector between the two points in the
     * direction of this point.
     *
     * @param point geometric object point
     * @return a new point object
     */
    public Vector subtractPoint(final Point point) {
        return new Vector(getX() - point.getX(), getY() - point.getY(), getZ() - point.getZ());
    }

    /**
     * Returns a new Vector as the result of the addition of another point with this point.
     */
    public Vector addPoint(final Point point) {
        return new Vector(getX() + point.getX(), getY() + point.getY(), getZ() + point.getZ());
    }

}
