package at.ac.univie.unet.a01638800.raytracer.phong;

import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Color;
import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Vector;
import at.ac.univie.unet.a01638800.raytracer.intersection.Intersection;
import at.ac.univie.unet.a01638800.raytracer.scene.MaterialSolid;
import at.ac.univie.unet.a01638800.raytracer.scene.ParallelLight;
import at.ac.univie.unet.a01638800.raytracer.scene.Scene;

/**
 * This class implements the phong illumination and shading model used in the raytraced scene.
 */
public class PhongShader {

    /** The lights in the scene */
    private final Scene.Lights lights;

    /** The material of the object to illuminate and shade */
    private final MaterialSolid materialSolid;

    /** The ray-object intersection data */
    private final Intersection intersection;

    /** The light color*/
    private Color lightColor;

    public PhongShader(Scene.Lights parsedLights, MaterialSolid materialSolid, Intersection intersection) {
        this.lights = parsedLights;
        this.materialSolid = materialSolid;
        this.intersection = intersection;
    }

    /**
     * This method calculates the pixel color in the following steps:
     * <ol>
     *     <li> Set up vectors needed for computing the color of the pixel </li>
     *     <li> compute the color components: ambient, diffuse, specular </li>
     *     <li> accumulating the components for each color value to get final pixel color </li>
     * </ol>
     * @return rgb array of pixel color
     */
    public double[] calculatePixelColor() {
        // set up rgb array
        double[] pixelColor = new double[3];

        // set up vectors
        Vector normalVector = this.getNormalVector();
        Vector pointToLightVector = this.getPointToLightVector();
        Vector pointToCameraVector = this.getPointToCameraVector();
        Vector reflectionVector = this.getReflectionVector(normalVector, pointToLightVector);

        // set up color components array (ambient, diffuse, specular)
        Color[] colorComponents = this.getColorComponents(normalVector, pointToLightVector, pointToCameraVector, reflectionVector);

        // accumulate each component red = red_ambient + red_diffuse + red_specular
        pixelColor[0] = colorComponents[0].getR() + colorComponents[1].getR() + colorComponents[2].getR();
        pixelColor[1] = colorComponents[0].getG() + colorComponents[1].getG() + colorComponents[2].getG();
        pixelColor[2] = colorComponents[0].getB() + colorComponents[1].getB() + colorComponents[2].getB();

        return pixelColor;
    }

    /**
     * Returns the normal from the intersection point.
     */
    private Vector getNormalVector() {
        return new Vector(
                this.intersection.getNormal().getX(),
                this.intersection.getNormal().getY(),
                this.intersection.getNormal().getZ()
        );
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

        if (this.lights.getLights().size() == 2) {
            // scene lighting is of type parallel light
            ParallelLight parallelLight = (ParallelLight) this.lights.getLights().get(1);

            pointToLightVector = new Vector(
                    Double.parseDouble(parallelLight.getDirection().getX()),
                    Double.parseDouble(parallelLight.getDirection().getY()),
                    Double.parseDouble(parallelLight.getDirection().getZ())
            );

            pointToLightVector = pointToLightVector.invert();

            // set light color
            this.lightColor = new Color(
                    Double.parseDouble(parallelLight.getColor().getR()),
                    Double.parseDouble(parallelLight.getColor().getG()),
                    Double.parseDouble(parallelLight.getColor().getB())
            );
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
     * @param normalVector the normal of the intersection point
     * @param pointToLightVector the point to light vector
     * @return the normalized reflection vector
     */
    private Vector getReflectionVector(Vector normalVector, Vector pointToLightVector) {
        Vector nlVector = normalVector.subtractVector(pointToLightVector);
        double nlScalar = normalVector.dotProduct(pointToLightVector);

        Vector reflectionVector = nlVector.scaleByFactor(2.0 * nlScalar);

        return reflectionVector.normalize();
    }

    /**
     * Computes the color components ambient, diffuse and specular.
     * @param normalVector the normal from the intersection point
     * @param pointToLightVector the vector from the intersection point to the light source
     * @param pointToCameraVector the vector from the intersection point to the camera
     * @param reflectionVector the reflection vector based on the light incident vector and normal
     * @return array of Color [ambient(r, g, b), diffuse(r, g, b), specular(r, g, b)]
     */
    private Color[] getColorComponents(Vector normalVector, Vector pointToLightVector, Vector pointToCameraVector, Vector reflectionVector) {

        // set up color array
        Color[] colorComponents = new Color[3];

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
        colorComponents[0] = ambient;
        colorComponents[1] = diffuse;
        colorComponents[2] = specular;

        return colorComponents;
    }

    /**
     * Computes the ambient color component by multiplying the object's color by the light intensity k_a given by the
     * parsed material.
     *
     * @param objectColor the object's color
     * @return the ambient color component for r, g and b
     */
    private Color getAmbientColorComponent(Color objectColor) {
        double materialAmbientComponent = Double.parseDouble(this.materialSolid.getPhong().getKa());

        return objectColor.multiplyByFactor(materialAmbientComponent);
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
     * @param objectColor object' color
     * @param normalVector intersection normal
     * @param pointToLightVector vector from intersection point to light source
     * @return Color containing only the diffuse color components for r, g and b
     */
    private Color getDiffuseColorComponent(Color objectColor, Vector normalVector, Vector pointToLightVector) {
        double materialDiffuseComponent = Double.parseDouble(this.materialSolid.getPhong().getKd());

        // max(0.0, n * l)
        double diffuseFactor = Math.max(0.0, normalVector.dotProduct(pointToLightVector));

        return objectColor
                .multiplyByFactor(diffuseFactor)
                .multiplyByFactor(materialDiffuseComponent);
    }

    /**
     * Computes the specular color component given the formula: <br><br>
     * k_s * max((r * v)^a, 0.0) <br><br>,
     *
     * where:
     * <ul>
     *     <li> k_s: the material specular component given by the parsed material</li>
     *     <li> r: reflection vector </li>
     *     <li> v: point to camera vector </li>
     *     <li> a: shininess exponent </li>
     * </ul>
     *
     * Note that the specular color component is only dependent on the light color and not on the object's color.
     *
     * @param normalVector intersection normal
     * @param pointToLightVector vector from intersection point to light source
     * @param pointToCameraVector vector from intersection point to camera
     * @param reflectionVector reflection vector based on incident of light onto the intersection point
     * @return Color containing only specular color components for r, g and b
     */
    private Color getSpecularColorComponent(Vector normalVector, Vector pointToLightVector, Vector pointToCameraVector, Vector reflectionVector) {
        double materialSpecularComponent = Double.parseDouble(this.materialSolid.getPhong().getKs());
        double shininess = Double.parseDouble(this.materialSolid.getPhong().getExponent());

        // max((r * v)^a, 0.0)
        double specularFactor = Math.max(0.0, Math.pow(reflectionVector.dotProduct(pointToCameraVector), shininess));

        Color specular = this.lightColor
                .multiplyByFactor(materialSpecularComponent)
                .multiplyByFactor(specularFactor);

        if (normalVector.dotProduct(pointToLightVector) < 0.0) {
            specular = new Color(0.0, 0.0, 0.0);
        }

        return specular;
    }
}