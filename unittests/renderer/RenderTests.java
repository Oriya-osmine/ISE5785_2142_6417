package renderer;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
class RenderTests {
    /**
     * Camera builder of the tests
     */
    private final Camera.Builder camera = Camera.getBuilder() //
            .setLocation(Point.ZERO).setDirection(new Point(0, 0, -1), Vector.AXIS_Y) //
            .setVpDistance(100) //
            .setVpSize(500, 500);

    /**
     * Default constructor to satisfy JavaDoc generator
     */
    RenderTests() { /* to satisfy JavaDoc generator */ }

    /**
     * Produce a scene with basic 3D model and render it into a png image with a
     * grid
     */
    @Test
    void renderTwoColorTest() {
        Scene scene = new Scene("Two color").setBackground(new Color(75, 127, 90))
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191)));
        scene.geometries //
                .add(// center
                        new Sphere(new Point(0, 0, -100), 50d),
                        // up left
                        new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)),
                        // down left
                        new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)),
                        // down right
                        new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100)));

        camera //
                .setRayTracer(scene, RayTracerType.SIMPLE) //
                .setResolution(1000, 1000) //
                .build() //
                .renderImage() //
                .printGrid(100, new Color(YELLOW)) //
                .writeToImage("Two color render test");
    }

    // For stage 6 - please disregard in stage 5

    /**
     * Produce a scene with basic 3D model - including individual lights of the
     * bodies and render it into a png image with a grid
     */
    @Test
    void renderMultiColorTest() {
        Scene scene = new Scene("Multi color").setAmbientLight(new AmbientLight(new Color(51, 51, 51)));
        scene.geometries //
                .add(// center
                        new Sphere(new Point(0, 0, -100), 50),
                        // up left
                        new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)) //
                                .setEmission(new Color(GREEN)),
                        // down left
                        new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)) //
                                .setEmission(new Color(RED)),
                        // down right
                        new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100)) //
                                .setEmission(new Color(BLUE)));

        camera //
                .setRayTracer(scene, RayTracerType.SIMPLE) //
                .setResolution(1000, 1000) //
                .build() //
                .renderImage() //
                .printGrid(100, new Color(WHITE)) //
                .writeToImage("color render test");
    }

    /**
     * Produce a scene with basic 3D model - including individual lights of the
     * bodies and render it into a png image with a grid
     * testing material
     */
    @Test
    void renderMultiColorMaterialTest() {
        Scene scene = new Scene("Multi color material").setAmbientLight(new AmbientLight(new Color(WHITE)));
        scene.geometries //
                .add(// center
                        new Sphere(new Point(0, 0, -100), 50).setMaterial(new Material().setKA(new Double3(0.4))),
                        // up left
                        new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)).setMaterial(new Material().setKA(new Double3(0, 0.8, 0))),
                        // down left
                        new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)).setMaterial(new Material().setKA(new Double3(0.8, 0, 0))),
                        // down right
                        new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100)).setMaterial(new Material().setKA(new Double3(0, 0, 0.8))));

        camera //
                .setRayTracer(scene, RayTracerType.SIMPLE) //
                .setResolution(1000, 1000) //
                .build() //
                .renderImage() //
                .printGrid(100, new Color(WHITE)) //
                .writeToImage("Multi color material");
    }

    /**
     * Test method to check {@link scene.Scene#Scene(String)}
     */
    @Test
    public void basicRenderXml() {
        //TC01: build a scene from xml
        Scene scene = new Scene("renderTestTwoColors.xml");
        camera //
                .setRayTracer(scene, RayTracerType.SIMPLE) //
                .setResolution(1000, 1000) //
                .build() //
                .renderImage() //
                .printGrid(100, new Color(YELLOW)) //
                .writeToImage("xml render test");
        //TC02: missing plane point
        assertThrows(IllegalArgumentException.class, () -> new Scene("renderTestFail.xml"));
    }

}
