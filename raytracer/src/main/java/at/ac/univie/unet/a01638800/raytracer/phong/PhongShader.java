package at.ac.univie.unet.a01638800.raytracer.phong;

import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Color;
import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Ray;
import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Vector;
import at.ac.univie.unet.a01638800.raytracer.intersection.Intersection;
import at.ac.univie.unet.a01638800.raytracer.scene.MaterialSolid;
import at.ac.univie.unet.a01638800.raytracer.scene.ParallelLight;
import at.ac.univie.unet.a01638800.raytracer.scene.Scene;

public class PhongShader {
    private final Scene.Lights lights;
    private final MaterialSolid materialSolid;
    private final Intersection intersection;

    private LightMode lightMode;

    private final Vector normalVector;
    private final Ray lightRay;
    private Color lightColor;
    private final Vector pointToCameraVector;

    public PhongShader(Scene.Lights lights, MaterialSolid materialSolid, Intersection intersection) {
        this.lights = lights;
        this.materialSolid = materialSolid;
        this.intersection = intersection;

        // get normal of intersection point
        this.normalVector = new Vector(
                this.intersection.getNormal().getX(),
                this.intersection.getNormal().getY(),
                this.intersection.getNormal().getZ()
        );

        this.lightRay = new Ray();
        this.lightColor = new Color();

        // get vector from intersection point to camera, which is the inverted ray direction
        this.pointToCameraVector = this.intersection.getRayDirection().invert();
        this.pointToCameraVector.normalize();

        this.setLightMode();
    }

    public double[] calculatePixelColor() {
        double[] pixelColor = new double[3];

        Vector reflectionVector = this.setupReflectionVector();

        Color[] colorComponents = this.computeColorComponents(
                lightRay.getDirection(),
                pointToCameraVector,
                reflectionVector,
                lightColor);

        pixelColor[0] = colorComponents[0].getR() + colorComponents[1].getR() + colorComponents[2].getR();
        pixelColor[1] = colorComponents[0].getG() + colorComponents[1].getG() + colorComponents[2].getG();
        pixelColor[2] = colorComponents[0].getB() + colorComponents[1].getB() + colorComponents[2].getB();

        return pixelColor;
    }

    private void setLightMode() {
        if (this.lights.getLights().size() >= 2 && this.lights.getLights().size() < 3) {
            this.lightMode = LightMode.PARALLEL_LIGHT;

            this.setupParallelLighting();

        } else if (this.lights.getLights().size() >= 3) {
            this.lightMode = LightMode.SPOTLIGHTS;
        } else {
            this.lightMode = LightMode.ONLY_AMBIENT;
        }
    }

    private void setupParallelLighting() {
        // get parsed parallel light
        ParallelLight parallelLight = (ParallelLight) this.lights.getLights()
                .stream()
                .filter(l -> l instanceof ParallelLight)
                .findFirst()
                .get();

        // set origin to intersection point
        lightRay.setOrigin(intersection.getIntersectionPoint());

        // get inverted direction (from intersection point to light)
        lightRay.setDirection(new Vector(
                -Double.parseDouble(parallelLight.getDirection().getX()),
                -Double.parseDouble(parallelLight.getDirection().getY()),
                -Double.parseDouble(parallelLight.getDirection().getZ())
        ));
        lightRay.getDirection().normalize();

        lightColor = new Color(
                Double.parseDouble(parallelLight.getColor().getR()),
                Double.parseDouble(parallelLight.getColor().getG()),
                Double.parseDouble(parallelLight.getColor().getB())
        );
    }

    private Vector setupReflectionVector() {
        Vector reflectionVector = normalVector.subtractVector(lightRay.getDirection());
        reflectionVector = reflectionVector.scaleByFactor(2.0 * lightRay.getDirection().dotProduct(normalVector));

        reflectionVector.normalize();

        return reflectionVector;
    }

    private Color[] computeColorComponents(Vector pointToLightVector, Vector pointToCameraVector, Vector reflectionVector, Color lightColor) {
        Color[] colorComponents = new Color[3];

        Color objectColor = new Color(
                Double.parseDouble(this.materialSolid.getColor().getR()),
                Double.parseDouble(this.materialSolid.getColor().getG()),
                Double.parseDouble(this.materialSolid.getColor().getB()));

        Color ambient = objectColor.multiplyByFactor(Double.parseDouble(this.materialSolid.getPhong().getKa()));

        Color diffuse = objectColor.multiplyByFactor(Double.parseDouble(this.materialSolid.getPhong().getKd()));
        double diffuseFactor = Math.max(0.0, normalVector.dotProduct(this.intersection.getNormal()));
        diffuse = diffuse.multiplyByFactor(diffuseFactor);

        Color specular = lightColor.multiplyByFactor(Double.parseDouble(this.materialSolid.getPhong().getKs()));
        double specularFactor = reflectionVector.dotProduct(pointToCameraVector);
        specularFactor = Math.max(0.0, specularFactor);
        specular = specular.multiplyByFactor(Math.pow(specularFactor, Double.parseDouble(this.materialSolid.getPhong().getExponent())));

        if (normalVector.dotProduct(pointToLightVector) < 0.0) {
            specular = new Color(0.0, 0.0, 0.0);
        }

        colorComponents[0] = ambient;
        colorComponents[1] = diffuse;
        colorComponents[2] = specular;

        return colorComponents;
    }
}
