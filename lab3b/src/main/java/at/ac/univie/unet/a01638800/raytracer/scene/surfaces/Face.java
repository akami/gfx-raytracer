package at.ac.univie.unet.a01638800.raytracer.scene.surfaces;

import at.ac.univie.unet.a01638800.raytracer.geometry.Material;
import at.ac.univie.unet.a01638800.raytracer.geometry.Vertex;

public class Face {

    private final Vertex[] vertices;

    private final Material material;

    private final Integer smoothingGroup;

    public Face(final Vertex[] vertices, final Material material, final Integer smoothingGroup) {
        this.vertices = vertices;
        this.material = material;
        this.smoothingGroup = smoothingGroup;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public Material getMaterial() {
        return material;
    }

    public Integer getSmoothingGroup() {
        return smoothingGroup;
    }
}
