package at.ac.univie.unet.a01638800.raytracer.scene;

import at.ac.univie.unet.a01638800.raytracer.DebugMode;
import at.ac.univie.unet.a01638800.raytracer.geometry.Color;
import at.ac.univie.unet.a01638800.raytracer.geometry.Ray;
import at.ac.univie.unet.a01638800.raytracer.scene.camera.Camera;
import at.ac.univie.unet.a01638800.raytracer.scene.intersection.Intersection;
import at.ac.univie.unet.a01638800.raytracer.scene.phong.IlluminationMode;
import at.ac.univie.unet.a01638800.raytracer.scene.phong.PhongShader;
import at.ac.univie.unet.a01638800.raytracer.scene.phong.Shadow;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.Surface;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.*;
import at.ac.univie.unet.a01638800.utils.Debugger;
import at.ac.univie.unet.a01638800.utils.RgbMapper;

import java.awt.image.BufferedImage;

/**
 * Represents the raytraced scene. In this class, the actual raytracing algorithm is implemented.
 * <p>
 * TODO replace surface.getXmlSurface() with custom material/texture class
 */
public class Scene {
    private final Camera camera;

    private final int sceneWidth;
    private final int sceneHeight;

    private final BufferedImage image;

    private final Debugger debugger;
    private final DebugMode debugMode;

    private final Raytracer raytracer;

    public Scene(final XmlScene scene, final Surface[] surfaces, final DebugMode debugMode) {
        this.debugMode = debugMode;

        // setup camera
        camera = new Camera(scene.getCamera());

        // set up image
        sceneWidth = Integer.parseInt(scene.getCamera().getResolution().getHorizontal());
        sceneHeight = Integer.parseInt(scene.getCamera().getResolution().getVertical());

        image = new BufferedImage(sceneWidth, sceneHeight, BufferedImage.TYPE_INT_RGB);

        // set up raytracer
        this.raytracer = new Raytracer(scene, surfaces);

        // set up debugger
        this.debugger = new Debugger(debugMode, surfaces, scene);

        // calculate pixels
        calculatePixels();
    }

    public BufferedImage getImage() {
        return image;
    }

    /**
     * Calculates the pixels based on the given debug mode.
     *
     * @see DebugMode
     */
    private void calculatePixels() {
        if(debugMode == DebugMode.NO_DEBUG) {
            calculatePixelsNoDebugMode();
        } else {
            this.debug();
        }
    }

    /**
     * In this mode, the actual raytracing happens.
     */
    private void calculatePixelsNoDebugMode() {
        // iterate through image dimensions
        for (int x = 0; x < sceneWidth; x++) {
            for (int y = 0; y < sceneHeight; y++) {

                // bounce is at the beginning zero
                Color pixelColor = this.raytracer.traceRay(camera.getRays()[x][y], 1);

                image.getRaster().setDataElements(x, sceneHeight - 1 - y, RgbMapper.mapColorToRgb(pixelColor.getRgbValues()));
            }
        }
    }


    private void debug() {
        for (int x = 0; x < sceneWidth; x++) {
            for (int y = 0; y < sceneHeight; y++) {

                int[] data = debugger.debug(camera.getRays()[x][y]);

                image.getRaster().setDataElements(x, sceneHeight - 1 - y, data);
            }
        }
    }
}
