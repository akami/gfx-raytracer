package at.ac.univie.unet.a01638800.raytracer.raytracedscene;

import at.ac.univie.unet.a01638800.raytracer.DebugMode;
import at.ac.univie.unet.a01638800.raytracer.camera.Camera;
import at.ac.univie.unet.a01638800.raytracer.intersection.Intersection;
import at.ac.univie.unet.a01638800.raytracer.phong.PhongShader;
import at.ac.univie.unet.a01638800.raytracer.scene.Light;
import at.ac.univie.unet.a01638800.raytracer.scene.Scene;
import at.ac.univie.unet.a01638800.raytracer.scene.Surface;
import at.ac.univie.unet.a01638800.raytracer.surfaces.Sphere;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RaytracedScene {
    private Scene parsedScene;
    private Camera camera;
    private Sphere[] spheres;

    private int sceneWidth;
    private int sceneHeight;

    private BufferedImage image;

    private DebugMode debugMode;

    public RaytracedScene(Scene scene, DebugMode debugMode) {
        this.parsedScene = scene;
        this.debugMode = debugMode;

        this.camera = new Camera(scene.getCamera());

        // assuming that all surfaces are of type sphere
        int sphereCount = scene.getSurfaces().getSurfaces().size();

        Sphere[] spheres = new Sphere[sphereCount];
        int i = 0;

        for (Surface surface : scene.getSurfaces().getSurfaces()) {
            if(surface instanceof at.ac.univie.unet.a01638800.raytracer.scene.Sphere) {
                spheres[i] = new Sphere((at.ac.univie.unet.a01638800.raytracer.scene.Sphere) surface);
                i++;
            }
        }

        this.spheres = spheres;

        this.sceneWidth = Integer.parseInt(scene.getCamera().getResolution().getHorizontal());
        this.sceneHeight = Integer.parseInt(scene.getCamera().getResolution().getVertical());

        this.image = new BufferedImage(this.sceneWidth, this.sceneHeight, BufferedImage.TYPE_INT_RGB);

        this.calculatePixels();
    }

    public BufferedImage getImage() {
        return this.image;
    }

    private void calculatePixels() {

        switch (this.debugMode) {
            case RAY_DIRECTION:
                this.calculatePixelsRayDirectionDebugMode();
                break;
            case NORMALS:
                this.calculatePixelsNormalsDebugMode();
                break;
            case NO_LIGHT:
                this.calculatePixelsNoLightDebugMode();
            default:
                this.calculatePixelsNoDebugMode();
                break;
        }
    }

    private void calculatePixelsNoDebugMode() {
        for(int x = 0; x < this.sceneWidth; x++) {
            for(int y = 0; y < this.sceneHeight; y ++) {
                Intersection intersection = null;
                at.ac.univie.unet.a01638800.raytracer.geometricobjects.Color pixelColor = new at.ac.univie.unet.a01638800.raytracer.geometricobjects.Color();

                for (Sphere sphere : this.spheres) {
                    intersection = sphere.intersectionDetected(this.camera.getRays()[x][y]);

                    if (intersection != null) {
                        // calculate pixel color illumination for each light
                        for (Light light : this.parsedScene.getLights().getLights()) {
                            PhongShader shader = new PhongShader(light, sphere.getParsedSphere().getMaterialSolid(), intersection);
                            pixelColor = pixelColor.addColor(shader.calculatePixelColor());
                        }
                        break;
                    }
                }

                if (intersection == null) {
                    // set pixel color to background color
                    pixelColor.setR(Double.parseDouble(this.parsedScene.getBackgroundColor().getR()));
                    pixelColor.setG(Double.parseDouble(this.parsedScene.getBackgroundColor().getG()));
                    pixelColor.setB(Double.parseDouble(this.parsedScene.getBackgroundColor().getB()));
                }

                this.image.getRaster().setDataElements(x, this.sceneHeight - 1 - y, mapColorToRgb(pixelColor.getRgbValues()));
            }
        }
    }


    private void calculatePixelsNoLightDebugMode() {
        for(int x = 0; x < this.sceneWidth; x++) {
            for(int y = 0; y < this.sceneHeight; y ++) {
                double[] pixelColor = new double[3];
                Intersection intersection = null;

                for (Sphere sphere : this.spheres) {
                    intersection = sphere.intersectionDetected(this.camera.getRays()[x][y]);

                    if (intersection != null) {
                        pixelColor[0] = Double.parseDouble(sphere.getParsedSphere().getMaterialSolid().getColor().getR());
                        pixelColor[1] = Double.parseDouble(sphere.getParsedSphere().getMaterialSolid().getColor().getG());
                        pixelColor[2] = Double.parseDouble(sphere.getParsedSphere().getMaterialSolid().getColor().getB());

                        break;
                    }
                }

                if (intersection == null) {
                    pixelColor[0] = Double.parseDouble(this.parsedScene.getBackgroundColor().getR());
                    pixelColor[1] = Double.parseDouble(this.parsedScene.getBackgroundColor().getG());
                    pixelColor[2] = Double.parseDouble(this.parsedScene.getBackgroundColor().getB());
                }

                this.image.getRaster().setDataElements(x, this.sceneHeight - 1 - y, mapColorToRgb(pixelColor));
            }
        }
    }

    private void calculatePixelsRayDirectionDebugMode() {
        for(int x = 0; x < this.sceneWidth; x++) {
            for(int y = 0; y < this.sceneHeight; y ++) {
                double[] coordinates = this.camera.getRays()[x][y].getDirection().getCoordinate().getXyzValues();
                Intersection intersection = null;

                for (Sphere sphere : this.spheres) {
                    intersection = sphere.intersectionDetected(this.camera.getRays()[x][y]);

                    if(intersection != null) {
                        break;
                    }
                }

                if (intersection == null) {
                    this.image.getRaster().setDataElements(x, this.sceneHeight - 1 - y, mapCoordinatesToRgb(coordinates));
                } else {
                    this.image.setRGB(x, this.sceneHeight - 1 - y, Color.WHITE.getRGB());
                }
            }
        }
    }

    private void calculatePixelsNormalsDebugMode() {
        for (int x = 0; x < this.sceneWidth; x++) {
            for (int y = 0; y < this.sceneHeight; y++) {
                double[] normalCoordinates = new double[3];
                double[] pixelColor = new double[3];
                Intersection intersection = null;

                for (Sphere sphere : this.spheres) {
                    intersection = sphere.intersectionDetected(this.camera.getRays()[x][y]);

                    if (intersection != null) {
                        break;
                    }
                }

                if (intersection == null) {
                    pixelColor[0] = Double.parseDouble(this.parsedScene.getBackgroundColor().getR());
                    pixelColor[1] = Double.parseDouble(this.parsedScene.getBackgroundColor().getG());
                    pixelColor[2] = Double.parseDouble(this.parsedScene.getBackgroundColor().getB());

                    this.image.getRaster().setDataElements(x, this.sceneHeight - 1 - y, mapColorToRgb(pixelColor));
                } else {
                    normalCoordinates[0] = intersection.getNormal().getX();
                    normalCoordinates[1] = intersection.getNormal().getY();
                    normalCoordinates[2] = intersection.getNormal().getZ();

                    this.image.getRaster().setDataElements(x, this.sceneHeight - 1 - y, mapCoordinatesToRgb(normalCoordinates));
                }
            }
        }
    }

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
     * @param coordinates the direction coordinate of the ray
     * @return integer byte array with corresponding rgb values
     */
    private int[] mapCoordinatesToRgb(double[] coordinates) {
        int[] rgbValues = new int[1];

        // coordinates are initially between -1 and 1
        int r = (int)(((coordinates[0] + 1.0) / 2.0) * 255.0);
        int g = (int)(((coordinates[1] + 1.0) / 2.0) * 255.0);
        int b = 0; // the z value is always -1 which corresponds to 0 in rgb value

        int rgb = r << 16 | g << 8 | b;

        rgbValues[0] = rgb;

        return rgbValues;
    }

    private int[] mapColorToRgb(double[] color) {
        int[] rgbValues = new int[1];

        // color is initially already normalized between 0 and 1
        int r = (int) (color[0] * 255.0);
        int g = (int) (color[1] * 255.0);
        int b = (int) (color[2] * 255.0);

        int rgb = r << 16 | g << 8 | b;

        rgbValues[0] = rgb;

        return rgbValues;
    }
}
