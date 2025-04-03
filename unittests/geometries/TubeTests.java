package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the Tube class.
 */
class TubeTests {
    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     * This test checks the normal vector to the tube at a given point.
     * It includes equivalence partition tests and boundary value tests.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        //TC01
        Tube tube = new Tube(1, new Ray(new Point(0, 0, 0)
                , new Vector(1, 0, 0)));
        assertEquals(new Vector(0, 1, 0), tube.getNormal(new Point(3, 1, 0)));
        // =============== Boundary Values Tests ==================
        //TC11
        assertEquals(new Point(0,-1,0),tube.getNormal(new Point(0, 1, 0)));
    }
}