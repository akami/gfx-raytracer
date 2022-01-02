package at.ac.univie.unet.a01638800.raytracer;

import at.ac.univie.unet.a01638800.raytracer.scene.Surface;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Scene {
    private Camera camera;
    private Sphere[] spheres;

    private int sceneWidth;
    private int sceneHeight;

    private BufferedImage image;

    public Scene(at.ac.univie.unet.a01638800.raytracer.scene.Scene scene) {
        this.camera = new Camera(scene.getCamera());

        // assuming that all surfaces are of type sphere
        int sphereCount = scene.getSurfaces().getSurfaces().size();

        Sphere[] spheres = new Sphere[sphereCount];
        int i = 0;

        for (Surface surface : scene.getSurfaces().getSurfaces()) {
            if(surface instanceof at.ac.univie.unet.a01638800.raytracer.scene.Sphere) {
                spheres[i] = new Sphere((at.ac.univie.unet.a01638800.raytracer.scene.Sphere) surface);
                spheres[i].getCenter().normalize();
                i++;
            }
        }

        this.spheres = spheres;

        this.sceneWidth = Integer.parseInt(scene.getCamera().getResolution().getHorizontal());
        this.sceneHeight = Integer.parseInt(scene.getCamera().getResolution().getVertical());

        this.image = new BufferedImage(this.sceneWidth, this.sceneHeight, BufferedImage.TYPE_INT_RGB);

        this.calculatePixels();
    }

    public BufferedImage getImage() {
        return this.image;
    }

    private void calculatePixels() {
        for(int x = 0; x < this.sceneWidth; x++) {
            for(int y = 0; y < this.sceneHeight; y ++) {
                double[] coordinates = this.camera.getRays()[x][y].getDirection().getCoordinate().getXyzValues();

                boolean intersectionDetected = false;

                for (Sphere sphere : this.spheres) {
                    intersectionDetected = sphere.intersectionDetected(this.camera.getRays()[x][y]);
                }

                if(intersectionDetected) {
                    this.image.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    this.image.getRaster().setDataElements(x, y, mapToRgb(coordinates));
                }
            }
        }
    }

    /**
     * Coordinates are ranged between -1 and 1. In order to map them to rgb values which range between
     * 0 and 255, this is done as follows:
     *
     * <ol>
     *     <li> Add 1 to all coordinates. This will result in a range from 0 to 2. </li>
     *     <li> Divide the newly ranged coordinates by 2 in order to achieve a range from 0 to 1. </li>
     *     <li> Multiply by 255 to get the corresponding rgb value. </li>
     *     <li> Apply a bit-shift in order to get the TYPE_INT_RGB values. </li>
     * </ol>
     * @param coordinates the direction coordinate of the ray
     * @return integer byte array with corresponding rgb values
     */
    private int[] mapToRgb(double[] coordinates) {
        int[] rgbValues = new int[1];

        int r = (int)(((coordinates[0] + 1.0) / 2.0) * 255.0);
        int g = (int)(((coordinates[1] + 1.0) / 2.0) * 255.0);
        int b = 0; // the z value is always -1 which corresponds to 0 in rgb value

        int rgb = r << 16 | g << 8 | b;

        rgbValues[0] = rgb;

        return rgbValues;
    }
}
