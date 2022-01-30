package at.ac.univie.unet.a01638800.raytracer.io.image;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Responsible for writing images
 */
public class ImageWriter {

    private static ImageWriter INSTANCE;

    public static ImageWriter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ImageWriter();
        }

        return INSTANCE;
    }

    /**
     * Writes the given {@link BufferedImage} to the given file
     *
     * @param image      the image to write
     * @param outputFile the target file path
     * @throws ImageWriterException whenever an issue occurs
     */
    public void writeImage(final BufferedImage image, final String outputFile) throws ImageWriterException {
        try {
            final File file = new File(outputFile);

            ImageIO.write(image, "PNG", file);
        } catch (final Exception ex) {
            throw new ImageWriterException("Could not output file!", ex);
        }
    }

}
