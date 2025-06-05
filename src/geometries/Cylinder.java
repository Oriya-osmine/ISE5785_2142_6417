package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.util.List;

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
        Point axisOrigin = ray.getPoint(0);
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
        // Duplicate code but faster than calling super
        return surfacePoint.subtract(projectedPoint).normalize();
    }
    @Override
    public List<Intersection> calculateIntersectionsHelper(Ray ray, double maxDistance) {
        // First, find intersections with the curved surface using Tube's method
        List<Intersection> intersections = new java.util.LinkedList<>();
        List<Intersection> tubeIntersections = super.calculateIntersectionsHelper(ray, maxDistance);

        // Check if there are intersections with the curved surface
        if (tubeIntersections != null) {
            // Filter out intersections that are outside the height range
            for (Intersection intersection : tubeIntersections) {
                Point point = intersection.point;
                Point axisOrigin = this.ray.getPoint(0);
                Vector axisDirection = this.ray.getDirection();

                // Calculate projection of intersection point on the axis
                double projectionLength = axisDirection.dotProduct(point.subtract(axisOrigin));

                // If intersection is within height limits, add it
                if (projectionLength >= 0 && projectionLength <= height) {
                    intersections.add(intersection);
                }
            }
        }

        // Check for intersections with the base circles
        Point axisOrigin = this.ray.getPoint(0);
        Vector axisDirection = this.ray.getDirection();
        double rSquared = radius * radius;

        // Calculate intersection with bottom base (at axisOrigin)
        double t1 = calculateBaseIntersection(ray, axisOrigin, axisDirection.scale(-1.0));
        if (t1 > 0 && t1 <= maxDistance) {
            Point intersectionPoint = ray.getPoint(t1);
            if (intersectionPoint.subtract(axisOrigin).lengthSquared() <= rSquared) {
                intersections.add(new Intersection(this, intersectionPoint));
            }
        }

        // Calculate intersection with top base (at axisOrigin + height*axisDirection)
        Point topCenter = axisOrigin.add(axisDirection.scale(height));
        double t2 = calculateBaseIntersection(ray, topCenter, axisDirection);
        if (t2 > 0 && t2 <= maxDistance) {
            Point intersectionPoint = ray.getPoint(t2);
            if (intersectionPoint.subtract(topCenter).lengthSquared() <= rSquared) {
                intersections.add(new Intersection(this, intersectionPoint));
            }
        }

        return intersections.isEmpty() ? null : intersections;
    }

    /**
     * Helper method to calculate intersection with a base circle
     * @param ray The ray to check intersections with
     * @param baseCenter The center point of the base
     * @param baseNormal The normal vector to the base (points outside the cylinder)
     * @return The parameter t for ray intersection or -1 if no intersection
     */
    private double calculateBaseIntersection(Ray ray, Point baseCenter, Vector baseNormal) {
        Vector rayDirection = ray.getDirection();
        Point rayOrigin = ray.getPoint(0);

        // Check if ray is parallel to the base
        double denominator = rayDirection.dotProduct(baseNormal);
        if (Math.abs(denominator) < 1e-10) {
            return -1; // Ray is parallel to the base
        }

        // Calculate t value for intersection with base plane
        double numerator = baseNormal.dotProduct(baseCenter.subtract(rayOrigin));
        return numerator / denominator;
    }



}
