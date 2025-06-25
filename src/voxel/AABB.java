package voxel;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * Represents an Axis-Aligned Bounding Box (AABB) in 3D space.
 */
public class AABB {
    /**
     * The minimum point of the AABB, representing the corner with the smallest coordinates
     */
    private final Point min;
    /**
     * The maximum point of the AABB, representing the corner with the largest coordinates
     */
    private final Point max;
    /**
     * The center point of the AABB, calculated lazily when requested
     * It is null until the center is computed for the first time
     */
    private Point center = null;
    /**
     * Min value before we decide its parallel
     */
    private final double DELTA = 1e-10;

    /**
     * Constructor
     *
     * @param min the minimum point of the AABB
     * @param max the maximum point of the AABB
     */
    public AABB(Point min, Point max) {
        this.min = min;
        this.max = max;
    }

    /**
     * Returns the minimum point of the AABB.
     *
     * @return the minimum point
     */
    public Point getMin() {
        return min;
    }

    /**
     * Returns the maximum point of the AABB.
     *
     * @return the maximum point
     */
    public Point getMax() {
        return max;
    }

    /**
     * Returns the center point of the AABB.
     * If the center has not been calculated yet, it computes it.
     *
     * @return the center point of the AABB
     */
    public Point getCenter() {
        if (center == null) {
            double centerX = (min.getX() + max.getX()) / 2;
            double centerY = (min.getY() + max.getY()) / 2;
            double centerZ = (min.getZ() + max.getZ()) / 2;
            center = new Point(centerX, centerY, centerZ);
        }
        return center;
    }

    /**
     * Checks if the AABB contains a given point.
     *
     * @param ray the ray to check for intersection with the AABB
     * @return true if the ray intersects the AABB, false otherwise
     */
    public boolean hasIntersection(Ray ray) {
        Point head = ray.getPoint(0);
        Vector dir = ray.getDirection();

        double tMin = Double.NEGATIVE_INFINITY;
        double tMax = Double.POSITIVE_INFINITY;

        // Build arrays of the X, Y, Z components for ray origin, direction, and AABB bounds
        double[] origins = { head.getX(), head.getY(), head.getZ() }; // ray origin components
        double[] dirs    = { dir.getX(),  dir.getY(),  dir.getZ()  }; // ray direction components
        double[] mins    = { min.getX(),  min.getY(),  min.getZ()  }; // AABB minimum corner components
        double[] maxs    = { max.getX(),  max.getY(),  max.getZ()  }; // AABB maximum corner components

        // Iterate over each axis: 0 = X, 1 = Y, 2 = Z
        for (int i = 0; i < 3; i++) {
            double axisDir    = dirs[i];    // direction component along this axis
            double axisOrigin = origins[i]; // ray origin component along this axis
            double axisMin    = mins[i];    // AABB min bound along this axis
            double axisMax    = maxs[i];    // AABB max bound along this axis

            // Handle parallel rays to an axis
            if (Math.abs(axisDir) < DELTA) {
                // Ray is parallel to slab. No hit if origin not within slab
                if (axisOrigin < axisMin || axisOrigin > axisMax) {
                    return false;
                }
                // Otherwise, parallel ray is inside this slab, so continue to next axis
                continue;
            }

            // Calculate intersection distances
            double t1 = (axisMin - axisOrigin) / axisDir;
            double t2 = (axisMax - axisOrigin) / axisDir;

            // Ensure t1 is the nearest intersection
            if (t1 > t2) {
                double temp = t1;
                t1 = t2;
                t2 = temp;
            }

            // Update tMin and tMax
            tMin = Math.max(tMin, t1);
            tMax = Math.min(tMax, t2);

            // No intersection if tMin > tMax
            if (tMin > tMax) {
                return false;
            }
        }

        // Ray intersects all 3 slabs, so there's an intersection
        return true;
    }

    /**
     * Union operation to combine two AABBs into a new AABB that encompasses both.
     * Helps to calculate the bounding box that contains all the scene objects.
     *
     * @param other the other AABB to union with this one
     * @return a new AABB that is the union of this AABB and the other AABB
     */
    public AABB union(AABB other) {
        Point newMin = new Point(
                Math.min(this.min.getX(), other.min.getX()),
                Math.min(this.min.getY(), other.min.getY()),
                Math.min(this.min.getZ(), other.min.getZ())
        );
        Point newMax = new Point(
                Math.max(this.max.getX(), other.max.getX()),
                Math.max(this.max.getY(), other.max.getY()),
                Math.max(this.max.getZ(), other.max.getZ())
        );
        return new AABB(newMin, newMax);
    }
}
