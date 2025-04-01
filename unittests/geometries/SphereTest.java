package geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import primitives.Point;
import primitives.Vector;

class SphereTest {

    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere sphere = new Sphere(1, new Point(0, 0, 0));
        assertEquals(new Vector(1,0,0),sphere.getNormal(new Point(1 ,0,0)));
        // =============== Boundary Values Tests ==================
    }
}