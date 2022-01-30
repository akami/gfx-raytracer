package at.ac.univie.unet.a01638800.raytracer.scene.camera;

import at.ac.univie.unet.a01638800.raytracer.geometry.Coordinate;
import at.ac.univie.unet.a01638800.raytracer.geometry.Matrix;
import at.ac.univie.unet.a01638800.raytracer.geometry.Point;
import at.ac.univie.unet.a01638800.raytracer.geometry.Ray;
import at.ac.univie.unet.a01638800.raytracer.geometry.Vector;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.XmlCamera;

/**
 * Represents the camera in the scene. The camera shoots rays at the image plane for every pixel.
 */
public class Camera {

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
     * The amount of samples (for supersampling)
     */
    private final int samples;

    /**
     * The 3D-array of rays the camera shoots at the image plane (3rd dimension is the amount of samples)
     */
    private Ray[][][] rays;

    public Camera(final XmlCamera inputCamera, final int samples) {
        this.inputCamera = inputCamera;

        // samples can only be done in a grid, so 2x anti aliasing results in 4x the rays
        this.samples = samples * samples;

        imageWidth = Integer.parseInt(inputCamera.getResolution().getHorizontal());
        imageHeight = Integer.parseInt(inputCamera.getResolution().getVertical());

        cameraPosition = new Point(
                Double.parseDouble(inputCamera.getPosition().getX()),
                Double.parseDouble(inputCamera.getPosition().getY()),
                Double.parseDouble(inputCamera.getPosition().getZ())
        );

        final Point lookAt = new Point(
                Double.parseDouble(inputCamera.getLookat().getX()),
                Double.parseDouble(inputCamera.getLookat().getY()),
                Double.parseDouble(inputCamera.getLookat().getZ())
        );

        final Vector up = new Vector(
                Double.parseDouble(inputCamera.getUp().getX()),
                Double.parseDouble(inputCamera.getUp().getY()),
                Double.parseDouble(inputCamera.getUp().getZ())
        );

        cameraMatrix = constructCameraMatrix(cameraPosition, lookAt, up);

        // pre-define 2D array size to match resolution of inputImage
        rays = new Ray[imageWidth][imageHeight][samples * samples];

        constructCameraRays();
    }

    public XmlCamera getInputCamera() {
        return inputCamera;
    }

    public Ray[][][] getRays() {
        return rays;
    }

    public void setRays(final Ray[][][] rays) {
        this.rays = rays;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    private Matrix constructCameraMatrix(final Point cameraPosition, final Point lookAt, final Vector up) {
        // construct column vectors
        final Vector z = cameraPosition.subtractPoint(lookAt).normalize();
        final Vector x = up.crossProduct(z).normalize();
        final Vector y = z.crossProduct(x).normalize();

        // throw vectors into a coordinate array
        final Coordinate[] coordinates = new Coordinate[4];

        coordinates[0] = x.getCoordinate();
        coordinates[1] = y.getCoordinate();
        coordinates[2] = z.getCoordinate();
        coordinates[3] = cameraPosition.getCoordinate();

        // construct matrix out of the array
        final Matrix cameraMatrix = new Matrix(coordinates, true);
        cameraMatrix.set(3, 0, 0);
        cameraMatrix.set(3, 1, 0);
        cameraMatrix.set(3, 2, 0);
        cameraMatrix.set(0, 3, 0);
        cameraMatrix.set(1, 3, 0);
        cameraMatrix.set(2, 3, 0);

        return cameraMatrix;
    }

    /**
     * This method constructs x camera rays for each pixel in the image plane, where x is the amount of the sample grid
     * size. For each pixel:
     * <ol>
     *     <li>Construct an imaginary grid</li>
     *     <li>For each grid entry</li>
     *     <ul>
     *          <li>Create a point for a pixel with image x/y and z = -1 as coordinates The z value corresponds to
     *          the location of the image plane in the scene.</li>
     *          <li>Normalizing the point; Range: (0...1)</li>
     *          <li>Mapping the pixel to the image plane; Range (-1...1)</li>
     *          <li>Calculate in the FOV and image dimensions</li>
     *          <li>Transform the pixel coordinate using the camera matrix</li>
     *          <li>Create a ray shooting from the camera position to the transformed coordinate</li>
     *          <li>Normalizing the ray direction</li>
     *          <li>Set the origin of the ray to the camera position</li>
     *     </ul>
     * </ol>
     * Note that this will produce an image that is upside down (starting from the bottom left corner going up). This
     * means that the final image has to be flipped.
     */
    public void constructCameraRays() {
        for (int x = 0; x < imageWidth; x++) {
            for (int y = 0; y < imageHeight; y++) {
                int currentSample = 0;

                // amount of samples per row/column
                final int samplesPerRowColumn = (int) Math.sqrt(samples);

                // value by which the offset into the pixel x and y axis are incremented
                final double offset = 1D / samplesPerRowColumn;

                // defines the start and end of the grid on which we shoot the rays
                final double gridPadding = offset / 2;

                // i = current row, j = current column of grid
                for (int i = 0; i < samplesPerRowColumn; i++) {
                    for (int j = 0; j < samplesPerRowColumn; j++) {
                        final Point pixel = new Point(x, y, -1.0);

                        // offset * the row/column - the gridpadding (to ensure that the distribution is uniform across the image)
                        final double offsetX = offset * i - gridPadding;
                        final double offsetY = offset * j - gridPadding;

                        normalizeCoordinate(pixel.getCoordinate(), offsetX, offsetY);
                        mapCoordinateToImagePlane(pixel.getCoordinate());
                        includeFOVAndImageDimensions(pixel.getCoordinate());

                        Vector direction = cameraMatrix.multiply(new Vector(pixel.getCoordinate()), true);
                        direction = direction.normalize();

                        final Ray ray = new Ray();
                        ray.setDirection(direction);
                        ray.setOrigin(cameraPosition);

                        rays[x][y][currentSample++] = ray;
                    }
                }
            }
        }
    }

    /**
     * This method normalizes the direction coordinate of the ray. The coordinates initially lie between (0 ... image dimension).
     * After calling this method, the parsed coordinate will lie between the range of 0...1, while adding an offset that
     * makes the array be shot into the middle of the pixel.
     *
     * @param coordinate ray direction coordinate
     * @param offsetX    value by which the ray is offset on the X axis
     * @param offsetY    value by which the ray is offset on the Y axis
     */
    public void normalizeCoordinate(final Coordinate coordinate, final double offsetX, final double offsetY) {
        coordinate.getXyzValues()[0] = (coordinate.getXyzValues()[0] + offsetX) / imageWidth;
        coordinate.getXyzValues()[1] = (coordinate.getXyzValues()[1] + offsetY) / imageHeight;
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
