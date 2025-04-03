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
    public Vector getNormal(Point surfacePoint) {
        Point axisOrigin = ray.getHead();
        Vector axisDirection = ray.getDirection();

        // Compute the projection of the surface point onto the cylinder's axis
        double projectionLength = axisDirection.dotProduct(surfacePoint.subtract(axisOrigin));
        // Check if the point is on one of the bases
        if (projectionLength <= 0) {
            return axisDirection.scale(-1.0); // Bottom base normal
        }
        if (projectionLength >= height) {
            return axisDirection; // Top base normal
        }

        // Point is on the curved surface
        Point projectedPoint = axisOrigin.add(axisDirection.scale(projectionLength));
        return surfacePoint.subtract(projectedPoint).normalize();
    }

}
