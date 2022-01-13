package at.ac.univie.unet.a01638800.raytracer.raytracedscene;

import at.ac.univie.unet.a01638800.raytracer.DebugMode;
import at.ac.univie.unet.a01638800.raytracer.camera.Camera;
import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Ray;
import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Vector;
import at.ac.univie.unet.a01638800.raytracer.intersection.Intersection;
import at.ac.univie.unet.a01638800.raytracer.phong.PhongShader;
import at.ac.univie.unet.a01638800.raytracer.scene.*;
import at.ac.univie.unet.a01638800.raytracer.surfaces.Sphere;
import at.ac.univie.unet.a01638800.utils.RgbMapper;
import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Color;

import java.awt.image.BufferedImage;

public class RaytracedScene {

    private final Scene parsedScene;
    private final Camera camera;
    private final Sphere[] spheres;

    private final int sceneWidth;
    private final int sceneHeight;

    private final BufferedImage image;

    private final DebugMode debugMode;

    public RaytracedScene(Scene scene, DebugMode debugMode) {
        this.parsedScene = scene;
        this.debugMode = debugMode;
        this.camera = new Camera(scene.getCamera());

        // set up surfaces
        // TODO assuming that all surfaces are of type sphere
        int sphereCount = scene.getSurfaces().getSurfaces().size();

        Sphere[] spheres = new Sphere[sphereCount];
        int i = 0;

        for (Surface surface : scene.getSurfaces().getSurfaces()) {
            if (surface instanceof at.ac.univie.unet.a01638800.raytracer.scene.Sphere) {
                spheres[i] = new Sphere((at.ac.univie.unet.a01638800.raytracer.scene.Sphere) surface);
                i++;
            }
        }

        this.spheres = spheres;

        // set up image
        this.sceneWidth = Integer.parseInt(scene.getCamera().getResolution().getHorizontal());
        this.sceneHeight = Integer.parseInt(scene.getCamera().getResolution().getVertical());

        this.image = new BufferedImage(this.sceneWidth, this.sceneHeight, BufferedImage.TYPE_INT_RGB);

        // calculate pixels
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

        // iterate through image dimensions
        for (int x = 0; x < this.sceneWidth; x++) {
            for (int y = 0; y < this.sceneHeight; y++) {

                // for every pixel, check camera ray intersection, shadow ray intersection and calculate pixel color
                Intersection cameraRayIntersection = null;
                Intersection shadowRayIntersection = null;
                Color pixelColor = new Color();

                // check for intersections with every surface
                for (Sphere sphere : this.spheres) {
                    cameraRayIntersection = sphere.intersectionDetected(this.camera.getRays()[x][y]);

                    // camera ray intersection returns an intersection
                    if (cameraRayIntersection != null) {

                        // illuminate object for every light
                        for (Light light : this.parsedScene.getLights().getLights()) {

                            // ambient light
                            if (light instanceof  AmbientLight) {

                                // Phong shader without illumination (only ambient)
                                PhongShader shader = new PhongShader(light, sphere.getParsedSphere().getMaterialSolid(), cameraRayIntersection, false);

                                pixelColor = pixelColor.addColor(shader.calculatePixelColor());

                               // directional light
                            } else if (light instanceof ParallelLight) {

                                // check for shadow
                               shadowRayIntersection = this.checkForShadow(cameraRayIntersection, (ParallelLight) light);

                                // no shadow was detected
                                if (shadowRayIntersection == null) {

                                    // calculate pixel color for each light with illumination (diffuse + specular)
                                    PhongShader shader = new PhongShader(light, sphere.getParsedSphere().getMaterialSolid(), cameraRayIntersection, true);

                                    pixelColor = pixelColor.addColor(shader.calculatePixelColor());

                                    break;
                                } // else ignore
                            }
                        }
                        break;
                    }
                }

                if (cameraRayIntersection == null) {
                    // set pixel color to background color
                    pixelColor.setR(Double.parseDouble(this.parsedScene.getBackgroundColor().getR()));
                    pixelColor.setG(Double.parseDouble(this.parsedScene.getBackgroundColor().getG()));
                    pixelColor.setB(Double.parseDouble(this.parsedScene.getBackgroundColor().getB()));
                }

                this.image.getRaster().setDataElements(x, this.sceneHeight - 1 - y, RgbMapper.mapColorToRgb(pixelColor.getRgbValues()));
            }
        }
    }

    private Intersection checkForShadow(Intersection cameraRayIntersection, ParallelLight parallelLight) {
        Intersection shadowIntersection = null;
        Ray shadowRay = new Ray();

        shadowRay.setOrigin(cameraRayIntersection.getIntersectionPoint());
        shadowRay.setDirection(new Vector(
                Double.parseDouble(parallelLight.getDirection().getX()),
                Double.parseDouble(parallelLight.getDirection().getY()),
                Double.parseDouble(parallelLight.getDirection().getZ())
        ).invert().normalize());

        for (Sphere sphere : this.spheres) {
            shadowIntersection = sphere.intersectionDetected(shadowRay);

            if (shadowIntersection != null) {
                break;
            }
        }

        return shadowIntersection;
    }


        private void calculatePixelsNoLightDebugMode () {
            for (int x = 0; x < this.sceneWidth; x++) {
                for (int y = 0; y < this.sceneHeight; y++) {
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

                    this.image.getRaster().setDataElements(x, this.sceneHeight - 1 - y, RgbMapper.mapColorToRgb(pixelColor));
                }
            }
        }

        private void calculatePixelsRayDirectionDebugMode () {
            for (int x = 0; x < this.sceneWidth; x++) {
                for (int y = 0; y < this.sceneHeight; y++) {
                    double[] coordinates = this.camera.getRays()[x][y].getDirection().getCoordinate().getXyzValues();
                    Intersection intersection = null;

                    for (Sphere sphere : this.spheres) {
                        intersection = sphere.intersectionDetected(this.camera.getRays()[x][y]);

                        if (intersection != null) {
                            break;
                        }
                    }

                    if (intersection == null) {
                        this.image.getRaster().setDataElements(x, this.sceneHeight - 1 - y, RgbMapper.mapCoordinatesToRgb(coordinates));
                    } else {
                        this.image.setRGB(x, this.sceneHeight - 1 - y, java.awt.Color.WHITE.getRGB());
                    }
                }
            }
        }

        private void calculatePixelsNormalsDebugMode () {
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

                        this.image.getRaster().setDataElements(x, this.sceneHeight - 1 - y, RgbMapper.mapColorToRgb(pixelColor));
                    } else {
                        normalCoordinates[0] = intersection.getNormal().getX();
                        normalCoordinates[1] = intersection.getNormal().getY();
                        normalCoordinates[2] = intersection.getNormal().getZ();

                        this.image.getRaster().setDataElements(x, this.sceneHeight - 1 - y, RgbMapper.mapCoordinatesToRgb(normalCoordinates));
                    }
                }
            }
        }
    }
