import java.awt.*;
import java.awt.geom.Line2D;

/**
 *
 */
public class Line {
    private Point start;
    private Point end;

    /**
     * @param start
     * @param end
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point((int) x1, (int) y1);
        this.end = new Point((int) x2, (int) y2);
    }
}