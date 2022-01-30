package at.ac.univie.unet.a01638800.raytracer.scene.surfaces.material;

import at.ac.univie.unet.a01638800.raytracer.geometry.Color;

public interface TextureMapping {
    Color mapTexture(/* TODO params: intersection point, vt's of face */);
}
