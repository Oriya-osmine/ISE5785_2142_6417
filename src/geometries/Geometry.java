package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents Geometry in 3D space.
 */
public abstract class Geometry {
    /**
     * Calculates the normal vector to the surface at the given point.
     *
     * @param point The point on the surface for which to compute the normal.
     * @return A normalized vector perpendicular to the surface at the given point.
     */
    public abstract Vector getNormal(Point point);
}
