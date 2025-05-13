package scene;
import primitives.Color;
import lighting.Ambientlight;
import geometries.Geometries;
public class Scene {
    public String name;
    public Color background;
    public Ambientlight ambientlight =Ambientlight.NONE;
    public Geometries geometries = new Geometries();

    public Scene(String nameScene){
        name=nameScene;
    }

    public Scene setName(String name) {
        this.name = name;
        return this;
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return this;

    }

    public Scene setAmbientlight(Ambientlight ambientlight) {
        this.ambientlight = ambientlight;
        return this;

    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;

    }
}
