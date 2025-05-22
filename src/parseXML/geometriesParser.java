package parseXML;

import geometries.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import primitives.Point;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static parseXML.primitivesParser.*;

public class geometriesParser {

    /**
     * Parses all geometries
     *
     * @param geometriesList the geometries Node to parse
     * @return the scene geometries
     */
    public static Geometries parseGeometries(NodeList geometriesList) {
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
}
