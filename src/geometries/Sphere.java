package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a sphere in 3D space
 */
public class Sphere extends RadicalGeometry {
    /**
     * A point which is the center of the sphere
     */
    Point point;

    /**
     * Constructor
     *
     * @param radius the radius
     * @param point the center
     */
    public Sphere(double radius, Point point) {
        super(radius);
        this.point = point;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
