package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Geometries class.
 */
class GeometriesTests {

    /**
     * Tests for  {@link geometries.Geometries#add(Intersectable...)}.
     */
    @Test
    void testAdd() {

    }

    /**
     * Tests for  {@link geometries.Geometries#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {
        Sphere sphere = new Sphere( new Point(0, 0, 3),1);
        Plane plane = new Plane(new Point(0, 0, 5), new Vector(0, 0, 1));
        Triangle triangle = new Triangle(
                new Point(-1, -1, 3),
                new Point(1, -1, 3),
                new Point(0, 1, 3)
        );
        Geometries geometries = new Geometries(sphere, plane, triangle);

        List<Point> intersections;

        // ============ Equivalence Partitions Tests ==============
        // TC01: Some but not all shapes intersected (Sphere + Triangle)
        Ray rayHitsSome = new Ray(new Point(0, 0, 4.5), new Vector(0, 0, -1));
        intersections = geometries.findIntersections(rayHitsSome);
        assertNotNull(intersections, "TC04: Expected intersections with some shapes");
        assertEquals(3, intersections.size(), "TC04: Expected 3 points (Sphere + Triangle)");


        // =============== Boundary Values Tests ==================

        // TC11: Empty geometries collection
        Geometries empty = new Geometries();
        assertNull(empty.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0))),
                "TC01: Expected null for empty geometry list");
        
        // TC12: No shapes intersected
        Ray rayMissesAll = new Ray(new Point(0, 0, 0), new Vector(0, -1, 0));
        assertNull(geometries.findIntersections(rayMissesAll),
                "TC02: Expected null when ray misses all shapes");

        // TC13: One shape intersected (Sphere)
        Ray rayHitsSphere = new Ray(new Point(0, -5, 2.5), new Vector(0, 1, 0));
        intersections = geometries.findIntersections(rayHitsSphere);
        assertNotNull(intersections, "TC03: Expected intersections with one shape");
        assertEquals(2, intersections.size(), "TC03: Expected 2 points (Sphere)");

        // TC14: All shapes intersected
        Ray rayHitsAll = new Ray(new Point(0, 0, -1), new Vector(0, 0, 1));
        intersections = geometries.findIntersections(rayHitsAll);
        assertNotNull(intersections, "TC05: Expected intersections with all shapes");
        assertEquals(4, intersections.size(), "TC05: Expected 4 points (Sphere:2, Triangle:1, Plane:1)");

        // =============== Find Intersections With Max Distance ===============

        // TC21: Ray intersects some shapes but max distance limits the results
        Ray rayWithLimit = new Ray(new Point(0, 0, -1), new Vector(0, 0, 1));
        intersections = geometries.findIntersections(rayWithLimit, 3.5);
        assertNotNull(intersections, "TC21: Expected intersections within max distance");
        assertEquals(1, intersections.size(), "TC21: Expected 1 points ");

        // TC22: Ray doesn't reach any shape due to max distance
        intersections = geometries.findIntersections(rayWithLimit, 1);
        assertNull(intersections, "TC22: Expected no intersections within max distance");

        // TC23: Ray reaches exactly to one intersection point
        intersections = geometries.findIntersections(rayWithLimit, 3);
        assertNotNull(intersections, "TC23: Expected intersection at exact distance");
        assertEquals(1, intersections.size(), "TC23: Expected 1 point at max distance");

        // TC24: Ray with distance that includes all intersections
        intersections = geometries.findIntersections(rayWithLimit, 10);
        assertNotNull(intersections, "TC24: Expected all possible intersections");
        assertEquals(4, intersections.size(), "TC24: Expected all 4 intersection points");
    }

}