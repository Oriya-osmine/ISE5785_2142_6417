package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import java.util.List;

/**
 * Represents a Tube in a 3D space
 */
public class Tube extends RadicalGeometry {
    /**
     * The axis ray
     */
    final protected Ray ray;

    /**
     * Constructor
     *
     * @param radius the radius of the tube
     * @param ray    the axis ray of the tube
     */
    public Tube(double radius, Ray ray) {
        super(radius);
        this.ray = ray;
    }

    @Override
    public Vector getNormal(Point surfacePoint) {
        Vector axisDirection = ray.getDirection();
        Point axisOrigin = ray.getPoint(0);
        //projectionLength = axisDirection * (P0 - surfacePoint)
        Vector p0ToPoint = surfacePoint.subtract(ray.getPoint(0));

        double projectionLength = axisDirection.dotProduct(p0ToPoint);
        if (projectionLength == 0) {
            return axisOrigin.subtract(surfacePoint);
        }

        //projectedPoint = axisOrigin + projectionLength * axisDirection
        Point projectedPoint = axisOrigin.add(axisDirection.scale(projectionLength));
        // (surfacePoint - projectedPoint)/|surfacePoint - projectedPoint|
        return surfacePoint.subtract(projectedPoint).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return List.of();
    }

}
