package geometries;

import primitives.*;

import java.util.List;

/**
 * Represents Geometry in 3D space.
 */
public abstract class Geometry extends Intersectable {

    /**
     * Emission color
     */
    protected Color emission = Color.BLACK;
    /**
     * The material of the geometry
     */
    private Material material = new Material();

    /**
     * Gets the material of the geometry
     *
     * @return the material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the material of the geometry
     *
     * @param material the material to set to
     * @return the geometry
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Gets the emission color of the geometry
     *
     * @return the emission color
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of the geometry
     *
     * @param emission the emission
     * @return the geometry
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Get the normal to the plane based on the point
     *
     * @param surfacePoint The point to normalize the vector on
     * @return Normalized Vector based on point
     */
    public abstract Vector getNormal(Point surfacePoint);

    @Override
    protected abstract List<Intersection> calculateIntersectionsHelper(Ray ray, double maxDistance);
    ;
}
