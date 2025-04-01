package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VectorTests {

    private final Vector v111 = new Vector(1, 1, 1);
    private final Vector v121 = new Vector(1, 2, 1);
    private final Vector v122 = new Vector(1, 2, 2);
    private final Vector v123 = new Vector(1, 2, 3);
    private final Vector v340 = new Vector(3, 4, 0);
    private final Vector v11n3 = new Vector(1, 1, -3);
    private final Vector v246 = new Vector(2, 4, 6);

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

    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(v246, v123.scale(2.0));
        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> v123.scale(0.0),
                "scale() for vector 0 does not throw an exception");
    }

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

    @Test
    void testCrossProduct() {
    }

    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(3, v111.lengthSquared());
    }

    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(3, v122.length());
    }

    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Vector(0.6, 0.8, 0), v340.normalize());
    }

    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(v123.add(v123), v246);
    }

    @Test
    void testEquals() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(v123, v246.scale(0.5));
        assertEquals(v123, v123);
    }
}