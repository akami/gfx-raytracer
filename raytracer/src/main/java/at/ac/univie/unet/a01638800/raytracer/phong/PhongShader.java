package at.ac.univie.unet.a01638800.raytracer.phong;

import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Color;
import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Normal;
import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Vector;
import at.ac.univie.unet.a01638800.raytracer.intersection.Intersection;
import at.ac.univie.unet.a01638800.raytracer.scene.MaterialSolid;
import at.ac.univie.unet.a01638800.raytracer.scene.ParallelLight;
import at.ac.univie.unet.a01638800.raytracer.scene.Scene;

public class PhongShader {
    private final Scene.Lights lights;
    private final MaterialSolid materialSolid;
    private final Intersection intersection;

    private Color lightColor;

    public PhongShader(Scene.Lights parsedLights, MaterialSolid materialSolid, Intersection intersection) {
        this.lights = parsedLights;
        this.materialSolid = materialSolid;
        this.intersection = intersection;
    }

    public double[] calculatePixelColor() {
        double[] pixelColor = new double[3];

        Vector normalVector = new Vector(
                this.intersection.getNormal().getX(),
                this.intersection.getNormal().getY(),
                this.intersection.getNormal().getZ()
        );

        Vector pointToLightVector = this.getPointToLightVector();
        pointToLightVector.normalize();

        Vector pointToCameraVector = new Vector();

        Vector reflectionVector = normalVector.subtractVector(pointToLightVector);
        reflectionVector = reflectionVector.scaleByFactor(2.0 * normalVector.dotProduct(pointToLightVector));

        Color[] colorComponents = this.computeColorComponents(normalVector, pointToLightVector, pointToCameraVector, reflectionVector);

        pixelColor[0] = colorComponents[0].getR() + colorComponents[1].getR();
        pixelColor[1] = colorComponents[0].getG() + colorComponents[1].getG();
        pixelColor[2] = colorComponents[0].getB() + colorComponents[1].getB();

        return pixelColor;
    }

    private Vector getPointToLightVector() {
        Vector pointToLightVector = new Vector();

        if (this.lights.getLights().size() == 2) {
            // scene lighting is of type parallel light
            ParallelLight parallelLight = (ParallelLight) this.lights.getLights().get(1);

            pointToLightVector = new Vector(
                    intersection.getIntersectionPoint(),
                    -Double.parseDouble(parallelLight.getDirection().getX()),
                    -Double.parseDouble(parallelLight.getDirection().getY()),
                    -Double.parseDouble(parallelLight.getDirection().getZ())
            );
            pointToLightVector.invert();

        } else if (this.lights.getLights().size() > 2) {
            // TODO implement
        }

        return pointToLightVector;
    }

    private Color[] computeColorComponents(Vector normalVector, Vector pointToLightVector, Vector pointToCameraVector, Vector reflectionVector) {
        Color[] colorComponents = new Color[3];

        Color objectColor = new Color(
                Double.parseDouble(this.materialSolid.getColor().getR()),
                Double.parseDouble(this.materialSolid.getColor().getG()),
                Double.parseDouble(this.materialSolid.getColor().getB())
        );

        Color ambient = objectColor.multiplyByFactor(Double.parseDouble(this.materialSolid.getPhong().getKa()));

        double diffuseFactor = Math.max(0.0, normalVector.dotProduct(pointToLightVector));
        Color diffuse = objectColor.multiplyByFactor(diffuseFactor);
        diffuse = diffuse.multiplyByFactor(Double.parseDouble(this.materialSolid.getPhong().getKd()));

        colorComponents[0] = ambient;
        colorComponents[1] = diffuse;

        return colorComponents;
    }
}