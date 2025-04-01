package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * Unit tests for the Vector class.
 */
class VectorTests {

    private final Vector v111 = new Vector(1, 1, 1);
    private final Vector v121 = new Vector(1, 2, 1);
    private final Vector v122 = new Vector(1, 2, 2);
    private final Vector v123 = new Vector(1, 2, 3);
    private final Vector v340 = new Vector(3, 4, 0);
    private final Vector v11n3 = new Vector(1, 1, -3);
    private final Vector v246 = new Vector(2, 4, 6);
    /**
     * Test method for {@link primitives.Vector#Vector(double, double, double)}.
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
        assertEquals(0, v121.dotProduct(v11n3));
        assertEquals(-1, v111.dotProduct(v11n3));
        assertEquals(v111.lengthSquared(), v111.dotProduct(v111));
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
        assertEquals(expected, v1.crossProduct(v2));

        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v1),
                "crossProduct() with itself does not throw an exception");

    }
    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     * This test checks the squared length of a vector.
     * It includes equivalence partition tests.
     */
    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(3, v111.lengthSquared());
    }
    /**
     * Test method for {@link primitives.Vector#length()}.
     * This test checks the length of a vector.
     * It includes equivalence partition tests.
     */
    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(3, v122.length());
    }
    /**
     * Test method for {@link primitives.Vector#normalize()}.
     * This test checks the normalization of a vector.
     * It includes equivalence partition tests.
     */
    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Vector(0.6, 0.8, 0), v340.normalize());
    }
    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     * This test checks the addition of two vectors.
     * It includes equivalence partition tests.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(v123.add(v123), v246);
    }

    /**
     * Test method for {@link primitives.Vector#subtract(Point)}.
     * This test checks the subtraction of two vectors.
     * It includes equivalence partition tests and boundary value tests.
     */
    @Test
    void testSubVector()  {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Vector(-1, -2, -3), v123.subtract(v246));
        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> v123.subtract(v123),
                "subtract() for vector 0 does not throw an exception");

    }
}