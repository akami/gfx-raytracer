package at.ac.univie.unet.a01638800.surface;

import at.ac.univie.unet.a01638800.Coordinate;

public class Sphere extends Surface{
    private int radius;
    private Coordinate position;

    public Sphere(int radius, Coordinate position) {
        this.radius = radius;
        this.position = position;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }
}
