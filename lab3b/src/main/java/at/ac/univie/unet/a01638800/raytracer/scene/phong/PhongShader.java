package at.ac.univie.unet.a01638800.raytracer.scene.phong;

import at.ac.univie.unet.a01638800.raytracer.geometry.Color;
import at.ac.univie.unet.a01638800.raytracer.geometry.Point;
import at.ac.univie.unet.a01638800.raytracer.geometry.Vector;
import at.ac.univie.unet.a01638800.raytracer.scene.intersection.Intersection;
import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.Surface;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlAmbientLight;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlLight;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlParallelLight;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlPointLight;

/**
 * This class implements the phong illumination and shading model used in the raytraced scene.
 */
public class PhongShader {

    /**
     * The lights in the scene
     */
    private final XmlLight light;

    /**
     * The surface to illuminate and shade
     */
    private final Surface surface;

    /**
     * The ray-object intersection data
     */
    private final Intersection intersection;

    /**
     * The light color
     */
    private final Color lightColor;

    public PhongShader(final XmlLight light, final Surface surface, final Intersection intersection) {
        this.light = light;
        this.surface = surface;
        this.intersection = intersection;

        // set light color
        lightColor = new Color(
                Double.parseDouble(light.getColor().getR()),
                Double.parseDouble(light.getColor().getG()),
                Double.parseDouble(light.getColor().getB())
        );
    }

    /**
     * This method calculates the pixel color in the following steps:
     * <ol>
     *     <li> Set up vectors needed for computing the color of the pixel </li>
     *     <li> compute the color components: ambient, diffuse, specular </li>
     *     <li> accumulating the components for each color value to get final pixel color </li>
     * </ol>
     *
     * @return Pixel color
     */
    public Color calculatePixelColor() {
        // set up vectors
        final Vector normalVector = intersection.getNormal();
        final Vector pointToLightVector = getPointToLightVector();
        final Vector pointToCameraVector = getPointToCameraVector();

        final Vector reflectionVector = getReflectionVector(normalVector, pointToLightVector);

        return getColorComponents(normalVector, pointToLightVector, pointToCameraVector, reflectionVector);
    }

    /**
     * Returns the normalized vector from the intersection point to the light source. <br>
     * There are different types of light sources:
     * <ul>
     *     <li>Ambient light: lights the whole scene. Returns a default vector since ambient light does not have a position
     *     or direction.</li>
     *     <li>Parallel light: a distant light source with a color and a direction. The point to light vector is given
     *     by the inverse light direction of the source.</li>
     *     <li>Point Light: a light source with a position that emits light in all directions. The point to light vector
     *     is given by the vector from the intersection point to the light position. </li>
     * </ul>
     */
    private Vector getPointToLightVector() {
        Vector pointToLightVector = new Vector();

        if (light instanceof XmlParallelLight) {
            // scene lighting is of type parallel light
            final XmlParallelLight parallelLight = (XmlParallelLight) light;

            pointToLightVector = new Vector(
                    Double.parseDouble(parallelLight.getDirection().getX()),
                    Double.parseDouble(parallelLight.getDirection().getY()),
                    Double.parseDouble(parallelLight.getDirection().getZ())
            );

            pointToLightVector = pointToLightVector.invert();
        } else if (light instanceof XmlPointLight) {
            // scene lighting is of type point light
            final XmlPointLight pointLight = (XmlPointLight) light;

            final Point pointLightPosition = new Point(
                    Double.parseDouble(pointLight.getPosition().getX()),
                    Double.parseDouble(pointLight.getPosition().getY()),
                    Double.parseDouble(pointLight.getPosition().getZ())
            );

            pointToLightVector = pointLightPosition.subtractPoint(intersection.getIntersectionPoint());
        }

        return pointToLightVector.normalize();
    }

    /**
     * Returns the normalized vector from the intersection point to the camera. This is given by the inverse direction of
     * the camera rays.
     */
    private Vector getPointToCameraVector() {
        Vector pointToCameraVector = intersection.getRayDirection();
        pointToCameraVector = pointToCameraVector.invert();

        return pointToCameraVector.normalize();
    }

    /**
     * The reflection vector is computed by: <br><br>
     * r = 2 * (n * l) * n - l
     *
     * @param normalVector       the normal of the intersection point
     * @param pointToLightVector the point to light vector
     * @return the normalized reflection vector
     */
    private Vector getReflectionVector(final Vector normalVector, final Vector pointToLightVector) {
        final double nlScalar = normalVector.dotProduct(pointToLightVector);

        final Vector reflectionVector = normalVector.scaleByFactor(2.0 * nlScalar).subtractVector(pointToLightVector);

        return reflectionVector.normalize();
    }

    /**
     * Computes the color components ambient, diffuse and specular.
     *
     * @param normalVector        the normal from the intersection point
     * @param pointToLightVector  the vector from the intersection point to the light source
     * @param pointToCameraVector the vector from the intersection point to the camera
     * @param reflectionVector    the reflection vector based on the light incident vector and normal
     * @return The final Color accumulating ambient, diffuse and specular components
     */
    private Color getColorComponents(final Vector normalVector, final Vector pointToLightVector, final Vector pointToCameraVector, final Vector reflectionVector) {
        // set up color array
        final Color colorComponents = new Color();
        final Color objectColor = surface.getColor(intersection);

        // compute color components individually
        final Color ambient = getAmbientColorComponent(objectColor);
        final Color diffuse = getDiffuseColorComponent(objectColor, normalVector, pointToLightVector);
        final Color specular = getSpecularColorComponent(normalVector, pointToLightVector, pointToCameraVector, reflectionVector);

        // fill color array with components
        return colorComponents.addColor(ambient).addColor(diffuse).addColor(specular);
    }

    /**
     * Computes the ambient color component by multiplying the object's color by the light intensity k_a given by the
     * parsed material.
     *
     * @param objectColor the object's color
     * @return the ambient color component for r, g and b
     *         <p>
     *         Note that the ambient component is only calculated if the pixel should not be illuminated by the diffuse and
     *         specular components. This happens when the given light is of type AmbientLight, for directional lights such as
     *         the ParallelLight, the ambient component does not have to be computed again.
     */
    private Color getAmbientColorComponent(final Color objectColor) {
        // only compute for ambient light
        if (light instanceof XmlAmbientLight) {
            return objectColor.multiplyByColor(lightColor).multiplyByFactor(surface.getMaterial().getAmbientComponent());
        }

        return new Color();
    }

    /**
     * Computes the diffuse color component by multiplying the object's color with the diffuse factor and the material
     * diffuse component:
     *
     * <ul>
     *     <li> object's color: given by the parsed object material </li>
     *     <li> diffuse factor: given by the dot product of intersection normal and point to light vector. Only factors
     *     that are pointing outwards the object are considered: <br><br>
     *     max(0.0, n * l)</li>
     * </ul>
     *
     * @param objectColor        object' color
     * @param normalVector       intersection normal
     * @param pointToLightVector vector from intersection point to light source
     * @return Color containing only the diffuse color components for r, g and b
     */
    private Color getDiffuseColorComponent(final Color objectColor, final Vector normalVector, final Vector pointToLightVector) {
        // max(0.0, n * l)
        final double diffuseFactor = Math.min(Math.max(0.0, normalVector.dotProduct(pointToLightVector)), 1.0);

        return objectColor
                .multiplyByColor(lightColor)
                .multiplyByFactor(surface.getMaterial().getDiffuseComponent())
                .multiplyByFactor(diffuseFactor);
    }

    /**
     * Computes the specular color component given the formula: <br><br>
     * k_s * max((r * v)^a, 0.0) <br><br>,
     * <p>
     * where:
     * <ul>
     *     <li> k_s: the material specular component given by the parsed material</li>
     *     <li> r: reflection vector </li>
     *     <li> v: point to camera vector </li>
     *     <li> a: shininess exponent </li>
     * </ul>
     * <p>
     * Note that the specular color component is only dependent on the light color and not on the object's color.
     *
     * @param normalVector        intersection normal
     * @param pointToLightVector  vector from intersection point to light source
     * @param pointToCameraVector vector from intersection point to camera
     * @param reflectionVector    reflection vector based on incident of light onto the intersection point
     * @return Color containing only specular color components for r, g and b
     */
    private Color getSpecularColorComponent(final Vector normalVector, final Vector pointToLightVector, final Vector pointToCameraVector, final Vector reflectionVector) {
        // max((r * v)^a, 0.0)
        final double specularFactor = Math.min(
                Math.max(
                        0.0,
                        Math.pow(reflectionVector.dotProduct(pointToCameraVector), surface.getMaterial().getShininessExponent()))
                , 1.0);

        Color specular = lightColor
                .multiplyByFactor(surface.getMaterial().getSpecularComponent())
                .multiplyByFactor(specularFactor);

        if (normalVector.dotProduct(pointToLightVector) < 0.0) {
            specular = new Color(0.0, 0.0, 0.0);
        }

        return specular;
    }

}