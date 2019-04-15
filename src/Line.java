import java.awt.*;
import java.awt.geom.Line2D;

/**
 *
 */
public class Line {
    private Point3D start;
    private Point3D end;

    /**
     * @param start
     * @param end
     */
    public Line(Point3D start, Point3D end) {
        this.start = start;
        this.end = end;
    }

    public Point3D getStart() {
        return start;
    }

    public Point3D getEnd() {
        return end;
    }
}