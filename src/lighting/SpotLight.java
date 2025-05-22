package lighting;

import primitives.*;

/**
 * Represents a spotlight in the scene.
 * A spotlight is a type of point light that emits light in a specific direction with a narrow beam.
 * The intensity of the light is affected by the angle between the light's direction and the direction to the target point.
 */
public class SpotLight extends PointLight {

    /**
     * The direction the light points at
     */
    private Vector direction;
    /**
     * the narrowness of the beam
     */
    private double narrowBeam = 1;

    /**
     * Constructor
     *
     * @param position  the position of the light
     * @param direction the direction of the light
     * @param intensity the intensity of the light
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * Sets the lights direction
     *
     * @param direction the direction to set to
     * @return this spotlight
     */
    public SpotLight setDirection(Vector direction) {
        this.direction = direction.normalize();
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double additionalFactor = Math.max(0, direction.dotProduct(getL(p)));
        additionalFactor = Math.pow(additionalFactor, narrowBeam);
        return super.getIntensity(p).scale(additionalFactor);
    }

    @Override
    public SpotLight setKc(double kC) {
        super.setKc(kC);
        return this;
    }

    @Override
    public SpotLight setKl(double kL) {
        super.setKl(kL);
        return this;
    }

    @Override
    public SpotLight setKq(double kQ) {
        super.setKq(kQ);
        return this;
    }

    /**
     * Sets the narrowness of the beam.
     *
     * @param narrowBeam the narrowness factor, must be greater than zero
     * @return the current SpotLight instance
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        if (narrowBeam <= 0)
            throw new IllegalArgumentException("narrowBeam must be greater than zero");
        this.narrowBeam = narrowBeam;
        return this;
    }
}
