package renderer;


import geometries.Intersectable;
import geometries.Intersectable.Intersection;
import primitives.Color;
import primitives.Double3;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;
import voxel.AABB;
import voxel.VoxelGrid;
import primitives.Point;

import java.util.LinkedList;
import java.util.List;

/**
 * A ray tracer that uses a voxel grid for spatial acceleration.
 * This class optimizes ray tracing by dividing the scene into a grid of voxels,
 * allowing efficient intersection calculations for bounded and unbounded geometries.
 */

public class VoxelRayTracer extends RayTracerBase {

    /**
     * Number of voxels per object for optimal grid size calculation
     */
    private static final int VOXELS_PER_OBJECT = 4;
    /**
     * The voxel grid
     */
    private final VoxelGrid voxelGrid;
    /**
     * List of geometries without bounding boxes (infinite geometries)
     */
    private final List<Intersectable> unboundedGeometries;


    /**
     * Constructs a VoxelRayTracer with the given scene and blackboard settings.
     *
     * @param scene The scene to be rendered.
     */
    public VoxelRayTracer(Scene scene) {
        super(scene);

        // Calculate optimal grid size based on the number of objects in the scene
        int objectCount = scene.geometries.getGeometries().size();
        int optimalGridSize = (int) Math.cbrt(objectCount * VOXELS_PER_OBJECT);

        // Create the voxel grid based on the scene's bounding box
        AABB sceneBounds = scene.geometries.getBoundingBox();
        this.voxelGrid = new VoxelGrid(sceneBounds, optimalGridSize, optimalGridSize, optimalGridSize);

        // Separate bounded and unbounded geometries
        this.unboundedGeometries = new LinkedList<>();
        for (Intersectable geometry : scene.geometries.getGeometries()) {
            AABB aabb = geometry.getBoundingBox();
            if (aabb == null) {
                unboundedGeometries.add(geometry);
            } else {
                voxelGrid.addObject(geometry, aabb);
            }
        }
    }

    /**
     * Traces a ray and calculates its color based on intersections with the scene.
     *
     * @param ray The ray to be traced.
     * @return The color of the ray based on intersections or the background color if no intersection is found.
     */
    @Override
    public Color traceRay(Ray ray) {
        Intersection intersection = findClosestIntersection(ray);
        return (intersection == null)
                ? scene.background
                : calcColor(intersection, ray);
    }

    /**
     * Finds the closest intersection of a ray with the scene's geometries.
     *
     * @param ray The ray to find intersections for.
     * @return The closest intersection or null if no intersection is found.
     */
    protected Intersection findClosestIntersection(Ray ray) {
        Intersection voxelHit = voxelGrid.findClosestIntersection(ray);
        Intersection unboundedHit = findClosestUnbounded(ray);

        if (voxelHit == null) return unboundedHit;
        if (unboundedHit == null) return voxelHit;

        // Compare distances to determine the closest intersection
        double distVoxel = ray.getPoint(0).distanceSquared(voxelHit.point);
        double distUnbounded = ray.getPoint(0).distanceSquared(unboundedHit.point);
        return distVoxel < distUnbounded ? voxelHit : unboundedHit;
    }

    /**
     * Calculates the transparency factor (ktr) for a given intersection point.
     *
     * @param intersection The intersection to check for shadow transparency.
     * @return A Double3 representing the accumulated transparency (ktr). If ktr falls below MIN_CALC_COLOR_K, returns Double3.ZERO.
     */
    protected Double3 transparency(Intersection intersection) {
        Vector lightDir = intersection.lightDirection.scale(-1.0); // From point to light
        Vector epsVector = intersection.normal.scale(intersection.dotProductGeometry < 0 ? DELTA : -DELTA);
        Point shadowRayOrigin = intersection.point.add(epsVector);
        Ray shadowRay = new Ray(shadowRayOrigin, lightDir); // Shadow ray

        double lightDistance = intersection.lightSource.getDistance(intersection.point);
        List<Intersection> shadowIntersections = voxelGrid.findIntersections(shadowRay, lightDistance);

        Double3 ktr = Double3.ONE;
        if (shadowIntersections == null) return ktr;

        for (Intersection shadowIntersection : shadowIntersections) {
            if (shadowIntersection.point.distance(intersection.point) < lightDistance) {
                ktr = ktr.product(shadowIntersection.material.kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
                    return Double3.ZERO;
                }
            }
        }

        return ktr;
    }

    /**
     * Finds the closest intersection of a ray with unbounded geometries.
     *
     * @param ray The ray to find intersections for.
     * @return The closest intersection or null if no intersection is found.
     */
    private Intersection findClosestUnbounded(Ray ray) {
        Intersection closest = null;
        double minDist = Double.POSITIVE_INFINITY;

        for (Intersectable g : unboundedGeometries) {
            List<Intersection> hits = g.calculateIntersections(ray);
            if (hits != null) {
                for (Intersection i : hits) {
                    double dist = ray.getPoint(0).distance(i.point);
                    if (dist < minDist) {
                        minDist = dist;
                        closest = i;
                    }
                }
            }
        }
        return closest;
    }

}