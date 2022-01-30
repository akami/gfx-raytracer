package at.ac.univie.unet.a01638800.raytracer.scene.surfaces.material;

import at.ac.univie.unet.a01638800.raytracer.geometry.Color;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlMaterialSolid;

/**
 * Class representing a solid material
 *
 * @see XmlMaterialSolid
 */
public class SolidMaterial extends Material {

    private final Color color;

    public SolidMaterial(final XmlMaterialSolid xmlMaterialSolid) {
        super(xmlMaterialSolid);

        color = new Color(
                Double.parseDouble(xmlMaterialSolid.getColor().getR()),
                Double.parseDouble(xmlMaterialSolid.getColor().getG()),
                Double.parseDouble(xmlMaterialSolid.getColor().getB())
        );
    }

    public Color getColor() {
        return color;
    }

    @Override
    public Color getColor(final double x, final double y) {
        return color;
    }

}
