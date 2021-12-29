package at.ac.univie.unet.a01638800.light;

import at.ac.univie.unet.a01638800.Color;
import at.ac.univie.unet.a01638800.Coordinate;
import at.ac.univie.unet.a01638800.Light;

public class ParallelLight extends Light {
    private Coordinate direction;

    public ParallelLight(Color color, Coordinate direction) {
        this.color = color;
        this.direction = direction;
    }

    public Coordinate getDirection() {
        return direction;
    }

    public void setDirection(Coordinate direction) {
        this.direction = direction;
    }
}
