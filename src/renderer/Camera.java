package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
     *  90 degrees Up from p0
     */
    private Vector vUp;
    /**
     *  90 degrees Right to p0
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

    public Point getP0(){
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


    public static class Builder{
        /**
         * The camera
         */
        final private Camera camera = new Camera();
        /**
         * @param p the location to set for the camera.
         * @return the Builder instance.
         */
        public Builder setP0(Point p) {
            camera.p0 = p;
            return this;
        }
        /**
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
            return this;
        }
    }
    /**
     * Constructs the ray of view plane
     * @param nX the x of view plane
     * @param nY the y of view plane
     * @param j the
     * @param i
     * @return
     */
    public Ray constructRay(int nX, int nY, int j, int i){
        Point viewCenter = p0.add(vTo.scale(distance));
        Vector upViewCenter = vUp.scale(0.5 * height);
        Point p1 = viewCenter.add(upViewCenter);
        Point p2 = viewCenter.subtract(upViewCenter);
        Point pj =
    }
}
