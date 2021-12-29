package at.ac.univie.unet.a01638800;

import at.ac.univie.unet.a01638800.surface.material.Material;

public class Intersection {
    private Point pointOfIntersection;
    private Vector distance;
    private Vector normal;
    private Material material;

    public Intersection() {
        // empty
    }

    public Intersection(Point pointOfIntersection, Vector distance, Vector normal, Material material) {
        this.pointOfIntersection = pointOfIntersection;
        this.distance = distance;
        this.normal = normal;
        this.material = material;
    }

    public Point getPointOfIntersection() {
        return pointOfIntersection;
    }

    public void setPointOfIntersection(Point pointOfIntersection) {
        this.pointOfIntersection = pointOfIntersection;
    }

    public Vector getDistance() {
        return distance;
    }

    public void setDistance(Vector distance) {
        this.distance = distance;
    }

    public Vector getNormal() {
        return normal;
    }

    public void setNormal(Vector normal) {
        this.normal = normal;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
