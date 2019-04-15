public class Vector {

    private double x;
    private double y;
    private double z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector add(Vector v) {
        return add(this, v);
    }

    public Vector sub(Vector v) {
        return sub(this, v);
    }

    public Vector scalarMult(double a) {
        return scalarMult(a, this);
    }

    public double dotProduct(Vector v) {
        return dotProduct(this, v);
    }

    public Vector crossProduct(Vector v) {
        return crossProduct(this, v);
    }

    public static Vector add(Vector v1, Vector v2) {
        double xSum = v1.getX() + v2.getX();
        double ySum = v1.getY() + v2.getY();
        double zSum = v1.getZ() + v2.getY();
        return new Vector(xSum, ySum, zSum);
    }

    public static Vector sub(Vector v1, Vector v2) {
        double xSum = v1.getX() - v2.getX();
        double ySum = v1.getY() - v2.getY();
        double zSum = v1.getZ() - v2.getZ();
        return new Vector(xSum, ySum, zSum);
    }

    public static Vector scalarMult(double a, Vector v) {
        double xRes = a * v.getX();
        double yRes = a * v.getY();
        double zRes = a * v.getZ();
        return new Vector(xRes, yRes, zRes);
    }

    public static double dotProduct(Vector v1, Vector v2) {
        double xRes = v1.getX() * v2.getX();
        double yRes = v1.getY() * v2.getY();
        double zRes = v1.getZ() * v2.getZ();
        return xRes + yRes + zRes;
    }

    public static Vector crossProduct(Vector v1, Vector v2) {
        double xRes = v1.getY() * v2.getZ() - v1.getZ() * v2.getY();
        double yRes = v1.getZ() * v2.getX() - v1.getX() * v2.getZ();
        double zRes = v1.getX() * v2.getY() - v1.getY() * v2.getX();
        return new Vector(xRes, yRes, zRes);
    }

    public static double angle(Vector v1, Vector v2) {
        double cAngle = dotProduct(v1, v2) / (v1.magnitude() * v2.magnitude());
        return Math.acos(cAngle);
    }

    public double magnitude() {
        double xMul = this.x * this.x;
        double yMul = this.y * this.y;
        double zMul = this.z * this.z;
        return Math.sqrt(xMul + yMul + zMul);
    }

    public Vector normalize() {
        return this.scalarMult(1 / this.magnitude());
    }

    public boolean isAffineCombination() {
        return (this.x + this.y + this.z == 1);
    }

    public boolean isConvexCombination() {
        boolean notNeg = (this.x >= 0) && (this.y >= 0) && (this.z >= 0);
        return notNeg && this.isAffineCombination();
    }


    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }
    public void setX(double x) {
        this.x = x;
    }

    public void setgetY(double y) {
        this.y = y;
    }
    public void setZ(double z) {
        this.z = z;
    }
}