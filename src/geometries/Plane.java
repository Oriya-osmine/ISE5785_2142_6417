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
    final private Point q;
    /**
     * The normal of the plane
     */
    final private Vector normal;

    /**
     * Constructor
     *
     * @param p1 point number 1
     * @param p2 point number 2
     * @param p3 point number 3
     * @throws IllegalArgumentException if one or more point are equals
     */
    public Plane(Point p1, Point p2, Point p3) {
        if (p1.equals(p2) || p1.equals(p3) || p2.equals(p3))
            throw new IllegalArgumentException("Cannot construct a plane from 2 or less points");
        this.q = p1;
        this.normal = p2.subtract(p1).crossProduct(p3.subtract(p1)).normalize();
    }

    /**
     * Constructor
     *
     * @param anchorPoint  The main point
     * @param normalVector The vector of the main point according to the plane
     */
    public Plane(Point anchorPoint, Vector normalVector) {
        this.q = anchorPoint;
        this.normal = normalVector.normalize();
    }


    @Override
    public Vector getNormal(Point surfacePoint) {
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
