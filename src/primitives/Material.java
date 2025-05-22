package primitives;

/**
 * Represents the material properties of a geometry, including reflection coefficients
 * and shininess factor for lighting calculations.
 */
public class Material {
    /**
     * How the material reflects ambient light aka Ambient reflection coefficient
     */
    public Double3 kA = Double3.ONE;
    /**
     * How the material reflects specular highlights aka Specular reflection coefficient
     */
    public Double3 kS = Double3.ZERO;
    /**
     * How the light diffuses across the surface aka Diffuse reflection coefficient
     */
    public Double3 kD = Double3.ZERO;
    /**
     * How sharp or blurry the specular reflection is aka Shininess factor
     */
    public int nSH = 0;

    /**
     * Sets Ambient reflection coefficient based on double3
     * @param kA the Ambient reflection coefficient
     * @return this Material
     */
    public Material setKA(Double3 kA) {
        this.kA = kA;
        return this;
    }
    /**
     * Sets Ambient reflection coefficient based on double
     * @param kA the Ambient reflection coefficient
     * @return this Material
     */
    public Material setKA(Double kA) {
        this.kA = new Double3(kA);
        return this;
    }
    /**
     * Sets Specular reflection coefficient based on double3
     * @param kS the Specular reflection coefficient
     * @return this Material
     */
    public Material setKS(Double3 kS) {
        this.kS = kS;
        return this;
    }
    /**
     * Sets Specular reflection coefficient based on double
     * @param kS the Specular reflection coefficient
     * @return this Material
     */
    public Material setKS(Double kS) {
        this.kS = new Double3(kS);
        return this;
    }
    /**
     * Sets Diffuse reflection coefficient based on double3
     * @param kD the Diffuse reflection coefficient
     * @return this Material
     */
    public Material setKD(Double3 kD) {
        this.kD = kD;
        return this;
    }
    /**
     * Sets Diffuse reflection coefficient based on double
     * @param kD the Diffuse reflection coefficient
     * @return this Material
     */
    public Material setKD(Double kD) {
        this.kD = new Double3(kD);
        return this;
    }
    /**
     * Sets Shininess factor
     * @param nSH the Shininess factor
     * @return this Material
     */
    public Material setShininess(int nSH) {
        this.nSH = nSH;
        return this;
    }

}
