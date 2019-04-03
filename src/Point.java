public class Point {
    private double x;
    private double y;
    private double z;

    /**
     *
     * @param x
     * @param y
     */
    public Point(double x, double y,double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    /**
     *
     * @return x
     */
    public double getX() {
        return this.x;
    }

    /**
     *
     * @return y
     */
    public double getY() {
        return this.y;
    }

    /**
     *
     * @return z
     */
    public double getZ(){
        return this.z;
    }
}