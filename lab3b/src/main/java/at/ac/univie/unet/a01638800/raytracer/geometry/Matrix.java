package at.ac.univie.unet.a01638800.raytracer.geometry;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class Matrix {
    private final double[][] data;

    public Matrix(int rows, int columns) {
        data = new double[rows][columns];
    }

    public Matrix(Coordinate[] coordinates, boolean homogenize) {
        if (homogenize) {
            data = new double[4][coordinates.length];
        } else {
            data = new double[3][coordinates.length];
        }

        for (int row = 0; row < getRows(); row++) {
            for (int column = 0; column < getColumns(); column++) {
                if (row > 2) {
                    data[row][column] = 1;
                } else {
                    data[row][column] = coordinates[column].getXyzValues()[row];
                }
            }
        }
    }

    public Matrix(Matrix matrix) {
        this.data = new double[matrix.getRows()][matrix.getColumns() == 0 ? 0 : matrix.getColumns()];

        for (int i = 0; i < matrix.getRows(); i++) {
            this.data[i] = ArrayUtils.clone(matrix.getData()[i]);
        }
    }

    private static double determinant(Matrix matrix) {
        if (matrix.getRows() != matrix.getColumns()) {
            throw new IllegalArgumentException("Illegal dimensions!");
        }

        if (matrix.getRows() == 1) {
            return matrix.get(0, 0);
        }

        if (matrix.getRows() == 2) {
            return (matrix.get(0, 0) * matrix.get(1, 1)) -
                    (matrix.get(0, 1) * matrix.get(1, 0));
        }

        double sum = 0.0;
        for (int column = 0; column < matrix.getColumns(); column++) {
            sum += (column % 2 == 0 ? 1 : -1) * matrix.get(0, column) * determinant(createSubMatrix(matrix, 0, column));
        }

        return sum;
    }

    private static Matrix createSubMatrix(Matrix matrix, int pivotRow, int pivotColumn) {
        Matrix newMatrix = new Matrix(matrix.getRows() - 1, matrix.getColumns() - 1);

        for (int row = 0, newRow = 0; row < matrix.getRows(); row++) {
            if (row != pivotRow) {
                for (int column = 0, newColumn = 0; column < matrix.getColumns(); column++) {
                    if (column != pivotColumn) {
                        newMatrix.set(newRow, newColumn, matrix.get(row, column));

                        newColumn++;
                    }
                }

                newRow++;
            }
        }

        return newMatrix;
    }

    protected double[][] getData() {
        return data;
    }

    public int getRows() {
        return data.length;
    }

    public int getColumns() {
        if (data.length == 0) {
            return 0;
        }

        return data[0].length;
    }

    public double get(int row, int column) {
        return data[row][column];
    }

    public void set(int row, int column, double value) {
        data[row][column] = value;
    }

    private Matrix multiply(double factor) {
        Matrix newMatrix = new Matrix(getRows(), getColumns());

        for (int row = 0; row < getRows(); row++) {
            for (int column = 0; column < getColumns(); column++) {
                newMatrix.set(row, column, get(row, column) * factor);
            }
        }

        return newMatrix;
    }

    public Matrix multiply(Matrix matrix) {
        if (getColumns() != matrix.getRows()) {
            throw new IllegalArgumentException("Illegal matrix dimensions!");
        }

        Matrix newMatrix = new Matrix(getRows(), matrix.getColumns());

        for (int newRow = 0; newRow < getRows(); newRow++) {
            for (int column = 0; column < getColumns(); column++) {
                for (int newColumn = 0; newColumn < matrix.getColumns(); newColumn++) {
                    newMatrix.set(newRow, newColumn, newMatrix.get(newRow, newColumn) + get(newRow, column) * matrix.get(column, newColumn));
                }
            }
        }

        return newMatrix;
    }

    public Coordinate multiply(Coordinate coordinate, boolean homogenize) {
        Matrix matrix = new Matrix(new Coordinate[]{coordinate}, homogenize);

        double[][] data = this.multiply(matrix).getData();
        double[] xyzValues = new double[]{data[0][0], data[1][0], data[2][0]};

        return new Coordinate(xyzValues);

    }

    public Point multiply(Point point, boolean homogenize) {
        return new Point(multiply(point.getCoordinate(), homogenize));
    }

    public Vector multiply(Vector vector, boolean homogenize) {
        return new Vector(multiply(vector.getCoordinate(), homogenize));
    }

    public double determinant() {
        return determinant(this);
    }

    public Matrix cofactor() {
        Matrix newMatrix = new Matrix(getRows(), getColumns());

        for (int row = 0; row < getRows(); row++) {
            for (int column = 0; column < getColumns(); column++) {
                newMatrix.set(row, column, (row % 2 == 0 ? 1 : -1) * (column % 2 == 0 ? 1 : -1) *
                        determinant(createSubMatrix(this, row, column)));
            }
        }

        return newMatrix;
    }

    public Matrix transpose() {
        Matrix newMatrix = new Matrix(getColumns(), getRows());

        for (int row = 0; row < getRows(); row++) {
            for (int column = 0; column < getColumns(); column++) {
                newMatrix.set(column, row, get(row, column));
            }
        }

        return newMatrix;
    }

    public Matrix inverse() {
        return cofactor().transpose().multiply(1 / determinant());
    }

    public Coordinate toCoordinate() {
        if (getColumns() != 1) {
            throw new IllegalArgumentException("Illegal dimensions!");
        }

        double[] xyzValues = Arrays.copyOf(this.data[0], 3);

        return new Coordinate(xyzValues);
    }

    public Point toPoint() {
        return new Point(toCoordinate());
    }

    public Vector toVector() {
        return new Vector(toCoordinate());
    }
}
