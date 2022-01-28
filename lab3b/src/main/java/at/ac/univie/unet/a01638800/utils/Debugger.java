package at.ac.univie.unet.a01638800.utils;

import at.ac.univie.unet.a01638800.raytracer.DebugMode;
import at.ac.univie.unet.a01638800.raytracer.geometry.Ray;
import at.ac.univie.unet.a01638800.raytracer.scene.intersection.Intersection;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.Surface;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlScene;

public class Debugger {
    private final DebugMode debugMode;
    private final Surface[] surfaces;
    private final XmlScene scene;

    public Debugger(DebugMode debugMode, Surface[] surfaces, XmlScene scene) {
        this.debugMode = debugMode;
        this.surfaces = surfaces;
        this.scene = scene;
    }

    public int[] debug(Ray ray) {
        int[] data = new int[3];

        switch (debugMode) {
            case RAY_DIRECTION:
                data = debugRayDirection(ray);
                break;
            case NORMALS:
                data = debugNormals(ray);
                break;
            case NO_LIGHT:
                data = debugNoLightColor(ray);
                break;
        }

        return data;
    }

    /**
     * This mode checks if the intersection works and calculates only the colors without any illumination.
     */
    private int[] debugNoLightColor(Ray ray) {
        final double[] pixelColor = new double[3];
        Intersection intersection = null;

        for (final Surface surface : surfaces) {
            intersection = surface.intersectionDetected(ray);

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

        return RgbMapper.mapColorToRgb(pixelColor);
    }

    /**
     * This debug mode encodes the camera ray direction to rgb. A positive intersection test results in a white pixel
     * color.
     */
    private int[] debugRayDirection(Ray ray) {
        final double[] coordinates = ray.getDirection().getCoordinate().getXyzValues();
        Intersection intersection = null;

        for (final Surface surface : surfaces) {
            intersection = surface.intersectionDetected(ray);

            if (intersection != null) {
                break;
            }
        }

        if (intersection == null) {
            return RgbMapper.mapCoordinatesToRgb(coordinates);
        } else {
            return RgbMapper.mapColorToRgb(new double[]{1.0, 1.0, 1.0});
        }
    }

    /**
     * This mode encodes the surface normals to rgb values.
     */
    private int[] debugNormals(Ray ray) {
        final double[] normalCoordinates = new double[3];
        final double[] pixelColor = new double[3];
        Intersection intersection = null;

        for (final Surface surface : surfaces) {
            intersection = surface.intersectionDetected(ray);

            if (intersection != null) {
                break;
            }
        }

        if (intersection == null) {
            pixelColor[0] = Double.parseDouble(scene.getBackgroundColor().getR());
            pixelColor[1] = Double.parseDouble(scene.getBackgroundColor().getG());
            pixelColor[2] = Double.parseDouble(scene.getBackgroundColor().getB());

            return RgbMapper.mapColorToRgb(pixelColor);
        } else {
            normalCoordinates[0] = intersection.getNormal().getX();
            normalCoordinates[1] = intersection.getNormal().getY();
            normalCoordinates[2] = intersection.getNormal().getZ();

            return RgbMapper.mapCoordinatesToRgb(normalCoordinates);
        }
    }
}
