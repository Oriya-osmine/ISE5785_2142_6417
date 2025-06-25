package primitives;

/**
 * Represents a point in 3D space.
 * Each point is defined by an immutable coordinate represented as a {@link Double3} object.
 * Utility methods are provided for geometric calculations, such as vector subtraction, point addition,
 * and distance computation between points.
 */
public class Point {

    /**
     * Represents the origin point (0,0,0) in 3D space.
     */
    public final static Point ZERO = new Point(0, 0, 0);

    /**
     * Immutable 3D coordinates of the point represented using {@link Double3}.
     */
    final protected Double3 xyz;

    /**
     * Constructs a new 3D point with the specified x, y, and z coordinates.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param z the z-coordinate of the point
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * Constructs a new 3D point using a {@link Double3} object.
     *
     * @param xyz the {@link Double3} object representing the coordinates of the point
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }
    /**
         * Returns the x-coordinate of the point.
         *
         * @return the x-coordinate value
         */
        public double getX() {
            return xyz.d1();
        }

        /**
         * Returns the y-coordinate of the point.
         *
         * @return the y-coordinate value
         */
        public double getY() {
            return xyz.d2();
        }

        /**
         * Returns the z-coordinate of the point.
         *
         * @return the z-coordinate value
         */
        public double getZ() {
            return xyz.d3();
        }

    /**
     * Subtracts the provided {@link Point} from the current point to create a new {@link Vector}.
     *
     * @param otherPoint the point to subtract from the current point
     * @return a {@link Vector} representing the difference between the two points
     */

    public Vector subtract(Point otherPoint) {
        return new Vector(this.xyz.subtract(otherPoint.xyz));
    }

    /**
     * Adds the specified {@link Vector} to the current point to create a new {@link Point}.
     *
     * @param vectorAdd the vector to add to the current point
     * @return a new {@link Point} calculated by adding the vector to the current point
     */
    public Point add(Vector vectorAdd) {
        return new Point(this.xyz.add(vectorAdd.xyz));
    }

    /**
     * Calculates the square of the distance between the current point and another {@link Point}.
     * This method avoids the overhead of computing the square root.
     *
     * @param other the other point to calculate the squared distance to
     * @return the squared distance between the two points
     */
    public double distanceSquared(Point other) {
        double dx = this.xyz.d1() - other.xyz.d1();
        double dy = this.xyz.d2() - other.xyz.d2();
        double dz = this.xyz.d3() - other.xyz.d3();
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Calculates the distance between the current point and another {@link Point}.
     * This method computes the square root of the squared distance.
     *
     * @param other the other point to calculate the distance to
     * @return the distance between the two points
     */
    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Point other)
                && this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return "xyz=" + xyz;
    }
}
