package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents Geometry in 3D space.
 */
public abstract class Geometry {
    /**
     * Get the normal to the plane based on the point
     *
     * @param surfacePoint The point to normalize the vector on
     * @return Normalized Vector based on point
     */
    public abstract Vector getNormal(Point surfacePoint);
}
