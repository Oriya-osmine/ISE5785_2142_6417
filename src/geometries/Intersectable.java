package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Interface for objects that can be intersected by a ray.
 */
public abstract class Intersectable {

    /**
     * Finds all intersection points between the given ray and the object.
     *
     * @param ray The ray to intersect with the object.
     * @return A list of intersection points.
     */
    public final List<Point> findIntersections(Ray ray) {
        var list = calculateIntersections(ray);
        return list == null ? null : list.stream().map(intersection -> intersection.point).toList();
    }

    protected abstract List<Intersection> calculateIntersectionsHelper(Ray ray);

    public final List<Intersection> calculateIntersections(Ray ray) {
        return calculateIntersectionsHelper(ray);
    }

    /**
     * PDS for an intersection
     */
    public static class Intersection {
        /**
         * the geometry of the intersection
         */
        public final Geometry geometry;
        /**
         * the Point of the intersection
         */
        public final Point point;

        public Intersection(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            return (obj instanceof Intersection other)
                    && this.geometry == other.geometry && this.point.equals(other.point);
        }

        @Override
        public String toString() {
            return "geometry = " + geometry + "\npoint: " + point;
        }
    }
}