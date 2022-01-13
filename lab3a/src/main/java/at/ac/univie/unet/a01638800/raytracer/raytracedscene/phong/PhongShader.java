package at.ac.univie.unet.a01638800.raytracer.raytracedscene.phong;

import at.ac.univie.unet.a01638800.raytracer.geometry.Color;
import at.ac.univie.unet.a01638800.raytracer.geometry.Vector;
import at.ac.univie.unet.a01638800.raytracer.raytracedscene.intersection.Intersection;
import at.ac.univie.unet.a01638800.raytracer.scene.Light;
import at.ac.univie.unet.a01638800.raytracer.scene.MaterialSolid;
import at.ac.univie.unet.a01638800.raytracer.scene.ParallelLight;

/**
 * This class implements the phong illumination and shading model used in the raytraced scene.
 */
public class PhongShader {

    /**
     * The lights in the scene
     */
    private final Light light;

    /**
     * The material of the object to illuminate and shade
     */
    private final MaterialSolid materialSolid;

    /**
     * The ray-object intersection data
     */
    private final Intersection intersection;

    private final boolean illuminate;

    /**
     * The light color
     */
    private Color lightColor;

    public PhongShader(Light light, MaterialSolid materialSolid, Intersection intersection, boolean illuminate) {
        this.light = light;
        this.materialSolid = materialSolid;
        this.intersection = intersection;
        this.illuminate = illuminate;

        // set light color
        this.lightColor = new Color(
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
        Vector normalVector = this.intersection.getNormal();
        Vector pointToLightVector = this.getPointToLightVector();
        Vector pointToCameraVector = this.getPointToCameraVector();

        Vector reflectionVector = this.getReflectionVector(normalVector, pointToLightVector);

        return this.getColorComponents(normalVector, pointToLightVector, pointToCameraVector, reflectionVector);
    }

    /**
     * Returns the normalized vector from the intersection point to the light source. <br>
     * There are different types of light sources:
     * <ul>
     *     <li>Ambient light: lights the whole scene. Returns a default vector since ambient light does not have a position
     *     or direction.</li>
     *     <li>Parallel light: a distant light source with a color and a direction. The point to light vector is given
     *     by the inverse light direction of the source.</li>
     * </ul>
     */
    private Vector getPointToLightVector() {
        Vector pointToLightVector = new Vector();

        if (this.light instanceof ParallelLight) {
            // scene lighting is of type parallel light
            ParallelLight parallelLight = (ParallelLight) this.light;

            pointToLightVector = new Vector(
                    Double.parseDouble(parallelLight.getDirection().getX()),
                    Double.parseDouble(parallelLight.getDirection().getY()),
                    Double.parseDouble(parallelLight.getDirection().getZ())
            );

            pointToLightVector = pointToLightVector.invert();

        }

        return pointToLightVector.normalize();
    }

    /**
     * Returns the normalized vector from the intersection point to the camera. This is given by the inverse direction of
     * the camera rays.
     */
    private Vector getPointToCameraVector() {
        Vector pointToCameraVector = this.intersection.getRayDirection();
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
    private Vector getReflectionVector(Vector normalVector, Vector pointToLightVector) {
        double nlScalar = normalVector.dotProduct(pointToLightVector);

        Vector reflectionVector = normalVector.scaleByFactor(2.0 * nlScalar).subtractVector(pointToLightVector);

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
    private Color getColorComponents(Vector normalVector, Vector pointToLightVector, Vector pointToCameraVector, Vector reflectionVector) {
        // set up color array
        Color colorComponents = new Color();

        // get color of parsed object material
        Color objectColor = new Color(
                Double.parseDouble(this.materialSolid.getColor().getR()),
                Double.parseDouble(this.materialSolid.getColor().getG()),
                Double.parseDouble(this.materialSolid.getColor().getB())
        );

        // compute color components individually
        Color ambient = this.getAmbientColorComponent(objectColor);
        Color diffuse = this.getDiffuseColorComponent(objectColor, normalVector, pointToLightVector);
        Color specular = this.getSpecularColorComponent(normalVector, pointToLightVector, pointToCameraVector, reflectionVector);

        // fill color array with components
        return colorComponents.addColor(ambient).addColor(diffuse).addColor(specular);
    }

    /**
     * Computes the ambient color component by multiplying the object's color by the light intensity k_a given by the
     * parsed material.
     *
     * @param objectColor the object's color
     * @return the ambient color component for r, g and b
     *
     * Note that the ambient component is only calculated if the pixel should not be illuminated by the diffuse and
     * specular components. This happens when the given light is of type AmbientLight, for directional lights such as
     * the ParallelLight, the ambient component does not have to be computed again.
     */
    private Color getAmbientColorComponent(Color objectColor) {
        // only compute for ambient light
        if (!illuminate) {
            double materialAmbientComponent = Double.parseDouble(this.materialSolid.getPhong().getKa());

            return objectColor.multiplyByFactor(materialAmbientComponent);
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
    private Color getDiffuseColorComponent(Color objectColor, Vector normalVector, Vector pointToLightVector) {
        double materialDiffuseComponent = Double.parseDouble(this.materialSolid.getPhong().getKd());

        // max(0.0, n * l)
        double diffuseFactor = Math.min(Math.max(0.0, normalVector.dotProduct(pointToLightVector)), 1.0);

        return objectColor
                .multiplyByFactor(materialDiffuseComponent * diffuseFactor);
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
    private Color getSpecularColorComponent(Vector normalVector, Vector pointToLightVector, Vector pointToCameraVector, Vector reflectionVector) {
        double materialSpecularComponent = Double.parseDouble(this.materialSolid.getPhong().getKs());
        double shininess = Double.parseDouble(this.materialSolid.getPhong().getExponent());

        // max((r * v)^a, 0.0)
        double specularFactor = Math.min(Math.max(0.0, Math.pow(reflectionVector.dotProduct(pointToCameraVector), shininess)), 1.0);

        Color specular = this.lightColor
                .multiplyByFactor(materialSpecularComponent)
                .multiplyByFactor(specularFactor);

        if (normalVector.dotProduct(pointToLightVector) < 0.0) {
            specular = new Color(0.0, 0.0, 0.0);
        }

        return specular;
    }
}