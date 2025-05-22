package parseXML;

import lighting.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Files;
import java.nio.file.Paths;

import primitives.*;
import scene.Scene;

import static parseXML.geometriesParser.parseGeometries;
import static parseXML.lightsParser.parseLights;
import static parseXML.primitivesParser.parseColor;

/**
 * Class to parse xml file into a scene
 */
public class sceneParser {
    /**
     * Constructs a scene using xml
     *
     * @param filePath the path to xml
     * @param scene    the scene
     */
    public static void SceneConstructor(String filePath, Scene scene) {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(Files.newInputStream(Paths.get(System.getProperty("user.dir") + "/xml/" + filePath)));
            doc.getDocumentElement().normalize();
            Element sceneElement = doc.getDocumentElement();

            scene.setBackground(parseBackground(sceneElement.getAttribute("background-color")));

            scene.setAmbientLight(parseAmbientLight(sceneElement.getElementsByTagName("ambient-light")));

            scene.setGeometries(parseGeometries(sceneElement.getElementsByTagName("geometries")));

            scene.setLights(parseLights(sceneElement.getElementsByTagName("lights")));

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Parses background
     *
     * @param background the background to parse
     * @return the scene background
     */
    private static Color parseBackground(String background) {
        if (!background.isEmpty()) {
            return parseColor(background);
        } else {
            return Color.BLACK;
        }
    }

    /**
     * Parses ambient light
     *
     * @param ambientLight the ambient light to parse
     * @return the scene ambient light
     */
    private static AmbientLight parseAmbientLight(NodeList ambientLight) {
        if (ambientLight.getLength() > 0) {
            Element ambientLightElement = (Element) ambientLight.item(0);
            String ambientColorStr = ambientLightElement.getAttribute("color");
            if (!ambientColorStr.isEmpty()) {
                return new AmbientLight(parseColor(ambientColorStr));
            } else {
                throw new IllegalArgumentException("Cannot parse ambient-light color is empty: " + ambientLight);
            }
        } else {
            return AmbientLight.NONE;
        }
    }



}