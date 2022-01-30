package at.ac.univie.unet.a01638800.raytracer.scene.surfaces;

import at.ac.univie.unet.a01638800.raytracer.scene.surfaces.material.Material;
import at.ac.univie.unet.a01638800.raytracer.geometry.Vertex;

// TODO documentation
public class Face {

    private final Vertex[] vertices;

    private final Integer smoothingGroup;

    public Face(final Vertex[] vertices, final Integer smoothingGroup) {
        this.vertices = vertices;
        this.smoothingGroup = smoothingGroup;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public Integer getSmoothingGroup() {
        return smoothingGroup;
    }
}
