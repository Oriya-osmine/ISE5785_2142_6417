package geometries;

import primitives.Point;

/**
 * Represents a triangle in a 3D space
 */
public class Triangle extends Polygon {
    /**
     * Constructs a Triangle from three points
     *
     * @param p1 First point
     * @param p2 Second point
     * @param p3 Third point
     * @throws IllegalArgumentException if not all three points are different from each other
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
        if (p1.equals(p2) || p1.equals(p3) || p2.equals(p3))
            throw new IllegalArgumentException("Cannot construct a plane from 2 or less points");
    }
}
