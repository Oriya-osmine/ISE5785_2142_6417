package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import primitives.Point;
import primitives.Vector;

/**
 * Unit tests for the Sphere class.
 */
class SphereTests {
    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     * This test checks the normal vector to the sphere at a given point.
     * It includes equivalence partition tests.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere sphere = new Sphere(1, new Point(0, 0, 0));
        assertEquals(new Vector(1, 0, 0), sphere.getNormal(new Point(1, 0, 0)));
    }
}