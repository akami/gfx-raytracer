package at.ac.univie.unet.a01638800.raytracer.scene.surfaces.material;

import at.ac.univie.unet.a01638800.raytracer.geometry.Color;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlMaterialTextured;
import at.ac.univie.unet.a01638800.utils.RgbMapper;

import java.awt.image.BufferedImage;

// TODO documentation
public class TexturedMaterial extends Material {
    private BufferedImage texture;

    public TexturedMaterial(final XmlMaterialTextured xmlMaterialTextured, BufferedImage texture) {
        super(xmlMaterialTextured);

        this.texture = texture;
    }

    @Override
    public Color getColor(double x, double y) {
        int rgb = texture.getRGB((int) (x * (texture.getWidth())), (int) (y * (texture.getHeight())));
        double[] color = RgbMapper.mapRgbToColor(rgb);

        return new Color(color[0], color[1], color[2]);
    }
}
