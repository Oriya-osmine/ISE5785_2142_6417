package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for the Plane class.
 */
class PlaneTest {
    private static final double DELTA = 0.000001;

    Point p1 = new Point(1, 1, 1);
    Point p2 = new Point(2, 2, 2);
    Point p1B = new Point(1, 1, 1);
    Point p2B = new Point(2, 2, 2);
    Point p3 = new Point(3, 3, 3);
    /**
     * Test method for {@link geometries.Plane#Plane(primitives.Point, primitives.Point, primitives.Point)}.
     * This test checks the constructor of the Plane class.
     * It includes equivalence partition tests and boundary value tests.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        Plane plane = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        Vector normal = plane.getNormal(new Point(0, 0, 1));

        // Ensure the normal is orthogonal to the vectors between the points
        Vector v1 = new Point(1, 0, 0).subtract(new Point(0, 0, 1));
        Vector v2 = new Point(0, 1, 0).subtract(new Point(0, 0, 1));
        assertEquals(0, normal.dotProduct(v1), DELTA);
        assertEquals(0, normal.dotProduct(v2), DELTA);

        // Ensure the normal is a unit vector
        assertEquals(1, normal.length(), DELTA);

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