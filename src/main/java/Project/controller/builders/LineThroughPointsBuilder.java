
package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

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
                a.setOnClicked();
                System.out.println("Accepting point");
                return true;
            } else if (p != a) {
                b = p;
                b.setOnClicked();
                System.out.println("Accepting point");
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
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricLine line = new GeometricLine("Prosta", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pA = a;
            private GeometricPoint pB = b;

            @Override
            public void update() {
                setLine(line, pA, pB);
            }
        };
        line.setUpdater(updater);
        line.update();
        line.setViewPane(viewPane);
        plane.addGeometricShape(line);
    }

    /**
     * Sets the equation of a geometric line using the coordinates of two points.
     *
     * @param line the geometric line to set the equation for
     * @param x1   the x-coordinate of the first point
     * @param y1   the y-coordinate of the first point
     * @param x2   the x-coordinate of the second point
     * @param y2   the y-coordinate of the second point
     */
    public static void setLine(GeometricLine line, double x1, double y1, double x2, double y2) {
        line.A = y1 - y2;
        line.B = x2 - x1;
        line.C = x1 * y2 - x2 * y1;
    }

    /**
     * Sets the equation of a geometric line using two geometric points.
     *
     * @param line the geometric line to set the equation for
     * @param a    the first geometric point
     * @param b    the second geometric point
     */
    public static void setLine(GeometricLine line, GeometricPoint a, GeometricPoint b) {
        setLine(line, a.x, a.y, b.x, b.y);
    }
}
