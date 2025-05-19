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
    protected SpotLight(Color intensity, Point position, Vector direction) {
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
    public PointLight setkC(double kC) {
        super.setkC(kC);
        return this;
    }

    @Override
    public PointLight setkL(double kL) {
        super.setkL(kL);
        return this;
    }

    @Override
    public PointLight setkQ(double kQ) {
        super.setkQ(kQ);
        return this;
    }
}
