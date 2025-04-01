package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Represents a cylinder in a 3D space
 */
public class Cylinder extends Tube {
    /**
     * The height of the cylinder
     */
    final private double height;

    /**
     * Constructor
     *
     * @param radius  The radius
     * @param axisRay The axis ray of the cylinder
     * @param height  The height of the cylinder
     * @throws IllegalArgumentException If height is less or equal to zero
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        if (height <= 0)
            throw new IllegalArgumentException("Height must be greater than zero");
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {
        Point p0 = ray.getHead();
        Vector v = ray.getDirection();

        // Calculate the projection of the point onto the ray
        Vector p0ToPoint = point.subtract(p0);
        double t = v.dotProduct(p0ToPoint);

        // Check if the point is on one of the bases
        //1) on buton base
        if (t <= 0) {
            return v.scale(-1.0).normalize();
        }
        //2) on top base
        else if (t >= height) {
            return v.normalize();
        }

        // Point is on the curved surface
        Point o = p0.add(v.scale(t));
        return point.subtract(o).normalize();
    }
}
