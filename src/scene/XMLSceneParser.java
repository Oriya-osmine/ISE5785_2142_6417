package scene;

import geometries.Plane;
import lighting.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import primitives.*;
import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import geometries.Polygon;

/**
 * Class to parse xml file into a scene
 */
public class XMLSceneParser {
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

    /**
     * Parses all geometries
     *
     * @param geometriesList the geometries Node to parse
     * @return the scene geometries
     */
    private static Geometries parseGeometries(NodeList geometriesList) {
        Geometries geometries = new Geometries();
        if (geometriesList.getLength() > 0) {
            Element geometriesElement = (Element) geometriesList.item(0);
            NodeList geometryNodes = geometriesElement.getChildNodes();

            for (int i = 0; i < geometryNodes.getLength(); i++) {
                if (geometryNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element geometryElement = (Element) geometryNodes.item(i);
                    String geometryType = geometryElement.getTagName();

                    switch (geometryType) {
                        case "sphere":
                            geometries.add(parseSphere(geometryElement));
                            break;
                        case "triangle":
                            geometries.add(parseTriangle(geometryElement));
                            break;
                        case "plane":
                            geometries.add(parsePlane(geometryElement));
                            break;
                        case "polygon":
                            geometries.add(parsePolygon(geometryElement));
                            break;
                        case "cylinder":
                        case "tube":
                            throw new IllegalArgumentException("Not supported");
                    }
                }
            }
            return geometries;
        }
        return null;
    }

    /**
     * Parses all lights
     *
     * @param lightsList the lights Node to parse
     * @return the scene lights
     */
    private static List<LightSource> parseLights(NodeList lightsList) {
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
     * Parses a material
     *
     * @param materialTags the xml tags to Parse to a material
     * @return the material
     */
    private static Material parseMaterial(Element materialTags) {
        String kAStr = materialTags.getAttribute("kA");
        String kSStr = materialTags.getAttribute("kS");
        String kDStr = materialTags.getAttribute("kD");
        String nSHStr = materialTags.getAttribute("nSH");
        Material material = new Material();
        if (!kAStr.isEmpty())
            if (kAStr.contains(" ")) {
                material.setKA(parseDouble3(kAStr));
            } else {
                material.setKA(Double.parseDouble(kAStr));
            }
        if (!kSStr.isEmpty())
            if (kSStr.contains(" ")) {
                material.setKS(parseDouble3(kSStr));
            } else {
                material.setKS(Double.parseDouble(kSStr));
            }
        if (!kDStr.isEmpty())
            if (kDStr.contains(" ")) {
                material.setKD(parseDouble3(kDStr));
            } else {
                material.setKD(Double.parseDouble(kDStr));
            }
        if (!nSHStr.isEmpty())
            material.setShininess(Integer.parseInt(nSHStr));
        return material;
    }

    /**
     * Parses a point
     *
     * @param colorStr the xml attribute in string to Parse to a point
     * @return the point
     */
    private static Color parseColor(String colorStr) {
        String[] rgb = colorStr.split(" ");
        if (rgb.length == 3) {
            double r = Double.parseDouble(rgb[0]);
            double g = Double.parseDouble(rgb[1]);
            double b = Double.parseDouble(rgb[2]);
            return new Color(r, g, b);
        }
        throw new IllegalArgumentException("Cannot parse Color: " + colorStr);
    }

    /**
     * Parses a point
     *
     * @param pointStr the xml attribute in string to Parse to a point
     * @return the point
     */
    private static Point parsePoint(String pointStr) {
        String[] coords = pointStr.split(" ");
        if (coords.length == 3) {
            double x = Double.parseDouble(coords[0]);
            double y = Double.parseDouble(coords[1]);
            double z = Double.parseDouble(coords[2]);
            return new Point(x, y, z);
        }
        throw new IllegalArgumentException("Cannot parse point: " + pointStr);
    }

    /**
     * Parses a vector
     *
     * @param vectorStr the xml attribute in string to Parse to a vector
     * @return the vector
     */
    private static Vector parseVector(String vectorStr) {
        String[] coords = vectorStr.split(" ");
        if (coords.length == 3) {
            double x = Double.parseDouble(coords[0]);
            double y = Double.parseDouble(coords[1]);
            double z = Double.parseDouble(coords[2]);
            return new Vector(x, y, z);
        }
        throw new IllegalArgumentException("Cannot parse vector: " + vectorStr);
    }

    /**
     * Parses a Double3
     *
     * @param double3Str the xml attribute in string to Parse to a Double3
     * @return the double3
     */
    private static Double3 parseDouble3(String double3Str) {
        String[] coords = double3Str.split(" ");
        if (coords.length == 3) {
            double x = Double.parseDouble(coords[0]);
            double y = Double.parseDouble(coords[1]);
            double z = Double.parseDouble(coords[2]);
            return new Double3(x, y, z);
        }
        throw new IllegalArgumentException("Cannot parse Double3: " + double3Str);
    }

    /**
     * Parses an element to a Plane
     *
     * @param geometryElement the xml element to parse
     * @return the plane
     */
    private static Plane parsePlane(Element geometryElement) {
        String q0Str = geometryElement.getAttribute("p0");
        String normalStr = geometryElement.getAttribute("normal");
        Plane plane;
        if (!q0Str.isEmpty()) {
            Point p0 = parsePoint(q0Str);
            if (!normalStr.isEmpty()) {
                Vector normal = parseVector(normalStr);
                plane = new Plane(p0, normal);
            } else {
                String p1StrPlane = geometryElement.getAttribute("p1");
                String p2StrPlane = geometryElement.getAttribute("p2");
                if (!p1StrPlane.isEmpty() && !p2StrPlane.isEmpty()) {
                    Point p1 = parsePoint(p1StrPlane);
                    Point p2 = parsePoint(p2StrPlane);
                    plane = new Plane(p0, p1, p2);
                } else {
                    throw new IllegalArgumentException("Cannot find all points for plane or normal is empty: " + geometryElement);
                }
            }
            plane.setMaterial(parseMaterial(geometryElement));
            String emissionStr = geometryElement.getAttribute("emission");
            if (!emissionStr.isEmpty())
                plane.setEmission(parseColor(emissionStr));
            return plane;
        } else {
            throw new IllegalArgumentException("Cannot find p0 for plane: " + geometryElement);
        }
    }

    /**
     * Parses an element to a Sphere
     *
     * @param geometryElement the xml element to parse
     * @return the sphere
     */
    private static Sphere parseSphere(Element geometryElement) {
        String centerStr = geometryElement.getAttribute("center");
        String radiusStr = geometryElement.getAttribute("radius");
        if (!centerStr.isEmpty() && !radiusStr.isEmpty()) {
            Point center = parsePoint(centerStr);
            double radius = Double.parseDouble(radiusStr);
            Sphere sphere = new Sphere(center, radius);
            sphere.setMaterial(parseMaterial(geometryElement));
            String emissionStr = geometryElement.getAttribute("emission");
            if (!emissionStr.isEmpty())
                sphere.setEmission(parseColor(emissionStr));
            return sphere;
        } else {
            throw new IllegalArgumentException("Cannot find center or radius for sphere: " + geometryElement);
        }
    }

    /**
     * Parses an element to a Polygon
     *
     * @param geometryElement the xml element to parse
     * @return the polygon
     */
    private static Polygon parsePolygon(Element geometryElement) {
        List<Point> polygonVerticesList = new ArrayList<>();
        String pStr;
        Point p;
        for (int j = 0; !(pStr = geometryElement.getAttribute("p" + j)).isEmpty(); j++) {
            p = parsePoint(pStr);
            polygonVerticesList.add(p);
        }
        if (!polygonVerticesList.isEmpty()) {
            Polygon polygon = new Polygon(polygonVerticesList.toArray(new Point[0]));
            polygon.setMaterial(parseMaterial(geometryElement));
            String emissionStr = geometryElement.getAttribute("emission");
            if (!emissionStr.isEmpty())
                polygon.setEmission(parseColor(emissionStr));
            return polygon;
        }
        throw new IllegalArgumentException("Cannot construct an empty polygon: " + geometryElement);
    }

    /**
     * Parses an element to a Triangle
     *
     * @param geometryElement the xml element to parse
     * @return the Triangle
     */
    private static Triangle parseTriangle(Element geometryElement) {
        String p0Str = geometryElement.getAttribute("p0");
        String p1Str = geometryElement.getAttribute("p1");
        String p2Str = geometryElement.getAttribute("p2");
        if (!p0Str.isEmpty() && !p1Str.isEmpty() && !p2Str.isEmpty()) {
            Point p0 = parsePoint(p0Str);
            Point p1 = parsePoint(p1Str);
            Point p2 = parsePoint(p2Str);
            Triangle triangle = new Triangle(p0, p1, p2);
            triangle.setMaterial(parseMaterial(geometryElement));
            String emissionStr = geometryElement.getAttribute("emission");
            if (!emissionStr.isEmpty())
                triangle.setEmission(parseColor(emissionStr));
            return triangle;
        } else {
            throw new IllegalArgumentException("Cannot find all points for triangle: " + geometryElement);
        }
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