package at.ac.univie.unet.a01638800.raytracer.scene.surfaces.transformation;

import at.ac.univie.unet.a01638800.raytracer.geometry.Matrix;
import at.ac.univie.unet.a01638800.raytracer.geometry.Point;
import at.ac.univie.unet.a01638800.raytracer.geometry.Ray;
import at.ac.univie.unet.a01638800.raytracer.geometry.Vector;
import at.ac.univie.unet.a01638800.raytracer.xml.scene.*;
import org.apache.commons.lang3.tuple.Pair;

public class Transformation {
    private Vector translationVector;
    private Vector scalingVector;

    private Matrix translationMatrix;

    private Matrix scalingMatrix;
    private Matrix inverseScalingMatrix;

    private Matrix rotationMatrix;
    private Matrix inverseRotationMatrix;

    public Transformation(XmlSurface.XmlTransform transform) {
        this.rotationMatrix = new Matrix(4, 4, true);
        this.inverseRotationMatrix = new Matrix(4, 4, true);

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
                this.inverseScalingMatrix = this.constructScalingMatrix(this.getScalingVector());
            } else if (xmlTransformation instanceof XmlRotateX) {
                double theta = Double.parseDouble(((XmlRotateX) xmlTransformation).getTheta());

                this.inverseRotationMatrix = inverseRotationMatrix.multiply(constructRotationXMatrix(theta));
                this.rotationMatrix = rotationMatrix.multiply(constructRotationXMatrix(-theta));
            } else if (xmlTransformation instanceof XmlRotateY) {
                double theta = Double.parseDouble(((XmlRotateY) xmlTransformation).getTheta());

                this.inverseRotationMatrix = inverseRotationMatrix.multiply(constructRotationYMatrix(theta));
                this.rotationMatrix = rotationMatrix.multiply(constructRotationYMatrix(-theta));
            } else if (xmlTransformation instanceof XmlRotateZ) {
                double theta = Double.parseDouble(((XmlRotateZ) xmlTransformation).getTheta());

                this.inverseRotationMatrix = inverseRotationMatrix.multiply(constructRotationZMatrix(theta));
                this.rotationMatrix = rotationMatrix.multiply(constructRotationZMatrix(-theta));
            }
        }
    }

    private Vector getInverseTranslationVector() {
        return this.translationVector.invert();
    }

    private Vector getScalingVector() {
        return new Vector(this.scalingVector.getX(), this.scalingVector.getY(), this.scalingVector.getZ());
    }
    private Vector getInverseScalingVector() {
        return new Vector(1D / this.scalingVector.getX(), 1D / this.scalingVector.getY(), 1D / this.scalingVector.getZ());
    }

    private Matrix constructTranslationMatrix(Vector translationVector) {
        Matrix newMatrix = new Matrix(4, 4, true);

        int column = newMatrix.getColumns() - 1;

        newMatrix.set(0, column, translationVector.getX());
        newMatrix.set(1, column, translationVector.getY());
        newMatrix.set(2, column, translationVector.getZ());

        return newMatrix;
    }

    private Matrix constructScalingMatrix(Vector scalingVector) {
        Matrix newMatrix = new Matrix(4, 4, true);

        newMatrix.set(0, 0, scalingVector.getX());
        newMatrix.set(1, 1, scalingVector.getY());
        newMatrix.set(2, 2, scalingVector.getZ());

        return newMatrix;
    }

    private Matrix constructRotationXMatrix(double angle) {
        Matrix newMatrix = new Matrix(4, 4, true);

        double angleInRadian = Math.toRadians(angle);
        newMatrix.set(1, 1, Math.cos(angleInRadian));
        newMatrix.set(1, 2, -Math.sin(angleInRadian));
        newMatrix.set(2, 1, Math.sin(angleInRadian));
        newMatrix.set(2, 2, Math.cos(angleInRadian));

        return newMatrix;
    }

    private Matrix constructRotationYMatrix(double angle) {
        Matrix newMatrix = new Matrix(4, 4, true);

        double angleInRadian = Math.toRadians(angle);
        newMatrix.set(0, 0, Math.cos(angleInRadian));
        newMatrix.set(2, 0, -Math.sin(angleInRadian));
        newMatrix.set(0, 2, Math.sin(angleInRadian));
        newMatrix.set(2, 2, Math.cos(angleInRadian));

        return newMatrix;
    }

    private Matrix constructRotationZMatrix(double angle) {
        Matrix newMatrix = new Matrix(4, 4, true);

        double angleInRadian = Math.toRadians(angle);
        newMatrix.set(0, 0, Math.cos(angleInRadian));
        newMatrix.set(0, 1, -Math.sin(angleInRadian));
        newMatrix.set(1, 0, Math.sin(angleInRadian));
        newMatrix.set(1, 1, Math.cos(angleInRadian));

        return newMatrix;
    }

    public Pair<Ray, Boolean> transform(Ray ray) {
        boolean transformed = false;

        Vector direction = ray.getDirection();
        Point origin = ray.getOrigin();

        if (scalingMatrix != null) {
            direction = scalingMatrix.multiply(direction, true);
            origin = scalingMatrix.multiply(origin, true);
            transformed = true;
        }

        if (rotationMatrix != null) {
            direction = rotationMatrix.multiply(direction, true);
            origin = rotationMatrix.multiply(origin, true);
            transformed = true;
        }

        if (translationMatrix != null) {
            origin = translationMatrix.multiply(origin, true);
            transformed = true;
        }

        Ray newRay = new Ray();
        newRay.setDirection(direction);
        newRay.setOrigin(origin);

        return Pair.of(newRay, transformed);
    }

    public Vector transform(Vector normal) {
        Vector newNormal = normal;

        if (inverseScalingMatrix != null) {
            newNormal = inverseScalingMatrix.multiply(newNormal, true);
        }

        if (inverseRotationMatrix != null) {
            newNormal = inverseRotationMatrix.multiply(newNormal, true);
        }

        return newNormal.normalize();
    }
}
