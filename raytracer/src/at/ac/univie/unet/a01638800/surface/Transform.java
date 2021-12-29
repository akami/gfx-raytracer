package at.ac.univie.unet.a01638800.surface;

import at.ac.univie.unet.a01638800.Coordinate;

public class Transform {
    private Coordinate translate;
    private Coordinate scale;
    private int rotateX;
    private int rotateY;
    private int rotateZ;

    public Transform(Coordinate translate, Coordinate scale, int rotateX, int rotateY, int rotateZ) {
        this.translate = translate;
        this.scale = scale;
        this.rotateX = rotateX;
        this.rotateY = rotateY;
        this.rotateZ = rotateZ;
    }

    public Coordinate getTranslate() {
        return translate;
    }

    public void setTranslate(Coordinate translate) {
        this.translate = translate;
    }

    public Coordinate getScale() {
        return scale;
    }

    public void setScale(Coordinate scale) {
        this.scale = scale;
    }

    public int getRotateX() {
        return rotateX;
    }

    public void setRotateX(int rotateX) {
        this.rotateX = rotateX;
    }

    public int getRotateY() {
        return rotateY;
    }

    public void setRotateY(int rotateY) {
        this.rotateY = rotateY;
    }

    public int getRotateZ() {
        return rotateZ;
    }

    public void setRotateZ(int rotateZ) {
        this.rotateZ = rotateZ;
    }
}
