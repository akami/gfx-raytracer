package at.ac.univie.unet.a01638800.raytracer.io.image;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Responsible for reading images
 */
public class ImageReader {

    private static ImageReader INSTANCE;

    public static ImageReader getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ImageReader();
        }

        return INSTANCE;
    }

    /**
     * Reads the given file and returns a {@link BufferedImage}
     *
     * @param inputFile the target file path
     * @return a {@link BufferedImage} that can be used for textures
     * @throws ImageReaderException whenever an issue occurs
     */
    public BufferedImage readImage(final String inputFile) throws ImageReaderException {
        try {
            final File file = new File(inputFile);

            return ImageIO.read(file);
        } catch (final Exception ex) {
            throw new ImageReaderException("Could not read file!", ex);
        }
    }

}
