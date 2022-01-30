package at.ac.univie.unet.a01638800.raytracer.scene;

import at.ac.univie.unet.a01638800.raytracer.geometry.Color;
import at.ac.univie.unet.a01638800.raytracer.geometry.Ray;
import at.ac.univie.unet.a01638800.raytracer.scene.camera.Camera;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.Surface;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlScene;
import at.ac.univie.unet.a01638800.utils.RgbMapper;
import java.awt.image.BufferedImage;

/**
 * Represents the raytraced scene. In this class, the general raytracing flow is implemented. The raytracing itself
 * is implemented in the {@link Raytracer}
 */
public class Scene {

    private final Camera camera;

    private final int sceneWidth;
    private final int sceneHeight;

    private final BufferedImage image;

    private final Raytracer raytracer;

    public Scene(final XmlScene scene, final Surface[] surfaces, final int samples) {
        // setup camera
        camera = new Camera(scene.getCamera(), samples);

        // set up image
        sceneWidth = Integer.parseInt(scene.getCamera().getResolution().getHorizontal());
        sceneHeight = Integer.parseInt(scene.getCamera().getResolution().getVertical());

        image = new BufferedImage(sceneWidth, sceneHeight, BufferedImage.TYPE_INT_RGB);

        // set up raytracer
        raytracer = new Raytracer(scene, surfaces);

        // calculate pixels
        calculatePixels();
    }

    public BufferedImage getImage() {
        return image;
    }

    /**
     * Calculates the pixels for all rays
     */
    private void calculatePixels() {
        // iterate through image dimensions
        for (int x = 0; x < sceneWidth; x++) {
            for (int y = 0; y < sceneHeight; y++) {
                // this loops through the samples for one single pixel (supersampling)
                final double[] rgbValues = new double[3];

                final Ray[] rays = camera.getRays()[x][y];
                for (final Ray ray : rays) {
                    // bounce is at the beginning one
                    final Color sampleColor = raytracer.traceRay(ray, 1);

                    final double[] sampleRgbValues = sampleColor.getRgbValues();
                    rgbValues[0] += sampleRgbValues[0];
                    rgbValues[1] += sampleRgbValues[1];
                    rgbValues[2] += sampleRgbValues[2];
                }

                // average accross all samples
                rgbValues[0] /= rays.length;
                rgbValues[1] /= rays.length;
                rgbValues[2] /= rays.length;

                image.getRaster().setDataElements(x, sceneHeight - 1 - y, RgbMapper.mapColorToRgb(rgbValues));
            }
        }
    }

}
