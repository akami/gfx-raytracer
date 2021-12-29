package at.ac.univie.unet.a01638800;

public class Vector {
    private Coordinate coordinate;

    public Vector() {
        // empty
    }

    public Vector(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public float dotProduct(Vector vector) {
        float result = 0;

        // TODO implement

        return result;
    }

    public Vector crossProduct (Vector vector) {
        float x = 0;
        float y = 0;
        float z = 0;

        // TODO implement

        return new Vector(new Coordinate(x, y, z));
    }

    public Vector scaleByFactor(float factor) {
        return new Vector(new Coordinate(this.coordinate.getX() * factor, this.coordinate.getY() * factor, this.coordinate.getZ()* factor));
    }

    public Vector addVector(Vector vector) {
        float x = 0;
        float y = 0;
        float z = 0;

        // TODO implement

        return new Vector(new Coordinate(x, y, z));
    }

    public Point addPoint(Point point) {
        float x = 0;
        float y = 0;
        float z = 0;

        // TODO implement

        return new Point(new Coordinate(x, y, z));
    }

}
