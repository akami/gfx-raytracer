package at.ac.univie.unet.a01638800.raytracer.scene.surfaces.transformation;

import at.ac.univie.unet.a01638800.raytracer.geometry.Matrix;
import at.ac.univie.unet.a01638800.raytracer.geometry.Vector;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.*;

public class Transformation {
    private Vector translationVector;
    private double[] xyzRotationFactors;
    private Vector scalingVector;

    private Matrix translationMatrix;
    private Matrix scalingMatrix;
    private Matrix rotationMatrix;

    public Transformation(XmlSurface.XmlTransform transform) {
        this.xyzRotationFactors = new double[3];

        for (XmlTransformation xmlTransformation : transform.getTransform()) {
            if (xmlTransformation instanceof XmlTranslate) {
                XmlTranslate translate = (XmlTranslate) xmlTransformation;

                this.translationVector = new Vector(
                        Double.parseDouble(translate.getX()),
                        Double.parseDouble(translate.getY()),
                        Double.parseDouble(translate.getZ())
                );
                this.translationMatrix = this.constructTranslationMatrix(this.getInverseTranslationVector());
            } else if (xmlTransformation instanceof XmlScale) {
                XmlScale scale = (XmlScale) xmlTransformation;

                this.scalingVector = new Vector(
                        Double.parseDouble(scale.getX()),
                        Double.parseDouble(scale.getY()),
                        Double.parseDouble(scale.getZ())
                );

                // get inverse scaling vector to directly apply transformation matrix onto ray direction
                this.scalingMatrix = this.constructScalingMatrix(this.getInverseScalingVector());

            } else if (xmlTransformation instanceof XmlRotateX) {
                this.xyzRotationFactors[0] = Double.parseDouble(((XmlRotateX) xmlTransformation).getTheta());

                double[] negatedXyzFactors = this.getInverseXyzRotationFactors();
                this.rotationMatrix = this.constructRotationXMatrix(negatedXyzFactors[0]);

            } else if (xmlTransformation instanceof XmlRotateY) {
                this.xyzRotationFactors[1] = Double.parseDouble(((XmlRotateY) xmlTransformation).getTheta());

                double[] negatedXyzFactors = this.getInverseXyzRotationFactors();
                this.rotationMatrix = this.constructRotationYMatrix(negatedXyzFactors[1]);

            } else if (xmlTransformation instanceof XmlRotateZ) {
                this.xyzRotationFactors[2] = Double.parseDouble(((XmlRotateZ) xmlTransformation).getTheta());

                double[] negatedXyzFactors = this.getInverseXyzRotationFactors();
                this.rotationMatrix = this.constructRotationZMatrix(negatedXyzFactors[2]);
            }
        }
    }

    public Vector getTranslationVector() {
        return translationVector;
    }

    public void setTranslationVector(Vector translationVector) {
        this.translationVector = translationVector;
    }

    public double[] getXyzRotationFactors() {
        return xyzRotationFactors;
    }

    public void setXyzRotationFactors(double[] xyzRotationFactors) {
        this.xyzRotationFactors = xyzRotationFactors;
    }

    public Vector getScalingVector() {
        return scalingVector;
    }

    public void setScalingVector(Vector scalingVector) {
        this.scalingVector = scalingVector;
    }

    public Matrix getTranslationMatrix() {
        return translationMatrix;
    }

    public void setTranslationMatrix(Matrix translationMatrix) {
        this.translationMatrix = translationMatrix;
    }

    public Matrix getScalingMatrix() {
        return scalingMatrix;
    }

    public void setScalingMatrix(Matrix scalingMatrix) {
        this.scalingMatrix = scalingMatrix;
    }

    public Matrix getRotationMatrix() {
        return rotationMatrix;
    }

    public void setRotationMatrix(Matrix rotationMatrix) {
        this.rotationMatrix = rotationMatrix;
    }

    public Vector getInverseTranslationVector() {
        return this.translationVector.invert();
    }

    public Vector getInverseScalingVector() {
        return new Vector(1D / this.scalingVector.getX(), 1D / this.scalingVector.getY(), 1D / this.scalingVector.getZ());
    }

    public double[] getInverseXyzRotationFactors() {
        double[] negatedFactors = this.xyzRotationFactors;

        for (double negatedFactor : negatedFactors) {
            negatedFactor = -negatedFactor;
        }

        return negatedFactors;
    }

    public Matrix constructTranslationMatrix(Vector translationVector) {
        Matrix newMatrix = new Matrix(4, 4, true);

        int column = newMatrix.getColumns() - 1;

        newMatrix.set(0, column, translationVector.getX());
        newMatrix.set(1, column, translationVector.getY());
        newMatrix.set(2, column, translationVector.getZ());

        return newMatrix;
    }

    public Matrix constructScalingMatrix(Vector scalingVector) {
        Matrix newMatrix = new Matrix(4, 4, true);

        newMatrix.set(0, 0, scalingVector.getX());
        newMatrix.set(1, 1, scalingVector.getY());
        newMatrix.set(2, 2, scalingVector.getZ());

        return newMatrix;
    }

    public Matrix constructRotationXMatrix(double angle) {
        Matrix newMatrix = new Matrix(4, 4, true);

        // TODO check if radian
        newMatrix.set(1, 1, Math.cos(angle));
        newMatrix.set(1, 2, -Math.sin(angle));
        newMatrix.set(2, 1, Math.sin(angle));
        newMatrix.set(2, 2, Math.cos(angle));

        return newMatrix;
    }

    public Matrix constructRotationYMatrix(double angle) {
        Matrix newMatrix = new Matrix(4, 4, true);

        // TODO check if radian
        newMatrix.set(0, 0, Math.cos(angle));
        newMatrix.set(2, 0,  -Math.sin(angle));
        newMatrix.set(0, 2, Math.sin(angle));
        newMatrix.set(2, 2, Math.cos(angle));

        return newMatrix;
    }

    public Matrix constructRotationZMatrix(double angle) {
        Matrix newMatrix = new Matrix(4, 4, true);

        // TODO check if radian
        newMatrix.set(0, 0, Math.cos(angle));
        newMatrix.set(0, 1, -Math.sin(angle));
        newMatrix.set(1, 0, Math.sin(angle));
        newMatrix.set(1, 1, Math.cos(angle));

        return newMatrix;
    }
}
