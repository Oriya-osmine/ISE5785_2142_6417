package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Cylinder class.
 */
class CylinderTests {
    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
     * This test checks the normal vector to the cylinder at a given point.
     * It includes equivalence partition tests and boundary value tests.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Cylinder cylinder = new Cylinder(1, new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)), 3);
        // TC01 on the shell
        assertEquals(new Vector(0, 1, 0), cylinder.getNormal(new Point(1, 1, 0)));
        //TC02 on the base
        assertEquals(new Vector(1, 0, 0), cylinder.getNormal(new Point(3, 0.5, 0)));
        // TC03 on the negative base
        assertEquals(new Vector(-1, 0, 0), cylinder.getNormal(new Point(-3, 0.5, 0)));
        // =============== Boundary Values Tests ==================
        //TC11 At the base of a circle somewhere
        assertEquals(new Vector(1, 0, 0), cylinder.getNormal(new Point(3, 0, 0)));
        // TC12 At the base of a circle somewhere at negative side
        assertEquals(new Vector(-1, 0, 0), cylinder.getNormal(new Point(-3, 0, 0)));
        //TC13 At the junction between the base and the shell
        assertEquals(new Vector(1, 0, 0), cylinder.getNormal(new Point(3, 1, 0)));
        // TC14 At the junction between the negative base and the shell
        assertEquals(new Vector(-1, 0, 0), cylinder.getNormal(new Point(-3, 1, 0)));
    }
}