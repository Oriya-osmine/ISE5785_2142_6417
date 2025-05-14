package scene;
import primitives.Color;
import lighting.AmbientLight;
import geometries.Geometries;
public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientlight =AmbientLight.NONE;
    public Geometries geometries = new Geometries();

    public Scene(String nameScene){
        this.name=nameScene;
    }

    public Scene setName(String name) {
        this.name = name;
        return this;
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return this;

    }

    public Scene setAmbientLight(AmbientLight ambientlight) {
        this.ambientlight = ambientlight;
        return this;

    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;

    }
}
