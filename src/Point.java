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
     * @return
     */
    public double getX() {
        return this.x;
    }

    /**
     *
     * @return
     */
    public double getY() {
        return this.y;
    }
    public double getZ(){
        return this.z;
    }
}