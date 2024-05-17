package Project.controller.builders;

import Project.controller.GeometricShapeBuilder;
import Project.controller.Transformation;
import Project.model.*;
import javafx.scene.layout.Pane;

public class LineAndCircleIntersectionBuilder implements GeometricShapeBuilder {
    private GeometricLine line = null;
    private GeometricCircle circle = null;

    @Override
    public boolean acceptArgument(GeometricShape shape) {
        if (shape instanceof GeometricLine l) {
            if (line == null) {
                line = l;
                line.setOnClicked();
                System.out.println("Accepting line");
                return true;
            }
        }
        if (shape instanceof GeometricCircle c) {
            if (circle == null) {
                circle = c;
                circle.setOnClicked();
                System.out.println("Accepting circle");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReady() {
        return line != null && circle != null;
    }

    @Override
    public void reset() {
        line = null;
        circle = null;
    }

    @Override
    public void build(Plane2D plane, Transformation transformation, Pane viewPane, double planeX, double planeY) {
        GeometricPoint intersection1 = new GeometricPoint("Intersection 1", plane, transformation);
        GeometricPoint intersection2 = new GeometricPoint("Intersection 2", plane, transformation);
        GeometricShapeUpdater updater = new GeometricShapeUpdater() {
            private GeometricLine l = line;
            private GeometricCircle c = circle;

            @Override
            public void update() {
                setPoints(intersection1, intersection2, l, c);
            }
        };
        intersection1.setUpdater(updater);
        intersection2.setUpdater(updater);
        intersection1.update();
        intersection2.update();
        intersection1.setViewPane(viewPane);
        intersection2.setViewPane(viewPane);
        plane.addGeometricShape(intersection1);
        plane.addGeometricShape(intersection2);
    }

    public static void setPoints(GeometricPoint i1, GeometricPoint i2, double lA, double lB, double lC, double centerX, double centerY, double R) {
        double A = lA;
        double B = lB;
        double x0 = centerX;
        double y0 = centerY;
        if (Math.abs(lB) <= BuilderUtils.EPSILON) {
            A = lB;
            B = lA;
            x0 = centerY;
            y0 = centerX;
        }

        double a = A * A + B * B;
        double b = 2 * (A * lC + A * B * y0 - B * B * x0);
        double c = lC * lC + 2 * B * lC * y0 - B * B * (R * R - x0 * x0 - y0 * y0);
        double delta = b * b - 4 * a * c;
        i1.x = (-b + Math.sqrt(delta)) / (2 * a);
        i1.y = (-A * i1.x - lC) / B;
        i2.x = (-b - Math.sqrt(delta)) / (2 * a);
        i2.y = (-A * i2.x - lC) / B;

        if (Math.abs(lB) <= BuilderUtils.EPSILON) {
            double tmp = i1.x;
            i1.x = i1.y;
            i1.y = tmp;
            tmp = i2.x;
            i2.x = i2.y;
            i2.y = tmp;
        }

        if (B < 0) {
            double tmp = i1.x;
            i1.x = i2.x;
            i2.x = tmp;
            tmp = i1.y;
            i1.y = i2.y;
            i2.y = tmp;
        }
    }

    public static void setPoints(GeometricPoint i1, GeometricPoint i2, GeometricLine l, GeometricCircle c) {
        setPoints(i1, i2, l.A, l.B, l.C, c.centerX, c.centerY, c.R);
    }

    public static void setPoints(GeometricPoint i1, GeometricPoint i2, GeometricCircle c, GeometricLine l) {
        setPoints(i1, i2, l.A, l.B, l.C, c.centerX, c.centerY, c.R);
    }
}
