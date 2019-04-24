import java.awt.*;
import java.awt.geom.Line2D;

/**
 *
 */
public class Line {
    private Point2Di start;
    private Point2Di end;

    /**
     * @param start
     * @param end
     */
    public Line(Point2Di start, Point2Di end) {
        this.start = start;
        this.end = end;
    }
    public Line(double x1,double y1,double x2,double y2){
        this.start = new Point2Di(x1,y1);
        this.end = new Point2Di(x2,y2);
    }

    public Point2Di getStart() {
        return start;
    }

    public Point2Di getEnd() {
        return end;
    }
    /**
     * @param other is point to check if intersecting with this Line
     * @return true if intersecting,false if not.
     */
    public boolean isIntersecting(Line other) {
        // Prepare shortcuts for readability
        double startX = start.getX();
        double startY = start.getY();
        double endX = end.getX();
        double endY = end.getY();
        double otherStartX = other.start.getX();
        double otherStartY = other.start.getY();
        double otherEndY = other.end.getY();
        double otherEndX = other.end.getX();
        double bx = endX - startX;
        double by = endY - startY;
        double dx = otherEndX - otherStartX;
        double dy = otherEndY - otherStartY;
        double det = bx * dy - by * dx;
        //if Parallel return false
        if (det == 0) {
            return false;
        }
        double cx = otherStartX - startX;
        double cy = otherStartY - startY;
        double t = (cx * dy - cy * dx) / det;
        //if not between the 2 lines return false
        if (t < 0 || t > 1) {
            return false;
        }
        //if between 2 lines return true ( there is intersection)
        double u = (cx * by - cy * bx) / det;
        return !(u < 0) && !(u > 1);
    }


    /**
     * Gets the intersection point of lines.
     *
     * @param other the other line
     * @return the intersection point, null if they don't intersect
     */
    public Point2Di intersectionWith(Line other) {
        // Check if lines intersect
        if (!isIntersecting(other)) {
            return null;
        }

        // Name the parameters - for convenience
        double thisX1 = start.getX();
        double thisX2 = end.getX();
        double thisY1 = start.getY();
        double thisY2 = end.getY();
        double otherX1 = other.getStart().getX();
        double otherX2 = other.getEnd().getX();
        double otherY1 = other.getStart().getY();
        double otherY2 = other.getEnd().getY();

        // Say a line is defined by an equation: a*x + b*y = c.

        // Get our line's equation
        double a1 = thisY2 - thisY1;
        double b1 = thisX1 - thisX2;
        double c1 = a1 * thisX1 + b1 * thisY1;

        // Get other line's equation
        double a2 = otherY2 - otherY1;
        double b2 = otherX1 - otherX2;
        double c2 = a2 * otherX1 + b2 * otherY1;

        // Get the intersection point
        double det = a1 * b2 - a2 * b1;
        if (det == 0) {
            return null; // Lines are parallel - return null
        }
        return new Point2Di((b2 * c1 - b1 * c2) / det,
                (a1 * c2 - a2 * c1) / det);
    }
}