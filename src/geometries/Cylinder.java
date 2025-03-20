package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Represents a cylinder in a 3D space
 */
public class  Cylinder extends Tube {
    /**
     * The height of the cylinder
     */
    final private double height;

    /**
     * Constructor
     *
     * @param radius The radius
     * @param axisRay The axis ray of the cylinder
     * @param height The height of the cylinder
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
