package lighting;

import primitives.*;

/**
 * Represents a directional light source.
 * A directional light has a fixed direction and intensity.
 */
public class DirectionalLight extends Light implements LightSource {

    /**
     * The direction the light points at
     */
    final private Vector direction;

    /**
     * Constructor
     *
     * @param direction the direction of the light
     * @param intensity the intensity of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return intensity;
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }
}
