package at.ac.univie.unet.a01638800.raytracer.scene.phong;

import at.ac.univie.unet.a01638800.raytracer.geometry.Point;
import at.ac.univie.unet.a01638800.raytracer.geometry.Ray;
import at.ac.univie.unet.a01638800.raytracer.geometry.Vector;
import at.ac.univie.unet.a01638800.raytracer.scene.intersection.Intersection;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.Surface;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlLight;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlParallelLight;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlPointLight;

/**
 * This class implements the shadow intersection test. It computes the shadow ray based on the results of a camera ray
 * intersection test in order to find out whether a surface lies in shadow (has no phong illumination besides ambient light)
 * or not.
 *
 * This class and its checkForShadow() method is used in the raytracing algorithm of the scene.
 *
 * @see at.ac.univie.unet.a01638800.raytracer.scene.Scene
 * @see PhongShader
 */
public class Shadow {
    private final Surface[] surfaces;

    public Shadow(Surface[] surfaces){
        this.surfaces = surfaces;
    }

    public Intersection checkForShadow(final Intersection cameraRayIntersection, final XmlLight light) {
        Intersection shadowIntersection = null;
        final Ray shadowRay = new Ray();

        // set shadow ray origin as the intersection point of the camera ray intersection test
        shadowRay.setOrigin(cameraRayIntersection.getIntersectionPoint());

        if (light instanceof XmlParallelLight) {

            // light is of type parallel light
            XmlParallelLight parallelLight = (XmlParallelLight) light;

            // the shadow ray direction is the inverse direction of the parallel light direction
            shadowRay.setDirection(new Vector(
                    Double.parseDouble(parallelLight.getDirection().getX()),
                    Double.parseDouble(parallelLight.getDirection().getY()),
                    Double.parseDouble(parallelLight.getDirection().getZ())
            ).invert().normalize());

        } else if (light instanceof XmlPointLight) {
            // light is of type point light
            XmlPointLight pointLight = (XmlPointLight) light;

            Point pointLightPosition = new Point(
                    Double.parseDouble(pointLight.getPosition().getX()),
                    Double.parseDouble(pointLight.getPosition().getY()),
                    Double.parseDouble(pointLight.getPosition().getZ())
            );

            // the direction is the vector from the intersection point to the point light position
            shadowRay.setDirection(pointLightPosition.subtractPoint(shadowRay.getOrigin()));

            // set max distance to distance between light source and ray origin
            shadowRay.setMaxDistance(shadowRay.getDirection().getLength());

            // normalize direction vector
            shadowRay.setDirection(shadowRay.getDirection().normalize());
        }

        // Test if shadow ray intersects with other surfaces. If yes, the surface lies in shadow.
        for (final Surface surface : surfaces) {
            shadowIntersection = surface.intersectionDetected(shadowRay);

            if (shadowIntersection != null) {
                break;
            }
        }

        // ignore intersections that are larger than shadow max distance
        if(shadowIntersection != null && shadowIntersection.getT() > shadowRay.getMaxDistance()) {
            return null;
        }

        return shadowIntersection;
    }
}
