package at.ac.univie.unet.a01638800.utils;

/**
 * Maps rgb values taken as double array of size 3 [r, g, b], to an integer array that can be parsed to
 * BufferedImage
 *
 * @see java.awt.image.BufferedImage
 */
public class RgbMapper {

    /**
     * Coordinates are ranged between -1 and 1. In order to map them to rgb values which range between
     * 0 and 255, this is done as follows:
     *
     * <ol>
     *     <li> Add 1 to all coordinates. This will result in a range from 0 to 2. </li>
     *     <li> Divide the newly ranged coordinates by 2 in order to achieve a range from 0 to 1. </li>
     *     <li> Multiply by 255 to get the corresponding rgb value. </li>
     *     <li> Apply a bit-shift in order to get the TYPE_INT_RGB values. </li>
     * </ol>
     *
     * @param coordinates the direction coordinate of the ray
     * @return integer byte array with corresponding rgb values
     */
    public static int[] mapCoordinatesToRgb(final double[] coordinates) {
        final int[] rgbValues = new int[1];

        // coordinates are initially between -1 and 1
        final int r = (int) (((coordinates[0] + 1.0) / 2.0) * 255.0);
        final int g = (int) (((coordinates[1] + 1.0) / 2.0) * 255.0);
        final int b = 0; // the z value is always -1 which corresponds to 0 in rgb value

        final int rgb = r << 16 | g << 8 | b;

        rgbValues[0] = rgb;

        return rgbValues;
    }

    public static int[] mapColorToRgb(final double[] color) {
        final int[] rgbValues = new int[1];

        // color is initially already normalized between 0 and 1
        final int r = (int) (color[0] * 255.0);
        final int g = (int) (color[1] * 255.0);
        final int b = (int) (color[2] * 255.0);

        final int rgb = r << 16 | g << 8 | b;

        rgbValues[0] = rgb;

        return rgbValues;
    }

    public static double[] mapRgbToColor(int rgb) {
        int r = (rgb & 0x00ff0000) >> 16;
        int g = (rgb & 0x0000ff00) >> 8;
        int b = (rgb & 0x000000ff);

        return new double[]{r / 255.0, g / 255.0, b / 255.0};
    }
}
