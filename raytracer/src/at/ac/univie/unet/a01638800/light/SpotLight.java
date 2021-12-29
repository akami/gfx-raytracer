package at.ac.univie.unet.a01638800.light;

import at.ac.univie.unet.a01638800.Coordinate;
import at.ac.univie.unet.a01638800.Light;

public class SpotLight extends Light {
    private Coordinate position;
    private Coordinate direction;
    private int alpha1;
    private int alpha2;

    public SpotLight(Coordinate position, Coordinate direction, int alpha1, int alpha2) {
        this.position = position;
        this.direction = direction;
        this.alpha1 = alpha1;
        this.alpha2 = alpha2;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }

    public Coordinate getDirection() {
        return direction;
    }

    public void setDirection(Coordinate direction) {
        this.direction = direction;
    }

    public int getAlpha1() {
        return alpha1;
    }

    public void setAlpha1(int alpha1) {
        this.alpha1 = alpha1;
    }

    public int getAlpha2() {
        return alpha2;
    }

    public void setAlpha2(int alpha2) {
        this.alpha2 = alpha2;
    }
}
