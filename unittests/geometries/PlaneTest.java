package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    Point p1 = new Point(1, 1, 1);
    Point p2 = new Point(2, 2, 2);
    Point p1B = new Point(1, 1, 1);
    Point p2B = new Point(2, 2, 2);
    Point p3 = new Point(3, 3, 3);
 @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Plane plane = new Plane(new Point(1, 1, 2), new Vector(0, 0, 5));
        assertEquals(new Vector(0, 0, 1), plane.getNormal(new Point(3, 4, 2)),
                "getNormal() does not return the correct normal vector");
        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p1B, p2),
                "Constructed a plane with 2 equal points");
        
        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p2, p1B),
                "Constructed a plane with 2 equal points");

        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p2, p2B),
                "Constructed a plane with 2 equal points");

        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p1, p1),
                "Constructed a plane with 3 equal points");

        assertThrows(IllegalArgumentException.class, () -> new Plane(p1, p2, p3),
                "Constructed a plane with 3 points that are not in the same plane");

    }


}