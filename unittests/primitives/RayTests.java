package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Ray class.
 */
class RayTests {

    /**
     * Test method for {@link primitives.Ray#getPoint(double)}.
     */
    @Test
    void testGetPoint() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Positive number
        Ray test = new Ray(new Point(1, 2, 3), new Vector(0, 0, 1));
        assertEquals(new Point(1, 2, 5), test.getPoint(2));

        // TC02: Negative number
        assertEquals(new Point(1, 2, 2), test.getPoint(-1));

        // =============== Boundary Values Tests ==================
        // TC10: Zero
        assertEquals(new Point(1, 2, 3), test.getPoint(0));
    }


}