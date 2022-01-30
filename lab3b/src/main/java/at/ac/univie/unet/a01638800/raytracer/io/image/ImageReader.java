package at.ac.univie.unet.a01638800.raytracer.io.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageReader {
    private static ImageReader INSTANCE;

    public static ImageReader getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ImageReader();
        }

        return INSTANCE;
    }

    public BufferedImage readImage(final String inputFile) throws ImageReaderException {
        try {
            final File file = new File(inputFile);

            return ImageIO.read(file);
        } catch (final Exception ex) {
            throw new ImageReaderException("Could not read file!", ex);
        }
    }
}
