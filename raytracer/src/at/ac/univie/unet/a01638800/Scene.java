package at.ac.univie.unet.a01638800;

import at.ac.univie.unet.a01638800.surface.Surface;

import java.util.ArrayList;

public class Scene {
    private String outputFile;
    private Color backgroundColor;
    private Camera camera;
    private ArrayList<Light> lights;
    private ArrayList<Surface> surfaces;

    public Scene() {
        // empty
    }

    public Scene(String outputFile, Color backgroundColor, Camera camera, ArrayList<Light> lights, ArrayList<Surface> surfaces) {
        this.outputFile = outputFile;
        this.backgroundColor = backgroundColor;
        this.camera = camera;
        this.lights = lights;
        this.surfaces = surfaces;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public ArrayList<Light> getLights() {
        return lights;
    }

    public void setLights(ArrayList<Light> lights) {
        this.lights = lights;
    }

    public ArrayList<Surface> getSurfaces() {
        return surfaces;
    }

    public void setSurfaces(ArrayList<Surface> surfaces) {
        this.surfaces = surfaces;
    }
}
