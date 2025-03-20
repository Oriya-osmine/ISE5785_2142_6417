package geometries;

import primitives.Ray;
import primitives.Vector;
import primitives.Point;

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
     * @param ray the axis ray of the tube
     */
    public Tube(double radius, Ray ray) {
        super(radius);
        this.ray = ray;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
