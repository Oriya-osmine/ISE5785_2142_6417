package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TriangleTests {

    /**
     * Test method for {@link geometries.Triangle#findIntersections(Ray)}.
     * This test checks the point of intersection between a ray and a triangle
     */
    @Test
    void testFindIntersections() {
        
        // TODO: make sure the tests are correct
        Triangle triangle = new Triangle(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));
        Ray ray;
        java.util.List<Point> result;

        // ============ Equivalence Partitions Tests ==============
        // TC01 Ray intersects plane inside triangle (not on edge/vertex)
        ray = new Ray(new Point(0.2, 0.2, -1), new Vector(0, 0, 1));
        result = triangle.findIntersections(ray);
        assertEquals(1, result.size(), "TC01: Should intersect strictly inside");
        assertEquals(new Point(0.2, 0.2, 0.6), result.getFirst(), "TC01: Intersection point is incorrect");

        // TC02 Ray intersects plane against a triangle side (no intersection)
        ray = new Ray(new Point(0.7, 0.7, -1), new Vector(0, 0, 1));
        result = triangle.findIntersections(ray);
        assertNull(result, "TC02: Should not intersect against a side");

        // TC03 Ray intersects plane against a triangle point (no intersection)
        ray = new Ray(new Point(1.5, 0, -1), new Vector(0, 0, 1));
        result = triangle.findIntersections(ray);
        assertNull(result, "TC03: Should not intersect against a point");

        // =============== Boundary Values Tests ==================
        // TC11 Ray intersects plane on continuation of an edge (no intersection)
        ray = new Ray(new Point(-0.5, 0.5, -1), new Vector(0, 0, 1));
        result = triangle.findIntersections(ray);
        assertNull(result, "TC11: Should not intersect on continuation of an edge");

        // TC12 Ray starts on the plane, intersects an edge (no intersection)
        ray = new Ray(new Point(0, 0.5, 0), new Vector(0, 0, 1));
        result = triangle.findIntersections(ray);
        assertNull(result, "TC12: Should not intersect when starting on plane");

        // TC13 Ray starts on the plane, intersects a vertex (no intersection)
        ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        result = triangle.findIntersections(ray);
        assertNull(result, "TC13: Should not intersect when starting on plane");
    }

}