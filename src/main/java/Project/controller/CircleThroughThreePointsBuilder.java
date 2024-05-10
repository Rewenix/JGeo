package Project.controller;

import Project.model.*;
import javafx.scene.layout.Pane;
import Project.model.GeometricPoint;

public class CircleThroughThreePointsBuilder implements GeometricShapeBuilder {
    private GeometricPoint a, b, c;

    @Override
    public Class<?> expectedClass() {
        return GeometricPoint.class;
    }

    @Override
    public void acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricPoint p) {
            if (a == null) {
                a = p;
                System.out.println("Accepting point");
            } else if (b == null) {
                b = p;
                System.out.println("Accepting point");
            } else if (c == null) {
                c = p;
                System.out.println("Accepting point");
            }
        }
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
        GeometricCircle circle = new GeometricCircle("Okrag", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricPoint pA = a;
            private GeometricPoint pB = b;
            private GeometricPoint pC = c;

            @Override
            public void update() {
                double x1 = pA.x;
                double y1 = pA.y;
                double x2 = pB.x;
                double y2 = pB.y;
                double x3 = pC.x;
                double y3 = pC.y;
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
        };
        circle.setUpdater(updater);
        circle.update();
        viewPane.getChildren().add(circle.getDrawableShape());
        plane.addGeometricShape(circle);
    }
}