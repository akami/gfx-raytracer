package at.ac.univie.unet.a01638800.raytracer.geometricobjects;

public class Point {
    private Coordinate coordinate;

    public Point() {
        this.coordinate = new Coordinate();

        double[] xyzValues = new double[3];

        xyzValues[0] = 0.;
        xyzValues[1] = 0.;
        xyzValues[2] = 0.;

        this.coordinate.setXyzValues(xyzValues);
    }

    public Point(double x, double y, double z) {
        double[] xyzValues = new double[3];

        xyzValues[0] = x;
        xyzValues[1] = y;
        xyzValues[2] = z;

        this.coordinate = new Coordinate(xyzValues);
    }

    public Point(Point origin, Vector direction, double t) {
        double[] xyzValues = new double[3];

        xyzValues[0] = origin.getX() + t * direction.getX();
        xyzValues[1] = origin.getY() + t * direction.getY();
        xyzValues[2] = origin.getZ() + t * direction.getZ();

        this.coordinate = new Coordinate(xyzValues);
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

    public Point addVector(Vector vector) {
        return new Point(this.getX() + vector.getX(), this.getY() + vector.getY(), this.getZ() + vector.getZ());
    }

    public Vector subtractPoint(Point point) {
        return new Vector(this.getX() - point.getX(), this.getY() - point.getY(), this.getZ() - point.getZ());
    }

    public Vector addPoint(Point point) {
        return new Vector(this.getX() + point.getX(), this.getY() + point.getY(), this.getZ() + point.getZ());
    }
}
