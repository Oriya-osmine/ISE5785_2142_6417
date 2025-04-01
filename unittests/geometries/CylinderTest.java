package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import primitives.Point;

class CylinderTest {

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Cylinder cylinder=new Cylinder(1,new Ray(new Point(0,0,0),new Vector(1,0,0)),3);
        assertEquals(new Vector(0,1,0),cylinder.getNormal(new Point(1,1,0)));
        assertEquals(new Vector(1,0,0),cylinder.getNormal(new Point(3,0.5,0)));
        assertEquals(new Vector(-1,0,0),cylinder.getNormal(new Point(-3,0.5,0)));
        // =============== Boundary Values Tests ==================
        assertEquals(new Vector(1,0,0),cylinder.getNormal(new Point(3,0,0)));
        assertEquals(new Vector(-1,0,0),cylinder.getNormal(new Point(-3,0,0)));
        assertEquals(new Vector(1,0,0),cylinder.getNormal(new Point(3,1,0)));
        assertEquals(new Vector(-1,0,0),cylinder.getNormal(new Point(-3,1,0)));
    }
}