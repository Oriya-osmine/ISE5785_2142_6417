package renderer;

import geometries.Intersectable.Intersection;
import primitives.*;
import scene.Scene;

import java.util.List;

/**
 * Represents a simple ray tracer
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * Max recursion level
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * Min attenuation
     */
    private static final double MIN_CALC_COLOR_K = 0.001;
    /**
     * Initial attenuation
     */
    private static final Double3 INITIAL_K = Double3.ONE;


    /**
     * Constructor, uses super
     *
     * @param scene the scene to use
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    private static final double DELTA = 0.1;

    @Override
    public Color traceRay(Ray ray) {
        List<Intersection> intersections = scene.geometries.calculateIntersections(ray);
        return intersections == null ? scene.background : calcColor(ray.findClosestIntersection(intersections), ray);
    }


    /**
     * Finds the closest intersection of a ray with objects in the scene
     *
     * @param ray The ray to trace
     * @return The closest intersection or null if none exists
     */
    protected Intersection findClosestIntersection(Ray ray) {
        try {
            List<Intersection> intersections = scene.geometries.calculateIntersections(ray);
            if (intersections == null) {
                return null;
            }
            return ray.findClosestIntersection(intersections);
        } catch (IllegalArgumentException e) {
            // Handle zero vector case that can occur during intersection calculations
            return null;
        }
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
        List<Intersection> shadowIntersections = scene.geometries.calculateIntersections(shadowRay, lightDistance);

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

}
