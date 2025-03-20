package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents Geometry in 3D space.
 */
public abstract class Geometry {
    /**
     * @param point The point to normalize the vector on
     * @return Normalized Vector based on point
     */
    public abstract Vector getNormal(Point point);
}
