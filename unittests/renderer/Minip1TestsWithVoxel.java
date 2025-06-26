package renderer;

import geometries.Cylinder;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

/**
 * Testing basic shadows
 *
 * @author Dan Zilberstein
 */

class Minip1TestsWithVoxel {

    /**
     * Scene of the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder of the tests
     */
    private final Camera.Builder camera = Camera.getBuilder()
            .setLocation(new Point(0, 50, 1000)) // מיקום המצלמה מול האובייקטים
            .setDirection(new Point(0, 0, 0), new Vector(0, 1, 0)) // כיוון המבט למרכז הסצנה
            .setVpDistance(1000)
            .setVpSize(400, 200);


    /**
     * basic test of all functionality
     */
    @Test
    void trianglesSphere() {


        //Low 3D table and Coffee
        scene.geometries
                .add(
                        new Polygon(
                                new Point(-22, 5, 650),
                                new Point(18, 5, 652),
                                new Point(22, 5, 632),
                                new Point(-18, 5, 630)
                        ).setEmission(new Color(101, 67, 33))
                                .setMaterial(new Material().setKD(0.6).setKS(0.3).setShininess(20)),
                        //Coffee cup
                        new Cylinder(2,
                                new Ray(new Point(-10, 5, 645), new Vector(0, 1, 0)),
                                3
                        ).setEmission(new Color(30, 30, 30))
                                .setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(100)),
                        //handle
                        new Cylinder(0.5,
                                new Ray(new Point(-8, 6.5, 645), new Vector(0, 0, -1)),
                                1
                        ).setEmission(new Color(30, 30, 30))
                                .setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(100)),
                        //Coffee cup 2
                        new Cylinder(2,
                                new Ray(new Point(0, 5, 640), new Vector(0, 1, 0)),
                                3
                        ).setEmission(new Color(40, 20, 20))
                                .setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(100)),
                        //handle
                        new Cylinder(0.5,
                                new Ray(new Point(2, 6.5, 640), new Vector(0, 0, -1)),
                                1
                        ).setEmission(new Color(40, 20, 20))
                                .setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(100)),
                        // Coffee in cups
                        new Cylinder(1.8,
                                new Ray(new Point(-10, 7, 645), new Vector(0, 0.1, 0)),
                                0.2
                        ).setEmission(new Color(30, 15, 5))
                                .setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(5)),
                        new Cylinder(1.8,
                                new Ray(new Point(0, 7, 640), new Vector(0, 0.1, 0)),
                                0.2
                        ).setEmission(new Color(30, 15, 5))
                                .setMaterial(new Material().setKD(0.8).setKS(0.2).setShininess(5)),

                        // Coffee beans scattered on the table
                        new Sphere(new Point(-15, 5.2, 640), 0.4)
                                .setEmission(new Color(50, 25, 0))
                                .setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(30)),
                        new Sphere(new Point(-14, 5.2, 642), 0.4)
                                .setEmission(new Color(40, 20, 0))
                                .setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(30)),
                        new Sphere(new Point(5, 5.2, 635), 0.4)
                                .setEmission(new Color(45, 22, 0))
                                .setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(30)),
                        new Sphere(new Point(7, 5.2, 637), 0.4)
                                .setEmission(new Color(55, 27, 0))
                                .setMaterial(new Material().setKD(0.7).setKS(0.3).setShininess(30)),

                        // spoon
                        new Cylinder(0.2,
                                new Ray(new Point(15, 5.1, 640), new Vector(-1, 0, 0.5)),
                                6
                        ).setEmission(new Color(192, 192, 192))
                                .setMaterial(new Material().setKD(0.7).setKS(0.5).setShininess(100)),
                        //legs
                        new Cylinder(1,
                                new Ray(new Point(-20, 5, 650), new Vector(0, -1, 0)),
                                10
                        ).setEmission(new Color(101, 67, 33))
                                .setMaterial(new Material().setKD(0.6).setKS(0.3).setShininess(20)),
                        new Cylinder(1,
                                new Ray(new Point(16, 5, 652), new Vector(0, -1, 0)),
                                10
                        ).setEmission(new Color(101, 67, 33))
                                .setMaterial(new Material().setKD(0.6).setKS(0.3).setShininess(20)),
                        new Cylinder(1,
                                new Ray(new Point(20, 5, 634), new Vector(0, -1, 0)),
                                10
                        ).setEmission(new Color(101, 67, 33))
                                .setMaterial(new Material().setKD(0.6).setKS(0.3).setShininess(20)),
                        new Cylinder(1,
                                new Ray(new Point(-16, 5, 632), new Vector(0, -1, 0)),
                                10
                        ).setEmission(new Color(101, 67, 33))
                                .setMaterial(new Material().setKD(0.6).setKS(0.3).setShininess(20))
                );


        //pergola
        scene.geometries
                .add(
                        new Polygon(
                                new Point(-30, 0, 550),
                                new Point(-30, 0, 595),
                                new Point(30, 0, 595),
                                new Point(30, 0, 550)
                        ).setEmission(new Color(40, 27, 13)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-30, 0, 595),
                                new Point(-30, 0, 640),
                                new Point(30, 0, 640),
                                new Point(30, 0, 595)
                        ).setEmission(new Color(42, 27, 12)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-30, 0, 640),
                                new Point(-30, 0, 685),
                                new Point(30, 0, 685),
                                new Point(30, 0, 640)
                        ).setEmission(new Color(44, 27, 12)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-30, 0, 685),
                                new Point(-30, 0, 730),
                                new Point(30, 0, 730),
                                new Point(30, 0, 685)
                        ).setEmission(new Color(46, 27, 11)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-30, 0, 730),
                                new Point(-30, 0, 775),
                                new Point(30, 0, 775),
                                new Point(30, 0, 730)
                        ).setEmission(new Color(47, 27, 11)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-30, 0, 775),
                                new Point(-30, 0, 820),
                                new Point(30, 0, 820),
                                new Point(30, 0, 775)
                        ).setEmission(new Color(49, 27, 10)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-30, 0, 820),
                                new Point(-30, 0, 865),
                                new Point(30, 0, 865),
                                new Point(30, 0, 820)
                        ).setEmission(new Color(50, 27, 10)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-30, 0, 865),
                                new Point(-30, 0, 910),
                                new Point(30, 0, 910),
                                new Point(30, 0, 865)
                        ).setEmission(new Color(52, 28, 9)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-30, 0, 910),
                                new Point(-30, 0, 955),
                                new Point(30, 0, 955),
                                new Point(30, 0, 910)
                        ).setEmission(new Color(54, 28, 8)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-30, 0, 955),
                                new Point(-30, 0, 1000),
                                new Point(30, 0, 1000),
                                new Point(30, 0, 955)
                        ).setEmission(new Color(56, 28, 8)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5))
                );


        //pergola
        scene.geometries
                .add(
                        new Polygon(
                                new Point(-100, 0, 600),
                                new Point(-100, 0, 590),
                                new Point(0, -5, 590),
                                new Point(0, -5, 600)
                        ).setEmission(new Color(48, 40, 20)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-100, 0, 590),
                                new Point(-100, 0, 580),
                                new Point(0, -5, 580),
                                new Point(0, -5, 590)
                        ).setEmission(new Color(46, 38, 19)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-100, 0, 580),
                                new Point(-100, 0, 570),
                                new Point(0, -5, 570),
                                new Point(0, -5, 580)
                        ).setEmission(new Color(45, 37, 18)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-100, 0, 570),
                                new Point(-100, 0, 560),
                                new Point(0, -5, 560),
                                new Point(0, -5, 570)
                        ).setEmission(new Color(43, 35, 16)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-100, 0, 560),
                                new Point(-100, 0, 550),
                                new Point(0, -5, 550),
                                new Point(0, -5, 560)
                        ).setEmission(new Color(42, 34, 15)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)), new Polygon(
                                new Point(0, -2, 625),
                                new Point(0, -2, 615),
                                new Point(100, 3, 615),
                                new Point(100, 3, 625)
                        ).setEmission(new Color(48, 40, 20)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(0, -2, 615),
                                new Point(0, -2, 605),
                                new Point(100, 3, 605),
                                new Point(100, 3, 615)
                        ).setEmission(new Color(46, 38, 19)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(0, -2, 605),
                                new Point(0, -2, 595),
                                new Point(100, 3, 595),
                                new Point(100, 3, 605)
                        ).setEmission(new Color(45, 37, 18)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(0, -2, 595),
                                new Point(0, -2, 585),
                                new Point(100, 3, 585),
                                new Point(100, 3, 595)
                        ).setEmission(new Color(43, 35, 16)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(0, -2, 585),
                                new Point(0, -2, 575),
                                new Point(100, 3, 575),
                                new Point(100, 3, 585)
                        ).setEmission(new Color(42, 34, 15)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        //path
                        new Polygon(
                                new Point(-100, 0, 600),
                                new Point(-100, 0, 580),
                                new Point(0, -5, 580),
                                new Point(0, -5, 600)
                        ).setEmission(new Color(48, 40, 20)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-100, 0, 580),
                                new Point(-100, 0, 560),
                                new Point(0, -5, 560),
                                new Point(0, -5, 580)
                        ).setEmission(new Color(44, 36, 18)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-100, 0, 560),
                                new Point(-100, 0, 550),
                                new Point(0, -5, 550),
                                new Point(0, -5, 560)
                        ).setEmission(new Color(40, 32, 16)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(0, -2, 625),
                                new Point(0, -2, 605),
                                new Point(100, 3, 605),
                                new Point(100, 3, 625)
                        ).setEmission(new Color(48, 40, 20)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(0, -2, 605),
                                new Point(0, -2, 585),
                                new Point(100, 3, 585),
                                new Point(100, 3, 605)
                        ).setEmission(new Color(44, 36, 18)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(0, -2, 585),
                                new Point(0, -2, 575),
                                new Point(100, 3, 575),
                                new Point(100, 3, 585)
                        ).setEmission(new Color(40, 32, 16)).setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5))
                );

        // Mountains and sky
        scene.geometries
                .add(
                        new Triangle(
                                new Point(50, 0, 300),
                                new Point(-40, -30, 400),
                                new Point(-36, -22, 395)
                        ).setEmission(new Color(56, 48, 36))
                                .setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Triangle(
                                new Point(50, 0, 300),
                                new Point(-36, -22, 395),
                                new Point(-32, -14, 390)
                        ).setEmission(new Color(58, 50, 38))
                                .setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Triangle(
                                new Point(50, 0, 300),
                                new Point(-32, -14, 390),
                                new Point(-28, -6, 385)
                        ).setEmission(new Color(59, 51, 40))
                                .setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Triangle(
                                new Point(50, 0, 300),
                                new Point(-28, -6, 385),
                                new Point(-24, 2, 380)
                        ).setEmission(new Color(61, 53, 42))
                                .setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Triangle(
                                new Point(50, 0, 300),
                                new Point(-24, 2, 380),
                                new Point(-20, 10, 375)
                        ).setEmission(new Color(62, 54, 44))
                                .setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Triangle(
                                new Point(50, 0, 300),
                                new Point(-20, 10, 375),
                                new Point(-16, 18, 370)
                        ).setEmission(new Color(64, 56, 46))
                                .setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Triangle(
                                new Point(50, 0, 300),
                                new Point(-16, 18, 370),
                                new Point(-12, 26, 365)
                        ).setEmission(new Color(66, 58, 48))
                                .setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Triangle(
                                new Point(50, 0, 300),
                                new Point(-12, 26, 365),
                                new Point(-8, 34, 360)
                        ).setEmission(new Color(67, 59, 50))
                                .setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Triangle(
                                new Point(50, 0, 300),
                                new Point(-8, 34, 360),
                                new Point(-4, 42, 355)
                        ).setEmission(new Color(69, 61, 52))
                                .setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Triangle(
                                new Point(50, 0, 300),
                                new Point(-4, 42, 355),
                                new Point(0, 50, 350)
                        ).setEmission(new Color(70, 62, 54))
                                .setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),


                        new Polygon(
                                new Point(0, 50, 350),
                                new Point(-20, 50, 350),
                                new Point(-38, -30, 370),
                                new Point(-20, -30, 370)
                        ).setEmission(new Color(56, 48, 36))
                                .setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-20, 50, 350),
                                new Point(-40, 50, 350),
                                new Point(-56, -30, 370),
                                new Point(-38, -30, 370)
                        ).setEmission(new Color(57, 49, 37))
                                .setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-40, 50, 350),
                                new Point(-60, 50, 350),
                                new Point(-74, -30, 370),
                                new Point(-56, -30, 370)
                        ).setEmission(new Color(58, 50, 38))
                                .setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-60, 50, 350),
                                new Point(-80, 50, 350),
                                new Point(-92, -30, 370),
                                new Point(-74, -30, 370)
                        ).setEmission(new Color(60, 52, 40))
                                .setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-80, 50, 350),
                                new Point(-100, 50, 350),
                                new Point(-110, -30, 370),
                                new Point(-92, -30, 370)
                        ).setEmission(new Color(61, 53, 41))
                                .setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-100, 50, 350),
                                new Point(-120, 50, 350),
                                new Point(-128, -30, 370),
                                new Point(-110, -30, 370)
                        ).setEmission(new Color(62, 54, 42))
                                .setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-120, 50, 350),
                                new Point(-140, 50, 350),
                                new Point(-146, -30, 370),
                                new Point(-128, -30, 370)
                        ).setEmission(new Color(63, 55, 43))
                                .setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-140, 50, 350),
                                new Point(-160, 50, 350),
                                new Point(-164, -30, 370),
                                new Point(-146, -30, 370)
                        ).setEmission(new Color(64, 56, 44))
                                .setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-160, 50, 350),
                                new Point(-180, 50, 350),
                                new Point(-182, -30, 370),
                                new Point(-164, -30, 370)
                        ).setEmission(new Color(66, 58, 46))
                                .setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),
                        new Polygon(
                                new Point(-180, 50, 350),
                                new Point(-200, 50, 350),
                                new Point(-200, -30, 370),
                                new Point(-182, -30, 370)
                        ).setEmission(new Color(67, 59, 47))
                                .setMaterial(new Material().setKD(0.9).setKS(0.2).setShininess(5)),

                        new Polygon(
                                new Point(-20, -25, 300),
                                new Point(-20, -25, 325),
                                new Point(100, 20, 320),
                                new Point(100, 20, 300)
                        ).setEmission(new Color(20, 16, 8))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(-20, -25, 325),
                                new Point(-20, -25, 350),
                                new Point(100, 20, 340),
                                new Point(100, 20, 320)
                        ).setEmission(new Color(25, 20, 12))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(-20, -25, 350),
                                new Point(-20, -25, 375),
                                new Point(100, 20, 360),
                                new Point(100, 20, 340)
                        ).setEmission(new Color(30, 25, 15))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(-20, -25, 375),
                                new Point(-20, -25, 400),
                                new Point(100, 20, 380),
                                new Point(100, 20, 360)
                        ).setEmission(new Color(34, 29, 19))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(-20, -25, 400),
                                new Point(-20, -25, 425),
                                new Point(100, 20, 400),
                                new Point(100, 20, 380)
                        ).setEmission(new Color(39, 33, 22))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(-20, -25, 425),
                                new Point(-20, -25, 450),
                                new Point(100, 20, 420),
                                new Point(100, 20, 400)
                        ).setEmission(new Color(44, 38, 26))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(-20, -25, 450),
                                new Point(-20, -25, 475),
                                new Point(100, 20, 440),
                                new Point(100, 20, 420)
                        ).setEmission(new Color(49, 42, 30))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(-20, -25, 475),
                                new Point(-20, -25, 500),
                                new Point(100, 20, 460),
                                new Point(100, 20, 440)
                        ).setEmission(new Color(54, 46, 34))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(-20, -25, 500),
                                new Point(-20, -25, 525),
                                new Point(100, 20, 480),
                                new Point(100, 20, 460)
                        ).setEmission(new Color(58, 51, 37))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(-20, -25, 525),
                                new Point(-20, -25, 570),
                                new Point(100, 20, 510),
                                new Point(100, 20, 480)
                        ).setEmission(new Color(63, 55, 41))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),


                        new Polygon(
                                new Point(200, 20, 300),
                                new Point(200, 20, 325),
                                new Point(100, 20, 320),
                                new Point(100, 20, 300)
                        ).setEmission(new Color(32, 28, 20))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 20, 325),
                                new Point(200, 20, 350),
                                new Point(100, 20, 340),
                                new Point(100, 20, 320)
                        ).setEmission(new Color(34, 30, 22))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 20, 350),
                                new Point(200, 20, 375),
                                new Point(100, 20, 360),
                                new Point(100, 20, 340)
                        ).setEmission(new Color(36, 32, 24))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 20, 375),
                                new Point(200, 20, 400),
                                new Point(100, 20, 380),
                                new Point(100, 20, 360)
                        ).setEmission(new Color(39, 35, 27))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 20, 400),
                                new Point(200, 20, 425),
                                new Point(100, 20, 400),
                                new Point(100, 20, 380)
                        ).setEmission(new Color(41, 37, 29))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 20, 425),
                                new Point(200, 20, 450),
                                new Point(100, 20, 420),
                                new Point(100, 20, 400)
                        ).setEmission(new Color(43, 39, 31))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 20, 450),
                                new Point(200, 20, 475),
                                new Point(100, 20, 440),
                                new Point(100, 20, 420)
                        ).setEmission(new Color(45, 41, 33))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 20, 475),
                                new Point(200, 20, 500),
                                new Point(100, 20, 460),
                                new Point(100, 20, 440)
                        ).setEmission(new Color(40, 40, 28))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 20, 500),
                                new Point(200, 20, 525),
                                new Point(100, 20, 480),
                                new Point(100, 20, 460)
                        ).setEmission(new Color(42, 38, 30))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 20, 525),
                                new Point(200, 20, 550),
                                new Point(100, 20, 500),
                                new Point(100, 20, 480)
                        ).setEmission(new Color(44, 40, 32))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),


                        //sky
                        new Polygon(
                                new Point(200, 100, 100),
                                new Point(200, 96, 100),
                                new Point(-200, 96, 100),
                                new Point(-200, 100, 100)
                        ).setEmission(new Color(12, 28, 52))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 96, 100),
                                new Point(200, 92, 100),
                                new Point(-200, 92, 100),
                                new Point(-200, 96, 100)
                        ).setEmission(new Color(16, 27, 56))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 92, 100),
                                new Point(200, 88, 100),
                                new Point(-200, 88, 100),
                                new Point(-200, 92, 100)
                        ).setEmission(new Color(20, 26, 56))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 88, 100),
                                new Point(200, 84, 100),
                                new Point(-200, 84, 100),
                                new Point(-200, 88, 100)
                        ).setEmission(new Color(25, 24, 51))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 84, 100),
                                new Point(200, 80, 100),
                                new Point(-200, 80, 100),
                                new Point(-200, 84, 100)
                        ).setEmission(new Color(29, 24, 55))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 80, 100),
                                new Point(200, 76, 100),
                                new Point(-200, 76, 100),
                                new Point(-200, 80, 100)
                        ).setEmission(new Color(34, 22, 50))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 76, 100),
                                new Point(200, 72, 100),
                                new Point(-200, 72, 100),
                                new Point(-200, 76, 100)
                        ).setEmission(new Color(38, 21, 46))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 72, 100),
                                new Point(200, 68, 100),
                                new Point(-200, 68, 100),
                                new Point(-200, 72, 100)
                        ).setEmission(new Color(42, 20, 42))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 68, 100),
                                new Point(200, 64, 100),
                                new Point(-200, 64, 100),
                                new Point(-200, 68, 100)
                        ).setEmission(new Color(46, 19, 38))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 64, 100),
                                new Point(200, 60, 100),
                                new Point(-200, 60, 100),
                                new Point(-200, 64, 100)
                        ).setEmission(new Color(50, 26, 42))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 60, 100),
                                new Point(200, 56, 100),
                                new Point(-200, 56, 100),
                                new Point(-200, 60, 100)
                        ).setEmission(new Color(55, 24, 37))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 56, 100),
                                new Point(200, 52, 100),
                                new Point(-200, 52, 100),
                                new Point(-200, 56, 100)
                        ).setEmission(new Color(59, 24, 33))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 52, 100),
                                new Point(200, 48, 100),
                                new Point(-200, 48, 100),
                                new Point(-200, 52, 100)
                        ).setEmission(new Color(64, 22, 28))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 48, 100),
                                new Point(200, 44, 100),
                                new Point(-200, 44, 100),
                                new Point(-200, 48, 100)
                        ).setEmission(new Color(52, 21, 24))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        new Polygon(
                                new Point(200, 44, 100),
                                new Point(200, 40, 100),
                                new Point(-200, 40, 100),
                                new Point(-200, 44, 100)
                        ).setEmission(new Color(60, 20, 20))
                                .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                        //moon
                        new Sphere(new Point(-120, 80, 150), 15)
                                .setEmission(new Color(240, 240, 230))
                                .setMaterial(new Material().setKD(0.8).setKS(0.6).setShininess(100)),

                        // Stars
                        new Sphere(new Point(-150, 95, 120), 1)
                                .setEmission(new Color(255, 255, 255))
                                .setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(100)),
                        new Sphere(new Point(-80, 85, 120), 0.8)
                                .setEmission(new Color(255, 255, 240))
                                .setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(100)),
                        new Sphere(new Point(-40, 90, 110), 1.2)
                                .setEmission(new Color(255, 255, 255))
                                .setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(100)),
                        new Sphere(new Point(30, 88, 130), 0.9)
                                .setEmission(new Color(255, 255, 240))
                                .setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(100)),
                        new Sphere(new Point(100, 92, 120), 1.1)
                                .setEmission(new Color(255, 255, 255))
                                .setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(100)),
                        new Sphere(new Point(160, 85, 115), 0.7)
                                .setEmission(new Color(255, 255, 240))
                                .setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(100)),
                        new Sphere(new Point(-180, 75, 125), 1.3)
                                .setEmission(new Color(255, 255, 255))
                                .setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(100)),
                        new Sphere(new Point(-60, 70, 135), 0.8)
                                .setEmission(new Color(255, 255, 240))
                                .setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(100)),
                        new Sphere(new Point(70, 65, 130), 1.0)
                                .setEmission(new Color(255, 255, 255))
                                .setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(100))

                );

        Point a = new Point(-20, -25, 550);
        Point b = new Point(100, -10, 550);
        Point c = new Point(100, 20, 500);
        for (int i = 1; i < 11; i++) {
            double f1 = i / 10.0;
            double f2 = (i + 1) / 10.0;
            Point p1 = a.add(b.subtract(a).scale(f1));
            Point p2 = a.add(b.subtract(a).scale(f2));
            double t = (i + 0.5) / 10.0;
            int r = (int) Math.round(82 + 28 * t);
            int g = (int) Math.round(72 + 28 * t);
            int bb = (int) Math.round(52 + 28 * t);
            scene.geometries.add(
                    new Triangle(p1, p2, c)
                            .setEmission(new Color(r, g, bb))
                            .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20))
            );
        }


        Point A = new Point(-50, -50, 200);
        Point B = new Point(-50, 45, 200);
        Point D = new Point(200, -20, 200);
        Point C = new Point(200, 45, 200);

        scene.geometries.add(
                new Polygon(
                        A.add(B.subtract(A).scale(0.3)),
                        A.add(B.subtract(A).scale(0.1)),
                        D.add(C.subtract(D).scale(0.1)),
                        D.add(C.subtract(D).scale(0.2))
                ).setEmission(new Color(21, 40, 21))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                new Polygon(
                        A.add(B.subtract(A).scale(0.1)),
                        A.add(B.subtract(A).scale(0.2)),
                        D.add(C.subtract(D).scale(0.2)),
                        D.add(C.subtract(D).scale(0.1))
                ).setEmission(new Color(25, 40, 18))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                new Polygon(
                        A.add(B.subtract(A).scale(0.2)),
                        A.add(B.subtract(A).scale(0.3)),
                        D.add(C.subtract(D).scale(0.3)),
                        D.add(C.subtract(D).scale(0.2))
                ).setEmission(new Color(30, 41, 16))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                new Polygon(
                        A.add(B.subtract(A).scale(0.3)),
                        A.add(B.subtract(A).scale(0.4)),
                        D.add(C.subtract(D).scale(0.4)),
                        D.add(C.subtract(D).scale(0.3))
                ).setEmission(new Color(35, 42, 14))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                new Polygon(
                        A.add(B.subtract(A).scale(0.4)),
                        A.add(B.subtract(A).scale(0.5)),
                        D.add(C.subtract(D).scale(0.5)),
                        D.add(C.subtract(D).scale(0.4))
                ).setEmission(new Color(40, 43, 20))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                new Polygon(
                        A.add(B.subtract(A).scale(0.5)),
                        A.add(B.subtract(A).scale(0.6)),
                        D.add(C.subtract(D).scale(0.6)),
                        D.add(C.subtract(D).scale(0.5))
                ).setEmission(new Color(43, 43, 16))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                new Polygon(
                        A.add(B.subtract(A).scale(0.6)),
                        A.add(B.subtract(A).scale(0.7)),
                        D.add(C.subtract(D).scale(0.7)),
                        D.add(C.subtract(D).scale(0.6))
                ).setEmission(new Color(43, 43, 12))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                new Polygon(
                        A.add(B.subtract(A).scale(0.7)),
                        A.add(B.subtract(A).scale(0.8)),
                        D.add(C.subtract(D).scale(0.8)),
                        D.add(C.subtract(D).scale(0.7))
                ).setEmission(new Color(43, 43, 8))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                new Polygon(
                        A.add(B.subtract(A).scale(0.8)),
                        A.add(B.subtract(A).scale(0.9)),
                        D.add(C.subtract(D).scale(0.9)),
                        D.add(C.subtract(D).scale(0.8))
                ).setEmission(new Color(39, 39, 4))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                new Polygon(
                        A.add(B.subtract(A).scale(0.9)),
                        A.add(B.subtract(A).scale(1.0)),
                        D.add(C.subtract(D).scale(1.0)),
                        D.add(C.subtract(D).scale(0.9))
                ).setEmission(new Color(39, 39, 0))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20))
        );

        //grass
        for (int i = 0; i < 10; i++) {
            double zTop = 1000 - i * 45;
            double zBottom = 1000 - (i + 1) * 45;
            int r = 34 + (i * 4);    // Gradually increasing red component
            int g = 70 + (i * 5);   // Gradually increasing green component
            int bbb = 34 + (i * 4);    // Gradually increasing blue component
            scene.geometries.add(
                    new Polygon(
                            new Point(-200, -4, zTop),
                            new Point(-200, -4, zBottom),
                            new Point(200, -4, zBottom),
                            new Point(200, -4, zTop)
                    ).setEmission(new Color(r, g, bbb))
                            .setMaterial(new Material().setKD(0.8).setKS(0.1).setShininess(10))
            );
        }


        //laps
        scene.geometries.add(
                new Cylinder(1, new Ray(new Point(-60, -4.5, 540), new Vector(0, 1, 0)), 15)
                        .setEmission(new Color(101, 67, 33))
                        .setMaterial(new Material().setKD(0.6).setKS(0.4).setShininess(20)),
                new Sphere(new Point(-60, 10.5, 540), 1)
                        .setEmission(new Color(255, 255, 0))
                        .setMaterial(new Material().setKD(0.6).setKS(0.4).setShininess(20)),
                new Triangle(
                        new Point(-60, 12.5, 540),
                        new Point(-62, 10.5, 540),
                        new Point(-60, 10.5, 535)
                ).setEmission(new Color(80, 80, 80))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                new Triangle(
                        new Point(-60, 12.5, 540),
                        new Point(-60, 10.5, 540),
                        new Point(-58, 10.5, 535)
                ).setEmission(new Color(80, 80, 80))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                new Triangle(
                        new Point(-60, 12.5, 540),
                        new Point(-58, 10.5, 540),
                        new Point(-62, 10.5, 540)
                ).setEmission(new Color(80, 80, 80))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),

                new Cylinder(1, new Ray(new Point(-30, -7.5, 540), new Vector(0, 1, 0)), 15)
                        .setEmission(new Color(101, 67, 33))
                        .setMaterial(new Material().setKD(0.6).setKS(0.4).setShininess(20)),
                new Sphere(new Point(-30, 7.5, 540), 1)
                        .setEmission(new Color(255, 255, 0))
                        .setMaterial(new Material().setKD(0.6).setKS(0.4).setShininess(20)),
                new Triangle(
                        new Point(-30, 9.5, 540),
                        new Point(-32, 7.5, 540),
                        new Point(-30, 7.5, 535)
                ).setEmission(new Color(80, 80, 80))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                new Triangle(
                        new Point(-30, 9.5, 540),
                        new Point(-30, 7.5, 535),
                        new Point(-28, 7.5, 540)
                ).setEmission(new Color(80, 80, 80))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                new Triangle(
                        new Point(-30, 9.5, 540),
                        new Point(-28, 7.5, 540),
                        new Point(-32, 7.5, 540)
                ).setEmission(new Color(80, 80, 80))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                new Cylinder(1, new Ray(new Point(20, -7.5, 540), new Vector(0, 1, 0)), 15)
                        .setEmission(new Color(101, 67, 33))
                        .setMaterial(new Material().setKD(0.6).setKS(0.4).setShininess(20)),
                new Sphere(new Point(20, 7.5, 540), 1)
                        .setEmission(new Color(255, 255, 0))
                        .setMaterial(new Material().setKD(0.6).setKS(0.4).setShininess(20)),
                new Triangle(
                        new Point(20, 9.5, 540),
                        new Point(18, 7.5, 540),
                        new Point(20, 7.5, 535)
                ).setEmission(new Color(80, 80, 80))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                new Triangle(
                        new Point(20, 9.5, 540),
                        new Point(20, 7.5, 535),
                        new Point(22, 7.5, 540)
                ).setEmission(new Color(80, 80, 80))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                new Triangle(
                        new Point(20, 9.5, 540),
                        new Point(22, 7.5, 540),
                        new Point(18, 7.5, 540)
                ).setEmission(new Color(80, 80, 80))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),

                new Cylinder(1, new Ray(new Point(70, -2.5, 570), new Vector(0, 1, 0)), 15)
                        .setEmission(new Color(101, 67, 33))
                        .setMaterial(new Material().setKD(0.6).setKS(0.4).setShininess(20)),
                new Sphere(new Point(70, 12.5, 570), 1)
                        .setEmission(new Color(255, 255, 0))
                        .setMaterial(new Material().setKD(0.6).setKS(0.4).setShininess(20)),
                new Triangle(
                        new Point(70, 14.5, 570),
                        new Point(68, 12.5, 570),
                        new Point(70, 12.5, 565)
                ).setEmission(new Color(80, 80, 80))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                new Triangle(
                        new Point(70, 14.5, 570),
                        new Point(70, 12.5, 565),
                        new Point(72, 12.5, 570)
                ).setEmission(new Color(80, 80, 80))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20)),
                new Triangle(
                        new Point(70, 14.5, 570),
                        new Point(72, 12.5, 570),
                        new Point(68, 12.5, 570)
                ).setEmission(new Color(80, 80, 80))
                        .setMaterial(new Material().setKD(0.5).setKS(0.3).setShininess(20))


        );


        scene.lights.add(new SpotLight(new Color(255, 244, 199), new Point(-60, 12, 540), new Vector(0.2, -1, 0.3))
                .setKl(0.001).setKq(0.0002).setNarrowBeam(15));
        scene.lights.add(new SpotLight(new Color(255, 244, 199), new Point(-30, 9, 540), new Vector(0.1, -1, 0.2))
                .setKl(0.001).setKq(0.0002).setNarrowBeam(15));
        scene.lights.add(new SpotLight(new Color(255, 244, 199), new Point(20, 9, 540), new Vector(-0.1, -1, 0.2))
                .setKl(0.001).setKq(0.0002).setNarrowBeam(15));
        scene.lights.add(new SpotLight(new Color(255, 244, 199), new Point(70, 14, 570), new Vector(-0.2, -1, 0.3))
                .setKl(0.001).setKq(0.0002).setNarrowBeam(15));

        // Additional ambient light for better overall illumination
        scene.setAmbientLight(new AmbientLight(new Color(0.2, 0.2, 0.2)));


        // Add warm lights for the pathway
        scene.lights.add(new SpotLight(new Color(255, 220, 150), new Point(-90, 5, 600), new Vector(0, -1, 0.2))
                .setKl(0.0005).setKq(0.0001).setNarrowBeam(25));
        scene.lights.add(new SpotLight(new Color(255, 220, 150), new Point(40, 5, 600), new Vector(0, -1, 0.2))
                .setKl(0.0005).setKq(0.0001).setNarrowBeam(25));

        // Add colored lights for visual interest
        scene.lights.add(new PointLight(new Color(180, 200, 255), new Point(-150, 20, 300))
                .setKl(0.0002).setKq(0.0001));


        // Add subtle ground illumination
        scene.lights.add(new SpotLight(new Color(100, 120, 80), new Point(0, 10, 650), new Vector(0, -1, 0))
                .setKl(0.0001).setKq(0.00005).setNarrowBeam(60));

        // Add star-like point lights in the sky
        scene.lights.add(new PointLight(new Color(240, 240, 255), new Point(-120, 80, 120))
                .setKl(0.0001).setKq(0.00001));


        // Add subtle colored edge lights
        scene.lights.add(new SpotLight(new Color(80, 100, 160), new Point(-200, 40, 400), new Vector(1, -0.5, 0))
                .setKl(0.0001).setKq(0.00005).setNarrowBeam(30));
        scene.lights.add(new SpotLight(new Color(160, 100, 80), new Point(200, 40, 400), new Vector(-1, -0.5, 0))
                .setKl(0.0001).setKq(0.00005).setNarrowBeam(30));

        // Add warm glow to the moon
        scene.lights.add(new PointLight(new Color(255, 255, 200), new Point(-120, 80, 150))
                .setKl(0.0001).setKq(0.00001));


        /*camera//
                .setResolution(1600, 1200) //
                .setMultithreading(10)
                .setDebugPrint(0.1)
                .build() //
                .renderImage()
                .writeToImage("3DSceneRendering1");*/


        camera//
                .setDepthOfField(10, 0.8, 350)
                .setResolution(1600, 1200) //
                .setRayTracer(scene, RayTracerType.VOXEL)
                .setMultithreading(-1)
                .build() //
                .renderImage()
                .writeToImage("aVOXEL");

        /*camera//
                .setDepthOfField(10, 0.8, 350)
                .setResolution(800, 600) //
                .setMultithreading(-1)
                .setDebugPrint(0.1)
                .setRayTracer(scene, RayTracerType.VOXEL)
                .build() //
                .renderImage()
                .writeToImage("bVOXEL");*/
        /*camera//
                .setDepthOfField(30, 0.7, 370)
                .setResolution(1600, 1200) //
                .setMultithreading(10)
                .setDebugPrint(0.1)
                .build() //
                .renderImage()
                .writeToImage("3DSceneRendering3");
        camera//
                .setDepthOfField(30, 3, 330)
                .setResolution(1600, 1200) //
                .setMultithreading(10)
                .setDebugPrint(0.1)
                .build() //
                .renderImage()
                .writeToImage("3DSceneRendering4");
        camera//
                .setDepthOfField(30, 0.9, 550)
                .setResolution(1600, 1200) //
                .setMultithreading(10)
                .setDebugPrint(0.1)
                .build() //
                .renderImage()
                .writeToImage("3DSceneRendering5");*/


    }

}




