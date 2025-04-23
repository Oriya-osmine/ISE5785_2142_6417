package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Plane class.
 */
class PlaneTests {
    /**
     * make the precision after . more accurate
     */
    private static final double DELTA = 0.000001;
    /**
     * Used in multiple math and test operations
     */
    Point p1 = new Point(1, 1, 1);
    /**
     * Used in multiple math and test operations
     */
    Point p2 = new Point(2, 2, 2);
    /**
     * Used in multiple math and test operations
     */
    Point p1B = new Point(1, 1, 1);
    /**
     * Used in multiple math and test operations
     */
    Point p2B = new Point(2, 2, 2);
    /**
     * Used in multiple math and test operations
     */
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

        //TC01 Normal orthogonality
        assertEquals(0, normal.dotProduct(v1), DELTA);
        //TC02 Normal orthogonality
        assertEquals(0, normal.dotProduct(v2), DELTA);
        //TC03 Normal unit vector
        assertEquals(1, normal.length(), DELTA);

        // =============== Boundary Values Tests ==================
        //TC11 2 Equal points (1, 2)
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p1B, p2),
                "Constructed a plane with 2 equal points");
        //TC12 2 Equal points (1, 3)
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p2, p1B),
                "Constructed a plane with 2 equal points");
        //TC13 2 Equal points (2, 3)
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p2, p2B),
                "Constructed a plane with 2 equal points");
        //TC14 3 Equal points
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p1, p1),
                "Constructed a plane with 3 equal points");
        //TC15 3 Collinear points
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p2, p3),
                "Constructed a plane with 3 points that are not in the same plane");
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(Ray)}.
     * This test checks the findIntersections method of the Plane class.
     * It includes equivalence partition tests and boundary value tests.
     */
    @Test
    void testFindIntersections() {
        // Plane: z = 1
        Plane plane = new Plane(new Point(0, 0, 1), new Vector(0, 0, 1));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects the plane and not orthogonal nor parallel
        Ray ray1 = new Ray(new Point(0, 0, 0), new Vector(0, 0.5, 1));
        List<Point> result1 = plane.findIntersections(ray1);
        assertNotNull(result1, "TC01: Expected intersection point");
        assertEquals(1, result1.size(), "TC01: Should be one intersection point");
        assertEquals(new Point(0, 0.5, 1), result1.get(0), "TC01: Wrong intersection point");

        // TC02: Ray does not intersect the plane and not orthogonal nor parallel
        Ray ray2 = new Ray(new Point(0, 0, 2), new Vector(0, 0.5, 1));
        assertNull(plane.findIntersections(ray2), "TC02: Expected no intersection");

        // =============== Boundary Value Tests ==================

        // TC10: Ray is parallel to the plane and outside
        Ray ray3a = new Ray(new Point(0, 0, 2), new Vector(1, 0, 0));
        assertNull(plane.findIntersections(ray3a), "TC010: Expected no intersection (parallel & outside)");

        // TC11: Ray is parallel to the plane and inside
        Ray ray3b = new Ray(new Point(1, 0, 1), new Vector(1, 0, 0));
        assertNull(plane.findIntersections(ray3b), "TC11: Expected no intersection (ray in plane)");

        // TC12: Ray is orthogonal to the plane and starts before
        Ray ray4 = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        List<Point> result4 = plane.findIntersections(ray4);
        assertNotNull(result4, "TC04: Expected intersection point");
        assertEquals(1, result4.size(), "TC12: Should be one intersection point");
        assertEquals(new Point(0, 0, 1), result4.get(0), "TC04: Wrong intersection point");

        // TC13: Ray is orthogonal to the plane and starts inside
        Ray ray5 = new Ray(new Point(0, 0, 1), new Vector(0, 0, 1));
        assertNull(plane.findIntersections(ray5), "TC13: Expected no intersection (starts in plane)");

        // TC14: Ray is orthogonal to the plane and starts after
        Ray ray6 = new Ray(new Point(0, 0, 2), new Vector(0, 0, 1));
        assertNull(plane.findIntersections(ray6), "TC14: Expected no intersection (starts after plane)");

        // TC15: Ray is neither orthogonal nor parallel, starts in the plane (no intersection)
        Ray ray7 = new Ray(new Point(0, 0.5, 1), new Vector(1, 1, 1));
        assertNull(plane.findIntersections(ray7), "TC15: No intersection - ray starts in the plane");

        // TC16: Ray is neither orthogonal nor parallel and starts at reference point on the plane
        Ray ray8 = new Ray(new Point(0, 0, 1), new Vector(1, 1, 1));
        assertNull(plane.findIntersections(ray8), "TC16: Expected no intersection (ray starts on plane)");
    }

}