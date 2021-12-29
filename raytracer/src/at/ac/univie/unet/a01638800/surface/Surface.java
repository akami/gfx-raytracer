package at.ac.univie.unet.a01638800.surface;

import at.ac.univie.unet.a01638800.Intersection;
import at.ac.univie.unet.a01638800.surface.material.Material;

public abstract class Surface {
    public Material material;
    public Transform transform;

    public Intersection intersect(Surface surface) {

        // TODO implement

        return new Intersection();
    }
}
