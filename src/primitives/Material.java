package primitives;

public class Material {
    public Double3 kA = Double3.ONE;

    public Material setD3kA(Double3 kA) {
        this.kA = kA;
        return this;
    }

    public Material setDkA(Double kA) {
        this.kA = new Double3(kA);
        return this;
    }
}
