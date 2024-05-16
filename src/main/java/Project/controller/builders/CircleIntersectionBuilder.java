package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

public class CircleIntersectionBuilder implements GeometricShapeBuilder {
    private GeometricCircle a = null;
    private GeometricCircle b = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricCircle c) {
            if (a == null) {
                a = c;
                a.setOnClicked();
                System.out.println("Accepting circle");
                return true;
            } else if (c != a) {
                b = c;
                b.setOnClicked();
                System.out.println("Accepting circle");
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
        GeometricPoint i1 = new GeometricPoint("Intersection 1", plane, transformation);
        GeometricPoint i2 = new GeometricPoint("Intersection 2", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricCircle cA = a;
            private GeometricCircle cB = b;

            @Override
            public void update() {
                setPoints(i1, i2, cA, cB);
            }
        };
        i1.setUpdater(updater);
        i2.setUpdater(updater);
        i1.update();
        i2.update();
        i1.setViewPane(viewPane);
        i2.setViewPane(viewPane);
        plane.addGeometricShape(i1);
        plane.addGeometricShape(i2);
    }

    public static void setPoints(GeometricPoint i1, GeometricPoint i2, double x1, double y1, double r1, double x2, double y2, double r2) {
        double d = GeometricPoint.distance(x1, y1, x2, y2);
        double a = (Math.pow(r1, 2) - Math.pow(r2, 2) + Math.pow(d, 2)) / (2 * d);
        double h = Math.sqrt(Math.pow(r1, 2) - Math.pow(a, 2));
        double x3 = x1 + a * (x2 - x1) / d;
        double y3 = y1 + a * (y2 - y1) / d;
        i1.x = x3 + h * (y2 - y1) / d;
        i1.y = y3 - h * (x2 - x1) / d;
        i2.x = x3 - h * (y2 - y1) / d;
        i2.y = y3 + h * (x2 - x1) / d;
    }

    public static void setPoints(GeometricPoint i1, GeometricPoint i2, GeometricCircle cA, GeometricCircle cB) {
        setPoints(i1, i2, cA.centerX, cA.centerY, cA.R, cB.centerX, cB.centerY, cB.R);
    }
}
