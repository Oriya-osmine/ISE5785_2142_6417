package primitives;

public class Point {

    protected Double3 xyz;
    static Point ZERO = new Point(0,0,0);

    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            return (obj instanceof Point other)
                    && this.xyz.equals(other.xyz);
        }

    @Override
    public String toString() {
        return "xyz=" + xyz ;
    }

    public Vector subtract(Point otherPoint) {//ToDO
        return new Vector(this.xyz.subtract(otherPoint.xyz));
    }

    public Point add(Vector vectorAdd) {
        return new Point(this.xyz.add(vectorAdd.xyz));

    }


    public double distanceSquared(Point other) {
        Double3 delta = this.xyz.subtract(other.xyz);
        return delta.d1() * delta.d1() + delta.d2() * delta.d2() + delta.d3() * delta.d3();
    }


    
    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }

}
