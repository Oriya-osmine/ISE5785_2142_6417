package geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Testing Polygons
 *
 * @author Dan
 */
class PolygonTests {
    /**
     * Delta value for accuracy when comparing the numbers of type 'double' in
     * assertEquals
     */
    private static final double DELTA = 0.000001;

    /**
     * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        assertDoesNotThrow(() -> new Polygon(new Point(0, 0, 1),
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(-1, 1, 1)),
                "Failed constructing a correct polygon");

        // TC02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0.5, 0.25, 0.5)), //
                "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0, 0.5, 0.5)),
                "Constructed a polygon with vertix on a side");

        // TC11: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                "Constructed a polygon with vertice on a side");

        // TC12: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                "Constructed a polygon with vertice on a side");

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Point[] pts =
                {new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1)};
        Polygon pol = new Polygon(pts);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = pol.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), DELTA, "Polygon's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1])), DELTA,
                    "Polygon's normal is not orthogonal to one of the edges");
    }

    /**
     * Test method for {@link geometries.Polygon#findIntersections(Ray)}
     */
    @Test
    void testFindIntersections() {
        Polygon polygon = new Polygon(
                new Point(0, 0, 0),
                new Point(2, 0, 0),
                new Point(3, 1, 0),
                new Point(1.5, 3, 0),
                new Point(0, 2, 0)
        );

        // ============ Equivalence Partitions Tests ==============

        // TC01: Intersection strictly inside the polygon
        Ray rayInside = new Ray(new Point(1, 1, 1), new Vector(0, 0, -1));
        assertEquals(List.of(new Point(1, 1, 0)), polygon.findIntersections(rayInside), "Ray does not intersects inside the polygon");

        // TC02: Intersection outside, against an edge
        Ray rayOutsideEdge = new Ray(new Point(1, -0.5, 1), new Vector(0, 0, -1));
        assertNull(polygon.findIntersections(rayOutsideEdge), "Ray intersects outside the polygon (against edge)");

        // TC03: Intersection outside, against a vertex
        Ray rayOutsideVertex = new Ray(new Point(2.1, 0.1, 1), new Vector(0.1, 0, -1));  // Fixed: Non-zero vector
        assertNull(polygon.findIntersections(rayOutsideVertex), "Ray intersects outside the polygon (against vertex)");

        // =============== Boundary Values Tests ==================

        // TC10: Intersection exactly on an edge
        Ray rayOnEdge = new Ray(new Point(1, 0, 1), new Vector(0, 0, -1));
        assertNull(polygon.findIntersections(rayOnEdge), "Ray intersects exactly on a polygon edge");

        // TC11: Intersection exactly on a vertex
        Ray rayOnVertex = new Ray(new Point(0, 0, 1), new Vector(0, 0, -1));
        assertNull(polygon.findIntersections(rayOnVertex),"Ray intersects exactly on a polygon vertex");

        // TC12: Intersection on the extension of an edge
        Ray rayOnEdgeExtension = new Ray(new Point(2.5, 0, 1), new Vector(0, 0, -1));
        assertNull(polygon.findIntersections(rayOnEdgeExtension),"Ray intersects on extension of a polygon edge");
    }
}
