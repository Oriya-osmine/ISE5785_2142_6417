package renderer;

import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testing Camera Class
 */
public class CameraIntersectionsIntegrationTests {

    /**
     * For 3x3 matrix
     */
    private static final int NX = 3;
    /**
     * For 3x3 matrix
     */
    private static final int NY = 3;
    /**
     * Camera at 0,0,0 points to z -1 and has distance 1, used for testing
     */
    private static final Camera camera0Zn1D1 = Camera.getBuilder().setLocation(new Point(0, 0, 0))
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpSize(3, 3).setVpDistance(1.0).build();

    /**
     * Computes the amount of rays based on a camera and Geometries
     *
     * @param camera     the camera
     * @param geometries the geometries
     * @return the amount of intersections between the geometries and the camera rays
     */
    private int rays(Camera camera, Geometries geometries) {
        List<Point> intersects = new LinkedList<>();// Initialize an empty list to store intersection points.
        // Iterate over all the pixels in the camera's view plane.
        for (int i = 0; i < NX; i++) {
            for (int j = 0; j < NY; j++) {
                // Construct a ray from the camera through the current pixel.
                Ray ray = camera.constructRay(NX, NY, i, j);
                // Find all intersection points between the ray and the geometry.
                List<Point> points = geometries.findIntersections(ray);
                if (points != null)
                    intersects.addAll(points);//if is empty this will not add anything
            }
        }
        // Return the total number of intersection points found.
        return intersects.size();
    }

    /**
     * Test sphere intersections with camera
     */
    @Test
    public void sphereTest() {
        // ============ Tests ==============
        // Test intersection points with the middle ray of HxW with the middle of sphere
        Sphere sphere = new Sphere(new Point(0, 0, -3), 1);
        assertEquals(2, rays(camera0Zn1D1, new Geometries(sphere)), "Only middle ray should intersect");
        // Test intersection points with all HxW rays
        Sphere sphere1 = new Sphere(new Point(0, 0, -2.5), 2.5);
        Camera camera1 = Camera.getBuilder().setLocation(new Point(0, 0, 0.5))
                .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setVpSize(3, 3).setVpDistance(1.0).build();
        assertEquals(18, rays(camera1, new Geometries(sphere1)), "All rays should intersect twice, on enter and on exit");
        // Test intersection points with some HxW rays
        Sphere sphere2 = new Sphere(new Point(0, 0, -2), 2);
        assertEquals(10, rays(camera1, new Geometries(sphere2)), "Only 5 rays should intersect");
        // Test intersection points with HxW rays inside the sphere
        Sphere sphere3 = new Sphere(new Point(0, 0, -1), 4);
        assertEquals(9, rays(camera1, new Geometries(sphere3)), "All rays should intersect once");
        // Test no intersection points with sphere behind camera
        Sphere sphere4 = new Sphere(new Point(0, 0, 3), 1);
        assertEquals(0, rays(camera1, new Geometries(sphere4)), "No rays should intersect");
    }

    /**
     * Test plane intersections with camera
     */
    @Test
    public void planeTest() {
        // ============ Tests ==============
        // Test intersection points with a plane parallel to axis y with all HxW rays
        Plane plane = new Plane(new Point(0, 0, -3), new Vector(0, 0, 1));
        assertEquals(9, rays(camera0Zn1D1, new Geometries(plane)), "All rays should intersect");
        // Test intersection points with a plane with all HxW rays
        Plane plane1 = new Plane(new Point(0, 0, -3), new Vector(0.5, 0, 1));
        assertEquals(9, rays(camera0Zn1D1, new Geometries(plane1)), "All rays should intersect");
        // Test intersection points with a plane with some HxW rays
        Plane plane2 = new Plane(new Point(0, 0, -3), new Vector(1, 0, 1));
        assertEquals(6, rays(camera0Zn1D1, new Geometries(plane2)), "Only middle and lower rays should intersect");
    }

    /**
     * Test triangle intersections with camera
     */
    @Test
    public void triangleTest() {
        // ============ Tests ==============
        // Test intersection point with a triangle parallel to axis y with middle HxW ray
        Triangle triangle = new Triangle(new Point(0, 1, -2), new Point(-1, -1, -2), new Point(1, -1, -2));
        assertEquals(1, rays(camera0Zn1D1, new Geometries(triangle)), "Only middle ray should intersect");

        // Test intersection points with a triangle parallel to axis y with middle middle and top middle HxW rays
        Triangle triangle1 = new Triangle(new Point(0, 20, -2), new Point(-1, -1, -2), new Point(1, -1, -2));
        assertEquals(2, rays(camera0Zn1D1, new Geometries(triangle1)), "Only middle middle and top middle rays should intersect");
    }
}
