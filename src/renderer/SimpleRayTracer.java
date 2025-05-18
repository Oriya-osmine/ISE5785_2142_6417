package renderer;

import geometries.Intersectable.Intersection;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * Represents a simple ray tracer
 */
public class SimpleRayTracer extends RayTracerBase {
    /**
     * Constructor, uses super
     *
     * @param scene the scene to use
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Intersection> intersections = scene.geometries.calculateIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }
        Intersection closestIntersection = ray.findClosestIntersection(intersections);
        return calcColor(closestIntersection);
    }

    /**
     * Gets the AmbientLight color intensity
     *
     * @param intersection the intersection to use
     * @return the AmbientLight color intensity
     */
    private Color calcColor(Intersection intersection) {
        return scene.ambientlight.getIntensity().add(intersection.geometry.getEmission());
    }
}
