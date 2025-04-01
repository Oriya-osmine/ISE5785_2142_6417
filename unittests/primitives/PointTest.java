package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for the Point class.
 */
class PointTest {
    private final Vector v1 = new Vector(-1,-2,-3);
    private final Point p1 = new Point(1, 2, 3);
    private final Point p2 = new Point(4, 5, 6);
    private final Point p3 = new Point(-3, -3, -3);
    private final Point p4 = new Point(3, 3, 3);
    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     * This test checks the subtraction of two points.
     * It includes equivalence partition tests and boundary value tests.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(p3,p1.subtract(p2));
        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1),
                "subtract() for point 0 does not throw an exception");
    }
    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}.
     * This test checks the addition of a vector to a point.
     * It includes equivalence partition tests.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(p4,p2.add(v1));


    }
    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
     * This test checks the squared distance between two points.
     * It includes equivalence partition tests.
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(27,p1.distanceSquared(p2));
        assertEquals(0,p1.distanceSquared(p1));


    }
    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     * This test checks the distance between two points.
     * It includes equivalence partition tests.
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(Math.sqrt(27),p1.distance(p2));


    }
}