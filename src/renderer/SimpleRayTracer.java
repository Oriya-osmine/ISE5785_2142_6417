package renderer;

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
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }
        Point closestPoint = ray.findClosetPoint(intersections);
        return calcColor(closestPoint);
    }

    /**
     * Gets the AmbientLight color intensity
     *
     * @param point the point to use
     * @return the AmbientLight color intensity
     */
    private Color calcColor(Point point) {
        return scene.ambientlight.getIntensity();
    }
}
