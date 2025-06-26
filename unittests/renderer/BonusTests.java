package renderer;

import geometries.Cylinder;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

/**
 * Testing basic shadows
 *
 * @author Dan Zilberstein
 */
class BonusTests {

    /**
     * Scene of the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder of the tests
     */
    private final Camera.Builder camera = Camera.getBuilder()
            .setLocation(new Point(200, 200, 800))
            .setDirection(new Point(0, 0, -100), new Vector(0, 1, 0))
            .setVpDistance(1000)
            .setVpSize(200, 200)
            .setRayTracer(scene, RayTracerType.SIMPLE);

    /**
     * basic test of all functionality
     */
    @Test
    void trianglesSphere() {
        scene.geometries //
                .add(
                        // Floor
                        new Polygon(
                                new Point(-120, -120, -150),
                                new Point(120, -120, -150),
                                new Point(120, 30, -150),
                                new Point(-120, 30, -150)
                        ).setEmission(new Color(20, 60, 0)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        // Sky
                        new Polygon(
                                new Point(-120, 30, -150),
                                new Point(120, 30, -150),
                                new Point(120, 120, -150),
                                new Point(-120, 120, -150)
                        ).setEmission(new Color(25, 25, 80)).setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)),


                        // House walls
                        new Polygon(
                                new Point(25, -50, 0),     // Bottom-left
                                new Point(90, -50, 0),     // Bottom-right
                                new Point(90, 50, 0),      // Top-right
                                new Point(25, 50, 0)       // Top-left
                        ).setEmission(new Color(139, 69, 19)).setMaterial(new Material().setKD(0.8).setKS(0.3).setShininess(30)),
                        new Polygon(
                                new Point(25, -50, -50),   // Bottom-left
                                new Point(25, 50, -50),    // Top-left
                                new Point(90, 50, -50),    // Top-right
                                new Point(90, -50, -50)    // Bottom-right
                        ).setEmission(new Color(139, 69, 19)).setMaterial(new Material().setKD(0.8).setKS(0.3).setShininess(30)),
                        new Polygon(
                                new Point(25, -50, 0),     // Front bottom
                                new Point(25, 50, 0),      // Front top
                                new Point(25, 50, -50),    // Back top
                                new Point(25, -50, -50)    // Back bottom
                        ).setEmission(new Color(139, 69, 19)).setMaterial(new Material().setKD(0.8).setKS(0.3).setShininess(30)),
                        new Polygon(
                                new Point(90, -50, 0),     // Front bottom
                                new Point(90, -50, -50),   // Back bottom
                                new Point(90, 50, -50),    // Back top
                                new Point(90, 50, 0)       // Front top
                        ).setEmission(new Color(139, 69, 19)).setMaterial(new Material().setKD(0.8).setKS(0.3).setShininess(30)),

                        // House roof
                        new Triangle(
                                new Point(25, 50, 0),      // Left
                                new Point(57, 70, 0),      // Middle top
                                new Point(90, 50, 0)       // Right
                        ).setEmission(new Color(178, 34, 34)).setMaterial(new Material().setKD(0.8).setKS(0.4).setShininess(50)),
                        new Triangle(
                                new Point(25, 50, -50),    // Left
                                new Point(90, 50, -50),    // Right
                                new Point(57, 70, -50)     // Middle top
                        ).setEmission(new Color(178, 34, 34)).setMaterial(new Material().setKD(0.8).setKS(0.4).setShininess(50)),
                        new Polygon(
                                new Point(25, 50, 0),      // Front left
                                new Point(25, 50, -50),    // Back left
                                new Point(57, 70, -50),    // Back top
                                new Point(57, 70, 0)       // Front top
                        ).setEmission(new Color(178, 34, 34)).setMaterial(new Material().setKD(0.8).setKS(0.4).setShininess(50)), new Polygon(
                                new Point(90, 50, 0),      // Front right
                                new Point(57, 70, 0),      // Front top
                                new Point(57, 70, -50),    // Back top
                                new Point(90, 50, -50)     // Back right
                        ).setEmission(new Color(178, 34, 34)).setMaterial(new Material().setKD(0.8).setKS(0.4).setShininess(50)),


                        // Sun
                        new Sphere(new Point(-80, 80, -100), 15)
                                .setEmission(new Color(255, 215, 0))
                                .setMaterial(new Material().setKD(0.6).setKS(0.95).setShininess(200)),

                        // Tree
                        new Cylinder(8.0, new Ray(new Point(-50, -50, -100), new Vector(0, 1, 0)), 120.0)
                                .setEmission(new Color(102, 51, 0))
                                .setMaterial(new Material().setKD(0.9).setKS(0.1).setShininess(10)),
                        // Tree foliage
                        new Sphere(new Point(-50, 60, -100), 30)
                                .setEmission(new Color(15, 72, 16))
                                .setMaterial(new Material().setKD(0.6).setKS(0.2).setShininess(30).setKT(0.3)),
                        new Sphere(new Point(-60, 85, -85), 5)
                                .setEmission(new Color(204, 0, 0))
                                .setMaterial(new Material().setKD(0.8).setKS(0.7).setShininess(100)),
                        new Sphere(new Point(-40, 40, -102), 5)
                                .setEmission(new Color(204, 0, 0))
                                .setMaterial(new Material().setKD(0.8).setKS(0.7).setShininess(100)),
                        new Sphere(new Point(-30, 74, -85), 5)
                                .setEmission(new Color(204, 0, 0))
                                .setMaterial(new Material().setKD(0.8).setKS(0.7).setShininess(100)),


                        // Blue cube
                        new Polygon(
                                new Point(55, -30, 100),
                                new Point(75, -30, 100),
                                new Point(75, 10, 100),
                                new Point(55, 10, 100)
                        ).setEmission(new Color(50, 50, 120)).setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(30)),
                        new Polygon(
                                new Point(55, -30, 80),
                                new Point(55, 10, 80),
                                new Point(75, 10, 80),
                                new Point(75, -30, 80)
                        ).setEmission(new Color(50, 50, 120)).setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(30)),
                        new Polygon(
                                new Point(55, 10, 100),
                                new Point(75, 10, 100),
                                new Point(75, 10, 80),
                                new Point(55, 10, 80)
                        ).setEmission(new Color(50, 50, 120)).setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(30)),
                        new Polygon(
                                new Point(55, -30, 100),
                                new Point(55, -30, 80),
                                new Point(75, -30, 80),
                                new Point(75, -30, 100)
                        ).setEmission(new Color(50, 50, 120)).setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(30)),
                        new Polygon(
                                new Point(55, -30, 100),
                                new Point(55, 10, 100),
                                new Point(55, 10, 80),
                                new Point(55, -30, 80)
                        ).setEmission(new Color(50, 50, 120)).setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(30)),
                        new Polygon(
                                new Point(75, -30, 100),
                                new Point(75, -30, 80),
                                new Point(75, 10, 80),
                                new Point(75, 10, 100)
                        ).setEmission(new Color(50, 50, 120)).setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(30)),


                        // Lake (between tree and house)
                        new Polygon(
                                new Point(-70, -49, -50),
                                new Point(20, -49, -50),
                                new Point(10, -49, -120),
                                new Point(-60, -49, -120)
                        ).setEmission(new Color(28, 107, 160))
                                .setMaterial(new Material().setKD(0.2).setKS(0.8).setShininess(100).setKR(0.5)) // High reflection coefficient (KR)


                );

        scene.lights.add(new PointLight(new Color(80, 150, 224), new Point(-30, 80, -100))
                .setKl(0.0005).setKq(0.0005));

        scene.lights.add(new SpotLight(new Color(100, 120, 100), new Point(60, 20, 200), new Vector(0, 0, -1))
                .setKl(0.0001).setKq(0.00005).setNarrowBeam(15));
        // Warm light from bottom right - creates contrast

        camera//
                .setResolution(600, 600) //
                .build() //
                .renderImage() //
                .writeToImage("3DSceneRendering");
    }

}


/**
 * Living Room Scene - Fixed Version
 * Simple coordinates and proper camera positioning
 */
class LivingRoomTests {

    /**
     * Scene of the tests
     */
    private final Scene scene = new Scene("Living Room Scene");
    /**
     * Camera positioned to look into the room from corner
     */
    private final Camera.Builder camera = Camera.getBuilder()
            .setLocation(new Point(200, 200, 800))
            .setDirection(new Point(0, 0, -100), new Vector(0, 1, 0))
            .setVpDistance(200)
            .setVpSize(200, 150)
            .setDebugPrint(0.1);
    /**
     * Camera positioned to the right side
     */
    private final Camera.Builder cameraRightView = Camera.getBuilder()
            .setLocation(new Point(300, 180, 750)) // Moved right and slightly lower
            .setDirection(new Point(0, 0, -100), new Vector(0, 1, 0))
            .setVpDistance(1000)
            .setVpSize(600, 600)
            .setRayTracer(scene, RayTracerType.SIMPLE);


    /**
     * Camera positioned to the left side
     */
    private final Camera.Builder cameraLeftView = Camera.getBuilder()
            .setLocation(new Point(30, 200, 800)) // Moved left
            .setDirection(new Point(0, 0, -100), new Vector(0, 1, 0)) // Ensure non-zero vector
            .setVpDistance(200)
            .setVpSize(200, 150)
            .setRayTracer(scene, RayTracerType.SIMPLE);

    /**
     * Camera positioned at a high angle
     */
    private final Camera.Builder cameraHighAngle = Camera.getBuilder()
            .setLocation(new Point(250, 290, 850)) // High position
            .setDirection(new Point(0, 0, -100), new Vector(0, 1, 0)) // Looking downward
            .setVpDistance(200)
            .setVpSize(200, 150)
            .setRayTracer(scene, RayTracerType.SIMPLE);

    /**
     * sofa color
     */
    Color sofa = new Color(110, 100, 60);
    /**
     * carpet color
     */
    Color carpet = new Color(139, 34, 34);
    /**
     * base table color
     */
    Color baseTable = new Color(101, 67, 33);
    /**
     * leg table color
     */
    Color legTable = new Color(70, 40, 20);
    /**
     * walls color
     */
    Color walls = new Color(35, 60, 40);

    /**
     * the wall material
     */
    Material wall = new Material().setKD(0.5).setKS(0.9).setShininess(20);

    /**
     * the living room
     */
    @Test
    void livingRoomScene() {
        // Main floor divided into square tiles using loops
        int tileSize = 75;
        int startX = -300;
        int endX = 500;
        int startZ = -200;
        int endZ = 500;
        for (int x = startX; x < endX; x += tileSize) {
            for (int z = startZ; z < endZ; z += tileSize) {
                // Alternate colors between two shades based on tile indices
                Color tileColor = (((x - startX) / tileSize) + ((z - startZ) / tileSize)) % 2 == 0
                        ? new Color(190, 150, 110)
                        : new Color(150, 110, 70);
                scene.geometries.add(
                        new Polygon(
                                new Point(x, -42, z),
                                new Point(x, -42, z + tileSize),
                                new Point(x + tileSize, -42, z + tileSize),
                                new Point(x + tileSize, -42, z)
                        ).setEmission(tileColor)
                                .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(80))
                );
            }
        }

        // Sofa and carpet
        scene.geometries
                .add(
                        new Polygon(
                                new Point(0, -20, 100),     // Bottom-left
                                new Point(300, -20, 100),     // Bottom-right
                                new Point(300, 20, 100),      // Top-right
                                new Point(0, 20, 100)       // Top-left
                        ).setEmission(sofa).setMaterial(new Material().setKD(0.8).setKS(0.3).setShininess(30)),
                        new Polygon(
                                new Point(0, -20, -100),   // Bottom-left
                                new Point(0, 20, -100),    // Top-left
                                new Point(300, 20, -100),    // Top-right
                                new Point(300, -20, -100)    // Bottom-right
                        ).setEmission(sofa).setMaterial(new Material().setKD(0.8).setKS(0.3).setShininess(30)),
                        new Polygon(
                                new Point(0, -20, 100),     // Front bottom
                                new Point(0, 20, 100),      // Front top
                                new Point(0, 20, -100),    // Back top
                                new Point(0, -20, -100)    // Back bottom
                        ).setEmission(sofa).setMaterial(new Material().setKD(0.8).setKS(0.3).setShininess(30)),
                        new Polygon(
                                new Point(300, -20, 100),     // Front bottom
                                new Point(300, -20, -100),   // Back bottom
                                new Point(300, 20, -100),    // Back top
                                new Point(300, 20, 100)       // Front top
                        ).setEmission(sofa).setMaterial(new Material().setKD(0.8).setKS(0.3).setShininess(30)),
                        new Polygon(
                                new Point(0, 20, 100),      // Front top
                                new Point(300, 20, 100),    // Back top
                                new Point(300, 20, -100),   // Back bottom
                                new Point(0, 20, -100)      // Front bottom
                        ).setEmission(sofa).setMaterial(new Material().setKD(0.8).setKS(0.3).setShininess(30)),
                        new Polygon(
                                new Point(0, 20, -100),      // Bottom-right
                                new Point(300, 20, -100),    // Bottom-left
                                new Point(300, 50, -150),    // Top-left (adjusted for 3D depth)
                                new Point(0, 50, -150)     // Top-right (adjusted for 3D depth)
                        ).setEmission(sofa).setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(20)),
                        //legs
                        new Cylinder(5.0, new Ray(new Point(5, -30, 100), new Vector(0, 1, 0)), 10.0)
                                .setEmission(new Color(80, 80, 80))
                                .setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(30)),
                        new Cylinder(5.0, new Ray(new Point(295, -30, 100), new Vector(0, 1, 0)), 10.0)
                                .setEmission(new Color(80, 80, 80))
                                .setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(30)),
                        //carpet
                        new Polygon(
                                new Point(-50, -40, -100),
                                new Point(310, -40, -100),
                                new Point(310, -40, 200),
                                new Point(-50, -40, 200)
                        ).setEmission(carpet).setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(20))
                );
        // Walls
        scene.geometries
                .add(
                        // Left wall
                        new Polygon(
                                new Point(-200, -300, -200),
                                new Point(-200, -300, 500),
                                new Point(-200, 300, 500),
                                new Point(-200, 300, -200)
                        ).setEmission(walls).setMaterial(wall),

                        // Right wall
                        new Polygon(
                                new Point(350, -300, -200),
                                new Point(350, 300, -200),
                                new Point(350, 300, 500),
                                new Point(350, -300, 500)
                        ).setEmission(walls).setMaterial(wall),

                        // Back wall - left section
                        new Polygon(
                                new Point(-200, -300, -200),
                                new Point(-115, -300, -200),
                                new Point(-115, 300, -200),
                                new Point(-200, 300, -200)
                        ).setEmission(walls).setMaterial(wall),

                        // Back wall - right section
                        new Polygon(
                                new Point(270, -300, -200),
                                new Point(350, -300, -200),
                                new Point(350, 300, -200),
                                new Point(270, 300, -200)
                        ).setEmission(walls).setMaterial(wall),

                        // Back wall - top section
                        new Polygon(
                                new Point(-115, 200, -200),
                                new Point(270, 200, -200),
                                new Point(270, 300, -200),
                                new Point(-115, 300, -200)
                        ).setEmission(walls).setMaterial(wall),

                        // Back wall - bottom section
                        new Polygon(
                                new Point(-115, 50, -200),
                                new Point(270, 50, -200),
                                new Point(270, -42, -200),
                                new Point(-115, -42, -200)
                        ).setEmission(walls).setMaterial(wall),

                        new Polygon(
                                new Point(-150, -300, 200),
                                new Point(-200, -300, 200),
                                new Point(-200, 300, 200),
                                new Point(-150, 300, 200)
                        ).setEmission(walls).setMaterial(wall),
                        new Polygon(
                                new Point(-150, -300, 150),
                                new Point(-150, -300, 200),
                                new Point(-150, 300, 200),
                                new Point(-150, 300, 150)
                        ).setEmission(walls).setMaterial(wall),

                        // Ceiling with a smooth, clean color
                        new Polygon(
                                new Point(-200, 300, -200),
                                new Point(350, 300, -200),
                                new Point(350, 300, 850),
                                new Point(-200, 300, 850)
                        ).setEmission(new Color(155, 90, 102))  // Light cream color for ceiling
                                .setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(5))
                );
        // Additions on the walls lamp mirror and painting
        scene.geometries
                .add(
                        // Mirror
                        new Polygon(
                                new Point(349, 50, -150),
                                new Point(349, 170, -150),
                                new Point(349, 170, 200),
                                new Point(349, 50, 200)
                        ).setEmission(new Color(black))
                                .setMaterial(new Material().setKD(0.2).setKS(0.6).setShininess(70).setKR(0.6)),
                        // Lamp
                        new Cylinder(8.0, new Ray(new Point(100, 300, 100), new Vector(0, -1, 0)), 10.0)
                                .setEmission(new Color(darkGray))  // Metal gray
                                .setMaterial(new Material().setKD(0.7).setKS(0.9).setShininess(100)),
                        new Cylinder(3.0, new Ray(new Point(100, 290, 100), new Vector(0, -1, 0)), 30.0)
                                .setEmission(new Color(gray))  // Metal silver
                                .setMaterial(new Material().setKD(0.6).setKS(0.9).setShininess(100)),
                        new Cylinder(2.0, new Ray(new Point(70, 260, 100), new Vector(1, 0, 0)), 60.0)
                                .setEmission(new Color(gray))  // Metal silver
                                .setMaterial(new Material().setKD(0.6).setKS(0.9).setShininess(100)),
                        // Left
                        new Sphere(new Point(70, 260, 100), 8)
                                .setEmission(new Color(yellow))
                                .setMaterial(new Material().setKD(0.4).setKS(0.9).setShininess(100).setKT(0.2)),
                        // Center
                        new Sphere(new Point(100, 260, 100), 8)
                                .setEmission(new Color(yellow))
                                .setMaterial(new Material().setKD(0.4).setKS(0.9).setShininess(100).setKT(0.2)),
                        // Right
                        new Sphere(new Point(130, 260, 100), 8)
                                .setEmission(new Color(yellow))
                                .setMaterial(new Material().setKD(0.4).setKS(0.9).setShininess(100).setKT(0.2)),
                        // Sky
                        new Polygon(
                                new Point(-300, 0, -271),
                                new Point(400, 0, -271),
                                new Point(400, 300, -271),
                                new Point(-300, 300, -271)
                        )
                                .setEmission(new Color(30, 40, 90))
                                .setMaterial(new Material().setKD(0.9).setKS(0.1).setShininess(5)),
                        // Distant mountains
                        new Triangle(
                                new Point(-120, 40, -201), new Point(-60, 140, -201), new Point(0, 40, -201)
                        )
                                .setEmission(new Color(50, 50, 70))
                                .setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(15)),
                        new Triangle(
                                new Point(180, 40, -201), new Point(250, 160, -201), new Point(320, 40, -201)
                        )
                                .setEmission(new Color(50, 70, 50))
                                .setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(15)),
                        // Lake
                        new Polygon(
                                new Point(-80, 30, -221), new Point(230, 30, -221),
                                new Point(200, 80, -221), new Point(-50, 80, -221)
                        )
                                .setEmission(new Color(64, 164, 223))
                                .setMaterial(new Material().setKD(0.2).setKS(0.8).setShininess(100).setKR(0.3)),
                        // Tree
                        new Triangle(
                                new Point(110, 90, -201), new Point(90, 135, -201), new Point(60, 90, -201)
                        )
                                .setEmission(new Color(34, 139, 34))
                                .setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(10)),
                        new Cylinder(
                                3.0, new Ray(new Point(90, 80, -201), new Vector(0, 1, 0)), 15.0
                        )
                                .setEmission(new Color(102, 51, 0))
                                .setMaterial(new Material().setKD(0.9).setKS(0.1).setShininess(10)),
                        // Small cabin
                        new Polygon(
                                new Point(120, 80, -201), new Point(170, 80, -201),
                                new Point(170, 110, -201), new Point(120, 110, -201)
                        )
                                .setEmission(new Color(139, 69, 19))
                                .setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(10)),
                        // Cabin roof
                        new Triangle(
                                new Point(115, 110, -201), new Point(145, 130, -201), new Point(175, 110, -201)
                        )
                                .setEmission(new Color(120, 40, 31))
                                .setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(10)),
                        // Birds
                        new Triangle(
                                new Point(50, 170, -201), new Point(60, 160, -201), new Point(70, 170, -201)
                        )
                                .setEmission(new Color(30, 30, 30))
                                .setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(10)),
                        new Triangle(
                                new Point(90, 180, -201), new Point(100, 170, -201), new Point(110, 180, -201)
                        )
                                .setEmission(new Color(30, 30, 30))
                                .setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(10)),
                        // Clouds
                        new Sphere(
                                new Point(-30, 160, -201), 15
                        )
                                .setEmission(new Color(255, 255, 255))
                                .setMaterial(new Material().setKD(0.9).setKS(0.1).setShininess(5).setKT(0.1)),
                        new Sphere(
                                new Point(0, 170, -201), 20
                        )
                                .setEmission(new Color(255, 255, 255))
                                .setMaterial(new Material().setKD(0.9).setKS(0.1).setShininess(5).setKT(0.1)),
                        new Sphere(
                                new Point(30, 165, -201), 17
                        )
                                .setEmission(new Color(255, 255, 255))
                                .setMaterial(new Material().setKD(0.9).setKS(0.1).setShininess(5).setKT(0.1)),
                        new Sphere(
                                new Point(180, 175, -201), 15
                        )
                                .setEmission(new Color(255, 255, 255))
                                .setMaterial(new Material().setKD(0.9).setKS(0.1).setShininess(5).setKT(0.1)),
                        new Sphere(
                                new Point(210, 160, -201), 20
                        )
                                .setEmission(new Color(255, 255, 255))
                                .setMaterial(new Material().setKD(0.9).setKS(0.1).setShininess(5).setKT(0.1))
                );
        // Table and objects
        scene.geometries
                .add(
                        // Wooden table
                        new Polygon(
                                new Point(30, 15, 150),
                                new Point(255, 15, 150),
                                new Point(255, 15, 300),
                                new Point(30, 15, 300)
                        ).setEmission(baseTable)
                                .setMaterial(new Material().setKD(0.8).setKS(0.3).setShininess(30)),
                        new Polygon(
                                new Point(30, 20, 150),
                                new Point(255, 20, 150),
                                new Point(255, 20, 300),
                                new Point(30, 20, 300)
                        ).setEmission(baseTable)
                                .setMaterial(new Material().setKD(0.8).setKS(0.3).setShininess(30)),
                        // Magazine stack
                        new Polygon(
                                new Point(50, 20.1, 170),
                                new Point(90, 20.1, 170),
                                new Point(90, 20.1, 220),
                                new Point(50, 20.1, 220)
                        ).setEmission(new Color(240, 50, 45))
                                .setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(10)),
                        new Polygon(
                                new Point(52, 20.11, 175),
                                new Point(88, 20.11, 175),
                                new Point(88, 20.11, 185),
                                new Point(52, 20.11, 185)
                        ).setEmission(new Color(255, 255, 255))
                                .setMaterial(new Material().setKD(0.8).setKS(0.3).setShininess(10)),
                        new Polygon(
                                new Point(55, 20.2, 190),
                                new Point(85, 20.2, 190),
                                new Point(85, 20.2, 215),
                                new Point(55, 20.2, 215)
                        ).setEmission(new Color(20, 180, 220))
                                .setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(10)),
                        new Polygon(
                                new Point(53, 20.3, 172),
                                new Point(93, 20.3, 172),
                                new Point(93, 20.3, 222),
                                new Point(53, 20.3, 222)
                        ).setEmission(new Color(30, 120, 160))
                                .setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(10)),
                        new Polygon(
                                new Point(56, 20.21, 177),
                                new Point(90, 20.21, 177),
                                new Point(90, 20.21, 195),
                                new Point(56, 20.21, 195)
                        ).setEmission(new Color(240, 240, 255))
                                .setMaterial(new Material().setKD(0.8).setKS(0.3).setShininess(10)),
                        new Polygon(
                                new Point(95, 20.3, 175),
                                new Point(95, 20.3, 225),
                                new Point(55, 20.3, 225)
                        ).setEmission(new Color(250, 220, 100))
                                .setMaterial(new Material().setKD(0.7).setKS(0.5).setShininess(30)),
                        // Papers
                        new Polygon(
                                new Point(150, 20.1, 200),
                                new Point(170, 20.1, 200),
                                new Point(170, 20.1, 230),
                                new Point(150, 20.1, 230)
                        ).setEmission(new Color(75, 105, 190))
                                .setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(155, 20.2, 190),
                                new Point(180, 20.2, 195),
                                new Point(175, 20.2, 225),
                                new Point(145, 20.2, 220)
                        ).setEmission(new Color(190, 60, 45))
                                .setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(160, 20.3, 195),
                                new Point(185, 20.3, 198),
                                new Point(182, 20.3, 228),
                                new Point(157, 20.3, 223)
                        ).setEmission(new Color(240, 200, 40))
                                .setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(5)),
                        // Small plant
                        new Cylinder(5.0, new Ray(new Point(220, 20.1, 256), new Vector(0, 1, 0)), 12.0)
                                .setEmission(new Color(120, 60, 30))
                                .setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(20)),
                        new Sphere(new Point(220, 35, 260), 7)
                                .setEmission(new Color(20, 80, 20))
                                .setMaterial(new Material().setKD(0.6).setKS(0.4).setShininess(15)),

                        new Sphere(new Point(214, 33, 256), 4)
                                .setEmission(new Color(30, 90, 30))
                                .setMaterial(new Material().setKD(0.6).setKS(0.4).setShininess(15)),

                        new Sphere(new Point(224, 34, 263), 5)
                                .setEmission(new Color(25, 85, 25))
                                .setMaterial(new Material().setKD(0.6).setKS(0.4).setShininess(15)),
                        // Base table
                        new Polygon(
                                new Point(30, 16, 300),
                                new Point(255, 16, 300),
                                new Point(255, 20, 300),
                                new Point(30, 20, 300)
                        ).setEmission(baseTable) // Brown color for wood
                                .setMaterial(new Material().setKD(0.8).setKS(0.3).setShininess(30)),
                        new Triangle(
                                new Point(50, 17, 300.1),
                                new Point(70, 17, 300.1),
                                new Point(60, 20, 300.1)
                        ).setEmission(new Color(80, 40, 10))
                                .setMaterial(new Material().setKD(0.7).setKS(0.4).setShininess(40)),
                        new Triangle(
                                new Point(100, 17, 300.1),
                                new Point(120, 17, 300.1),
                                new Point(110, 20, 300.1)
                        ).setEmission(new Color(80, 40, 10))
                                .setMaterial(new Material().setKD(0.7).setKS(0.4).setShininess(40)),
                        new Triangle(
                                new Point(150, 17, 300.1),
                                new Point(170, 17, 300.1),
                                new Point(160, 20, 300.1)
                        ).setEmission(new Color(80, 40, 10))
                                .setMaterial(new Material().setKD(0.7).setKS(0.4).setShininess(40)),
                        new Triangle(
                                new Point(200, 17, 300.1),
                                new Point(220, 17, 300.1),
                                new Point(210, 20, 300.1)
                        ).setEmission(new Color(80, 40, 10))
                                .setMaterial(new Material().setKD(0.7).setKS(0.4).setShininess(40)),
                        // Leg table
                        new Cylinder(5.0, new Ray(new Point(35, -15, 150), new Vector(0, 3, 0)), 30.0)
                                .setEmission(legTable)
                                .setMaterial(new Material().setKD(0.8).setKS(0.3).setShininess(30)),
                        new Cylinder(5.0, new Ray(new Point(250, -15, 150), new Vector(0, 3, 0)), 30.0)
                                .setEmission(legTable)
                                .setMaterial(new Material().setKD(0.8).setKS(0.3).setShininess(30)),
                        new Cylinder(5.0, new Ray(new Point(35, -15, 300), new Vector(0, 3, 0)), 30.0)
                                .setEmission(legTable)
                                .setMaterial(new Material().setKD(0.8).setKS(0.3).setShininess(30)),
                        new Cylinder(5.0, new Ray(new Point(250, -15, 300), new Vector(0, 3, 0)), 30.0)
                                .setEmission(legTable)
                                .setMaterial(new Material().setKD(0.8).setKS(0.3).setShininess(30))
                );
        // Object and treey
        scene.geometries
                .add(
                        // Small decorative object on floor
                        new Sphere(
                                new Point(-80, -36, 40), 6
                        ).setEmission(new Color(200, 50, 50))
                                .setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(100)),

                        new Cylinder(15, new Ray(new Point(-100, -40, 0), new Vector(0, 3, 0)), 120.0)
                                .setEmission(new Color(102, 51, 0)) // Brown color for the pot
                                .setMaterial(new Material().setKD(0.8).setKS(0.3).setShininess(30)),

                        new Cylinder(40, new Ray(new Point(-100, -40, 0), new Vector(0, 3, 0)), 60.0)
                                .setEmission(new Color(102, 100, 20)) // Brown color for the pot
                                .setMaterial(new Material().setKD(0.8).setKS(0.3).setShininess(30)),
                        new Sphere(new Point(-100, 100, 0), 45)
                                .setEmission(new Color(15, 72, 16))
                                .setMaterial(new Material().setKD(0.6).setKS(0.2).setShininess(30).setKT(0.3)),
                        new Sphere(new Point(-120, 120, 10), 20)
                                .setEmission(new Color(15, 72, 16))
                                .setMaterial(new Material().setKD(0.6).setKS(0.2).setShininess(30).setKT(0.3)),
                        new Sphere(new Point(-80, 130, -10), 15)
                                .setEmission(new Color(15, 72, 16))
                                .setMaterial(new Material().setKD(0.6).setKS(0.2).setShininess(30).setKT(0.3)),
                        new Sphere(new Point(-100, 140, 20), 25)
                                .setEmission(new Color(15, 72, 16))
                                .setMaterial(new Material().setKD(0.6).setKS(0.2).setShininess(30).setKT(0.3)),
                        new Cylinder(5, new Ray(new Point(-100, 70, 0), new Vector(-1, 2, 0.2)), 70.0)
                                .setEmission(new Color(102, 51, 0)) // Brown color for branches
                                .setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(20)),
                        new Cylinder(5, new Ray(new Point(-100, 70, 0), new Vector(0.6, 2, -0.4)), 60.0)
                                .setEmission(new Color(102, 51, 0)) // Brown color for branches
                                .setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(20))

                );


        // Main ceiling light - warm centered light
        scene.lights.add(new PointLight(new Color(255, 220, 180), new Point(150, 150, 150))
                .setKl(0.0005).setKq(0.0002));

        // Table lamp light - creates a focal point
        scene.lights.add(new SpotLight(new Color(255, 200, 120), new Point(220, 30, 275), new Vector(0, -1, 0))
                .setKl(0.001).setKq(0.0005).setNarrowBeam(25));


        // Plant accent light - highlighting the houseplant
        scene.lights.add(new SpotLight(new Color(70, 200, 70), new Point(-150, 50, 50), new Vector(1, 0.5, -0.2))
                .setKl(0.001).setKq(0.001).setNarrowBeam(15));
        // Window light (coming through the "painting/window" on the back wall)
        scene.lights.add(new SpotLight(new Color(200, 220, 255), new Point(100, 120, -180), new Vector(-0.2, -0.3, 1))
                .setKl(0.00005).setKq(0.000025).setNarrowBeam(20));

        // Accent light to highlight the mirror
        scene.lights.add(new SpotLight(new Color(220, 220, 255), new Point(300, 110, 50), new Vector(0.7, -0.2, 0.1))
                .setKl(0.0001).setKq(0.0001).setNarrowBeam(10));

        // Soft ambient light from bottom corner to create depth
        scene.lights.add(new PointLight(new Color(100, 120, 150), new Point(-150, -30, 150))
                .setKl(0.0003).setKq(0.0003));

        // Decorative highlight for magazines on table
        scene.lights.add(new SpotLight(new Color(255, 240, 220), new Point(70, 50, 200), new Vector(0, -0.9, 0.1))
                .setKl(0.0002).setKq(0.0002).setNarrowBeam(15));


        camera.setResolution(800, 600)
                .setRayTracer(scene, RayTracerType.SIMPLE)
                .build() //
                .renderImage() //
                .writeToImage("IwantBounus1");

        cameraHighAngle.setResolution(800, 600)
                .setRayTracer(scene, RayTracerType.SIMPLE)
                .build() //
                .renderImage() //
                .writeToImage("IwantBounus2");

        cameraLeftView.setResolution(800, 600)
                .setRayTracer(scene, RayTracerType.SIMPLE)
                .build() //
                .renderImage() //
                .writeToImage("IwantBounus3");
        cameraRightView.setResolution(800, 600)
                .setRayTracer(scene, RayTracerType.SIMPLE)
                .build() //
                .renderImage() //
                .writeToImage("IwantBounus4");


    }
}
