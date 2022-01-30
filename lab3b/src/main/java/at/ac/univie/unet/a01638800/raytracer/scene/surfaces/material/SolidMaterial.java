package at.ac.univie.unet.a01638800.raytracer.scene.surfaces.material;

import at.ac.univie.unet.a01638800.raytracer.geometry.Color;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlMaterialSolid;

// TODO documentation
public class SolidMaterial extends Material {
    private final Color color;

    public SolidMaterial(final XmlMaterialSolid xmlMaterialSolid) {
        super(xmlMaterialSolid);

        this.color = new Color(
                Double.parseDouble(xmlMaterialSolid.getColor().getR()),
                Double.parseDouble(xmlMaterialSolid.getColor().getG()),
                Double.parseDouble(xmlMaterialSolid.getColor().getB())
        );
    }

    public Color getColor() {
        return this.color;
    }

    @Override
    public Color getColor(double x, double y) {
        return color;
    }
}
