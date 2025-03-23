package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a flat infinite surface in 3D space.
 */
public class Plane extends Geometry {
    /**
     * A fixed point on the plane.
     */
    final protected Point q;
    /**
     * The perpendicular vector to the plane.
     */
    final protected Vector normal;

    /**
     * Creates a plane by three points in space.
     *
     * @param point1 First point on the plane.
     * @param point2 Second point on the plane.
     * @param point3 Third point on the plane.
     * @throws IllegalArgumentException if the points are collinear.
     */
    public Plane(Point point1, Point point2, Point point3) {
        this.q = point1;
        this.normal = null;
    }

    /**
     * Creates a plane using a point and a normal vector.
     *
     * @param p A point on the plane.
     * @param n A vector perpendicular to the plane.
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
     * Returns the normal vector to the plane.
     *
     * @return The normalized normal vector.
     */
    public Vector getNormal() {
        return normal;
    }
}
