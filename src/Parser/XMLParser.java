package Parser;

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

import scene.Scene;
import primitives.Color;
import lighting.AmbientLight;
import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import geometries.Polygon;
import primitives.Point;
import primitives.Vector;

/**
 * Class to parse xml file into a scene
 */
public class XMLParser {
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

            // Parse background color
            String backgroundColorStr = sceneElement.getAttribute("background-color");
            if (!backgroundColorStr.isEmpty()) {
                scene.setBackground(parseColor(backgroundColorStr));
            } else {
                throw new IllegalArgumentException("Cannot parse background color is empty");
            }

            // Parse ambient light
            NodeList ambientLight = sceneElement.getElementsByTagName("ambient-light");
            if (ambientLight.getLength() > 0) {
                Element ambientLightElement = (Element) ambientLight.item(0);
                String ambientColorStr = ambientLightElement.getAttribute("color");
                if (!ambientColorStr.isEmpty()) {
                    scene.setAmbientLight(new AmbientLight(parseColor(ambientColorStr)));
                } else {
                    throw new IllegalArgumentException("Cannot parse ambient-light color is empty");
                }
            } else {
                throw new IllegalArgumentException("Cannot parse ambient-light is empty");
            }

            // Parse geometries
            NodeList geometriesList = sceneElement.getElementsByTagName("geometries");
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
                                String centerStr = geometryElement.getAttribute("center");
                                String radiusStr = geometryElement.getAttribute("radius");
                                if (!centerStr.isEmpty() && !radiusStr.isEmpty()) {
                                    Point center = parsePoint(centerStr);
                                    double radius = Double.parseDouble(radiusStr);
                                    geometries.add(new Sphere(center, radius));
                                }
                                break;
                            case "triangle":
                                String p0Str = geometryElement.getAttribute("p0");
                                String p1Str = geometryElement.getAttribute("p1");
                                String p2Str = geometryElement.getAttribute("p2");
                                if (!p0Str.isEmpty() && !p1Str.isEmpty() && !p2Str.isEmpty()) {
                                    Point p0 = parsePoint(p0Str);
                                    Point p1 = parsePoint(p1Str);
                                    Point p2 = parsePoint(p2Str);
                                    geometries.add(new Triangle(p0, p1, p2));
                                }
                                break;
                            case "plane":
                                String q0Str = geometryElement.getAttribute("p0");
                                String normalStr = geometryElement.getAttribute("normal");
                                if (!q0Str.isEmpty()) {
                                    Point p0 = parsePoint(q0Str);
                                    if (!normalStr.isEmpty()) {
                                        Vector normal = parseVector(normalStr);
                                        geometries.add(new Plane(p0, normal));
                                    } else {
                                        String p1StrPlane = geometryElement.getAttribute("p1");
                                        String p2StrPlane = geometryElement.getAttribute("p2");
                                        if (!p1StrPlane.isEmpty() && !p2StrPlane.isEmpty()) {
                                            Point p1 = parsePoint(p1StrPlane);
                                            Point p2 = parsePoint(p2StrPlane);
                                            geometries.add(new Plane(p0, p1, p2));
                                        }
                                    }
                                }
                                break;
                            case "polygon":
                                List<Point> polygonVerticesList = new ArrayList<>();
                                String pStr;
                                Point p;
                                for (int j = 0; !(pStr = geometryElement.getAttribute("p" + j)).isEmpty(); j++) {
                                    p = parsePoint(pStr);
                                    polygonVerticesList.add(p);
                                }
                                if (!polygonVerticesList.isEmpty()) {
                                    geometries.add(new Polygon(polygonVerticesList.toArray(new Point[0])));
                                }
                                break;
                        }
                    }
                }
                scene.setGeometries(geometries);
            }

        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Parses a point from xml
     *
     * @param colorStr the xml attribute to Parse to a point
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
        throw new IllegalArgumentException("Cannot parse Color:" + colorStr);
    }

    /**
     * Parses a point from xml
     *
     * @param pointStr the xml attribute to Parse to a point
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
        throw new IllegalArgumentException("Cannot parse point:" + pointStr);
    }

    /**
     * Parses a vector from xml
     *
     * @param vectorStr the xml attribute to Parse to a vector
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
        throw new IllegalArgumentException("Cannot parse vector:" + vectorStr);
    }
}