package Project.controller.builders.lines;

import Project.controller.GeometricShapeBuilder;
import Project.controller.builders.BuilderUtils;
import Project.model.GeometricShape;
import Project.model.GeometricShapeUpdater;
import Project.model.basicshapes.BasicPoint;
import Project.model.geometricshapes.GeometricLine;
import Project.model.geometricshapes.GeometricPoint;
import Project.view.viewable.ViewablePlane;

/**
 * A builder class for creating an angle bisector using three points.
 */
public class AngleBisectorThreePointsBuilder implements GeometricShapeBuilder {
    private GeometricPoint a, b, c;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricPoint p) {
            if (a == null) {
                a = p;
                return true;
            }
            else if (b == null) {
                b = p;
                return true;
            }
            else if (c == null) {
                c = p;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return a != null && b != null && c != null;
    }

    @Override
    public void reset() {
        a = null;
        b = null;
        c = null;
    }

    @Override
    public boolean awaitsPoint() {
        return a == null || b == null || c == null;
    }

    @Override
    public void build(ViewablePlane viewablePlane, double planeX, double planeY) {
        GeometricLine line = new GeometricLine(a, b, c);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private final GeometricPoint pA = a;
            private final GeometricPoint pB = b;
            private final GeometricPoint pC = c;

            @Override
            public void update() {
                setLine(line, pA, pB, pC);
            }
        };
        line.setUpdater(updater);
        BuilderUtils.addToPlane(line, viewablePlane);
    }

    public static void setLine(GeometricLine line, BasicPoint a, BasicPoint b, BasicPoint c) {
        double AB = BasicPoint.distance(a, b);
        double BC = BasicPoint.distance(b, c);

        double x = (BC * a.x + AB * c.x) / (AB + BC);
        double y = (BC * a.y + AB * c.y) / (AB + BC);

        LineThroughPointsBuilder.setLine(line, b, new BasicPoint(x, y));
    }

    /**
     * Sets the line of the angle bisector based on three points.
     *
     * @param line the line representing the angle bisector
     * @param a    the first point
     * @param b    the second point
     * @param c    the third point
     */
    public static void setLine(GeometricLine line, GeometricPoint a, GeometricPoint b, GeometricPoint c) {
        setLine(line, a.point, b.point, c.point);
    }

}
