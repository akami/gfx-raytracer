package at.ac.univie.unet.a01638800.raytracer.scene.surfaces;

import at.ac.univie.unet.a01638800.raytracer.geometry.Vertex;

/**
 * A class representing a face consisting of multiple vertices
 */
public class Face {

    private final Vertex[] vertices;

    public Face(final Vertex[] vertices) {
        this.vertices = vertices;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

}
