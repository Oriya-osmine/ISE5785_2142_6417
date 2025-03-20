package primitives;

/**
 * Represents a vector in a 3D space
 */
public class Vector extends Point {
    /**
     * Constructor
     *
     * @param x Coordinate 1
     * @param y Coordinate 2
     * @param z Coordinate 3
     * @throws IllegalArgumentException if the vector is the zero vector
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (this.xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector zero is not allowed.");
        }
    }

    /**
     * Constructor
     *
     * @param xyz The coordinates of the vector
     * @throws IllegalArgumentException If the vector is the zero vector
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector zero is not allowed.");
        }
    }

    /**
     * Multiply the vector with scalar
     *
     * @param scalar The number to multiply with
     * @return The scalared vector
     */
    public Vector scale(Double scalar) {
        return new Vector(this.xyz.scale(scalar));
    }

    /**
     * Calculates the dot products of this vector wth another one
     *
     * @param multi The vector to perform the dot product on
     * @return The dot product of their "multiplication"
     */
    public double dotProduct(Vector multi) {
        return xyz.d1() * multi.xyz.d1() +
                xyz.d2() * multi.xyz.d2() +
                xyz.d3() * multi.xyz.d3();
    }

    /**
     * Calculates the cross product of this vector with another one
     *
     * @param Vmulti The vector to perform the cross product on
     * @return The cross product of their "multiplication"
     */
    public Vector crossProduct(Vector Vmulti) {
        return new Vector(xyz.d2() * Vmulti.xyz.d3() - xyz.d3() * Vmulti.xyz.d2(),
                xyz.d3() * Vmulti.xyz.d1() - xyz.d1() * Vmulti.xyz.d3(),
                xyz.d1() * Vmulti.xyz.d2() - xyz.d2() * Vmulti.xyz.d1());
    }

    /**
     * Calculates the length squared of the vector
     *
     * @return The length squared of th vector
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    /**
     * Calculates the length of the vector
     *
     * @return The length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Normalized the vector
     *
     * @return A new Normalized version of the vector
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
        return (obj instanceof Vector ComapareVec)
                && super.equals(ComapareVec);
    }
}
