package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;
import Project.model.GeometricPoint;

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

    public static void setCircle(GeometricCircle circle, GeometricPoint a, GeometricPoint b, GeometricPoint c) {
        setCircle(circle, a.x, a.y, b.x, b.y, c.x, c.y);
    }
}
