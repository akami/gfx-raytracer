package at.ac.univie.unet.a01638800.raytracer.geometry;

/**
 * Represents the a vertex, including its position, normal and texture.
 */
public class Vertex {

    private final Point position;
    private final Vector normal;
    private final Point texture;

    public Vertex(final Point position, final Vector normal, final Point texture) {
        this.position = position;
        this.normal = normal;
        this.texture = texture;
    }

    public Point getPosition() {
        return position;
    }

    public Vector getNormal() {
        return normal;
    }

    public Point getTexture() {
        return texture;
    }

}
