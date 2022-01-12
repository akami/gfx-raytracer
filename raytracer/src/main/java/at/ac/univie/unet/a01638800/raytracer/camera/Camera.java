package at.ac.univie.unet.a01638800.raytracer.camera;

import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Coordinate;
import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Point;
import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Ray;
import at.ac.univie.unet.a01638800.raytracer.geometricobjects.Vector;
import at.ac.univie.unet.a01638800.raytracer.scene.Position;

public class Camera {
    private static final double OFFSET = 0.5; // offset to shoot ray through the middle of the pixel

    private final at.ac.univie.unet.a01638800.raytracer.scene.Camera inputCamera;

    private final int imageWidth;
    private final int imageHeight;

    private final Point cameraPosition;

    private Ray[][] rays;

    public Camera(at.ac.univie.unet.a01638800.raytracer.scene.Camera inputCamera) {
        this.inputCamera = inputCamera;

        this.imageWidth = Integer.parseInt(inputCamera.getResolution().getHorizontal());
        this.imageHeight = Integer.parseInt(inputCamera.getResolution().getVertical());

        Position position = inputCamera.getPosition();

        this.cameraPosition = new Point(Double.parseDouble(position.getX()), Double.parseDouble(position.getY()), Double.parseDouble(position.getZ()));

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

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void constructCameraRays() {
        for (int x = 0; x < imageWidth; x++) {
            for (int y = 0; y < imageHeight; y++) {
                this.rays[x][y] = new Ray();

                Vector direction = new Vector(x, y, -1.0);

                this.normalizeCoordinate(direction.getCoordinate());
                this.mapCoordinateToImagePlane(direction.getCoordinate());
                this.includeFOVAndImageDimensions(direction.getCoordinate());

                direction = direction.normalize();

                this.rays[x][y].setDirection(direction);
                this.rays[x][y].setOrigin(this.cameraPosition);
            }
        }
    }

    public void normalizeCoordinate(Coordinate coordinate) {
        coordinate.getXyzValues()[0] = (coordinate.getXyzValues()[0] + OFFSET) / this.imageWidth;
        coordinate.getXyzValues()[1] = (coordinate.getXyzValues()[1] + OFFSET) / this.imageHeight;
    }

    private void mapCoordinateToImagePlane(Coordinate coordinate) {
        coordinate.getXyzValues()[0] = 2 * coordinate.getXyzValues()[0] - 1;
        coordinate.getXyzValues()[1] = 2 * coordinate.getXyzValues()[1] - 1;
    }

    private void includeFOVAndImageDimensions(Coordinate coordinate) {
        double fovX = 0;
        double fovY = 0;

        if (this.imageWidth >= this.imageHeight) {
            fovX = Double.parseDouble(this.inputCamera.getHorizontalFov().getAngle()) * (Math.PI / 180);
            fovY = fovX * ((double) this.imageHeight / (double) this.imageWidth);
        } else {
            fovX = Double.parseDouble(this.inputCamera.getHorizontalFov().getAngle()) * (Math.PI / 180);
            fovY = fovX * ((double) this.imageWidth / (double) this.imageHeight);
        }

        coordinate.getXyzValues()[0] = coordinate.getXyzValues()[0] * Math.tan(fovX);
        coordinate.getXyzValues()[1] = coordinate.getXyzValues()[1] * Math.tan(fovY);
    }
}
