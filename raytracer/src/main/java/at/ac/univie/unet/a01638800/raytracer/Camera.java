package at.ac.univie.unet.a01638800.raytracer;

import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Coordinate;
import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Vector;

public class Camera {
    private static final double OFFSET = 0.5; // offset to shoot ray through the middle of the pixel

    private final at.ac.univie.unet.a01638800.raytracer.scene.Camera inputCamera;
    private final int imageWidth;
    private final int imageHeight;
    private Ray[][] rays;

    public Camera(at.ac.univie.unet.a01638800.raytracer.scene.Camera inputCamera) {
        this.inputCamera = inputCamera;

        this.imageWidth = Integer.parseInt(inputCamera.getResolution().getHorizontal());
        this.imageHeight = Integer.parseInt(inputCamera.getResolution().getVertical());

        // pre-define 2D array size to match resolution of inputImage
        this.rays = new Ray[imageWidth][imageHeight];

        this.constructCameraRays();
    }

    public at.ac.univie.unet.a01638800.raytracer.scene.Camera getInputCamera() {
        return inputCamera;
    }

    public Ray[][] getRays() {
        return rays;
    }

    public void setRays(Ray[][] rays) {
        this.rays = rays;
    }

    public double getImageWidth() {
        return imageWidth;
    }

    public double getImageHeight() {
        return imageHeight;
    }

    public void constructCameraRays() {
        for (int x = 0; x < imageWidth; x++) {
            for (int y = 0; y < imageHeight; y++) {
                this.rays[x][y] = new Ray();

                Vector direction = new Vector(x, y, -1.0);

                this.normalizeCoordinate(direction);
                this.mapCoordinateToImagePlane(direction.getCoordinate());
                this.includeFOVAndImageDimensions(direction.getCoordinate());

                direction.normalize();

                direction.setZ(-1);

                this.rays[x][y].setDirection(direction);
            }
        }
    }

    public void normalizeCoordinate(Vector direction) {
        direction.setX((direction.getX() + OFFSET) / this.imageWidth);
        direction.setY((direction.getY() + OFFSET) / this.imageHeight);
    }

    private void mapCoordinateToImagePlane(Coordinate coordinate) {
        coordinate.getXyzValues()[0] = 2 * coordinate.getXyzValues()[0] - 1;
        coordinate.getXyzValues()[1] = 2 * coordinate.getXyzValues()[1] - 1;
    }

    private void includeFOVAndImageDimensions(Coordinate coordinate) {
        double fovX = Double.parseDouble(this.inputCamera.getHorizontalFov().getAngle());
        double fovY = fovX * ((double) this.imageHeight / (double) this.imageWidth);

        coordinate.getXyzValues()[0] = coordinate.getXyzValues()[0] * Math.tan(fovX);
        coordinate.getXyzValues()[1] = coordinate.getXyzValues()[1] * Math.tan(fovY);
    }

}
