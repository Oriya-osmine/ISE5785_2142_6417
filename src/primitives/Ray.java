package primitives;

/**
 * A ray in 3D space, defined by its start point and direction.
 * The direction is always normalized for accurate calculations.
 */
public class Ray {

    /**
     * The starting point of the ray.
     */
    final private Point head;

    /**
     * The normalized direction of the ray.
     */
    final private Vector direction;

    /**
     * Creates a ray using the given start point and direction vector.
     *
     * @param startPoint      The start point of the ray.
     * @param directionVector The direction of the ray, normalized automatically.
     */
    public Ray(Point startPoint, Vector directionVector) {
        this.head = startPoint;
        this.direction = directionVector.normalize();
    }
}
