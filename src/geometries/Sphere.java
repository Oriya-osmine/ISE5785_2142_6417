package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

import java.util.List;

/**
 * Represents a sphere in 3D space defined by a center point and a radius.
 * Used for geometric calculations and rendering in 3D graphics.
 */
public class Sphere extends RadicalGeometry {
    /**
     * The center point of the sphere.
     * This defines the sphere's position in 3D space.
     */
    final private Point center;

    /**
     * Creates a new sphere with a given radius and center point.
     *
     * @param radius The radius of the sphere (must be greater than zero).
     * @param center The center of the sphere in 3D space.
     * @throws IllegalArgumentException if the radius is zero or negative.
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }


    @Override
    public Vector getNormal(Point surfacePoint) {
        //(surfacePoint-center)/|surfacePoint-O|
        return surfacePoint.subtract(center).normalize();
    }


    @Override
    public List<Point> findIntersections(Ray ray) {

        Vector u=center.subtract(ray.getHead());
        double tm=ray.getDirection().dotProduct(u);

        //if the ray 90 maalot from the center of sphere
        if (isZero(tm)){
            double d =ray.getHead().distance(this.center);
            //check if the head of ray in the sphere
            if(d<radius){
                double th =Math.sqrt(radius*radius-d*d);
                double t = alignZero(th);
                if (t > 0)
                    return List.of(ray.getHead().add(ray.getDirection().scale(t)));
            }
            //case1: the ray not in the sphere
            //case2:the head near or on the end of sphere
            return null;

        }


        double d =Math.sqrt(u.dotProduct(u)-tm*tm);
        if (d>=radius) //head of the ray out from sphere
            return null;

        double th =Math.sqrt(radius*radius-d*d);
        double t1=alignZero(tm+th);
        double t2=alignZero(tm-th);

        if (t1>0&&t2>0)
            return List.of(ray.getHead().add(ray.getDirection().scale(t1))
            ,ray.getHead().add(ray.getDirection().scale(t2)));
        else if (t1>0&&t2<=0)
             return List.of(ray.getHead().add(ray.getDirection().scale(t1)));
        else
            return  List.of(ray.getHead().add(ray.getDirection().scale(t2)));


    }
}
