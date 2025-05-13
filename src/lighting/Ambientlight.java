package lighting;
import primitives.Color;

/**
 * Represents ambient light in a scene.
 */
public class Ambientlight {

    /** The intensity of the ambient light. */
    private final Color intenstiy;

    /** A constant representing no ambient light. */
    static public Ambientlight NONE = new Ambientlight(Color.BLACK);

    /**
     * Constructs an Ambientlight with the specified color intensity.
     *
     * @param myColor the color intensity of the ambient light
     */
    public Ambientlight(Color myColor) {
        intenstiy = myColor;
    }

    /**
     * Gets the intensity of the ambient light.
     *
     * @return the intensity of the ambient light
     */
    public Color getIntensity() {
        return intenstiy;
    }
}
