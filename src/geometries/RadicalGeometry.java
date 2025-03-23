package geometries;

/**
 * Represents a 3D object with a defined radius, like spheres or tubes.
 */
public abstract class RadicalGeometry extends Geometry {
    /**
     * Defines the size of the object as its radius.
     */
    final protected double radius;

    /**
     * Creates a 3D object with a given radius.
     *
     * @param radius The size of the object (must be positive).
     * @throws IllegalArgumentException if the radius is zero or negative.
     */
    public RadicalGeometry(double radius) {
        if (radius <= 0)
            throw new IllegalArgumentException("radius must be greater than zero");
        this.radius = radius;
    }
}
