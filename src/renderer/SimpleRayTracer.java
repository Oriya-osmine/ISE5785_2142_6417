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

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
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
        return calcColor(intersection, MAX_CALC_COLOR_LEVEL, INITIAL_K);
    }

    /**
         * Calculates a single global effect (reflection or transparency) for a secondary ray.
         *
         * @param secondaryRay The secondary ray (reflection or transparency)
         * @param level The recursion level
         * @param k The accumulated attenuation factor
         * @param kEffect The attenuation coefficient for this effect (kR or kT)
         * @return The color contribution from this global effect
         */
    private Color calcGlobalEffect(Ray secondaryRay, int level, Double3 k, Double3 kEffect) {
        Intersection intersection = findClosestIntersection(secondaryRay);
        if (intersection == null) {
            return Color.BLACK;
        }

        // Initialize the intersection with the secondary ray's direction
        if (!preprocessIntersection(intersection, secondaryRay.getDirection())) {
            return Color.BLACK;
        }

        Color effectColor = calcColor(intersection, level - 1, k.product(kEffect));
        return effectColor.scale(kEffect);

    }


    /**
     * Calculates the global effects (reflection and transparency) at an intersection.
     *
     * @param intersection The intersection point
     * @param level The recursion level
     * @param k The accumulated attenuation factor
     * @return The combined color from global effects (transparency and reflection)
     */
    private Color calcGlobalEffects(Intersection intersection, int level, Double3 k) {
        // If the level of recursion is at base or the effect is negligible - stop recursion
        Color color = intersection.geometry.getEmission();
        if (level == 1 || k.lowerThan(MIN_CALC_COLOR_K)) {
            return Color.BLACK;
        }

        // Add ambient light contribution
        if (scene.ambientlight.getIntensity() != null) {
            color = color.add(scene.ambientlight.getIntensity().scale(intersection.material.kA));
        }

        Vector v = intersection.direction;
        Vector n = intersection.normal;
        Point point = intersection.point;
        double nv = alignZero(n.dotProduct(v));

        // Calculate reflection contribution if material has reflection
        Double3 kR = intersection.material.kR;
        if (!kR.equals(Double3.ZERO)) {
            // r = v - 2(v·n)n
            Vector r = v.subtract(n.scale(nv*2));
            // Create slightly offset reflection ray to avoid self-intersection
            Ray reflectedRay = new Ray(point.add(n.scale(nv < 0 ? DELTA : -DELTA)), r);
            color = color.add(calcGlobalEffect(reflectedRay, level, k, kR));
        }


        // Calculate transparency/refraction contribution if material has transparency
        Double3 kT = intersection.material.kT;
        if (!kT.equals(Double3.ZERO)) {
            // For transparency, ray continues in the same direction
            Ray reflectedRay = new Ray(point.add(n.scale(nv > 0 ? DELTA : -DELTA)), v);
            color = color.add(calcGlobalEffect(reflectedRay, level, k, kT));
        }
       

        return color;
    }


    private Color calcColor(Intersection intersection, int level, Double3 k) {
        Vector n = intersection.geometry.getNormal(intersection.point);
        Vector v = intersection.direction; // Use the direction from the intersection
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;

        Color color = calcColorLocalEffects(intersection);
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, level, k));
    }
/**
 * Finds the closest intersection of a ray with objects in the scene
 *
 * @param ray The ray to trace
 * @return The closest intersection or null if none exists
 */
private Intersection findClosestIntersection(Ray ray) {
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
            if (setLightSource(intersection, lightSource)) {
                Double3 ktr = transparency(intersection);
                if(!(ktr.product(INITIAL_K)).lowerThan(MIN_CALC_COLOR_K))
                    color = color.add(lightSource.getIntensity(intersection.point).scale(ktr).scale(calcDiffusive(intersection).add(calcSpecular(intersection))));
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

        double lightDistance = intersection.lightSource.getDistance(intersection.point);
        List<Intersection> intersections = scene.geometries.calculateIntersections(shadowRay, lightDistance);

        if (intersections == null)
            return true; // No intersections = not shadowed

        // Check if any intersecting object has low transparency (causes shadow)
        for (Intersection i : intersections) {
            if (i.material.kT.lowerThan(MIN_CALC_COLOR_K))
                return false; // Found a non-transparent enough object = shadowed
        }

        return true; // All intersecting objects are transparent enough
    }

    /**
     * Calculates the transparency factor (ktr) for a given intersection point.
     *
     * @param intersection The intersection to check for shadow transparency.
     * @return A Double3 representing the accumulated transparency (ktr). If ktr falls below MIN_CALC_COLOR_K, returns Double3.ZERO.
     */
    private Double3 transparency(Intersection intersection) {
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
