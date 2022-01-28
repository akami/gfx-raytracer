package at.ac.univie.unet.a01638800.raytracer.geometry;

/**
 * Represents coordinates as an array of doubles [x, y, z].
 */
public class Coordinate {

    private double[] xyzValues;

    public Coordinate() {
        xyzValues = new double[3];
    }

    public Coordinate(final double[] xyzValues) {
        this.xyzValues = xyzValues;
    }

    public double[] getXyzValues() {
        return xyzValues;
    }

    public void setXyzValues(final double[] xyzValues) {
        this.xyzValues = xyzValues;
    }

}
