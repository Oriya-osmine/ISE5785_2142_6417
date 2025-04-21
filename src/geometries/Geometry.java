package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Represents Geometry in 3D space.
 */
public abstract class Geometry implements Intersectable {
    /**
     * Get the normal to the plane based on the point
     *
     * @param surfacePoint The point to normalize the vector on
     * @return Normalized Vector based on point
     */
    public abstract Vector getNormal(Point surfacePoint);

    @Override
    public abstract List<Point> findIntersections(Ray ray);
}
