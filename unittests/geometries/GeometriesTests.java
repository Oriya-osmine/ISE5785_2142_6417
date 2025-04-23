package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        // =============== Boundary Values Tests ==================

        // TC01: Empty geometries collection
        Geometries empty = new Geometries();
        assertNull(empty.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0))),
                "TC01: Expected null for empty geometry list");

        Sphere sphere = new Sphere(1, new Point(0, 0, 3));
        Plane plane = new Plane(new Point(0, 0, 5), new Vector(0, 0, 1));
        Triangle triangle = new Triangle(
                new Point(-1, -1, 3),
                new Point(1, -1, 3),
                new Point(0, 1, 3)
        );
        Geometries geometries = new Geometries(sphere, plane, triangle);

        // TC02: No shapes intersected
        Ray rayMissesAll = new Ray(new Point(0, 0, 0), new Vector(0, -1, 0));
        assertNull(geometries.findIntersections(rayMissesAll),
                "TC02: Expected null when ray misses all shapes");

        // TC03: One shape intersected (Sphere)
        Ray rayHitsSphere = new Ray(new Point(0,-5,2.5), new Vector(0, 1, 0));
        List<Point> intersections = geometries.findIntersections(rayHitsSphere);
        assertNotNull(intersections, "TC03: Expected intersections with one shape");
        assertEquals(2, intersections.size(), "TC03: Expected 2 points (Sphere)");

        // ============ Equivalence Partitions Tests ==============

        // TC04: Some but not all shapes intersected (Sphere + Triangle)
        Ray rayHitsSome = new Ray(new Point(0, 0, 4.5), new Vector(0, 0, -1));
        intersections = geometries.findIntersections(rayHitsSome);
        assertNotNull(intersections, "TC04: Expected intersections with some shapes");
        assertEquals(3, intersections.size(), "TC04: Expected 3 points (Sphere + Triangle)");

        // TC05: All shapes intersected
        Ray rayHitsAll = new Ray(new Point(0, 0, -1), new Vector(0, 0, 1));
        intersections = geometries.findIntersections(rayHitsAll);
        assertNotNull(intersections, "TC05: Expected intersections with all shapes");
        assertEquals(4, intersections.size(), "TC05: Expected 4 points (Sphere:2, Triangle:1, Plane:1)");
    }

}