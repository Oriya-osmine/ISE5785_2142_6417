package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    private final Vector v1 = new Vector(-1,-2,-3);
    private final Point p1 = new Point(1, 2, 3);
    private final Point p2 = new Point(4, 5, 6);
    private final Point p3 = new Point(-3, -3, -3);
    private final Point p4 = new Point(3, 3, 3);
    @Test
    void subtract() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(p3,p1.subtract(p2),
                "subtract() for points 1 and 2 does not work correctly");
        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p1),
                "subtract() for point 0 does not throw an exception");
    }

    @Test
    void add() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(p4,p2.add(v1),
                "add() for points 2 and vector 1 does not work correctly");
        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> p1.add(v1),
                "add() for point 0 does not throw an exception");
    }

    @Test
    void distanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(27,p1.distanceSquared(p2),
                "distanceSquared() for points 1 and 2 does not work correctly");
        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> p1.distanceSquared(p1),
                "distanceSquared() for point 0 does not throw an exception");
    }

    @Test
    void distance() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(Math.sqrt(27),p1.distance(p2),
                "distance() for points 1 and 2 does not work correctly");
        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> p1.distance(p1),
                "distance() for point 0 does not throw an exception");
    }
}