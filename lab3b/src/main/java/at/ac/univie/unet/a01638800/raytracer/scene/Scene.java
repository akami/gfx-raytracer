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
import at.ac.univie.unet.a01638800.utils.RgbMapper;

import java.awt.image.BufferedImage;

/**
 * Represents the raytraced scene. In this class, the actual raytracing algorithm is implemented.
 * <p>
 * TODO replace surface.getXmlSurface() with custom material/texture class
 */
public class Scene {

    private final XmlScene scene;
    private final Surface[] surfaces;

    private final Camera camera;
    private final int maxBounces;

    private final int sceneWidth;
    private final int sceneHeight;

    private final BufferedImage image;

    private final DebugMode debugMode;

    private final Shadow shadow;

    public Scene(final XmlScene scene, final Surface[] surfaces, final DebugMode debugMode) {
        this.scene = scene;
        this.surfaces = surfaces;
        this.debugMode = debugMode;
        this.shadow = new Shadow(surfaces);

        // setup camera
        camera = new Camera(scene.getCamera());
        this.maxBounces = Integer.parseInt(scene.getCamera().getMaxBounces().getN());

        // set up image
        sceneWidth = Integer.parseInt(scene.getCamera().getResolution().getHorizontal());
        sceneHeight = Integer.parseInt(scene.getCamera().getResolution().getVertical());

        image = new BufferedImage(sceneWidth, sceneHeight, BufferedImage.TYPE_INT_RGB);

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
        switch (debugMode) {
            case RAY_DIRECTION:
                calculatePixelsRayDirectionDebugMode();
                break;
            case NORMALS:
                calculatePixelsNormalsDebugMode();
                break;
            case NO_LIGHT:
                calculatePixelsNoLightDebugMode();
                break;
            default:
                calculatePixelsNoDebugMode();
                break;
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
                Color pixelColor = this.traceRay(camera.getRays()[x][y], 0);

                image.getRaster().setDataElements(x, sceneHeight - 1 - y, RgbMapper.mapColorToRgb(pixelColor.getRgbValues()));
            }
        }
    }

    /**
     * This mode checks if the intersection works and calculates only the colors without any illumination.
     */
    private void calculatePixelsNoLightDebugMode() {
        for (int x = 0; x < sceneWidth; x++) {
            for (int y = 0; y < sceneHeight; y++) {
                final double[] pixelColor = new double[3];
                Intersection intersection = null;

                for (final Surface surface : surfaces) {
                    intersection = surface.intersectionDetected(camera.getRays()[x][y]);

                    if (intersection != null) {
                        pixelColor[0] = Double.parseDouble(surface.getXmlSurface().getMaterialSolid().getColor().getR());
                        pixelColor[1] = Double.parseDouble(surface.getXmlSurface().getMaterialSolid().getColor().getG());
                        pixelColor[2] = Double.parseDouble(surface.getXmlSurface().getMaterialSolid().getColor().getB());

                        break;
                    }
                }

                if (intersection == null) {
                    pixelColor[0] = Double.parseDouble(scene.getBackgroundColor().getR());
                    pixelColor[1] = Double.parseDouble(scene.getBackgroundColor().getG());
                    pixelColor[2] = Double.parseDouble(scene.getBackgroundColor().getB());
                }

                image.getRaster().setDataElements(x, sceneHeight - 1 - y, RgbMapper.mapColorToRgb(pixelColor));
            }
        }
    }

    /**
     * This debug mode encodes the camera ray direction to rgb. A positive intersection test results in a white pixel
     * color.
     */
    private void calculatePixelsRayDirectionDebugMode() {
        for (int x = 0; x < sceneWidth; x++) {
            for (int y = 0; y < sceneHeight; y++) {
                final double[] coordinates = camera.getRays()[x][y].getDirection().getCoordinate().getXyzValues();
                Intersection intersection = null;

                for (final Surface surface : surfaces) {
                    intersection = surface.intersectionDetected(camera.getRays()[x][y]);

                    if (intersection != null) {
                        break;
                    }
                }

                if (intersection == null) {
                    image.getRaster().setDataElements(x, sceneHeight - 1 - y, RgbMapper.mapCoordinatesToRgb(coordinates));
                } else {
                    image.setRGB(x, sceneHeight - 1 - y, java.awt.Color.WHITE.getRGB());
                }
            }
        }
    }

    /**
     * This mode encodes the surface normals to rgb values.
     */
    private void calculatePixelsNormalsDebugMode() {
        for (int x = 0; x < sceneWidth; x++) {
            for (int y = 0; y < sceneHeight; y++) {
                final double[] normalCoordinates = new double[3];
                final double[] pixelColor = new double[3];
                Intersection intersection = null;

                for (final Surface surface : surfaces) {
                    intersection = surface.intersectionDetected(camera.getRays()[x][y]);

                    if (intersection != null) {
                        break;
                    }
                }

                if (intersection == null) {
                    pixelColor[0] = Double.parseDouble(scene.getBackgroundColor().getR());
                    pixelColor[1] = Double.parseDouble(scene.getBackgroundColor().getG());
                    pixelColor[2] = Double.parseDouble(scene.getBackgroundColor().getB());

                    image.getRaster().setDataElements(x, sceneHeight - 1 - y, RgbMapper.mapColorToRgb(pixelColor));
                } else {
                    normalCoordinates[0] = intersection.getNormal().getX();
                    normalCoordinates[1] = intersection.getNormal().getY();
                    normalCoordinates[2] = intersection.getNormal().getZ();

                    image.getRaster().setDataElements(x, sceneHeight - 1 - y, RgbMapper.mapCoordinatesToRgb(normalCoordinates));
                }
            }
        }
    }

    /**
     * Implements the standard raytracing algorithm:
     *
     * <ol>
     *     <li> Iterate through 2D pixels which are given by the image dimension: </li>
     *             <li> Iterate through surfaces and check for an intersection: </li>
     *             <ul>
     *                 <li> Intersection: </li>
     *                 <ol>
     *                     <li> Iterate through all light sources to determine the shading and illumination: </li>
     *                         <li> Check for type of light: </li>
     *                         <ul>
     *                             <li> Ambient Light: <br>
     *                             Shade the pixel based on the ambient component of the light and material without
     *                             illumination.
     *                             </li>
     *                             <li> Parallel Light: <br>
     *                             Check for shadow intersection:
     *                             </li>
     *                             <ul>
     *                                 <li>
     *                                     Shadow: <br>
     *                                     Don't illuminate the surface.
     *                                 </li>
     *                                 <li>
     *                                     No shadow: <br>
     *                                     Illuminate the surface.
     *                                 </li>
     *                             </ul>
     *                     </ul>
     *                 </ol>
     *                 <li> No Intersection: <br>
     *                 Set the pixel to background color.
     *                 </li>
     *             </ul>
     * </ol>
     */
    private Color traceRay(Ray ray, int bounce) {
        Intersection cameraRayIntersection = null;

        Color color = new Color();
        Color reflectedColor = new Color();
        Color refractedColor = new Color();

        double reflectance = 0D;
        double transmittance = 0D;

        // check for intersections with every surface
        for (final Surface surface : surfaces) {
            cameraRayIntersection = surface.intersectionDetected(ray);
            reflectance = Double.parseDouble(surface.getXmlSurface().getMaterialSolid().getReflectance().getR());
            transmittance = Double.parseDouble(surface.getXmlSurface().getMaterialSolid().getTransmittance().getT());

            // camera ray intersection returns an intersection
            if (cameraRayIntersection != null) {
                color = this.illuminate(surface, cameraRayIntersection);

                // max bounces reached
                if (bounce > this.maxBounces) {
                    return color;
                }

                // check surface reflectance
                if (reflectance > 0D) {
                    Ray reflectedRay = this.getReflectedRay();
                    reflectedColor = traceRay(reflectedRay, bounce + 1).multiplyByFactor(reflectance);
                }

                // check surface transmittance
                if (transmittance > 0D) {
                    Ray refractedRay = this.getRefractedRay();
                    refractedColor = traceRay(refractedRay, bounce + 1);
                }

                break;
            }
        }

        if (cameraRayIntersection == null) {
            // set pixel color to background color
            color = new Color(
                    Double.parseDouble(scene.getBackgroundColor().getR()),
                    Double.parseDouble(scene.getBackgroundColor().getG()),
                    Double.parseDouble(scene.getBackgroundColor().getB())
            );
        }

        return color.multiplyByFactor(1 - reflectance - transmittance).addColor(reflectedColor).addColor(refractedColor);
    }

    private Color illuminate(Surface surface, Intersection cameraRayIntersection) {
        Intersection shadowRayIntersection;
        Color color = new Color();

        // illuminate object for every light
        for (final XmlLight light : scene.getLights().getLights()) {

            // ambient light
            if (light instanceof XmlAmbientLight) {

                // Phong shader without illumination (only ambient)
                final PhongShader shader = new PhongShader(light, surface.getXmlSurface().getMaterialSolid(), cameraRayIntersection, IlluminationMode.AMBIENT);

                color = color.addColor(shader.calculatePixelColor());

                // directional light
            } else if (light instanceof XmlParallelLight) {

                // check for shadow
                shadowRayIntersection = this.shadow.checkForShadow(cameraRayIntersection, light);

                // no shadow was detected
                if (shadowRayIntersection == null) {

                    // calculate pixel color for each light with illumination (diffuse + specular)
                    final PhongShader shader = new PhongShader(light, surface.getXmlSurface().getMaterialSolid(), cameraRayIntersection, IlluminationMode.PARALLEL);

                    color = color.addColor(shader.calculatePixelColor());

                } // else ignore
            } else if (light instanceof XmlPointLight) {

                // check for shadow
                shadowRayIntersection = this.shadow.checkForShadow(cameraRayIntersection, light);

                // no shadow was detected
                if (shadowRayIntersection == null) {

                    // calculate pixel color for each light with illumination (diffuse + specular)
                    final PhongShader shader = new PhongShader(light, surface.getXmlSurface().getMaterialSolid(), cameraRayIntersection, IlluminationMode.POINT);

                    color = color.addColor(shader.calculatePixelColor());
                }
            }
        }

        return color;
    }

    private Ray getReflectedRay() {
        // TODO implement

        return new Ray();
    }

    private Ray getRefractedRay() {
        // TODO implement

        return new Ray();
    }

}
