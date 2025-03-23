package primitives;

/**
 * Represents a vector in 3D space, defined by (x, y, z) coordinates.
 * Provides operations like scaling, dot/cross products, and normalization.
 */
public class Vector extends Point {
    /**
     * Creates a Vector using (x, y, z) coordinates.
     *
     * @param x X-coordinate.
     * @param y Y-coordinate.
     * @param z Z-coordinate.
     * @throws IllegalArgumentException if the vector is (0, 0, 0).
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (this.xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector zero is not allowed.");
        }
    }

    /**
     * Creates a Vector from a Double3 object.
     *
     * @param xyz A Double3 of the (x, y, z) coordinates.
     * @throws IllegalArgumentException if xyz is (0, 0, 0).
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector zero is not allowed.");
        }
    }

    /**
     * Multiplies this vector by a scalar.
     *
     * @param scalar The multiplier.
     * @return A new scaled Vector.
     */
    public Vector scale(Double scalar) {
        return new Vector(this.xyz.scale(scalar));
    }

    /**
     * Computes the dot product with another vector.
     *
     * @param multi The other vector.
     * @return The dot product result as a scalar.
     */
    public double dotProduct(Vector multi) {
        return xyz.d1() * multi.xyz.d1() +
                xyz.d2() * multi.xyz.d2() +
                xyz.d3() * multi.xyz.d3();
    }

    /**
     * Computes the cross product with another vector.
     *
     * @param Vmulti The other vector.
     * @return A Vector perpendicular to both vectors.
     */
    public Vector crossProduct(Vector Vmulti) {
        return new Vector(xyz.d2() * Vmulti.xyz.d3() - xyz.d3() * Vmulti.xyz.d2(),
                xyz.d3() * Vmulti.xyz.d1() - xyz.d1() * Vmulti.xyz.d3(),
                xyz.d1() * Vmulti.xyz.d2() - xyz.d2() * Vmulti.xyz.d1());
    }

    /**
     * Calculates the squared length of the vector.
     *
     * @return The squared magnitude.
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    /**
     * Computes the vector's magnitude (length).
     *
     * @return The length.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Normalizes the vector to a length of 1.
     *
     * @return A normalized Vector.
     * @throws ArithmeticException if the length is zero.
     */
    public Vector normalize() {
        return new Vector(xyz.scale(1 / length()));
    }


    @Override
    public Vector add(Vector Add) {
        return new Vector(this.xyz.add(Add.xyz));
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Vector other && super.equals(other);
    }


    @Override
    public String toString() {
        return "->" + super.toString();
    }
}
