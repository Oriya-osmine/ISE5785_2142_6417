package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a collection of shapes in 3D space
 */
public class Geometries extends Intersectable {
    /**
     * list of shapes in 3D space
     */
    private final List<Intersectable> geometries = new LinkedList<>();

    /**
     * Empty constructor
     */
    public Geometries() {

    }

    /**
     * Constructor
     *
     * @param geometries the geometries to add to the space
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * adds the geometries to the space
     *
     * @param geometries the geometries to add to the space
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(this.geometries, geometries);
    }


    @Override
    protected List<Intersection> calculateIntersectionsHelper(Ray ray, double maxDistance) {
        List<Intersection> intersections = null;

        for (Intersectable geometry : geometries) {
            List<Intersection> geometryIntersections = geometry.calculateIntersectionsHelper(ray, maxDistance);
            if (geometryIntersections != null) {
                if (intersections == null)
                    intersections = new LinkedList<>();
                intersections.addAll(geometryIntersections);
            }
        }
        return intersections;
    }

}
