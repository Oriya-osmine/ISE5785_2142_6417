package primitives;

import geometries.Intersectable.Intersection;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Represents a ray in 3D space, defined by a starting point (head) and a direction vector.
 * The direction vector is always normalized to ensure consistent mathematical operations.
 */
public class Ray {
    /**
     * The starting point of the ray, representing its origin in 3D space.
     */
    final private Point head;

    /**
     * The direction vector of the ray, always normalized to ensure accurate and consistent computations.
     */
    final private Vector direction;

    /**
     * align zero is not good enough
     */
    private static final double DELTA = 1E-10;


    /**
     * Constructs a new Ray with the specified head and direction.
     * The direction vector is normalized upon construction.
     *
     * @param startPoint      the starting point (head) of the ray
     * @param directionVector the direction vector of the ray
     */
    public Ray(Point startPoint, Vector directionVector) {
        this.head = startPoint;
        this.direction = directionVector.normalize();
    }

    /**
     * Gets the t times the vector + the head of ray
     *
     * @param t the variable to multiply the direction vector with
     * @return t times the vector + the head of ray
     */
    public Point getPoint(double t) {
        if (isZero(alignZero(t)) || t < DELTA)
            return head;
        return head.add(direction.scale(t));
    }

    /**
     * Gets the direction vector of the ray
     *
     * @return direction vector
     */
    public Vector getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray ComapareRay)
                && this.head == ComapareRay.head
                && this.direction.equals(ComapareRay.direction);
    }

    @Override
    public String toString() {
        return "Ray head: " + head + "\nRay direction: " + direction;
    }

    /**
     * Finds the closest point to the ray's head from a given list of points.
     *
     * @param IntersectionList the list of intersections to search for the closest point
     * @return the closest point to the ray's head, or null if the list is null
     */
    public Intersection findClosestIntersection(List<Intersection> IntersectionList) {
        double minDistance, nowDistance;
        int index = 0;
        if (IntersectionList == null) {
            return null;
        }

        minDistance = this.head.distance(IntersectionList.getFirst().point);

        for (int i = 1; i < IntersectionList.size(); i++) {
            nowDistance = this.head.distance(IntersectionList.get(i).point);
            if (minDistance > nowDistance) {
                minDistance = nowDistance;
                index = i;
            }
        }
        return IntersectionList.get(index);
    }

    /**
     * Finds the closest point
     *
     * @param points list of points
     * @return the closest point
     */
    public Point findClosetPoint(List<Point> points) {
        return points == null ? null
                : findClosestIntersection(points.stream().map(p -> new Intersection(null, p)).toList()).point;
    }
}
