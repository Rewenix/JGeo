package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;
import Project.model.GeometricPoint;

/**
 * A builder class for creating a circle through three points.
 */
public class CircleThroughThreePointsBuilder implements GeometricShapeBuilder {
    private GeometricPoint a, b, c;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricPoint p) {
            if (a == null) {
                a = p;
                a.setOnClicked();
                System.out.println("Accepting point");
                return true;
            } else if (b == null) {
                b = p;
                b.setOnClicked();
                System.out.println("Accepting point");
                return true;
            } else if (c == null) {
                c = p;
                c.setOnClicked();
                System.out.println("Accepting point");
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
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricCircle circle = new GeometricCircle("Okrąg", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pA = a;
            private GeometricPoint pB = b;
            private GeometricPoint pC = c;

            @Override
            public void update() {
                setCircle(circle, pA, pB, pC);
            }
        };
        circle.setUpdater(updater);
        circle.update();
        circle.setViewPane(viewPane);
        plane.addGeometricShape(circle);
    }

    /**
     * Sets the properties of the circle based on the given coordinates.
     *
     * @param circle The circle to set the properties for.
     * @param x1     The x-coordinate of the first point.
     * @param y1     The y-coordinate of the first point.
     * @param x2     The x-coordinate of the second point.
     * @param y2     The y-coordinate of the second point.
     * @param x3     The x-coordinate of the third point.
     * @param y3     The y-coordinate of the third point.
     */
    public static void setCircle(GeometricCircle circle, double x1, double y1, double x2, double y2, double x3,
            double y3) {
        double a = x1 * (y2 - y3) - y1 * (x2 - x3) + x2 * y3 - x3 * y2;
        double b = (x1 * x1 + y1 * y1) * (y3 - y2) + (x2 * x2 + y2 * y2) * (y1 - y3)
                + (x3 * x3 + y3 * y3) * (y2 - y1);
        double c = (x1 * x1 + y1 * y1) * (x2 - x3) + (x2 * x2 + y2 * y2) * (x3 - x1)
                + (x3 * x3 + y3 * y3) * (x1 - x2);
        double d = (x1 * x1 + y1 * y1) * (x3 * y2 - x2 * y3) + (x2 * x2 + y2 * y2) * (x1 * y3 - x3 * y1)
                + (x3 * x3 + y3 * y3) * (x2 * y1 - x1 * y2);
        circle.centerX = -b / (2 * a);
        circle.centerY = -c / (2 * a);
        circle.R = Math.sqrt(b * b + c * c - 4 * a * d) / (2 * Math.abs(a));
    }

    /**
     * Sets the properties of the circle based on the given points.
     *
     * @param circle The circle to set the properties for.
     * @param a      The first point.
     * @param b      The second point.
     * @param c      The third point.
     */
    public static void setCircle(GeometricCircle circle, GeometricPoint a, GeometricPoint b, GeometricPoint c) {
        setCircle(circle, a.x, a.y, b.x, b.y, c.x, c.y);
    }
}
