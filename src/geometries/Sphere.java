package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a sphere in 3D space defined by a center point and a radius.
 * Used for geometric calculations and rendering in 3D graphics.
 */
public class Sphere extends RadicalGeometry {
    /**
     * The center point of the sphere.
     * This defines the sphere's position in 3D space.
     */
    final private Point center;

    /**
     * Creates a new sphere with a given radius and center point.
     *
     * @param radius The radius of the sphere (must be greater than zero).
     * @param center The center of the sphere in 3D space.
     * @throws IllegalArgumentException if the radius is zero or negative.
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }


    @Override
    public Vector getNormal(Point surfacePoint) {
        //(surfacePoint-center)/|surfacePoint-O|
        return surfacePoint.subtract(center).normalize();
    }

}
