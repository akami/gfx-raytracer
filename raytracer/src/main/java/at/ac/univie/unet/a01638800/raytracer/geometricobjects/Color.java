package at.ac.univie.unet.a01638800.raytracer.geometricobjects;

/**
 * Represents the color components r, g and b.
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
        this.rgbValues = new double[]{0.0, 0.0, 0.0};
    }

    public Color(double r, double g, double b) {
        this.rgbValues = new double[]{inRange(r), inRange(g), inRange(b)};
    }

    public double getR() {
        return this.rgbValues[0];
    }

    public void setR(double r) {
        this.rgbValues[0] = inRange(r);
    }

    public double getG() {
        return this.rgbValues[1];
    }

    public void setG(double g) {
        this.rgbValues[1] = inRange(g);
    }

    public double getB() {
        return this.rgbValues[2];
    }

    public void setB(double b) {
        this.rgbValues[2] = inRange(b);
    }

    public Color multiplyByFactor(double factor) {
        return new Color(inRange(this.getR() * factor), inRange(this.getG() * factor), inRange(this.getB() * factor));
    }

    public Color addColor(Color color) {
        return new Color(inRange(this.getR() + color.getR()), inRange(this.getG() + color.getG()), inRange(this.getB() + color.getB()));
    }

    private double inRange(double value) {
        return Math.max(Math.min(value, 1.0), 0.0);
    }
}
