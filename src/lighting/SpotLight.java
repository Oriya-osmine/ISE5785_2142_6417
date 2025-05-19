package lighting;
import primitives.*;

public class SpotLight extends PointLight{

    /**
     * The direction the light points at
     */
    private Vector direction;
    /**
     * Constructor
     *
     * @param intensity the intensity of the light
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    public SpotLight setDirection(Vector direction){
        this.direction = direction;
        return this;
    }
    @Override
    public Color getIntensity(Point p) {
        Color superColor = super.getIntensity(p);
        return superColor.scale(Math.max(0, direction.dotProduct(getL(p))));
    }

    @Override
    public PointLight setKc(double kC) {
        super.setKc(kC);
        return this;
    }

    @Override
    public PointLight setKl(double kL) {
        super.setKl(kL);
        return this;
    }

    @Override
    public PointLight setKq(double kQ) {
        super.setKq(kQ);
        return this;
    }
}
