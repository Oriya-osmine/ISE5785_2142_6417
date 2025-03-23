package primitives;

/**
 * Represents a point in 3D space, defined by three fixed numbers (x, y, z).
 * This class includes methods to calculate distances, add vectors, and subtract other points.
 */
public class Point {

    /**
     * A constant point at the origin (0, 0, 0) in 3D space.
     */
    public final static Point ZERO = new Point(0, 0, 0);

    /**
     * Immutable 3D coordinates of the point represented using {@link Double3}.
     */
    final protected Double3 xyz;

    /**
     * Creates a point in 3D space with given x, y, z coordinates.
     *
     * @param x X-coordinate of the point.
     * @param y Y-coordinate of the point.
     * @param z Z-coordinate of the point.
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * Creates a point in 3D space using a Double3 object for the coordinates.
     *
     * @param xyz A Double3 object that holds x, y, z values of the point.
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Subtracts another point from this point, returning the resulting vector.
     *
     * @param otherPoint The point to subtract from this point.
     * @return A vector showing the direction and distance between the two points.
     */

    public Vector subtract(Point otherPoint) {
        return new Vector(this.xyz.subtract(otherPoint.xyz));
    }

    /**
     * Adds a vector to this point, resulting in a new point.
     *
     * @param vectorAdd The vector to add to this point.
     * @return A new point shifted by the vector.
     */
    public Point add(Vector vectorAdd) {
        return new Point(this.xyz.add(vectorAdd.xyz));
    }

    /**
     * Finds the squared distance between this point and another point.
     * Faster than calculating the actual distance because it skips the square root step.
     *
     * @param other Another point to measure the squared distance to.
     * @return The squared distance as a double.
     */
    public double distanceSquared(Point other) {
        double dx = this.xyz.d1() - other.xyz.d1();
        double dy = this.xyz.d2() - other.xyz.d2();
        double dz = this.xyz.d3() - other.xyz.d3();
        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Calculates the distance between this point and another point in space.
     *
     * @param other Another point to measure the distance to.
     * @return The direct distance as a double.
     */
    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Point other && xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return "" + xyz;
    }
}
