package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a 2D plane in 3D space
 */
public class Plane extends Geometry {
    /**
     * A point on the plane(out main point)
     */
    final protected Point q;
    /**
     * The normal of the plane
     */
    final protected Vector normal;

    /**
     * Constructor
     *
     * @param point1 point number 1
     * @param point2 point number 2
     * @param point3 point number 3
     */
    public Plane(Point point1, Point point2, Point point3) {
        this.q = point1;
        this.normal = null;
    }

    /**
     * Constructor
     *
     * @param p The main point
     * @param n The vector of the main point according to the plane
     */
    public Plane(Point p, Vector n) {
        this.q = p;
        this.normal = n.normalize();
    }

    @Override
    public Vector getNormal(Point p) {
        return normal;
    }

    /**
     * gets the normal of our main point
     *
     * @return normalized vector of out main point
     */
    public Vector getNormal() {
        return normal;
    }
}
