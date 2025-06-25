package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point;

import java.util.LinkedList;
import java.util.List;
import voxel.AABB;

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
        Vector rDirection = ray.getDirection();
        Point rHead = ray.getPoint(0);
        //projectionLength = axisDirection * (P0 - surfacePoint)
        Vector p0ToPoint = surfacePoint.subtract(ray.getPoint(0));

        double t = rDirection.dotProduct(p0ToPoint);
        if (t == 0) {
            return rHead.subtract(surfacePoint);
        }

        //projectedPoint = axisOrigin + projectionLength * axisDirection
        Point projectedPoint = ray.getPoint(t);
        // (surfacePoint - projectedPoint)/|surfacePoint - projectedPoint|
        return surfacePoint.subtract(projectedPoint).normalize();
    }

    @Override
    public List<Intersection> calculateIntersectionsHelper(Ray ray, double maxDistance) {
        Vector v = ray.getDirection();
        Point p0 = ray.getPoint(0);
        Vector va = this.ray.getDirection();
        Point pa = this.ray.getPoint(0);

        // Vector between ray origin and tube axis origin
        Vector deltaP = p0.subtract(pa);

        // Precompute cross products
        Vector v_cross_va = v.crossProduct(va);
        Vector deltaP_cross_va = deltaP.crossProduct(va);

        // Quadratic coefficients
        double vls = v_cross_va.lengthSquared();
        double dpls = deltaP_cross_va.lengthSquared();
        double vals = va.lengthSquared();
        double rSquared = radius * radius;
        // If ray is almost parallel to tube axis
        if (vls < 1e-10) {
            // Calculate distance from ray to axis
            double distanceSquared = dpls / vals;
            if (Math.abs(distanceSquared - rSquared) < 1e-10) {
                // Ray is on the tube surface
                return null;
            }
            if (distanceSquared > rSquared) {
                // Ray is outside the tube
                return null;
            }
            // Ray is inside the tube and parallel - special case
            return null;
        }

        // Calculate remaining quadratic coefficients
        double b = 2 * v_cross_va.dotProduct(deltaP_cross_va);
        double c = dpls - rSquared * vals;

        // Calculate discriminant
        double discriminant = b * b - 4 * vls * c;

        if (discriminant < 0) {
            // No real solutions, ray misses the tube
            return null;
        }

        // Calculate parameters for intersection points
        double sqrtDiscriminant = Math.sqrt(discriminant);
        double a2 = 1 / (2 * vls);
        double t1 = (-b - sqrtDiscriminant) * a2;
        double t2 = (-b + sqrtDiscriminant) * a2;

        // Create a list to store intersections
        List<Intersection> intersections = new LinkedList<>();

        // Check if first intersection is valid
        if (t1 > 0 && t1 <= maxDistance) {
            Point intersectionPoint = ray.getPoint(t1);
            intersections.add(new Intersection(this, intersectionPoint));
        }

        // Check if second intersection is valid
        if (t2 > 0 && t2 <= maxDistance) {
            Point intersectionPoint = ray.getPoint(t2);
            intersections.add(new Intersection(this, intersectionPoint));
        }

        return intersections.isEmpty() ? null : intersections;
    }

    @Override
    public AABB getBoundingBox() {
        double R = this.radius;
        double M = Double.MAX_VALUE / 2;

        Point min = new Point(-M, -M, -M).subtract(new Vector(R, R, R));
        Point max = new Point( M,  M,  M).add   (new Vector(R, R, R));
        return new AABB(min, max);
    }

}
