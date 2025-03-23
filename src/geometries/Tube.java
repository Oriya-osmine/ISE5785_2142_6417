package geometries;
//

import primitives.Ray;
import primitives.Vector;
import primitives.Point;

/**
 * A simple representation of an infinite tube in 3D space.
 * The tube is defined by its radius and central axis.
 */
public class Tube extends RadicalGeometry {
    /**
     * The central axis of the tube, represented as a {@link Ray}.
     */
    final protected Ray ray;

    /**
     * Constructs a tube with a specific radius and axis.
     *
     * @param radius the radius of the tube (must be greater than 0).
     * @param ray    the central axis of the tube.
     * @throws IllegalArgumentException if the radius is zero or negative.
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

