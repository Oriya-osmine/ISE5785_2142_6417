package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VectorTests {

    private final Vector v111 = new Vector(1, 1, 1);
    private final Vector v123 = new Vector(1, 2, 3);
    private final Vector v11n3 = new Vector(1, 1, -1);
    private final Vector v246 = new Vector(2, 4, 6);
    private final double d28 = 28;
    private final double d0 = 0;
    private final double dn1 = -1;
    private final double dn12 = -12;

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

        assertEquals(dn12, v11n3.dotProduct(v246));
        // =============== Boundary Values Tests ==================
        assertEquals(d28, v123.dotProduct(v246));
        assertEquals(d0, v123.dotProduct(v11n3));
        assertEquals(dn1, v111.dotProduct(v11n3));
        assertEquals(dn1, v111.dotProduct(v111));
    }

    @Test
    void testCrossProduct() {
    }

    @Test
    void testLengthSquared() {
    }

    @Test
    void testLength() {
    }

    @Test
    void testNormalize() {
    }

    @Test
    void testAdd() {
    }

    @Test
    void testEquals() {
    }
}