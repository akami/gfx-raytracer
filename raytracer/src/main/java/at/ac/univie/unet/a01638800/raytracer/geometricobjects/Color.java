package at.ac.univie.unet.a01638800.raytracer.geometricobjects;

/**
 * Represents the color components r, g and b.
 */
public class Color {
    /** Array of doubles that represent the values for red, green and blue */
    private double[] rgbValues;

    /**
     * Creates a black color object.
     */
    public Color(){
        double[] rgbValues = new double[3];

        rgbValues[0] = 0.0;
        rgbValues[1] = 0.0;
        rgbValues[2] = 0.0;

        this.rgbValues = rgbValues;
    }

    public Color(double r, double g, double b) {
        double[] rgbValues = new double[3];

        rgbValues[0] = r;
        rgbValues[1] = g;
        rgbValues[2] = b;

        this.rgbValues = rgbValues;
    }

    public double getR() {
        return this.rgbValues[0];
    }

    public void setR(double r) {
        this.rgbValues[0] = r;
    }

    public double getG() {
        return this.rgbValues[1];
    }

    public void setG(double g) {
        this.rgbValues[1] = g;
    }

    public double getB() {
        return this.rgbValues[2];
    }

    public void setB(double b) {
        this.rgbValues[2] = b;
    }

    public Color multiplyByFactor(double factor) {
        return new Color(this.getR() * factor, this.getG() * factor, this.getB() * factor);
    }

    public Color multiplyColor(Color color) {
        return new Color(this.getR() * color.getR(), this.getG() * color.getG(), this.getB() * color.getB());
    }
}
