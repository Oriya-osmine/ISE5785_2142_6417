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
    Point centerPoint;

    /**
     * Constructor
     *
     * @param radius the radius
     * @param centerPoint the center
     */
    public Sphere(double radius, Point centerPoint) {
        super(radius);
        this.centerPoint = centerPoint;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
