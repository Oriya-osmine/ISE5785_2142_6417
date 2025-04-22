package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * Unit tests for the Sphere class.
 */
class SphereTests {
    final Sphere sphere10 =new Sphere(1.0, new Point(0, 0, 0)); // Sphere centered at origin with radius 1
    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     * This test checks the normal vector to the sphere10 at a given point.
     * It includes equivalence partition tests.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01 get normal vector
        Sphere sphere10 = new Sphere(1, new Point(0, 0, 0));
        assertEquals(new Vector(1, 0, 0), sphere10.getNormal(new Point(1, 0, 0)));
    }
    /**
     * Test method for {@link geometries.Sphere#findIntersections(Ray)}.
     * This test checks the Intersections between a ray and a sphere.
     * It includes equivalence partition (EP) and boundary value (BV) tests.
     */
    @Test
    void testFindIntersections() {
        // TODO: ensure test are correct

        // ============ Equivalence Partition Tests ============

        // TC01: Ray is outside the sphere10 and does not intersect
        assertNull(sphere10.findIntersections(new Ray(new Point(0, 0, 3), new Vector(0, 1, 0))),
                "Ray outside sphere10 should not intersect");

        // TC02: Ray starts before and goes through the sphere10 (2 points)
        List<Point> result = sphere10.findIntersections(new Ray(new Point(0, 0, 3), new Vector(0, 0, -2)));
        assertNotNull(result, "Ray should intersect the sphere10");
        assertEquals(new Point(0,0,-1), result.get(0), "Ray should intersect in  z=-1");
        assertEquals(new Point(0,0,1), result.get(1), "Ray should intersect in z=1");

        // TC03: Ray starts inside the sphere10 and exits (1 point)
        result = sphere10.findIntersections(new Ray(new Point(0, 0, 0.5), new Vector(0, 0, 2)));
        assertNotNull(result, "Ray from inside sphere10 should intersect");
        assertEquals(new Point(0,0,1), result.get(0), "Ray should intersect in z=1 ");

        // TC04: Ray starts after the sphere10 and goes away (no intersection)
        assertNull(sphere10.findIntersections(new Ray(new Point(0, 0, 1), new Vector(0, 0, 1))),
                "Ray after the sphere10 and going away should not intersect");

        // ============ Boundary Value Tests ============

        // Group 1: Ray is on the line intersecting the sphere10 but not through the center

        // TC05: Ray starts inside and intersects once
        result = sphere10.findIntersections(new Ray(new Point(1, 0, 0), new Vector(-1, 0, 0)));
        assertNotNull(result, "Ray from inside should intersect once");
        assertEquals(new Point(-1,0,0), result.get(0));

        // TC06: Ray starts after the sphere10 on the same line
        assertNull(sphere10.findIntersections(new Ray(new Point(1, 0, 0), new Vector(1, 0, 0))),
                "Ray after the sphere10 should not intersect");

        // Group 2: Ray goes through the center

        // TC07: Ray starts before and goes through center (2 points)
        result = sphere10.findIntersections(new Ray(new Point(2, 0, 0), new Vector(-1, 0, 0)));
        assertNotNull(result, "Ray through center should intersect in 2 points");
        assertEquals(new Point(-1,0,0), result.get(0));
        assertEquals(new Point(1,0,0), result.get(1));

        // TC08: Ray starts at sphere10 and goes through center (1 point)
        result = sphere10.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 0, 0)));
        assertNotNull(result, "Ray from surface toward center should intersect once");
        assertEquals(new Point(1,0,0), result.get(0));

        // TC09: Ray starts at center (1 point)
        result = sphere10.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)));
        assertNotNull(result, "Ray from center should intersect once");
        assertEquals(new Point(1,0,0), result.get(0));

        // TC10: Ray starts at surface and goes away from center (no intersection)
        assertNull(sphere10.findIntersections(new Ray(new Point(1, 0, 0), new Vector(1, 0, 0))),
                "Ray from surface going outward should not intersect");

        // TC11: Ray starts after the sphere10 on same line (no intersection)
        assertNull(sphere10.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))),
                "Ray after sphere10 going away should not intersect");

        // TC12: Ray starts in the sphere10 on same line (1 intersection)
        result = sphere10.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0)));
        assertNotNull(result, "Ray from center should intersect once");
        assertEquals(new Point(1,0,0), result.get(0));

        // Group 3: Ray is tangent to the sphere10

        // TC13: Ray is tangent before touching point
        assertNull(sphere10.findIntersections(new Ray(new Point(-1, 1, 0), new Vector(1, 0, 0))),
                "Ray tangent before sphere10 should not intersect");

        // TC14: Ray is tangent at touching point
        assertNull(sphere10.findIntersections(new Ray(new Point(0, 1, 0), new Vector(1, 0, 0))),
                "Ray exactly tangent should not intersect");

        // TC15: Ray is tangent after touching point
        assertNull(sphere10.findIntersections(new Ray(new Point(1, 1, 0), new Vector(1, 0, 0))),
                "Ray after tangent point should not intersect");

        // Group 4: Special project-discovered cases

        // TC16: Ray orthogonal to radius, starts at distance > radius
        assertNull(sphere10.findIntersections(new Ray(new Point(0, 2, 0), new Vector(1, 0, 0))),
                "Orthogonal ray from beyond radius should not intersect");

        // TC17: Ray orthogonal to radius, starts at distance = radius (tangent case)
        assertNull(sphere10.findIntersections(new Ray(new Point(0, 1, 0), new Vector(1, 0, 0))),
                "Orthogonal ray tangent to sphere10 should not intersect");

        // TC18: Ray orthogonal to radius, starts at distance < radius
        result = sphere10.findIntersections(new Ray(new Point(0, 0.5, 0), new Vector(1, 0, 0)));
        assertNotNull(result, "Orthogonal ray inside sphere10 should intersect");
        assertEquals(1, result.size());
    }


}