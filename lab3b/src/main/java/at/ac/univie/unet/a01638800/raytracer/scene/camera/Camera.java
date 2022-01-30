package at.ac.univie.unet.a01638800.raytracer.scene.camera;

import at.ac.univie.unet.a01638800.raytracer.geometry.*;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlCamera;

/**
 * Represents the camera in the scene. The camera shoots rays at the image plane for every pixel.
 */
public class Camera {

    /**
     * Offset to shoot the ray through the middle of the pixel
     */
    private static final double OFFSET = 0.5;

    /**
     * The parsed camera data from the input xml file
     */
    private final XmlCamera inputCamera;

    /**
     * The width of the image plane
     */
    private final int imageWidth;

    /**
     * The height of the image plane
     */
    private final int imageHeight;

    /**
     * The position of the camera of the scene
     */
    private final Point cameraPosition;

    /**
     * The matrix we apply camera transformations to
     */
    private final Matrix cameraMatrix;

    /**
     * The 2D-array of rays the camera shoots at the image plane
     */
    private Ray[][] rays;

    public Camera(final XmlCamera inputCamera) {
        this.inputCamera = inputCamera;

        this.imageWidth = Integer.parseInt(inputCamera.getResolution().getHorizontal());
        this.imageHeight = Integer.parseInt(inputCamera.getResolution().getVertical());

        cameraPosition = new Point(
                Double.parseDouble(inputCamera.getPosition().getX()),
                Double.parseDouble(inputCamera.getPosition().getY()),
                Double.parseDouble(inputCamera.getPosition().getZ())
        );

        Point lookAt = new Point(
                Double.parseDouble(inputCamera.getLookat().getX()),
                Double.parseDouble(inputCamera.getLookat().getY()),
                Double.parseDouble(inputCamera.getLookat().getZ())
        );

        Vector up = new Vector(
                Double.parseDouble(inputCamera.getUp().getX()),
                Double.parseDouble(inputCamera.getUp().getY()),
                Double.parseDouble(inputCamera.getUp().getZ())
        );

        this.cameraMatrix = constructCameraMatrix(cameraPosition, lookAt, up);

        // pre-define 2D array size to match resolution of inputImage
        this.rays = new Ray[imageWidth][imageHeight];

        constructCameraRays();
    }

    public XmlCamera getInputCamera() {
        return inputCamera;
    }

    public Ray[][] getRays() {
        return rays;
    }

    public void setRays(final Ray[][] rays) {
        this.rays = rays;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    private Matrix constructCameraMatrix(Point cameraPosition, Point lookAt, Vector up) {
        // construct column vectors
        Vector z = cameraPosition.subtractPoint(lookAt).normalize();
        Vector x = up.crossProduct(z).normalize();
        Vector y = z.crossProduct(x).normalize();

        // throw vectors into a coordinate array
        Coordinate[] coordinates = new Coordinate[4];

        coordinates[0] = x.getCoordinate();
        coordinates[1] = y.getCoordinate();
        coordinates[2] = z.getCoordinate();
        coordinates[3] = cameraPosition.getCoordinate();

        // construct matrix out of the array
        Matrix cameraMatrix = new Matrix(coordinates, true);
        cameraMatrix.set(3, 0, 0);
        cameraMatrix.set(3, 1, 0);
        cameraMatrix.set(3, 2, 0);
        cameraMatrix.set(0, 3, 0);
        cameraMatrix.set(1, 3, 0);
        cameraMatrix.set(2, 3, 0);

        return cameraMatrix;
    }

    /**
     * This method constructs a camera ray for each pixel in the image plane. For each pixel:
     *
     * <ol>
     *     <li> create a new ray </li>
     *     <li> set the direction of the ray to the pixel coordinate and the z value to -1. The z value corresponds to
     *     the location of the image plane in the scene. </li>
     *     <li> normalizing the direction. range: (0...1) </li>
     *     <li> mapping the direction to the image plane. range (-1...1) </li>
     *     <li> taking the FOV and image dimensions into account for rectangular images </li>
     *     <li> normalizing the direction </li>
     *     <li> set the final direction of the ray </li>
     *     <li> set the origin of the ray to the camera position </li>
     * </ol>
     * <p>
     * Note that this will produce an image that is upside down (starting from the bottom left corner going up). This
     * means that the final image has to be flipped.
     */
    public void constructCameraRays() {
        for (int x = 0; x < imageWidth; x++) {
            for (int y = 0; y < imageHeight; y++) {
                rays[x][y] = new Ray();

                Point pixel = new Point(x, y, -1.0);

                normalizeCoordinate(pixel.getCoordinate());
                mapCoordinateToImagePlane(pixel.getCoordinate());
                includeFOVAndImageDimensions(pixel.getCoordinate());

                Vector direction = cameraMatrix.multiply(new Vector(pixel.getCoordinate()), true);
                direction = direction.normalize();

                rays[x][y].setDirection(direction);
                rays[x][y].setOrigin(cameraPosition);
            }
        }
    }

    /**
     * This method normalizes the direction coordinate of the ray. The coordinates initially lie between (0 ... image dimension).
     * After calling this method, the parsed coordinate will lie between the range of 0...1, while adding an offset that
     * makes the array be shot into the middle of the pixel.
     *
     * @param coordinate ray direction coordinate
     */
    public void normalizeCoordinate(final Coordinate coordinate) {
        coordinate.getXyzValues()[0] = (coordinate.getXyzValues()[0] + OFFSET) / imageWidth;
        coordinate.getXyzValues()[1] = (coordinate.getXyzValues()[1] + OFFSET) / imageHeight;
    }

    /**
     * Maps the ray direction coordinate from range (0...1) to range (-1...1)
     * The coordinate is first doubled to be between 0...2 and then shifted by 1 to finally be in the desired
     * range between -1...1
     *
     * @param coordinate ray direction coordinate
     */
    private void mapCoordinateToImagePlane(final Coordinate coordinate) {
        coordinate.getXyzValues()[0] = 2 * coordinate.getXyzValues()[0] - 1;
        coordinate.getXyzValues()[1] = 2 * coordinate.getXyzValues()[1] - 1;
    }

    /**
     * Taking the field of view of the camera and the image dimensions into account.
     *
     * @param coordinate ray direction coordinate
     */
    private void includeFOVAndImageDimensions(final Coordinate coordinate) {
        double fovX = 0;
        double fovY = 0;

        if (imageWidth >= imageHeight) {
            fovX = Double.parseDouble(inputCamera.getHorizontalFov().getAngle()) * (Math.PI / 180);
            fovY = fovX * ((double) imageHeight / (double) imageWidth);
        } else {
            fovX = Double.parseDouble(inputCamera.getHorizontalFov().getAngle()) * (Math.PI / 180);
            fovY = fovX * ((double) imageWidth / (double) imageHeight);
        }

        coordinate.getXyzValues()[0] = coordinate.getXyzValues()[0] * Math.tan(fovX);
        coordinate.getXyzValues()[1] = coordinate.getXyzValues()[1] * Math.tan(fovY);
    }

}
