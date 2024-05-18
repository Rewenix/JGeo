package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

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
                a.setOnClicked();
                return true;
            }
            else if (b == null) {
                b = p;
                b.setOnClicked();
                return true;
            }
            else if (c == null) {
                c = p;
                c.setOnClicked();
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
        if (a != null)
            a.unclick();
        a = null;
        if (b != null)
            b.unclick();
        b = null;
        if (c != null)
            c.unclick();
        c = null;
    }

    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricLine line = new GeometricLine("Dwusieczna", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pA = a;
            private GeometricPoint pB = b;
            private GeometricPoint pC = c;

            @Override
            public void update() {
                setLine(line, pA, pB, pC);
            }
        };
        BuilderUtils.setUpdaterAndAdd(line, updater, viewPane, plane);
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
