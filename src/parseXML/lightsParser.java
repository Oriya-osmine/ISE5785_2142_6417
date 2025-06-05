package parseXML;

import lighting.DirectionalLight;
import lighting.LightSource;
import lighting.PointLight;
import lighting.SpotLight;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.LinkedList;
import java.util.List;

import static parseXML.primitivesParser.*;

/**
 * Parses lights from xml
 */
public class lightsParser {
    /**
     * Parses all lights
     *
     * @param lightsList the lights Node to parse
     * @return the scene lights
     */
    public static List<LightSource> parseLights(NodeList lightsList) {
        if (lightsList.getLength() > 0) {
            Element lightsElement = (Element) lightsList.item(0);
            NodeList lightNodes = lightsElement.getChildNodes();
            List<LightSource> lights = new LinkedList<>();

            for (int i = 0; i < lightNodes.getLength(); i++) {
                if (lightNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element lightElement = (Element) lightNodes.item(i);
                    String geometryType = lightElement.getTagName();

                    switch (geometryType) {
                        case "directional-light":
                            lights.add(parseDirectionalLight(lightElement));
                            break;
                        case "point-light":
                            lights.add(parsePointLight(lightElement));
                            break;
                        case "spot-light":
                            lights.add(parseSpotLight(lightElement));
                            break;
                    }
                }
            }
            return lights;
        }
        return null;
    }

    /**
     * Parses an element to a DirectionalLight
     *
     * @param lightElement the xml element to parse
     * @return the DirectionalLight
     */
    private static DirectionalLight parseDirectionalLight(Element lightElement) {
        String intensityStr = lightElement.getAttribute("intensity");
        String directionStr = lightElement.getAttribute("direction");
        if (!intensityStr.isEmpty() && !directionStr.isEmpty()) {
            return new DirectionalLight(parseColor(intensityStr), parseVector(directionStr));
        } else {
            throw new IllegalArgumentException("Cannot find all fields for DirectionalLight: " + lightElement);
        }
    }

    /**
     * Parses an element to basic PointLight fields
     *
     * @param pointLight   the light point to apply it to
     * @param lightElement the xml element to parse
     */
    private static void parseBasicPointLight(Element lightElement, PointLight pointLight) {
        String kCStr = lightElement.getAttribute("kC");
        String kQStr = lightElement.getAttribute("kQ");
        String kLStr = lightElement.getAttribute("kL");
        if (!kCStr.isEmpty())
            pointLight.setKc(Double.parseDouble(kCStr));
        if (!kQStr.isEmpty())
            pointLight.setKq(Double.parseDouble(kQStr));
        if (!kLStr.isEmpty())
            pointLight.setKl(Double.parseDouble(kLStr));
    }

    /**
     * Parses an element to a PointLight
     *
     * @param lightElement the xml element to parse
     * @return the PointLight
     */
    private static PointLight parsePointLight(Element lightElement) {
        String intensityStr = lightElement.getAttribute("intensity");
        String positionStr = lightElement.getAttribute("position");
        if (!intensityStr.isEmpty() && !positionStr.isEmpty()) {
            PointLight pointLight = new PointLight(parseColor(intensityStr), parsePoint(positionStr));
            parseBasicPointLight(lightElement, pointLight);
            return pointLight;
        } else {
            throw new IllegalArgumentException("Cannot find all fields for PointLight: " + lightElement);
        }
    }

    /**
     * Parses an element to a SpotLight
     *
     * @param lightElement the xml element to parse
     * @return the SpotLight
     */
    private static SpotLight parseSpotLight(Element lightElement) {
        String intensityStr = lightElement.getAttribute("intensity");
        String positionStr = lightElement.getAttribute("position");
        String directionStr = lightElement.getAttribute("direction");

        if (!intensityStr.isEmpty() && !directionStr.isEmpty()) {
            SpotLight spotLight = new SpotLight(parseColor(intensityStr), parsePoint(positionStr), parseVector(directionStr));
            parseBasicPointLight(lightElement, spotLight);
            String narrowBeamStr = lightElement.getAttribute("narrow-beam");
            if (!narrowBeamStr.isEmpty())
                spotLight.setNarrowBeam(Double.parseDouble(narrowBeamStr));
            return spotLight;
        } else {
            throw new IllegalArgumentException("Cannot find all fields for PointLight: " + lightElement);
        }
    }
}
