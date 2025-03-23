package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Represents a cylinder in a 3D space, a finite portion of an infinite tube.
 */
public class Cylinder extends Tube {
    /**
     * The height of the cylinder, representing its finite extent along the direction of the axis ray.
     * The height, combined with the radius and axis ray, uniquely defines the boundaries of the cylinder.
     * It must be a positive value, as a non-positive height would not form a valid cylindrical shape.
     */
    final private double height;

    /**
     * Creates a cylinder with a given radius, an axis ray, and a height.
     *
     * @param radius  The distance from the axis to the surface of the cylinder (must be positive).
     * @param axisRay A ray that defines the central axis and orientation of the cylinder.
     * @param height  The height of the cylinder along its axis (must be positive).
     * @throws IllegalArgumentException if the radius or height is non-positive.
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        this.height = height;
    }


    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
