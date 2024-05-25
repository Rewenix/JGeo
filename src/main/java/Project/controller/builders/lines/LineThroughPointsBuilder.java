package Project.controller.builders.lines;

import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.model.*;
import Project.view.ViewablePlane;

/**
 * A builder class for creating a geometric line through two points.
 */
public class LineThroughPointsBuilder implements GeometricShapeBuilder {
    protected GeometricPoint a = null;
    protected GeometricPoint b = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricPoint p) {
            if (a == null) {
                a = p;
                return true;
            }
            else if (p != a) {
                b = p;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return a != null && b != null;
    }

    @Override
    public void reset() {
        a = null;
        b = null;
    }

    @Override
    public boolean awaitsPoint() {
        return b == null || a == null;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricLine line = new GeometricLine();
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pA = a;
            private GeometricPoint pB = b;

            @Override
            public void update() {
                setLine(line, pA, pB);
            }
        };
        line.setUpdater(updater);
        BuilderUtils.addToPlane(line, viewablePlane);
    }

    /**
     * Sets the equation of a geometric line using the coordinates of two points.
     *
     * @param line the geometric line to set the equation for
     * @param p1   the first point
     * @param p2   the second point
     */
    public static void setLine(GeometricLine line, BasicPoint p1, BasicPoint p2) {
        line.setCoordinates(getLine(p1, p2));
    }

    /**
     * Sets the equation of a geometric line using two geometric points.
     *
     * @param line the geometric line to set the equation for
     * @param a    the first geometric point
     * @param b    the second geometric point
     */
    public static void setLine(GeometricLine line, GeometricPoint a, GeometricPoint b) {
        setLine(line, a.point, b.point);
    }

    public static BasicLine getLine(BasicPoint p1, BasicPoint p2) {
        double a = p1.y - p2.y;
        double b = p2.x - p1.x;
        double c = p1.x * p2.y - p2.x * p1.y;
        return new BasicLine(a, b, c);
    }
}
