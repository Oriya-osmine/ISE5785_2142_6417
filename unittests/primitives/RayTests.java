package primitives;

import org.junit.jupiter.api.Test;
import java.util.List;
import javax.swing.*;

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

    @Test
    void testFindClosetPoint(){
        // ============ Equivalence Partitions Tests ==============
        //TC01: simple check
        List<Point> pointList = List.of(
                new Point(0, 0, 0),
                new Point(1, 1, 1),
                new Point(2, 2, 2),
                new Point(3, 3, 3));
        Ray test = new Ray(new Point(2.2, 2.4, 1.9), new Vector(3, 2.4, 1.9));
        assertEquals(pointList.get(2),test.findClosetPoint(pointList));
        // =============== Boundary Values Tests ==================
        //TC01: null check
        assertNull(test.findClosetPoint(null));
        //TC02: first point
        Ray test2 = new Ray(new Point(0.2, 0.2, 0.2), new Vector(1, 0, 0));
        assertEquals(pointList.get(0),test2.findClosetPoint(pointList));
        //TC: last point
        Ray test3 = new Ray(new Point(4, 4, 4), new Vector(5, 4, 4));
        assertEquals(pointList.get(3),test3.findClosetPoint(pointList));
    }


}