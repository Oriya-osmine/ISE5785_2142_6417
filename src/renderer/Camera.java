package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.stream.IntStream;

import static primitives.Util.isZero;

/**
 * Represents a camera in a 3d space
 */
public class Camera implements Cloneable {
    /**
     * Amount of threads to spare for Java VM threads:<br>
     * Spare threads if trying to use all the cores
     */
    private static final int SPARE_THREADS = 2;
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
     * The center point of the view plane.
     */
    private Point VP_Center;
    /**
     * Amount of threads to use fore rendering image by the camera
     */
    private int threadsCount = 1;
    /**
     * Debug print interval in seconds (for progress percentage)<br>
     * if it is zero - there is no progress output
     */
    private double printInterval = 0;
    /**
     * Pixel manager for supporting:
     * <ul>
     * <li>multi-threading</li>
     * <li>debug print of progress percentage in Console window/tab</li>
     * </ul>
     */
    private PixelManager pixelManager;

    /**
     * The image writer used for rendering the image.
     */
    private ImageWriter imageWriter;

    /**
     * The ray tracer used for tracing rays in the scene.
     */
    private RayTracerBase rayTracer;

    /**
     * The horizontal resolution of the view plane (number of pixels in the X direction).
     */
    private int nX = 1;

    /**
     * The vertical resolution of the view plane (number of pixels in the Y direction).
     */
    private int nY = 1;

    /**
     * The number of rays to cast per pixel when Depth of Field is enabled.
     */
    private int dofRays = 1; // Default to 1 (no DoF)

    /**
     * The size of the camera's virtual aperture.
     * The stronger the value the stronger the blur effect is.
     */
    private double aperture = 1.5;

    /**
     * The distance from the camera's origin (p0) to the focal plane.
     * Objects at this distance will appear in focus, objects closer or farther will be blurred.
     */
    private double focalDistance = 500;

    /**
     * Private constructor to avoid accidental construction.
     */
    private Camera() {
    }


    /**
     * Creates a new camera builder instance
     *
     * @return new builder fo camera
     */

    public static Builder getBuilder() {
        return new Builder();
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
        Point pc = this.VP_Center;

        double rY = height / nY;
        double rX = width / nX;

        double yI = -(i - (nY - 1) * 0.5) * rY;
        double xJ = (j - (nX - 1) * 0.5) * rX;

        Point pij = pc;
        if (!isZero(xJ)) pij = pij.add(vRight.scale(xJ));
        if (!isZero(yI)) pij = pij.add(vUp.scale(yI));

        return new Ray(p0, pij.subtract(p0));
    }

    /**
     * Traces a specific ray from the camera and writes it's color in the correct pixel
     *
     * @param nX     The horizontal resolution of the view plane (number of pixels in the X direction).
     * @param nY     The vertical resolution of the view plane (number of pixels in the Y direction).
     * @param column the matrix column
     * @param row    the matrix row
     */
    private void castRay(int nX, int nY, int column, int row) {
        if (dofRays == 1) {
            imageWriter.writePixel(column, row, rayTracer.traceRay(constructRay(nX, nY, column, row)));
            pixelManager.pixelDone();
            return;
        }
        Point focusPoint = constructRay(nX, nY, column, row).getPoint(focalDistance);
        Color color = Color.BLACK;
        List<Point> jitteredOrigins = jitteredPoints();
        for (Point jitteredOrigin : jitteredOrigins) {
            color = color.add(rayTracer.traceRay(new Ray(jitteredOrigin, focusPoint.subtract(jitteredOrigin))));
        }
        imageWriter.writePixel(column, row, color.reduce(dofRays));
        pixelManager.pixelDone();
    }

    /**
     * Creates a list of jittered points
     *
     * @return the list
     */
    private List<Point> jitteredPoints() {
        List<Point> jitteredOrigins = new LinkedList<>();
        double sampling = aperture * 0.5;
        for (int k = 0; k < dofRays; k++) { // used k instead of i or j to avoid confusion
            double offsetX = (Math.random() * 2 - 1) * sampling;
            double offsetY = (Math.random() * 2 - 1) * sampling;
            Point jitteredOrigin = p0.add(vRight.scale(offsetX)).add(vUp.scale(offsetY));
            jitteredOrigins.add(jitteredOrigin);
        }
        return jitteredOrigins;
    }

    /**
     * Render image using multi-threading by parallel streaming
     *
     * @return the camera object itself
     */
    private Camera renderImageStream() {
        IntStream.range(0, nY).parallel()
                .forEach(i -> IntStream.range(0, nX).parallel()
                        .forEach(j -> castRay(nX, nY, j, i)));
        return this;
    }

    /**
     * Render image without multi-threading
     *
     * @return the camera object itself
     */
    private Camera renderImageNoThreads() {
        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j)
                castRay(nX, nY, j, i);
        return this;
    }

    /**
     * Render image using multi-threading by creating and running raw threads
     *
     * @return the camera object itself
     */
    private Camera renderImageRawThreads() {
        var threads = new LinkedList<Thread>();
        while (threadsCount-- > 0)
            threads.add(new Thread(() -> {
                PixelManager.Pixel pixel;
                while ((pixel = pixelManager.nextPixel()) != null)
                    castRay(nX, nY, pixel.col(), pixel.row());
            }));
        for (var thread : threads) thread.start();
        try {
            for (var thread : threads) thread.join();
        } catch (InterruptedException ignored) {
        }
        return this;
    }

    /**
     * This function renders image's pixel color map from the scene
     * included in the ray tracer object
     *
     * @return the camera object itself
     */
    public Camera renderImage() {
        pixelManager = new PixelManager(nY, nX, printInterval);
        return switch (threadsCount) {
            case 0 -> renderImageNoThreads();
            case -1 -> renderImageStream();
            default -> renderImageRawThreads();
        };
    }

    /**
     * Prints a grid on the image with the specified color and interval.
     *
     * @param color    the color of the grid lines
     * @param interval the interval between grid lines
     * @return the Camera instance
     */
    public Camera printGrid(int interval, Color color) {
        if (color == null || interval <= 0) {
            throw new IllegalArgumentException("Color cannot be null and interval must be greater than 0");
        }

        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
        return this;
    }

    /**
     * Writes the image to the images directory
     *
     * @param name the name of the image written
     * @return this camera
     */
    public Camera writeToImage(String name) {
        this.imageWriter.writeToImage(name);
        return this;
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
         * Sets all DOF parameters ( Rays, aperture & focal distance)
         *
         * @param dofRays       the amount of rays
         * @param aperture      the aperture
         * @param focalDistance the distance to blur
         * @return the Builder instance.
         */
        public Builder setDepthOfField(int dofRays, double aperture, double focalDistance) {
            if (dofRays < 1) throw new IllegalArgumentException("Number of dofRays must be at least 1");
            if (aperture < 0) throw new IllegalArgumentException("Aperture size cannot be negative");
            if (focalDistance < 0) throw new IllegalArgumentException("Focal distance cannot be negative");
            camera.dofRays = dofRays;
            camera.aperture = aperture;
            camera.focalDistance = focalDistance;
            return this;
        }

        /**
         * Sets the focal Distance of depth of field
         *
         * @param focalDistance the distance to blur
         * @return the Builder instance.
         */
        public Builder setFocalDistance(double focalDistance) {
            if (focalDistance < 0) throw new IllegalArgumentException("Focal distance cannot be negative");
            camera.focalDistance = focalDistance;
            return this;
        }

        /**
         * Sets the aperture of depth of field
         *
         * @param aperture the aperture
         * @return the Builder instance.
         */
        public Builder setAperture(double aperture) {
            if (aperture < 0) throw new IllegalArgumentException("Aperture size cannot be negative");
            camera.aperture = aperture;
            return this;
        }

        /**
         * Sets the amount of depth of field rays
         *
         * @param dofRays the amount of rays
         * @return the Builder instance.
         */
        public Builder setDofRays(int dofRays) {
            if (dofRays < 1) throw new IllegalArgumentException("Number of dofRays must be at least 1");
            camera.dofRays = dofRays;
            return this;
        }

        /**
         * Set multi-threading <br>
         * Parameter value meaning:
         * <ul>
         * <li>-2 - number of threads is number of logical processors less 2</li>
         * <li>-1 - stream processing parallelization (implicit multi-threading) is used</li>
         * <li>0 - multi-threading is not activated</li>
         * <li>1 and more - literally number of threads</li>
         * </ul>
         *
         * @param threads number of threads
         * @return builder object itself
         */
        public Builder setMultithreading(int threads) {
            if (threads < -3)
                throw new IllegalArgumentException("Multithreading parameter must be -2 or higher");
            if (threads == -2) {
                int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
                camera.threadsCount = cores <= 2 ? 1 : cores;
            } else
                camera.threadsCount = threads;
            return this;
        }

        /**
         * Set debug printing interval. If it's zero - there won't be printing at all
         *
         * @param interval printing interval in %
         * @return builder object itself
         */
        public Builder setDebugPrint(double interval) {
            if (interval < 0) throw new IllegalArgumentException("interval parameter must be non-negative");
            camera.printInterval = interval;
            return this;
        }

        /**
         * Sets the camera location
         *
         * @param p the location to set for the camera.
         * @return the Builder instance.
         */
        public Builder setLocation(Point p) {
            if (p == null) throw new IllegalArgumentException("location cannot be null");
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
            camera.nX = nX;
            camera.nY = nY;
            return this;
        }

        /**
         * Sets the ray tracer for the camera
         *
         * @param scene the scene to be rendered
         * @param type  the type of ray tracer to use
         * @return the Builder instance.
         */
        public Builder setRayTracer(Scene scene, RayTracerType type) {
            if (type == RayTracerType.SIMPLE) {
                camera.rayTracer = new SimpleRayTracer(scene);
            } else if (type == RayTracerType.VOXEL) {
                camera.rayTracer = new VoxelRayTracer(scene);
            } else {
                camera.rayTracer = null;
            }
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
            camera.imageWriter = new ImageWriter(camera.nX, camera.nY);
            if (camera.rayTracer == null) {
                camera.rayTracer = new SimpleRayTracer(null);
            }

            try {
                camera.VP_Center = camera.p0.add(camera.vTo.scale(camera.distance));
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
