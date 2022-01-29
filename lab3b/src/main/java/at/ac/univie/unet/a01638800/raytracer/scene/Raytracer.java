package at.ac.univie.unet.a01638800.raytracer.scene;

import at.ac.univie.unet.a01638800.raytracer.geometry.Color;
import at.ac.univie.unet.a01638800.raytracer.geometry.Ray;
import at.ac.univie.unet.a01638800.raytracer.geometry.Vector;
import at.ac.univie.unet.a01638800.raytracer.scene.intersection.Intersection;
import at.ac.univie.unet.a01638800.raytracer.scene.phong.IlluminationMode;
import at.ac.univie.unet.a01638800.raytracer.scene.phong.PhongShader;
import at.ac.univie.unet.a01638800.raytracer.scene.phong.Shadow;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.Surface;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.*;

public class Raytracer {
    private final XmlScene scene;
    private final Surface[] surfaces;
    private final int maxBounces;
    private final Color backgroundColor;
    private final Shadow shadow;

    public Raytracer(XmlScene scene, Surface[] surfaces) {
        this.scene = scene;
        this.surfaces = surfaces;

        this.maxBounces = Integer.parseInt(scene.getCamera().getMaxBounces().getN());

        this.backgroundColor = new Color(
                Double.parseDouble(scene.getBackgroundColor().getR()),
                Double.parseDouble(scene.getBackgroundColor().getG()),
                Double.parseDouble(scene.getBackgroundColor().getB())
        );

        this.shadow = new Shadow(this.surfaces);
    }

    public Color traceRay(Ray ray, int bounce) {
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
                    Ray reflectedRay = this.getReflectedRay(ray, cameraRayIntersection);
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
            color = this.backgroundColor;
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

    private Ray getReflectedRay(Ray incidentRay, Intersection intersection) {
        Vector lVector = incidentRay.getDirection().normalize();
        Vector nVector = intersection.getNormal();

        Vector rVector = nVector.scaleByFactor(2.0 * (lVector.invert()).dotProduct(nVector)).addVector(lVector);

        Ray reflectedRay = new Ray();
        reflectedRay.setOrigin(intersection.getIntersectionPoint());
        reflectedRay.setDirection(rVector.normalize());

        return new Ray();
    }

    private Ray getRefractedRay() {
        // TODO implement

        return new Ray();
    }
}
