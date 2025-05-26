package renderer;

import geometries.Intersectable;
import geometries.Intersectable.Intersection;
import lighting.LightSource;
import primitives.Color;
import primitives.Double3;
import primitives.Ray;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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

    private static final double DELTA = 0.1;

    @Override
    public Color traceRay(Ray ray) {
        List<Intersection> intersections = scene.geometries.calculateIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }
        Intersection closestIntersection = ray.findClosestIntersection(intersections);
        return calcColor(closestIntersection, ray);
    }

    /**
     * Gets the AmbientLight color intensity
     *
     * @param ray          the ray to use it on
     * @param intersection the intersection to use
     * @return the AmbientLight color intensity
     */
    private Color calcColor(Intersection intersection, Ray ray) {
        if (!preprocessIntersection(intersection, ray.getDirection()))
            return Color.BLACK;
        return scene.ambientlight.getIntensity().scale(intersection.geometry.getMaterial().kA).add(calcColorLocalEffects(intersection));
    }

    /**
     * Sets the light source and related objects in Intersection
     *
     * @param intersection the intersection
     * @param lightSource  the light source
     * @return true if the light source is a valid one, false otherwise
     */
    public boolean setLightSource(Intersection intersection, LightSource lightSource) {
        intersection.lightSource = lightSource;
        intersection.lightDirection = lightSource.getL(intersection.point);
        intersection.dotProductLightSource = alignZero(intersection.lightDirection.dotProduct(intersection.normal));
        return intersection.dotProductGeometry * intersection.dotProductLightSource > 0;
    }

    /**
     * Initializes intersection direction, normal and their dotProduct
     *
     * @param intersection the intersection
     * @param direction    the direction of the ray
     * @return true if the function succeed, false otherwise
     */
    private boolean preprocessIntersection(Intersection intersection, Vector direction) {
        intersection.direction = direction;
        intersection.normal = intersection.geometry.getNormal(intersection.point);
        intersection.dotProductGeometry = alignZero(direction.dotProduct(intersection.normal));
        return !isZero(intersection.dotProductGeometry);
    }

    /**
     * Calculates the local effects of lighting at an intersection.
     *
     * @param intersection The intersection
     * @return The color result.
     */
    private Color calcColorLocalEffects(Intersectable.Intersection intersection) {
        Color color = intersection.geometry.getEmission();
        for (LightSource lightSource : scene.lights) {
            if (setLightSource(intersection, lightSource) &&unshaded(intersection) ) {

                color = color.add(lightSource.getIntensity(intersection.point).scale(calcDiffusive(intersection).add(calcSpecular(intersection))));
            }

        }
        return color;
    }

    /**
     * Checks if an intersection point is not in shadow.
     *
     * @param intersection The intersection to check
     * @return true if the point is not shadowed, false otherwise
     */
    private boolean unshaded(Intersection intersection) {
        Vector pointToLight = intersection.lightDirection.scale(-1.0); // From point to light source
        Vector epsVector = intersection.normal.scale(intersection.dotProductGeometry < 0 ? DELTA : -DELTA);
        Point point = intersection.point.add(epsVector);
        Ray shadowRay = new Ray(point, pointToLight); // Shadow ray
        List<Intersection> intersections = scene.geometries.calculateIntersections(shadowRay);

        if (intersections == null) {
            return true; // No intersections, point is not shadowed
        }

        double lightDistance = intersection.lightSource.getDistance(intersection.point);
        for (Intersection shadowIntersection : intersections) {
            if (alignZero(shadowIntersection.point.distance(intersection.point) - lightDistance) <= 0) {
                return false; // Point is shadowed
            }
        }

        return true; // Point is not shadowed
    }



    /**
     * Calculates the specular lighting effect at an intersection.
     *
     * @param intersection The intersection.
     * @return The specular lighting effect.
     */
    private Double3 calcSpecular(Intersectable.Intersection intersection) {
        Vector nDirection = intersection.direction.scale(-1.0);
        double refraction = nDirection.dotProduct(calcReflection(intersection));
        double factor = Math.pow(refraction <= 0 ? 0 : refraction, intersection.material.nSH);
        return intersection.material.kS.scale(factor);
    }

    /**
     * Calculates the diffusive lighting effect at an intersection.
     *
     * @param intersection The intersection.
     * @return The diffusive lighting effect.
     */
    private Double3 calcDiffusive(Intersectable.Intersection intersection) {
        Double3 res = intersection.material.kD.scale(intersection.dotProductLightSource);
        double q1 = res.d1() < 0 ? -res.d1() : res.d1();
        double q2 = res.d2() < 0 ? -res.d2() : res.d2();
        double q3 = res.d3() < 0 ? -res.d3() : res.d3();
        return new Double3(q1, q2, q3);
    }

    /**
     * Calculates the reflection vector at an intersection.
     *
     * @param intersection The intersection.
     * @return The reflection vector.
     */
    private Vector calcReflection(Intersectable.Intersection intersection) {
        Vector normal = intersection.normal;
        return intersection.lightDirection.add((normal.scale(intersection.lightDirection.dotProduct(normal)).scale(-2.0)));
    }
}
