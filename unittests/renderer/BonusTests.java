package renderer;
import geometries.Polygon;
import geometries.Cylinder;
import geometries.Intersectable;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;
import scene.Scene;

import static java.awt.Color.BLUE;

/**
 * Testing basic shadows
 * @author Dan Zilberstein
 */
class BonusTests {
   /** Default constructor to satisfy JavaDoc generator */
   BonusTests() { /* to satisfy JavaDoc generator */ }

   /** Scene of the tests */
   private final Scene          scene      = new Scene("Test scene");
   /** Camera builder of the tests */
   private final Camera.Builder camera     = Camera.getBuilder()
           .setLocation(new Point(200, 200, 800))
           .setDirection(new Point(0, 0, -100), new Vector(0, 1, 0))
           .setVpDistance(1000)
           .setVpSize(200, 200)
           .setRayTracer(scene, RayTracerType.SIMPLE);


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
                 ).setEmission(new Color(178, 34, 34)).setMaterial(new Material().setKD(0.8).setKS(0.4).setShininess(50)),new Polygon(
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


                 //blue cube
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
