package geometries;

import lighting.LightSource;
import primitives.Material;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
import voxel.AABB;
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
    /**
     * Finds all intersection points between the given ray and the object within a maximum distance.
     *
     * @param ray The ray to intersect with the object.
     * @param maxDistance The maximum distance for intersection points.
     * @return A list of intersection points within the maximum distance.
     */
    public final List<Point> findIntersections(Ray ray, double maxDistance) {
        var list = calculateIntersections(ray, maxDistance);
        return list == null ? null : list.stream().map(intersection -> intersection.point).toList();
    }

    /**
     * Calculates all intersections with a ray
     *
     * @param ray         the ray to intersect with
     * @param maxDistance the max distance of the ray
     * @return list of intersections
     */
    public final List<Intersection> calculateIntersections(Ray ray, double maxDistance) {
        return calculateIntersectionsHelper(ray, maxDistance);
    }

    /**
     * Calculates all intersections with a ray
     *
     * @param ray the ray to intersect with
     * @return list of intersections
     */
    public final List<Intersection> calculateIntersections(Ray ray) {
        return calculateIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * helper function to {@link geometries.Intersectable#calculateIntersections(primitives.Ray, double)}
     *
     * @param ray         the ray to intersect with
     * @param maxDistance the max distance of the ray
     * @return list of intersections
     */
    protected abstract List<Intersection> calculateIntersectionsHelper(Ray ray, double maxDistance);

    /**
     * Returns the axis-aligned bounding box (AABB) of the geometry.
     * Used for acceleration structures like BVH (Bounding Volume Hierarchy).
     *
     * @return the AABB that contains this geometry
     */
    public abstract AABB getBoundingBox();

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
        /**
         * The material of the geometry.
         */
        public final Material material;
        /**
         * * The direction of the ray.
         */
        public Vector direction;
        /**
         * The normal vector at the intersection point.
         */
        public Vector normal;
        /**
         * The dot product of the ray direction and the normal vector.
         */
        public double dotProductGeometry;
        /**
         * The light source.
         */
        public LightSource lightSource;
        /**
         * The direction of the light.
         */
        public Vector lightDirection;
        /**
         * The dot product of the light direction and the normal vector.
         */
        public double dotProductLightSource;

        /**
         * Constructor
         *
         * @param geometry the intersection geometry
         * @param point    the intersection point
         */
        public Intersection(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
            this.material = geometry == null ? null : geometry.getMaterial();
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