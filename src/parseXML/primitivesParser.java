package parseXML;

import org.w3c.dom.Element;
import primitives.*;

public class primitivesParser {
    /**
     * Parses a material
     *
     * @param materialTags the xml tags to Parse to a material
     * @return the material
     */
    public static Material parseMaterial(Element materialTags) {
        String kAStr = materialTags.getAttribute("kA");
        String kSStr = materialTags.getAttribute("kS");
        String kDStr = materialTags.getAttribute("kD");
        String kTStr = materialTags.getAttribute("kT");
        String kRStr = materialTags.getAttribute("kR");
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
        if (!kTStr.isEmpty())
            if (kTStr.contains(" ")) {
                material.setKT(parseDouble3(kTStr));
            } else {
                material.setKT(Double.parseDouble(kTStr));
            }
        if (!kRStr.isEmpty())
            if (kRStr.contains(" ")) {
                material.setKR(parseDouble3(kRStr));
            } else {
                material.setKR(Double.parseDouble(kRStr));
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
    public static Color parseColor(String colorStr) {
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
    public static Point parsePoint(String pointStr) {
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
    public static Vector parseVector(String vectorStr) {
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
    public static Double3 parseDouble3(String double3Str) {
        String[] coords = double3Str.split(" ");
        if (coords.length == 3) {
            double x = Double.parseDouble(coords[0]);
            double y = Double.parseDouble(coords[1]);
            double z = Double.parseDouble(coords[2]);
            return new Double3(x, y, z);
        }
        throw new IllegalArgumentException("Cannot parse Double3: " + double3Str);
    }
}
