package at.ac.univie.unet.a01638800.raytracer.geometricobjects;

public class Normal {
    private Coordinate coordinate;

    public Normal() {
        // empty
    }

    public Normal(double x, double y, double z) {
        double[] xyzValues = new double[3];

        xyzValues[0] = x;
        xyzValues[1] = y;
        xyzValues[2] = z;

        this.coordinate = new Coordinate(xyzValues);
    }

    public Normal(Vector vector) {
        Vector normal = new Vector(vector.getX(), vector.getY(), vector.getZ());
        normal.normalize();

        double[] xyzValues = new double[3];

        xyzValues[0] = normal.getX();
        xyzValues[1] = normal.getY();
        xyzValues[2] = normal.getZ();

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
}
