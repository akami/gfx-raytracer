package at.ac.univie.unet.a01638800.light;

import at.ac.univie.unet.a01638800.Color;
import at.ac.univie.unet.a01638800.Coordinate;
import at.ac.univie.unet.a01638800.Light;

public class PointLight extends Light {
    private Coordinate position;

    public PointLight(Color color, Coordinate position) {
        this.color = color;
        this.position = position;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }
}
