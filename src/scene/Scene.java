package scene;

import Parser.XMLParser;
import primitives.Color;
import lighting.AmbientLight;
import geometries.Geometries;

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
    public Color background;
    /**
     * The scene ambient light
     */
    public AmbientLight ambientlight = AmbientLight.NONE;
    /**
     * The scene geometries
     */
    public Geometries geometries = new Geometries();

    /**
     * Constructor, constructs name of the scene
     *
     * @param nameScene name of the scene
     */
    public Scene(String nameScene) {
        if (nameScene.endsWith(".xml")) {
            this.name = nameScene.substring(0, nameScene.length() - 4); // Remove ".xml"
            XMLParser.SceneConstructor(nameScene, this);
        } else {
            this.name = nameScene;
        }
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
        this.geometries = geometries;
        return this;
    }
}
