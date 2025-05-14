package lighting;

import primitives.Color;

/**
 * Represents ambient light in a scene.
 */
public class AmbientLight {

    /**
     * A constant representing no ambient light.
     */
    static public AmbientLight NONE = new AmbientLight(Color.BLACK);
    /**
     * The intensity of the ambient light.
     */
    private final Color intensity;

    /**
     * Constructs an AmbientLight with the specified color intensity.
     *
     * @param myColor the color intensity of the ambient light
     */
    public AmbientLight(Color myColor) {
        this.intensity = myColor;
    }

    /**
     * Gets the intensity of the ambient light.
     *
     * @return the intensity of the ambient light
     */
    public Color getIntensity() {
        return intensity;
    }
}
