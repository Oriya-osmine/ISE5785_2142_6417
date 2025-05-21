package lighting;

import primitives.*;

public class PointLight extends Light implements LightSource{

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

    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

}
