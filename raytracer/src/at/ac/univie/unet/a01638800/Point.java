package at.ac.univie.unet.a01638800;

public class Point {
    private Coordinate coordinate;

    private Point() {
        // empty
    }

    public Point(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Point addVector(Vector vector) {
        float x = 0;
        float y = 0;
        float z = 0;

        // TODO implement

        return new Point(new Coordinate(x, y, z));
    }

    public Vector subtractPoint(Point point) {
        float x = 0;
        float y = 0;
        float z = 0;

        // TODO implement

        return new Vector(new Coordinate(x, y, z));
    }
}
