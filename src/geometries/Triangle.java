package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Represents a triangle in a 3D space
 */
public class Triangle extends Polygon {
    /**
     * Constructs a Triangle from three points
     *
     * @param p1 First point
     * @param p2 Second point
     * @param p3 Third point
     * @throws IllegalArgumentException if not all three points are different from each other
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
        if (p1.equals(p2) || p1.equals(p3) || p2.equals(p3))
            throw new IllegalArgumentException("Cannot construct a plane from 2 or less points");
    }

    @Override
    public List<Intersection> calculateIntersectionsHelper(Ray ray, double maxDistance) {
        // Uses Möller–Trumbore algorithm and Cramer’s rule
        // https://cadxfem.org/inf/Fast%20MinimumStorage%20RayTriangle%20Intersection.pdf
        // H + t * D = (1 - u - v)*p0 + u*p1 + v*p2
        // ⇔ -D * t + (p1 - p0) * u + (p2 - p0) * v = H - p0
        // This gives three equations in the unknowns (t, u, v), one per x,y,z component.
        // We write it in matrix form A·X = B, where:
        //
        //   [ -D.x,  (p1-p0).x,  (p2-p0).x ]   [ t ]   [ H.x - p0.x ]
        //   [ -D.y,  (p1-p0).y,  (p2-p0).y ] · [ u ] = [ H.y - p0.y ]
        //   [ -D.z,  (p1-p0).z,  (p2-p0).z ]   [ v ]   [ H.z - p0.z ]
        //
        // Let A be the 3×3 coefficient matrix, X = [t, u, v]^T, and B the RHS vector.
        // Using Cramer’s Rule:
        //
        //   detA = det( A )
        //
        //   t = det( A_t ) / detA
        //   u = det( A_u ) / detA
        //   v = det( A_v ) / detA
        //

        Point rayOrigin = ray.getPoint(0);
        Vector rayDirection = ray.getDirection();

        Point vertex0 = vertices.getFirst();
        Point vertex1 = vertices.get(1);
        Point vertex2 = vertices.get(2);

        Vector edge1 = vertex1.subtract(vertex0);
        Vector edge2 = vertex2.subtract(vertex0);

        double parallelCos = Math.abs(rayDirection.dotProduct(edge2.normalize()));
        if (alignZero(parallelCos - 1.0) >= 0) {
            // Ray and triangle plane are parallel; no intersection
            return null;
        }
        // Calculate the determinant
        Vector pVec = rayDirection.crossProduct(edge2);
        double determinant = edge1.dotProduct(pVec);

        // If determinant is close to zero, ray is parallel to triangle plane
        // (no solutions or infinite solution)
        if (isZero(alignZero(determinant))) {
            return null;
        }

        // Inverse of determinant for later calculations
        double inverseDet = 1.0 / determinant;

        // Calculate u parameter and test bounds
        Vector tVec = rayOrigin.subtract(vertex0);
        double u = inverseDet * tVec.dotProduct(pVec);
        if (alignZero(u) <= 0.0 || u > 1.0) {
            return null;
        }

        // Calculate v parameter and test bounds
        Vector qVec = tVec.crossProduct(edge1);
        double v = inverseDet * rayDirection.dotProduct(qVec);
        if (alignZero(v) <= 0.0 || u + v >= 1) {
            return null;
        }

        // Calculate distance t from ray origin to intersection point
        double t = inverseDet * edge2.dotProduct(qVec);

        // Use alignZero to check if t is close to zero, ensuring precision handling
        if (alignZero(t) > 0.0 && alignZero(t - maxDistance) <= 0.0) { // Intersection is in front of the ray and within maxDistance
            return List.of(new Intersection(this, ray.getPoint(t)));
        }
        // Intersection is behind the ray (or too small to be considered valid)
        return null;
    }

}
