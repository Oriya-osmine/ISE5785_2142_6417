package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    private static final double DELTA = 0.000001;

    Point p1 = new Point(1, 1, 1);
    Point p2 = new Point(2, 2, 2);
    Point p1B = new Point(1, 1, 1);
    Point p2B = new Point(2, 2, 2);
    Point p3 = new Point(3, 3, 3);

    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that the normal is orthogonal to at least two different vectors and has a length of 1
        Plane plane = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        Vector normal = plane.getNormal(new Point(0, 0, 1));

        // Ensure the normal is orthogonal to the vectors between the points
        Vector v1 = new Point(1, 0, 0).subtract(new Point(0, 0, 1));
        Vector v2 = new Point(0, 1, 0).subtract(new Point(0, 0, 1));
        assertEquals(0, normal.dotProduct(v1), DELTA, "Normal is not orthogonal to v1");
        assertEquals(0, normal.dotProduct(v2), DELTA, "Normal is not orthogonal to v2");

        // Ensure the normal is a unit vector
        assertEquals(1, normal.length(), DELTA, "Normal is not a unit vector");

        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p1B, p2),
                "Constructed a plane with 2 equal points");

        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p2, p1B),
                "Constructed a plane with 2 equal points");

        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p2, p2B),
                "Constructed a plane with 2 equal points");

        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p1, p1),
                "Constructed a plane with 3 equal points");

        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p2, p3),
                "Constructed a plane with 3 points that are not in the same plane");
    }


}