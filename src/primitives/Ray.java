package primitives;

import static primitives.Util.*;

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
        if (isZero(t))
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

}
