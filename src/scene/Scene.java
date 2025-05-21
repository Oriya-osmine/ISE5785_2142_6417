package scene;

import lighting.LightSource;
import primitives.Color;
import lighting.AmbientLight;
import geometries.Geometries;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a scene
 */
public class Scene {
    /**
     * The scene name
     */
    public String name;
    /**
     * The scene background color
     */
    public Color background = Color.BLACK;
    /**
     * The scene ambient light
     */
    public AmbientLight ambientlight = AmbientLight.NONE;
    /**
     * The scene geometries
     */
    public Geometries geometries = new Geometries();
    /**
     * The scene lights
     */
    public List<LightSource> lights = new LinkedList<>();
    /**
     * Constructor, constructs name of the scene
     *
     * @param nameScene name of the scene
     */
    public Scene(String nameScene) {
        if (nameScene.endsWith(".xml")) {
            this.name = nameScene.substring(0, nameScene.length() - 4); // Remove ".xml"
            XMLSceneParser.SceneConstructor(nameScene, this);
        } else {
            this.name = nameScene;
        }
    }

    /**
     * Sets the scene lights
     * @param lights the lights
     * @return this scene
     */
    public Scene setLights(List<LightSource> lights) {
        if(lights != null)
            this.lights = lights;
        return this;
    }

    /**
     * Sets the scene background color
     *
     * @param background the background color
     * @return this scene
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    /**
     * Sets the scene ambient light
     *
     * @param ambientlight the ambient light
     * @return this scene
     */
    public Scene setAmbientLight(AmbientLight ambientlight) {
        this.ambientlight = ambientlight;
        return this;

    }

    /**
     * Sets the scene geometries
     *
     * @param geometries the geometries
     * @return this scene
     */
    public Scene setGeometries(Geometries geometries) {
        if(geometries != null)
            this.geometries = geometries;
        return this;
    }
}
