package lighting;

import primitives.*;

/**
 * Represents a point light source in the scene.
 * A point light emits light in all directions from a specific position in space.
 * The intensity of the light decreases with distance based on attenuation coefficients.
 */
public class PointLight extends Light implements LightSource {

    /**
     * The location of the light
     */
    protected Point position;
    /**
     * How constant the light intensity remains over distance aka Constant attenuation coefficient
     */
    private double kC = 1;
    /**
     * How the light intensity decreases linearly with distance aka Linear attenuation coefficient
     */
    private double kL = 0;
    /**
     * How the light intensity decreases quadratically with distance aka Quadratic attenuation coefficient
     */
    private double kQ = 0;


    /**
     * Constructor
     *
     * @param position  the position of the light
     * @param intensity the intensity of the light
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point p) {
        double d2 = p.distance(position);
        return intensity.scale(1 / (kC + kL * d2 + kQ * d2 * d2));
    }

    @Override
    public Vector getL(Point point) {
        return point.subtract(position).normalize();
    }

    /**
     * Sets the Constant attenuation coefficient of the light
     *
     * @param kC the Constant attenuation coefficient
     * @return this light
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Sets the Linear attenuation coefficient of the light
     *
     * @param kL the Linear attenuation coefficient
     * @return this light
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Sets the Quadratic attenuation coefficient of the light
     *
     * @param kQ the Quadratic attenuation coefficient
     * @return this light
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

}
