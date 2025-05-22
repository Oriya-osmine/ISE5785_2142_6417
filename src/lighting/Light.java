package lighting;

import primitives.Color;

/**
 * Represents the light
 */
class Light {
    /**
     * the intensity of the light
     */
    final protected Color intensity;

    /**
     * Constructor
     *
     * @param intensity the intensity of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Gets the light intensity
     *
     * @return the light's intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}
