package lighting;
import primitives.*;

/**
 * represents a light source
 */
public interface LightSource {


    Color getIntensity(Point p);
    Vector getL(Point p);
}
