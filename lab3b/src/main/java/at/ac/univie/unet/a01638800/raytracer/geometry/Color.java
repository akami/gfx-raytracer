package at.ac.univie.unet.a01638800.raytracer.geometry;

/**
 * Represents the color components r, g and b as an array of doubles [r, g, b]
 */
public class Color {

    /**
     * Array of doubles that represent the values for red, green and blue
     */
    private double[] rgbValues;

    /**
     * Creates a black color object.
     */
    public Color() {
        rgbValues = new double[]{0.0, 0.0, 0.0};
    }

    public Color(final double r, final double g, final double b) {
        rgbValues = new double[]{inRange(r), inRange(g), inRange(b)};
    }

    public double[] getRgbValues() {
        return rgbValues;
    }

    public void setRgbValues(final double[] rgbValues) {
        this.rgbValues = rgbValues;
    }

    public double getR() {
        return rgbValues[0];
    }

    public void setR(final double r) {
        rgbValues[0] = inRange(r);
    }

    public double getG() {
        return rgbValues[1];
    }

    public void setG(final double g) {
        rgbValues[1] = inRange(g);
    }

    public double getB() {
        return rgbValues[2];
    }

    public void setB(final double b) {
        rgbValues[2] = inRange(b);
    }

    public Color multiplyByFactor(final double factor) {
        return new Color(inRange(getR() * factor), inRange(getG() * factor), inRange(getB() * factor));
    }

    public Color addColor(final Color color) {
        return new Color(inRange(getR() + color.getR()), inRange(getG() + color.getG()), inRange(getB() + color.getB()));
    }

    private double inRange(final double value) {
        return Math.max(Math.min(value, 1.0), 0.0);
    }

}
