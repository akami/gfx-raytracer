package at.ac.univie.unet.a01638800.surface.material;

public class Phong {
    private float ka;
    private float kd;
    private float ks;
    private int exponent;

    public Phong(float ka, float kd, float ks, int exponent) {
        this.ka = ka;
        this.kd = kd;
        this.ks = ks;
        this.exponent = exponent;
    }

    public float getKa() {
        return ka;
    }

    public void setKa(float ka) {
        this.ka = ka;
    }

    public float getKd() {
        return kd;
    }

    public void setKd(float kd) {
        this.kd = kd;
    }

    public float getKs() {
        return ks;
    }

    public void setKs(float ks) {
        this.ks = ks;
    }

    public int getExponent() {
        return exponent;
    }

    public void setExponent(int exponent) {
        this.exponent = exponent;
    }
}
