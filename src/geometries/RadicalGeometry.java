package geometries;

/**
 * Represents Radical Objects in 3D space
 */
public abstract class RadicalGeometry extends Geometry {
    /**
     * The radius of the Radical object
     */
    final protected double radius;

    /**
     * Constructs the object
     *
     * @param radius The radius to set for the object
     * @throws IllegalArgumentException If radius is less or equal to zero
     */
    public RadicalGeometry(double radius) {
        if (radius <= 0)
            throw new IllegalArgumentException("radius must be greater than zero");
        this.radius = radius;
    }
}
