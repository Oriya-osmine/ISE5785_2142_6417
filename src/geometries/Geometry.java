package geometries;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
    public abstract List<Intersection> calculateIntersectionsHelper(Ray ray);
}
