package at.ac.univie.unet.a01638800.raytracer.scene.surfaces.material;

import at.ac.univie.unet.a01638800.raytracer.geometry.Color;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlMaterial;

// TODO documentation
public abstract class Material {
    // phong
    private final int shininessExponent;
    private final double ambientComponent;
    private final double diffuseComponent;
    private final double specularComponent;

    private final double reflectance;
    private final double transmittance;
    private final double refraction;

    protected Material(XmlMaterial xmlMaterial) {
        this.shininessExponent = Integer.parseInt(xmlMaterial.getPhong().getExponent());
        this.ambientComponent = Double.parseDouble(xmlMaterial.getPhong().getKa());
        this.diffuseComponent = Double.parseDouble(xmlMaterial.getPhong().getKd());
        this.specularComponent = Double.parseDouble(xmlMaterial.getPhong().getKs());

        this.reflectance = Double.parseDouble(xmlMaterial.getReflectance().getR());
        this.transmittance = Double.parseDouble(xmlMaterial.getTransmittance().getT());
        this.refraction = Double.parseDouble(xmlMaterial.getRefraction().getIof());
    }

    public int getShininessExponent() {
        return shininessExponent;
    }

    public double getAmbientComponent() {
        return ambientComponent;
    }

    public double getDiffuseComponent() {
        return diffuseComponent;
    }

    public double getSpecularComponent() {
        return specularComponent;
    }

    public double getReflectance() {
        return reflectance;
    }

    public double getTransmittance() {
        return transmittance;
    }

    public double getRefraction() {
        return refraction;
    }

    public abstract Color getColor(double u, double v);
}
