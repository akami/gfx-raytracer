package at.ac.univie.unet.a01638800.raytracer.scene;

import at.ac.univie.unet.a01638800.raytracer.geometry.Color;
import at.ac.univie.unet.a01638800.raytracer.geometry.Point;
import at.ac.univie.unet.a01638800.raytracer.geometry.Ray;
import at.ac.univie.unet.a01638800.raytracer.geometry.Vector;
import at.ac.univie.unet.a01638800.raytracer.scene.intersection.Intersection;
import at.ac.univie.unet.a01638800.raytracer.scene.phong.PhongShader;
import at.ac.univie.unet.a01638800.raytracer.scene.phong.Shadow;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.Surface;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlAmbientLight;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlLight;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlParallelLight;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlPointLight;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlScene;

public class Raytracer {

    private final XmlScene scene;
    private final Surface[] surfaces;
    private final int maxBounces;
    private final Color backgroundColor;
    private final Shadow shadow;

    public Raytracer(final XmlScene scene, final Surface[] surfaces) {
        this.scene = scene;
        this.surfaces = surfaces;

        maxBounces = Integer.parseInt(scene.getCamera().getMaxBounces().getN());

        backgroundColor = new Color(
                Double.parseDouble(scene.getBackgroundColor().getR()),
                Double.parseDouble(scene.getBackgroundColor().getG()),
                Double.parseDouble(scene.getBackgroundColor().getB())
        );

        shadow = new Shadow(this.surfaces);
    }

    public Color traceRay(final Ray ray, final int bounce) {
        Intersection cameraRayIntersection = null;
        Color color = new Color();

        // check for intersections with every surface
        for (final Surface surface : surfaces) {
            cameraRayIntersection = surface.intersectionDetected(ray);

            // camera ray intersection returns an intersection
            if (cameraRayIntersection != null) {
                color = illuminate(surface, cameraRayIntersection, bounce);

                break;
            }
        }

        if (cameraRayIntersection == null) {
            // set pixel color to background color
            color = backgroundColor;
        }

        return color;
    }

    private Color illuminate(final Surface surface, final Intersection cameraRayIntersection, final int bounce) {
        Color color = new Color();

        final double reflectance = surface.getMaterial().getReflectance();
        final double transmittance = surface.getMaterial().getTransmittance();
        final double refraction = surface.getMaterial().getRefraction();

        Color reflectedColor = new Color();
        Color refractedColor = new Color();

        // illuminate object for every light
        for (final XmlLight light : scene.getLights().getLights()) {
            // ambient light
            if (light instanceof XmlAmbientLight) {
                // Phong shader without illumination (only ambient)
                color = illuminateWithAmbientLight(surface, cameraRayIntersection, (XmlAmbientLight) light, color);

                // directional light
            } else if (light instanceof XmlParallelLight) {
                // illuminate (diffuse + specular)
                color = illuminateWithParallelLight(surface, cameraRayIntersection, (XmlParallelLight) light, color);

            } else if (light instanceof XmlPointLight) {
                // illuminate (diffuse + specular + reflectance)
                color = illuminateWithPointLight(surface, cameraRayIntersection, (XmlPointLight) light, color);
            }
        }

        // max bounces reached
        if (bounce > maxBounces) {
            return color;
        }

        // check surface reflectance
        if (reflectance > 0D) {
            final Ray reflectedRay = getReflectedRay(cameraRayIntersection);
            reflectedColor = traceRay(reflectedRay, bounce + 1).multiplyByFactor(reflectance);
        }

        // check surface transmittance
        if (transmittance > 0D) {
            final Ray refractedRay = getRefractedRay(cameraRayIntersection, refraction);
            refractedColor = traceRay(refractedRay, bounce + 1).multiplyByFactor(transmittance);
        }

        return color.multiplyByFactor(1 - reflectance - transmittance).addColor(reflectedColor).addColor(refractedColor);
    }

    private Color illuminateWithAmbientLight(final Surface surface, final Intersection cameraRayIntersection, final XmlAmbientLight light, final Color color) {
        final PhongShader shader = new PhongShader(light, surface, cameraRayIntersection);

        return color.addColor(shader.calculatePixelColor());
    }

    private Color illuminateWithParallelLight(final Surface surface, final Intersection cameraRayIntersection, final XmlParallelLight light, Color color) {

        // check for shadow
        final Intersection shadowRayIntersection = shadow.checkForShadow(cameraRayIntersection, light);

        // no shadow was detected
        if (shadowRayIntersection == null) {

            // calculate pixel color for each light with illumination (diffuse + specular)
            final PhongShader shader = new PhongShader(light, surface, cameraRayIntersection);

            color = color.addColor(shader.calculatePixelColor());

        } // else ignore

        return color;
    }

    private Color illuminateWithPointLight(final Surface surface, final Intersection cameraRayIntersection, final XmlPointLight light, Color color) {
        // check for shadow
        final Intersection shadowRayIntersection = shadow.checkForShadow(cameraRayIntersection, light);

        // no shadow was detected
        if (shadowRayIntersection == null) {
            // calculate pixel color for each light with illumination (diffuse + specular)
            final PhongShader shader = new PhongShader(light, surface, cameraRayIntersection);

            color = color.addColor(shader.calculatePixelColor());

        } // else ignore

        return color;
    }

    private Ray getReflectedRay(final Intersection cameraRayIntersection) {
        final Point intersectionPoint = cameraRayIntersection.getIntersectionPoint();

        // incident vector (inverted)
        final Vector lVector = cameraRayIntersection.getRayDirection().invert();

        // intersection normal
        final Vector nVector = cameraRayIntersection.getNormal();

        // reflection vector
        final Vector rVector = nVector.scaleByFactor(2 * (lVector).dotProduct(nVector)).subtractVector(lVector);

        final Ray reflectedRay = new Ray();
        reflectedRay.setOrigin(intersectionPoint);
        reflectedRay.setDirection(rVector.normalize());

        return reflectedRay;
    }

    private Ray getRefractedRay(final Intersection cameraRayIntersection, final double refraction) {
        final Point intersectionPoint = cameraRayIntersection.getIntersectionPoint();

        // incident ray direction pointing towards surface
        final Vector incidentVector = cameraRayIntersection.getRayDirection();
        Vector intersectionNormal = cameraRayIntersection.getNormal();

        double enterMedium = 1D;
        double leavingMedium = refraction;

        double theta = incidentVector.dotProduct(intersectionNormal);

        if (theta < 0D) {
            // the ray hits from the outside, we must flip the angle
            theta = -theta;

        } else { // ray hits from inside
            // normal direction is reversed and needs to be flipped
            intersectionNormal = intersectionNormal.invert();

            // enter/leaving media need to be swapped
            enterMedium = refraction;
            leavingMedium = 1D;
        }

        final double scalingFactor1 = enterMedium / leavingMedium;
        Vector a = incidentVector.addVector(intersectionNormal.scaleByFactor(theta));
        a = a.scaleByFactor(scalingFactor1);

        final double scalingFactor2 = Math.sqrt(1 - (scalingFactor1 * scalingFactor1) * (1 - (theta * theta)));

//        // handle case of total internal reflection
//        if (Double.isNaN(scalingFactor2)) {
//            return this.getReflectedRay(cameraRayIntersection);
//        }

        final Vector b = intersectionNormal.scaleByFactor(scalingFactor2);

        final Vector refractionDirection = a.subtractVector(b).normalize();

        final Ray refractedRay = new Ray();
        refractedRay.setOrigin(intersectionPoint);
        refractedRay.setDirection(refractionDirection);

        return refractedRay;
    }

}
