package at.ac.univie.unet.a01638800.raytracer;

public class Pixel {
    private int[] rgbValues;

    public Pixel() {
        this.rgbValues = new int[1];
    }

    public Pixel(int[] rgbValues) {
        this.rgbValues = rgbValues;
    }

    public int[] getRgbValues() {
        return rgbValues;
    }

    public void setRgbValues(int[] rgbValues) {
        this.rgbValues = rgbValues;
    }
}
