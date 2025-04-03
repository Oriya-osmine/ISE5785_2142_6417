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
        // TC01: constructor with 3 doubles
        assertEquals(new Vector(1, 2, 3), v123);
        // TC02: constructor with Double3
        assertEquals(new Vector(new Double3(-1, -2, -3)), v123.scale(-1.0));
        // =============== Boundary Values Tests ==================
        // TC11: constructor of vector zero with 3 doubles
        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0),
                "scale() for vector 0 does not throw an exception");
        // TC12: constructor of vector zero with Double3
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
        // TC01: regular scale positive test
        assertEquals(v246, v123.scale(2.0));
        // TC02: regular scale negative test
        assertEquals(new Vector(-2,-4,-6), v123.scale(-2.0));
        // =============== Boundary Values Tests ==================
        // TC11: scale with 0
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
        // TC01: Dot product of two vectors forming an obtuse angle
        assertTrue(v123.dotProduct(new Vector(-1, -3, 2)) < 0,
                "Dot product should be negative for obtuse angles");

        // TC02: Dot product of two vectors forming an acute angle
        assertTrue(v123.dotProduct(new Vector(3, 1, 2)) > 0,
                "Dot product should be positive for acute angles");

        // =============== Boundary Value Tests ==================
        // TC11: Dot product of two perpendicular vectors (90 degrees)
        assertEquals(0, new Vector(1, 2, 1).dotProduct(v11n3),
                "Dot product should be zero for perpendicular vectors");
        // TC12: Dot product with a unit vector
        assertEquals(1, new Vector(1, 0, 0).dotProduct(v11n3),
                "Dot product with a unit vector should be correct");
        // TC13: Dot product of a vector with itself
        assertEquals(v123.lengthSquared(), v123.dotProduct(v123),
                "Dot product of a vector with itself should be its squared length");
    }



    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     * This test checks the cross product of two vectors.
     * It includes equivalence partition tests and boundary value tests.
     */
    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: two vectors with an obtuse angle
        assertEquals(new Vector(5, -10, 5), v123.crossProduct(new Vector(-2, 1, 4)));

        // TC02: two vectors with an acute angle
        assertEquals(new Vector(1, 7, -5), v123.crossProduct(new Vector(3, 1, 2)));

        // =============== Boundary Values Tests ==================
        // TC11: vector with itself
        assertThrows(IllegalArgumentException.class, () -> v123.crossProduct(v123),
                "crossProduct() with itself does not throw an exception");
        // TC12: two vectors with the same direction
        assertThrows(IllegalArgumentException.class, () -> v123.crossProduct(v123.scale(2.0)),
                "crossProduct() with same direction does not throw an exception");
        // TC13: two vectors against each other direction, same length
        assertThrows(IllegalArgumentException.class, () -> v123.crossProduct(v123.scale(-1.0)),
                "crossProduct() with opposite direction does not throw an exception");
        // TC14: two vectors against each other direction, different length
        assertThrows(IllegalArgumentException.class, () -> v123.crossProduct(v123.scale(-2.0)),
                "crossProduct() with opposite direction does not throw an exception");
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
        // TC01: simple addition with two positive vectors
        assertEquals(v246, v123.add(v123));
        // TC02: simple addition with one negative one positive
        assertEquals(v246.scale(-1.0), v123.add(v123.scale(-3.0)));
        // =============== Boundary Values Tests ==================
        // TC11: add vector with negative itself
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
        // TC01: simple subtraction with two positive vector
        assertEquals(v123.scale(-1.0), v123.subtract(v246));
        // TC02: simple subtraction with one negative one positive
        assertEquals(v246.scale(2.0), v123.subtract(v123.scale(-3.0)));
        // =============== Boundary Values Tests ==================
        // TC11: subtract vector with itself
        assertThrows(IllegalArgumentException.class, () -> v123.subtract(v123),
                "subtract() for vector 0 does not throw an exception");
    }
}