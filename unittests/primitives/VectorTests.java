package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the Vector class.
 */
class VectorTests {
    /**
     * Used in multiple math and test operations
     */
    private final Vector v1 = new Vector(1, 1, 1);
    /**
     * Used in multiple math and test operations
     */
    private final Vector v123 = new Vector(1, 2, 3);
    /**
     * Used in multiple math and test operations
     */
    private final Vector v11n3 = new Vector(1, 1, -3);
    /**
     * Used in multiple math and test operations
     */
    private final Vector v246 = new Vector(2, 4, 6);

    /**
     * Test method for {@link primitives.Vector#Vector(double, double, double)}.
     * Test method for {@link primitives.Vector#Vector(Double3)}.
     * This test checks the constructor of the Vector class.
     * It includes equivalence partition tests and boundary value tests.
     */
    @Test
    void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Vector(1, 2, 3), v123);
        assertEquals(new Vector(new Double3(1, 2, 3)), v123);
        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0),
                "scale() for vector 0 does not throw an exception");
        assertThrows(IllegalArgumentException.class, () -> new Vector(new Double3(0, 0, 0)),
                "scale() for vector 0 does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#scale(Double)}.
     * This test checks the scaling of a vector.
     * It includes equivalence partition tests and boundary value tests.
     */
    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(v246, v123.scale(2.0));
        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> v123.scale(0.0),
                "scale() for vector 0 does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     * This test checks the dot product of two vectors.
     * It includes equivalence partition tests and boundary value tests.
     */
    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(-12, v11n3.dotProduct(v246));
        // =============== Boundary Values Tests ==================
        assertEquals(28, v123.dotProduct(v246));
        assertEquals(0, new Vector(1, 2, 1).dotProduct(v11n3));
        assertEquals(-1, v1.dotProduct(v11n3));
        assertEquals(v1.lengthSquared(), v1.dotProduct(v1));
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     * This test checks the cross product of two vectors.
     * It includes equivalence partition tests and boundary value tests.
     */
    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(4, 5, 6);
        Vector expected = new Vector(-3, 6, -3);
        // TC01: two vectors, 
        assertEquals(expected, v1.crossProduct(v2));

        // =============== Boundary Values Tests ==================
        // TC11: vector with itself
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v1),
                "crossProduct() with itself does not throw an exception");
        // TC12: two vector with the same direction
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v1.scale(2.0)),
                "crossProduct() with same direction does not throw an exception");
        // TC13: two vector against each other direction, same length
        assertThrows(IllegalArgumentException.class, () -> v123.crossProduct(v123.scale(-1.0)),
                "crossProduct() with same direction does not throw an exception");
        // TC14: two vector against each other direction, different length
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v1.scale(-2.0)),
                "crossProduct() with same direction does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     * This test checks the squared length of a vector.
     * It includes equivalence partition tests.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple length Squared of a vector
        assertEquals(3, v1.lengthSquared());
    }

    /**
     * Test method for {@link primitives.Vector#length()}.
     * This test checks the length of a vector.
     * It includes equivalence partition tests.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple length of a vector
        assertEquals(3, new Vector(1, 2, 2).length());
    }

    /**
     * Test method for {@link primitives.Vector#normalize()}.
     * This test checks the normalization of a vector.
     * It includes equivalence partition tests.
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple normalization of a vector
        assertEquals(new Vector(0.6, 0.8, 0), new Vector(3, 4, 0).normalize());
    }

    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     * This test checks the addition of two vectors.
     * It includes equivalence partition tests.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple subtraction with two vector
        assertEquals(v123.add(v123), v246);
        // =============== Boundary Values Tests ==================
        // TC12: add vector with negative itself
        assertThrows(IllegalArgumentException.class, () -> v1.scale(-1.0).add(v1),
                "Add() for vector 0 does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#subtract(Point)}.
     * This test checks the subtraction of two vectors.
     * It includes equivalence partition tests and boundary value tests.
     */
    @Test
    void testSubVector() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: simple subtraction with two vector
        assertEquals(new Vector(-1, -2, -3), v123.subtract(v246));
        // =============== Boundary Values Tests ==================
        // TC12: subtract vector with itself
        assertThrows(IllegalArgumentException.class, () -> v123.subtract(v123),
                "subtract() for vector 0 does not throw an exception");
    }
}