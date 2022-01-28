package at.ac.univie.unet.a01638800.raytracer.scene;

import at.ac.univie.unet.a01638800.raytracer.DebugMode;
import at.ac.univie.unet.a01638800.raytracer.geometry.Color;
import at.ac.univie.unet.a01638800.raytracer.geometry.Ray;
import at.ac.univie.unet.a01638800.raytracer.geometry.Vector;
import at.ac.univie.unet.a01638800.raytracer.scene.camera.Camera;
import at.ac.univie.unet.a01638800.raytracer.scene.intersection.Intersection;
import at.ac.univie.unet.a01638800.raytracer.scene.phong.IlluminationMode;
import at.ac.univie.unet.a01638800.raytracer.scene.phong.PhongShader;
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

    private final int sceneWidth;
    private final int sceneHeight;

    private final BufferedImage image;

    private final DebugMode debugMode;

    public Scene(final XmlScene scene, final Surface[] surfaces, final DebugMode debugMode) {
        this.scene = scene;
        this.surfaces = surfaces;
        this.debugMode = debugMode;

        // setup camera
        camera = new Camera(scene.getCamera());

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
    private void calculatePixelsNoDebugMode() {
        // iterate through image dimensions
        for (int x = 0; x < sceneWidth; x++) {
            for (int y = 0; y < sceneHeight; y++) {

                // for every pixel, check camera ray intersection, shadow ray intersection and calculate pixel color
                Intersection cameraRayIntersection = null;
                Intersection shadowRayIntersection = null;
                Color pixelColor = new Color();

                // check for intersections with every surface
                for (final Surface surface : surfaces) {
                    cameraRayIntersection = surface.intersectionDetected(camera.getRays()[x][y]);

                    // camera ray intersection returns an intersection
                    if (cameraRayIntersection != null) {

                        // illuminate object for every light
                        for (final XmlLight light : scene.getLights().getLights()) {

                            // ambient light
                            if (light instanceof XmlAmbientLight) {

                                // Phong shader without illumination (only ambient)
                                final PhongShader shader = new PhongShader(light, surface.getXmlSurface().getMaterialSolid(), cameraRayIntersection, IlluminationMode.AMBIENT);

                                pixelColor = pixelColor.addColor(shader.calculatePixelColor());

                                // directional light
                            } else if (light instanceof XmlParallelLight) {

                                // check for shadow
                                shadowRayIntersection = checkForShadow(cameraRayIntersection, (XmlParallelLight) light);

                                // no shadow was detected
                                if (shadowRayIntersection == null) {

                                    // calculate pixel color for each light with illumination (diffuse + specular)
                                    final PhongShader shader = new PhongShader(light, surface.getXmlSurface().getMaterialSolid(), cameraRayIntersection, IlluminationMode.PARALLEL);

                                    pixelColor = pixelColor.addColor(shader.calculatePixelColor());

                                    break;
                                } // else ignore
                            } else if (light instanceof XmlSpotLight) {

                                // check for shadow
                                shadowRayIntersection = checkForShadow(cameraRayIntersection, (XmlSpotLight) light);

                                // no shadow was detected
                                if (shadowRayIntersection == null) {

                                    // calculate pixel color for each light with illumination (diffuse + specular)
                                    final PhongShader shader = new PhongShader(light, surface.getXmlSurface().getMaterialSolid(), cameraRayIntersection, IlluminationMode.SPOT);

                                    pixelColor = pixelColor.addColor(shader.calculatePixelColor());

                                    break;
                                }
                            }
                        }
                        break;
                    }
                }

                if (cameraRayIntersection == null) {
                    // set pixel color to background color
                    pixelColor.setR(Double.parseDouble(scene.getBackgroundColor().getR()));
                    pixelColor.setG(Double.parseDouble(scene.getBackgroundColor().getG()));
                    pixelColor.setB(Double.parseDouble(scene.getBackgroundColor().getB()));
                }

                image.getRaster().setDataElements(x, sceneHeight - 1 - y, RgbMapper.mapColorToRgb(pixelColor.getRgbValues()));
            }
        }
    }

    // TODO make class with generic type for light
    private Intersection checkForShadow(final Intersection cameraRayIntersection, final XmlLight light) {
        Intersection shadowIntersection = null;
        final Ray shadowRay = new Ray();

        shadowRay.setOrigin(cameraRayIntersection.getIntersectionPoint());

        if (light instanceof XmlParallelLight) {
           XmlParallelLight directionalLight = (XmlParallelLight) light;

            shadowRay.setDirection(new Vector(
                    Double.parseDouble(directionalLight.getDirection().getX()),
                    Double.parseDouble(directionalLight.getDirection().getY()),
                    Double.parseDouble(directionalLight.getDirection().getZ())
            ).invert().normalize());

        } else if (light instanceof XmlSpotLight) {
            XmlSpotLight directionalLight = (XmlSpotLight) light;

            shadowRay.setDirection(new Vector(
                    Double.parseDouble(directionalLight.getDirection().getX()),
                    Double.parseDouble(directionalLight.getDirection().getY()),
                    Double.parseDouble(directionalLight.getDirection().getZ())
            ).invert().normalize());
        }

        for (final Surface surface : surfaces) {
            shadowIntersection = surface.intersectionDetected(shadowRay);

            if (shadowIntersection != null) {
                break;
            }
        }

        return shadowIntersection;
    }

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

}
