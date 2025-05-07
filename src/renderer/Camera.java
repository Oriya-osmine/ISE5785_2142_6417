package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

/**
 * Represents a camera in a 3d space
 */
public class Camera {
    /**
     * The camera point
     */
    private Point p0;
    /**
     * Continues straight  from p0
     */
    private Vector vTo;
    /**
     * 90 degrees Up from p0
     */
    private Vector vUp;
    /**
     * 90 degrees Right to p0
     */
    private Vector vRight;
    /**
     * Width of view plane
     */
    private double width;
    /**
     * Height of view plane
     */
    private double height;
    /**
     * Distance of the view plane from p0
     */
    private double distance;

    /**
     * Private constructor to avoid accidental construction.
     */
    private Camera() {
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public Point getP0() {
        return p0;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvRight() {
        return vRight;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDistance() {
        return distance;
    }

    /**
     * Constructs the ray of view plane
     *
     * @param nX the x of view plane
     * @param nY the y of view plane
     * @param j  the matrix column
     * @param i  the matrix row
     * @return the constructed ray
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point pc = p0.add(vTo.scale(distance));

        double rY = height / nY;
        double rX = width / nX;

        double yI = -(i - (nY - 1) * 0.5) * rY;
        double xJ = (j - (nX - 1) * 0.5) * rX;

        Point pij = pc;
        if (!isZero(xJ)) pij = pij.add(vRight.scale(xJ));
        if (!isZero(yI)) pij = pij.add(vUp.scale(yI));

        Vector dir = pij.subtract(p0);
        return new Ray(p0, dir);
    }

    /**
     * Builds the Camera
     */
    public static class Builder {
        /**
         * The camera
         */
        final private Camera camera = new Camera();

        /**
         * @param p the location to set for the camera.
         * @return the Builder instance.
         */
        public Builder setLocation(Point p) {
            camera.p0 = p;
            return this;
        }

        /**
         * Sets the camera direction based on vector up and to
         *
         * @param vUp the "up" vector to set for the camera.
         * @param vTo the "to" vector to set for the camera.
         * @return the Builder instance.
         * @throws IllegalArgumentException if the vectors are not perpendicular.
         */
        public Builder setDirection(Vector vTo, Vector vUp) throws IllegalArgumentException {
            if (!isZero(vUp.dotProduct(vTo)))
                throw new IllegalArgumentException("the vectors vTo and vUp are not perpendicular");
            camera.vUp = vUp.normalize();
            camera.vTo = vTo.normalize();
            camera.vRight = (vTo.crossProduct(vUp)).normalize();
            return this;
        }

        /**
         * Sets the camera direction based on a target point
         *
         * @param target the point to target
         * @return the Builder instance.
         */
        public Builder setDirection(Point target) {
            Vector dir = target.subtract(camera.p0).normalize();
            camera.vTo = dir;
            camera.vRight = dir.crossProduct(new Vector(0, 1, 0)).normalize();
            camera.vUp = camera.vRight.crossProduct(dir).normalize();
            return this;
        }

        /**
         * Sets the camera direction based on a target point and an up vector
         *
         * @param target the point to target
         * @param up     the up vector
         * @return the Builder instance.
         */
        public Builder setDirection(Point target, Vector up) {
            Vector dir = target.subtract(camera.p0).normalize(); // vTo
            Vector vRight = dir.crossProduct(up).normalize();    // vTo × up
            Vector vUp = vRight.crossProduct(dir).normalize();   // vRight × vTo
            camera.vTo = dir;
            camera.vRight = vRight;
            camera.vUp = vUp;
            return this;
        }

        /**
         * Sets the height and width of the matrix
         *
         * @param width  the width
         * @param height the height
         * @return the Builder instance.
         * @throws IllegalArgumentException if one of them is less than 0
         */
        public Builder setVpSize(double width, double height) throws IllegalArgumentException {
            if (width <= 0 || height <= 0)
                throw new IllegalArgumentException("the width and height can't be zero or less");
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * Sets the distance between the camera and the matrix
         *
         * @param distance the distance
         * @return the Builder instance.
         * @throws IllegalArgumentException if distance is lss than 0
         */
        public Builder setVpDistance(double distance) {
            if (distance <= 0)
                throw new IllegalArgumentException("Distance must be greater than zero");
            camera.distance = distance;
            return this;
        }

        /**
         * Sets the resolution of the camera
         *
         * @param nX the X axis
         * @param nY the Y axis
         * @return the Builder instance.
         * @throws IllegalArgumentException if one of them is less than 0
         */
        public Builder setResolution(int nX, int nY) {
            if (nX <= 0 || nY <= 0)
                throw new IllegalArgumentException("Resolution must be positive");
            return this;
        }

        /**
         * Builds the camera
         *
         * @return the built camera
         * @throws MissingResourceException if one of the camera values are invalid
         */
        public Camera build() {
            final String MSG = "Missing rendering parameter";
            final String CLASS = Camera.class.getName();

            if (camera.p0 == null) throw new MissingResourceException(MSG, CLASS, "p0");
            if (camera.vTo == null) throw new MissingResourceException(MSG, CLASS, "vTo");
            if (camera.vUp == null) throw new MissingResourceException(MSG, CLASS, "vUp");
            if (camera.width <= 0) throw new MissingResourceException(MSG, CLASS, "width");
            if (camera.height <= 0) throw new MissingResourceException(MSG, CLASS, "height");
            if (camera.distance <= 0) throw new MissingResourceException(MSG, CLASS, "distance");

            if (camera.vRight == null) {
                camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            }

            try {
                return (Camera) camera.clone();
            } catch (CloneNotSupportedException e) {
                Camera c = new Camera();
                c.p0 = camera.p0;
                c.vTo = camera.vTo;
                c.vUp = camera.vUp;
                c.vRight = camera.vRight;
                c.width = camera.width;
                c.height = camera.height;
                c.distance = camera.distance;
                return c;
            }
        }

    }

}
