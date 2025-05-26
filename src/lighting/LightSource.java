package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * represents a light source
 */
public interface LightSource {


    /**
     * Gets the intensity of the light at a specific point.
     *
     * @param p the point where the intensity is calculated
     * @return the intensity of the light at the given point
     */
    Color getIntensity(Point p);

    /**
     * Gets the direction vector from the light source to a specific point.
     *
     * @param p the point where the direction is calculated
     * @return the direction vector from the light source to the given point
     */
    Vector getL(Point p);

    double getDistance(Point point);

}
