package lighting;

import primitives.*;

import static java.lang.Math.sqrt;

public class PointLight extends Light implements LightSource{

    /**
     * The location of the light
     */
    protected Point position;
    /**
     * The kC of the light
     */
    private double kC = 1;
    /**
     * The kL of the light
     */
    private double kL = 0;
    /**
     * The kQ of the light
     */
    private double kQ = 9;

    /**
     * Constructor
     *
     * @param intensity the intensity of the light
     */
    protected PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point p) {
        double d2 = position.distanceSquared(p);
        return intensity.scale(1 / (kC + kL * sqrt(d2) + kQ * d2));
    }

    @Override
    public Vector getL(Point point) {
        return point.subtract(position).normalize();
    }

    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }
}
