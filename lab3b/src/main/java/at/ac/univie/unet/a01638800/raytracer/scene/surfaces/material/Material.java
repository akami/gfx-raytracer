package at.ac.univie.unet.a01638800.raytracer.scene.surfaces.material;

import at.ac.univie.unet.a01638800.raytracer.geometry.Color;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlMaterial;

/**
 * Class representing any material
 */
public abstract class Material {

    // phong
    private final int shininessExponent;
    private final double ambientComponent;
    private final double diffuseComponent;
    private final double specularComponent;

    private final double reflectance;
    private final double transmittance;
    private final double refraction;

    protected Material(final XmlMaterial xmlMaterial) {
        shininessExponent = Integer.parseInt(xmlMaterial.getPhong().getExponent());
        ambientComponent = Double.parseDouble(xmlMaterial.getPhong().getKa());
        diffuseComponent = Double.parseDouble(xmlMaterial.getPhong().getKd());
        specularComponent = Double.parseDouble(xmlMaterial.getPhong().getKs());

        reflectance = Double.parseDouble(xmlMaterial.getReflectance().getR());
        transmittance = Double.parseDouble(xmlMaterial.getTransmittance().getT());
        refraction = Double.parseDouble(xmlMaterial.getRefraction().getIof());
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

    /**
     * Returns the color for the mapped u/v values for textures (texture mapping), or a color for solid materials
     *
     * @param u the mapped u value
     * @param v the mapped v value
     * @return the color for u and v
     */
    public abstract Color getColor(double u, double v);

}
