package at.ac.univie.unet.a01638800.surface.material;

import at.ac.univie.unet.a01638800.Color;

public class MaterialSolid extends Material{
    private Color color;

    public MaterialSolid(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
