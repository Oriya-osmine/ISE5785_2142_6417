package lighting;

import primitives.*;

public class DirectionalLight extends Light implements LightSource {

    /**
     * The direction the light points at
     */
    final private Vector direction;
    /**
     * Constructor
     *
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
