package scene;

import geometries.Plane;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import primitives.*;
import lighting.AmbientLight;
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

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static Color parseBackground(String background) {
        if (!background.isEmpty()) {
            return parseColor(background);
        } else {
            throw new IllegalArgumentException("Cannot parse background it is empty: " + background);
        }
    }

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
            throw new IllegalArgumentException("Cannot parse ambient-light is empty: " + ambientLight);
        }
    }

    private static Geometries parseGeometries(NodeList geometriesList) {
        if (geometriesList.getLength() > 0) {
            Element geometriesElement = (Element) geometriesList.item(0);
            NodeList geometryNodes = geometriesElement.getChildNodes();
            Geometries geometries = new Geometries();

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
        } else {
            throw new IllegalArgumentException("no geometries found but the node was defined");
        }
    }

    /**
     * Parses a material
     *
     * @param materialStr the xml attribute in string to Parse to a material
     * @return the material
     */
    private static Material parseMaterial(String materialStr) {
        String[] material = materialStr.split(" ");
        if (material.length == 1) {
            return new Material().setKA(Double.parseDouble(material[0]));
        } else if (material.length == 3) {
            double x = Double.parseDouble(material[0]);
            double y = Double.parseDouble(material[1]);
            double z = Double.parseDouble(material[2]);
            return new Material().setKA(new Double3(x, y, z));
        }
        throw new IllegalArgumentException("Cannot parse vector: " + materialStr);
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
            String materialStr = geometryElement.getAttribute("material");
            if (!materialStr.isEmpty())
                plane.setMaterial(parseMaterial(materialStr));
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
            String materialStr = geometryElement.getAttribute("material");
            if (!materialStr.isEmpty())
                sphere.setMaterial(parseMaterial(materialStr));
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
            String materialStr = geometryElement.getAttribute("material");
            if (!materialStr.isEmpty())
                polygon.setMaterial(parseMaterial(materialStr));
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
            String materialStr = geometryElement.getAttribute("material");
            if (!materialStr.isEmpty())
                triangle.setMaterial(parseMaterial(materialStr));
            String emissionStr = geometryElement.getAttribute("emission");
            if (!emissionStr.isEmpty())
                triangle.setEmission(parseColor(emissionStr));
            return triangle;
        } else {
            throw new IllegalArgumentException("Cannot find all points for triangle: " + geometryElement);
        }
    }
}