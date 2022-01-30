package at.ac.univie.unet.a01638800.raytracer.scene.surfaces.material;

import at.ac.univie.unet.a01638800.raytracer.geometry.Color;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlMaterialTextured;
import at.ac.univie.unet.a01638800.utils.RgbMapper;
import java.awt.image.BufferedImage;

/**
 * Class representing a textured material
 *
 * @see XmlMaterialTextured
 */
public class TexturedMaterial extends Material {

    private final BufferedImage texture;

    public TexturedMaterial(final XmlMaterialTextured xmlMaterialTextured, final BufferedImage texture) {
        super(xmlMaterialTextured);

        this.texture = texture;
    }

    @Override
    public Color getColor(final double u, final double v) {
        final int x = (int) (u * (texture.getWidth())) % texture.getWidth();
        final int y = (int) (v * (texture.getHeight())) % texture.getHeight();

        final int rgb = texture.getRGB(x, y);
        final double[] color = RgbMapper.mapRgbToColor(rgb);

        return new Color(color[0], color[1], color[2]);
    }

}
