package geometries;
import primitives.Point;
import primitives.Ray;
import java.util.List;

/**
 * Interface for objects that can be intersected by a ray.
 */
public interface Intersectable {
    /**
     * Finds all intersection points between the given ray and the object.
     *
     * @param ray The ray to intersect with the object.
     * @return A list of intersection points.
     */
    List<Point> findIntersections(Ray ray);
}