package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Point class.
 */
class PointTests {
    /**
     * Used in multiple math and test operations
     */
    private final Point p123 = new Point(1, 2, 3);
    /**
     * Used in multiple math and test operations
     */
    private final Point p456 = new Point(4, 5, 6);

    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     * This test checks the subtraction of two points.
     * It includes equivalence partition tests and boundary value tests.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple subtraction with one negative one positive
        assertEquals(p123, new Point(2, 4, 6).subtract(p123));
    }

    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}.
     * This test checks the addition of a vector to a point.
     * It includes equivalence partition tests.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple addition with one negative one positive
        assertEquals(new Point(3, 3, 3), p456.add(new Vector(-1, -2, -3)));
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
     * This test checks the squared distance between two points.
     * It includes equivalence partition tests.
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple Distance Squared
        assertEquals(27, p123.distanceSquared(p456));
    }

    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     * This test checks the distance between two points.
     * It includes equivalence partition tests.
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple Distance
        assertEquals(Math.sqrt(27), p123.distance(p456));
    }
}