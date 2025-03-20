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
     */
    public RadicalGeometry(double radius) {
        this.radius = radius;
    }
}
