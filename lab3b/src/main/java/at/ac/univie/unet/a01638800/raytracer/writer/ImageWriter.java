package at.ac.univie.unet.a01638800.raytracer.writer;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageWriter {

    private static ImageWriter INSTANCE;

    public static ImageWriter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ImageWriter();
        }

        return INSTANCE;
    }

    public void writeImage(final BufferedImage image, final String outputFile) throws ImageWriterException {
        try {
            final File file = new File(outputFile);

            ImageIO.write(image, "PNG", file);
        } catch (final Exception ex) {
            throw new ImageWriterException("Could not output file!", ex);
        }
    }

}
